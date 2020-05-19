package Controlers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import Backgrounds.CloudsBG;
import Blocks.Bridge;
import Obstacles.Obstacles;
import Start.Start;
import Coins.Coin;
import Constants.Constants;
import Constants.GameSettings;
import Constants.Sounds;
import Keys.Key;
import Lives.Life;
import Menus.GameMenu;
import Menus.InBetweenSlide;

/**
 * Game Class Contains Animation Timer Every event that needs to be checked
 * live, is controlled here.
 *
 */
public class Game extends Application {

	File folder = new File(Constants.LEVEL_PATH);
	File[] levels = folder.listFiles();

	// static to access the Player in other classes
	public static Player player;
	public static Level level;
	public static int levelNr = 1;
	HashMap<KeyCode, Boolean> keyboard = new HashMap<KeyCode, Boolean>();
	boolean dead, levelFinished;
	public static AnimationTimer anim;
	public static long now;

	public static Pane root;
	public static Scene sceneGame;
	public static Stage stageGame;

	Date date;
	public static long start;
	public static double score = 0;
	public static double totalScore = 0;
	public static boolean firstRound = true;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageGame = primaryStage;

		date = new Date();
		start = date.getTime();
		Life.collectedLivesNr = 2;
		Coin.collectedCoinsNr = 0;
		totalScore = 0;
		GameSettings.runGame = true;
		firstRound = true;

		// Level should be first created
		level = new Level(Constants.LEVEL_PATH + "/level" + levelNr + ".txt");

		root = level;
		// create the player after creating the Level
		player = Level.playerI;
		level.setPlayer(player);

		sceneGame = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		/**
		 * MODIFIED 01-04-2019
		 */
		sceneGame.setOnKeyPressed(e -> {
			keyboard.put(e.getCode(), true);
			if (e.getCode() == KeyCode.ESCAPE) {
				try {
					if (Start.stageMenu != null) {
						GameSettings.continueGame = true;
						anim.stop();
						Start.sceneMenu.setRoot(new GameMenu());
						stageGame.setScene(Start.sceneMenu);
						stageGame.setTitle("Welcome to IO Game");
						Start.stageMenu.centerOnScreen();

						keyboard.clear();
					}
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		});
		sceneGame.setOnKeyReleased(e -> keyboard.put(e.getCode(), false));
		anim = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (continueGame()) {
					play();
				}
			}
		};

