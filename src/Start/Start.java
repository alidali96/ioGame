package Start;

import Constants.Constants;
import Constants.GameSettings;
import Constants.Sounds;
import Menus.GameMenu;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 * Main Class.
 * Start the game from here
 *
 */
public class Start extends Application {
	public static Stage stageMenu;
	public static Scene sceneMenu;
	VBox root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new VBox(25);
		if (GameSettings.playerName.equals("Name")) {
			sceneMenu = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		} else {
			sceneMenu.setRoot(new GameMenu());
		}
		stageMenu = primaryStage;

		root.setPadding(new Insets(25));

		Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.setVolume(0.5);

		TextField input = new TextField();
		input.setPromptText("Enter your name");
		input.setStyle(
				"-fx-background-color: rgba(15, 143, 160,.5); -fx-prompt-text-fill: #fff; -fx-text-fill: #fff; -fx-corner-radius: 40; "
						+ "-fx-background-insets: 0; -fx-background-radius: 5; -fx-border-color: #0F8FA0;  -fx-border-width: 3;");

		input.setMaxWidth(300);
		input.setFont(Font.font("Dubai", 33));

		Button next = new Button("Next");
		next.setFont(Font.font("Dubai", 33));
		next.setMaxSize(150, 50);
		next.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));

		next.setOnMouseEntered(e -> {
			next.setBackground(
					new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), Insets.EMPTY)));
			next.setStyle("-fx-cursor: hand; -fx-text-fill: white;");

		});

		next.setOnMouseExited(e -> {
			next.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
			next.setStyle("-fx-text-fill: black;");
		});

		next.setOnAction(e -> {
			if (!input.getText().isEmpty())
				GameSettings.playerName = input.getText();
			sceneMenu.setRoot(new GameMenu());
			stageMenu.setScene(sceneMenu);
		});

		TranslateTransition translateText = new TranslateTransition(Duration.millis(2500), input);
		translateText.setFromY(-300);
		translateText.setToY(0);

		TranslateTransition translateButton = new TranslateTransition(Duration.millis(2500), next);
		translateButton.setFromY(300);
		translateButton.setToY(0);

		SequentialTransition seq = new SequentialTransition(translateText, translateButton);
		seq.play();

		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(input, next);
		root.setStyle("-fx-background-image: url('Images/menuBackground.jpg'); "
				+ "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");

		stageMenu.setScene(sceneMenu);
		stageMenu.setTitle("Welcome to IO Game");
		stageMenu.centerOnScreen();
		stageMenu.show();

		sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				sceneMenu.setRoot(new GameMenu());
			}
		});
	}

}