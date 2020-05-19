package Obstacles;

import java.util.ArrayList;
//Fadi: I copied and pasted the whole class

import Constants.Constants;
import Controlers.Game;
import Controlers.Level;
import Model.GameModel;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * THe obstacles class.It extends the game model Every obstacle extends this
 * class
 *
 */
public abstract class Obstacles extends GameModel {

	private static ArrayList<Node> obstacles = Level.obstacles;
	private static boolean hit;

	public Obstacles(int x, int y, String url) {
		super(x, y, url);
	}

	public Obstacles(int x, int y) {
		super(x, y);
	}

	// we may need them as a condition to repeat the current level
	// in case there was a collision with any obstacle
	public static boolean isHit() {
		return hit;
	}

	/**
	 * Check if the player touches the obstacle
	 */
	public static void checkObstacles() {
		if (obstacles.size() != 0) {
			if (obstacles.contains(Level.s)) {
				Saw.animation();
			}
			for (Node obstacle : obstacles) {

				if (obstacle instanceof Circle) {
					Pendulum.swing();
				}

				// add a condition to check if there is any collision with any obstacle in the
				// level
				if (Game.player.getBoundsInParent().intersects(obstacle.getBoundsInParent())
						&& Game.player.getTranslateX() - obstacle.getTranslateX() < Constants.PLAYER_SIZE
						&& Game.player.getTranslateX() - obstacle.getTranslateX() > -Constants.PLAYER_SIZE
						&& Game.player.getTranslateY() - obstacle.getTranslateY() < Constants.PLAYER_SIZE
						&& Game.player.getTranslateY() - obstacle.getTranslateY() > -Constants.PLAYER_SIZE) {
					hit = true;
					Game.player.setAlive(false);
					break;
				}

				// Pendulum
				if (obstacle instanceof Circle) {
					if (Game.player.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
						hit = true;
						Game.player.setAlive(false);
						break;
					}
				}

			}
		}
	}

}
