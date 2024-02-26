import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    public static final int CELL_SIZE = 20;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;

    public static final int EMPTY = 0;
    public static final int SNAKE_BODY = 1;
    public static final int FOOD = 2;

    private List<int[]> snake; // List to store snake's body
    private int[] food; // Array to store food position
    private int[][] board; // 2D array representing the game board

    private boolean gameOver;
    private int score;

    private Random random;

    private int direction; // 0 - up, 1 - right, 2 - down, 3 - left

    public SnakeGame() {
        snake = new ArrayList<>();
        snake.add(new int[]{HEIGHT / 2, WIDTH / 2}); // Initial position of the snake
        board = new int[HEIGHT][WIDTH];
        food = new int[2];
        random = new Random();
        gameOver = false;
        score = 0;
        direction = 1; // Initially moving right
        placeFood();
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        Timer timer = new Timer(100, this);
        timer.start();
    }

    public void move() {
        int[] head = snake.get(0);
        int[] newHead = new int[]{head[0], head[1]};

        // Move the head in the current direction
        switch (direction) {
            case 0: // Up
                newHead[0]--;
                break;
            case 1: // Right
                newHead[1]++;
                break;
            case 2: // Down
                newHead[0]++;
                break;
            case 3: // Left
                newHead[1]--;
                break;
        }

        // Check for collisions
        if (newHead[0] < 0 || newHead[0] >= HEIGHT || newHead[1] < 0 || newHead[1] >= WIDTH) {
            gameOver = true; // Snake hits the wall
            return;
        }

        for (int[] part : snake) {
            if (part[0] == newHead[0] && part[1] == newHead[1]) {
                gameOver = true; // Snake collides with itself
                return;
            }
        }

        // Check if snake eats food
        if (newHead[0] == food[0] && newHead[1] == food[1]) {
            score++;
            placeFood(); // Place new food
        } else {
            // Remove the tail segment
            int[] tail = snake.remove(snake.size() - 1);
            board[tail[0]][tail[1]] = EMPTY;
        }

        // Move the snake's head
        snake.add(0, newHead);
        board[newHead[0]][newHead[1]] = SNAKE_BODY;
    }

    private void placeFood() {
        int x, y;
        do {
            x = random.nextInt(HEIGHT);
            y = random.nextInt(WIDTH);
        } while (board[x][y] != EMPTY);

        food[0] = x;
        food[1] = y;
        board[x][y] = FOOD;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (board[i][j] == EMPTY) {
                    g.setColor(Color.WHITE);
                } else if (board[i][j] == SNAKE_BODY) {
                    g.setColor(Color.GREEN);
                } else if (board[i][j] == FOOD) {
                    g.setColor(Color.RED);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver()) {
            move();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Keyboard input handling
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP:
                        game.setDirection(0);
                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        game.setDirection(1);
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN:
                        game.setDirection(2);
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        game.setDirection(3);
                        break;
                }
            }
        });
    }
}
