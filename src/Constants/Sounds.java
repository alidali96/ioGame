package Constants;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class to store the music url for each media player
 *
 */
public class Sounds {
	public static Media MENU_HOVER_MEDIA = new Media(new File("Music/menu.wav").toURI().toString());
	public static MediaPlayer MENU_HOVER_MEDIAPLAYER = new MediaPlayer(MENU_HOVER_MEDIA);

	public static Media BACKGROUND_MUSIC_MEDIA = new Media(
			new File("Music/bg.mp3").toURI().toString());
	public static MediaPlayer BACKGROUND_MEUSIC_MEDIAPLAYER = new MediaPlayer(BACKGROUND_MUSIC_MEDIA);


	public static Media COIN_MEDIA = new Media(new File("Music/coin.mp3").toURI().toString());
	public static MediaPlayer COIN_MEDIAPLAYER = new MediaPlayer(COIN_MEDIA);

	public static Media PLAYER_JUMP_MEDIA = new Media(new File("Music/jump.mp3").toURI().toString());
	public static MediaPlayer PLAYER_JUMP_MEDIAPLAYER = new MediaPlayer(PLAYER_JUMP_MEDIA);

	//New sounds effects added to the game
	//Sunday April 14
	public static Media MEETING_MEDIA = new Media(new File("Music/theymet.mp3").toURI().toString());
	public static MediaPlayer MEETING_MEDIAPLAYER = new MediaPlayer(MEETING_MEDIA);

	public static Media REACHED_GATE_MEDIA = new Media(new File("Music/reachgate.mp3").toURI().toString());
	public static MediaPlayer REACHED_GATE_MEDIAPLAYER = new MediaPlayer(REACHED_GATE_MEDIA);

	public static Media DIE_MEDIA = new Media(new File("Music/loose.mp3").toURI().toString());
	public static MediaPlayer DIE_MEDIAPLAYER = new MediaPlayer(DIE_MEDIA);

	public static Media LIFE_MEDIA = new Media(new File("Music/heartcollect.mp3").toURI().toString());
	public static MediaPlayer LIFE_MEDIAPLAYER = new MediaPlayer(LIFE_MEDIA);

	public static Media KEY_MEDIA = new Media(new File("Music/key.mp3").toURI().toString());
	public static MediaPlayer KEY_MEDIAPLAYER = new MediaPlayer(KEY_MEDIA);

}
