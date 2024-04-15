import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth;
    int boardHeight;
    int collectedApples = 0;
    int collectedBananas = 0;
    int EcollectedApples = 0;
    int EcollectedBananas = 0;

    //Game Settings
    int tileSize = 25;
    int gridX = 40;
    int gridY = 30;
    int appleCount = 5;
    int appleMax = 5;
    int bananaCount = 2;
    int bananaMax = 2;
    int obstacleCount = 20;
    int obstacleMax = 20;
    int gameSpeedms = 125;
    boolean players2 = false;
    boolean grid = false;
    boolean spineIDK = false;
    boolean locator = false;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile EsnakeHead;
    ArrayList<Tile> EsnakeBody;

    Tile[] apple;
    Tile[] banana;
    Tile[] obstacle;

    Random random;
    Timer gameLoop;

    int velocityX;
    int velocityY;

    int EvelocityX;
    int EvelocityY;

    boolean gameOver;
    boolean EgameOver;
    boolean gamePaused;

    enum direction{
        LEFT,RIGHT,DOWN,UP,RESET
    }

    direction previous;
    direction Eprevious;

    int boardXoffset;
    int boardYoffset;
    int spawnXoffset;
    int spawnYoffset;

    JButton exitToMenu = new JButton("Main Menu");
    JButton restart = new JButton("Restart");

    SnakeGame(int deviceWidth, int deviceHeight) {

        apple = new Tile[appleMax];
        banana = new Tile[bananaMax];
        obstacle = new Tile[obstacleMax];

        velocityX = 0;
        velocityY = 0;

        gameOver = false;
        EgameOver = false;
        gamePaused = false;

        this.boardWidth = gridX*tileSize;
        this.boardHeight = gridY*tileSize;

        boardXoffset = (deviceWidth-boardWidth)/2;
        boardYoffset = (deviceHeight-boardHeight)/2;

        setPreferredSize(new Dimension(deviceWidth, deviceHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        this.add(exitToMenu);
        exitToMenu.setVisible(false);
        exitToMenu.addActionListener(this);
        this.add(restart);
        restart.setVisible(false);
        restart.addActionListener(this);

        random = new Random();
        snakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize));
        snakeBody = new ArrayList();

        EsnakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize));
        EsnakeBody = new ArrayList();

        System.out.println(snakeHead.x + ", " + snakeHead.y);
        System.out.println(EsnakeHead.x + ", " + EsnakeHead.y);


        for (int i = 0; i < appleCount; ++i) {
            apple[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize));
        }

        for (int i = 0; i < bananaCount; ++i) {
            banana[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize));
        }

        for (int i = 0; i < obstacleCount; ++i) {
            obstacle[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize));
        }



        for (int i = 0; i<appleCount; i++) {
            placeApple(i);
        }
        for (int i = 0; i<bananaCount; i++) {
            placeBanana(i);
        }
        for (int i = 0; i<obstacleCount; i++) {
            placeObstacle(i);
        }

        gameLoop = new Timer(gameSpeedms, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        players2 = App.players2;
    }

    public void draw(Graphics g) {

        //Grid
        if (grid) {
            for (int i = 0; i < boardWidth / tileSize +1; i++) {
                g.drawLine(i * tileSize + boardXoffset, boardYoffset, i * tileSize + boardXoffset, boardHeight + boardYoffset);
            }// Draw Grid X
            for (int i = 0; i < boardHeight / tileSize +1; i++) {
                g.drawLine(boardXoffset, i * tileSize + boardYoffset, boardWidth + boardXoffset, i * tileSize + boardYoffset);
            }// Draw Grid Y
        }

        g.setColor(Color.cyan);
        g.drawLine(boardXoffset, boardYoffset, boardWidth + boardXoffset, boardYoffset);
        g.drawLine(boardXoffset, boardYoffset, boardXoffset, boardHeight + boardYoffset);
        g.drawLine(boardXoffset,  boardHeight + boardYoffset, boardWidth + boardXoffset, boardHeight + boardYoffset);
        g.drawLine(boardXoffset + boardWidth, boardHeight + boardYoffset, boardWidth + boardXoffset, boardYoffset);

        for (int i = 0; i < appleCount; ++i) {
            g.setColor(Color.red);
            g.fill3DRect(apple[i].x * tileSize + boardXoffset, apple[i].y * tileSize + boardYoffset, tileSize, tileSize, true);
        }

        for (int i = 0; i < bananaCount; ++i) {
            g.setColor(Color.yellow);
            g.fill3DRect(banana[i].x * tileSize + boardXoffset, banana[i].y * tileSize + boardYoffset, tileSize, tileSize, true);
        }

        for (int i = 0; i < obstacleCount; ++i) {
            g.setColor(Color.gray);
            g.fill3DRect(obstacle[i].x * tileSize + boardXoffset, obstacle[i].y * tileSize + boardYoffset, tileSize, tileSize, true);
        }

        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize + boardXoffset, snakeHead.y * tileSize + boardYoffset, tileSize, tileSize, true);

        for (int i = 0; i < snakeBody.size(); ++i) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize + boardXoffset, snakePart.y * tileSize + boardYoffset, tileSize, tileSize, true);
        }

        if (players2) {
            g.setColor(Color.blue);
            g.fill3DRect(EsnakeHead.x * tileSize + boardXoffset, EsnakeHead.y * tileSize + boardYoffset, tileSize, tileSize, true);

            for (int i = 0; i < EsnakeBody.size(); ++i) {
                Tile EsnakePart = EsnakeBody.get(i);
                g.fill3DRect(EsnakePart.x * tileSize + boardXoffset, EsnakePart.y * tileSize + boardYoffset, tileSize, tileSize, true);
            }
        }

        g.setFont(new Font("TimesNewRoman", 1, 16));
        if (gameOver) {
            g.setColor(Color.green);
            g.drawString("Game Over: " + (collectedApples + (collectedBananas * 3)), boardXoffset + 10, boardYoffset - 10);
        } else {
            g.setColor(Color.green);
            g.drawString("Score: " + (collectedApples + (collectedBananas * 3)), boardXoffset + 10, boardYoffset - 10);
        }

        if (players2) {
            if (EgameOver) {
                g.setColor(Color.blue);
                FontMetrics fm = g.getFontMetrics();
                int Ilength = fm.stringWidth("Game Over: " + String.valueOf(EsnakeBody.size()));
                g.drawString("Game Over: " + (EcollectedApples + (EcollectedBananas * 3)), boardXoffset  + boardWidth - Ilength - 10, boardYoffset - 10);
            } else {
                g.setColor(Color.blue);
                FontMetrics fm = g.getFontMetrics();
                int Ilength = fm.stringWidth("Score: " + (EcollectedApples + (EcollectedBananas * 3)));
                g.drawString("Score: " + (EcollectedApples + (EcollectedBananas * 3)), boardXoffset + boardWidth - Ilength - 10, boardYoffset - 10);
            }
        }

        if (gamePaused) {
            g.setColor(Color.yellow);
            FontMetrics fm = g.getFontMetrics();
            int Ilength = fm.stringWidth("Game Paused");
            int Iheight = fm.getHeight();
            g.drawString("Game Paused", boardWidth/2 - Ilength/2 + boardXoffset, boardHeight/2 - Iheight/2 + boardYoffset);
        }

        if (spineIDK) {
            g.setColor(Color.green);
            for (int i = 0; i < snakeBody.size(); ++i) {
                Tile snakePart = snakeBody.get(i);
                g.drawLine(snakeHead.x * tileSize + tileSize / 2, snakeHead.y * tileSize + tileSize / 2, snakePart.x * tileSize + tileSize / 2, snakePart.y * tileSize + tileSize / 2);
            }

            if (players2) {
                g.setColor(Color.blue);
                for (int i = 0; i < EsnakeBody.size(); ++i) {
                    Tile EsnakePart = EsnakeBody.get(i);
                    g.drawLine(EsnakeHead.x * tileSize + tileSize / 2, EsnakeHead.y * tileSize + tileSize / 2, EsnakePart.x * tileSize + tileSize / 2, EsnakePart.y * tileSize + tileSize / 2);
                }
            }
        }

        if (locator) {
            g.setColor(Color.yellow);
            g.drawLine(snakeHead.x * tileSize + tileSize / 2, snakeHead.y * tileSize + tileSize / 2, boardWidth / 2, boardHeight / 2);
        }

        //Show border tiles
        /*g.setColor(Color.yellow);
        for (int i=0; i<boardWidth; i=i+tileSize) {
            g.fill3DRect(i, 0, tileSize, tileSize, true);
        }
        for (int i=0; i<boardHeight; i=i+tileSize) {
            g.fill3DRect(0, i, tileSize, tileSize, true);
        }
        for (int i=0; i<boardWidth; i=i+tileSize) {
            g.fill3DRect(i, boardHeight-tileSize, tileSize, tileSize, true);
        }
        for (int i=0; i<boardHeight; i=i+tileSize) {
            g.fill3DRect(boardHeight-tileSize, i, tileSize, tileSize, true);
        }*/

    }

    public void placeApple(int i) {
            apple[i].x = random.nextInt(boardWidth / tileSize);
            apple[i].y = random.nextInt(boardHeight / tileSize);
            //Continue to replace same apple until valid location found
            spawnCheckapple(i);
    }

    public void placeBanana(int i) {
            banana[i].x = random.nextInt(boardWidth / tileSize);
            banana[i].y = random.nextInt(boardHeight / tileSize);
            //Continue to replace same banana until valid location found
            spawnCheckBanana(i);
    }

    public void placeObstacle(int i) {
            obstacle[i].x = random.nextInt(boardWidth / tileSize);
            obstacle[i].y = random.nextInt(boardHeight / tileSize);
            //Continue to replace same obstacle until valid location found
            spawnCheckObstacle(i);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public boolean onWall(Tile tile1) {
        return tile1.x * tileSize >= boardWidth - tileSize || tile1.x == 0 || tile1.y * tileSize >= boardHeight - tileSize || tile1.y == 0;
    }

    public void move() {

        Tile snakePart;
        Tile EsnakePart;

        if (!gameOver) {

            //Snake Eat

            for (int i = 0; i < appleCount; ++i) {
                if (collision(snakeHead, apple[i])) {
                    snakeBody.add(new Tile(apple[i].x, apple[i].y));
                    placeApple(i);
                    collectedApples++;
                }
            }

            for (int i = 0; i < bananaCount; ++i) {
                if (collision(snakeHead, banana[i])) {
                    for (int j = 0; j < 3; j++) {
                        snakeBody.add(new Tile(banana[i].x, banana[i].y));
                    }
                    placeBanana(i);
                    collectedBananas++;
                }
            }

            //Snake Move

            for (int i = snakeBody.size() - 1; i >= 0; --i) {
                snakePart = snakeBody.get(i);
                if (i == 0) {
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                } else {
                    Tile prevSnakePart = snakeBody.get(i - 1);
                    snakePart.x = prevSnakePart.x;
                    snakePart.y = prevSnakePart.y;
                }
            }

            if (velocityX == 1) {
                previous = direction.RIGHT;
            }
            if (velocityX == -1) {
                previous = direction.LEFT;
            }
            if (velocityY == 1) {
                previous = direction.DOWN;
            }
            if (velocityY == -1) {
                previous = direction.UP;
            }

            snakeHead.x += velocityX;
            snakeHead.y += velocityY;

            //Snake Collisions

            for (int i = 0; i < snakeBody.size(); ++i) {
                snakePart = snakeBody.get(i);
                if (collision(snakeHead, snakePart)) {
                    gameOver = true;
                }
                if (players2) {
                    for (int h = 0; h < EsnakeBody.size(); ++h) {
                        EsnakePart = EsnakeBody.get(h);
                        if (collision(snakeHead, EsnakePart)) {
                            gameOver = true;
                        }
                    }
                }
            }

            //?????????
            for (int i = 0; i < snakeBody.size(); ++i) {
                EsnakePart = snakeBody.get(i);

            }

            for (int i = 0; i < obstacleCount; ++i) {
                if (collision(snakeHead, obstacle[i])) {
                    gameOver = true;
                }
            }

            //Snake Kill at Border

            if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize >= boardHeight) {
                gameOver = true;
            }

        }

        if (players2 && !EgameOver) {

            //Esnake Eat

            for (int i = 0; i < appleCount; ++i) {
                if (collision(EsnakeHead, apple[i])) {
                    EsnakeBody.add(new Tile(apple[i].x, apple[i].y));
                    placeApple(i);
                    EcollectedApples++;
                }
            }

            for (int i = 0; i < bananaCount; ++i) {
                if (collision(EsnakeHead, banana[i])) {
                    for (int j = 0; j < 3; j++) {
                        EsnakeBody.add(new Tile(banana[i].x, banana[i].y));
                    }
                    placeBanana(i);
                    EcollectedBananas++;
                }
            }

            //Esnake Move

            for (int i = EsnakeBody.size() - 1; i >= 0; --i) {
                EsnakePart = EsnakeBody.get(i);
                if (i == 0) {
                    EsnakePart.x = EsnakeHead.x;
                    EsnakePart.y = EsnakeHead.y;
                } else {
                    Tile EprevSnakePart = EsnakeBody.get(i - 1);
                    EsnakePart.x = EprevSnakePart.x;
                    EsnakePart.y = EprevSnakePart.y;
                }
            }

            if (EvelocityX == 1) {
                Eprevious = direction.RIGHT;
            }
            if (EvelocityX == -1) {
                Eprevious = direction.LEFT;
            }
            if (EvelocityY == 1) {
                Eprevious = direction.DOWN;
            }
            if (EvelocityY == -1) {
                Eprevious = direction.UP;
            }

            EsnakeHead.x += EvelocityX;
            EsnakeHead.y += EvelocityY;

            //Esnake Collisions

            for (int i = 0; i < EsnakeBody.size(); ++i) {
                EsnakePart = EsnakeBody.get(i);
                for (int h = 0; h < snakeBody.size(); ++h) {
                    snakePart = snakeBody.get(h);
                    if (collision(EsnakeHead, EsnakePart) || collision(EsnakeHead, snakePart)) {
                        EgameOver = true;
                    }
                }
            }


            for (int i = 0; i < obstacleCount; ++i) {
                if (collision(EsnakeHead, obstacle[i])) {
                    EgameOver = true;
                }
            }

            //Esnake Kill at Border

            if (EsnakeHead.x * tileSize < 0 || EsnakeHead.x * tileSize >= boardWidth || EsnakeHead.y * tileSize < 0 || EsnakeHead.y * tileSize >= boardHeight) {
                EgameOver = true;
            }

        }
    }

    public void spawnCheckapple(int i) {
        //replace apple if spawned on top of snake
        if (collision(snakeHead, apple[i])) {
            placeApple(i);
        }

        if (players2) {
            if (collision(EsnakeHead, apple[i])) {
                placeApple(i);
            }
        }

        //replace apple if spawned on an obstacle
        for(int h = 0; h < obstacleCount; ++h) {
            if (collision(apple[i], obstacle[h])) {
                placeApple(i);
            }
        }

        //replace apple if apple is overlapping
        for(int h = 0; h < appleCount; ++h) {
            if (collision(apple[i], apple[h])) {
                if (i != h) {
                    placeApple(i);
                }
            }
        }
    }

    public void spawnCheckBanana(int i) {
        //replace banana if spawned on top of snake
        if (collision(snakeHead, banana[i])) {
            placeBanana(i);
        }

        if (players2) {
            if (collision(EsnakeHead, banana[i])) {
                placeBanana(i);
            }
        }

        //replace banana if spawned on an obstacle
        for(int h = 0; h < obstacleCount; ++h) {
            if (collision(banana[i], obstacle[h])) {
                placeBanana(i);
            }
        }

        //replace banana if banana is overlapping
        for(int h = 0; h < bananaCount; ++h) {
            if (collision(banana[i], banana[h])) {
                if (i != h) {
                    placeBanana(i);
                }
            }
        }
    }

    public void spawnCheckObstacle(int i) {

        //replace obstacle if spawned on top of snake
        if (collision(snakeHead, obstacle[i])) {
            placeObstacle(i);
        }

        if (players2) {
            if (collision(EsnakeHead, obstacle[i])) {
                placeObstacle(i);
            }
        }

        //replace obstacle if next to wall
        if (onWall(obstacle[i])) {
            placeObstacle(i);
        }

        //replace obstacle if obstacles are overlapping
        for(int h = 0; h < obstacleCount; ++h) {
            if (collision(obstacle[i], obstacle[h])) {
                if (i != h) {
                    placeObstacle(i);
                }
            }
        }
    }

    public void pauseGame() {
        gamePaused = true;
    }

    public void unpauseGame() {
        gamePaused = false;
    }

    private void resetGame() {

        exitToMenu.setVisible(false);
        restart.setVisible(false);

        gameOver = false;
        collectedApples = 0;
        collectedBananas = 0;
        if (players2){
            EgameOver = false;
            EcollectedApples = 0;
            EcollectedBananas = 0;
        }
        gameLoop.start();

        velocityX = 0;
        velocityY = 0;
        previous = direction.RESET;

        EvelocityX = 0;
        EvelocityY = 0;
        Eprevious = direction.RESET;

        snakeHead.x = random.nextInt(boardWidth / tileSize);
        snakeHead.y = random.nextInt(boardHeight / tileSize);

        if (players2) {
            EsnakeHead.x = random.nextInt(boardWidth / tileSize);
            EsnakeHead.y = random.nextInt(boardHeight / tileSize);
        }

        for (int i = snakeBody.size() - 1; i >= 0; --i) {
            snakeBody.remove(i);
        }

        if (players2) {
            for (int i = EsnakeBody.size() - 1; i >= 0; --i) {
                EsnakeBody.remove(i);
            }
        }

        for (int i = 0; i<appleCount; i++) {
            placeApple(i);
        }
        for (int i = 0; i<bananaCount; i++) {
            placeBanana(i);
        }
        for (int i = 0; i<obstacleCount; i++) {
            placeObstacle(i);
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (!gamePaused) {
            move();
        }

        repaint();

        if (players2) {
            if (gameOver && EgameOver) {
                gameLoop.stop();
//                exitToMenu.setVisible(true);
                restart.setVisible(true);
                repaint();
            }
        } else {
            if (gameOver) {
                gameLoop.stop();
//                exitToMenu.setVisible(true);
                restart.setVisible(true);
                repaint();
            }
        }

        if (e.getSource() == restart && gameOver){
            resetGame();
        }

/*        if (e.getSource() == exitToMenu){
            App.menuStatus = true;
            App.snakeStart = true;
            System.out.printf("%b, %b%n", App.menuStatus, App.snakeStart);
            resetGame();
        }*/

    }

    public void keyPressed(KeyEvent e) {
        if (!gamePaused) {
            if (e.getKeyCode() == 38 && previous != direction.DOWN) {
                velocityX = 0;
                velocityY = -1;
            } else if (e.getKeyCode() == 40 && previous != direction.UP) {
                velocityX = 0;
                velocityY = 1;
            } else if (e.getKeyCode() == 37 && previous != direction.RIGHT) {
                velocityX = -1;
                velocityY = 0;
            } else if (e.getKeyCode() == 39 && previous != direction.LEFT) {
                velocityX = 1;
                velocityY = 0;
            }

            if (players2) {
                if (e.getKeyCode() == KeyEvent.VK_W && Eprevious != direction.DOWN) {
                    EvelocityX = 0;
                    EvelocityY = -1;
                } else if (e.getKeyCode() == KeyEvent.VK_S && Eprevious != direction.UP) {
                    EvelocityX = 0;
                    EvelocityY = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_A && Eprevious != direction.RIGHT) {
                    EvelocityX = -1;
                    EvelocityY = 0;
                } else if (e.getKeyCode() == KeyEvent.VK_D && Eprevious != direction.LEFT) {
                    EvelocityX = 1;
                    EvelocityY = 0;
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (!gamePaused) {
                pauseGame();
            } else {
                unpauseGame();
            }
        }


        // spawn bug test
        if (e.getKeyCode() == KeyEvent.VK_DELETE /*&& gameOver && !players2 || e.getKeyCode() == KeyEvent.VK_DELETE && gameOver && players2 && EgameOver*/){
            resetGame();
        }


    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}// Snake Game