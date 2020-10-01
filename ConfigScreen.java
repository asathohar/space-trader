import java.util.ArrayList;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * configuration for screen
 */
public class ConfigScreen extends GridPane {

    private Stage stage;
    private Player player;

    private TextField nameTextField;
    private Text pointsText;
    private Text pilotText;
    private Text fighterText;
    private Text merchantText;
    private Text engineerText;
    private Difficulty difficulty;

    /**
     * constructs a ConfigScreen
     * @param stage the stage holding elements on the config screen
     */
    public ConfigScreen(Stage stage) {
        this.stage = stage;
        player = new Player();

        setBackground(SpaceTrader.BACKGROUND);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints(90);
        getColumnConstraints().addAll(col1, col2, col3);

        List<Node> nodeList = new ArrayList<>();

        // Name

        Text nameLabel = Util.generateText("Name: ", 24, Color.WHITE);
        Util.setIndex(this, nameLabel, 0, 0);
        nodeList.add(nameLabel);

        nameTextField = new TextField();
        Util.setIndex(this, nameTextField, 0, 1);
        setColumnSpan(nameTextField, 3);
        nodeList.add(nameTextField);

        // Difficulty

        difficulty(nodeList);

        // Skill points

        Text pointsLabel = Util.generateText("Skill points: ", 24, Color.WHITE);
        Util.setIndex(this, pointsLabel, 2, 0);
        nodeList.add(pointsLabel);

        pointsText = Util.generateText("20", 24, Color.WHITE);
        Util.setIndex(this, pointsText, 2, 2);
        setHalignment(pointsText, HPos.CENTER);
        nodeList.add(pointsText);

        skillPointAllocation(nodeList);

        // Submit

        Button submit = new Button("Submit");
        submit.setPrefSize(150, 35);
        submit.setOnAction(event -> {
            if (nameTextField.getText() != null && !nameTextField.getText().isEmpty()) {
                submitButtonPressed();
            }
        });
        setMargin(submit, new Insets(40, 0, 0, 0));
        Util.setIndex(this, submit, 8, 0);
        setColumnSpan(submit, 4);
        setHalignment(submit, HPos.CENTER);
        nodeList.add(submit);

        getChildren().addAll(nodeList);
    }

    /**
     * helper method for difficulty settings
     * @param nodeList list containing nodes on this screen
     */
    private void difficulty(List<Node> nodeList) {
        Text difficultyLabel = Util.generateText("Difficulty: ", 24, Color.WHITE);
        Util.setIndex(this, difficultyLabel, 1, 0);
        nodeList.add(difficultyLabel);

        difficulty = Difficulty.NORMAL;
        Text difficultyText = Util.generateText("Normal", 24, Color.WHITE);
        setHalignment(difficultyText, HPos.CENTER);
        Util.setIndex(this, difficultyText, 1, 2);
        nodeList.add(difficultyText);

        Button difficultyDecrease = new Button("-");
        difficultyDecrease.setPrefSize(25, 25);
        difficultyDecrease.setOnAction(event -> {
            if (difficultyText.getText().equals("Normal")) {
                difficultyText.setText("Easy");
                difficulty = Difficulty.EASY;
                pointsText.setText("24");
                resetSkillPoints();
            } else if (difficultyText.getText().equals("Hard")) {
                difficultyText.setText("Normal");
                difficulty = Difficulty.NORMAL;
                pointsText.setText("20");
                resetSkillPoints();
            }
        });
        Util.setIndex(this, difficultyDecrease, 1, 1);
        nodeList.add(difficultyDecrease);

        Button difficultyIncrease = new Button("+");
        difficultyIncrease.setPrefSize(25, 25);
        difficultyIncrease.setOnAction(event -> {
            if (difficultyText.getText().equals("Normal")) {
                difficultyText.setText("Hard");
                difficulty = Difficulty.HARD;
                pointsText.setText("16");
                resetSkillPoints();
            } else if (difficultyText.getText().equals("Easy")) {
                difficultyText.setText("Normal");
                difficulty = Difficulty.NORMAL;
                pointsText.setText("20");
                resetSkillPoints();
            }
        });
        Util.setIndex(this, difficultyIncrease, 1, 3);
        setHalignment(difficultyIncrease, HPos.RIGHT);
        nodeList.add(difficultyIncrease);
    }

