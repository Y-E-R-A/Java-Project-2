package rbadia.voidspace.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The game's main frame. Contains all the game's labels, file menu, and game screen.
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private GameScreen gameScreen = null;

	private JLabel destroyedLabel;
	private JLabel destroyedValueLabel;

	private JLabel shipsLabel;
	private JLabel shipsValueLabel;

	private JLabel highScoreLabel;
	private JLabel highScoreValueLabel;

	private JLabel scoreLabel;
	private JLabel scoreValueLabel;

	private JLabel levelValueLabel;
	private JLabel levelLabel;
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		initialize();

	}

	/**
	 * This method initializes this
	 * @return void
	 */
	private void initialize() {
		this.setSize(700, 600);
		this.setContentPane(getJContentPane());
		this.setTitle("War Of Galaxy");
		Dimension dim = this.getToolkit().getScreenSize();
		Rectangle bounds = this.getBounds();
		this.setLocation(
				(dim.width - bounds.width) / 2,
				(dim.height - bounds.height) / 2);
	}

	/**
	 * This method initializes jContentPane
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.weightx = 1.0D;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.anchor = GridBagConstraints.EAST;
			gridBagConstraints9.weightx = 1.0D;
			gridBagConstraints9.gridx = 0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.weightx = 1.0D;
			gridBagConstraints8.gridx = 7;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints7.gridy = 1;
			gridBagConstraints7.anchor = GridBagConstraints.EAST;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.gridx = 6;
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints6.gridy = 1;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.weightx = 1.0D;
			gridBagConstraints6.gridx = 5;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints5.gridy = 1;
			gridBagConstraints5.anchor = GridBagConstraints.EAST;
			gridBagConstraints5.weightx = 1.0D;
			gridBagConstraints5.gridx = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints4.gridy = 1;
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.gridx = 3;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.weightx = 1.0D;
			gridBagConstraints3.gridx = 2;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.weightx = 1.0D;
			gridBagConstraints2.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.anchor = GridBagConstraints.EAST;
			gridBagConstraints1.weightx = 1.0D;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.fill = GridBagConstraints.NONE;
			gridBagConstraints.gridwidth = 10;

			//initialize labels for the content Pane

			shipsLabel = new JLabel("Ships Left: ");
			shipsLabel.setForeground(Color.RED);
			shipsValueLabel = new JLabel("3");
			shipsValueLabel.setForeground(Color.WHITE);
			destroyedLabel = new JLabel("kills: ");
			destroyedLabel.setForeground(Color.RED);
			destroyedValueLabel = new JLabel("0");
			destroyedValueLabel.setForeground(Color.WHITE);
			scoreLabel = new JLabel("Score: ");
			scoreLabel.setForeground(new Color(48,212,253));
			scoreValueLabel= new JLabel("0");
			scoreValueLabel.setForeground(Color.WHITE);
			highScoreLabel= new JLabel("High Score: ");
			highScoreLabel.setForeground(new Color(48,212,253));
			highScoreValueLabel=new JLabel("0");
			highScoreValueLabel.setForeground(Color.WHITE);
			levelLabel = new JLabel("Level:   ");
			levelLabel.setForeground(new Color(48,212,253));
			levelValueLabel= new JLabel("1");
			levelValueLabel.setForeground(Color.WHITE);
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());

			//add labels to the content Pane
			jContentPane.add(getGameScreen(), gridBagConstraints);
			jContentPane.add(shipsLabel, gridBagConstraints1);
			jContentPane.add(shipsValueLabel, gridBagConstraints2);
			jContentPane.add(scoreLabel, gridBagConstraints3);
			jContentPane.add(scoreValueLabel, gridBagConstraints4);
			jContentPane.add(destroyedLabel, gridBagConstraints5);
			jContentPane.add(destroyedValueLabel, gridBagConstraints6);
			jContentPane.add(highScoreLabel, gridBagConstraints7);
			jContentPane.add(highScoreValueLabel, gridBagConstraints8);
			jContentPane.add(levelLabel, gridBagConstraints9);
			jContentPane.add(levelValueLabel, gridBagConstraints10);
			jContentPane.setBackground(Color.BLACK);
		}
		return jContentPane;
	}

	/**
	 * This method initializes gameScreen	
	 * @return GameScreen
	 */
	public GameScreen getGameScreen() {
		if (gameScreen == null) {
			
			gameScreen = new GameScreen();
			gameScreen.setShipsValueLabel(shipsValueLabel);
			gameScreen.setDestroyedValueLabel(destroyedValueLabel);
			gameScreen.setScoreValueLabel(scoreValueLabel);
			gameScreen.setLevelValueLabel(levelValueLabel);
			gameScreen.setHighScoreValueLabel(highScoreValueLabel);
		}
		return gameScreen;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"