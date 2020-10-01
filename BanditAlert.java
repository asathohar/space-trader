import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

public class BanditAlert extends Alert implements IAlert {

    private Stage stage;
    private Player player;
    private Region desiredRegion;
    private Region prevRegion;

    public BanditAlert(AlertType alertType) {
        super(alertType);
    }

    public BanditAlert(AlertType alertType, Stage stage, Player player, Region desiredRegion,
                       Region prevRegion) {
        this(alertType);

        this.stage = stage;
        this.player = player;
        this.desiredRegion = desiredRegion;
        this.prevRegion = prevRegion;

        alert();
    }

    @Override
    public void alert() {
        setTitle("Encounter!");

        Random rand = new Random();
        int creditsDemanded = rand.nextInt(225) + 25;

        setHeaderText("You have encountered a bandit! He demands " + creditsDemanded + " credits!");
        setContentText("What would you like to do?");

        ButtonType pay = new ButtonType("Pay the Bandit");
        ButtonType flee = new ButtonType("Try to Flee");
        ButtonType fight = new ButtonType("Try to Fight");

        getButtonTypes().setAll(pay, flee, fight);

        Optional<ButtonType> result = showAndWait();
        if (result.get() == pay) {
            pay(creditsDemanded);
        } else if (result.get() == flee) {
            flee();
        } else {
            fight();
        }
    }

    private void pay(int creditsDemanded) {
        Alert alert = new Alert(AlertType.ERROR);
        if (player.getCredits() < creditsDemanded) {
            if (player.getShip().getInventory().size() == 0) {
                alert.setTitle("");
                setHeaderText("You have insufficient credits and your inventory is empty!");
                setContentText("The bandit has damaged your ship.\n-350 health");
                player.getShip().setHealth(player.getShip().getHealth() - 350);
                if (!shipHealthZero()) {
                    ButtonType cont = new ButtonType("Continue");
                    getButtonTypes().setAll(cont);
                }
            } else {
                setHeaderText("You have insufficient credits!");
                setContentText("The bandit has stolen all of your items.");
                player.getShip().setInventory(new HashMap<>());
                ButtonType cont = new ButtonType("Continue");
                getButtonTypes().setAll(cont);
            }
            showAndWait();
        } else if (player.getCredits() >= creditsDemanded) {
            setHeaderText("You paid the bandit " + creditsDemanded + " credits.");
            setContentText("You will now travel to your desired destination.");
            player.setCredits(player.getCredits() - creditsDemanded);

            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
        }
        if (!desiredRegion.isVisited()) {
            Market market = new Market(desiredRegion);
            market.adjustPrices(player);
            desiredRegion.setMarket(market);
        }
        desiredRegion.setVisited(true);
        stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
    }

    private void flee() {
        double rand = Math.random();
        if (rand < player.getPilotPoints() / 25.0) {
            setHeaderText("You escaped the bandit!");
            setContentText("You will now travel back to your previous location.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
            stage.getScene().setRoot(new RegionScreen(stage, player, prevRegion));
        } else {
            setHeaderText("You failed to escape the bandit!");
            setContentText("The bandit has stolen all of your credits and damaged your ship."
                    + "\n-250 health");
            player.setCredits(0);
            player.getShip().setHealth(player.getShip().getHealth() - 250);
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            if (!shipHealthZero()) {
                showAndWait();
                stage.getScene().setRoot(new RegionScreen(stage, player, prevRegion));
            }
        }
    }

    private void fight() {
        double rand = Math.random();
        if (rand < player.getFighterPoints() / 25.0) {
            Random r = new Random();
            int creditsWon = r.nextInt(100) + 25;
            setHeaderText("You defeated the bandit!");
            setContentText("You won " + creditsWon + " credits!\n"
                    + "You will now travel to your desired destination.");
            player.setCredits(player.getCredits() + creditsWon);

            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
            if (!desiredRegion.isVisited()) {
                Market market = new Market(desiredRegion);
                market.adjustPrices(player);
                desiredRegion.setMarket(market);
            }
            desiredRegion.setVisited(true);
            stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
        } else {
            setHeaderText("You failed to defeat the bandit!");
            setContentText("The bandit has stolen all of your credits and damaged your ship."
                    + "\n-250 health");
            player.setCredits(0);
            player.getShip().setHealth(player.getShip().getHealth() - 250);
            if (!shipHealthZero()) {
                ButtonType cont = new ButtonType("Continue");
                getButtonTypes().setAll(cont);
                Optional<ButtonType> res = showAndWait();
                if (res.get() == cont) {
                    stage.getScene().setRoot(new RegionScreen(stage, player, prevRegion));
                }
            }
        }
    }

    public boolean shipHealthZero() {
        if (player.getShip().getHealth() <= 0) {
            EndScreen loseScreen = new EndScreen(stage, false);
            stage.getScene().setRoot(loseScreen);
            return true;
        } else {
            return false;
        }
    }

}
