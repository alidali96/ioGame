package Controlers;

import java.util.ArrayList;

import Blocks.Block;
import Constants.Constants;
import Constants.GameSettings;
import Constants.Sounds;
import Model.GameModel;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer.Status;

public class Player extends GameModel {
	private boolean isAlive = true;
	private Point2D velocity = new Point2D(0, 0);
	private boolean jump;
	private ArrayList<GameModel> blocks = Level.blocks;

	public Player(int x, int y, String url) {
		super(x, y, url);
	}

	/**
	 * Function to move the player horizontally
	 * 
	 * @param value how much pixels should the player move. positive or negative
	 */
	public void goHorizontal(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (GameModel block : blocks) {
				if (getBoundsInParent().intersects(block.getBoundsInParent())) {
					if (movingRight) {
						if (getTranslateX() + getBoundsInParent().getWidth() == block.getTranslateX()
								&& getTranslateY() <= block.getTranslateY() + block.getFitWidth() - 2) {
							return;
						}
					} else {
						if (getTranslateX() == block.getTranslateX() + block.getBoundsInParent().getWidth()
								&& getTranslateY() <= block.getTranslateY() + block.getFitWidth() - 2) {
							return;
						}
					}
				}
			}
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
		}
	}

	/**
	 * Function to move the player vertical
	 * 
	 * @param value how much pixels should the player move. positive or negative
	 */
	public void goVertical(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i < Math.abs(value); i++) {
			for (GameModel block : blocks) {
				if (getBoundsInParent().intersects(block.getBoundsInParent())
						&& getTranslateX() - block.getTranslateX() < block.getFitWidth()
						&& getTranslateX() - block.getTranslateX() > -block.getFitWidth()) {
					if (movingDown) {
						if (getTranslateY() + getBoundsInParent().getHeight() == block.getTranslateY()) {
							setTranslateY(getTranslateY() - 1);
							jump = true;
							return;
						}
					} else {
						if (getTranslateY() <= block.getTranslateY() + block.getBoundsInParent().getHeight() + 1) {
							velocity = new Point2D(0, 0);
							return;
						}
					}
				}
			}
			setTranslateY(getTranslateY() + (movingDown ? 1 : -1));
		}
	}
	/**
	 * Make the player jump
	 * 
	 */
	public void jumpPlayer() {
		if (jump) {
			if (GameSettings.gameSounds) {
				if (Sounds.PLAYER_JUMP_MEDIAPLAYER.getStatus() == Status.PLAYING) {
					Sounds.PLAYER_JUMP_MEDIAPLAYER.stop();
					Sounds.PLAYER_JUMP_MEDIAPLAYER.play();
				} else {
					Sounds.PLAYER_JUMP_MEDIAPLAYER.play();
				}
				Sounds.PLAYER_JUMP_MEDIAPLAYER.play();
			}
			velocity = velocity.add(0, -20);
			jump = false;
		}
	}

	public void gravity() {
		if (velocity.getY() < 5) {
			velocity = velocity.add(0, 1);
		}
		this.goVertical((int) velocity.getY());
	}

	/**
	 * Added 01-04-2019
	 */
	public void move() {

		this.goHorizontal((int) (velocity.getX()));

		if (velocity.getX() > 0) {
			velocity = velocity.add(-0.5, 0);
		}
		if (velocity.getX() < 0) {
			velocity = velocity.add(0.5, 0);
		}

		// To not go out of screen
		if (getTranslateX() < 0)
			setTranslateX(0);
		if (getTranslateX() + Constants.PLAYER_SIZE > Constants.SCREEN_WIDTH)
			setTranslateX(Constants.SCREEN_WIDTH - Constants.PLAYER_SIZE);
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Point2D velocity) {
		this.velocity = velocity;
	}

	public void addVelocity(int x, int y) {
		this.velocity = this.velocity.add(x, y);
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

}
