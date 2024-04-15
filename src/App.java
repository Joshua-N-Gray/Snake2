import javax.swing.*;
import java.awt.*;

public class App {
    public static boolean players2 = false;

    public static boolean snakeStart = true;

    // True for main menu | False for snake game
    public static boolean menuStatus = true;

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

        MainMenu mainMenu = new MainMenu(deviceWidth, deviceHeight);
        frame.add(mainMenu);
        frame.pack();

        SnakeGame snakeGame = new SnakeGame(deviceWidth, deviceHeight);

        while (true) {
            if (!menuStatus && snakeStart) {
                snakeGame.players2 = players2;
                frame.add(snakeGame);
                frame.remove(mainMenu);
                frame.pack();
                snakeStart = false;
                snakeGame.requestFocus();
            } else if (menuStatus) {
                frame.remove(snakeGame);
                frame.add(mainMenu);
                frame.pack();
                snakeStart = true;
            }

        }
    }
}// Class