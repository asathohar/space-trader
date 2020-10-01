import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class TraderAlert extends Alert implements IAlert {

    private Stage stage;
    private Player player;
    private Region desiredRegion;
    private Region prevRegion;

    public TraderAlert(AlertType alertType) {
        super(alertType);
    }

    public TraderAlert(AlertType alertType, Stage stage, Player player, Region desiredRegion,
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

        setHeaderText("Trader Frank wants to sell you some water for 100 credits.");
        setContentText("What would you like to do?");

        ButtonType ignore = new ButtonType("Ignore Frank");
        ButtonType buy = new ButtonType("Buy the item");
        ButtonType rob = new ButtonType("Rob Frank");
        ButtonType negotiate = new ButtonType("Negotiate the price");

        getButtonTypes().setAll(ignore, buy, rob, negotiate);

        Optional<ButtonType> result = showAndWait();
        if (result.get() == ignore) {
            ignoreHelper();
        } else if (result.get() == buy) {
            buyHelper(100);
        } else if (result.get() == rob) {
            robHelper();
        } else if (result.get() == negotiate) {
            negotiateHelper();
        }
    }

    private void ignoreHelper() {
        desiredRegion.setVisited(true);
        stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
    }

    private void buyHelper(int amt) {
        int currCredits = this.player.getCredits();

        if (currCredits >= amt) {
            this.player.setCredits(currCredits - amt);

            Item water = Item.WATER;
            this.player.getShip().getInventory().put(water, 25);

            setHeaderText("You have purchased the water.");
            setContentText("You will now travel to the desired region.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
        } else {
            setHeaderText("You do not have enough credits for the water.");
            setContentText("You will now travel to the desired region.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
        }

        desiredRegion.setVisited(true);
        stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
    }

    private void robHelper() {
        //any fighter points amount over 20 has a 100% chance of success
        //any amount under 20 has a *chance* of success
        float chance = this.player.getFighterPoints() / 2.0f;
        double rand = Math.random() * 10;

        System.out.println("Chance: " + chance);
        System.out.println("Rand: " + rand);
        boolean success = chance > rand;

        if (success) {
            Item water = Item.WATER;
            this.player.getShip().getInventory().put(water, 25);

            setHeaderText("You have successfully robbed Frank of the water.");
            setContentText("You will now travel to the desired region.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();
            desiredRegion.setVisited(true);
            stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
        } else {
            int currShipHealth = this.player.getShip().getHealth();
            this.player.getShip().setHealth(currShipHealth - 250);
            if (!shipHealthZero()) {
                setHeaderText("You have failed to rob Frank, and your ship took 250 damage.");
                setContentText("You will now continue to the desired region.");
                ButtonType cont = new ButtonType("Continue");
                getButtonTypes().setAll(cont);
                showAndWait();
                desiredRegion.setVisited(true);
                stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
            }
        }
    }

    private void negotiateHelper() {
        //any merchant points amount over 20 has a 100% chance of success
        //any amount under 20 has a *chance* of success
        float chance = this.player.getMerchantPoints() / 2.0f;
        double rand = Math.random() * 10;

        System.out.println("Chance: " + chance);
        System.out.println("Rand: " + rand);
        boolean success = chance > rand;

        if (success) {
            Item water = Item.WATER;
            this.player.getShip().getInventory().put(water, 25);

            int currCredits = this.player.getCredits();
            this.player.setCredits(currCredits - 10);

            setHeaderText("You have successfully negotiated and purchased the "
                    + "water for 10 credits.");
            setContentText("You will now travel to the desired region.");
            ButtonType cont = new ButtonType("Continue");
            getButtonTypes().setAll(cont);
            showAndWait();

            desiredRegion.setVisited(true);
            stage.getScene().setRoot(new RegionScreen(stage, player, desiredRegion));
        } else {
            setHeaderText("You have upset Frank. The price increased to 300 credits.");
            setContentText("You have lost the ability to negotiate.");
            ButtonType ignore = new ButtonType("Ignore Frank");
            ButtonType buy = new ButtonType("Buy the item");
            ButtonType rob = new ButtonType("Rob Frank");
            getButtonTypes().setAll(ignore, buy, rob);
            Optional<ButtonType> result = showAndWait();
            if (result.get() == ignore) {
                ignoreHelper();
            } else if (result.get() == buy) {
                buyHelper(100);
            } else if (result.get() == rob) {
                robHelper();
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
