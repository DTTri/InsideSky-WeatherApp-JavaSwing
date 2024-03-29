// backend

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InsideSky {
    public static JSONObject getWeatherData(String locationName)
    {
         JSONArray locationData = getLocationData(locationName);

         //extract latitude and longitude data
         JSONObject location = (JSONObject) locationData.getFirst();
         double latitude = (double)location.get("latitude");
         double longitude = (double)location.get("longitude");

         // build API request URL with location coordinates
         String urlString="https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&timezone=Asia%2FBangkok";
         try{
             HttpURLConnection conn = fetchApiResponse(urlString);

             if(conn.getResponseCode()!=200)
             {
                 System.out.println("Error: Could not connect to API");
                 return null;
             }
             else{
                 StringBuilder resultJson = new StringBuilder();
                 Scanner sc = new Scanner(conn.getInputStream());
                 while(sc.hasNext())
                 {
                     resultJson.append(sc.nextLine());
                 }

                 sc.close();

                 conn.disconnect();

                 JSONParser parser = new JSONParser();
                 JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                 JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");

                 // get index by time
                 JSONArray time = (JSONArray) hourly.get("time");
                 int index= findIndexOfCurrentTime(time);

                 // get temperature by index
                 JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
                 double temperature = (double)temperatureData.get(index);

                 //get weather code by index
                 JSONArray weathercode = (JSONArray) hourly.get("weather_code");
                 String weatherCondition = convertWeatherCode((long) weathercode.get(index));

                 //get humidity by index
                 JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
                 long humidity = (long) relativeHumidity.get(index);

                 //get windspeed by index
                 JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
                 double windspeed = (double) windspeedData.get(index);

                 // summarize weather data
                 JSONObject weatherData = new JSONObject();
                 weatherData.put("temperature", temperature);
                 weatherData.put("weather_condition", weatherCondition);
                 weatherData.put("humidity", humidity);
                 weatherData.put("windspeed", windspeed);
                 return weatherData;


             }
         }catch(Exception e){
             e.printStackTrace();
         }

         return null;
    }

    public static JSONArray getLocationData(String locationName){
        locationName = locationName.replaceAll(" ", "+");

        //build API url with location parameter
        String urlString= "https://geocoding-api.open-meteo.com/v1/search?name="
                +locationName
                +"&count=10&language=en&format=json";

        try{
            HttpURLConnection conn = fetchApiResponse(urlString);
            if(conn.getResponseCode()!=200)
            {
                System.out.println("Error: Could not connect to API");
                return null;
            }
            else{ // 200 OK -> success
                StringBuilder resultJson = new StringBuilder();
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext())
                {
                    resultJson.append(sc.nextLine());
                }

                sc.close();

                conn.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                JSONArray locationData = (JSONArray) resultJsonObj.get("results");
                return locationData;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // create connection
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // set GET method
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
        }catch(IOException e)
        {
            // khong tim thay location nay/ khong the openConnection

            e.printStackTrace();
        }
        return null;
    }

    private static int findIndexOfCurrentTime(JSONArray timeList){
        String currentTime = getCurrentTime();
        for(int i=0; i<timeList.size(); i++)
        {
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }
        return 0;
    }

    public static String getCurrentTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();

        // format date to be 2023-09-02T00:00 (this is how is is read in the API)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");

        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    private static String convertWeatherCode(long weathercode)
    {
        String weatherCondition = "";
        if(weathercode == 0L){
            // clear
            weatherCondition = "Clear";
        }else if(weathercode <= 3L){
            // cloudy
            weatherCondition = "Cloudy";
        }else if((weathercode >= 51L && weathercode <= 67L)
                || (weathercode >= 80L && weathercode <= 99L)){
            // rain
            weatherCondition = "Rain";
        }else if(weathercode >= 71L && weathercode <= 77L){
            // snow
            weatherCondition = "Snow";
        }

        return weatherCondition;

    }
}
