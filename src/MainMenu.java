import jdk.jfr.internal.tool.Main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

    int width;
    int height;

    JFrame frame;


    MainMenu(int deviceWidth, int deviceHeight, JFrame frame){

        this.width = deviceWidth;
        this.height = deviceHeight;

        setPreferredSize(new Dimension(this.width, this.height));
        singlePlayer.setSize(100, 25);
        this.add(singlePlayer);
        this.add(twoPlayer);
        singlePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);

        this.frame = frame;

    }

    JButton singlePlayer = new JButton("Single Player");
    JButton twoPlayer = new JButton("Two Player");

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }

    private void draw(Graphics g) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayer) {
            System.out.println("Single Player clicked");
            SnakeGame snakeGame = new SnakeGame(width, height);
            frame.add(snakeGame);
            snakeGame.setVisible(true);
            snakeGame.requestFocus();
            snakeGame.setPlayers2(false);
        }
        if (e.getSource() == twoPlayer) {
            System.out.println("Two Player clicked");
            SnakeGame snakeGame = new SnakeGame(width, height);
            frame.add(snakeGame);
            snakeGame.setVisible(true);
            snakeGame.requestFocus();
            snakeGame.setPlayers2(true);
        }
    }
}
