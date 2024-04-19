import jdk.jfr.internal.tool.Main;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

    int width;
    int height;

    MainMenu(int deviceWidth, int deviceHeight){

        this.width = deviceWidth;
        this.height = deviceHeight;

        setPreferredSize(new Dimension(this.width, this.height));
        this.add(singlePlayer);
        this.add(twoPlayer);
        singlePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);
    }

    JButton singlePlayer = new JButton("Single Player");
    JButton twoPlayer = new JButton("Two Player");


    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.setColor(Color.black);
        g.fill3DRect(0, 0, width, height/3, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == singlePlayer) {
            System.out.println("Single Player clicked");
            App.players2 = false;
            App.menuStatus = false;
        }
        if (e.getSource() == twoPlayer) {
            System.out.println("Two Player clicked");
            App.players2 = true;
            App.menuStatus = false;
        }
    }
}
