package rbadia.voidspace.sounds;

import java.applet.Applet;
import java.applet.AudioClip;

import rbadia.voidspace.main.GameScreen;

/**
 * Manages and plays the game's sounds.
 */
public class SoundManager {
	private static final boolean SOUND_ON = true;

	private AudioClip shipExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/shipExplosion.wav"));
	private AudioClip asteroidExplosionSound=Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/asteroidExplosion.wav"));
	private AudioClip gameOverSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/gameOver.wav"));
	private AudioClip bulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/laser.wav"));
	private AudioClip bulletEnemySound=Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/BulletEnemySound.wav"));
	private AudioClip rocketTurboSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/RocketTurboSound.wav"));
	private AudioClip bossBulletSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/bossBullet.wav"));
	private AudioClip gameSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/GameSong.wav"));
	private AudioClip scare = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/Scary.wav"));
	private AudioClip bossExplosionSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/bossExplosion.wav"));
	private AudioClip healthSound = Applet.newAudioClip(GameScreen.class.getResource(
			"/rbadia/voidspace/sounds/healthSound.wav"));

	/*
	 * Plays sound for health catched sound.
	 */
	public void playHealthSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					healthSound.play();
				}
			}).start();
		}
	}

	/*
	 * Plays sound for bullets fired by the ship.
	 */
	public void playBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletSound.play();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for ship explosions.
	 */
	public void playShipExplosionSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					shipExplosionSound.play();
				}
			}).start();
		}
	}
	public void playEnemyBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bulletEnemySound.play();
				}
			}).start();
		}
	}
	public void playBossBulletSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bossBulletSound.play();
				}
			}).start();
		}
	}
	public void gameOverSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					gameOverSound.play();
				}
			}).start();
		}
	}
	/**
	 * Plays sound for background sound
	 */
	public void gameSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					gameSound.loop();
				}
			}).start();
		}
	}

	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playAsteroidExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					asteroidExplosionSound.play();
				}
			}).start();
		}
	}
	/**
	 * Plays sound for asteroid explosions.
	 */
	public void playBossExplosionSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					bossExplosionSound.play();
				}
			}).start();
		}
	}
	/**
	 * Plays sound for scary boss.
	 */
	public void playScareSound(){
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					scare.play();
				}
			}).start();
		}
	}
	/**
	 * Plays sound for the start sound of the thrusters
	 */
	public void playRocketTurboSound(){
		// play sound for asteroid explosions
		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					rocketTurboSound.play();
				}
			}).start(); 
		}
	}

	public void playGameSound(){
		// play game theme song

		if(SOUND_ON){
			new Thread(new Runnable(){
				public void run() {
					gameSound.play();
				}
			}).start();
		}

	}

	public void stopHealthCatched(){
		healthSound.stop();
	}
	public void stopBossExplosion(){
		bossExplosionSound.stop();
	}
	public void stopAsteroidExplosion(){
		asteroidExplosionSound.stop();
	}

	public void stopRocketTurboSound(){
		rocketTurboSound.stop();
	}

	public void stopBulletSound() {
		bulletSound.stop();

	}
	public void stopScareSound() {
		scare.stop();

	}
	public void stopEnemyBulletSound() {
		bulletEnemySound.stop();
	}
	public void stopBossBulletSound() {
		bossBulletSound.stop();
	}

	public void stopShipExplosion() {
		shipExplosionSound.stop();

	}
}