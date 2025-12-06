package tests;


import model.*;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for GameState using multiple Hero subclasses.
 */
public class GameStateTest {

    //  Constructor & Getter Tests
    @Test
    void testConstructorWithWarrior() {
        Hero hero = new Warrior("WarriorHero");
        Dungeon dungeon = new Dungeon(4, 4);
        GameState state = new GameState(hero, dungeon, "Warrior");

        assertEquals(hero, state.getHero());
        assertEquals(dungeon, state.getDungeon());
        assertEquals("Warrior", state.getCharacterName());
    }

    @Test
    void testConstructorWithPriestess() {
        Hero hero = new Priestess("PriestessHero");
        Dungeon dungeon = new Dungeon(5, 5);
        GameState state = new GameState(hero, dungeon, "Priestess");

        assertEquals(hero, state.getHero());
        assertEquals(dungeon, state.getDungeon());
        assertEquals("Priestess", state.getCharacterName());
    }

    @Test
    void testConstructorWithThief() {
        Hero hero = new Thief("ThiefHero");
        Dungeon dungeon = new Dungeon(3, 3);
        GameState state = new GameState(hero, dungeon, "Thief");

        assertEquals(hero, state.getHero());
        assertEquals(dungeon, state.getDungeon());
        assertEquals("Thief", state.getCharacterName());
    }


    //  Serialization & Deserialization
    @Test
    void testSerializationWithWarrior() throws IOException, ClassNotFoundException {
        Hero hero = new Warrior("SerializeWarrior");
        Dungeon dungeon = new Dungeon(4, 4);
        GameState original = new GameState(hero, dungeon, "Warrior");

        GameState deserialized = serializeAndDeserialize(original);

        assertNotNull(deserialized);
        assertEquals("Warrior", deserialized.getCharacterName());
        assertEquals("SerializeWarrior", deserialized.getHero().getMyName());
    }

    @Test
    void testSerializationWithPriestess() throws IOException, ClassNotFoundException {
        Hero hero = new Priestess("SerializePriestess");
        Dungeon dungeon = new Dungeon(5, 5);
        GameState original = new GameState(hero, dungeon, "Priestess");

        GameState deserialized = serializeAndDeserialize(original);

        assertNotNull(deserialized);
        assertEquals("Priestess", deserialized.getCharacterName());
        assertEquals("SerializePriestess", deserialized.getHero().getMyName());
    }

    @Test
    void testSerializationWithThief() throws IOException, ClassNotFoundException {
        Hero hero = new Thief("SerializeThief");
        Dungeon dungeon = new Dungeon(3, 3);
        GameState original = new GameState(hero, dungeon, "Thief");

        GameState deserialized = serializeAndDeserialize(original);

        assertNotNull(deserialized);
        assertEquals("Thief", deserialized.getCharacterName());
        assertEquals("SerializeThief", deserialized.getHero().getMyName());
    }

    //  Edge Case: Corrupted Data
    @Test
    void testDeserializationFailsOnCorruptedData() {
        byte[] corrupted = {0x00, 0x01, 0x02}; // invalid stream
        ByteArrayInputStream bis = new ByteArrayInputStream(corrupted);

        assertThrows(IOException.class, () -> {
            ObjectInputStream in = new ObjectInputStream(bis);
            in.readObject();
        });
    }

    // Helper Method
    private GameState serializeAndDeserialize(GameState original)
            throws IOException, ClassNotFoundException {
        // Serialize to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(original);
        out.flush();

        // Deserialize from byte array
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        return (GameState) in.readObject();
    }
}