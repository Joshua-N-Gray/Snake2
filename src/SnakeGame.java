import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.Font.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    enum direction{
        LEFT,RIGHT,DOWN,UP,RESET
    }

    class Tile {
        int x;
        int y;
        direction Dir;

        Tile(int x, int y, direction Dir) {
            this.x = x;
            this.y = y;
            this.Dir = Dir;
        }
    }

    public static Font font1 = new Font("Arial", Font.PLAIN, 40);

    int boardWidth;
    int boardHeight;
    int collectedApples = 0;
    int collectedCherries = 0;
    int collectedBananas = 0;
    int EcollectedApples = 0;
    int EcollectedCherries = 0;
    int EcollectedBananas = 0;
    int spendingApples = 0;
    int spendingCherries = 0;
    int spendingBananas = 0;
    int EspendingApples = 0;
    int EspendingCherries = 0;
    int EspendingBananas = 0;

    //Game Settings
    int tileSize = 25;
    int gridX = 40;
    int gridY = 30;
    int appleCount = 10;
    public static int appleMax = 15;
    int cherryCount = 10;
    public static int cherryMax = 10;
    int bananaCount = 5;
    public static int bananaMax = 5;
    int obstacleCount = 90;
    int obstacleMax = appleMax*2 + cherryCount*3 + bananaMax*5 + 5;
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
    Tile[] cherry;
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

    final BufferedImage appleG;
    {
        try {
            appleG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/AppleV1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage cherryG;
    {
        try {
            cherryG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/CherryV1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage bananaG;
    {
        try {
            bananaG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/BananaV1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakeHeadG;
    {
        try {
            snakeHeadG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakeHead.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakeTailG;
    {
        try {
            snakeTailG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakeTail.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG0;
    {
        try {
            snakePartG0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart0.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG0t;
    {
        try {
            snakePartG0t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart0t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG1;
    {
        try {
            snakePartG1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG1t;
    {
        try {
            snakePartG1t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart1t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG2;
    {
        try {
            snakePartG2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart2.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG2t;
    {
        try {
            snakePartG2t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart2t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG3;
    {
        try {
            snakePartG3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage snakePartG3t;
    {
        try {
            snakePartG3t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/snakePart3t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakeHeadG;
    {
        try {
            EsnakeHeadG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakeHead.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakeTailG;
    {
        try {
            EsnakeTailG = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakeTail.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG0;
    {
        try {
            EsnakePartG0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart0.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG0t;
    {
        try {
            EsnakePartG0t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart0t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG1;
    {
        try {
            EsnakePartG1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart1.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG1t;
    {
        try {
            EsnakePartG1t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart1t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG2;
    {
        try {
            EsnakePartG2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart2.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG2t;
    {
        try {
            EsnakePartG2t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart2t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG3;
    {
        try {
            EsnakePartG3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart3.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final BufferedImage EsnakePartG3t;
    {
        try {
            EsnakePartG3t = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Sprites/EsnakePart3t.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    direction previous;
    direction Eprevious;

    int boardXoffset;
    int boardYoffset;

    JButton exitToMenu = new JButton("Main Menu");
    JButton restart = new JButton("Restart");
    JButton exit = new JButton("Quit");


    JPanel shopPanel = new JPanel();
    JButton sExitToMenu = new JButton("Main Menu");

    File file = new File("src/Stats.txt");
    Scanner statsReader;
    {
        try {
            statsReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    int highScore;
    int score;
    int Escore;
    int totalCollectedApples;
    int totalCollectedCherries;
    int totalCollectedBananas;

    SnakeGame(int deviceWidth, int deviceHeight) {

        statsReader.next();
        statsReader.next();
        highScore = statsReader.nextInt();
        statsReader.next();
        statsReader.next();
        totalCollectedApples = statsReader.nextInt();
        statsReader.next();
        statsReader.next();
        totalCollectedCherries = statsReader.nextInt();
        statsReader.next();
        statsReader.next();
        totalCollectedBananas = statsReader.nextInt();

        apple = new Tile[appleMax];
        cherry = new Tile[cherryMax];
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
        this.add(exit);
        exit.setVisible(false);
        exit.addActionListener(this);

        shopPanel.setPreferredSize(new Dimension(deviceWidth, deviceHeight));
        shopPanel.setVisible(false);
        this.add(shopPanel);
        shopPanel.setBackground(Color.DARK_GRAY);
        shopPanel.add(sExitToMenu);
        sExitToMenu.setVisible(false);
        sExitToMenu.addActionListener(this);
        

        random = new Random();
        snakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        snakeBody = new ArrayList();

        EsnakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        EsnakeBody = new ArrayList();

        System.out.println(snakeHead.x + ", " + snakeHead.y);
        System.out.println(EsnakeHead.x + ", " + EsnakeHead.y);


        for (int i = 0; i < appleMax; ++i) {
            apple[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        }

        for (int i = 0; i < cherryCount; ++i) {
            cherry[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        }

        for (int i = 0; i < bananaMax; ++i) {
            banana[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        }

        for (int i = 0; i < obstacleMax; ++i) {
            obstacle[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardHeight / tileSize), direction.UP);
        }



        for (int i = 0; i<appleCount; i++) {
            placeApple(i);
        }
        for (int i = 0; i<cherryCount; i++) {
            placeCherry(i);
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
        settingsUpdate();
    }

    private void settingsUpdate() {
        players2 = App.players2;
        appleCount = App.appleCount;
        cherryCount = App.cherryCount;
        bananaCount = App.bananaCount;
        obstacleCount = (appleCount * 2) + (cherryCount * 3) + (bananaCount * 5) + 5;
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
            g.drawImage(appleG,apple[i].x * tileSize + boardXoffset, apple[i].y * tileSize + boardYoffset, tileSize, tileSize, null);
        }

        for (int i = 0; i < cherryCount; ++i) {
            g.setColor(Color.red);
            g.drawImage(cherryG,cherry[i].x * tileSize + boardXoffset, cherry[i].y * tileSize + boardYoffset, tileSize, tileSize, null);
        }

        for (int i = 0; i < bananaCount; ++i) {
            g.setColor(Color.yellow);
            g.drawImage(bananaG,banana[i].x * tileSize + boardXoffset, banana[i].y * tileSize + boardYoffset, tileSize, tileSize, null);
        }

        for (int i = 0; i < obstacleCount; ++i) {
            g.setColor(Color.gray);
            g.fill3DRect(obstacle[i].x * tileSize + boardXoffset, obstacle[i].y * tileSize + boardYoffset, tileSize, tileSize, true);
        }

        //Draw Snake Head

        //Rotation
        Double rotationRequired = Math.toRadians (0);

        if (snakeHead.Dir == direction.UP) {
            rotationRequired = Math.toRadians (0);
        } else if (snakeHead.Dir == direction.DOWN) {
            rotationRequired = Math.toRadians (180);
        } else if (snakeHead.Dir == direction.LEFT) {
            rotationRequired = Math.toRadians (270);
        } else if (snakeHead.Dir == direction.RIGHT) {
            rotationRequired = Math.toRadians (90);
        }

        double locationX = (double) snakeHeadG.getWidth() / 2;
        double locationY = (double) snakeHeadG.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        g.drawImage(op.filter(snakeHeadG, null), snakeHead.x * tileSize + boardXoffset, snakeHead.y * tileSize + boardYoffset, tileSize, tileSize, null);

        //Draw Snake Parts
        for (int i = 0; i < snakeBody.size(); ++i) {
            Tile snakePart = snakeBody.get(i);

            //Find Part Orientation
            if (snakePart.Dir == direction.UP) {
                rotationRequired = Math.toRadians (0);
            } else if (snakePart.Dir == direction.DOWN) {
                rotationRequired = Math.toRadians (180);
            } else if (snakePart.Dir == direction.LEFT) {
                rotationRequired = Math.toRadians (270);
            } else if (snakePart.Dir == direction.RIGHT) {
                rotationRequired = Math.toRadians (90);
            }

            //Draw Tail
            if (i == snakeBody.size() - 1) {
                AffineTransform tx1 = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                AffineTransformOp op1 = new AffineTransformOp(tx1, AffineTransformOp.TYPE_BILINEAR);
                g.drawImage(op1.filter(snakeTailG, null), snakePart.x * tileSize + boardXoffset, snakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

            } else {
                Tile nextSnakePart = snakeBody.get(i + 1);

                BufferedImage partStyle = snakePartG0;
                BufferedImage partStyleT = snakePartG0t;
                int styleNum = i % 4;

                if (styleNum == 0) {
                    partStyle = snakePartG0;
                    partStyleT = snakePartG0t;
                } else if (styleNum == 1) {
                    partStyle = snakePartG1;
                    partStyleT = snakePartG1t;
                } else if (styleNum == 2) {
                    partStyle = snakePartG2;
                    partStyleT = snakePartG2t;
                } else if (styleNum == 3) {
                    partStyle = snakePartG3;
                    partStyleT = snakePartG3t;
                }

                if (snakePart.Dir != nextSnakePart.Dir) {
                    if (snakePart.Dir == direction.UP && nextSnakePart.Dir == direction.LEFT) {
                        rotationRequired = Math.toRadians (270);
                    } else if (snakePart.Dir == direction.UP && nextSnakePart.Dir == direction.RIGHT) {
                        rotationRequired = Math.toRadians (180);
                    } else if (snakePart.Dir == direction.DOWN && nextSnakePart.Dir == direction.LEFT) {
                        rotationRequired = Math.toRadians (0);
                    } else if (snakePart.Dir == direction.DOWN && nextSnakePart.Dir == direction.RIGHT) {
                        rotationRequired = Math.toRadians (90);
                    } else if (snakePart.Dir == direction.RIGHT && nextSnakePart.Dir == direction.UP) {
                        rotationRequired = Math.toRadians (0);
                    } else if (snakePart.Dir == direction.RIGHT && nextSnakePart.Dir == direction.DOWN) {
                        rotationRequired = Math.toRadians (270);
                    } else if (snakePart.Dir == direction.LEFT && nextSnakePart.Dir == direction.UP) {
                        rotationRequired = Math.toRadians (90);
                    } else if (snakePart.Dir == direction.LEFT && nextSnakePart.Dir == direction.DOWN) {
                        rotationRequired = Math.toRadians (180);
                    }

                    AffineTransform tx2 = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                    AffineTransformOp op2 = new AffineTransformOp(tx2, AffineTransformOp.TYPE_BILINEAR);
                    g.drawImage(op2.filter(partStyleT, null), snakePart.x * tileSize + boardXoffset, snakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

                } else {
                    AffineTransform tx3 = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
                    AffineTransformOp op3 = new AffineTransformOp(tx3, AffineTransformOp.TYPE_BILINEAR);
                    g.drawImage(op3.filter(partStyle, null), snakePart.x * tileSize + boardXoffset, snakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

                }
            }
        }

        if (players2) {
            //Draw ESnake Head

            //Rotation
            double ErotationRequired = Math.toRadians (0);

            if (EsnakeHead.Dir == direction.UP) {
                ErotationRequired = Math.toRadians (0);
            } else if (EsnakeHead.Dir == direction.DOWN) {
                ErotationRequired = Math.toRadians (180);
            } else if (EsnakeHead.Dir == direction.LEFT) {
                ErotationRequired = Math.toRadians (270);
            } else if (EsnakeHead.Dir == direction.RIGHT) {
                ErotationRequired = Math.toRadians (90);
            }

            double ElocationX = (double) EsnakeHeadG.getWidth() / 2;
            double ElocationY = (double) EsnakeHeadG.getHeight() / 2;
            AffineTransform tx4 = AffineTransform.getRotateInstance(ErotationRequired, ElocationX, ElocationY);
            AffineTransformOp op4 = new AffineTransformOp(tx4, AffineTransformOp.TYPE_BILINEAR);

            g.drawImage(op4.filter(EsnakeHeadG, null), EsnakeHead.x * tileSize + boardXoffset, EsnakeHead.y * tileSize + boardYoffset, tileSize, tileSize, null);

            //Draw ESnake Parts
            for (int i = 0; i < EsnakeBody.size(); ++i) {
                Tile EsnakePart = EsnakeBody.get(i);

                //Find Part Orientation
                if (EsnakePart.Dir == direction.UP) {
                    ErotationRequired = Math.toRadians (0);
                } else if (EsnakePart.Dir == direction.DOWN) {
                    ErotationRequired = Math.toRadians (180);
                } else if (EsnakePart.Dir == direction.LEFT) {
                    ErotationRequired = Math.toRadians (270);
                } else if (EsnakePart.Dir == direction.RIGHT) {
                    ErotationRequired = Math.toRadians (90);
                }

                //Draw Tail
                if (i == EsnakeBody.size() - 1) {
                    AffineTransform tx5 = AffineTransform.getRotateInstance(ErotationRequired, ElocationX, ElocationY);
                    AffineTransformOp op5 = new AffineTransformOp(tx5, AffineTransformOp.TYPE_BILINEAR);
                    g.drawImage(op5.filter(EsnakeTailG, null), EsnakePart.x * tileSize + boardXoffset, EsnakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

                } else {
                    Tile EnextSnakePart = EsnakeBody.get(i + 1);

                    BufferedImage EpartStyle = EsnakePartG0;
                    BufferedImage EpartStyleT = EsnakePartG0t;
                    int styleNum = i % 4;

                    if (styleNum == 0) {
                        EpartStyle = EsnakePartG0;
                        EpartStyleT = EsnakePartG0t;
                    } else if (styleNum == 1) {
                        EpartStyle = EsnakePartG1;
                        EpartStyleT = EsnakePartG1t;
                    } else if (styleNum == 2) {
                        EpartStyle = EsnakePartG2;
                        EpartStyleT = EsnakePartG2t;
                    } else if (styleNum == 3) {
                        EpartStyle = EsnakePartG3;
                        EpartStyleT = EsnakePartG3t;
                    }

                    if (EsnakePart.Dir != EnextSnakePart.Dir) {
                        if (EsnakePart.Dir == direction.UP && EnextSnakePart.Dir == direction.LEFT) {
                            ErotationRequired = Math.toRadians (270);
                        } else if (EsnakePart.Dir == direction.UP && EnextSnakePart.Dir == direction.RIGHT) {
                            ErotationRequired = Math.toRadians (180);
                        } else if (EsnakePart.Dir == direction.DOWN && EnextSnakePart.Dir == direction.LEFT) {
                            ErotationRequired = Math.toRadians (0);
                        } else if (EsnakePart.Dir == direction.DOWN && EnextSnakePart.Dir == direction.RIGHT) {
                            ErotationRequired = Math.toRadians (90);
                        } else if (EsnakePart.Dir == direction.RIGHT && EnextSnakePart.Dir == direction.UP) {
                            ErotationRequired = Math.toRadians (0);
                        } else if (EsnakePart.Dir == direction.RIGHT && EnextSnakePart.Dir == direction.DOWN) {
                            ErotationRequired = Math.toRadians (270);
                        } else if (EsnakePart.Dir == direction.LEFT && EnextSnakePart.Dir == direction.UP) {
                            ErotationRequired = Math.toRadians (90);
                        } else if (EsnakePart.Dir == direction.LEFT && EnextSnakePart.Dir == direction.DOWN) {
                            ErotationRequired = Math.toRadians (180);
                        }

                        AffineTransform tx6 = AffineTransform.getRotateInstance(ErotationRequired, ElocationX, ElocationY);
                        AffineTransformOp op6 = new AffineTransformOp(tx6, AffineTransformOp.TYPE_BILINEAR);
                        g.drawImage(op6.filter(EpartStyleT, null), EsnakePart.x * tileSize + boardXoffset, EsnakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

                    } else {
                        AffineTransform tx7 = AffineTransform.getRotateInstance(ErotationRequired, ElocationX, ElocationY);
                        AffineTransformOp op7 = new AffineTransformOp(tx7, AffineTransformOp.TYPE_BILINEAR);
                        g.drawImage(op7.filter(EpartStyle, null), EsnakePart.x * tileSize + boardXoffset, EsnakePart.y * tileSize + boardYoffset, tileSize, tileSize, null);

                    }
                }
            }
        }

        g.setFont(new Font("TimesNewRoman", 1, 16));
        if (gameOver) {
            g.setColor(Color.green);
            g.drawString("Game Over: " + (collectedApples + (collectedCherries * 2) + (collectedBananas * 3)), boardXoffset + 10, boardYoffset - 10);
        } else {
            g.setColor(Color.green);
            g.drawString("Score: " + (collectedApples + (collectedCherries * 2) + (collectedBananas * 3)), boardXoffset + 10, boardYoffset - 10);
        }

        if (players2) {
            if (EgameOver) {
                g.setColor(Color.blue);
                FontMetrics fm = g.getFontMetrics();
                int Ilength = fm.stringWidth("Game Over: " + String.valueOf(EsnakeBody.size()));
                g.drawString("Game Over: " + (EcollectedApples+ (EcollectedCherries * 2)  + (EcollectedBananas * 3)), boardXoffset  + boardWidth - Ilength - 10, boardYoffset - 10);
            } else {
                g.setColor(Color.blue);
                FontMetrics fm = g.getFontMetrics();
                int Ilength = fm.stringWidth("Score: " + (EcollectedApples + (EcollectedBananas * 3)));
                g.drawString("Score: " + (EcollectedApples+ (EcollectedCherries * 2)  + (EcollectedBananas * 3)), boardXoffset + boardWidth - Ilength - 10, boardYoffset - 10);
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

    public void placeCherry(int i) {
            cherry[i].x = random.nextInt(boardWidth / tileSize);
            cherry[i].y = random.nextInt(boardHeight / tileSize);
            //Continue to replace same cherry until valid location found
            spawnCheckcherry(i);
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
                    snakeBody.add(new Tile(apple[i].x, apple[i].y, direction.UP));
                    collectedApples++;
                    spendingApples++;
                    placeApple(i);
                }
            }

            for (int i = 0; i < cherryCount; ++i) {
                if (collision(snakeHead, cherry[i])) {
                    for (int j = 0; j < 2; j++) {
                        snakeBody.add(new Tile(cherry[i].x, cherry[i].y, direction.UP));
                    }
                    collectedCherries++;
                    spendingCherries++;
                    placeCherry(i);
                }
            }

            for (int i = 0; i < bananaCount; ++i) {
                if (collision(snakeHead, banana[i])) {
                    for (int j = 0; j < 3; j++) {
                        snakeBody.add(new Tile(banana[i].x, banana[i].y, direction.UP));
                    }
                    collectedBananas++;
                    spendingBananas++;
                    placeBanana(i);
                }
            }

            //Snake Move

            for (int i = snakeBody.size() - 1; i >= 0; --i) {
                snakePart = snakeBody.get(i);
                if (i == 0) {
                    snakePart.x = snakeHead.x;
                    snakePart.y = snakeHead.y;
                    snakePart.Dir = snakeHead.Dir;
                } else {
                    Tile prevSnakePart = snakeBody.get(i - 1);
                    snakePart.x = prevSnakePart.x;
                    snakePart.y = prevSnakePart.y;
                    snakePart.Dir = prevSnakePart.Dir;
                }
            }

            if ( velocityX != 0 || velocityY != 0) {
                previous = snakeHead.Dir;
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
                    EsnakeBody.add(new Tile(apple[i].x, apple[i].y, direction.UP));
                    placeApple(i);
                    EcollectedApples++;
                    EspendingApples++;
                }
            }

            for (int i = 0; i < cherryCount; ++i) {
                if (collision(EsnakeHead, cherry[i])) {
                    for (int j = 0; j < 2; j++) {
                        EsnakeBody.add(new Tile(cherry[i].x, cherry[i].y, direction.UP));
                    }
                    placeCherry(i);
                    EcollectedCherries++;
                    EspendingCherries++;
                }
            }

            for (int i = 0; i < bananaCount; ++i) {
                if (collision(EsnakeHead, banana[i])) {
                    for (int j = 0; j < 3; j++) {
                        EsnakeBody.add(new Tile(banana[i].x, banana[i].y, direction.UP));
                    }
                    placeBanana(i);
                    EcollectedBananas++;
                    EspendingBananas++;
                }
            }

            //Esnake Move

            for (int i = EsnakeBody.size() - 1; i >= 0; --i) {
                EsnakePart = EsnakeBody.get(i);
                if (i == 0) {
                    EsnakePart.x = EsnakeHead.x;
                    EsnakePart.y = EsnakeHead.y;
                    EsnakePart.Dir = EsnakeHead.Dir;
                } else {
                    Tile EprevSnakePart = EsnakeBody.get(i - 1);
                    EsnakePart.x = EprevSnakePart.x;
                    EsnakePart.y = EprevSnakePart.y;
                    EsnakePart.Dir = EprevSnakePart.Dir;
                }
            }

            if ( EvelocityX != 0 || EvelocityY != 0) {
                Eprevious = EsnakeHead.Dir;
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

    public void spawnCheckcherry(int i) {
        //replace cherry if spawned on top of snake
        if (collision(snakeHead, cherry[i])) {
            placeCherry(i);
        }

        if (players2) {
            if (collision(EsnakeHead, cherry[i])) {
                placeCherry(i);
            }
        }

        //replace cherry if spawned on an obstacle
        for(int h = 0; h < obstacleCount; ++h) {
            if (collision(cherry[i], obstacle[h])) {
                placeCherry(i);
            }
        }

        //replace cherry if cherry is overlapping
        for(int h = 0; h < cherryCount; ++h) {
            if (collision(cherry[i], cherry[h])) {
                if (i != h) {
                    placeCherry(i);
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
        if (!gameOver) {
            shopPanel.setVisible(true);
            sExitToMenu.setVisible(true);
            gamePaused = true;
        }
    }

    public void unpauseGame() {
        if (!gameOver) {
            shopPanel.setVisible(false);
            sExitToMenu.setVisible(false);
            gamePaused = false;
        }
    }

    private void resetGame() {
        FileWriter statsWriter;
        {
            try {
                statsWriter = new FileWriter(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        score = collectedApples + (collectedCherries * 2) + (collectedBananas * 3);
        if (score > highScore){
            highScore = score;
        }
        if (players2){
            Escore = EcollectedApples + (EcollectedCherries * 2) + (EcollectedBananas * 3);
            if (Escore > highScore){
                highScore = Escore;
            }
        }

        totalCollectedApples = collectedApples + EcollectedApples + totalCollectedApples;
        totalCollectedCherries = collectedCherries + EcollectedCherries + totalCollectedCherries;
        totalCollectedBananas = collectedBananas + EcollectedBananas + totalCollectedBananas;

        try {
            statsWriter.write("High Score: " + highScore +
                    "\nCollected Apples: " + totalCollectedApples +
                    "\nCollected Cherries: " + totalCollectedCherries +
                    "\nCollected Bananas: " + totalCollectedBananas);
            statsWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        exitToMenu.setVisible(false);
        restart.setVisible(false);
        exit.setVisible(false);

        gameOver = false;
        collectedApples = 0;
        collectedCherries = 0;
        collectedBananas = 0;
        spendingApples = 0;
        spendingCherries = 0;
        spendingBananas = 0;
        if (players2){
            EgameOver = false;
            EcollectedApples = 0;
            EcollectedCherries = 0;
            EcollectedBananas = 0;
            EspendingApples = 0;
            EspendingCherries = 0;
            EspendingBananas = 0;
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
        for (int i = 0; i<cherryCount; i++) {
            placeCherry(i);
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
                exitToMenu.setVisible(true);
                restart.setVisible(true);
                exit.setVisible(true);
                repaint();
            }
        } else {
            if (gameOver) {
                gameLoop.stop();
                exitToMenu.setVisible(true);
                restart.setVisible(true);
                exit.setVisible(true);
                repaint();
            }
        }

        if (e.getSource() == restart){
            resetGame();
        }

        if (e.getSource() == exitToMenu){
            App.menuStatus = true;
            App.snakeRunning = false;
            resetGame();
        }

        if (e.getSource() == exit){
            resetGame();
            System.exit(1);
        }

        if (e.getSource() == sExitToMenu){
            App.menuStatus = true;
            App.snakeRunning = false;
            resetGame();
            unpauseGame();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (!gamePaused) {
            if (e.getKeyCode() == 38 && previous != direction.DOWN) {
                velocityX = 0;
                velocityY = -1;
                snakeHead.Dir = direction.UP;
            } else if (e.getKeyCode() == 40 && previous != direction.UP) {
                velocityX = 0;
                velocityY = 1;
                snakeHead.Dir = direction.DOWN;
            } else if (e.getKeyCode() == 37 && previous != direction.RIGHT) {
                velocityX = -1;
                velocityY = 0;
                snakeHead.Dir = direction.LEFT;
            } else if (e.getKeyCode() == 39 && previous != direction.LEFT) {
                velocityX = 1;
                velocityY = 0;
                snakeHead.Dir = direction.RIGHT;
            }

            if (players2) {
                if (e.getKeyCode() == KeyEvent.VK_W && Eprevious != direction.DOWN) {
                    EvelocityX = 0;
                    EvelocityY = -1;
                    EsnakeHead.Dir = direction.UP;
                } else if (e.getKeyCode() == KeyEvent.VK_S && Eprevious != direction.UP) {
                    EvelocityX = 0;
                    EvelocityY = 1;
                    EsnakeHead.Dir = direction.DOWN;
                } else if (e.getKeyCode() == KeyEvent.VK_A && Eprevious != direction.RIGHT) {
                    EvelocityX = -1;
                    EvelocityY = 0;
                    EsnakeHead.Dir = direction.LEFT;
                } else if (e.getKeyCode() == KeyEvent.VK_D && Eprevious != direction.LEFT) {
                    EvelocityX = 1;
                    EvelocityY = 0;
                    EsnakeHead.Dir = direction.RIGHT;
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
        if (e.getKeyCode() == KeyEvent.VK_DELETE && gameOver && !players2 || e.getKeyCode() == KeyEvent.VK_DELETE && gameOver && players2 && EgameOver){
            resetGame();
        }

    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}// Snake Game