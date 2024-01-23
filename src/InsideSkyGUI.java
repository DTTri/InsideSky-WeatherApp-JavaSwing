import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InsideSkyGUI extends JFrame {
    public InsideSkyGUI(){
        super("InsideSky");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //getContentPane().setBackground(Color.BLACK);
        setSize(450, 650);

        // load the gui at the center of the screen
        setLocationRelativeTo(null);

        // make our layout manager null to manually position our components within the gui
        setLayout(null);

        // prevent any resize of our gui
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents(){
        //search field
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        //search Button
        JButton searchButton = new JButton();
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.setIcon(loadImageIcon("src/assets/search.png", searchButton.getWidth(), searchButton.getHeight()));
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(searchButton);

        //weather condition
        JLabel weatherConditionImage = new JLabel();
        weatherConditionImage.setBounds(50, 125, 300, 217);
        weatherConditionImage.setIcon(loadImageIcon("src/assets/cloudyWX.png", weatherConditionImage.getWidth(), weatherConditionImage.getHeight()));
        add(weatherConditionImage);

        //temperature
        JLabel temperatureText= new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);
        // description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityImage = new JLabel();
        humidityImage.setBounds(15, 500, 74, 66);
        humidityImage.setIcon(loadImageIcon("src/assets/humidityWX.png", humidityImage.getWidth(), humidityImage.getHeight()));
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        // windspeed
        JLabel windspeedImage = new JLabel();
        windspeedImage.setBounds(220, 500, 74, 66);
        windspeedImage.setIcon(loadImageIcon("src/assets/windspeed.png", windspeedImage.getWidth(), windspeedImage.getHeight()));
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);
    }

    private ImageIcon loadImageIcon(String path, int width, int height)
    {
        try{
            BufferedImage img = ImageIO.read(new File(path));

            return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        }
        catch(IOException e){
           e.printStackTrace();
    }
        System.out.println("Could not find resource");
        return null;
    }
}
