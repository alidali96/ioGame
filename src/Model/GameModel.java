package Model;

import Constants.Constants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * The main game model.
 * Every other model in game extends this class
 *
 */
public abstract class GameModel extends ImageView {
	/**
	 * 
	 * @param x Layout X
	 * @param y Layout Y
	 * @param url Image path url
	 */
	public GameModel(int x, int y, String url) {
		this.setFitWidth(Constants.PLAYER_SIZE);
		this.setFitHeight(Constants.PLAYER_SIZE);
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setImage(new Image(url));
	}

	/*
	 * @param x layout X
	 * @param Y layout Y
	 */
	public GameModel(int x, int y) {
		this.setFitWidth(Constants.PLAYER_SIZE);
		this.setFitHeight(Constants.PLAYER_SIZE);
		this.setTranslateX(x);
		this.setTranslateY(y);
	}

}
