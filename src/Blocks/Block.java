package Blocks;
import Model.GameModel;

/**
 * This class creates blocks where the players can walk and stay over them.
 * It Extends GameModel Class
 *
 */
public class Block extends GameModel{
	/**
	 * Constructor to crate blocks.
	 * @param x the X layout on the screen
	 * @param y the Y layout on the screen
	 * @param url the url of the image of the block
	 */
	public Block(int x, int y, String url) {
		super(x, y, url);
	}

}
