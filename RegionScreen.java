import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * defines the view of a region
 */
public class RegionScreen extends GridPane {

    private Stage stage;
    private Player player;
    private Region region;

    /**
     * displays info about a region
     * @param stage current stage
     * @param player current player
     * @param region region being displayed
     */
    public RegionScreen(Stage stage, Player player, Region region) {
        this.stage = stage;
        this.player = player;
        this.region = region;

        setBackground(SpaceTrader.BACKGROUND);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        List<Node> nodeList = new ArrayList<>();

        Text nameLabel = Util.generateText("Name: ", 20, Color.WHITE);
        Util.setIndex(this, nameLabel, 0, 0);
        nodeList.add(nameLabel);

        Text techLabel = Util.generateText("Technology Level:  ", 20, Color.WHITE);
        Util.setIndex(this, techLabel, 1, 0);
        nodeList.add(techLabel);

        Text descriptionLabel = Util.generateText("Description: ", 20, Color.WHITE);
        Util.setIndex(this, descriptionLabel, 2, 0);
        nodeList.add(descriptionLabel);

        Text regionName = Util.generateText(region.getName(), 20, Color.WHITE);
        Util.setIndex(this, regionName, 0, 1);
        nodeList.add(regionName);

        Text regionTechLevel = Util.generateText(region.getTechLevel(), 20, Color.WHITE);
        Util.setIndex(this, regionTechLevel, 1, 1);
        nodeList.add(regionTechLevel);

        Text regionDescription = Util.generateText(region.getDescription(), 20, Color.WHITE);
        regionDescription.setWrappingWidth(300);
        Util.setIndex(this, regionDescription, 2, 1);
        nodeList.add(regionDescription);

        /*
        Text locationLabel = Util.generateText("Location: ", 24, Color.WHITE);
        Util.setIndex(this, locationLabel, 3, 0);
        nodeList.add(locationLabel);

        String loc = "X: " + region.getX() + "   Y: " + region.getY();
        Text regionLocation = Util.generateText(loc, 24, Color.WHITE);
        Util.setIndex(this, regionLocation, 3, 1);
        nodeList.add(regionLocation);
         */
        //

        Button marketButton = new Button("View Market");
        marketButton.setPrefWidth(100);
        marketButton.setPrefHeight(35);
        marketButton.setOnAction(e -> {
            marketButtonPressed();
        });
        Util.setIndex(this, marketButton, 8, 1);
        setHalignment(marketButton, HPos.CENTER);
        nodeList.add(marketButton);

        Button universeButton = new Button("View Universe");
        universeButton.setPrefWidth(100);
        universeButton.setPrefHeight(35);
        universeButton.setOnAction(e -> {
            universeButtonPressed();
        });
        Util.setIndex(this, universeButton, 8, 0);
        nodeList.add(universeButton);

        Button shipButton = new Button("View Ship");
        shipButton.setPrefWidth(100);
        shipButton.setPrefHeight(35);
        shipButton.setOnAction(e -> loadShipScreen(this.player.getShip()));

        Util.setIndex(this, shipButton, 8, 2);
        nodeList.add(shipButton);

        getChildren().addAll(nodeList);
    }

    private void marketButtonPressed() {
        MarketScreen screen = new MarketScreen(stage, player, region);
        getScene().setRoot(screen);
    }

    /**
     * helper method to transition to the universe screen
     */
    private void universeButtonPressed() {
        Universe universe = new Universe(stage, player, region);
        getScene().setRoot(universe);
    }

    private void loadShipScreen(Ship ship) {
        ShipScreen screen = new ShipScreen(ship, this.player, this.stage, this.region);
        getScene().setRoot(screen);
    }

}
