package model;
import java.io.*;

/**
 * Allows the game to be saved/loaded even after the game is closed. The
 * GameState is written to a file and the file can be read to load the game back
 *
 * @author Carson Poirier
 * @version 11/23/25
 */

public final class SaveLoadManager {

    private static final String SAVE_FILE = "game_save.dat";

    /**
     * Saves the current game state to disk.
     *
     * @param theGameState the game state to save
     */
    public static void saveGame(final GameState theGameState) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(theGameState);
            System.out.println("Game saved successfully.");
        } catch (IOException theException) {
            System.err.println("Error saving game: " + theException.getMessage());
        }
    }

    /**
     * Loads the game state from disk.
     *
     * @return the loaded GameState, or null if loading fails
     */
    public static GameState loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException theException) {
            System.err.println("Error loading game: " + theException.getMessage());
            return null;
        }
    }
}
