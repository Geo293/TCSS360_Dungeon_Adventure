package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Serializable wrapper for the entire game state for saving/loading the game.
 * @author Carson Poirier
 * @version 11/8/25
 */
public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Hero hero;
    private final Dungeon dungeon;

    public GameState(Hero hero, Dungeon dungeon) {
        this.hero = hero;
        this.dungeon = dungeon;
    }

    public Hero getHero() { return hero; }
    public Dungeon getDungeon() { return dungeon; }
}
