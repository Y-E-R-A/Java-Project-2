package rbadia.voidspace.model;

import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;

/**
 * Represents a enemy ship/space craft.
 *
 */
public class EnemyShip extends Rectangle {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_SPEED = 3;
	
	private int shipWidth = 30;
	private int shipHeight = 26;
	private int speed = DEFAULT_SPEED;
	
	/**
	 * Creates a new enemy ship at the default initial location. 
	 * @param screen the game screen
	 */
	public EnemyShip(GameScreen screen){
		Random rand=new Random();
		this.setLocation(rand.nextInt(screen.getWidth()-shipWidth),
				 -shipHeight);
		this.setSize(shipWidth, shipHeight);
	}
	
	/**
	 * Get the default enemy ship width
	 * @return the default enemy ship width
	 */
	public int getEnemyShipWidth() {
		return shipWidth;
	}
	
	/**
	 * Get the default enemy ship height
	 * @return the default enemy ship height
	 */
	public int getEnemyShipHeight() {
		return shipHeight;
	}
	
	/**
	 * Returns the current enemy ship speed
	 * @return the current enemy ship speed
	 */
	public int getEnemyShipSpeed() {
		return speed;
	}
	
	/**
	 * Set the current enemy ship speed
	 * @param speed the enemy speed to set
	 */
	public void setEnemyShipSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Returns the default enemy ship speed.
	 * @return the default enemy ship speed
	 */
	public int getEnemyShipDefaultSpeed(){
		return DEFAULT_SPEED;
	}
	
}