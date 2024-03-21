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
    int tileSize = 25;

    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    Tile snakePart;

    Tile EsnakeHead;
    ArrayList<Tile> EsnakeBody;

    Tile EsnakePart;

    int foodCount = 4;
    Tile[] food;

    int obstacleCount;
    Tile[] obstacle;

    Random random;
    Timer gameLoop;

    int velocityX;
    int velocityY;

    int EvelocityX;
    int EvelocityY;

    boolean gameOver;
    boolean EgameOver;

    boolean players2 = false;

    enum direction{
        LEFT,RIGHT,DOWN,UP
    }

    direction previous;
    direction Eprevious;

    SnakeGame(int boardWitdth, int boardHeight) {

        food = new Tile[foodCount];

        obstacleCount = 0;
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
        placeObstacles();
        gameLoop = new Timer(200, this);
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

    public void placeObstacles() {
        for (int i = 0; i < obstacleCount; ++i) {
            obstacle[i].x = random.nextInt(boardWidth / tileSize);
            obstacle[i].y = random.nextInt(boardHeight / tileSize);
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {

        for(int i = 0; i < foodCount; ++i) {
            if (collision(snakeHead, food[i])) {
                snakeBody.add(new Tile(food[i].x, food[i].y));
                placeFoods(i);
            }
        }

        for(int i = snakeBody.size() - 1; i >= 0; --i) {
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

        for(int i = 0; i < snakeBody.size(); ++i) {
            snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        for(int i = 0; i < snakeBody.size(); ++i) {
            EsnakePart = snakeBody.get(i);
            if (collision(snakeHead, EsnakePart)) {
                gameOver = true;
            }
        }

        for(int i = 0; i < obstacleCount; ++i) {
            if (collision(snakeHead, obstacle[i])) {
                gameOver = true;
            }
        }

        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize >= boardHeight) {
            gameOver = true;
        }



    }

    public void Emove() {
            for (int i = 0; i < foodCount; ++i) {
                if (collision(EsnakeHead, food[i])) {
                    EsnakeBody.add(new Tile(food[i].x, food[i].y));
                    placeFoods(i);
                }
            }


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

            for (int i = 0; i < EsnakeBody.size(); ++i) {
                EsnakePart = EsnakeBody.get(i);
                if (collision(EsnakeHead, EsnakePart)) {
                    EgameOver = true;
                }
            }

            for (int i = 0; i < EsnakeBody.size(); ++i) {
                snakePart = EsnakeBody.get(i);
                if (collision(EsnakeHead, snakePart)) {
                    EgameOver = true;
                }
            }

            for (int i = 0; i < obstacleCount; ++i) {
                if (collision(EsnakeHead, obstacle[i])) {
                    gameOver = true;
                }
            }

            if (EsnakeHead.x * tileSize < 0 || EsnakeHead.x * tileSize >= boardWidth || EsnakeHead.y * tileSize < 0 || EsnakeHead.y * tileSize >= boardHeight) {
                EgameOver = true;
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
    }

    public void actionPerformed(ActionEvent e) {

        if (!gameOver) {
            move();
        }

        if (players2) {
            if (!EgameOver) {
                Emove();
            }
        }

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