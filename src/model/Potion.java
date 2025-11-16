package model;

import java.io.Serial;

/**
 * Class for potions(visibility, healing).
 * @author Carson Poirier
 * @version 11/15/25
 */

public class Potion extends Item {
    @Serial
    private static final long serialVersionUID = 1L;

    public Potion(String name) {
        super(name);
    }

    @Override
    public boolean isConsumable() {
        return true;
    }

    // Later: add effect logic (heal, vis, etc.)
}
