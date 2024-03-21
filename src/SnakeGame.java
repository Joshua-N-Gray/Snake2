import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private class Tile{

        int x;

        int y;
        Tile (int x, int y){
            this.x = x;
            this.y = y;
        }// Tile Constructor

    }// Tile

    // Init's
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Food
    Tile food;
    Random random;

    // Game logic
    Timer gameLoop;
    int velocityX = 0;
    int velocityY = 0;
    boolean gameOver = false;

    SnakeGame( int boardWitdth, int boardHeight){

        this.boardWidth = boardWitdth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();


        gameLoop = new Timer(100, this);
        gameLoop.start();

    }// SnakeGame

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }// PaintComponent

    public void draw(Graphics g){

/*        //Grid
        for ( int i = 0; i < boardWitdth/tileSize; i++){
            g.drawLine(i * tileSize, 0 , i * tileSize, boardHeight);
            g.drawLine(0, i * tileSize, boardWitdth, i * tileSize);
        }// Draw Grid*/

        // Food
        g.setColor(Color.red);
       // g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);


        // Snake Head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // Snake Body
        for ( int i = 0; i < snakeBody.size(); i++ ) {
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }// Creates a new segment for the snakes body

        // Score
        g.setFont(new Font("TimesNewRoman", Font.PLAIN, 16 ));
        if ( gameOver ){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }// Draw

    public void placeFood() {
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
    }// placeFood

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move(){
        // eat food
        if ( collision(snakeHead, food) ){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }// Reads the eating of food and calls the snake body method and method for a new piece of food

        // Snake Body
        for ( int i = snakeBody.size() - 1; i >= 0; i-- ){
            Tile snakePart = snakeBody.get(i);
            if ( i == 0 ){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }

        }// Moves the snakes body

        // Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // Game Over Conditions
        for ( int i = 0; i < snakeBody.size(); i++ ){
            Tile snakePart = snakeBody.get(i);
            // collide with the snake head
            if ( collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }// Self Collision

        if ( snakeHead.x * tileSize < 0 || snakeHead.x * tileSize >= boardWidth ||
             snakeHead.y * tileSize < 0 || snakeHead.y * tileSize >= boardHeight ){
            gameOver = true;
        }// Move out of bounds
    }// Move

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver){
            gameLoop.stop();
        }
    }// Game Loop

    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }// Arrow Up
        else if ( e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }// Arrow Down
        else if ( e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }// Arrow left
        else if ( e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }// Arrow Right
    }// Key Listener


    @Override// Not needed
    public void keyTyped(KeyEvent e) {}// Not needed
    @Override// Not needed
    public void keyReleased(KeyEvent e) {}// Not needed

}// Snake Game