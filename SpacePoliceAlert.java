import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class SpacePoliceAlert extends Alert implements IAlert {

    private Stage stage;
    private Player player;
    private Region desiredRegion;
    private Region prevRegion;

    /**
     * base constructor
     * @param alertType type of alert
     */
    public SpacePoliceAlert(AlertType alertType) {
        super(alertType);
    }

    /**
     * constructor to pass in all necessary data
     * @param alertType type of alert
     * @param stage stage being used
     * @param player player of game
     * @param desiredRegion where player wants to go
     * @param prevRegion where player comes from
     */
    public SpacePoliceAlert(AlertType alertType, Stage stage, Player player, Region desiredRegion,
                            Region prevRegion) {
        this(alertType);

        this.setHeight(this.getHeight() + 300);
        this.stage = stage;
        this.player = player;
        this.desiredRegion = desiredRegion;
        this.prevRegion = prevRegion;

        alert();
    }

    @Override
    public void alert() {
        setTitle("Encounter!");

        Map<Item, Integer> map = new HashMap<>(); //items and amount that are being requested

        //determine which items and how many
        for (Map.Entry<Item, Integer> item : player.getShip().getInventory().entrySet()) {
            Random random = new Random();
            double chance = random.nextDouble();
            if (chance < .25) {
                int temp = random.nextInt(item.getValue()) + 1;
                map.put(item.getKey(), temp);
            }
        }

        //in the event no items were added
        if (map.size() == 0) {
            for (Map.Entry<Item, Integer> item : player.getShip().getInventory().entrySet()) {
                Random random = new Random();
                int temp = random.nextInt(item.getValue()) + 1;
                map.put(item.getKey(), temp);
                break;
            }
        }

        //turn the map into a string that is readable in the alert
        String itemsDemanded = "";
        for (Map.Entry<Item, Integer> item : map.entrySet()) {
            itemsDemanded += item.getValue() + " " + item.getKey().name() + ", ";
        }

        itemsDemanded = itemsDemanded.substring(0, itemsDemanded.length() - 2);

        setHeaderText("The Space Police are on to you! They believe you have stolen items and"
                + " demand " + itemsDemanded);
        setContentText("What would you like to do?");

        ButtonType forfeit = new ButtonType("Forfeit the items");
        ButtonType flee = new ButtonType("Try to Flee");
        ButtonType fight = new ButtonType("Try to Fight");

        getButtonTypes().setAll(forfeit, flee, fight);

        Optional<ButtonType> result = showAndWait();
        if (result.get() == forfeit) {
            forfeitHelper(map);
        } else if (result.get() == flee) {
            fleeHelper(map);
        } else {
            fightHelper(map);
        }
    }

    /**
     * removes what the police are asking for
     * @param map map of what the police are asking for
     */
    private void removeFromInventory(Map<Item, Integer> map) {
        for (Map.Entry<Item, Integer> item : map.entrySet()) {
            int temp = player.getShip().getInventory().get(item.getKey()); //total in your inventory
            temp = temp - item.getValue();
            player.getShip().setCurrentCapacity(player.getShip().getCurrentCapacity()
                    - item.getValue());
            player.getShip().getInventory().remove(item.getKey());
            if (temp > 0) {
                player.getShip().getInventory().put(item.getKey(), temp);
            }
        }
    }

    /**
     * helper for forfeit scenario
     * @param map map of what is being asked for
     */
    public void forfeitHelper(Map<Item, Integer> map) {

        removeFromInventory(map);

        setHeaderText("You forfeited all of the requested items to the space police.");
        setContentText("You will now travel to your desired destination.");

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
    }

    /**
     * helper for flee scenario
     * @param map map of what is being asked for
     */
    public void fleeHelper(Map<Item, Integer> map) {
        double rand = Math.random();
        if (rand < player.getPilotPoints() / 25.0) {
            setHeaderText("You escaped the Space Police!");
            setContentText("You will now travel back to your previous location.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
            stage.getScene().setRoot(new RegionScreen(stage, player, prevRegion));
        } else {
            player.getShip().setHealth(player.getShip().getHealth() - 250);
            if (!shipHealthZero()) {
                this.setHeight(this.getHeight() + 50);

                setHeaderText("You failed to escape the Space Police!");

                removeFromInventory(map);

                setContentText("The Space Police have taken all of the items they asked for"
                        + " and damaged your ship and fined you.\n-250 health and -150 credits");
                player.setCredits(player.getCredits() - 150);
                if (player.getCredits() < 0) {
                    player.setCredits(0);
                }
                ButtonType cont = new ButtonType("Continue");
                getButtonTypes().setAll(cont);
                showAndWait();
                stage.getScene().setRoot(new RegionScreen(stage, player, prevRegion));
            }
        }
    }

    /**
     * helper for fight scenario
     * @param map map of what is being asked for
     */
    public void fightHelper(Map<Item, Integer> map) {
        double rand = Math.random();
        if (rand < player.getFighterPoints() / 25.0) {
            Random r = new Random();
            setHeaderText("You won the fight!");
            setContentText("You successfully fought off the space police!\n"
                    + "You will now travel to your desired destination.");

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
            this.setHeight(this.getHeight() + 50);

            setHeaderText("You lost the fight!");
            setContentText("The Space Police did not appreciate you trying to fight them."
                    + "\nThey have taken all of the items they asked for and damaged your "
                    + "ship and fined you.\n-250 health and -150 credits");

            removeFromInventory(map);

            player.setCredits(player.getCredits() - 150);
            if (player.getCredits() < 0) {
                player.setCredits(0);
            }
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
