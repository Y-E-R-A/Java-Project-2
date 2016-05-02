package rbadia.voidspace.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rbadia.voidspace.graphics.GraphicsManager;
import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Health;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;

/**
 * Main game screen. Handles all game graphics updates and some of the game logic.
 */
public class GameScreen extends JPanel  {
	private static final long serialVersionUID = 1L;

	private BufferedImage backBuffer;
	private Graphics2D g2d;

	private static final int NEW_SHIP_DELAY = 500;
	private static final int NEW_ASTEROID_DELAY = 500;
	private static final int NEW_HEALTH_DELAY = 500;
	private static final int NEW_LEVEL_DELAY=1000;
	private static final int FIRE_DELAY=1000;
	private static final int DEAD_DELAY = 2000;
	private static final int SCORE_SHIP=500;
	private static final int SCORE_ASTEROID=100;
	private static final int SCORE_BOSS=1000;

	private long lastShipTime;
	private long lastHealthTime;
	private long lastAsteroidTime;
	private long lastBulletTime;
	private long lastEnemyShip;
	private long lastBossShipTime;

	private Rectangle healthSpark;
	private Rectangle asteroidExplosion;
	private Rectangle shipExplosion;
	private Rectangle enemyShipExplosion;
	private Rectangle bossShipExplosion;


	private JLabel shipsValueLabel;
	private JLabel destroyedValueLabel;
	private JLabel scoreValueLabel;
	private JLabel highScoreValueLabel;
	private JLabel levelValueLabel;

	private Random rand;
	private int lives =0;
	private boolean bossOnScreen= true;


	private Font originalFont;
	private Font bigFont;
	private Font biggestFont;

	private GameStatus status;
	private SoundManager soundMan;
	private GraphicsManager graphicsMan;
	private GameLogic gameLogic;

	private long timeForLevel;
	private long deadTime;

	protected int actualLevel=0;
	private int counter=0;

	private ArrayList<Boolean> asteroidDestroyed=new ArrayList<Boolean>();
	private ArrayList<Boolean> healthCatched=new ArrayList<Boolean>();
	private ArrayList<Boolean> enemyShipDestroyed=new ArrayList<Boolean>();
	private ArrayList<Boolean> bossShipDestroyed=new ArrayList<Boolean>();

	/**
	 * This method initializes 
	 * 
	 */
	public GameScreen() {
		super();
		// initialize random number generator
		rand = new Random();
		initialize();

		// initialize graphics manager
		setGraphicsMan(new GraphicsManager());

		// initialize back buffer image
		backBuffer = new BufferedImage(500, 400, BufferedImage.TYPE_INT_RGB);
		g2d=backBuffer.createGraphics();
	}

	/**
	 * Initialization method (for VE compatibility).
	 */
	private void initialize() {
		// set panel properties
		this.setSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setBackground(Color.BLACK);
	}

	/**
	 * Update the game screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw current back buffer to the actual game screen
		g.drawImage(backBuffer, 0, 0, this);
	}

	/**
	 * Update the game screen's back buffer image.
	 */
	public void updateScreen(){
		Ship ship = gameLogic.getShip();

		List<Asteroid> asteroid = gameLogic.getAsteroid();
		List<Health> health = gameLogic.getHealth();
		List<EnemyShip> enemyShip=gameLogic.getEnemyShip();
		List<BossShip> bossShip=gameLogic.getBossShip();
		List<Bullet> bullets = gameLogic.getBullets();
		List<Bullet> enemyBullet= gameLogic.getEnemyBullet();
		List<Bullet> bossBullet= gameLogic.getBossBullet();

		// set original font - for later use
		if(this.originalFont == null){
			this.originalFont = g2d.getFont();
			this.bigFont = originalFont;
		}

		// erase screen
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);

		// draw 15-50 random stars
		drawStars(15);
		if(actualLevel>=4){
			drawStars(50);
		}

		// if the game is starting, draw "Get Ready" message
		if(status.isGameStarting()){
			drawGetReady();
			return;
		}

