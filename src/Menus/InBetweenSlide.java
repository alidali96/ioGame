package Menus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Coins.Coin;
import Constants.Constants;
import Controlers.Game;
import Lives.Life;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * The menu to be shown between the Levels when the player dies or he passed the level
 *
 */
public class InBetweenSlide extends Pane {

	static Label message;
	static TextField coinsCounter;
	static Button moveToNextBt;

	static HBox coinsHbox;
	static ImageView coin;
	static Label coinCounter;

	static HBox livesHbox;
	static ImageView life;
	static Label livesCounter;

	static Label scoreLabel;
	static Label totalScoreLabel;
	static VBox statisticsVbox;


	public InBetweenSlide(String buttonText) {
		Rectangle thisBg = new Rectangle(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		thisBg.setFill(Color.BLACK);
		message = new Label("You lost the level");
		message.setTextFill(Color.WHITE);

		coinsHbox = new HBox();
		coin = new ImageView(new Image("Images/coin.png"));
		coinCounter = new Label("" + Coin.collectedCoinsNr);
		coinCounter.setTextFill(Color.WHITE);
		coinsHbox.getChildren().addAll(coin, coinCounter);
		coinsHbox.setPadding(new Insets(0, 0, 0, 60));
		coinsHbox.setSpacing(20);

		livesHbox = new HBox();
		life = new ImageView(new Image("Images/life.png"));
		livesCounter = new Label("" + Life.collectedLivesNr);
		livesCounter.setTextFill(Color.WHITE);
		livesHbox.getChildren().addAll(life, livesCounter);
		livesHbox.setPadding(new Insets(0, 0, 0, 60));
		livesHbox.setSpacing(20);

		scoreLabel = new Label("Score: ");
		scoreLabel.setText(String.format("Score: %.0f", Game.score));
		scoreLabel.setTextFill(Color.WHITE);

		totalScoreLabel = new Label("Total Score: ");
		totalScoreLabel.setText(String.format("Total Score: %.0f", Game.totalScore));
		totalScoreLabel.setTextFill(Color.WHITE);

		moveToNextBt = new Button(buttonText);
		moveToNextBt.setMinWidth(150);

		statisticsVbox = new VBox(message, coinsHbox, livesHbox, scoreLabel, totalScoreLabel, moveToNextBt);
		statisticsVbox.setSpacing(10);
		statisticsVbox.setLayoutX(550);
		statisticsVbox.setLayoutY(200);
		statisticsVbox.setAlignment(Pos.CENTER);

		VBox.setMargin(moveToNextBt, new Insets(50, 0, 0, 0));

		this.getChildren().addAll(thisBg, statisticsVbox);

		this.minWidth(Constants.SCREEN_WIDTH);
		this.minHeight(Constants.SCREEN_HEIGHT);

		thisBg.setOpacity(0.8);

		try {
			Font menuFont = Font.loadFont(new FileInputStream(new File("Fonts/NotoSans-Light.ttf")), 22);
			message.setFont(menuFont);
			livesCounter.setFont(menuFont);
			coinCounter.setFont(menuFont);
			moveToNextBt.setFont(menuFont);
			scoreLabel.setFont(menuFont);
			totalScoreLabel.setFont(menuFont);
		} catch (FileNotFoundException e1e) {
			e1e.printStackTrace();
		}
		moveToNextBt.setFont(Font.font(18));
		moveToNextBt.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
		moveToNextBt.setOnMouseEntered(e -> {
			moveToNextBt.setStyle("-fx-cursor: hand;-fx-text-fill:#fff;");
			moveToNextBt.setBackground(
					new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(15), Insets.EMPTY)));
		});
		moveToNextBt.setOnMouseExited(e -> {
			moveToNextBt
					.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
			moveToNextBt.setStyle("-fx-text-fill:#000;");
		});
	}

	public static Label getMessage() {
		return message;
	}

	public static TextField getCoinsCounter() {
		return coinsCounter;
	}

	public static Button getMoveToNextBt() {
		return moveToNextBt;
	}

}
