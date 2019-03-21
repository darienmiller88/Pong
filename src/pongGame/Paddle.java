package pongGame;

import java.awt.Color;
import java.awt.Graphics;

class Paddle{
	private int score;
	private int height, width;
	private int x, y;
	private int paddleSpeed;
	private int windowWidth, windowHeight;
	private int paddleSegmentHeight;//we will visualize the paddle in 8 segments, so the ball will given a different angle to 
	//bounce off of when it hits a different part of the paddle. This variable represents the height of each pixel.
	
	public Paddle(int x, int height, int width, int windowWidth, int windowHeight) {
		this.height = height;
		this.width = width;
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.x = x;
		y = (windowHeight / 2) - (height / 2);
		paddleSegmentHeight = height / 8;
	}
	

	public void printPaddle(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getScore() {
		return score;
	}
	
	public void increaseScore() {
		score++;		
	}
	
	public void update() {
		y += paddleSpeed;
		
		if(y <= 0)
			y = 0;
		else if(y > windowHeight - height) 
			y = windowHeight - height;
	}
	
	public void movePaddle(int changeInY) {
		paddleSpeed = changeInY;
	}
	
	public boolean ballHitsSegment(int ballY, int whichSegment) {
		return ballY > getSegment(whichSegment - 1) && ballY <= getSegment(whichSegment);
	} 
	
	public int getSegment(int whichSegment) {
		return y + (whichSegment * paddleSegmentHeight - 1);
	}
	
}