		// if the game is over, draw the "Game Over" message
		if(status.isGameOver()){
			// draw the message
			drawGameOver();
			drawFinalScore();

			long currentTime = System.currentTimeMillis();
			// draw the explosions until their time passes
			if((currentTime - lastHealthTime) < NEW_HEALTH_DELAY){
				graphicsMan.drawHealthShine(healthSpark, g2d, this);
			}
			if((currentTime - lastAsteroidTime) < NEW_ASTEROID_DELAY){
				graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
			}
			if((currentTime - lastShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
			}
			if((currentTime - lastEnemyShip) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(enemyShipExplosion, g2d, this);
			}
			if((currentTime - lastBossShipTime) < NEW_SHIP_DELAY){
				graphicsMan.drawShipExplosion(bossShipExplosion, g2d, this);
			}
			return;
		}

		// the game has not started yet
		if(!status.isGameStarted()){
			// draw game title screen
			initialMessage();
			soundMan.playGameSound();
			return;
		}

		//Checks level and adds asteroids and enemy ships to the array
		if(status.getLevel()!=actualLevel)
		{
			timeForLevel=System.currentTimeMillis();
			actualLevel=status.getLevel(); 
		}
		else
		{
			if(asteroid.size()<actualLevel && asteroid.size()<=5)
			{
				gameLogic.newAsteroid(this);
				asteroidDestroyed.add(false);
			}
			if(enemyShip.size()<actualLevel && enemyShip.size()<2)
			{
				gameLogic.newEnemyShip(this);
				enemyShipDestroyed.add(false);
			}
			if(bossShip.size()<actualLevel && bossShip.size()<2)
			{
				gameLogic.newBossShip(this);
				bossShipDestroyed.add(false);
			}
			if(health.size()<actualLevel && health.size()<2)
			{
				gameLogic.newHealth(this);
				healthCatched.add(false);
			}
			long nowTime=System.currentTimeMillis();
			if((nowTime -timeForLevel)<NEW_LEVEL_DELAY)
			{
				drawNewLevel();

			}
		}

		//Draw health 
		for(int i=0; i<health.size();i++)
		{
			Health aHealth = health.get(i);

			if(i<2)
			{
				if(healthCatched.get(i))
				{
					//Will draw a new health in the current time plus 5
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastHealthTime) > NEW_HEALTH_DELAY){
						// draw a new asteroid
						lastHealthTime = currentTime;
						//negative y so it does not appear initially on screen
						aHealth.setLocation(rand.nextInt(getWidth() -40), (int) -aHealth.getHeight());
						healthCatched.set(i, false);
					}
					else{
						// draw explosion
						graphicsMan.drawHealthShine(healthSpark, g2d, this);
					}
				}
				else{if(aHealth.getY() + aHealth.getSpeed() < this.getHeight()){
					aHealth.translate(0, aHealth.getSpeed());
					graphicsMan.drawHealth(health.get(i), g2d, this);
				}
				else{
					//negative y so it does not appear initially on screen
					aHealth.setLocation(rand.nextInt(400) , (int) -aHealth.getHeight());
				}
			}
		}

			//Draw asteroids
			for(int index=0; index<asteroid.size();index++)
			{
				Asteroid ast = asteroid.get(index);

				if(index<2)
				{
					if(asteroidDestroyed.get(index))
					{
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
							// draw a new asteroid
							lastAsteroidTime = currentTime;
							//negative y so it does not appear initially on screen
							ast.setLocation(rand.nextInt(getWidth() -40), (int) -ast.getHeight());
							asteroidDestroyed.set(index, false);
						}
						else{
							// draw explosion
							graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
						}
					}
					else{if(ast.getY() + ast.getSpeed() < this.getHeight()){
						ast.translate(0, ast.getSpeed());
						graphicsMan.drawAsteroid(asteroid.get(index), g2d, this);
					}
					else{
						//negative y so it does not appear initially on screen
						ast.setLocation(rand.nextInt(400) , (int) -ast.getHeight());
					}
					}
				}

