import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CharacterUpgradeScreen extends GridPane {
    private Stage stage;
    private Player player;
    private Region region;
    private Ship ship;

    public CharacterUpgradeScreen(Ship ship, Player player, Region region, Stage stage) {
        this.ship = ship;
        this.player = player;
        this.stage = stage;
        this.region = region;

        setBackground(SpaceTrader.BACKGROUND);
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);

        List<Node> nodeList = new ArrayList<>();
        List<Text> nodes = separateConstructorMethod(nodeList);
        Text creditsText = nodes.get(0);
        Text epts = nodes.get(1);
        Text mpts = nodes.get(3);
        Text fpts = nodes.get(2);
        Text ppts = nodes.get(4);

        Text wrenchQuantity = Util.generateText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.WRENCH)), 20, Color.WHITE);
        Util.setIndex(this, wrenchQuantity, 6, 3);
        setHalignment(wrenchQuantity, HPos.CENTER);
        nodeList.add(wrenchQuantity);

        Text laserQuantity = Util.generateText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.LASER_CANNON)), 20, Color.WHITE);
        Util.setIndex(this, laserQuantity, 7, 3);
        setHalignment(laserQuantity, HPos.CENTER);
        nodeList.add(laserQuantity);

        Text scrollQuantity = Util.generateText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.SCROLL_OF_TRADING)), 20, Color.WHITE);
        Util.setIndex(this, scrollQuantity, 8, 3);
        setHalignment(scrollQuantity, HPos.CENTER);
        nodeList.add(scrollQuantity);

        Text controlsQuantity = Util.generateText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.ADVANCED_CONTROLS)), 20, Color.WHITE);
        Util.setIndex(this, controlsQuantity, 9, 3);
        setHalignment(controlsQuantity, HPos.CENTER);
        nodeList.add(controlsQuantity);

        Text wrenchUpgrade = Util.generateText(
                "Wrench \n(cost: 200, engineer +2): ", 12, Color.WHITE);
        Util.setIndex(this, wrenchUpgrade, 6, 0);
        nodeList.add(wrenchUpgrade);

        Button buyWrench = new Button("Buy");
        buyWrench.setOnAction(e -> {
            int price = CharacterUpgrade.WRENCH.getPrice();
            if (this.player.getCredits() >= price) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.WRENCH, this.player.getCharacterUpgrades()
                        .get(CharacterUpgrade.WRENCH) + 1);
                this.player.setCredits(this.player.getCredits() - price);
                CharacterUpgrade.WRENCH.addPlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, buyWrench, 6, 1);
        nodeList.add(buyWrench);

        Button sellWrench = new Button("Sell");
        sellWrench.setOnAction(e -> {
            int price = CharacterUpgrade.WRENCH.getPrice();
            if (this.player.getCharacterUpgrades().get(CharacterUpgrade.WRENCH) > 0) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.WRENCH, this.player.getCharacterUpgrades()
                        .get(CharacterUpgrade.WRENCH) - 1);
                this.player.setCredits(this.player.getCredits() + price);
                CharacterUpgrade.WRENCH.removePlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, sellWrench, 6, 2);
        nodeList.add(sellWrench);

        Text laserUpgrade = Util.generateText(
                "Laser Cannon \n(cost: 200, fighter +2): ", 12, Color.WHITE);
        Util.setIndex(this, laserUpgrade, 7, 0);
        nodeList.add(laserUpgrade);

        otherConstructorMethod(nodeList, wrenchQuantity, laserQuantity, scrollQuantity,
                controlsQuantity, nodes);

        getChildren().addAll(nodeList);
    }

    private void otherConstructorMethod(List<Node> nodeList, Text wrenchQuantity,
                                              Text laserQuantity, Text scrollQuantity,
                                              Text controlsQuantity, List<Text> nodes) {
        Text creditsText = nodes.get(0);
        Text epts = nodes.get(1);
        Text mpts = nodes.get(3);
        Text fpts = nodes.get(2);
        Text ppts = nodes.get(4);
        Button buyLaserCannon = new Button("Buy");
        buyLaserCannon.setOnAction(e -> {
            int price = CharacterUpgrade.LASER_CANNON.getPrice();
            if (this.player.getCredits() >= price) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.LASER_CANNON, this.player.getCharacterUpgrades()
                                .get(CharacterUpgrade.LASER_CANNON) + 1);
                this.player.setCredits(this.player.getCredits() - price);
                CharacterUpgrade.LASER_CANNON.addPlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, buyLaserCannon, 7, 1);
        nodeList.add(buyLaserCannon);

        Button sellLaserCannon = new Button("Sell");
        sellLaserCannon.setOnAction(e -> {
            int price = CharacterUpgrade.LASER_CANNON.getPrice();
            if (this.player.getCharacterUpgrades().get(CharacterUpgrade.LASER_CANNON) > 0) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.LASER_CANNON, this.player.getCharacterUpgrades()
                                .get(CharacterUpgrade.LASER_CANNON) - 1);
                this.player.setCredits(this.player.getCredits() + price);
                CharacterUpgrade.LASER_CANNON.removePlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, sellLaserCannon, 7, 2);
        nodeList.add(sellLaserCannon);

        Text tradingScrollUpgrade = Util.generateText(
                "Scroll of Trading \n(cost: 200, merchant +2): ", 12, Color.WHITE);
        Util.setIndex(this, tradingScrollUpgrade, 8, 0);
        nodeList.add(tradingScrollUpgrade);

        Button buyTradingScroll = new Button("Buy");
        buyTradingScroll.setOnAction(e -> {
            int price = CharacterUpgrade.SCROLL_OF_TRADING.getPrice();
            if (this.player.getCredits() >= price) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.SCROLL_OF_TRADING, this.player.getCharacterUpgrades()
                                .get(CharacterUpgrade.SCROLL_OF_TRADING) + 1);
                this.player.setCredits(this.player.getCredits() - price);
                CharacterUpgrade.SCROLL_OF_TRADING.addPlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, buyTradingScroll, 8, 1);
        nodeList.add(buyTradingScroll);

        Button sellTradingScroll = new Button("Sell");
        sellTradingScroll.setOnAction(e -> {
            int price = CharacterUpgrade.SCROLL_OF_TRADING.getPrice();
            if (this.player.getCharacterUpgrades().get(CharacterUpgrade.SCROLL_OF_TRADING) > 0) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.SCROLL_OF_TRADING, this.player.getCharacterUpgrades()
                                .get(CharacterUpgrade.SCROLL_OF_TRADING) - 1);
                this.player.setCredits(this.player.getCredits() + price);
                CharacterUpgrade.SCROLL_OF_TRADING.removePlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, sellTradingScroll, 8, 2);
        nodeList.add(sellTradingScroll);

        Text controlsUpgrade = Util.generateText(
                "Advanced Controls \n(cost: 200, pilot +2): ", 12, Color.WHITE);
        Util.setIndex(this, controlsUpgrade, 9, 0);
        nodeList.add(controlsUpgrade);

        Button buyControls = new Button("Buy");
        buyControls.setOnAction(e -> {
            int price = CharacterUpgrade.ADVANCED_CONTROLS.getPrice();
            if (this.player.getCredits() >= price) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.ADVANCED_CONTROLS, this.player.getCharacterUpgrades()
                                .get(CharacterUpgrade.ADVANCED_CONTROLS) + 1);
                this.player.setCredits(this.player.getCredits() - price);
                CharacterUpgrade.ADVANCED_CONTROLS.addPlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, buyControls, 9, 1);
        nodeList.add(buyControls);

        Button sellControls = new Button("Sell");
        sellControls.setOnAction(e -> {
            int price = CharacterUpgrade.ADVANCED_CONTROLS.getPrice();
            if (this.player.getCharacterUpgrades().get(CharacterUpgrade.ADVANCED_CONTROLS) > 0) {
                this.player.getCharacterUpgrades().put(
                        CharacterUpgrade.ADVANCED_CONTROLS, this.player.getCharacterUpgrades()
                        .get(CharacterUpgrade.ADVANCED_CONTROLS) - 1);
                this.player.setCredits(this.player.getCredits() + price);
                CharacterUpgrade.ADVANCED_CONTROLS.removePlayerPoints(player);
                updatePointLabels(creditsText, epts, fpts, mpts, ppts);
                updateCharacterUpgrades(wrenchQuantity, laserQuantity,
                        scrollQuantity, controlsQuantity);
            }
        });
        Util.setIndex(this, sellControls, 9, 2);
        nodeList.add(sellControls);

        Button returnToShip = new Button("View Ship");
        returnToShip.setPrefWidth(100);
        returnToShip.setPrefHeight(35);
        returnToShip.setOnAction(e -> {
            ShipScreen shipScreen = new ShipScreen(this.ship, this.player, this.stage, this.region);
            getScene().setRoot(shipScreen);
        });
        Util.setIndex(this, returnToShip, 10, 0);
        setColumnSpan(returnToShip, 2);
        nodeList.add(returnToShip);
    }

    private List<Text> separateConstructorMethod(List<Node> nodeList) {
        List<Text> returnNodes = new ArrayList<>();

        Text creditsLabel = Util.generateText("Credits: ", 20, Color.WHITE);
        Util.setIndex(this, creditsLabel, 0, 0);
        nodeList.add(creditsLabel);

        Text creditsText = Util.generateText(String.valueOf(player.getCredits()), 20, Color.WHITE);
        Util.setIndex(this, creditsText, 0, 1);
        nodeList.add(creditsText);
        returnNodes.add(creditsText);

        Text engineerPoints = Util.generateText("Current Engineer Points: ", 20, Color.WHITE);
        Util.setIndex(this, engineerPoints, 1, 0);
        nodeList.add(engineerPoints);

        Text epts = Util.generateText(String.valueOf(player.getEngineerPoints()), 20, Color.WHITE);
        Util.setIndex(this, epts, 1, 1);
        nodeList.add(epts);
        returnNodes.add(epts);

        Text fighterPoints = Util.generateText("Current Fighter Points: ", 20, Color.WHITE);
        Util.setIndex(this, fighterPoints, 2, 0);
        nodeList.add(fighterPoints);

        Text fpts = Util.generateText(String.valueOf(player.getFighterPoints()), 20, Color.WHITE);
        Util.setIndex(this, fpts, 2, 1);
        nodeList.add(fpts);
        returnNodes.add(fpts);

        Text merchantPoints = Util.generateText("Current Merchant Points: ", 20, Color.WHITE);
        Util.setIndex(this,  merchantPoints, 3, 0);
        nodeList.add(merchantPoints);

        Text mpts = Util.generateText(String.valueOf(player.getMerchantPoints()), 20, Color.WHITE);
        Util.setIndex(this, mpts, 3, 1);
        nodeList.add(mpts);
        returnNodes.add(mpts);

        Text pilotPoints = Util.generateText("Current Pilot Points: ", 20, Color.WHITE);
        Util.setIndex(this, pilotPoints, 4, 0);
        nodeList.add(pilotPoints);

        Text ppts = Util.generateText(String.valueOf(player.getPilotPoints()), 20, Color.WHITE);
        Util.setIndex(this, ppts, 4, 1);
        nodeList.add(ppts);
        returnNodes.add(ppts);

        Text upgradesText = Util.generateText("Upgrades", 20, Color.WHITE);
        Util.setIndex(this, upgradesText, 5, 0);
        nodeList.add(upgradesText);

        Text quantityText = Util.generateText("Quantity", 20, Color.WHITE);
        Util.setIndex(this, quantityText, 5, 3);
        nodeList.add(quantityText);

        return returnNodes;
    }

    private void updatePointLabels(Text creditsText, Text epts, Text fpts, Text mpts, Text ppts) {
        creditsText.setText(String.valueOf(player.getCredits()));
        epts.setText(String.valueOf(player.getEngineerPoints()));
        fpts.setText(String.valueOf(player.getFighterPoints()));
        mpts.setText(String.valueOf(player.getMerchantPoints()));
        ppts.setText(String.valueOf(player.getPilotPoints()));
    }

    private void updateCharacterUpgrades(Text wrench, Text laser, Text scroll, Text controls) {
        wrench.setText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.WRENCH)));
        laser.setText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.LASER_CANNON)));
        scroll.setText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.SCROLL_OF_TRADING)));
        controls.setText(String.valueOf(player.getCharacterUpgrades()
                .get(CharacterUpgrade.ADVANCED_CONTROLS)));
    }

}
