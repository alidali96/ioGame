package Obstacles;

import java.util.ArrayList;

import Controlers.Level;
import javafx.scene.Node;

/**
 * saw class
 *
 */
public class Saw extends Obstacles {
	private static ArrayList<Node> obstacles = Level.obstacles;

	public Saw(int x, int y, String url) {
		super(x, y, url);
	}

	public static void animation() {
		if (obstacles.size() != 0) {
			for (Node obstacle : obstacles) {
				if (obstacle instanceof Saw) {
					obstacle.setRotate(obstacle.getRotate() + 10);
				}
			}
		}

	}

}
