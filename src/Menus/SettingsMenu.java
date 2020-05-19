package Menus;

import java.util.ArrayList;

import Constants.GameSettings;
import Constants.Sounds;
import Start.Start;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Setting menu class.
 * Here users can change the game settings
 *
 */
public class SettingsMenu extends VBox {
	String[] languages = { "English", "Français", "العربية", "Shqip", "Italiano", "Türkçe", "Español", "Deutsch" };

	String[][] translation = GameSettings.SETTINGS_LANGUAGE;
	public static int language = 0;

	public SettingsMenu() {
		ArrayList<Label> labelsList = new ArrayList<Label>();
		// Game Music
		labelsList.add(new Label(translation[language][1]));
		// Game Sound
		labelsList.add(new Label(translation[language][2]));
		// Select a Language
		labelsList.add(new Label(translation[language][3]));
		// Music Volume
		labelsList.add(new Label(translation[language][4]));
		// Sound Volume
		labelsList.add(new Label(translation[language][5]));

		for (Label label : labelsList) {
			label.setFont(Font.font("Impact", 24));
		}

		Button back = new Button(translation[language][0]);
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

		CheckBox bgMusicCheck = new CheckBox();
		bgMusicCheck.setSelected(GameSettings.backgroundMusic);

		bgMusicCheck.setOnAction(e -> {
			if (bgMusicCheck.isSelected()) {
				GameSettings.backgroundMusic = true;
				Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.play();
			} else {
				GameSettings.backgroundMusic = false;
				Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.pause();
			}
		});

		ComboBox<String> langBox = new ComboBox<String>(FXCollections.observableArrayList(languages));
		langBox.setValue(languages[language]);
		langBox.setOnAction(e -> {
			language = langBox.getSelectionModel().getSelectedIndex();
			Start.sceneMenu.setRoot(new SettingsMenu());
		});

		CheckBox gameSoundsCheck = new CheckBox();
		gameSoundsCheck.setSelected(GameSettings.gameSounds);

		gameSoundsCheck.setOnAction(e -> {
			if (gameSoundsCheck.isSelected()) {
				GameSettings.gameSounds = true;
			} else {
				GameSettings.gameSounds = false;
			}
		});

		HBox soundsContainer = new HBox(10);

		HBox langContainer = new HBox(10);

		HBox volumeMusicContainer = new HBox(10);
		HBox volumeSoundContainer = new HBox(10);

		Slider musicSlider = new Slider(0, 10, Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.getVolume() * 10);
		Slider soundSlider = new Slider(0, 10, GameSettings.soundVolume * 10);

		musicSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				Sounds.BACKGROUND_MEUSIC_MEDIAPLAYER.setVolume(newValue.intValue() / 10.0);
			}
		});

		soundSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				GameSettings.soundVolume = newValue.intValue() / 10.0;
				Sounds.PLAYER_JUMP_MEDIAPLAYER.setVolume(newValue.intValue() / 10.0);
				Sounds.MENU_HOVER_MEDIAPLAYER.setVolume(newValue.intValue() / 10.0);
			}
		});

		// If Arabic choosen then change the order. (Right to Left)
		if (language == 2) {
			soundsContainer.getChildren().addAll(bgMusicCheck, labelsList.get(0), gameSoundsCheck, labelsList.get(1));
			langContainer.getChildren().addAll(langBox, labelsList.get(2));
			this.setAlignment(Pos.TOP_RIGHT);
			volumeMusicContainer.getChildren().addAll(musicSlider, labelsList.get(3));
			volumeSoundContainer.getChildren().addAll(soundSlider, labelsList.get(4));

			musicSlider.setRotate(180);
			soundSlider.setRotate(180);
		} else {
			soundsContainer.getChildren().addAll(labelsList.get(0), bgMusicCheck, labelsList.get(1), gameSoundsCheck);
			langContainer.getChildren().addAll(labelsList.get(2), langBox);
			volumeMusicContainer.getChildren().addAll(labelsList.get(3), musicSlider);
			volumeSoundContainer.getChildren().addAll(labelsList.get(4), soundSlider);
			this.setAlignment(Pos.TOP_LEFT);

		}

		soundsContainer.setAlignment(Pos.CENTER);
		langContainer.setAlignment(Pos.CENTER);
		volumeMusicContainer.setAlignment(Pos.CENTER);
		volumeSoundContainer.setAlignment(Pos.CENTER);

		this.getChildren().addAll(back, langContainer, soundsContainer, volumeMusicContainer, volumeSoundContainer);
		this.setStyle("-fx-background-image: url('Images/menuBackground.jpg'); "
				+ "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");

		this.setSpacing(20);
		VBox.setMargin(back, new Insets(10, 10, 100, 10));
		Start.sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				Start.sceneMenu.setRoot(new GameMenu());
			}
		});
	}

}
