package Menus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import Constants.Constants;
import Constants.GameSettings;
import Constants.Sounds;
import Controlers.Game;
import MapBuilder.MapBuilder;
import Start.Start;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 * The menu of the game
 *
 */
public class GameMenu extends BorderPane {
	Rectangle rec;
	TranslateTransition translatel;
	FadeTransition fade;
	ArrayList<FillTransition> ff;
	ParallelTransition pl;
	Random r = new Random();
	static boolean animate = true;
	int menu = 0;
	int language = SettingsMenu.language;
	VBox customMenu;
	VBox ioMenu;
	String[][] translation = GameSettings.MAIN_LANGUAGE;
	Font menuFont;

	public GameMenu() {
		try {
			menuFont = Font.loadFont(new FileInputStream(new File("Fonts/Jua-Regular.ttf")), 55);

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		this.setPadding(new Insets(25));
		String ioGameString = "ioGAME";
		Text[] ioGameText = new Text[ioGameString.length()];
		pl = new ParallelTransition();
		for (int i = 0; i < ioGameString.length(); i++) {
			ioGameText[i] = new Text(ioGameString.charAt(i) + "");
			ioGameText[i].setFont(menuFont);
			ioGameText[i].setFill(Color.WHITE);
			ioGameText[i].setEffect(new DropShadow(2, 3, 3, Color.rgb(150, 143, 160)));
			ioGameText[i].setStroke(Color.rgb(150, 143, 160));
			ioGameText[i].setStrokeWidth(1.5);
			translatel = new TranslateTransition(Duration.millis(2000), ioGameText[i]);
			translatel.setDelay(Duration.millis(i * 100));
			translatel.setByY(20);
			translatel.setAutoReverse(true);
			translatel.setCycleCount(Timeline.INDEFINITE);
			translatel.play();
		}

		String player = "Welcome " + GameSettings.playerName;
		Text[] playerText = new Text[player.length()];
		for (int i = 0; i < player.length(); i++) {
			playerText[i] = new Text(player.charAt(i) + "");
			playerText[i].setFont(Font.font("Dubai", 44));
			if (i < 7) {
				playerText[i].setFill(Color.WHITE);
			} else {
				playerText[i].setFill(Color.rgb(15, 143, 160));
			}
			playerText[i].setOpacity(0);
			fade = new FadeTransition(Duration.millis(3500), playerText[i]);
			fade.setDelay(Duration.millis(i * 50));
			fade.setToValue(1);
			fade.setCycleCount(Timeline.INDEFINITE);
			fade.setAutoReverse(true);
			fade.play();
		}

		ArrayList<MenuItem> items = new ArrayList<>();
		if (GameSettings.continueGame) {
			items.add(new MenuItem(translation[language][0]));
			items.get(menu++).setOnMouseClicked(e -> {
				continueGame();
			});
		}
		items.add(new MenuItem(translation[language][1]));
		items.add(new MenuItem(translation[language][2]));
		items.add(new MenuItem(translation[language][3]));
		items.add(new MenuItem(translation[language][4]));
		items.add(new MenuItem(translation[language][5]));
		items.add(new MenuItem(translation[language][6]));

		// New Game
		items.get(menu++).setOnMouseClicked(e -> {
			this.setCenter(customMenu);
		});

		// Build Map
		items.get(menu++).setOnMouseClicked(e -> {
			MapBuilder mapBuilder = new MapBuilder();
			try {
				mapBuilder.start(Start.stageMenu);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// High Score
		items.get(menu++).setOnMouseClicked(e -> {
			Start.sceneMenu.setRoot(new HighScoreMenu());
		});

		// Settings
		items.get(menu++).setOnMouseClicked(e -> {
			Start.sceneMenu.setRoot(new SettingsMenu());
		});

		// Credits
		items.get(menu++).setOnMouseClicked(e -> {
			Start.sceneMenu.setRoot(new CreditMenu());
		});

		// Exit
		items.get(menu++).setOnMouseClicked(e -> System.exit(0));

		VBox mainMenu = new VBox();
		mainMenu.setAlignment(Pos.CENTER);

		for (int i = 0; i < items.size(); i++) {
			mainMenu.getChildren().addAll(items.get(i));
		}

		HBox gameTitle = new HBox(3, ioGameText);
		gameTitle.setAlignment(Pos.CENTER);

		HBox welcomeMessage = new HBox(playerText);
		welcomeMessage.setAlignment(Pos.CENTER);

		ScaleTransition scaleMenu = new ScaleTransition(Duration.millis(2500), mainMenu);
		scaleMenu.setFromX(0.1);
		scaleMenu.setFromY(0.1);
		scaleMenu.setToX(1);
		scaleMenu.setToY(1);
		if (animate) {
			scaleMenu.play();
			animate = false;
		}

		ParallelTransition par = new ParallelTransition();
		par.play();

		/**
		 * Custom Menu
		 */

		MenuItem gameLevels = new MenuItem("Game Levels");
		MenuItem customLevels = new MenuItem("Custom Levels");

		MenuItem iLevels = new MenuItem("I Levels");
		MenuItem ioLevels = new MenuItem("IO Levels");

		gameLevels.setOnMouseClicked(e -> {
			Constants.LEVEL_PATH = "Levels/Game";
			this.setCenter(ioMenu);
		});

		customLevels.setOnMouseClicked(e -> {
			Constants.LEVEL_PATH = "Levels/Custom";
			this.setCenter(ioMenu);
		});

		iLevels.setOnMouseClicked(e -> {
			Constants.LEVEL_PATH += "/i";
			if (Constants.LEVEL_PATH.contains("Custom")) {
				this.setCenter(new CustomMenu());
			} else {
				Game.levelNr = 1;
				newGame();
			}
		});

		ioLevels.setOnMouseClicked(e -> {
			Constants.LEVEL_PATH += "/io";
			if (Constants.LEVEL_PATH.contains("Custom")) {
				this.setCenter(new CustomMenu());
			} else {
				Game.levelNr = 1;
				newGame();
			}
		});

		customMenu = new VBox(gameLevels, customLevels);
		ioMenu = new VBox(iLevels, ioLevels);
		customMenu.setSpacing(30);
		ioMenu.setSpacing(30);

		customMenu.setAlignment(Pos.CENTER);
		ioMenu.setAlignment(Pos.CENTER);

		VBox topBox = new VBox(gameTitle, welcomeMessage);
		topBox.setSpacing(25);
		this.setTop(topBox);
		this.setCenter(mainMenu);
		this.setStyle("-fx-background-image: url('Images/menuBackground.jpg'); "
				+ "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");

		playBackgroundMusic();

		Start.sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				if (GameSettings.continueGame)
					continueGame();
			}
		});

	}

	public void playBackgroundMusic() {
		if (GameSettings.backgroundMusic) {
			Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.setCycleCount(Timeline.INDEFINITE);
			Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.play();

		}
	}

	private void continueGame() {
		Game.sceneGame.setRoot(Game.root);
		Game.stageGame.setScene(Game.sceneGame);
		Game.stageGame.centerOnScreen();
		Game.stageGame.setTitle("IO Game");
		if (GameSettings.runGame) {
			Game.anim.start();
		}
	}

	private void newGame() {
		Game game = new Game();
		try {
			game.start(Start.stageMenu);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
