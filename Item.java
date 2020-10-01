public enum Item {

    WATER("Low", 1, 25),
    FURS("Medium", 1, 25),
    GRAIN("Low", 1, 25),
    ORE("Low", 1, 50),
    WOOD("Low", 1, 25),
    GEMS("Medium", 1, 100),
    FRUITS("Low", 1, 50),
    JUICES("Medium", 1, 50),
    SPICES("Medium", 1, 50),
    GOLD("High", 1, 200),
    SILVER("High", 1, 150),
    GAMES("Medium", 1, 50),
    FIREARMS("High", 1, 150),
    SWORDS("High", 1, 50),
    MEDICINE("Very High", 1, 150),
    MACHINES("Very High", 1, 150),
    ROBOTS("Very High", 1, 200),
    RADIOS("Very High", 1, 250),
    NARCOTICS("High", 1, 150);

    private int buyPrice;
    private int sellPrice;
    private String techLevel;
    private int capacity;
    private int basePrice;

    Item(String techLevel, int capacity, int basePrice) {
        this.techLevel = techLevel;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.buyPrice = basePrice;
        this.sellPrice = (int) (buyPrice * 0.7); // this actually needs to be fixed
        // and be based off what is stored in the hashmap of items in the region
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getTechLevel() {
        return techLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

}
