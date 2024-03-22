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

    //Game Settings
    int tileSize = 100;
    int foodCount = 35;
    int obstacleCount = 0;
    int gameSpeedms = 100;
    boolean players2 = false;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile EsnakeHead;
    ArrayList<Tile> EsnakeBody;



    Tile[] food;
    Tile[] obstacle;

    Random random;
    Timer gameLoop;

    int velocityX;
    int velocityY;

    int EvelocityX;
    int EvelocityY;

    boolean gameOver;
    boolean EgameOver;

    enum direction{
        LEFT,RIGHT,DOWN,UP
    }

    direction previous;
    direction Eprevious;

    SnakeGame(int boardWitdth, int boardHeight) {

        food = new Tile[foodCount];
        obstacle = new Tile[obstacleCount];

        velocityX = 0;
        velocityY = 0;

        gameOver = false;
        EgameOver = false;

        this.boardWidth = boardWitdth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        random = new Random();
        snakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardWidth / tileSize));
        snakeBody = new ArrayList();

        if (players2) {
            EsnakeHead = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardWidth / tileSize));
            EsnakeBody = new ArrayList();
        }

        for (int i = 0; i < foodCount; ++i) {
            food[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardWidth / tileSize));
        }

        for (int i = 0; i < obstacleCount; ++i) {
            obstacle[i] = new Tile(random.nextInt(boardWidth / tileSize), random.nextInt(boardWidth / tileSize));
        }



        for (int i = 0; i<foodCount; i++) {
            placeFoods(i);
        }
        for (int i = 0; i<obstacleCount; i++) {
            placeObstacles(i);
        }

        spawnCheck();

        gameLoop = new Timer(gameSpeedms, this);
        gameLoop.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < foodCount; ++i) {
            g.setColor(Color.red);
            g.fill3DRect(food[i].x * tileSize, food[i].y * tileSize, tileSize, tileSize, true);
        }

        for (int i = 0; i < obstacleCount; ++i) {
            g.setColor(Color.gray);
            g.fill3DRect(obstacle[i].x * tileSize, obstacle[i].y * tileSize, tileSize, tileSize, true);
        }

        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        for (int i = 0; i < snakeBody.size(); ++i) {
            Tile snakePart =  snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        if (players2) {
            g.setColor(Color.blue);
            g.fill3DRect(EsnakeHead.x * tileSize, EsnakeHead.y * tileSize, tileSize, tileSize, true);

            for (int i = 0; i < EsnakeBody.size(); ++i) {
                Tile EsnakePart = EsnakeBody.get(i);
                g.fill3DRect(EsnakePart.x * tileSize, EsnakePart.y * tileSize, tileSize, tileSize, true);
            }
        }

        g.setFont(new Font("TimesNewRoman", 0, 16));
        if (gameOver) {
            g.setColor(Color.green);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.setColor(Color.green);
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

        if (players2) {
            if (EgameOver) {
                g.setColor(Color.blue);
                g.drawString("Game Over: " + String.valueOf(EsnakeBody.size()), boardWidth - 116, tileSize);
            } else {
                g.setColor(Color.blue);
                g.drawString("Score: " + String.valueOf(EsnakeBody.size()), boardWidth - 84, tileSize);
            }
        }

    }

    public void placeFoods(int i) {
            food[i].x = random.nextInt(boardWidth / tileSize);
            food[i].y = random.nextInt(boardHeight / tileSize);
    }

    public void placeObstacles(int i) {
            obstacle[i].x = random.nextInt(boardWidth / tileSize);
            obstacle[i].y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void spawnCheck() {
        for(int i = 0; i < foodCount; ++i) {
            if (collision(snakeHead, food[i])) {
                placeFoods(i);
            }
        }
        for(int i = 0; i < obstacleCount; ++i) {
            if (collision(snakeHead, obstacle[i])) {
                placeObstacles(i);
            }
        }
    }

    public void move() {

        Tile snakePart;
        Tile EsnakePart;

        if (!gameOver) {

            //Snake Eat

            for (int i = 0; i < foodCount; ++i) {
                if (collision(snakeHead, food[i])) {
                    snakeBody.add(new Tile(food[i].x, food[i].y));
                    placeFoods(i);
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

            for (int i = 0; i < foodCount; ++i) {
                if (collision(EsnakeHead, food[i])) {
                    EsnakeBody.add(new Tile(food[i].x, food[i].y));
                    placeFoods(i);
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

    public void testFood() {
        for(int i = 0; i < foodCount; ++i) {
            for(int h = 0; h < obstacleCount; ++h) {
                if (collision(food[i], obstacle[h])) {
                    placeFoods(i);
                }
            }
        }
        for(int i = 0; i < foodCount; ++i) {
            for(int h = 0; h < foodCount; ++h) {
                if (collision(food[i], food[h])) {
                    if (!(collision(food[i], food[i]))) {
                        placeFoods(i);
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

        move();
        testFood();

        repaint();

        if (players2) {
            if (gameOver && EgameOver) {
                gameLoop.stop();
            }
        } else {
            if (gameOver) {
                gameLoop.stop();
            }
        }

    }

    public void keyPressed(KeyEvent e) {
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

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}// Snake Game