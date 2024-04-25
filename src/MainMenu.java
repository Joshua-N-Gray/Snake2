import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MainMenu extends JPanel implements ActionListener {

    // Variable initiations
    int width;
    int height;

    // Main Menu
    JButton singlePlayer = new JButton("Single Player");
    JButton twoPlayer = new JButton("Two Player");
    JButton settings = new JButton("Settings");
    JButton quit = new JButton("Quit");
    JPanel second = new JPanel();
    JPanel main = new JPanel();
    JPanel third = new JPanel();
    JPanel fourth = new JPanel();

    // Settings Menu
    JPanel sets = new JPanel();
    JButton goBack = new JButton("Return");
    JButton addApple = new JButton("Add apple (max: "+SnakeGame.appleMax+"): " + App.appleCount);
    JButton lessenApple = new JButton("Remove apple (min: 1): " + App.appleCount);
    JButton addCherry = new JButton("Add cherry (max: "+SnakeGame.cherryMax+"): " + App.cherryCount);
    JButton lessenCherry = new JButton("Remove Cherry (min: 0): " + App.cherryCount);
    JButton addBanana = new JButton("Add banana (max: "+SnakeGame.bananaMax+"): " + App.bananaCount);
    JButton lessenBanana = new JButton("Remove banana (min: 0): " + App.bananaCount);



    MainMenu(int deviceWidth, int deviceHeight){

        // Main initiation
        main.add(singlePlayer);
        main.add(twoPlayer);
        third.add(settings);
        fourth.add(quit);
        main.setBackground(Color.black);
        second.setBackground(Color.black);
        third.setBackground(Color.black);
        fourth.setBackground(Color.black);
        singlePlayer.addActionListener(this);
        twoPlayer.addActionListener(this);
        settings.addActionListener(this);
        quit.addActionListener(this);

        singlePlayer.setPreferredSize(new Dimension(150, 50));
        twoPlayer.setPreferredSize(new Dimension(150, 50));
        settings.setPreferredSize(new Dimension(150, 50));
        quit.setPreferredSize(new Dimension(150, 50));

        this.add(second);
        this.add(main);
        this.add(third);
        this.add(fourth);

        // Settings initiation
        sets.setBackground(Color.darkGray);
        sets.add(goBack);
        sets.add(addApple);
        sets.add(lessenApple);
        sets.add(addCherry);
        sets.add(lessenCherry);
        sets.add(addBanana);
        sets.add(lessenBanana);

        goBack.setPreferredSize(new Dimension(150, 50));
        addApple.setPreferredSize(new Dimension(200, 50));
        lessenApple.setPreferredSize(new Dimension(200, 50));
        addCherry.setPreferredSize(new Dimension(200, 50));
        lessenCherry.setPreferredSize(new Dimension(200, 50));
        addBanana.setPreferredSize(new Dimension(200, 50));
        lessenBanana.setPreferredSize(new Dimension(200, 50));

        goBack.addActionListener(this);
        addApple.addActionListener(this);
        lessenApple.addActionListener(this);
        addCherry.addActionListener(this);
        lessenCherry.addActionListener(this);
        addBanana.addActionListener(this);
        lessenBanana.addActionListener(this);

        ////////////////////////////////////////////////////////////

        // General
        this.width = deviceWidth;
        this.height = deviceHeight;

        setPreferredSize(new Dimension(this.width, this.height));

        second.setPreferredSize(new Dimension(width, height * 2/3));
        main.setPreferredSize(new Dimension(width, height/9));
        third.setPreferredSize(new Dimension(width, height/9));
        fourth.setPreferredSize(new Dimension(width, height/9));
        sets.setPreferredSize(new Dimension(width, height));

        setBackground(Color.black);

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
        if (e.getSource() == quit){
            System.exit(1);
        }
        if (e.getSource() == settings){
            this.remove(main);
            this.remove(second);
            this.remove(third);
            this.remove(fourth);
            this.add(sets);
        }
        if (e.getSource() == goBack){
            this.remove(sets);
            this.add(second);
            this.add(main);
            this.add(third);
            this.add(fourth);
        }
        if (e.getSource() == addApple){
            if (App.appleCount < SnakeGame.appleMax) {
                App.appleCount++;
            }
            addApple.setLabel("Add apple (max: "+SnakeGame.appleMax+"): " + App.appleCount);
            lessenApple.setLabel("Remove apple (min: 1): " + App.appleCount);
        }
        if (e.getSource() == lessenApple){
            if (App.appleCount > 1) {
                App.appleCount--;
            }
            addApple.setLabel("Add apple (max: "+SnakeGame.appleMax+"): " + App.appleCount);
            lessenApple.setLabel("Remove apple (min: 1): " + App.appleCount);
        }
        if (e.getSource() == addCherry){
            if (App.cherryCount < SnakeGame.cherryMax) {
                App.cherryCount++;
            }
            addCherry.setLabel("Add cherry (max: "+SnakeGame.cherryMax+"): " + App.cherryCount);
            lessenCherry.setLabel("Remove Cherry (min: 0): " + App.cherryCount);
        }
        if (e.getSource() == lessenCherry){
            if (App.cherryCount > 0) {
                App.cherryCount--;
            }
            addCherry.setLabel("Add cherry (max: "+SnakeGame.cherryMax+"): " + App.cherryCount);
            lessenCherry.setLabel("Remove Cherry (min: 0): " + App.cherryCount);
        }
        if (e.getSource() == addBanana){
            if (App.bananaCount < SnakeGame.bananaMax) {
                App.bananaCount++;
            }
            addBanana.setLabel("Add banana (max: "+SnakeGame.bananaMax+"): " + App.bananaCount);
            lessenBanana.setLabel("Remove banana (min: 0): " + App.bananaCount);
        }
        if (e.getSource() == lessenBanana){
            if (App.bananaCount > 0) {
                App.bananaCount--;
            }
            addBanana.setLabel("Add banana (max: "+SnakeGame.bananaMax+"): " + App.bananaCount);
            lessenBanana.setLabel("Remove banana (min: 0): " + App.bananaCount);
        }
    }
}
