import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {

        // Inits
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int deviceWidth = (int) Math.round(screenSize.getWidth());
        int deviceHeight = (int) Math.round(screenSize.getHeight());

        System.out.println(deviceWidth + ", " + deviceHeight);

        JFrame frame = new JFrame("Snake");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setSize(deviceWidth, deviceHeight);
        frame.setLocation(0, 0);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(deviceWidth, deviceHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();

    }// Main
}// Class