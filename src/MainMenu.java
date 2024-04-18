import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

    int width;
    int height;

    JButton singlePlayer = new JButton("Single Player");
    JButton twoPlayer = new JButton("Two Player");

    MainMenu(int deviceWidth, int deviceHeight){

        JPanel p = new JPanel();
        p.add(singlePlayer);
        p.add(twoPlayer);
        p.setBackground(Color.lightGray);

        this.width = deviceWidth;
        this.height = deviceHeight;

        setPreferredSize(new Dimension(this.width, this.height));

        p.setPreferredSize(new Dimension(width, height/3));

        singlePlayer.setPreferredSize(new Dimension(150, 50));
        twoPlayer.setPreferredSize(new Dimension(150, 50));

        this.add(p);

        singlePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);

        setBackground(Color.darkGray);

    }

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
            App.players2 = false;
            App.menuStatus = false;
            App.snakeRunning = false;
        }
        if (e.getSource() == twoPlayer) {
            App.players2 = true;
            App.menuStatus = false;
            App.snakeRunning = false;
        }
    }
}
