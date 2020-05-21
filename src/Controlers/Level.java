package Controlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Blocks.Block;
import Blocks.Bridge;
import Coins.Coin;
import Constants.Constants;
import Model.GameModel;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Gates.Gate;
import Keys.Key;
import Lives.Life;
import Obstacles.Pendulum;
import Obstacles.Saw;
/**
 * Level class to create each level
 * It reads a text file and converts all those characters to game objects
 */
public class Level extends Pane {
	public static ArrayList<GameModel> blocks = new ArrayList<GameModel>();
	public static ArrayList<ImageView> coins = new ArrayList<ImageView>();


	// change the type of the obstacles array list from ImageView to Node
	// in order to include all the components of the obstacles
	public static ArrayList<Node> obstacles = new ArrayList<Node>();

	public static Text coinNr = new Text("X " + Coin.collectedCoinsNr);
	public static ImageView coinPlaceHolder = new ImageView("Images/coin.png");
	public static Text lifeNr = new Text("X " + Life.collectedLivesNr);
	public static Text secNr = new Text("Time: 0");
	ImageView lifePlaceHolder = new ImageView("Images/life.png");
	ImageView timePlaceHolder = new ImageView("Images/time.png");
	HBox topBar = new HBox(coinPlaceHolder, coinNr, lifePlaceHolder, lifeNr, timePlaceHolder, secNr);

	String fileUrl;
	static Player playerI, playerO;
	Player currentPlayer;
	Coin c;
	Block b;
	public static Key key;
	public static Saw s;

	Pendulum p;
	Bridge r;
	public static Gate g;
	BufferedReader br = null;
	public static Life life;
	ImageView BG;
	HashMap<KeyCode, Boolean> keyboard = new HashMap<KeyCode, Boolean>();

	public Level(String url) {
		// Clear all ArrayLists
		blocks.clear();
		coins.clear();
		obstacles.clear();
		lifeNr.setText("X " + Life.collectedLivesNr);
		playerI=null;
		playerO=null;
		r = null;
		key = null;
		g = null;
		life = null;
		
		BG = new ImageView("Images/BG.jpg");
		topBar.setTranslateX(20);
		topBar.setTranslateY(20);
		topBar.setSpacing(10);
		topBar.setAlignment(Pos.CENTER);
		try {
			Font menuFont = Font.loadFont(new FileInputStream(new File("Fonts/IndieFlower.ttf")), 25);
			coinNr.setFont(menuFont);
			lifeNr.setFont(menuFont);
			secNr.setFont(menuFont);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		this.getChildren().addAll(BG);
		blocks.clear();
		coins.clear();
		fileUrl = url;
		try {
			br = new BufferedReader(br = new BufferedReader(new FileReader(fileUrl)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int nrOfLines = 0;
		String line;
		try {
			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					switch (line.charAt(i)) {
					case 'c':
						c = new Coin(i * Constants.BLOCK_SIZE, nrOfLines * Constants.BLOCK_SIZE, "Images/coin.png");
						this.getChildren().add(c);
						coins.add(c);
						break;
					case 'b':
						b = new Block(i * Constants.BLOCK_SIZE, nrOfLines * Constants.BLOCK_SIZE, "Images/block.png");
						this.getChildren().add(b);
						blocks.add(b);
						break;
					case 'x':
						s = new Saw(i * Constants.BLOCK_SIZE, nrOfLines * Constants.BLOCK_SIZE, "Images/saw1.png");
						this.getChildren().add(s);
						obstacles.add(s);
						break;
					case 'p': 
						p = new Pendulum(i * Constants.BLOCK_SIZE, nrOfLines * Constants.BLOCK_SIZE);
						this.getChildren().addAll(p, p.getUpperBall(), p.getThread(),								p.getLowerBall());
						obstacles.add(p.getLowerBall());
						break;
					case 'o':
						playerO = new Player(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE,
								"Images/o.png");
						this.getChildren().add(playerO);
						break;
					case 'i':
						playerI = new Player(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE,
								"Images/i.png");
						this.getChildren().add(playerI);
						break;
					case 'k':
						key = new Key(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE, "Images/key.png");
						this.getChildren().add(key);
						Key.keyIsReached = true;
						break;
					case 'g':
						g = new Gate(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE, "Images/gate.png");
						this.getChildren().add(g);
						g.setOpacity(0.5);
						g.setFitHeight(80);
						g.setFitWidth(80);
						break;
					case 'l':
						if (Game.firstRound) {
							life = new Life(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE,
									"Images/life.png");
							this.getChildren().add(life);
							Life.removeL = false;
						}
						break;

					case 'r':
						r = new Bridge(i * Constants.PLAYER_SIZE, nrOfLines * Constants.PLAYER_SIZE,
								"Images/bridge.png", Constants.PLAYER_SIZE * 2);
						this.getChildren().add(r);
						blocks.add(r);
						break;
					}
				}
				nrOfLines++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		changeBlocksImg();
		this.getChildren().addAll(topBar);
	}

	public void updateLevel() {
		if (!Life.removeL && Level.life != null) {
			Life.checkLife();
		}
		if (Level.key != null) {
			Key.checkKeys();
		}

		if (r != null) {
			r.moveBridge();
		}
	}

	// remove objects from the level
	public void removeChild(ImageView object) {
		this.getChildren().remove(object);
	}

	boolean goToNextLevel() {
		return didTheyMeet() || reachedGate(currentPlayer);
	}
	/**
	 * Check if the player met each other
	 * @return true if they met each other
	 */
	boolean didTheyMeet() {
		if (playerI != null && playerO != null) {
			if ((playerI.getTranslateX() - playerO.getTranslateX() > -Constants.PLAYER_SIZE)
					&& (playerI.getTranslateX() - playerO.getTranslateX() < Constants.PLAYER_SIZE)
					&& (playerI.getTranslateY() - playerO.getTranslateY() > -Constants.PLAYER_SIZE)
					&& (playerI.getTranslateY() - playerO.getTranslateY() < Constants.PLAYER_SIZE)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param player the Active player
	 * @return true if the player reached the gate
	 */
	boolean reachedGate(Player player) {
		if (g != null) {
			if (player.getBoundsInParent().intersects(g.getBoundsInParent()) && g.getOpacity() == 1) {
				Gate.deactivateGate();
				return true;
			}
		}
		return false;
	}
	/**
	 * If the block, has another block above it, change its fill image.
	 */
	void changeBlocksImg() {
		for (int i = 0; i < blocks.size(); i++) {
			for (int j = i + 1; j < blocks.size(); j++) {
				if (blocks.get(i).getBoundsInParent().intersects(blocks.get(j).getBoundsInParent())
						&& blocks.get(i).getTranslateX() - blocks.get(j).getTranslateX() < Constants.BLOCK_SIZE
						&& blocks.get(i).getTranslateX() - blocks.get(j).getTranslateX() > -Constants.BLOCK_SIZE) {
					blocks.get(j).setImage(new Image("Images/under-block.png"));
				}
			}
		}
	}
	
	/**
	 * @param player Change the active player
	 */
	public void setPlayer(Player player) {
		currentPlayer = player;
	}
	

}
