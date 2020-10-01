import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * main menu of the game
 */
public class MainMenu extends VBox {

    private Stage stage;

    /**
     * constructor
     * @param stage the stage holding elements on the main menu
     */
    public MainMenu(Stage stage) {
        this.stage = stage;

        setBackground(SpaceTrader.BACKGROUND);

        setAlignment(Pos.CENTER);

        Text titleText = new Text("Space Trader");
        titleText.setFont(new Font(45));
        titleText.setFill(Color.WHITE);

        Button button = new Button("Start Game");
        button.setPrefWidth(100);
        button.setPrefHeight(50);
        button.setOnAction(event -> {
            startGamePressed();
        });

        getChildren().addAll(titleText, button);
        setSpacing(65);
    }

    /**
     * action when game started
     */
    private void startGamePressed() {
        ConfigScreen configScreen = new ConfigScreen(stage);
        stage.getScene().setRoot(configScreen);
    }

}
