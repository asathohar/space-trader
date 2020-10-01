import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * JavaFX utilities
 */
public class Util {
    /**
     * returns a formatted text
     * @param text - String, words that will be displayed
     * @param fontSize - int, size of font that will be displayed
     * @param color - Color, color of words
     * @return text - Text, a formatted text
     */
    public static Text generateText(String text, int fontSize, Color color) {
        Text label = new Text(text);
        label.setFont(new Font(fontSize));
        label.setFill(color);
        return label;
    }

    /**
     * sets index of pane
     * @param pane - Gridpane, pane to be worked in
     * @param node - Node
     * @param row - int
     * @param col - int
     */
    public static void setIndex(GridPane pane, Node node, int row, int col) {
        pane.setRowIndex(node, row);
        pane.setColumnIndex(node, col);
    }

    /**
     * Returns the Euclidean distance between two points
     * @param x1 the first x coordinate
     * @param y1 the first y coordinate
     * @param x2 the second x coordinate
     * @param y2 the second y coordinate
     * @return the distance between the two points
     */
    public static double distanceBetween(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static int calcBuyPriceByTechLevel(String techLevel, Item item) {
        switch (techLevel) {
        case "Low":
            return (int) Math.round(item.getBuyPrice() * 0.75);
        case "Medium":
            return item.getBuyPrice();
        case "High":
            return (int) Math.round(item.getBuyPrice() * 1.25);
        default:
            return (int) Math.round(item.getBuyPrice() * 1.5);
        }
    }

    public static int calcSellPriceByTechLevel(String techLevel, Item item) {
        switch (techLevel) {
        case "Low":
            return (int) Math.round(item.getSellPrice() * 0.75);
        case "Medium":
            return item.getSellPrice();
        case "High":
            return (int) Math.round(item.getSellPrice() * 1.25);
        default:
            return (int) Math.round(item.getSellPrice() * 1.5);
        }
    }

}
