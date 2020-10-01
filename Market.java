
import java.util.*;

public class Market {

    private Player player;
    private Ship ship;
    private Region parent;
    private Map<Item, Integer> items;

    /**
     * Creates new Market
     * @param parent Region that Market belongs to
     */
    public Market(Region parent) {
        this.parent = parent;
        items = new HashMap<>();

        for (Item item : Item.values()) {
            Random random = new Random();
            double variance = random.nextDouble();
            //now to determine if item will be in region since
            // not all regions have all items
            if (parent.getTechLevel().equals("Very High")) {
                if (items.size() > 8) {
                    break;
                }
                if (item.getTechLevel().equals("Very High")) {
                    if (variance < .8) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("High")) {
                    if (variance < .7) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Medium")) {
                    if (variance < .6) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Low")) {
                    if (variance < .5) {
                        items.put(item, item.getBasePrice());
                    }
                }
            } else if (parent.getTechLevel().equals("High")) {
                variance = random.nextDouble();
                if (item.getTechLevel().equals("High")) {
                    if (variance < .8) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Medium")) {
                    if (variance < .7) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Low")) {
                    if (variance < .5) {
                        items.put(item, item.getBasePrice());
                    }
                }
            } else if (parent.getTechLevel().equals("Medium")) {
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Medium")) {
                    if (variance < .8) {
                        items.put(item, item.getBasePrice());
                    }
                }
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Low")) {
                    if (variance < .7) {
                        items.put(item, item.getBasePrice());
                    }
                }
            } else {
                variance = random.nextDouble();
                if (item.getTechLevel().equals("Low")) {
                    if (variance < .75) {
                        items.put(item, item.getBasePrice());
                    }
                }
            }
        }
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }

    /**
     * must use this method when creating a new Marketplace for a new region
     * since it depends on a player, it can not be in the constructor
     * @param player Player who is buying
     */
    public void adjustPrices(Player player) {
        for (Map.Entry<Item, Integer> item : items.entrySet()) {
            Random random = new Random();
            //randomizes price with maximums variability
            int price = (int) (item.getKey().getBasePrice()
                    //of +/- 7.5% of price
                    + item.getKey().getBasePrice() * .15 * (.5 - Math.abs(random.nextGaussian())));
            //makes things cheaper for people with merchant points
            price = price - (int) (player.getMerchantPoints() * .1 * item.getKey().getBasePrice()
                    * .15 * Math.abs(.5 - Math.abs(random.nextGaussian())));
            item.setValue(price);
        }
    }

}
