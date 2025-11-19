package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Organized inventory that separates consumables from key items.
 *
 * @author Carson Poirier
 */
public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Item> consumables;
    private final List<Item> keyItems;

    public Inventory() {
        this.consumables = new ArrayList<>();
        this.keyItems = new ArrayList<>();
    }

    public void addItem(Item item) {
        if (item.isConsumable()) {
            consumables.add(item);
        } else {
            keyItems.add(item);
        }
    }

    public boolean removeItem(Item item) {
        return item.isConsumable() ? consumables.remove(item) : keyItems.remove(item);
    }

    public List<Item> getConsumables() {
        return new ArrayList<>(consumables);
    }

    public List<Item> getKeyItems() {
        return new ArrayList<>(keyItems);
    }

    public void clear() {
        consumables.clear();
        keyItems.clear();
    }

    @Override
    public String toString() {
        return "Consumables: " + consumables + "\nKey Items: " + keyItems;
    }
}
