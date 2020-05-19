package Gates;

import Controlers.Level;
import Model.GameModel;

public class Gate extends GameModel {

	public Gate(int x, int y, String url) {
		super(x, y, url);
	}

	public static void deactivateGate() {
		// check if the player reach the gate then put the gate away from the reach of
		// the player
		Level.g.setTranslateX(-1000);
	}
}
