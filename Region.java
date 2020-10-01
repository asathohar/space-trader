
/**
 * enum that defines a region
 */
public enum Region {

    ISILDUR("Isildur", "Medium", "A terrestrial planet famous for its rich ore deposits."
            + " Vast colonies of micro-organisms are also common here."),
    SEGOMO("Segomo", "Medium", "A desert world mostly covered with golden yellow sand."
            + " The planet has very little life besides simple microorganisms."),
    VANDIA("Vandia", "High", "A terrestrial planet famous for its beautiful meadows and"
            + " giant ant hills. It is populated by intelligent slugs who evolved on a"
            + " high-gravity world."),
    MIASMA("Miasma", "Very High", "A geologically active world with"
            + " rugged mountainous terrain. A colony of intelligent Wixians have established"
            + " themselves on the planet."),
    APOLLO("Apollo", "Medium", "A desert world with a nitrogen/oxygen atmosphere."
            + " The largest creatures on the planet are large succulent plants."),
    AKAFAR("Akafar", "Low", "A habitable dwarf planet famous for its high plateaus."),
    NARAKA("Naraka", "Low", "A large rocky planet with high gravity and an atmosphere"
            + " of mostly CO2."),
    OCASTA("Ocasta", "Low", " A lifeless rock with no atmosphere, slightly larger than"
            + " Earth's moon. The planet is used as a dumping ground for "
            + " various industrial by-products."),
    VULIA("Vulia", "Very High", "The homeworld of the Ikroden species. Sprawling mega-cities"
            + " cover much of the surface, but the planet is also known for its geothermal vent"),
    AZURE("Azure", "High", "A lush jungle world. The planet is home to a species of"
            + " sentient invertibrates known as Thindrians.");

    private String name;
    private String techLevel;
    private String description;
    private boolean visited;
    private int x;
    private int y;
    private Market market;

    Region(String name, String techLevel, String description) {
        this.name = name;
        this.techLevel = techLevel;
        this.description = description;
        visited = false;
        market = null; // must be initialized with a region so can't be done in constructor

        x = 40 + (int) (Math.random() * 561);
        y = 40 + (int) (Math.random() * 401);
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * @return region name
     */
    public String getName() {
        return name;
    }

    /**
     * @return amount of tech points
     */
    public String getTechLevel() {
        return techLevel;
    }


    /**
     * @return region's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return true of the player has visited the region
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @return x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * set whether or not the region has been visited
     * @param visited whether or not the region has been visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * set new x coordinate
     * @param x x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set new y coordinate
     * @param y y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

}
