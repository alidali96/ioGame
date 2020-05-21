package Blocks;

import Controlers.Level;
import Model.GameModel;
import javafx.scene.image.ImageView;

/**
 * The class to create a bridge. It extends GameModel.
 * The bridge is designed to move and help the player to move fron one place to another. The players should move themselves alongside the bridge to stay over it.
 *
 */
public class Bridge extends GameModel {

	// variables
	private static double dx = 0.03;

	public Bridge(int x, int y, String url) {
		super(x, y, url);
	}

	public Bridge(int x, int y, String url, double width) {
		super(x, y, url);
		setFitWidth(width);
	}

	// create a function to control moving the bridge and check collision
	public  void moveBridge() {
		if (Level.blocks.size() != 0) {

			for (ImageView block : Level.blocks) {
				if (block instanceof Block) {
					setTranslateX(getTranslateX() + dx);
					if (block.getBoundsInParent().intersects(getBoundsInParent())&&block.getTranslateY()==getTranslateY()) {
						dx = -dx;
					}

				}
			}
		}
	}

}
