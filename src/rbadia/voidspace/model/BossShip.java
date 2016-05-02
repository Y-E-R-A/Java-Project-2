

package rbadia.voidspace.model;
import java.awt.Rectangle;
import java.util.Random;

import rbadia.voidspace.main.GameScreen;


/**
 * Represents a boss ship/space craft.
 *
 */
public class BossShip extends Rectangle  {

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_SPEED = 3;

	private int shipWidth = 200;
	private int shipHeight = 200;
	private int speed = DEFAULT_SPEED;

	/**
	 * Creates a new boss ship at the default initial location. 
	 * @param screen the game screen
	 */
	public BossShip(GameScreen screen){
		Random rand=new Random();
		this.setLocation(rand.nextInt(screen.getWidth()-shipWidth),
				-shipHeight);
		this.setSize(shipWidth, shipHeight);
	}

	/**
	 * Get the default boss ship width
	 * @return the default boss ship width
	 */
	public int getBossShipWidth() {
		return shipWidth;
	}

	/**
	 * Get the default boss ship height
	 * @return the default boss ship height
	 */
	public int getBossShipHeight() {
		return shipHeight;
	}

	/**
	 * Returns the current boss ship speed
	 * @return the current boss ship speed
	 */
	public int getBossShipSpeed() {
		return speed;
	}

	/**
	 * Set the current boss ship speed
	 * @param speed the boss speed to set
	 */
	public void setBossShipSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns the default boss ship speed.
	 * @return the default boss ship speed
	 */
	public int getBossShipDefaultSpeed(){
		return DEFAULT_SPEED;
	}

}


