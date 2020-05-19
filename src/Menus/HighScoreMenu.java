package Menus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Constants.GameSettings;
import Start.Start;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
	/**
	 * Creating the high score menu
	 *
	 */
public class HighScoreMenu extends VBox {
	ArrayList<Text> credits = new ArrayList<>();
	boolean color = false;

	public HighScoreMenu() {
		Button back = new Button(GameSettings.SETTINGS_LANGUAGE[SettingsMenu.language][0]);
		back.setOnAction(e -> {
			Start.sceneMenu.setRoot(new GameMenu());
		});

		back.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(10))));
		back.setFont(Font.font("Dubai", 22));
		back.setPadding(new Insets(15, 25, 15, 25));
		back.setOnMouseEntered(e -> {
			back.setBackground(
					new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), new Insets(10))));
			back.setStyle("-fx-cursor: hand; -fx-text-fill: white;");
		});

		back.setOnMouseExited(e -> {
			back.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(10))));
			back.setStyle("-fx-text-fill: black;");
		});

		// if Arabic selected
		if (SettingsMenu.language == 2) {
			this.setAlignment(Pos.TOP_RIGHT);
		} else {
			this.setAlignment(Pos.TOP_LEFT);
		}

		ScrollPane scrollPane = new ScrollPane();

		VBox container = new VBox();
		HBox header = new HBox();
		Label nameLabel = new Label("\tName");
		Label scoreLabel = new Label("Score");
		Label dateLabel = new Label("Date");
		nameLabel.setFont(Font.font("Dubai", FontWeight.BOLD, 26));
		scoreLabel.setFont(Font.font("Dubai", FontWeight.BOLD, 26));
		dateLabel.setFont(Font.font("Dubai", FontWeight.BOLD, 26));
		nameLabel.setPrefWidth(500);
		scoreLabel.setPrefWidth(500);
		dateLabel.setPrefWidth(500);
		nameLabel.setTextFill(Color.WHITE);
		scoreLabel.setTextFill(Color.WHITE);
		dateLabel.setTextFill(Color.WHITE);

		header.setStyle("-fx-background-color:rgb(15, 143, 160)");

		header.getChildren().addAll(nameLabel, scoreLabel, dateLabel);

		container.getChildren().add(header);

		try {
			BufferedReader in = new BufferedReader(new FileReader(new File("Score/high_score.txt")));
			String line = "";
			while ((line = in.readLine()) != null) {

				StringTokenizer value = new StringTokenizer(line, ",");
				HBox scoreBox = new HBox();
				while (value.hasMoreTokens()) {
					Label label = new Label(value.nextToken());
					label.setFont(Font.font("Dubai", 18));
					label.setPrefWidth(500);
					scoreBox.getChildren().add(label);
				}
				if (color) {
					scoreBox.setStyle("-fx-background-color:#efefef");
					color = false;
				} else {
					scoreBox.setStyle("-fx-background-color:white");
					color = true;
				}

				container.getChildren().add(scoreBox);
			}

			in.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setContent(container);
		scrollPane.setPadding(new Insets(15));

		this.getChildren().addAll(back, scrollPane);
		this.setStyle("-fx-background-image: url('Images/menuBackground.jpg'); "
				+ "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
		VBox.setMargin(back, new Insets(10));
		VBox.setMargin(scrollPane, new Insets(25));

		Start.sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE)
				Start.sceneMenu.setRoot(new GameMenu());
		});
	}
}
