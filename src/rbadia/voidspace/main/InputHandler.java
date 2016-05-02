package rbadia.voidspace.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Handles user input events.
 */
public class InputHandler implements KeyListener{
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed;
	static boolean shiftIsPressed;
	private boolean beginTurboSound=false;
	private boolean turboSound=false;
	private long beginThusterTime;
	private SoundManager soundMan;

	private long lastBulletTime;

	private GameLogic gameLogic;

	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(GameLogic gameLogic){
		this.gameLogic = gameLogic;
		soundMan = new SoundManager();
	}

	/**
	 * Handle user input after screen update.
	 * @param gameScreen he game screen
	 */
	public void handleInput(GameScreen gameScreen){
		GameStatus status = gameLogic.getStatus();
		if(!status.isGameOver() && !status.isNewShip() && !status.isGameStarting() && status.isGameStarted()){
			// fire bullet if space is pressed
			if(spaceIsPressed){
				// fire only up to 5 bullets per second
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/4.5){
					lastBulletTime = currentTime;
					gameLogic.fireBullet();
				}
			}

			Ship ship = gameLogic.getShip();

			if(shiftIsPressed){
				ship.setSpeed(ship.getDefaultSpeed() * 2);
				if(!beginTurboSound)
				{
					soundMan.playRocketTurboSound();
					beginThusterTime=System.currentTimeMillis();
					beginTurboSound=true;
				}
				long currentTime = System.currentTimeMillis();
				if(((currentTime - beginThusterTime) > 2950) && !turboSound ){

					turboSound=true;
				}	
			}

			if(upIsPressed){
				moveShipUp(ship);
			}

			if(downIsPressed){
				moveShipDown(ship, gameScreen.getHeight());
			}

			if(leftIsPressed){
				moveShipLeft(ship);
			}

			if(rightIsPressed){
				moveShipRight(ship, gameScreen.getWidth());
			}

		}
	}

	/**
	 * Move the ship up
	 * @param ship the ship
	 */
	private void moveShipUp(Ship ship){
		if(ship.getY() - ship.getSpeed() >= 0){
			ship.translate(0, -ship.getSpeed());
		}
	}

	/**
	 * Move the ship down
	 * @param ship the ship
	 */
	private void moveShipDown(Ship ship, int screenHeight){
		if(ship.getY() + ship.getSpeed() + ship.height < screenHeight){
			ship.translate(0, ship.getSpeed());
		}
	}

	/**
	 * Move the ship left
	 * @param ship the ship
	 */
	private void moveShipLeft(Ship ship){
		if(ship.getX() - ship.getSpeed() >= 0){
			ship.translate(-ship.getSpeed(), 0);
		}
	}

	/**
	 * Move the ship right
	 * @param ship the ship
	 */
	private void moveShipRight(Ship ship, int screenWidth){
		if(ship.getX() + ship.getSpeed() + ship.width < screenWidth){
			ship.translate(ship.getSpeed(), 0);
		}
	}

	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			GameStatus status = gameLogic.getStatus();
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting()){
				// new game
				lastBulletTime = System.currentTimeMillis();
				leftIsPressed = false;
				rightIsPressed = false;
				downIsPressed = false;
				upIsPressed = false;
				spaceIsPressed = false;

				gameLogic.newGame();
			}
			else{
				this.spaceIsPressed = true;
			}
			break;
		case KeyEvent.VK_SHIFT:
			InputHandler.shiftIsPressed = true;
			break;
		case KeyEvent. VK_F11:
	
		JOptionPane.showOptionDialog(null,
				"\n\n Try to accumulate the best score possible without being killed"
				+ "\n by other galaxies enemies.  Be carefull with several dangers like"
				+ "\n multiple asteroids, enemy ships, and the boss ship.  The boss is"
				+ "\n the principal command ship that require 25 bullets shoots to explode"
				+ "\n Explode an asteroid gives you 100 pts, an enemy ship 500 pts, and  "
				+ "\n a boss 1000 pts. You have 3 lives at the begginning, but each collision"
				+ "\n take off one. The red hearts will give you extra lives. Good luck! "
				+ "\n\nCo-Authors:  "
				+ "\n  Yomaira Rivera Albarran"
				+ "\n  Kensy Bernadeau"
				+ "\n  @2016", 
				"War Of Galaxy Help:",JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, 
			      null, null, null);
			break;
			
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		}
		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			InputHandler.shiftIsPressed = false;
			Ship ship = gameLogic.getShip(); 
			ship.setSpeed(ship.getDefaultSpeed());
			soundMan.stopRocketTurboSound();

			beginTurboSound=false;
			turboSound=false;
			break;
		}
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}
	public static boolean shiftPressed()
	{
		return shiftIsPressed;
	}
	
}