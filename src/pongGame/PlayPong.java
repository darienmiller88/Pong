/**
 * 
 */
package pongGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Darien Miller
 *
 */
@SuppressWarnings("serial")
class PlayPong extends JPanel implements ActionListener, KeyListener{

	private JFrame frame;
	private Ball ball;
	private Paddle leftPaddle, rightPaddle;
	private Timer t;
	private FrameRate FPS;
	private final int ballWidth = 15, paddleWidth = 20, paddleHeight = 80, paddleSpeed = 10;
	private static final int WIDTH = 700, HEIGHT = 500;
	private final int realWindowWidth = WIDTH - 30, realWindowHeight = HEIGHT - 50;
	
	public PlayPong() {
		t = new Timer(1, this);
		frame = new JFrame("Pong!");
		ball = new Ball(realWindowWidth, realWindowHeight, ballWidth);
		leftPaddle = new Paddle(paddleWidth, paddleHeight, paddleWidth, realWindowWidth, realWindowHeight);
		rightPaddle = new Paddle(realWindowWidth - paddleWidth, paddleHeight, paddleWidth, realWindowWidth, realWindowHeight);
		FPS = new FrameRate();
		
		t.start();
		this.setBackground(Color.black);
		frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PlayPong();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		ball.printBall(g);
		leftPaddle.printPaddle(g);
		rightPaddle.printPaddle(g);
		printDottedLine(g);
		printScore(g);
		FPS.printFPS(g);
		FPS.limitFPSTo60();
		
	}
	
	/**
	 * Prints the dotted line in the middle of the board
	 * 
	 * @param g
	 */
	public void printDottedLine(Graphics g) {
		g.setColor(Color.white);
		for(int i = 0; i < 600; i += 10)
			g.fillRect(WIDTH / 2, i, 3, 5);
	}
	
	public void printScore(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString(new Integer(leftPaddle.getScore()).toString(), (WIDTH / 2) - 55, 30);//print the left paddle score
		g.drawString(new Integer(rightPaddle.getScore()).toString(), (WIDTH / 2) + 30, 30);//print the right paddle score
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		ball.moveBall(leftPaddle, rightPaddle);
		ball.collideWithLeftPaddle(leftPaddle);
		ball.collideWithRightPaddle(rightPaddle);
		//ball.collideWithPaddle(leftPaddle, rightPaddle);
		
		leftPaddle.update();
		rightPaddle.update();
		FPS.calculateFPS();
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_K)
			rightPaddle.movePaddle(-paddleSpeed);
		else if(key.getKeyCode() == KeyEvent.VK_M)
			rightPaddle.movePaddle(paddleSpeed);
		else if(key.getKeyCode() == KeyEvent.VK_A)
			leftPaddle.movePaddle(-paddleSpeed);
		else if(key.getKeyCode() == KeyEvent.VK_Z)
			leftPaddle.movePaddle(paddleSpeed);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		leftPaddle.movePaddle(0);
		rightPaddle.movePaddle(0);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
