package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Base class for all items in the game.
 * @author Carson Poirier
 * @version 11/15/25
 */
public abstract class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Whether the item can be consumed/used.
     */
    public abstract boolean isConsumable();
}
