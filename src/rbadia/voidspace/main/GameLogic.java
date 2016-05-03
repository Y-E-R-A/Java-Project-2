package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Health;
import rbadia.voidspace.model.Ship;

import rbadia.voidspace.sounds.SoundManager;

/**
 * Handles general game logic and status.
 */
public class GameLogic {
	private GameScreen gameScreen;
	private GameStatus status;
	private SoundManager soundMan;

	private Ship ship;

	private List<EnemyShip> enemyShip;
	private List<BossShip> bossShip;
	private List<Health> health;
	private List<Asteroid> asteroid;
	private List<Bullet> bullets;
	private List<Bullet> enemyBullet;
	private List<Bullet> bossBullet;
	
	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;

		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();

	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}
	/**
	 * Returns the sounds effects manager
	 * @return the sounds effects manager
	 */
	public SoundManager getSoundMan() {
		return soundMan;
	}

	/**
	 * Returns the game screen
	 * @return the game screen
	 */
	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){
		status.setGameStarting(true);

		//initialize game variables
		bullets = new ArrayList<Bullet>();
		new ArrayList<Bullet>();

		health= new ArrayList<Health>();
		
		asteroid= new ArrayList<Asteroid>();

		enemyShip=new ArrayList<EnemyShip>();
		enemyBullet=new ArrayList<Bullet>();

		bossShip=new ArrayList<BossShip>();
		bossBullet=new ArrayList<Bullet>();

		status.setShipsLeft(5);
		status.setGameOver(false);
		status.setEnemiesDestroyed(0);
		status.addScore(-status.getScore());
		status.setNewAsteroid(false);
		status.setNewHealth(false);
		status.setLevel(1);

		// init the ship and the asteroid
		newShip(gameScreen);

		// prepare game screen
		gameScreen.NewGame();

		// delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * next level or game over conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}

	/**
	 * Actions to take when the game is over.
	 * @throws FileNotFoundException 
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();
		soundMan.gameOverSound();
		

		// delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	/**
	 * Fire a bullet from user ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);

		soundMan.stopBulletSound();
		soundMan.playBulletSound();
	}
	/**
	 * Fire a bullet from enemy ship.
	 * @param ship a enemy ship
	 */
	public void fireEnemyBullet(EnemyShip ship){
		Bullet bullet=new Bullet(ship);
		enemyBullet.add(bullet);
		soundMan.stopEnemyBulletSound();
		soundMan.playEnemyBulletSound();
	}
	/**
	 * Fire a bullet from boss ship.
	 * @param ship a boss ship
	 */
	public void fireBossBullet(BossShip ship){
		Bullet bullet=new Bullet(ship);
		bossBullet.add(bullet);
		soundMan.stopBossBulletSound();
		soundMan.playBossBulletSound();
	}

	/**
	 * Move user bullet once fired.
	 * @param bullet the bullet to move
	 * @return true if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Move enemy bullet once fired.
	 * @param bullet the bullet to move
	 * @return true if the bullet should be removed from screen
	 */

	public boolean moveEnemyBullet(Bullet enemyBullet)
	{
		if(enemyBullet.getY() + enemyBullet.getSpeed() < gameScreen.getHeight()){
			enemyBullet.translate(0, enemyBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Move boss bullet once fired.
	 * @param bullet the bullet to move
	 * @return true if the bullet should be removed from screen
	 */
	public boolean moveBossBullet(Bullet bossBullet)
	{
		if(bossBullet.getY() + bossBullet.getSpeed() < gameScreen.getHeight()){
			bossBullet.translate(0, bossBullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Create user ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}
	/**
	 * Create a new enemy ship.
	 */
	public void newEnemyShip(GameScreen screen){
		this.enemyShip.add(new EnemyShip(screen));
	}
	/**
	 * Create a new boss ship.
	 */
	public void newBossShip(GameScreen screen){
		this.bossShip.add(new BossShip(screen));
		soundMan.playScareSound();
	}
	/**
	 * Create a new asteroid.
	 */
	public void newAsteroid(GameScreen screen){
		asteroid.add(new Asteroid(screen));
	}
	/**
	 * Create a new health.
	 */
	public void newHealth (GameScreen screen){
		health.add(new Health(screen));
	}
	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * Returns the asteroid.
	 * @return the asteroid
	 */
	public List<Asteroid> getAsteroid() {
		return asteroid;
	}
	
	/**
	 * Returns the health.
	 * @return health 
	 */
	public List<Health> getHealth() {
		return health;
	}
	/**
	 * Return the enemy ship
	 * @return enemy ship
	 */
	public List<EnemyShip> getEnemyShip(){
		return enemyShip;
	}
	/**
	 * Return the boss ship
	 * @return a boss ship
	 */
	public List<BossShip> getBossShip(){
		return bossShip;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}
	/**
	 * Returns a list of bullets of the enemy ship.
	 * @return the list of bullets of enemy
	 */

	public List<Bullet> getEnemyBullet()
	{
		return enemyBullet;
	}
	/**
	 * Returns a list of bullets of the boss ship.
	 * @return the list of bullets of enemy
	 */
	public List<Bullet> getBossBullet()
	{
		return bossBullet;
	}

}