package Obstacles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * 
 * Pendulum class
 *
 */
public class Pendulum extends Obstacles {
	private static double dx;
	private static double dy;
	private static Circle upperBall;
	private static Line thread;
	private static Circle lowerBall;
	private static double currentAngle = 0;
	private static double angleChange = .9;
	static boolean right = true;

	// alter the constructor function of the pendulum
	public Pendulum(int x, int y) {
		super(x, y);
		upperBall = new Circle(x, y, 5);
		thread = new Line();
		thread.setStartX(x);
		thread.setStartY(y);
		lowerBall = new Circle(x, y + 150, 20);
		lowerBall.setFill(Color.RED);

	}

	public static Circle getUpperBall() {
		return upperBall;
	}

	public static Line getThread() {
		return thread;
	}

	public static Circle getLowerBall() {
		return lowerBall;
	}

	// change the name of the function to (swing) instead of animation
	public static void swing() {

		currentAngle = currentAngle + angleChange;
		dx = 150 * Math.sin(Math.toRadians(currentAngle));
		dy = 150 * Math.cos(Math.toRadians(currentAngle));
		thread.setEndX(thread.getStartX() + dx);
		thread.setEndY(thread.getStartY() + dy);
		lowerBall.setCenterX(thread.getEndX());
		lowerBall.setCenterY(thread.getEndY());
		lowerBall.setFill(new ImagePattern(new Image("Images/pendulum2.png")));
		if (right) {
			lowerBall.setRotate(lowerBall.getRotate() - 10);
		} else {
			lowerBall.setRotate(lowerBall.getRotate() + 10);
		}

		// check the angle of movement
		if ((currentAngle > 80) || (currentAngle < -80)) {
			angleChange = -angleChange;

			if (right) {
				right = false;
			} else {
				right = true;
			}

		}
	}
}
