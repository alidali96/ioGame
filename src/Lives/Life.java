package Lives;

import Constants.GameSettings;
import Constants.Sounds;
import Controlers.Game;
import Controlers.Level;
import Model.GameModel;
import javafx.scene.media.MediaPlayer;

/**
 * Class to create lives.
 * The player stays alive until he has lives available on his inventory.
 *
 */
public class Life extends GameModel {
	public static int collectedLivesNr = 2;
	public static boolean removeL;

	public Life(int x, int y, String url) {
		super(x, y, url);

	}

	/**
	 * Check if the player collected the life
	 * Update the text in level top menu
	 */
	public static void checkLife() {

		if (Level.life.getBoundsInParent().intersects(Game.player.getBoundsInParent())) {
			Game.firstRound = false;

			// play LIFE_MEDIA
			// Sunday April 14
			if (GameSettings.gameSounds) {
				Sounds.LIFE_MEDIAPLAYER = new MediaPlayer(Sounds.LIFE_MEDIA);
				Sounds.LIFE_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
				Sounds.LIFE_MEDIAPLAYER.play();
			}

			Game.returnLevel().removeChild(Level.life);
			collectedLivesNr++;
			Level.lifeNr.setText("X" + collectedLivesNr);
			removeL = true;
		}

	}

}