		anim.start();
		stageGame.setScene(sceneGame);
		stageGame.centerOnScreen();
		stageGame.setTitle("IO Game");
		stageGame.show();
		stageGame.setResizable(false);
	}

	void play() {

		date = new Date();
		now = (long) ((date.getTime() - start));
		Level.secNr.setText(String.format("%d", now / 1000));

		createCloud();

		controlPlayer();

		checkWin();

		checkAlive();

		player.gravity();
		player.move();

		Coin.checkCoins();
		Obstacles.checkObstacles();

		if (!Life.removeL && Level.life != null) {
			Life.checkLife();
		}
		if (Level.key != null) {
			Key.checkKeys();
		}

		if (Level.blocks.contains(Level.r)) {
			Bridge.moveBridge();
		}

	}

	boolean keyPressed(KeyCode key) {
		return keyboard.getOrDefault(key, false);
	}

	/**
	 * Should the game continue ?
	 * 
	 * @return true if player is alive and level not finished
	 */
	boolean continueGame() {
		return !(levelFinished || dead);
	}

	// Return the Level to remove objects from the Pane (for example: collected
	// coins)
	public static Level returnLevel() {
		return level;
	}

	/**
	 * Function to create clouds It uses math to add them rarely random And randomly
	 * rare some of the clouds are on top.
	 */
	private void createCloud() {
		int shouldItDisplayCloud = (int) (Math.random() * 300 + 1);
		if (shouldItDisplayCloud == 1) {

			CloudsBG bgg = new CloudsBG();
			int idx = level.getChildren().indexOf(level.BG);
			level.getChildren().add(idx + 1, bgg);

			int toFrontt = (int) (Math.random() * 10 + 1);
			if (toFrontt == 1) {
				bgg.toFront();
			}
		}
	}

	/**
	 * Control the players by arrows, A,D,W,S or I & O
	 */
	private void controlPlayer() {
		if (keyPressed(KeyCode.A) || keyPressed(KeyCode.LEFT)) {
			if (player.getVelocity().getX() > -5)
				player.addVelocity(-1, 0);
		}
		if (keyPressed(KeyCode.D) || keyPressed(KeyCode.RIGHT)) {
			if (player.getVelocity().getX() < 5)
				player.addVelocity(1, 0);
		}
		if (keyPressed(KeyCode.W) || keyPressed(KeyCode.UP)) {
			player.jumpPlayer();
		}
		if (keyPressed(KeyCode.S) || keyPressed(KeyCode.DOWN)) {
			player.goVertical(5);
		}
		if (keyPressed(KeyCode.I)) {
			if (Level.playerI != null) {
				player = Level.playerI;
				level.setPlayer(player);
			}
		}
		if (keyPressed(KeyCode.O)) {
			if (Level.playerO != null) {
				player = Level.playerO;
				level.setPlayer(player);
			}
		}
	}

	/**
	 * Check if the player is still alive
	 */
	private void checkAlive() {
		// Display a screen when the player loses the level

		if (!(player.isAlive())) {

			// play DIE_MEDIA
			if (GameSettings.gameSounds) {
				Sounds.DIE_MEDIAPLAYER = new MediaPlayer(Sounds.DIE_MEDIA);
				Sounds.DIE_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
				Sounds.DIE_MEDIAPLAYER.play();
			}

			if (Life.collectedLivesNr > 0) {
				anim.stop();
				score = 0;
				GameSettings.runGame = false;
				Life.collectedLivesNr--;
				root.getChildren().add(new InBetweenSlide("Restart The Level"));
				InBetweenSlide.getMoveToNextBt().setOnAction(e -> {
					restartGame();
					level = new Level(Constants.LEVEL_PATH + "/level" + levelNr + ".txt");
					root = level;
					// default player when the level starts
					player = Level.playerI;
					level.setPlayer(player);
					sceneGame.setRoot(root);
					anim.start();
					GameSettings.runGame = true;

				});

			} else {
				anim.stop();
				score = 0;
				root.getChildren().add(new InBetweenSlide("Main Menu"));
				InBetweenSlide.getMessage().setText("GAME OVER!");
				GameSettings.continueGame = false;
				sceneGame.setOnKeyPressed(x -> {
				});

				addHighScore();

				InBetweenSlide.getMoveToNextBt().setOnAction(e -> {
					Start.sceneMenu.setRoot(new GameMenu());
					stageGame.setScene(Start.sceneMenu);
					stageGame.setTitle("Welcome to IO Game");
					Start.stageMenu.centerOnScreen();
				});
			}
		}

		if (player.getTranslateY() >= Constants.SCREEN_HEIGHT) {
			player.setAlive(false);
		}

	}

	/**
	 * The function to check if the user won by meeting both characters with each
	 * other or by reaching the gate.
	 */
	private void checkWin() {
		// Change the Level
		if (level.goToNextLevel()) {

			// play media when i & o meet
			if (level.didTheyMeet()) {
				if (GameSettings.gameSounds) {
					Sounds.MEETING_MEDIAPLAYER = new MediaPlayer(Sounds.MEETING_MEDIA);
					Sounds.MEETING_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
					Sounds.MEETING_MEDIAPLAYER.play();
				}
			} else {
				if (GameSettings.gameSounds) {
					Sounds.REACHED_GATE_MEDIAPLAYER = new MediaPlayer(Sounds.REACHED_GATE_MEDIA);
					Sounds.REACHED_GATE_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
					Sounds.REACHED_GATE_MEDIAPLAYER.play();
				}
			}

			if (levelNr < levels.length) {
				levelNr++;

				anim.stop();
				calculateScore();
				GameSettings.runGame = false;
				root.getChildren().add(new InBetweenSlide("Next Level"));
				InBetweenSlide.getMessage().setText("You passed the level!");

				InBetweenSlide.getMoveToNextBt().setOnAction(e -> {
					restartGame();
					GameSettings.runGame = true;
					firstRound = true;
					level = new Level(Constants.LEVEL_PATH + "/level" + levelNr + ".txt");
					root = level;
					// default player when the level starts
					player = Level.playerI;
					level.setPlayer(player);
					sceneGame.setRoot(level);
					anim.start();
				});

			} else {
				anim.stop();
				calculateScore();
				GameSettings.runGame = false;
				root.getChildren().add(new InBetweenSlide("Main Menu"));
				InBetweenSlide.getMessage().setText("\tCongratolations!\n    You passed all Levels!");

				addHighScore();

				InBetweenSlide.getMoveToNextBt().setOnAction(e -> {
					GameSettings.continueGame = false;
					Start.sceneMenu.setRoot(new GameMenu());
					stageGame.setScene(Start.sceneMenu);
					stageGame.setTitle("Welcome to IO Game");
					Start.stageMenu.centerOnScreen();
				});
			}

		}
	}

	/**
	 * Restart the game Reset the variables
	 */
	private void restartGame() {
		date = new Date();
		start = date.getTime();
		score = 0;
		Coin.collectedCoinsLvl = 0;
		Level.coinNr.setText("X " + Coin.collectedCoinsLvl);
		Level.lifeNr.setText("X " + Life.collectedLivesNr);

	}

	/**
	 * The function to calculate the scores. Its a calculation considering life,
	 * couns and time.
	 */
	private void calculateScore() {
		score = ((5 * Life.collectedLivesNr) + Coin.collectedCoinsNr + 1) / (Game.now / 1000.0) * 10000;
		totalScore += score;
	}

	/**
	 * Add high scores to the file.
	 */
	private void addHighScore() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("Score/high_score.txt"), true));
			out.write(String.format("\n\t   %s,%.0f,%s", GameSettings.playerName, totalScore, dateFormat.format(date)));
			out.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
