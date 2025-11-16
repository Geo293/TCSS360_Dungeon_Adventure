package model;

import java.io.Serial;

public class Pillar extends Item {
    @Serial
    private static final long serialVersionUID = 1L;

    public Pillar(String name) {
        super(name);
    }

    @Override
    public boolean isConsumable() {
        return false;
    }
}