				//Asteroids move in diagonal 
				else if(index==2){
					if(asteroidDestroyed.get(index))
					{
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastAsteroidTime) > NEW_ASTEROID_DELAY){
							// draw a new asteroid
							lastAsteroidTime = currentTime;
							ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
							asteroidDestroyed.set(index,false);
						}
						else{
							// draw explosion
							graphicsMan.drawAsteroidExplosion(asteroidExplosion, g2d, this);
						}
					}
					else{
						if(ast.getY() + ast.getSpeed() < this.getHeight()){
							if(counter==0)
							{
								ast.translate(2, ast.getSpeed());
								graphicsMan.drawAsteroid(ast, g2d, this);
							}
							else{
								ast.translate(-2, ast.getSpeed());
								graphicsMan.drawAsteroid(ast, g2d, this);
							}
						}
						else{
							counter=rand.nextInt(2);
							ast.setLocation(rand.nextInt(getWidth() - ast.width), (int) -ast.getHeight());
						}
					}
				}

			}

			//Draw enemy ship
			if(actualLevel>2 &&actualLevel!=3 && !bossOnScreen){
				for(int index=0;index<enemyShip.size();index++)
				{
					EnemyShip eShip=enemyShip.get(index);
					if(enemyShipDestroyed.get(index))
					{
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastEnemyShip) > NEW_ASTEROID_DELAY){
							// draw a enemy ship
							lastEnemyShip = currentTime;
							enemyShipDestroyed.set(index, false);
						}
						else{
							// draw explosion
							graphicsMan.drawAsteroidExplosion(enemyShipExplosion, g2d, this);
						}
					}
					else{
						if(eShip.getY()+eShip.getEnemyShipSpeed()<this.getHeight())
						{
							eShip.translate(0, eShip.getEnemyShipSpeed());
							graphicsMan.drawEnemyShip(eShip, g2d, this);
						}
						else{
							eShip.setLocation(rand.nextInt(getWidth() - eShip.width), (int) -eShip.getHeight());
						}
					}
				}

				//initialize enemy bullets
				for(int index=0;index<enemyShip.size();index++)
				{
					int r=rand.nextInt(enemyShip.size());
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastBulletTime) > FIRE_DELAY){
						if(enemyShip.get(r).getX()>0)
						{
							lastBulletTime = currentTime;
							gameLogic.fireEnemyBullet(enemyShip.get(r));
						}
					}
				}

				//draw enemy bullet
				for(int index=0;index<enemyBullet.size();index++){
					Bullet bullet = enemyBullet.get(index);
					graphicsMan.drawEnemyBullet(bullet, g2d, this);

					boolean remove = gameLogic.moveEnemyBullet(bullet);
					if(remove){
						enemyBullet.remove(index);
						index--;
					}
				}
			}

			//Draw boss Ship

			if((actualLevel>3)  && bossOnScreen ){
				
				for(int index=0;index<bossShip.size();index++)
				{
					BossShip boss=bossShip.get(index);
					graphicsMan.drawBossShip(boss, g2d, this);
					//boss.translate(0, boss.getBossShipSpeed()/50);
					
					if(bossShipDestroyed.get(index))
					{ //Boss is destroyed
						long currentTime = System.currentTimeMillis();
						if((currentTime - lastBossShipTime) > NEW_ASTEROID_DELAY){
							// draw a new boss
							lastBossShipTime = currentTime+5000;
							bossShipDestroyed.set(index, false);
						}
						else{
							// draw boss explosion
							graphicsMan.drawBossExplosion(bossShipExplosion, g2d, this);
						}
					}
					else{
						//redraw boss
						if(boss.getY()+boss.getBossShipSpeed()<this.getHeight())
						{
								boss.translate(i, 90);
								
						}
							
								if((boss.getX()>getWidth()-300)||(boss.getY()>getWidth()-300)){
								int randomx = rand.nextInt(100)+1;
								int randomy = rand.nextInt(100)+50;
								//set the location of boss out of screen
								boss.setLocation(randomx+randomy, 2*randomy );
								graphicsMan.drawBossShip(boss, g2d, this);

							}

							graphicsMan.drawBossShip(boss, g2d, this);
						}
//						else{
//							boss.setLocation(rand.nextInt(getWidth() - boss.width), (int) -boss.getHeight());
//						}
					}
				}


				//initialize boss bullets
				for(int index=0;index<bossShip.size();index++)
				{
					int r=rand.nextInt(bossShip.size());
					long currentTime = System.currentTimeMillis();
					if((currentTime - lastBulletTime) > FIRE_DELAY){
						if(bossShip.get(r).getX()>0)
						{
							lastBulletTime = currentTime;
							gameLogic.fireBossBullet(bossShip.get(r));
						}
					}
				}

				//draw enemy bullet
				for(int index=0;index <bossBullet.size();index++){
					Bullet bullet = bossBullet.get(index);

					graphicsMan.drawBossBullet(bullet, g2d, this);

					boolean remove = gameLogic.moveBossBullet(bullet);
					if(remove){
						bossBullet.remove(index);
						index--;
					}
				}
			}

			long currentTimeDead = System.currentTimeMillis();
			if((currentTimeDead - deadTime) > DEAD_DELAY)
			{
				//check enemyShip-ship collision
				checkEnemyShipImpactShip(enemyShip,ship);

				//check boss to ship collisions 
				checkBossShipImpactShip(bossShip,ship);

				// check asteroid- ship collisions
				checkAsteroidImpactShip(asteroid,ship);
				// check health- ship collisions
				checkHealthImpactShip(health,ship);

				//check enemyBullet-ship collision
				checkEnemyBulletImpactShip(enemyBullet,ship);

				//check BossBullet-ship collision
				checkBossBulletImpactShip(bossBullet,ship);


			}

			//check ship bullet collides enemy ship
			checkShipBulletImpactEnemyShip(bullets,enemyShip,SCORE_SHIP);

			//check ship bullet collides boss ship 
			checkShipBulletImpactBossShip(bullets,bossShip,SCORE_BOSS);

			// check ship bullet collides asteroid 
			checkShipBulletImpactAsteroid(bullets,asteroid,SCORE_ASTEROID);

			// draw bullets
			for(int indexbullet=0; indexbullet<bullets.size(); indexbullet++){
				Bullet bullet = bullets.get(indexbullet);
				graphicsMan.drawBullet(bullet, g2d, this);

				boolean remove = gameLogic.moveBullet(bullet);
				if(remove){
					bullets.remove(indexbullet);
					indexbullet--;
				}
			}

			// draw ship
			if(!status.isNewShip()){

				graphicsMan.drawShip(ship, g2d, this);

			}
			else{
				// draw a new one
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastShipTime) > NEW_SHIP_DELAY){
					lastShipTime = currentTime;
					status.setNewShip(false);
					ship = gameLogic.newShip(this);
				}
				else{
					// draw explosion
					graphicsMan.drawShipExplosion(shipExplosion, g2d, this);
				}
			}
			//Labels to display

			// update asteroids destroyed label
			destroyedValueLabel.setText(Long.toString(status.getEnemiesDestroyed()));
			//update score label
			scoreValueLabel.setText(Integer.toString(status.getScore()));
			// update ships left label
			levelValueLabel.setText(Integer.toString(status.getLevel()));
			// update ships left label
			shipsValueLabel.setText(Integer.toString(status.getShipsLeft())); 
			//update high score value label
			status.updateHighScore();
			highScoreValueLabel.setText(Integer.toString(status.getHighScore()));
		}
	


	/**
	 * Draws the "Game Over" message.
	 */
	private void drawGameOver() {  
		String gameOverStr = "GAME OVER";
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.ITALIC);
		FontMetrics fm = g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameOverStr);
		if(strWidth > this.getWidth() - 10){
			biggestFont = currentFont;
			bigFont = biggestFont;
			fm = g2d.getFontMetrics(bigFont);
			strWidth = fm.stringWidth(gameOverStr);
		}
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setFont(bigFont);
		g2d.setPaint(Color.RED);
		g2d.drawString(gameOverStr, strX, strY);
	}

	/**
	 * Draws the initial "Get Ready!" message.
	 */
	private void drawGetReady() {
		String readyStr = "Get Ready!";
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(readyStr, strX, strY);

	}
	/**
	 * Draw level update in the screen.
	 */
	private void drawNewLevel(){
		String readyStr = "LEVEL "+status.getLevel();
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 1));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(readyStr);
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(readyStr, strX, strY);
	}
	/**
	 * Draw final score update in the screen after game over.
	 */
	private void drawFinalScore(){
		String finalScore = "YOUR SCORE "+status.getScore();
		g2d.setFont(originalFont.deriveFont(originalFont.getSize2D() + 5));
		FontMetrics fm = g2d.getFontMetrics();
		int ascent = fm.getAscent();
		int strWidth = fm.stringWidth(finalScore);
		int strX = (this.getWidth() - strWidth)/4;
		int strY = (this.getHeight() + ascent)/4;
		g2d.setPaint(Color.YELLOW);
		g2d.drawString(finalScore, strX, strY);
	}

	/**
	 * Draws the specified number of stars randomly on the game screen.
	 * @param numberOfStars the number of stars to draw
	 */
	private void drawStars(int numberOfStars) {

		if(actualLevel>=2&&actualLevel<4){
			g2d.setColor(Color.CYAN);
			for(int i=0; i<numberOfStars; i++){
				int x = (int)(Math.random() * this.getWidth());
				int y = (int)(Math.random() * this.getHeight());
				g2d.drawLine(x, y, x, y);
			}
			g2d.setColor(Color.RED);
			for(int i=0; i<numberOfStars; i++){
				int x = (int)(Math.random() * this.getWidth());
				int y = (int)(Math.random() * this.getHeight());
				g2d.drawLine(x, y, x, y);
			}

		}
		else if(actualLevel>=4){
			g2d.setColor(Color.YELLOW);
			for(int i=0; i<numberOfStars; i++){
				int x = (int)(Math.random() * this.getWidth());
				int y = (int)(Math.random() * this.getHeight());
				g2d.drawLine(x, y, x, y);
			}
			g2d.setColor(Color.RED);
			for(int i=0; i<numberOfStars; i++){
				int x = (int)(Math.random() * this.getWidth());
				int y = (int)(Math.random() * this.getHeight());
				g2d.drawLine(x, y, x, y);
			}
		}
		else{
			g2d.setColor(Color.WHITE);
			for(int i=0; i<numberOfStars; i++){
				int x = (int)(Math.random() * this.getWidth());
				int y = (int)(Math.random() * this.getHeight());
				g2d.drawLine(x, y, x, y);

			}
		}
	}
	/**
	 * Display initial game title screen.
	 */
	private void initialMessage() {
//		graphicsMan.drawBossShip(boss, g2d, this);
	  graphicsMan.drawBackground(g2d, this);
		String gameTitleStr = "WAR OF GALAXY";
		
		Font currentFont = biggestFont == null? bigFont : biggestFont;
		float fontSize = currentFont.getSize2D();
		bigFont = currentFont.deriveFont(fontSize + 1).deriveFont(Font.ITALIC).deriveFont(Font.ITALIC);
		FontMetrics fm =g2d.getFontMetrics(bigFont);
		int strWidth = fm.stringWidth(gameTitleStr);
		if(strWidth > this.getWidth() - 10){
			bigFont = currentFont;
			biggestFont = currentFont;
			fm = g2d.getFontMetrics(currentFont);
			strWidth = fm.stringWidth(gameTitleStr);
		}
		g2d.setFont(bigFont);
		int ascent = fm.getAscent();
		int strX = (this.getWidth() - strWidth)/2;
		int strY = (this.getHeight() + ascent)/2 - ascent;
		g2d.setPaint(new Color(100,226,80));
		g2d.drawString(gameTitleStr, strX, strY);
		g2d.setFont(originalFont);
		
		
		fm = g2d.getFontMetrics();
		String newGameStr = "Press <Space> to Start a New Game.";
		strWidth = fm.stringWidth(newGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = (this.getHeight() + fm.getAscent())/2 + ascent + 16;
		g2d.setPaint(Color.WHITE);
		g2d.drawString(newGameStr, strX, strY);

		fm = g2d.getFontMetrics();
		String exitGameStr = "Press <Esc> to Exit the Game.";
		strWidth = fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		g2d.drawString(exitGameStr, strX, strY);
		
		fm = g2d.getFontMetrics();
		String helpGameStr = "Press <f11> to Game Help";
		strWidth=fm.stringWidth(exitGameStr);
		strX = (this.getWidth() - strWidth)/2;
		strY = strY + 16;
		
		status.HighScore();
		g2d.drawString(helpGameStr, strX, strY);
		highScoreValueLabel.setText(Integer.toString(status.getHighScore()));
		
		
		
	}

	/**
	 * Prepare screen for game over.
	 */
	public void doGameOver() {
		shipsValueLabel.setForeground(new Color(128, 0, 0));
	}

	/**
	 * Prepare screen for a new game.
	 */
	public void NewGame(){  
		lastAsteroidTime = -NEW_ASTEROID_DELAY;
		lastShipTime = -NEW_SHIP_DELAY;

		bigFont = originalFont;
		biggestFont = null;

		// set labels' text
		shipsValueLabel.setText(Integer.toString(status.getShipsLeft()));
		destroyedValueLabel.setText(Long.toString(status.getEnemiesDestroyed()));
		levelValueLabel.setText("1");
		scoreValueLabel.setText("0");
		highScoreValueLabel.setText(Integer.toString(status.getHighScore()));

	}

	/**
	 * Sets the game graphics manager.
	 * @param graphicsMan the graphics manager
	 */
	public void setGraphicsMan(GraphicsManager graphicsMan) {
		this.graphicsMan = graphicsMan;
	}

	/**
	 * Sets the game logic handler
	 * @param gameLogic    the game logic handler
	 */
	public void setGameLogic(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.status = gameLogic.getStatus();
		this.soundMan = gameLogic.getSoundMan();
	}

	/**
	 * Sets the label that displays the value for asteroids destroyed.
	 * @param destroyedValueLabel the label to set
	 */
	public void setDestroyedValueLabel(JLabel destroyedValueLabel) {
		this.destroyedValueLabel = destroyedValueLabel;
	}

	/**
	 * Sets the label that displays the value for ship (lives) left
	 * @param shipsValueLabel the label to set
	 */
	public void setShipsValueLabel(JLabel shipsValueLabel) {
		this.shipsValueLabel = shipsValueLabel;
	}
	/**
	 * Sets the label that displays the score value.
	 * @param scoreValueLabel the label to set
	 */
	public void setScoreValueLabel(JLabel scoreValueLabel){
		this.scoreValueLabel=scoreValueLabel;
	}
	/**
	 * Sets the label that displays the high score record value.
	 * @param highScoreValueLabel the label to set
	 */
	public void setHighScoreValueLabel(JLabel highScoreValueLabel){
		this.highScoreValueLabel=highScoreValueLabel;
	}
	/**
	 * Sets the label that displays the value for game level.
	 * @param levelValueLabel the label to set
	 */
	public void setLevelValueLabel(JLabel levelValueLabel){
		this.levelValueLabel= levelValueLabel;
	}


	/**
	 * Checks if the user ship impacts health 
	 * @param health
	 * @param ship  user ship
	 */
	public void checkHealthImpactShip (List<?> health, Ship ship){

		@SuppressWarnings("unchecked")
		List<Rectangle> aHealth = (List<Rectangle>)health;
		for(int index=0;index<aHealth.size();index++)
		{ 
			if(aHealth.get(index).intersects(ship))
			{
				//remove heart
				healthSpark=(new Rectangle(
						aHealth.get(index).x,
						aHealth.get(index).y,
						aHealth.get(index).width,
						aHealth.get(index).height));
				aHealth.get(index).setLocation(aHealth.get(index).width, aHealth.get(index).height);
				lastHealthTime=(System.currentTimeMillis());
				healthCatched.set(index,true);
				//Stop health catch effect
				soundMan.stopHealthCatched();
				//Play health catch effect
				soundMan.playHealthSound();
				status.setShipsLeft(status.getShipsLeft()+1);
				break;
			}
		}  
	}

	/**
	 * Checks if the user ship bullets impact asteroid
	 * @param shipBullet
	 * @param asteroid  list of asteroids
	 * @param score of asteroid to add  
	 */
	public void checkShipBulletImpactAsteroid (List<Bullet> shipBullet, List<?> asteroid, int score)
	{
		@SuppressWarnings("unchecked")
		List<Rectangle> anAsteroid = (List<Rectangle>)asteroid;

		for(int i=0;i<shipBullet.size();i++)
		{
			Bullet bullet = shipBullet.get(i);
			for(int j=0; j< anAsteroid.size(); j++)
			{
				if(anAsteroid.get(j).intersects(bullet)){
					// increase enemies destroyed count    
					status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
					status.addScore(score);

					// "remove" asteroid
					asteroidExplosion=(new Rectangle(
							anAsteroid.get(j).x,
							anAsteroid.get(j).y,
							anAsteroid.get(j).width,
							anAsteroid.get(j).height));
					anAsteroid.get(j).setLocation(anAsteroid.get(j).width, anAsteroid.get(j).height);
					lastAsteroidTime=(System.currentTimeMillis());
					asteroidDestroyed.set(j,true);
					//Stop asteroid explosion to play it again.
					soundMan.stopAsteroidExplosion();
					// play asteroid explosion sound
					soundMan.playAsteroidExplosionSound();
					break;
				}
			}
		} 
	}

	/**
	 * Checks if the user ship bullets impact asteroid
	 * @param shipBullet
	 * @param enemyShip  list of enemy Ships 
	 * @param score of enemy ship to add  
	 */
	public void checkShipBulletImpactEnemyShip (List<Bullet> ShipBullet, List<?> enemyShip, int score){

		@SuppressWarnings("unchecked")
		List<Rectangle> anEnemy = (List<Rectangle>)enemyShip;

		for(int i=0;i<ShipBullet.size();i++)
		{
			Bullet bullet = ShipBullet.get(i);
			for(int j=0; j< anEnemy.size(); j++)
			{
				if(anEnemy.get(j).intersects(bullet)){

					// increase enemies destroyed count
					status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
					status.addScore(score);

					// "remove" enemy
					enemyShipExplosion = new Rectangle(
							anEnemy.get(j).x,
							anEnemy.get(j).y,
							anEnemy.get(j).width,
							anEnemy.get(j).height);
					anEnemy.get(j).setLocation(-anEnemy.get(j).width, -anEnemy.get(j).height);
					lastEnemyShip= System.currentTimeMillis();

					// play asteroid explosion sound
					soundMan.stopShipExplosion();
					soundMan.playShipExplosionSound();

					enemyShipDestroyed.set(j, true);
					// remove bullet
					ShipBullet.remove(i);
					break;
				}
			}
		}
	}

	/**
	 * Checks if the boss ship impacts user ship 
	 * @param bossShip
	 * @param ship  user ship
	 */
	@SuppressWarnings("unchecked")
	public void checkBossShipImpactShip (List<?> bossShip, Ship ship)
	{
		List<Rectangle> a = (List<Rectangle>)bossShip;
		for(int i=0;i<a.size();i++)
		{ 
			if(a.get(i).intersects(ship))
			{
				int randomx = rand.nextInt(100)+1;
				int randomy = rand.nextInt(getHeight()/2)+20;


				a.get(i).setLocation(getWidth()/randomx, getHeight()/randomy);

				// remove ship after collision
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);

				lastShipTime = System.currentTimeMillis();
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				if ( lives == 25){
					// bossShip will explode
					bossShipExplosion = new Rectangle(
							a.get(i).x,
							a.get(i).y,
							a.get(i).width,
							a.get(i).height);
					a.get(i).setLocation(-a.get(i).width, -a.get(i).height);
					lastBossShipTime = System.currentTimeMillis();
					soundMan.stopShipExplosion();
					soundMan.playBossExplosionSound();
					bossShipDestroyed.set(i, true);
					break;
				}
			}
		}
	}

	/**
	 * Checks if the enemy ship bullets impact user ship 
	 * @param enemyBullet list of enemy bullets
	 * @param ship user ship
	 * 
	 **/
	public void checkEnemyBulletImpactShip (List<?> enemyBullet, Ship ship){

		@SuppressWarnings("unchecked")
		List<Rectangle> ebullet = (List<Rectangle>)enemyBullet;
		for(int index=0;index<ebullet.size();index++)
		{ 
			if(ebullet.get(index).intersects(ship))
			{
				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);

				lastShipTime = System.currentTimeMillis();
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);
				// play ship explosion sound
				soundMan.playShipExplosionSound();
				ebullet.remove(index);
				break;

			}
		} 
	}
	/**
	 * Checks if the enemy ship bullets impact user ship 
	 * @param bossBullet list of enemy bullets
	 * @param ship user ship
	 * 
	 **/
	public void checkBossBulletImpactShip (List<?> bossBullet, Ship ship){

		@SuppressWarnings("unchecked")
		List<Rectangle> bbullet = (List<Rectangle>)bossBullet;
		for(int index=0;index<bbullet.size();index++)
		{ 
			if(bbullet.get(index).intersects(ship))
			{
				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);

				lastShipTime = System.currentTimeMillis();
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);
				// play ship explosion sound
				soundMan.playShipExplosionSound();
				bbullet.remove(index);
				break;

			}
		} 
	}

	/**
	 * Checks if the asteroid impact user ship 
	 * @param asteroid list of asteroid 
	 * @param ship user ship
	 * 
	 **/
	public void checkAsteroidImpactShip (List<?> asteroid,Ship ship){

		@SuppressWarnings("unchecked")
		List<Rectangle> aAsteroid = (List<Rectangle>)asteroid;
		for(int i=0;i<aAsteroid.size();i++)
		{ 
			if(aAsteroid.get(i).intersects(ship))
			{
				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);

				lastShipTime = System.currentTimeMillis();
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				asteroidExplosion=(new Rectangle(
						aAsteroid.get(i).x,
						aAsteroid.get(i).y,
						aAsteroid.get(i).width,
						aAsteroid.get(i).height));
				aAsteroid.get(i).setLocation(aAsteroid.get(i).width, aAsteroid.get(i).height);
				lastAsteroidTime=(System.currentTimeMillis());
				asteroidDestroyed.set(i,true);
				//Stop asteroid explosion to play it again.
				soundMan.stopAsteroidExplosion();
				// play asteroid explosion sound
				soundMan.playAsteroidExplosionSound();

				break;
			}
		}  
	}

	/**
	 * Checks if the enemy ship impacts user ship 
	 * @param enemyShip
	 * @param ship  user ship
	 */
	public void checkEnemyShipImpactShip (List<?> enemyShip, Ship ship){
		@SuppressWarnings("unchecked")
		List<Rectangle> rectangle = (List<Rectangle>) enemyShip;
		for(int i=0;i<rectangle.size();i++)
		{ 
			if(rectangle.get(i).intersects(ship))
			{
				// "remove" ship
				shipExplosion = new Rectangle(
						ship.x,
						ship.y,
						ship.width,
						ship.height);
				ship.setLocation(this.getWidth() + ship.width, -ship.height);
				status.setNewShip(true);

				lastShipTime = System.currentTimeMillis();
				// decrease number of ships left
				status.setShipsLeft(status.getShipsLeft() - 1);

				//enemy ship explosion
				enemyShipExplosion = new Rectangle(
						rectangle.get(i).x,
						rectangle.get(i).y,
						rectangle.get(i).width,
						rectangle.get(i).height);
				rectangle.get(i).setLocation(-rectangle.get(i).width, -rectangle.get(i).height);
				lastEnemyShip = System.currentTimeMillis();
				soundMan.stopShipExplosion();
				soundMan.playShipExplosionSound();
				enemyShipDestroyed.set(i, true);

				break;
			}
		} 
	}

	/**
	 * Check if the ship bullet impacts the boss ship
	 * @param shipBullet  Bullets from ship 
	 * @param bossShip- List of boss ship
	 * @param score -static score of boss to add
	 */
	public void checkShipBulletImpactBossShip (List<Bullet> shipBullet,List<?> bossShip, int score)
	{
		@SuppressWarnings("unchecked")
		List<Rectangle> anEnemy = (List<Rectangle>)bossShip;

		for(int bulletIndex=0;bulletIndex<shipBullet.size();bulletIndex++)
		{
			Bullet bullet = shipBullet.get(bulletIndex);
			for(int enemyIndex=0;enemyIndex<anEnemy.size();enemyIndex++)
			{
				if(anEnemy.get(enemyIndex).intersects(bullet)){

					lives+=1;
					if( lives==10 )
					{ 
						bossOnScreen = false;
						status.setEnemiesDestroyed(status.getEnemiesDestroyed()+1);
						status.addScore(score);

						bossShipExplosion = new Rectangle(
								anEnemy.get(enemyIndex).x,
								anEnemy.get(enemyIndex).y,
								anEnemy.get(enemyIndex).width,
								anEnemy.get(enemyIndex).height);
						anEnemy.get(enemyIndex).setLocation(-anEnemy.get(enemyIndex).width, -anEnemy.get(enemyIndex).height);
						lastBossShipTime= System.currentTimeMillis();

						// play asteroid explosion sound
						soundMan.playBossExplosionSound();

						bossShipDestroyed.set(enemyIndex, true);
						// remove bullet
						shipBullet.remove(bulletIndex);
						break;
					}
					//Create effect of small explosions before boss death
					else 
					{
						bossOnScreen = true;
						// play asteroid explosion sound
						soundMan.stopAsteroidExplosion();
						soundMan.playAsteroidExplosionSound();

						// remove bullet
						shipBullet.remove(bulletIndex);
						break;
					}
				}
			}
		} 
	}
}
