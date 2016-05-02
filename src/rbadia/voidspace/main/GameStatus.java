package rbadia.voidspace.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Container for game flags and/or status variables.
 */
public class GameStatus {
	// game flags
	private boolean gameStarted = false;
	private boolean gameStarting = false;
	private boolean gameOver = false;

	// status variables
	private boolean newShip;
	private boolean newAsteroid;
	private boolean newHealth;
	private long enemiesDestroyed = 0;
	private int shipsLeft;
	private int score;
	private int highscore;
	public GameStatus(){

	}

	/**
	 * Indicates if the game has already started or not.
	 * @return if the game has already started or not
	 */
	public synchronized boolean isGameStarted() {
		return gameStarted;
	}


	public synchronized void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * Indicates if the game is starting ("Get Ready" message is displaying) or not.
	 * @return if the game is starting or not.
	 */
	public synchronized boolean isGameStarting() {
		return gameStarting;
	}

	public synchronized void setGameStarting(boolean gameStarting) {
		this.gameStarting = gameStarting;
	}

	/**
	 * Indicates if the game has ended and the "Game Over" message is displaying.
	 * @return if the game has ended and the "Game Over" message is displaying.
	 */
	public synchronized boolean isGameOver() {
		return gameOver;
	}

	public synchronized void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Indicates if a new ship should be created/drawn.
	 * @return if a new ship should be created/drawn
	 */
	public synchronized boolean isNewShip() {
		return newShip;
	}

	public synchronized void setNewShip(boolean newShip) {
		this.newShip = newShip;
	}

	/**
	 * Indicates if a new asteroid should be created/drawn.
	 * @return if a new asteroid should be created/drawn
	 */
	public synchronized boolean isNewAsteroid() {
		return newAsteroid;
	}

	public synchronized void setNewAsteroid(boolean newAsteroid) {
		this.newAsteroid = newAsteroid;
	}
	
	/**
	 * Indicates if a new health should be created/drawn.
	 * @return if a new health should be created/drawn
	 */
	public synchronized boolean isNewHealth() {
		return newHealth;
	}

	public synchronized void setNewHealth(boolean newHealth) {
		this.newHealth = newHealth;
	}

	/**
	 * Returns the number of enemies destroyed. 
	 * @return the number of enemies destroyed
	 */
	public synchronized long getEnemiesDestroyed() {
		return enemiesDestroyed;
	}

	public synchronized void setEnemiesDestroyed(long enemiesDestroyed) {
		this.enemiesDestroyed = enemiesDestroyed;
	}
	/**
	 * Returns the number of boss destroyed. 
	 * @return the number of boss destroyed
	 */
	public synchronized long getBossDestroyed() {
		return enemiesDestroyed;
	}

	public synchronized void setBossDestroyed(long bossDestroyed) {
		this.enemiesDestroyed = bossDestroyed;
	}
	/**
	 * Returns the number ships/lives left.
	 * @return the number ships left
	 */
	public synchronized int getShipsLeft() {
		return shipsLeft;
	}

	public synchronized void setShipsLeft(int shipsLeft) {
		this.shipsLeft = shipsLeft;
	}
	
	/**
	 * Calculate the score with static score of the respective enemy destroyed
	 * @return score value
	 */
	public synchronized void addScore(int points)
	{
		score+=points;
	}
	/**
	 * Get the score on a specific instant
	 * @return score value
	 */
	public synchronized int getScore()
	{
		return score;
	}
		
	/**
	 * Get the level reached on the game instant
	 * @return level  
	 */
	public synchronized int getLevel()
	{
		return (int) ((getEnemiesDestroyed()+5)/5);
	}
	/**
	 * Set the level reached on the game instant on this.level
	 * @return void  
	 */
	public synchronized void setLevel(int level) {
	}
	
	/**
	 * Update the high score in a file
	 * @return void  
	 */
	public void updateHighScore()
	{
		if(highscore<score)
		{
			PrintWriter file;
			try {
				file= new PrintWriter("highScore.txt");
				file.println(score);
				file.close();
				highscore=score;
			} catch (FileNotFoundException e) {

				JOptionPane.showMessageDialog(null, "High Score file not found",
						null, JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	/**
	 * Store the high score (record) in a program file
	 * @return void  
	 */
	public void HighScore()
	{
		File readscorefile=new File("highScore.txt");
		Scanner file;
		try {
			file = new Scanner(readscorefile);
			highscore=file.nextInt();
		} catch (FileNotFoundException e) {

			JOptionPane.showMessageDialog(null, "High Score file not found",
					null, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	/**
	 * Get the high score of the game stored in a file
	 * @return high score  
	 */
	
	public int getHighScore()
	{
		return highscore;
	}
}