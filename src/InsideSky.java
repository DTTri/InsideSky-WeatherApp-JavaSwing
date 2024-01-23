import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InsideSky extends JFrame {
    public InsideSky(){
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
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JButton searchButton = new JButton();
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.setIcon(loadImageIcon("src/assets/search.png", searchButton.getWidth(), searchButton.getHeight()));
        searchButton.setContentAreaFilled(false);
        searchButton.setBorderPainted(false);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(searchButton);

        JLabel weatherConditionImage = new JLabel();
        weatherConditionImage.setBounds(50, 125, 300, 217);
        weatherConditionImage.setIcon(loadImageIcon("src/assets/cloudyWX.png", weatherConditionImage.getWidth(), weatherConditionImage.getHeight()));
        add(weatherConditionImage);
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
