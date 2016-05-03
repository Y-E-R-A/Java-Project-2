package rbadia.voidspace.main;

import javax.swing.JFrame;

//Edited by: 
//Yomaira Rivera Albarran
//Kensy Bernadeau Rivera
// Last time edited: 5/2/16

/**
 * Main game class. Starts the game.
 */
public class VoidSpaceMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// initialize main frame
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get game screen
		GameScreen gameScreen = frame.getGameScreen();

		// initialize game logic handler
		GameLogic gameLogic = new GameLogic(gameScreen);

		// pass some variables to game screen
		gameScreen.setGameLogic(gameLogic);

		// initialize input handler
		InputHandler inputHandler = new InputHandler(gameLogic);
		frame.addKeyListener(inputHandler);

		// show main frame
		frame.setVisible(true);

		// initialize main game loop
		new Thread(new GameLoop(gameScreen, gameLogic, inputHandler)).start();
	}
}