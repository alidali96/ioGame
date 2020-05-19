package Menus;

import java.util.ArrayList;
import java.util.Collections;

import Constants.GameSettings;
import Start.Start;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CreditMenu extends VBox {
	ArrayList<Label> credits = new ArrayList<>();

	public CreditMenu() {
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
			back.setStyle("-fx-cursor: hand; -fx-Label-fill: white;");
		});

		back.setOnMouseExited(e -> {
			back.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), new Insets(10))));
			back.setStyle("-fx-Label-fill: black;");
		});

		credits.add(new Label("Ali"));
		credits.add(new Label("Fadi"));
		credits.add(new Label("Ndriçim"));
		Collections.shuffle(credits);
		credits.add(0, new Label("Special Thanks To: Câi Filiault"));
		credits.add(1, new Label("Developed By"));
		credits.get(1).setUnderline(true);

		VBox box = new VBox(20);
		box.setAlignment(Pos.CENTER);

		for (Label name : credits) {
			name.setFont(Font.font("Dubai", 36));
			name.setTextFill(Color.rgb(15, 143, 160));
			name.setEffect(new MotionBlur(3, 3));
			name.setEffect(new DropShadow(2, 2, 2, Color.WHITE));
			box.getChildren().add(name);
		}

		credits.get(0).setBackground(
				new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), new Insets(-10))));
		credits.get(0).setTextFill(Color.WHITE);

		// if Arabic selected
		if (SettingsMenu.language == 2) {
			this.setAlignment(Pos.TOP_RIGHT);
		} else {
			this.setAlignment(Pos.TOP_LEFT);
		}

		Label creditLabel = new Label("Graphics By: freepic.com\nMusic By: freesound.org");
		creditLabel.setFont(Font.font("Dubai", 25));
		creditLabel.setTextFill(Color.rgb(15, 143, 160));

		VBox credits = new VBox(creditLabel);
		credits.setAlignment(Pos.CENTER);
		VBox.setMargin(credits, new Insets(50));

		this.getChildren().addAll(back, box, credits);
		this.setStyle("-fx-background-image: url('Images/menuBackground.jpg'); "
				+ "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
		this.setSpacing(20);
		VBox.setMargin(back, new Insets(10));

		Start.sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE)
				Start.sceneMenu.setRoot(new GameMenu());
		});
	}
}
