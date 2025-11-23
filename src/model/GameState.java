package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Serializable wrapper for the entire game state for saving and loading.
 * Holds references to the current hero and dungeon.
 *
 * @author Carson Poirier
 * @version 11/15/25
 */
public final class GameState implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** The hero in the current game state. */
    private final Hero myHero;

    /** The dungeon in the current game state. */
    private final Dungeon myDungeon;

    /**
     * Constructs a GameState with the given hero and dungeon.
     *
     * @param theHero    the hero to save
     * @param theDungeon the dungeon to save
     */
    public GameState(final Hero theHero, final Dungeon theDungeon) {
        myHero = theHero;
        myDungeon = theDungeon;
    }

    /**
     * Returns the hero in this game state.
     *
     * @return the hero
     */
    public Hero getHero() {
        return myHero;
    }

    /**
     * Returns the dungeon in this game state.
     *
     * @return the dungeon
     */
    public Dungeon getDungeon() {
        return myDungeon;
    }
}
