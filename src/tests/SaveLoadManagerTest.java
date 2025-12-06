package tests;

import controller.SaveLoadManager;
import model.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for SaveLoadManager.
 * @author Carson Poirier
 * @version 12/5/25
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SaveLoadManagerTest {

    private static final String SAVE_FILE = "game_save.dat";

    @BeforeEach
    void cleanSaveFile() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }


    @Test
    @Order(1)
    void testSaveAndLoadWarrior() {
        Hero hero = new Warrior("WarriorHero");
        Dungeon dungeon = new Dungeon(4, 4);
        GameState state = new GameState(hero, dungeon, "Warrior");

        SaveLoadManager.saveGame(state);
        GameState loaded = SaveLoadManager.loadGame();

        assertNotNull(loaded);
        assertEquals("Warrior", loaded.getCharacterName());
        assertEquals("WarriorHero", loaded.getHero().getMyName());
    }

    @Test
    @Order(2)
    void testSaveAndLoadPriestess() {
        Hero hero = new Priestess("PriestessHero");
        Dungeon dungeon = new Dungeon(5, 5);
        GameState state = new GameState(hero, dungeon, "Priestess");

        SaveLoadManager.saveGame(state);
        GameState loaded = SaveLoadManager.loadGame();

        assertNotNull(loaded);
        assertEquals("Priestess", loaded.getCharacterName());
        assertEquals("PriestessHero", loaded.getHero().getMyName());
    }

    @Test
    @Order(3)
    void testSaveAndLoadThief() {
        Hero hero = new Thief("ThiefHero");
        Dungeon dungeon = new Dungeon(3, 3);
        GameState state = new GameState(hero, dungeon, "Thief");

        SaveLoadManager.saveGame(state);
        GameState loaded = SaveLoadManager.loadGame();

        assertNotNull(loaded);
        assertEquals("Thief", loaded.getCharacterName());
        assertEquals("ThiefHero", loaded.getHero().getMyName());
    }



    @Test
    @Order(5)
    void testLoadWhenNoFileExists() {
        File file = new File(SAVE_FILE);
        if (file.exists()) file.delete();

        GameState loaded = SaveLoadManager.loadGame();
        assertNull(loaded);
    }

    @Test
    @Order(6)
    void testLoadCorruptedFile() throws IOException {
        // Write junk data to the save file
        try (FileOutputStream fos = new FileOutputStream(SAVE_FILE)) {
            fos.write(new byte[]{0x00, 0x01, 0x02});
        }

        GameState loaded = SaveLoadManager.loadGame();
        assertNull(loaded);
    }
}