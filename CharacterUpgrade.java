public enum CharacterUpgrade {

    WRENCH(200, 0, 0, 0, 2),
    LASER_CANNON(200, 0, 2, 0, 0),
    SCROLL_OF_TRADING(200, 0, 0, 2, 0),
    ADVANCED_CONTROLS(200, 2, 0, 0, 0);

    private int buyPrice;
    private int pilotPoints;
    private int fighterPoints;
    private int merchantPoints;
    private int engineerPoints;

    CharacterUpgrade(int buyPrice, int pilotPoints, int fighterPoints, int merchantPoints,
                     int engineerPoints) {
        this.buyPrice = buyPrice;
        this.pilotPoints = pilotPoints;
        this.fighterPoints = fighterPoints;
        this.merchantPoints = merchantPoints;
        this.engineerPoints = engineerPoints;
    }

    public void addPlayerPoints(Player player) {
        player.setPilotPoints(player.getPilotPoints() + pilotPoints);
        player.setFighterPoints(player.getFighterPoints() + fighterPoints);
        player.setMerchantPoints(player.getMerchantPoints() + merchantPoints);
        player.setEngineerPoints(player.getEngineerPoints() + engineerPoints);
    }

    public void removePlayerPoints(Player player) {
        player.setPilotPoints(player.getPilotPoints() - pilotPoints);
        player.setFighterPoints(player.getFighterPoints() - fighterPoints);
        player.setMerchantPoints(player.getMerchantPoints() - merchantPoints);
        player.setEngineerPoints(player.getEngineerPoints() - engineerPoints);
    }

    public int getPrice() {
        return buyPrice;
    }

}
