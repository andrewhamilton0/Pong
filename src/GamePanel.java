import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    final static int SCREEN_HEIGHT = 650;
    final static int SCREEN_WIDTH = 1270;
    final static int UNIT_SIZE = 25;
    final static int DELAY = 10;
    final static double BALL_SPEED = 10;
    final static int PADDLE_LENGTH = 5;
    int ballAngle;
    int rightPaddleX;
    int rightPaddleY;
    int leftPaddleX;
    int leftPaddleY;
    int leftScore;
    int rightScore;
    int ballX = SCREEN_WIDTH / 2;
    int ballY = SCREEN_HEIGHT / 2;

    boolean leftPaddleOn;
    boolean rightPaddleOn;
    boolean running;
    char rightPaddleDirection;
    char leftPaddleDirection;
    String ballDirection;
    Random random = new Random();
    Timer timer;


    JPanel rightPaddle = new JPanel();
    JPanel leftPaddle = new JPanel();
    JPanel ball = new JPanel();

    GamePanel() {
        rightPaddleX = SCREEN_WIDTH - UNIT_SIZE;
        rightPaddleY = (SCREEN_HEIGHT / 2) - ((PADDLE_LENGTH * UNIT_SIZE) / 2);
        leftPaddleX = 0;
        leftPaddleY = (SCREEN_HEIGHT / 2) - ((PADDLE_LENGTH * UNIT_SIZE) / 2);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x000000));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        running = true;
        ballDirection = "E";
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void moveRightPaddle() {
        if ((rightPaddleDirection == 'D') && (rightPaddleY < (SCREEN_HEIGHT - (PADDLE_LENGTH * UNIT_SIZE)))) {
            rightPaddleY += UNIT_SIZE / 2;
        }
        if ((rightPaddleDirection == 'U') && (rightPaddleY > 3)) {
            rightPaddleY -= UNIT_SIZE / 2;
        }
    }

    public void moveLeftPaddle() {
        if ((leftPaddleDirection == 'D') && (leftPaddleY < (SCREEN_HEIGHT - (PADDLE_LENGTH * UNIT_SIZE)))) {
            leftPaddleY += UNIT_SIZE / 2;
        }
        if ((leftPaddleDirection == 'U') && (leftPaddleY > 3)) {
            leftPaddleY -= UNIT_SIZE / 2;
        }
    }

    public void gameOver() {

    }

    public void checkBounds() {

    }

    public void checkCollision(){
        //Checks Collision with left paddle
        if(((ballX <= leftPaddleX + UNIT_SIZE) && (ballX >= leftPaddleX)) && (((ballY + UNIT_SIZE) >= (leftPaddleY)) && (ballY <= (leftPaddleY + PADDLE_LENGTH * UNIT_SIZE)))){
            if (ballDirection == "W"){
                ballDirection = "SE";
            }
            if (ballDirection == "NW"){
                ballDirection = "NE";
            }
            if (ballDirection == "SW"){
                ballDirection = "SE";
            }
            ballAngle = (random.nextInt() % 5);
        }
        //Checks collision with right paddle
        if(((ballX + UNIT_SIZE >= rightPaddleX) && (ballX + UNIT_SIZE <= rightPaddleX + UNIT_SIZE)) && (((ballY + UNIT_SIZE) >= (rightPaddleY)) && (ballY <= (rightPaddleY + PADDLE_LENGTH * UNIT_SIZE)))){
            if (ballDirection == "E"){
                ballDirection = "SW";
            }
            if (ballDirection == "SE"){
                ballDirection = "SW";
            }
            if (ballDirection == "NE"){
                ballDirection = "NW";
            }
            ballAngle = (random.nextInt() % 5);
        }

        if(ballY <= 5){
            if (ballDirection == "NE"){
                ballDirection = "SE";
            }
            if (ballDirection == "NW"){
                ballDirection = "SW";
            }
        }
        if(ballY + UNIT_SIZE >= SCREEN_HEIGHT - 5){
            if (ballDirection == "SE"){
                ballDirection = "NE";
            }
            if (ballDirection == "SW"){
                ballDirection = "NW";
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void moveBall() {
        if(ballDirection == "W"){
            ballX -= BALL_SPEED;
        }
        if(ballDirection == "E"){
            ballX += BALL_SPEED;
        }
        if(ballDirection == "NE"){
            if((ballAngle == 2) || (ballAngle == 3) || (ballAngle == 4)){
                ballY -= BALL_SPEED;
                ballX += BALL_SPEED;
            }
            if(ballAngle == 0) {
                ballY -= BALL_SPEED +2;
                ballX += BALL_SPEED -2;
            }
            if(ballAngle == 1) {
                ballY -= BALL_SPEED -2;
                ballX += BALL_SPEED +2;
            }
        }
        if(ballDirection == "NW"){
            if((ballAngle == 2) || (ballAngle == 3) || (ballAngle == 4)){
                ballY -= BALL_SPEED;
                ballX -= BALL_SPEED;
            }
            if(ballAngle == 0) {
                ballY -= BALL_SPEED +2;
                ballX -= BALL_SPEED -2;
            }
            if(ballAngle == 1) {
                ballY -= BALL_SPEED -2;
                ballX -= BALL_SPEED +2;
            }
        }
        if(ballDirection == "SW") {
            if ((ballAngle == 2) || (ballAngle == 3) || (ballAngle == 4)) {
                ballY += BALL_SPEED;
                ballX -= BALL_SPEED;
            }
            if (ballAngle == 0) {
                ballY += BALL_SPEED +2;
                ballX -= BALL_SPEED -2;
            }
            if (ballAngle == 1) {
                ballY += BALL_SPEED -2;
                ballX -= BALL_SPEED +2;
            }
        }

        if(ballDirection == "SE") {
            if ((ballAngle == 2) || (ballAngle == 3) || (ballAngle == 4)){
                ballY += BALL_SPEED;
                ballX += BALL_SPEED;
            }
            if (ballAngle == 0) {
                ballY += BALL_SPEED +2;
                ballX += BALL_SPEED -2;
            }
            if (ballAngle == 1) {
                ballY += BALL_SPEED -2;
                ballX += BALL_SPEED +2;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, UNIT_SIZE, UNIT_SIZE);
        g.fillRect(leftPaddleX, leftPaddleY, UNIT_SIZE, ((PADDLE_LENGTH) * UNIT_SIZE));
        g.fillRect(rightPaddleX, rightPaddleY, UNIT_SIZE, ((PADDLE_LENGTH) * UNIT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            moveBall();
            checkCollision();
            checkBounds();
            if(leftPaddleOn){
                moveLeftPaddle();
            }
            if(rightPaddleOn){
                moveRightPaddle();
            }
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    leftPaddleDirection = 'D';
                    leftPaddleOn = true;
                    break;
                case KeyEvent.VK_W:
                    leftPaddleDirection = 'U';
                    leftPaddleOn = true;
                    break;
                case KeyEvent.VK_DOWN:
                    rightPaddleDirection = 'D';
                    rightPaddleOn = true;
                    break;
                case KeyEvent.VK_UP:
                    rightPaddleDirection = 'U';
                    rightPaddleOn = true;
                    break;
        }
        }
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                    leftPaddleOn = false;
                    break;
                case KeyEvent.VK_W:
                    leftPaddleOn = false;
                    break;
                case KeyEvent.VK_DOWN:
                    rightPaddleOn = false;
                    break;
                case KeyEvent.VK_UP:
                    rightPaddleOn = false;
                    break;
            }
        }
    }
}
