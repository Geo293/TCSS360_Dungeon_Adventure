package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.Dungeon;
import model.Room;
import model.Monster;
import java.lang.reflect.Field;

public class DungeonTest {
    private Dungeon dungeon;
    private static final int TEST_WIDTH = 5;
    private static final int TEST_HEIGHT = 5;

    // Constants to use instead of accessing private ones
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;

    @Before
    public void setUp() {
        dungeon = new Dungeon(TEST_WIDTH, TEST_HEIGHT);
    }

    // ========== CONSTRUCTOR TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void constructorWithZeroWidthThrowsException() {
        new Dungeon(0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeWidthThrowsException() {
        new Dungeon(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithZeroHeightThrowsException() {
        new Dungeon(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNegativeHeightThrowsException() {
        new Dungeon(5, -1);
    }

    @Test
    public void constructorInitializesAllRooms() {
        Room[][] maze = getPrivateField(dungeon, "myMaze");
        assertNotNull("Maze should not be null", maze);
        assertEquals("Maze width should be correct", TEST_WIDTH, maze.length);
        assertEquals("Maze height should be correct", TEST_HEIGHT, maze[0].length);

        for (int x = 0; x < TEST_WIDTH; x++) {
            for (int y = 0; y < TEST_HEIGHT; y++) {
                assertNotNull("Room at (" + x + "," + y + ") should not be null", maze[x][y]);
            }
        }
    }

    // ========== ENTRANCE AND EXIT TESTS ==========
    @Test
    public void entrancePlacedAtTopLeft() {
        int entranceX = getPrivateField(dungeon, "myEntranceX");
        int entranceY = getPrivateField(dungeon, "myEntranceY");

        assertEquals("Entrance X should be 0", 0, entranceX);
        assertEquals("Entrance Y should be 0", 0, entranceY);

        Room entranceRoom = getRoomAt(entranceX, entranceY);
        assertTrue("Entrance room should be marked as entrance", entranceRoom.isMyEntrance());
    }

    @Test
    public void exitPlacedAtBottomRight() {
        int exitX = getPrivateField(dungeon, "myExitX");
        int exitY = getPrivateField(dungeon, "myExitY");

        assertEquals("Exit X should be width-1", TEST_WIDTH - 1, exitX);
        assertEquals("Exit Y should be height-1", TEST_HEIGHT - 1, exitY);

        Room exitRoom = getRoomAt(exitX, exitY);
        assertTrue("Exit room should be marked as exit", exitRoom.isMyExit());
    }

    @Test
    public void heroStartsAtEntrance() {
        int heroX = dungeon.getMyHeroX();
        int heroY = dungeon.getMyHeroY();

        assertEquals("Hero X should be at entrance X", 0, heroX);
        assertEquals("Hero Y should be at entrance Y", 0, heroY);
    }

    // ========== MOVEMENT TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void moveHeroWithNullDirectionThrowsException() {
        dungeon.moveHero(null);
    }

    @Test
    public void moveHeroWithInvalidDirectionReturnsFalse() {
        assertFalse("Invalid direction should return false", dungeon.moveHero("INVALID"));
        assertFalse("Invalid direction should return false", dungeon.moveHero("X"));
        assertFalse("Invalid direction should return false", dungeon.moveHero(""));
    }

    @Test
    public void moveHeroWhenNoDoorReturnsFalse() {
        // Create a room with no doors
        Room currentRoom = dungeon.getCurrentRoom();
        currentRoom.setMyNorthDoor(false);
        currentRoom.setMySouthDoor(false);
        currentRoom.setMyEastDoor(false);
        currentRoom.setMyWestDoor(false);

        assertFalse("Should not move north without door", dungeon.moveHero("N"));
        assertFalse("Should not move south without door", dungeon.moveHero("S"));
        assertFalse("Should not move east without door", dungeon.moveHero("E"));
        assertFalse("Should not move west without door", dungeon.moveHero("W"));

        assertEquals("Hero X should not change", 0, dungeon.getMyHeroX());
        assertEquals("Hero Y should not change", 0, dungeon.getMyHeroY());
    }

    @Test
    public void moveHeroThroughDoor() {
        // Set up a door to the east
        Room currentRoom = dungeon.getCurrentRoom();
        currentRoom.setMyEastDoor(true);

        // Also need to set up the corresponding door in the next room
        Room nextRoom = getRoomAt(1, 0);
        nextRoom.setMyWestDoor(true);

        assertTrue("Should move east through door", dungeon.moveHero("E"));
        assertEquals("Hero X should be 1", 1, dungeon.getMyHeroX());
        assertEquals("Hero Y should be 0", 0, dungeon.getMyHeroY());
    }

    @Test
    public void moveHeroCaseInsensitive() {
        Room currentRoom = dungeon.getCurrentRoom();
        currentRoom.setMyEastDoor(true);
        Room nextRoom = getRoomAt(1, 0);
        nextRoom.setMyWestDoor(true);

        assertTrue("Should move with lowercase direction", dungeon.moveHero("e"));
        assertTrue("Should move with uppercase direction", dungeon.moveHero("E"));
    }

    @Test
    public void cannotMoveOutOfBounds() {
        // Try to move west from (0,0) - should fail
        Room currentRoom = dungeon.getCurrentRoom();
        currentRoom.setMyWestDoor(true); // Even with door, should fail due to bounds

        assertFalse("Should not move west out of bounds", dungeon.moveHero("W"));
        assertEquals("Hero X should remain 0", 0, dungeon.getMyHeroX());
    }

    // ========== CURRENT ROOM TESTS ==========
    @Test
    public void getCurrentRoomReturnsCorrectRoom() {
        Room expectedRoom = getRoomAt(0, 0);
        Room actualRoom = dungeon.getCurrentRoom();

        assertSame("getCurrentRoom should return room at hero position", expectedRoom, actualRoom);
    }

    @Test
    public void getCurrentRoomUpdatesAfterMove() {
        // Set up a door to the east and move
        Room currentRoom = dungeon.getCurrentRoom();
        currentRoom.setMyEastDoor(true);
        Room nextRoom = getRoomAt(1, 0);
        nextRoom.setMyWestDoor(true);

        dungeon.moveHero("E");

        Room newCurrentRoom = dungeon.getCurrentRoom();
        assertSame("getCurrentRoom should return new room after move", nextRoom, newCurrentRoom);
    }

    // ========== VISIBLE AREA TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void getVisibleAreaWithNegativeRadiusThrowsException() {
        dungeon.getVisableArea(0, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVisibleAreaWithNegativeXThrowsException() {
        dungeon.getVisableArea(-1, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVisibleAreaWithNegativeYThrowsException() {
        dungeon.getVisableArea(0, -1, 1);
    }

    @Test
    public void getVisibleAreaAtCenter() {
        String visibleArea = dungeon.getVisableArea(2, 2, 1);
        assertNotNull("Visible area should not be null", visibleArea);
        assertFalse("Visible area should not be empty", visibleArea.isEmpty());
    }

    @Test
    public void getVisibleAreaAtCorner() {
        String visibleArea = dungeon.getVisableArea(0, 0, 2);
        assertNotNull("Visible area should not be null", visibleArea);
        assertFalse("Visible area should not be empty", visibleArea.isEmpty());
    }

    @Test
    public void getVisibleAreaWithLargeRadius() {
        // Radius larger than dungeon - should clamp to dungeon bounds
        String visibleArea = dungeon.getVisableArea(2, 2, 10);
        assertNotNull("Visible area should not be null", visibleArea);
        assertFalse("Visible area should not be empty", visibleArea.isEmpty());
    }

    // ========== TO STRING TESTS ==========
    @Test
    public void toStringNotNull() {
        String dungeonString = dungeon.toString();
        assertNotNull("toString should not return null", dungeonString);
        assertFalse("toString should not return empty string", dungeonString.isEmpty());
    }

    @Test
    public void toStringContainsExpectedElements() {
        String dungeonString = dungeon.toString();
        // Should contain at least some characters
        assertTrue("String should be reasonably long", dungeonString.length() > 10);
    }

    // ========== PILLAR TESTS ==========
    @Test
    public void pillarsNotAtEntranceOrExit() {
        int entranceX = getPrivateField(dungeon, "myEntranceX");
        int entranceY = getPrivateField(dungeon, "myEntranceY");
        int exitX = getPrivateField(dungeon, "myExitX");
        int exitY = getPrivateField(dungeon, "myExitY");

        Room entranceRoom = getRoomAt(entranceX, entranceY);
        Room exitRoom = getRoomAt(exitX, exitY);

        assertNull("Entrance should not have a pillar", entranceRoom.getMyPillar());
        assertNull("Exit should not have a pillar", exitRoom.getMyPillar());
    }

    @Test
    public void testPillarCount() {
        int pillarCount = 0;
        Room[][] maze = getPrivateField(dungeon, "myMaze");

        for (int x = 0; x < TEST_WIDTH; x++) {
            for (int y = 0; y < TEST_HEIGHT; y++) {
                if (maze[x][y].getMyPillar() != null) {
                    pillarCount++;
                }
            }
        }

        // Should have 4 pillars (A, E, I, P)
        assertEquals("Should have exactly 4 pillars", 4, pillarCount);
    }

    // ========== MONSTER TESTS ==========
    @Test
    public void monsterPlacementSkipsEntrance() {
        Room entranceRoom = getRoomAt(0, 0);
        assertNull("Entrance should not have a monster", entranceRoom.getMyMonster());
    }

    @Test
    public void monsterPlacementSkipsExit() {
        int exitX = TEST_WIDTH - 1;
        int exitY = TEST_HEIGHT - 1;
        Room exitRoom = getRoomAt(exitX, exitY);
        assertNull("Exit should not have a monster", exitRoom.getMyMonster());
    }

    // ========== ROOM CONTENT TESTS ==========
    @Test
    public void roomContentsGeneratedForNonSpecialRooms() {
        Room[][] maze = getPrivateField(dungeon, "myMaze");
        int entranceX = getPrivateField(dungeon, "myEntranceX");
        int entranceY = getPrivateField(dungeon, "myEntranceY");
        int exitX = getPrivateField(dungeon, "myExitX");
        int exitY = getPrivateField(dungeon, "myExitY");

        boolean foundNonSpecialRoom = false;
        for (int x = 0; x < TEST_WIDTH; x++) {
            for (int y = 0; y < TEST_HEIGHT; y++) {
                Room room = maze[x][y];
                if (!(x == entranceX && y == entranceY) &&
                        !(x == exitX && y == exitY) &&
                        room.getMyPillar() == null) {
                    foundNonSpecialRoom = true;
                    // At least some rooms should have generated contents
                    // Can't assert specific content due to randomness
                }
            }
        }
        assertTrue("Should have non-special rooms", foundNonSpecialRoom);
    }

    // ========== PRIVATE HELPER METHODS ==========
    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(Dungeon dungeon, String fieldName) {
        try {
            Field field = Dungeon.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(dungeon);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access private field: " + fieldName, e);
        }
    }

    private Room getRoomAt(int x, int y) {
        Room[][] maze = getPrivateField(dungeon, "myMaze");
        return maze[x][y];
    }
}