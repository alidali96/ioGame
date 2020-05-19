package Menus;

import java.io.File;

import Constants.Constants;
import Constants.GameSettings;
import Controlers.Game;
import Start.Start;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CustomMenu extends FlowPane {
	VBox container = new VBox(5);

	public CustomMenu() {

		File folder = new File(Constants.LEVEL_PATH);
		File[] levels = folder.listFiles();

		for (int i = 0; i < levels.length; i++) {

			MenuItem level = new MenuItem(GameSettings.MAIN_LANGUAGE[SettingsMenu.language][7] + " " + (i + 1));
			level.setMinWidth(190);
			level.setPadding(new Insets(5, 5, 5, 5));
			level.setStyle(
					"-fx-border-color: #0F8FA0;  -fx-border-width: 1;-fx-background-color: rgba(15,143,160,.5);");
			level.setFont(Font.font(30));
			level.setOnMouseEntered(e -> {
				level.setStyle(
						"-fx-background-color: #0F8FA0; -fx-prompt-text-fill: #fff; -fx-text-fill: #fff; -fx-corner-radius: 20; "
								+ "-fx-background-insets: 0; -fx-background-radius: 5; -fx-border-color: #0F8FA0;  -fx-border-width: 1;-fx-cursor:hand;");

			});
			level.setOnMouseClicked(e -> {
				Game.levelNr = this.getChildren().indexOf(level) + 1;
				Game game = new Game();
				try {
					game.start(Start.stageMenu);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});

			level.setOnMouseExited(ee -> {
				level.setStyle(
						"-fx-border-color: #0F8FA0;  -fx-border-width: 1;-fx-background-color: rgba(15,143,160,.5);");
			});

			this.getChildren().add(level);
			FlowPane.setMargin(level, new Insets(5, 5, 5, 5));
		}

		Start.sceneMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				Start.sceneMenu.setRoot(new GameMenu());
				Start.stageMenu.setScene(Start.sceneMenu);
			}
		});

		// If Arabic choosen change the layout (Right to Left)
		if (SettingsMenu.language == 2)
			this.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	}

}
