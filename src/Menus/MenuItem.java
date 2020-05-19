package Menus;

import Constants.GameSettings;
import Constants.Sounds;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
/**
 * Each menu item class
 *
 */
public class MenuItem extends Label {

	public MenuItem(String text) {
		super(text);
		this.setTextFill(Color.CRIMSON);

		this.setFont(Font.font("Dubai", 36));

		this.setOnMouseEntered(e -> {
			this.isActive(true);
			this.setStyle("-fx-cursor: hand;");
			Thread thread = new Thread(new PlaySound());
			thread.start();
		});

		this.setOnMouseExited(e -> {
			this.isActive(false);
			stopSound();
		});
	}

	public void isActive(boolean active) {
		if (active) {
			this.setTextFill(Color.WHITE);
			this.setBackground(new Background(
					new BackgroundFill(Color.rgb(15, 143, 160, .8), new CornerRadii(3), new Insets(0, -50, 0, -50))));
		} else {
			this.setTextFill(Color.CRIMSON);
			this.setBackground(null);
			this.setScaleX(1);
			this.setScaleY(1);
		}
	}

	private void stopSound() {
		Sounds.MENU_HOVER_MEDIAPLAYER.stop();
	}

	class PlaySound implements Runnable {

		@Override
		public void run() {
			if (GameSettings.gameSounds) {
				Sounds.MENU_HOVER_MEDIAPLAYER.stop();
				Sounds.MENU_HOVER_MEDIAPLAYER.play();
			}
		}

	}
}