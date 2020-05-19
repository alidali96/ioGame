package MapBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Constants.Constants;
import Start.Start;

/**
 * Class to create map
 *
 */
public class MapBuilder extends Application {
	boolean hasI, hasO;
	BufferedWriter bw;

	public static void main(String[] args) throws IOException {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String[][] liness = new String[Constants.SCREEN_HEIGHT / Constants.BLOCK_SIZE][Constants.SCREEN_WIDTH
				/ Constants.BLOCK_SIZE];
		Label title = new Label("Options");
		ImageView BG = new ImageView(new Image("Images/menuBackground.jpg"));
		ImageView blocks = new ImageView(new Image("Images/block.png"));
		blocks.setFitWidth(Constants.BLOCK_SIZE - 2);
		blocks.setFitHeight(Constants.BLOCK_SIZE - 4);
		blocks.setUserData("b");
		Tooltip.install(blocks, new Tooltip("Block"));
		ImageView coins = new ImageView(new Image("Images/coin.png"));
		coins.setFitWidth(Constants.BLOCK_SIZE - 2);
		coins.setFitHeight(Constants.BLOCK_SIZE - 4);
		coins.setUserData("c");
		Tooltip.install(coins, new Tooltip("Coin"));
		ImageView pli = new ImageView(new Image("Images/i.png"));
		pli.setFitWidth(Constants.BLOCK_SIZE - 2);
		pli.setFitHeight(Constants.BLOCK_SIZE - 4);
		pli.setUserData("i");
		Tooltip.install(pli, new Tooltip("Player I"));
		ImageView plo = new ImageView(new Image("Images/o.png"));
		plo.setFitWidth(Constants.BLOCK_SIZE - 2);
		plo.setFitHeight(Constants.BLOCK_SIZE - 4);
		plo.setUserData("o");
		Tooltip.install(plo, new Tooltip("Player O"));
		ImageView killer = new ImageView(new Image("Images/saw1.png"));
		killer.setFitWidth(Constants.BLOCK_SIZE - 2);
		killer.setFitHeight(Constants.BLOCK_SIZE - 4);
		killer.setUserData("x");
		Tooltip.install(killer, new Tooltip("Saw"));
		ImageView pendulum = new ImageView(new Image("Images/pendulum2.png"));
		pendulum.setFitWidth(Constants.BLOCK_SIZE - 2);
		pendulum.setFitHeight(Constants.BLOCK_SIZE - 4);
		pendulum.setUserData("p");
		Tooltip.install(pendulum, new Tooltip("Pendulum"));
		ImageView bridge = new ImageView(new Image("Images/bridge.png"));
		bridge.setFitWidth(Constants.BLOCK_SIZE - 2);
		bridge.setFitHeight(Constants.BLOCK_SIZE - 4);
		bridge.setUserData("r");
		Tooltip.install(bridge, new Tooltip("Bridge"));
		ImageView life = new ImageView(new Image("Images/life.png"));
		life.setFitWidth(Constants.BLOCK_SIZE - 2);
		life.setFitHeight(Constants.BLOCK_SIZE - 4);
		life.setUserData("l");
		Tooltip.install(life, new Tooltip("Life"));
		ImageView gate = new ImageView(new Image("Images/gate.png"));
		gate.setFitWidth(Constants.BLOCK_SIZE - 2);
		gate.setFitHeight(Constants.BLOCK_SIZE - 4);
		gate.setUserData("g");
		Tooltip.install(life, new Tooltip("Gate"));
		ImageView key = new ImageView(new Image("Images/key.png"));
		key.setFitWidth(Constants.BLOCK_SIZE - 2);
		key.setFitHeight(Constants.BLOCK_SIZE - 4);
		key.setUserData("k");
		Tooltip.install(life, new Tooltip("Gate"));
		ImageView blank = new ImageView(new Image("Images/blank.png"));
		blank.setFitWidth(Constants.BLOCK_SIZE - 2);
		blank.setFitHeight(Constants.BLOCK_SIZE - 4);
		blank.setUserData("e");
		Tooltip.install(blank, new Tooltip("Blank Space"));
		title.setFont(Font.font("Dubai", 18));
		title.setTextFill(Color.CRIMSON);
		VBox options = new VBox(title, blocks, coins, pli, plo, killer, blank, pendulum, bridge, life, gate, key);
		options.setTranslateY(22);
		options.setSpacing(5);
		Label activeTitle = new Label("Active");
		activeTitle.setFont(Font.font("Dubai", 18));
		activeTitle.setTextFill(Color.CRIMSON);
		activeTitle.setTranslateY(5);
		ImageView active = new ImageView(new Image("Images/blank.png"));
		active.setFitWidth(Constants.BLOCK_SIZE - 2);
		active.setFitHeight(Constants.BLOCK_SIZE - 4);
		Button save = new Button("Save");
		Button clear = new Button("Clear");
		Label areYouSure = new Label("Are you sure you want to clear the map ?");
		areYouSure.setFont(Font.font("Dubai", 18));
		areYouSure.setTextFill(Color.CRIMSON);
		areYouSure.setTranslateY(5);
		Button confirmYes = new Button("Yes");
		Button confirmNo = new Button("No");
		Text message = new Text("");
		HBox menu = new HBox(activeTitle, active, save, clear, message);
		message.setFont(Font.font("Dubai", 18));
		message.setFill(Color.CRIMSON);
		message.setTranslateY(5);
		HBox confirm = new HBox(areYouSure, confirmYes, confirmNo);
		save.setFont(Font.font("Dubai", 18));
		save.setPrefSize(80, 0);
		save.setBackground(
				new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), Insets.EMPTY)));
		save.setTextFill(Color.WHITE);

		confirmYes.setFont(Font.font("Dubai", 18));
		confirmYes.setPrefSize(80, 0);
		confirmYes.setBackground(
				new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), Insets.EMPTY)));
		confirmYes.setTextFill(Color.WHITE);
		save.setOnMouseEntered(e -> {
			save.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
			save.setStyle("-fx-cursor: hand; -fx-text-fill: rgb(15,143,160);");

		});
		save.setOnMouseExited(e -> {
			save.setBackground(
					new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), Insets.EMPTY)));
			save.setStyle("-fx-text-fill: white;");
		});
		save.setOnMousePressed(e -> {
			save.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		});

		clear.setFont(Font.font("Dubai", 18));
		clear.setPrefSize(80, 10);
		clear.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(10), Insets.EMPTY)));
		clear.setTextFill(Color.WHITE);
		clear.setOnMouseEntered(e -> {
			clear.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
			clear.setStyle("-fx-cursor: hand; -fx-text-fill: crimson;");

		});
		clear.setOnMouseExited(e -> {
			clear.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(10), Insets.EMPTY)));
			clear.setStyle("-fx-text-fill: white;");
		});
		clear.setOnMousePressed(e -> {
			clear.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		});
		confirmYes.setOnMouseEntered(e -> {
			confirmYes
					.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
			confirmYes.setStyle("-fx-cursor: hand; -fx-text-fill: rgb(15,143,160);");

		});
		confirmYes.setOnMouseExited(e -> {
			confirmYes.setBackground(
					new Background(new BackgroundFill(Color.rgb(15, 143, 160), new CornerRadii(10), Insets.EMPTY)));
			confirmYes.setStyle("-fx-text-fill: white;");
		});
		confirmYes.setOnMousePressed(e -> {
			confirmYes
					.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		});

		confirmNo.setFont(Font.font("Dubai", 18));
		confirmNo.setPrefSize(80, 10);
		confirmNo.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(10), Insets.EMPTY)));
		confirmNo.setTextFill(Color.WHITE);
		confirmNo.setOnMouseEntered(e -> {
			confirmNo.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
			confirmNo.setStyle("-fx-cursor: hand; -fx-text-fill: crimson;");

		});
		confirmNo.setOnMouseExited(e -> {
			confirmNo.setBackground(
					new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(10), Insets.EMPTY)));
			confirmNo.setStyle("-fx-text-fill: white;");
		});
		confirmNo.setOnMousePressed(e -> {
			confirmNo.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		});
		confirm.setSpacing(15);
		menu.setSpacing(15);
		menu.setLayoutX(100);
		Pane root = new Pane(BG);
		Pane allBlocks = new Pane();
		/**
		 * If the player clicks the clear button, alert him
		 */
		clear.setOnAction(cl -> {
			menu.getChildren().add(confirm);
		});

		/**
		 * If the player confirmed clear, clear all the screen
		 */
		confirmYes.setOnAction(clearEvent -> {
			for (Node block : allBlocks.getChildren()) {
				((ImageView) block).setImage(new Image("Images/blank.png"));
				block.setUserData("e");
			}
			// return back the players
			int indxi = options.getChildren().indexOf(pli);
			ImageView tempi = (ImageView) options.getChildren().get(indxi);
			tempi.setDisable(false);
			tempi.setOpacity(1);
			hasI = false;
			int indxo = options.getChildren().indexOf(plo);
			ImageView tempo = (ImageView) options.getChildren().get(indxo);
			tempo.setDisable(false);
			tempo.setOpacity(1);
			hasO = false;
			menu.getChildren().remove(confirm);
		});
		/**
		 * If the player confirmed no clear
		 */
		confirmNo.setOnAction(clearEvent -> {
			menu.getChildren().remove(confirm);
		});
		/**
		 * if the player clicked save the map
		 */
		save.setOnAction(saveEvent -> {
			if (!(hasI || hasO)) {
				message.setText("You should select at least one of the players in order to save the map.");
				return;
			}
			String level = null;
			String levelNew;
			String fileUrl = "";
			if (hasI && hasO) {
				fileUrl = "Levels/Custom/io";
				File repo = new File(fileUrl);
				File[] fileList = repo.listFiles();
				int levelNr = fileList.length;
				levelNr++;
				fileUrl = fileUrl + "/level" + levelNr + ".txt";

			}
			if ((hasI && !hasO) || (!hasI && hasO)) {
				fileUrl = "Levels/Custom/i";
				File repo = new File(fileUrl);
				File[] fileList = repo.listFiles();
				int levelNr = fileList.length;
				levelNr++;
				fileUrl = fileUrl + "/level" + levelNr + ".txt";
			}
			try {
				bw = new BufferedWriter(new FileWriter(new File(fileUrl), true));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (Node block : allBlocks.getChildren()) {
				for (int i = 0; i < Constants.SCREEN_WIDTH / Constants.BLOCK_SIZE; i++) {
					for (int j = 0; j < Constants.SCREEN_HEIGHT / Constants.BLOCK_SIZE; j++) {
						String id = "row" + i + "column" + j;
						if (block.getId().equals(id)) {
							if (block.getUserData() != "null") {
								liness[j][i] = (String) block.getUserData();
							}
						}
					}
				}
			}
			for (int i = 0; i < Constants.SCREEN_HEIGHT / Constants.BLOCK_SIZE; i++) {
				for (int j = 0; j < Constants.SCREEN_WIDTH / Constants.BLOCK_SIZE; j++) {
					level += liness[i][j];
				}
				level += "\n";
			}
			levelNew = level.replaceAll("null", "");
			if (hasO && !hasI) {
				levelNew = levelNew.replaceAll("o", "i");
			}
			try {

				bw.write(levelNew);
				message.setText("File saved sucessfully at " + fileUrl);

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
		for (int i = 0; i < Constants.SCREEN_WIDTH / Constants.BLOCK_SIZE; i++) {
			for (int j = 0; j < Constants.SCREEN_HEIGHT / Constants.BLOCK_SIZE; j++) {
				ImageView rec = new ImageView(new Image("Images/blank.png"));
				rec.setFitWidth(Constants.BLOCK_SIZE - 2);
				rec.setFitHeight(Constants.BLOCK_SIZE - 4);
				rec.setLayoutX(i * (Constants.BLOCK_SIZE - 2) + Constants.BLOCK_SIZE);
				rec.setLayoutY(j * (Constants.BLOCK_SIZE - 4) + Constants.BLOCK_SIZE);
				rec.setId("row" + i + "column" + j);
				rec.setUserData("e");
				allBlocks.getChildren().add(rec);
			}

		}
		for (Node block : options.getChildren()) {
			block.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (block instanceof ImageView) {
						active.setImage(((ImageView) block).getImage());
						active.setUserData(block.getUserData());
					}
				}
			});
		}

		for (Node block : allBlocks.getChildren()) {
			block.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					if (block.getUserData() == "o") {
						int indx = options.getChildren().indexOf(plo);
						ImageView temp = (ImageView) options.getChildren().get(indx);
						temp.setDisable(false);
						temp.setOpacity(1);
						hasO = false;
					}
					if (block.getUserData() == "i") {
						int indx = options.getChildren().indexOf(pli);
						ImageView temp = (ImageView) options.getChildren().get(indx);
						temp.setDisable(false);
						temp.setOpacity(1);
						hasI = false;
					}
					((ImageView) block).setImage(active.getImage());
					block.setUserData(active.getUserData());
					if (active.getUserData() == "i")
						hasI = true;
					if (active.getUserData() == "o")
						hasO = true;
					if (block.getUserData() == "i") {
						active.setImage(new Image("Images/blank.png"));
						active.setUserData("e");
						int indx = options.getChildren().indexOf(pli);
						ImageView temp = (ImageView) options.getChildren().get(indx);
						temp.setDisable(true);
						temp.setOpacity(.6);
					}
					if (block.getUserData() == "o") {
						active.setImage(new Image("Images/blank.png"));
						active.setUserData("e");
						int indx = options.getChildren().indexOf(plo);
						ImageView temp = (ImageView) options.getChildren().get(indx);
						temp.setDisable(true);
						temp.setOpacity(.6);
					}
				}
			});
			block.setOnMouseDragged(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					((ImageView) block).setImage(active.getImage());
					block.setUserData(active.getUserData());
					event.setDragDetect(true);
					if (block.getUserData() == "i" || block.getUserData() == "o") {
						active.setImage(new Image("Images/blank.png"));
						active.setUserData("e");
						int indx = options.getChildren().indexOf(pli);
						ImageView temp = (ImageView) options.getChildren().get(indx);
						temp.setDisable(true);
						temp.setOpacity(.6);
						((ImageView) active).setImage(new Image("Images/blank.png"));
						((ImageView) block).setImage(new Image("Images/blank.png"));
					}

				}
			});
			block.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					block.startFullDrag();
				}
			});
			block.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
				public void handle(MouseDragEvent event) {
					((ImageView) block).setImage(active.getImage());
					block.setUserData(active.getUserData());
				}
			});
		}

		options.setTranslateX(10);
		allBlocks.setTranslateX(20);
		allBlocks.setTranslateY(10);
		menu.setTranslateX(50);
		menu.setTranslateY(5);
		root.getChildren().addAll(allBlocks, options, menu);

		Scene scene = new Scene(root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Build your own map");
		primaryStage.show();

		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ESCAPE) {
				try {
					primaryStage.setScene(Start.sceneMenu);
					primaryStage.setTitle("Welcome to IO Game");
					primaryStage.centerOnScreen();
				} catch (Exception x) {
					x.printStackTrace();
				}
			}
		});
	}
}
