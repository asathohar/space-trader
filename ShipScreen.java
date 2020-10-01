import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class ShipScreen extends GridPane {

    private Stage stage;
    private Player player;
    private Region region;
    private Ship ship;

    public ShipScreen(Ship ship, Player player, Stage stage, Region region) {
        this.player = player;
        this.stage = stage;
        this.region = region;
        this.ship = ship;

        setBackground(SpaceTrader.BACKGROUND);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        List<Node> nodeList = new ArrayList<>();

        Text nameLabel = Util.generateText("Name: ", 20, Color.WHITE);
        Util.setIndex(this, nameLabel, 0, 0);
        nodeList.add(nameLabel);

        Text name = Util.generateText(player.getName() + "'s Ship", 20, Color.WHITE);
        name.setWrappingWidth(300);
        Util.setIndex(this, name, 0, 1);
        nodeList.add(name);

        Text cargoCapacity = Util.generateText("Cargo Capacity:  ", 20, Color.WHITE);
        Util.setIndex(this, cargoCapacity, 1, 0);
        nodeList.add(cargoCapacity);

        Text cargoCap = Util.generateText(String.valueOf(ship.getCargoCapacity()), 20, Color.WHITE);
        cargoCap.setWrappingWidth(300);
        Util.setIndex(this, cargoCap, 1, 1);
        nodeList.add(cargoCap);

        Text itemInventory = Util.generateText("Item Inventory: ", 20, Color.WHITE);
        Util.setIndex(this, itemInventory, 2, 0);
        nodeList.add(itemInventory);

        String items = "";
        for (Item key : player.getShip().getInventory().keySet()) {
            if (player.getShip().getInventory().getOrDefault(key, 0) > 0) {
                items += key.name() + ": " + player.getShip().getInventory().get(key) + "; ";
            }
        }
        if (items.length() > 2) {
            items = items.substring(0, items.length() - 2);
        }

        Text itemList = Util.generateText(items, 20, Color.WHITE);
        itemList.setWrappingWidth(300);
        Util.setIndex(this, itemList, 2, 1);
        nodeList.add(itemList);

        Text fuelCapacity = Util.generateText("Fuel: ", 20, Color.WHITE);
        Util.setIndex(this, fuelCapacity, 3, 0);
        nodeList.add(fuelCapacity);

        Text fuelCap = Util.generateText(ship.getCurrentFuel() + "/"
                + ship.getFuelCapacity(), 20, Color.WHITE);
        fuelCap.setWrappingWidth(300);
        Util.setIndex(this, fuelCap, 3, 1);
        nodeList.add(fuelCap);

        Text health = Util.generateText("Health: ", 20, Color.WHITE);
        Util.setIndex(this, health, 4, 0);
        nodeList.add(health);

        Text healthLevel = Util.generateText(ship.getHealth() + "/"
                + ship.getMaxHealth(), 20, Color.WHITE);
        healthLevel.setWrappingWidth(300);
        Util.setIndex(this, healthLevel, 4, 1);
        nodeList.add(healthLevel);

        Text creditLabel = Util.generateText("Credits: ", 20, Color.WHITE);
        Util.setIndex(this, creditLabel, 5, 0);
        nodeList.add(creditLabel);

        Text creditText = Util.generateText(String.valueOf(player.getCredits()), 20, Color.WHITE);
        Util.setIndex(this, creditText, 5, 1);
        nodeList.add(creditText);

        Button returnToRegion = new Button("View Region");
        returnToRegion.setPrefWidth(100);
        returnToRegion.setPrefHeight(35);
        returnToRegion.setOnAction(e -> {
            RegionScreen regionScreen = new RegionScreen(stage, player, region);
            getScene().setRoot(regionScreen);
        });
        Util.setIndex(this, returnToRegion, 6, 0);
        setColumnSpan(returnToRegion, 2);
        nodeList.add(returnToRegion);

        Button upgradeButton = new Button("Upgrade");
        upgradeButton.setPrefWidth(100);
        upgradeButton.setPrefHeight(35);
        upgradeButton.setOnAction(e -> loadCharacterUpgradeScreen());

        Util.setIndex(this, upgradeButton, 6, 1);
        setColumnSpan(upgradeButton, 2);
        nodeList.add(upgradeButton);

        Button refuel = refuelButton(fuelCap, creditText);
        Util.setIndex(this, refuel, 7, 0);
        nodeList.add(refuel);

        Button restoreHealth = restoreHealthButton(creditText, healthLevel);
        Util.setIndex(this, restoreHealth, 7, 1);
        nodeList.add(restoreHealth);

        getChildren().addAll(nodeList);
    }

    private Button refuelButton(Text fuelCap, Text credits) {
        Button refuel = new Button("Refuel\n50 Fuel\nCost: 100");
        refuel.setPrefWidth(100);
        refuel.setOnAction(event -> {
            if (player.getCredits() < 100) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Insufficient Credits!");
                alert.setContentText("Sell items and try again.");
                alert.showAndWait();
            } else if (ship.getCurrentFuel() + 50 > ship.getFuelCapacity()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Insufficient Fuel Capacity!");
                alert.setContentText("Travel and try again.");
                alert.showAndWait();
            } else {
                player.setCredits(player.getCredits() - 100);
                ship.setCurrentFuel(ship.getCurrentFuel() + 50);
                fuelCap.setText(ship.getCurrentFuel() + "/" + ship.getFuelCapacity());
                credits.setText("" + player.getCredits());
            }
        });
        return refuel;
    }

    private Button restoreHealthButton(Text credits, Text healthLevel) {
        int cost = 1000 - (player.getEngineerPoints() * 100);
        if (cost <= 0 || this.player.getShip().getHealth()
                == this.player.getShip().getMaxHealth()) {
            cost = 0;
        }
        final int cost1 = cost;

        Button rhbutton = new Button("Restore Health\nCost: " + Integer.toString(cost));
        rhbutton.setPrefWidth(100);
        rhbutton.setOnAction(event -> {
            if (player.getCredits() < cost1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ship Health");
                alert.setHeaderText("Insufficient Credits!");
                alert.setContentText("Sell items and try again.");
                alert.showAndWait();
            } else if (player.getShip().getMaxHealth() == player.getShip().getHealth()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ship Health");
                alert.setHeaderText("Health already maximized!");
                alert.setContentText("You weren't charged.\nClick OK and keep trading.");
                alert.showAndWait();
            } else {
                this.player.getShip().setHealth(this.player.getShip().getMaxHealth());
                this.player.setCredits(this.player.getCredits() - cost1);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ship Health");
                alert.setHeaderText("Success!");
                alert.setContentText("You restored your ship's health.");
                alert.showAndWait();
                healthLevel.setText(ship.getHealth() + "/" + ship.getMaxHealth());
                credits.setText("" + player.getCredits());
            }
        });
        return rhbutton;
    }

    private void loadCharacterUpgradeScreen() {
        CharacterUpgradeScreen screen = new CharacterUpgradeScreen(
                this.ship, this.player, this.region, this.stage);
        getScene().setRoot(screen);
    }

}
