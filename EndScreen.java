import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class EndScreen extends VBox {

    private Stage stage;
    private boolean wonGame;

    /**
     * constructor
     * @param stage the stage holding elements on the main menu
     * @param wonGame true if player won the game
     */
    public EndScreen(Stage stage, boolean wonGame) {
        this.stage = stage;
        this.wonGame = wonGame;

        setBackground(SpaceTrader.BACKGROUND);

        setAlignment(Pos.CENTER);

        List<Node> nodeList = new ArrayList<>();

        if (wonGame) {
            Text winLabel = Util.generateText("You Won!\n", 50, Color.WHITE);
            nodeList.add(winLabel);
        } else {
            Text lostLabel = Util.generateText("You Lost!\n", 50, Color.WHITE);
            nodeList.add(lostLabel);
        }
        Text creditsLabel = Util.generateText("Credits", 40, Color.WHITE);
        nodeList.add(creditsLabel);
        Text nameLabel1 = Util.generateText("\nAkshat Nadella", 20, Color.WHITE);
        nodeList.add(nameLabel1);
        Text nameLabel2 = Util.generateText("Ishawn Gullapalli", 20, Color.WHITE);
        nodeList.add(nameLabel2);
        Text nameLabel3 = Util.generateText("John Leal", 20, Color.WHITE);
        nodeList.add(nameLabel3);
        Text nameLabel4 = Util.generateText("Asa Harbin\n\n\n", 20, Color.WHITE);
        nodeList.add(nameLabel4);

        Button newGame = new Button("Start New Game");
        newGame.setPrefSize(150, 50);
        newGame.setOnAction(event -> {
            for (Region reg : Region.values()) {
                reg.setVisited(false);
                reg.setMarket(new Market(reg));
            }
            ConfigScreen configScreen = new ConfigScreen(stage);
            stage.getScene().setRoot(configScreen);
        });
        nodeList.add(newGame);

        getChildren().addAll(nodeList);
    }
}
