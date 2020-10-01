import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarketScreen extends GridPane {

    private Stage stage;
    private Player player;
    private Region region;

    /**
     * displays info about a region
     * @param stage current stage
     * @param player current player
     * @param region region being displayed
     */
    public MarketScreen(Stage stage, Player player, Region region) {
        this.stage = stage;
        this.player = player;
        this.region = region;

        setBackground(SpaceTrader.BACKGROUND);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        List<Node> nodeList = new ArrayList<>();

        Text nameLabel = Util.generateText("Region: ", 20, Color.WHITE);
        Util.setIndex(this, nameLabel, 0, 0);
        nodeList.add(nameLabel);

        Text regionName = Util.generateText(region.getName(), 20, Color.WHITE);
        Util.setIndex(this, regionName, 0, 1);
        nodeList.add(regionName);

        Text techLabel = Util.generateText("Tech Level: ", 20, Color.WHITE);
        Util.setIndex(this, techLabel, 1, 0);
        nodeList.add(techLabel);

        Text techLevel = Util.generateText(region.getTechLevel(), 20, Color.WHITE);
        Util.setIndex(this, techLevel, 1, 1);
        nodeList.add(techLevel);

        Text creditLabel = Util.generateText("Credits: ", 20, Color.WHITE);
        Util.setIndex(this, creditLabel, 0, 3);
        nodeList.add(creditLabel);

        Text creditText = Util.generateText(String.valueOf(player.getCredits()), 20, Color.WHITE);
        Util.setIndex(this, creditText, 0, 4);
        nodeList.add(creditText);

        Text capacityLabel = Util.generateText("Capacity: ", 20, Color.WHITE);
        Util.setIndex(this, capacityLabel, 1, 3);
        nodeList.add(capacityLabel);

        Text capacityText = Util.generateText(player.getShip().getCurrentCapacity() + "/"
                + player.getShip().getCargoCapacity(), 20, Color.WHITE);
        Util.setIndex(this, capacityText, 1, 4);
        nodeList.add(capacityText);

        Text itemsLabel = Util.generateText("Items", 20, Color.WHITE);
        Util.setIndex(this, itemsLabel, 2, 0);
        nodeList.add(itemsLabel);

        Text buyPriceLabel = Util.generateText("Buy Price", 20, Color.WHITE);
        Util.setIndex(this, buyPriceLabel, 2, 1);
        nodeList.add(buyPriceLabel);

        Text sellPriceLabel = Util.generateText("Sell Price", 20, Color.WHITE);
        Util.setIndex(this, sellPriceLabel, 2, 3);
        nodeList.add(sellPriceLabel);

        Text inventoryLabel = Util.generateText("Inventory", 20, Color.WHITE);
        Util.setIndex(this, inventoryLabel, 2, 5);
        nodeList.add(inventoryLabel);

        int i = 0;
        for (Map.Entry<Item, Integer> item : region.getMarket().getItems().entrySet()) {
            Text itemLabel = Util.generateText(item.getKey().toString(), 12, Color.WHITE);
            Util.setIndex(this, itemLabel, 3 + i, 0);
            nodeList.add(itemLabel);

            Map<Item, Integer> inventory = player.getShip().getInventory();
            Text inventoryAmount = Util.generateText("0", 12, Color.WHITE);
            if (inventory.containsKey(item.getKey())) {
                inventoryAmount.setText(String.valueOf(inventory.get(item.getKey())));
            }
            Util.setIndex(this, inventoryAmount, 3 + i, 5);
            setHalignment(inventoryAmount, HPos.CENTER);
            nodeList.add(inventoryAmount);

            int itemBuyPrice = Util.calcBuyPriceByTechLevel(region.getTechLevel(), item.getKey());
            Text buyLabel = Util.generateText(String.valueOf(itemBuyPrice), 12, Color.WHITE);
            Util.setIndex(this, buyLabel, 3 + i, 1);
            setHalignment(buyLabel, HPos.CENTER);
            nodeList.add(buyLabel);

            Button buyButton = new Button("Buy");
            buyButton.setPrefWidth(40);
            buyButton.setPrefHeight(12);
            buyButton.setOnAction(e -> {
                this.buyItem(item.getKey());
                this.updateLabelsAfterTransaction(creditText,
                        capacityText, inventoryAmount, item.getKey());
            });
            Util.setIndex(this, buyButton, 3 + i, 2);
            nodeList.add(buyButton);

            int itemSellPrice = Util.calcSellPriceByTechLevel(region.getTechLevel(), item.getKey());
            Text sellLabel = Util.generateText(String.valueOf(itemSellPrice), 12, Color.WHITE);
            Util.setIndex(this, sellLabel, 3 + i, 3);
            setHalignment(sellLabel, HPos.CENTER);
            nodeList.add(sellLabel);

            Button sellButton = new Button("Sell");
            sellButton.setPrefWidth(40);
            sellButton.setPrefHeight(12);
            sellButton.setOnAction(e -> {
                this.sellItem(item.getKey());
                this.updateLabelsAfterTransaction(creditText,
                        capacityText, inventoryAmount, item.getKey());
            });
            Util.setIndex(this, sellButton, 3 + i, 4);
            nodeList.add(sellButton);

            i++;
        }

        Button regionScreenButton = new Button("View Region");
        regionScreenButton.setPrefWidth(100);
        regionScreenButton.setPrefHeight(35);
        regionScreenButton.setOnAction(e -> {
            regionScreenButtonPressed();
        });
        Util.setIndex(this, regionScreenButton, i + 3, 0);
        setColumnSpan(regionScreenButton, 2);
        nodeList.add(regionScreenButton);

        if (region.getName().equals(player.getWinningRegion())) {
            Button winningButton = new Button("Buy magic wand and win\ncost: 1200 credits");
            winningButton.setPrefWidth(200);
            winningButton.setPrefHeight(70);
            winningButton.setOnAction(e -> {
                if (player.getCredits() >= 1200) {
                    EndScreen endScreen = new EndScreen(stage, true);
                    getScene().setRoot(endScreen);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Winning Item");
                    alert.setHeaderText("Insufficient Credits");
                    alert.setContentText("You need more credits to buy this item.");
                    alert.showAndWait();
                }
            });
            Util.setIndex(this, winningButton, i + 4, 0);
            setColumnSpan(winningButton, 2);
            nodeList.add(winningButton);
        }

        getChildren().addAll(nodeList);
    }

    private void regionScreenButtonPressed() {
        RegionScreen regionScreen = new RegionScreen(stage, player, region);
        getScene().setRoot(regionScreen);
    }

    public void buyItem(Item item) {
        int itemPrice = Util.calcBuyPriceByTechLevel(region.getTechLevel(), item);
        if (player.getShip().getCurrentCapacity() + item
                .getCapacity() > player.getShip().getCargoCapacity()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cargo is Full!");
            alert.setContentText("Sell items and try again.");
            alert.showAndWait();
        } else if (player.getCredits() < itemPrice) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insufficient Credits!");
            alert.setContentText("Sell items and try again.");
            alert.showAndWait();
        } else  {
            player.setCredits(player.getCredits() - itemPrice);
            player.getShip().getInventory().put(item, player.getShip()
                    .getInventory().getOrDefault(item, 0) + 1);
            player.getShip().setCurrentCapacity(player
                    .getShip().getCurrentCapacity() + item.getCapacity());
        }
    }

    public void sellItem(Item item) {
        int itemPrice = Util.calcSellPriceByTechLevel(region.getTechLevel(), item);
        if (player.getShip().getInventory().getOrDefault(item, 0) == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You don't have that item!");
            alert.setContentText("Try again later.");
            alert.showAndWait();
        } else {
            player.setCredits(player.getCredits() + itemPrice);
            player.getShip().getInventory().put(item,
                    player.getShip().getInventory().get(item) - 1);
            player.getShip().setCurrentCapacity(player.getShip()
                    .getCurrentCapacity() - item.getCapacity());
        }
    }

    private void updateLabelsAfterTransaction(Text credits, Text capacity,
                                              Text inventoryAmount, Item item) {
        credits.setText(String.valueOf(player.getCredits()));
        capacity.setText(player.getShip().getCurrentCapacity() + "/"
                + player.getShip().getCargoCapacity());
        Map<Item, Integer> inventory = player.getShip().getInventory();
        if (inventory.containsKey(item)) {
            inventoryAmount.setText(String.valueOf(inventory.get(item)));
        } else {
            inventoryAmount.setText("0");
        }
    }

}
