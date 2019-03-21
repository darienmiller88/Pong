/**
 * 
 */
package pongGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * @author Darien Miller
 *
 */
class Ball {
	private int x, y;
	private int ballWidth;
	private int xSpeed, ySpeed;
	private int windowWidth, windowHeight;
	private int angle, scaledSpeed;
	private Random rand;
	
	public Ball(int windowWidth, int windowHeight, int ballWidth) {
		rand = new Random();
		scaledSpeed = 7;
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.x = windowWidth / 2;
		this.y = windowHeight / 2;
		this.ballWidth = ballWidth;
		angle = provideSafeAngle();
		ySpeed = (int) (scaledSpeed * Math.sin(Math.toRadians(angle)));
		xSpeed = (int) (scaledSpeed * Math.cos(Math.toRadians(angle)));	
	}
	
	public void moveBall(Paddle leftPaddle, Paddle rightPaddle) {
		x += xSpeed;
		y += ySpeed;
		
		if(y >= windowHeight)
			ySpeed = -ySpeed;
		else if(y <= 0)
			ySpeed = Math.abs(ySpeed);
		else if(x <= 0) {
			rightPaddle.increaseScore();
			reset();
		}else if(x >= windowWidth) {
			leftPaddle.increaseScore();
			reset();
		}
			System.out.println("xSoeed: " + xSpeed + " ySpeed: " + ySpeed);
	}
	
	public void collideWithLeftPaddle(Paddle leftPaddle) {
		//if the ball hits the first segment or the last segment of the paddle, change its current angle to a 45 degree one.
		if(x <= leftPaddle.getX() + leftPaddle.getWidth() && (leftPaddle.ballHitsSegment(y, 1) || leftPaddle.ballHitsSegment(y, 8)))
			changeAngle(45);
		else if(x <= leftPaddle.getX() + leftPaddle.getWidth() && (leftPaddle.ballHitsSegment(y, 2) || leftPaddle.ballHitsSegment(y, 7)))
			changeAngle(30);
		else if(x <= leftPaddle.getX() + leftPaddle.getWidth() && (leftPaddle.ballHitsSegment(y, 3) || leftPaddle.ballHitsSegment(y, 6)))
			changeAngle(20);
		else if(x <= leftPaddle.getX() + leftPaddle.getWidth() && (leftPaddle.ballHitsSegment(y, 4) || leftPaddle.ballHitsSegment(y, 5)))
			changeAngle(60);
	}
	
	public void collideWithRightPaddle(Paddle rightPaddle) {
		//if the ball hits the first segment or the last segment of the paddle, change its current angle to a 45 degree one.
	
		if(x >= rightPaddle.getX() && (rightPaddle.ballHitsSegment(y, 1) || rightPaddle.ballHitsSegment(y, 8)))
			changeAngle(135);
		else if(x >= rightPaddle.getX() && (rightPaddle.ballHitsSegment(y, 2) || rightPaddle.ballHitsSegment(y, 7)))
			changeAngle(150);
		else if(x >= rightPaddle.getX() && (rightPaddle.ballHitsSegment(y, 3) || rightPaddle.ballHitsSegment(y, 6)))
			changeAngle(160);
		else if(x >= rightPaddle.getX() && (rightPaddle.ballHitsSegment(y, 4) || rightPaddle.ballHitsSegment(y, 5)))
			changeAngle(180);
	}
	
	public void collideWithPaddle(Paddle leftPaddle, Paddle rightPaddle) {
		if(x <= leftPaddle.getX() + leftPaddle.getWidth() && (y >= leftPaddle.getY() && y <= leftPaddle.getY() + leftPaddle.getHeight()))
			xSpeed = Math.abs(xSpeed);
		else if(x >= rightPaddle.getX() && (y >= rightPaddle.getY() && y <= rightPaddle.getY() + rightPaddle.getHeight()))
			xSpeed = -xSpeed;
	}
	
	private void changeAngle(int newAngle) {
		//if the ball is moving up (negative y speed), change its angle to negative so it continues going up. Otherwise, keep the angle positive
		if(ySpeed < 0)
			newAngle *= -1;
		
		ySpeed = (int) (scaledSpeed * Math.sin(Math.toRadians(newAngle)));
		xSpeed = (int) (scaledSpeed * Math.cos(Math.toRadians(newAngle)));	
	}
	
	private void reset() {
		x = windowWidth / 2;
		y = random((windowHeight / 2) - 20, (windowHeight/ 2) + 20);
		angle = provideSafeAngle();
		ySpeed = (int) (scaledSpeed * Math.sin(Math.toRadians(angle)));
		xSpeed = (int) (scaledSpeed * Math.cos(Math.toRadians(angle)));	
	}
	
	private int random(int min, int max) {
		return rand.nextInt((max - min) - 1) + min;
	}
	
	public void printBall(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, ballWidth, ballWidth);
	}
	
	/**
	 * In order to ensure that the ball is consistently given a non linear angle, this method is needed. When you take the sine and
	 * cosine of any given angle between 0 and 360, it may return 0 depending on the angle (ex: cos(90 degrees) = 0, sin(0 degrees) = 0)
	 * This is problematic because this return value will be assigned to the x and y velocities, which is used to move the ball. If
	 * the ball has a xSpeed of 2, but a ySpeed of 0, the ball will only move horizontally, and vice versa, only vertically.   
	 *
	 * @return the angle once its been given a proper angle. 
	 */
	private int provideSafeAngle() {
		int newAngle = rand.nextInt(361);//generate a random angle between 0 and 360
		
		//if the sin OR cos of the above angle times the scaled speed casted to int is 0, generate a new angle, and repeat until the result is no longer 0.
		while((int)(scaledSpeed * Math.sin(Math.toRadians(newAngle))) == 0 || (int)(scaledSpeed * Math.cos(Math.toRadians(newAngle))) == 0 )
			newAngle = rand.nextInt(361);
		return newAngle;
	}

	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	

}
