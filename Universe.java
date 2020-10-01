
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * defines the stage for game-playing
 */
public class Universe extends Pane {

    private Stage stage;
    private Player player;
    private Region currentRegion;

    /**
     * constructs a universe
     * @param stage javafx stage
     * @param player game player
     * @param currentRegion the region the player is in
     */
    public Universe(Stage stage, Player player, Region currentRegion) {
        this.stage = stage;
        this.player = player;
        this.currentRegion = currentRegion;

        setBackground(SpaceTrader.BACKGROUND);

        List<Region> closestRegions = findClosestRegions();
        for (Region region : closestRegions) {
            Line line = new Line(region.getX(), region.getY(), currentRegion.getX(),
                    currentRegion.getY());
            line.setStrokeWidth(3);
            line.setStroke(Color.WHITE);
            getChildren().add(line);
        }

        for (Region region : Region.values()) {
            drawRegion(region, closestRegions);
        }

    }

    /**
     * helper method to find the closest regions
     * @return a list of the closest 2 regions
     */
    private List<Region> findClosestRegions() {
        List<Region> regions = new ArrayList<>(Arrays.asList(Region.values()));
        regions.remove(currentRegion);
        for (Region region : Region.values()) {
            if (region.isVisited()) {
                regions.remove(region);
            }
        }
        regions.sort(Comparator.comparing(r -> Util.distanceBetween(currentRegion.getX(),
                currentRegion.getY(), r.getX(), r.getY())));
        List<Region> closest = new ArrayList<>();
        if (regions.size() < 2) {
            return regions;
        }
        for (int i = 0; i < 2; i++) {
            closest.add(regions.get(i));
        }
        return closest;
    }

    /**
     * helper method to draw a region on the canvas
     *
     * @param region the region to draw
     * @param closest the 2 closest regions
     */
    private void drawRegion(Region region, List<Region> closest) {
        Circle circle = new Circle(30);
        int x = region.getX();
        int y = region.getY();
        circle.setCenterX(x);
        circle.setCenterY(y);
        int dist = (int) Util.distanceBetween(region.getX(), region.getY(), currentRegion.getX(),
                currentRegion.getY());
        int fuelDist = (int) (dist * Math.min(1 - player.getPilotPoints() / 50.0, 0.5));
        if (region.equals(currentRegion)) {
            circle.setFill(Color.YELLOW);
        } else if (fuelDist > player.getShip().getCurrentFuel()) {
            circle.setFill(Color.RED);
        } else if (region.isVisited()) {
            circle.setFill(Color.LIGHTGREEN);
        } else {
            circle.setFill(Color.WHITE);
        }
        circle.setStroke(Color.BLACK);
        circle.setStrokeType(StrokeType.INSIDE);
        circle.setStrokeWidth(2);
        circle.setOnMouseClicked(e -> {
            loadNPCScreen(region, closest, fuelDist);
        });
        Text name = new Text(region.isVisited() ? region.getName() : "?\n" + dist + " AU");
        name.setTextAlignment(TextAlignment.CENTER);
        name.setFont(Font.font("Times", FontWeight.BOLD, 12));
        name.setFill(Color.BLACK);
        name.setX(x - name.getLayoutBounds().getWidth() / 2);
        name.setY(y - 8);
        name.setOnMouseClicked(e -> {
            loadNPCScreen(region, closest, fuelDist);
        });
        Text desc = new Text(region.isVisited() ? region.getX() + ", " + region.getY() + "\n "
                + dist + " AU" : "");
        desc.setFont(new Font(8));
        desc.setFill(Color.BLACK);
        desc.setX(x - desc.getLayoutBounds().getWidth() / 2);
        desc.setY(y + 4);
        desc.setOnMouseClicked(e -> {
            loadNPCScreen(region, closest, fuelDist);
        });
        getChildren().addAll(circle, name, desc);
    }

    /**
     * helper method to load the selected region's screen
     * @param region the region to load
     * @param closest the closest 2 regions
     * @param dist the distance to the region
     */
    private void loadRegionScreen(Region region, List<Region> closest, int dist) {
        if (region.isVisited() || closest.contains(region)) {
            RegionScreen screen = new RegionScreen(stage, player, region);
            if (!region.isVisited()) {
                Market market = new Market(region);
                market.adjustPrices(player);
                region.setMarket(market);
            }
            player.getShip().setCurrentFuel(player.getShip().getCurrentFuel() - dist);
            region.setVisited(true);
            getScene().setRoot(screen);
        }
    }

    /**
     * this loads the next region with possibility of NPCs
     * @param region region going to
     * @param closest List of regions
     * @param dist the distance to the region
     */
    private void loadNPCScreen(Region region, List<Region> closest, int dist) {
        if (region.equals(currentRegion)) {
            loadRegionScreen(region, closest, dist);
            return;
        }
        if (dist > player.getShip().getCurrentFuel()) {
            return;
        }
        if (region.isVisited() || closest.contains(region)) {
            Random rand = new Random();
            int randInt = rand.nextInt(100);
            if (randInt < 15) {
                new BanditAlert(Alert.AlertType.CONFIRMATION, stage, player, region, currentRegion);
            } else if (randInt < 30) {
                if (player.getShip().getInventory().size() != 0) {
                    new SpacePoliceAlert(Alert.AlertType.CONFIRMATION, stage, player, region,
                            currentRegion);
                } else {
                    loadRegionScreen(region, closest, dist);
                }
            } else if (randInt < 50) {
                new TraderAlert(Alert.AlertType.CONFIRMATION, stage, player, region, currentRegion);
            } else {
                loadRegionScreen(region, closest, dist);
            }
        }

    }

}
