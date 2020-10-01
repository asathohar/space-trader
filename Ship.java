import java.util.HashMap;
import java.util.Map;

public class Ship {

    private Player player;
    private String name;
    private int currentCapacity = 0;
    private int cargoCapacity;
    private Map<Item, Integer> inventory = new HashMap<>();
    private int fuelCapacity;
    private int currentFuel;
    private int health;
    private int maxHealth;

    public Ship(Player player, String name) {
        this.name = name;
        switch (player.getDifficulty()) {
        case "Easy":
            cargoCapacity = 18;
            health = 3000;
            fuelCapacity = 1000;
            maxHealth = 3000;
            break;
        case "Hard":
            cargoCapacity = 10;
            health = 2000;
            fuelCapacity = 500;
            maxHealth = 2000;
            break;
        default:
            cargoCapacity = 14;
            health = 2500;
            fuelCapacity = 750;
            maxHealth = 2500;
        }
        currentFuel = fuelCapacity;
    }



    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public Map<Item, Integer> getInventory() {
        return inventory;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public int getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(int fuel) {
        currentFuel = fuel;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public void setInventory(HashMap<Item, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setFuelCapacity(int fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

}
