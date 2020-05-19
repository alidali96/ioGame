package Keys;
import Constants.GameSettings;
import Constants.Sounds;
import Controlers.Game;
import Controlers.Level;
import Model.GameModel;
import javafx.scene.media.MediaPlayer;
/**
 * Class to create Key.
 * Player should collect a key, in order to open the gate and then finish the level
 *
 */
public class Key extends GameModel{
	public static boolean keyIsReached = true;

	public Key(int x, int y, String url) {
		super(x, y, url);
	}
	
	//create a function to control the key 
	public static void checkKeys() {
		
		//set animation for the keys in the game 
		Level.key.setRotate(Level.key.getRotate() + 2);
		
		//check if the player reach the key
		if(Game.player.getBoundsInParent().intersects(Level.key.getBoundsInParent())) {
			
			//delete the key once it is reached
			Game.returnLevel().removeChild(Level.key);
			
			Level.g.setOpacity(1);
			
			//play KEY_MEDIA
			//Sunday April 14
			if (GameSettings.gameSounds && keyIsReached) {
				Sounds.KEY_MEDIAPLAYER = new MediaPlayer(Sounds.KEY_MEDIA);
				Sounds.KEY_MEDIAPLAYER.setVolume(GameSettings.soundVolume);
				Sounds.KEY_MEDIAPLAYER.play();
			}
			
			keyIsReached = false;
		}
	}

}