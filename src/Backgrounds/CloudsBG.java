package Backgrounds;

import Constants.Constants;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
/**
 * This Class will create clouds to the screen.
 * They have Random size, speed and location.
 * How often should add a cloud is controlled by Controlers/Game
 *
 */
public class CloudsBG extends Pane {
	public CloudsBG() {
		double width = (Math.random() * ((250 - 120) + 1)) + 120;
		double Y = (Math.random() * ((350 - 20) + 1)) + 20;
		int time = (int) ((Math.random() * ((25000 - 19000) + 1)) + 19000);
		ImageView img = new ImageView(new Image("Images/cloud1.png"));
		img.setFitWidth(width);
		img.setPreserveRatio(true);
		this.getChildren().add(img);
		img.setTranslateX(Constants.SCREEN_WIDTH + 10);
		img.setTranslateY(Y);
		TranslateTransition tr = new TranslateTransition(Duration.millis(time), img);
		tr.setToX(-300);
		tr.play();
	}
}
