package Coins;

import java.util.ArrayList;

import Constants.GameSettings;
import Constants.Sounds;
import Controlers.Game;
import Controlers.Level;
import Lives.Life;
import Model.GameModel;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;

/**
 * This class creates coins. Coins can be collected by players. 5 Coins are converted to 1 Life.
 */
public class Coin extends GameModel {
	private static ArrayList<ImageView> coins = Level.coins;

	public static int collectedCoinsNr = 0;
	public static int collectedCoinsLvl = 0;
	/**
	 * Constructor to crate coins.
	 * @param x the X layout on the screen
	 * @param y the Y layout on the screen
	 * @param url the url of the image of the coin
	 */
	public Coin(int x, int y, String url) {
		super(x, y, url);
	}
	/**
	 * This code is run in Controlers/Game.
	 * It rotates the coins.
	 */
	public static void checkCoins() {
		if (coins.size() != 0) {

			for (ImageView coin : coins) {
				coin.setRotationAxis(Rotate.Y_AXIS);
				coin.setRotate(coin.getRotate() - 5);

				/**
				 * 01-04-2019
				 */
				if (coin.getBoundsInParent().intersects(Game.player.getBoundsInParent())) {
					if (GameSettings.gameSounds) {
						// TRY NOT TO CREATE A NEW MEDIAPLAYER
						Sounds.COIN_MEDIAPLAYER = new MediaPlayer(Sounds.COIN_MEDIA);
						Sounds.COIN_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
						Sounds.COIN_MEDIAPLAYER.play();
					}
					if (++collectedCoinsLvl % 5 == 0) {
						Level.lifeNr.setText("X" + ++Life.collectedLivesNr);
					}
					Game.returnLevel().removeChild(coin);
					coins.remove(coin);
					collectedCoinsNr++;
					Level.coinNr.setText("X" +collectedCoinsLvl);
					break;
				}

			}
		}
	}


}
