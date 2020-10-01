import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * defines character screen
 */
public class CharacterScreen extends BorderPane {

    private Stage stage;
    private Player player;

    /**
     * constructor
     * @param stage the stage holding elements on character screen
     * @param player the player that is currently playing
     */
    public CharacterScreen(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        setBackground(SpaceTrader.BACKGROUND);
        gridPane.setHgap(70);
        gridPane.setVgap(25);

        List<Node> nodeList = new ArrayList<>();

        Text nameLabel = Util.generateText("Name", 24, Color.WHITE);
        Util.setIndex(gridPane, nameLabel, 0, 0);
        nodeList.add(nameLabel);

        Text difficultyLabel = Util.generateText("Difficulty", 24, Color.WHITE);
        Util.setIndex(gridPane, difficultyLabel, 0, 1);
        nodeList.add(difficultyLabel);

        Text skillPointsLabel = Util.generateText("Skill Points", 24, Color.WHITE);
        Util.setIndex(gridPane, skillPointsLabel, 0, 2);
        nodeList.add(skillPointsLabel);

        Text creditsLabel = Util.generateText("Credits", 24, Color.WHITE);
        Util.setIndex(gridPane, creditsLabel, 0, 3);
        nodeList.add(creditsLabel);

        Text playerName = Util.generateText(player.getName(), 18, Color.WHITE);
        Util.setIndex(gridPane, playerName, 1, 0);
        nodeList.add(playerName);

        Text playerDifficulty = Util.generateText(player.getDifficulty(), 18, Color.WHITE);
        Util.setIndex(gridPane, playerDifficulty, 1, 1);
        nodeList.add(playerDifficulty);

        List<Node> pointsList = new ArrayList<>();

        Text playerPoints = Util.generateText("Remaining Points:  " + player.getSkillPoints(),
                18, Color.WHITE);
        pointsList.add(playerPoints);

        Text playerPilotPoints = Util.generateText("Pilot Points:  " + player.getPilotPoints(),
                18, Color.WHITE);
        pointsList.add(playerPilotPoints);

        Text playerFighterPoints = Util.generateText("Fighter Points:  "
                + player.getFighterPoints(), 18, Color.WHITE);
        pointsList.add(playerFighterPoints);

        Text playerMerchantPoints = Util.generateText("Merchant Points:  "
                + player.getMerchantPoints(), 18, Color.WHITE);
        pointsList.add(playerMerchantPoints);

        Text playerEngineerPoints = Util.generateText("Engineer Points:  "
                + player.getEngineerPoints(), 18, Color.WHITE);
        pointsList.add(playerEngineerPoints);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(pointsList);
        Util.setIndex(gridPane, vbox, 1, 2);

        Text playerCredits = Util.generateText(String.valueOf(player.getCredits()),
                18, Color.WHITE);
        Util.setIndex(gridPane, playerCredits, 1, 3);
        nodeList.add(playerCredits);

        Button spawnButton = new Button("Spawn");
        spawnButton.setOnAction(e -> initializeUniverse());
        spawnButton.setPrefWidth(85);
        spawnButton.setPrefHeight(35);
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.getChildren().add(spawnButton);

        gridPane.setValignment(playerName, VPos.TOP);
        gridPane.setValignment(playerDifficulty, VPos.TOP);
        gridPane.setValignment(playerCredits, VPos.TOP);
        gridPane.getChildren().addAll(nodeList);
        gridPane.getChildren().add(vbox);

        setCenter(gridPane);
        setBottom(buttonBox);

        setMargin(buttonBox, new Insets(0, 0, 60, 0));
    }

    /**
     * action when player clicks spawn
     */
    private void initializeUniverse() {
        Region r = Region.values()[(int) (Math.random() * Region.values().length)];
        for (int i = 0; i < 10; i++) {
            Region r1 = Region.values()[i];
            for (int j = 0; j < 10; j++) {
                Region r2 = Region.values()[j];
                boolean changed = false;
                while (r1 != r2 && Util.distanceBetween(r1.getX(), r1.getY(), r2.getX(),
                        r2.getY()) <= 3 * 30) {
                    r2.setX(40 + (int) (Math.random() * 561));
                    r2.setY(40 + (int) (Math.random() * 401));
                    changed = true;
                }
                if (changed) {
                    i = 0;
                    break;
                }
            }
        }
        if (!r.isVisited()) {
            Market market = new Market(r);
            market.adjustPrices(player);
            r.setMarket(market);
        }
        r.setVisited(true);
        RegionScreen regionScreen = new RegionScreen(stage, player, r);
        stage.getScene().setRoot(regionScreen);
    }
}
