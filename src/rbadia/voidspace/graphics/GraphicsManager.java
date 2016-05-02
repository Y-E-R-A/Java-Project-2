package rbadia.voidspace.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.BossShip;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Health;
//import rbadia.voidspace.model.EnemyShipHard;
import rbadia.voidspace.model.Ship;

/**
 * Manages and draws game graphics and images.
 */
public class GraphicsManager {
	private BufferedImage shipImg;
	private BufferedImage bulletImg;
	private BufferedImage healthImg;
	private BufferedImage asteroidImg;
	private BufferedImage healthShineImg;
	private BufferedImage asteroidExplosionImg;
	private BufferedImage shipExplosionImg;
	private BufferedImage enemyShipImg;
	private BufferedImage bossShipImg;
	private BufferedImage enemyBulletImg;
	private BufferedImage bossBulletImg;
	private BufferedImage bossExplosionImg;
	private Image backgroundImg;
	/**
	 * Creates a new graphics manager and loads the game images.
	 */
	public GraphicsManager(){
		// load images
		try {
			this.shipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/ship.png"));
			this.bossShipImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/BossShip.png"));
			this.enemyShipImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemyShip.png"));
			this.asteroidImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroid.png"));
			this.healthImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/health.png"));
			this.asteroidExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/asteroidExplosion.png"));
			this.healthShineImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/healthShine.png"));
			this.shipExplosionImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/shipExplosion.png"));
			this.bulletImg = ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bullet.png"));
			this.enemyBulletImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemybullet.png"));
			this.bossBulletImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/enemybullet.png"));
			this.bossExplosionImg=ImageIO.read(getClass().getResource("/rbadia/voidspace/graphics/bossExplosion.png"));
			this.backgroundImg= new ImageIcon(getClass().getResource("/rbadia/voidspace/graphics/galaxy3d.gif")).getImage();


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, " graphic files not found",
					null, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBackground(Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(backgroundImg, 0, 0, observer);
	}
	

	/**
	 * Draws a ship image to the specified graphics canvas.
	 * @param ship the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShip(Ship ship, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipImg, ship.x, ship.y, observer);
	}
	
	/**
	 * Draws an asteroid image to the specified graphics canvas.
	 * @param asteroid the asteroid to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroid(Asteroid asteroid, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidImg, asteroid.x, asteroid.y, observer);
	}
	
	/**
	 * Draws a enemy ship image to the specified graphics canvas.
	 * @param enemyShip the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyShip(EnemyShip enemyShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyShipImg, enemyShip.x, enemyShip.y, observer);
	}
	/**
	 * Draws a boss ship image to the specified graphics canvas.
	 * @param bossShip the ship to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossShip(BossShip bossShip, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossShipImg, bossShip.x, bossShip.y, observer);
	}
	/**
	 * Draws a bullet image to the specified graphics canvas.
	 * @param bullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBullet(Bullet bullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bulletImg, bullet.x, bullet.y, observer);
	}
	/**
	 * Draws a enemy bullet image to the specified graphics canvas.
	 * @param enemyBullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawEnemyBullet(Bullet enemyBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(enemyBulletImg, enemyBullet.x, enemyBullet.y, observer);
	}
	/**
	 * Draws a boss bullet image to the specified graphics canvas.
	 * @param bossBullet the bullet to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossBullet(Bullet bossBullet, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossBulletImg, bossBullet.x, bossBullet.y, observer);
	}

	/**
	 * Draws a ship explosion image to the specified graphics canvas.
	 * @param shipExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawShipExplosion(Rectangle shipExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(shipExplosionImg, shipExplosion.x, shipExplosion.y, observer);
	}
	/**
	 * Draws a boss ship explosion image to the specified graphics canvas.
	 * @param bossExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawBossExplosion(Rectangle bossExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(bossExplosionImg, bossExplosion.x, bossExplosion.y, observer);
	}
	
	/**
	 * Draws an asteroid explosion image to the specified graphics canvas.
	 * @param asteroidExplosion the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawAsteroidExplosion(Rectangle asteroidExplosion, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(asteroidExplosionImg, asteroidExplosion.x, asteroidExplosion.y, observer);
	}

	/**
	 * Draws a health image to the specified graphics canvas.
	 * @param health the health to draw
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawHealth(Health health, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(healthImg, health.x, health.y, observer);
	}
	/**
	 * Draws an health shine image to the specified graphics canvas.
	 * @param healthShine the bounding rectangle of the explosion
	 * @param g2d the graphics canvas
	 * @param observer object to be notified
	 */
	public void drawHealthShine(Rectangle healthShine, Graphics2D g2d, ImageObserver observer) {
		g2d.drawImage(healthShineImg, healthShine.x, healthShine.y, observer);
	}

}