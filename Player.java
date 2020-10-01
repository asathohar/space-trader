import java.util.HashMap;
import java.util.Map;

/**
 * defines a game player
 */
public class Player {

    private String[] regionArray = {"Isildur", "Segomo", "Vandia", "Miasma", "Apollo",
                                    "Akafar", "Naraka", "Ocasta", "Vulia", "Azure"};
    private String name;
    private int skillPoints;
    private int pilotPoints;
    private int fighterPoints;
    private int merchantPoints;
    private int engineerPoints;
    private int credits;
    private String winningRegion = regionArray[(int) (Math.random() * 10)];
    private Difficulty difficulty;
    private Region currentRegion;
    private Ship ship;
    private Map<CharacterUpgrade, Integer> characterUpgrades;

    /**
     * @param name String, will be name of player
     * @param skillPoints int, number of initial skillpoints
     * @param pilotPoints int, number of initial pilotpoints
     * @param fighterPoints int, number of initial fighterpoints
     * @param merchantPoints int, number of initial merchantpoints
     * @param engineerPoints int, number of initial engineerpoints
     * @param credits int, number of initial credits
     */
    public Player(String name, int skillPoints, int pilotPoints, int fighterPoints,
                  int merchantPoints, int engineerPoints, int credits) {
        this.name = name;
        this.skillPoints = skillPoints;
        this.pilotPoints = pilotPoints;
        this.fighterPoints = fighterPoints;
        this.merchantPoints = merchantPoints;
        this.engineerPoints = engineerPoints;
        this.credits = credits;
        difficulty = Difficulty.NORMAL;
        this.ship = new Ship(this, this.name + "'s Ship");

        characterUpgrades = new HashMap<>();
        for (CharacterUpgrade cu : CharacterUpgrade.values()) {
            characterUpgrades.put(cu, 0);
        }
    }

    /**
     * Player constructor
     */
    public Player() {
        this("", 0, 0, 0, 0, 0, 0);
    }

    /**
     * getter for name
     * @return name, String
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for skill points
     * @return skill points, int
     */
    public int getSkillPoints() {
        return skillPoints;
    }

    /**
     * sets skill points
     * @param skillPoints int
     */
    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    /**
     * gets pilot points
     * @return pilot points, int
     */
    public int getPilotPoints() {
        return pilotPoints;
    }

    /**
     * sets pilot points
     * @param pilotPoints int
     */
    public void setPilotPoints(int pilotPoints) {
        this.pilotPoints = pilotPoints;
    }

    /**
     * gets fighter points
     * @return fighter points, int
     */
    public int getFighterPoints() {
        return fighterPoints;
    }

    /**
     * sets fighter points
     * @param fighterPoints int
     */
    public void setFighterPoints(int fighterPoints) {
        this.fighterPoints = fighterPoints;
    }

    /**
     * gets merchant points
     * @return merchant points amount
     */
    public int getMerchantPoints() {
        return merchantPoints;
    }

    /**
     * sets merchant points
     * @param merchantPoints int
     */
    public void setMerchantPoints(int merchantPoints) {
        this.merchantPoints = merchantPoints;
    }

    /**
     * gets engineer points
     * @return engineer points, int
     */
    public int getEngineerPoints() {
        return engineerPoints;
    }

    /**
     * sets engineer points
     * @param engineerPoints int
     */
    public void setEngineerPoints(int engineerPoints) {
        this.engineerPoints = engineerPoints;
    }

    /**
     * gets credits
     * @return credits, int
     */
    public int getCredits() {
        return credits;
    }

    public Map<CharacterUpgrade, Integer> getCharacterUpgrades() {
        return characterUpgrades;
    }

    /**
     * sets credits
     * @param credits int
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * gets difficulty level
     * @return difficulty level, String
     */
    public String getDifficulty() {
        return difficulty.name().charAt(0) + difficulty.name().substring(1).toLowerCase();
    }

    /**
     * sets difficulty
     * @param difficulty Difficulty
     */
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * gets current region
     * @return current region
     */
    public Region getCurrentRegion() {
        return currentRegion;
    }

    /**
     * sets current region
     * @param currentRegion region to change to
     */
    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public Ship getShip() {
        return ship;
    }

    public String getWinningRegion() {
        return winningRegion;
    }
}
