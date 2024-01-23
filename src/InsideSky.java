import javax.swing.*;
public class InsideSky extends JFrame {
    public InsideSky(){
        super("InsideSky");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 650);

        // load the gui at the center of the screen
        setLocationRelativeTo(null);

        // make our layout manager null to manually position our components within the gui
        setLayout(null);

        // prevent any resize of our gui
        setResizable(false);

    }
}
