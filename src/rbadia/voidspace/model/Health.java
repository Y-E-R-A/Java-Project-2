package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

public class Health extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 4;
	
	private int healthWidth = 32;
	private int healthHeigth = 32;
	private int speed = DEFAULT_SPEED;

	private Random rand = new Random();
	
	/**
	 * Crates a new health at a random x location at the top of the screen 
	 * @param screen the game screen
	 */
	public Health(GameScreen screen){
		this.setLocation(
        		rand.nextInt(screen.getWidth() - healthWidth),
        		0);
		this.setSize(healthWidth, healthHeigth);
	}
	
	public int getHealthWidth() {
		return healthWidth;
	}
	public int getHealthHeight() {
		return healthHeigth;
	}

	/**
	 * Returns the current health speed
	 * @return the current health speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * Set the current health speed
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default health speed.
	 * @return the default health speed
	 */
	public int getDefaultSpeed(){
		return DEFAULT_SPEED;
	}
}