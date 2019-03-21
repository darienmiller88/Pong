package pongGame;

import java.awt.Font;
import java.awt.Graphics;

public class FrameRate {
	private String frameRate;
	private long lastTime, startTicksFor60FPS;
    private long delta, maxFPS;
    private int frameCount;
    
    public FrameRate (){
    	startTicksFor60FPS = System.currentTimeMillis();
	    lastTime = System.currentTimeMillis();
	    frameRate = "FPS 0";
        maxFPS = 60;
    }
    
    public void calculateFPS(){
    	startTicksFor60FPS = System.currentTimeMillis(); 
        long current = System.currentTimeMillis();
        delta += current - lastTime;
        lastTime = current;
        frameCount++;

        if(delta > 1000){
            delta -= 1000;
            frameRate = String.format("FPS: %s", frameCount);
            frameCount = 0;	
        }
    }
    
    public void printFPS(Graphics g) {
    	g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString(frameRate, 500, 50);
    }
    
    public void limitFPSTo60()  {
    	long frameTicks = System.currentTimeMillis() - startTicksFor60FPS;
    	if(1000.0 / maxFPS > frameTicks) {
    		//System.out.println("MAX FPS");
			try {
				Thread.sleep((1000 / maxFPS) - frameTicks);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//System.out.println( 1000 / maxFPS +  " < " + frameTicks);
    }
	
    public String getFrameRate(){
        return frameRate;
    }

}