    /**
     * helper method for point allocations
     * @param nodeList list containing nodes on this screen
     */
    public void skillPointAllocation(List<Node> nodeList) {
        // Pilot
        Text pilotLabel = Util.generateText("Pilot: ", 24, Color.WHITE);
        Util.setIndex(this, pilotLabel, 3, 0);
        nodeList.add(pilotLabel);

        pilotText = Util.generateText("0", 24, Color.WHITE);
        Util.setIndex(this, pilotText, 3, 2);
        setHalignment(pilotText, HPos.CENTER);
        nodeList.add(pilotText);

        Button pilotDecrease = new Button("-");
        pilotDecrease.setPrefSize(25, 25);
        pilotDecrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int pilotPoints = Integer.parseInt(pilotText.getText());
            if (pilotPoints > 0) {
                pilotText.setText(String.valueOf(pilotPoints - 1));
                pointsText.setText(String.valueOf(points + 1));
            }
        });
        Util.setIndex(this, pilotDecrease, 3, 1);
        nodeList.add(pilotDecrease);

        Button pilotIncrease = new Button("+");
        pilotIncrease.setPrefSize(25, 25);
        pilotIncrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int pilotPoints = Integer.parseInt(pilotText.getText());
            if (points > 0) {
                pilotText.setText(String.valueOf(pilotPoints + 1));
                pointsText.setText(String.valueOf(points - 1));
            }
        });
        Util.setIndex(this, pilotIncrease, 3, 3);
        setHalignment(pilotIncrease, HPos.RIGHT);
        nodeList.add(pilotIncrease);

        // Fighter
        Text fighterLabel = Util.generateText("Fighter: ", 24, Color.WHITE);
        Util.setIndex(this, fighterLabel, 4, 0);
        nodeList.add(fighterLabel);

        fighterText = Util.generateText("0", 24, Color.WHITE);
        Util.setIndex(this, fighterText, 4, 2);
        setHalignment(fighterText, HPos.CENTER);
        nodeList.add(fighterText);

        Button fighterDecrease = new Button("-");
        fighterDecrease.setPrefSize(25, 25);
        fighterDecrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int fighterPoints = Integer.parseInt(fighterText.getText());
            if (fighterPoints > 0) {
                fighterText.setText(String.valueOf(fighterPoints - 1));
                pointsText.setText(String.valueOf(points + 1));
            }
        });
        Util.setIndex(this, fighterDecrease, 4, 1);
        nodeList.add(fighterDecrease);

        Button fighterIncrease = new Button("+");
        fighterIncrease.setPrefSize(25, 25);
        fighterIncrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int fighterPoints = Integer.parseInt(fighterText.getText());
            if (points > 0) {
                fighterText.setText(String.valueOf(fighterPoints + 1));
                pointsText.setText(String.valueOf(points - 1));
            }
        });
        Util.setIndex(this, fighterIncrease, 4, 3);
        setHalignment(fighterIncrease, HPos.RIGHT);
        nodeList.add(fighterIncrease);

        // Merchant
        Text merchantLabel = Util.generateText("Merchant: ", 24, Color.WHITE);
        Util.setIndex(this, merchantLabel, 5, 0);
        nodeList.add(merchantLabel);

        merchantText = Util.generateText("0", 24, Color.WHITE);
        Util.setIndex(this, merchantText, 5, 2);
        setHalignment(merchantText, HPos.CENTER);
        nodeList.add(merchantText);

        Button merchantDecrease = new Button("-");
        merchantDecrease.setPrefSize(25, 25);
        merchantDecrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int merchantPoints = Integer.parseInt(merchantText.getText());
            if (merchantPoints > 0) {
                merchantText.setText(String.valueOf(merchantPoints - 1));
                pointsText.setText(String.valueOf(points + 1));
            }
        });
        Util.setIndex(this, merchantDecrease, 5, 1);
        nodeList.add(merchantDecrease);

        Button merchantIncrease = new Button("+");
        merchantIncrease.setPrefSize(25, 25);
        merchantIncrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int merchantPoints = Integer.parseInt(merchantText.getText());
            if (points > 0) {
                merchantText.setText(String.valueOf(merchantPoints + 1));
                pointsText.setText(String.valueOf(points - 1));
            }
        });
        Util.setIndex(this, merchantIncrease, 5, 3);
        setHalignment(merchantIncrease, HPos.RIGHT);
        nodeList.add(merchantIncrease);

        // Engineer
        Text engineerLabel = Util.generateText("Engineer: ", 24, Color.WHITE);
        Util.setIndex(this, engineerLabel, 6, 0);
        nodeList.add(engineerLabel);

        engineerText = Util.generateText("0", 24, Color.WHITE);
        Util.setIndex(this, engineerText, 6, 2);
        setHalignment(engineerText, HPos.CENTER);
        nodeList.add(engineerText);

        Button engineerDecrease = new Button("-");
        engineerDecrease.setPrefSize(25, 25);
        engineerDecrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int engineerPoints = Integer.parseInt(engineerText.getText());
            if (engineerPoints > 0) {
                engineerText.setText(String.valueOf(engineerPoints - 1));
                pointsText.setText(String.valueOf(points + 1));
            }
        });
        Util.setIndex(this, engineerDecrease, 6, 1);
        nodeList.add(engineerDecrease);

        Button engineerIncrease = new Button("+");
        engineerIncrease.setPrefSize(25, 25);
        engineerIncrease.setOnAction(event -> {
            int points = Integer.parseInt(pointsText.getText());
            int engineerPoints = Integer.parseInt(engineerText.getText());
            if (points > 0) {
                engineerText.setText(String.valueOf(engineerPoints + 1));
                pointsText.setText(String.valueOf(points - 1));
            }
        });
        Util.setIndex(this, engineerIncrease, 6, 3);
        setHalignment(engineerIncrease, HPos.RIGHT);
        nodeList.add(engineerIncrease);
    }

    /**
     * helper method to reset skill points
     */
    private void resetSkillPoints() {
        pilotText.setText("0");
        fighterText.setText("0");
        merchantText.setText("0");
        engineerText.setText("0");
    }

    /**
     * sets action when submit button is pressed
     */
    private void submitButtonPressed() {
        player.setName(nameTextField.getText());
        player.setSkillPoints(Integer.parseInt(pointsText.getText()));
        player.setPilotPoints(Integer.parseInt(pilotText.getText()));
        player.setFighterPoints(Integer.parseInt(fighterText.getText()));
        player.setMerchantPoints(Integer.parseInt(merchantText.getText()));
        player.setEngineerPoints(Integer.parseInt(engineerText.getText()));
        player.setDifficulty(difficulty);
        if (difficulty == Difficulty.EASY) {
            player.setCredits(1500);
        } else if (difficulty == Difficulty.NORMAL) {
            player.setCredits(1000);
        } else {
            player.setCredits(500);
        }
        CharacterScreen characterSheet = new CharacterScreen(stage, player);
        stage.getScene().setRoot(characterSheet);
    }
}
