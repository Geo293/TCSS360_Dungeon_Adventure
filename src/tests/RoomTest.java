package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.Monster;
import model.Room;

public class RoomTest {
    private Room room;
    private Monster testMonster;

    @Before
    public void setUp() {
        room = new Room();
        // Create a test monster
        testMonster = new TestMonster();
    }

    // Test Monster implementation
    private static class TestMonster extends Monster {
        public TestMonster() {
            super("Test Monster", 100, 10, 20, 2, 0.7, 0.3, 5, 15);
        }
    }

    // ========== CONSTRUCTOR TESTS ==========
    @Test
    public void constructorInitializesDefaultValues() {
        assertFalse("North door should be false", room.hasNorthDoor());
        assertFalse("South door should be false", room.hasSouthDoor());
        assertFalse("East door should be false", room.hasEastDoor());
        assertFalse("West door should be false", room.hasWestDoor());
        assertFalse("Healing potion should be false", room.hasHealingPotion());
        assertFalse("Vision potion should be false", room.hasVisionPotion());
        assertFalse("Pit should be false", room.hasPit());
        assertFalse("Entrance should be false", room.isMyEntrance());
        assertFalse("Exit should be false", room.isMyExit());
        assertNull("Pillar should be null", room.getMyPillar());
        assertNull("Monster should be null", room.getMyMonster());
    }

    // ========== ENTRANCE/EXIT TESTS ==========
    @Test
    public void setEntranceClearsRoomAndPreventsExit() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        room.setMyPit(true);
        room.setMyPillar("A");
        room.setMyMonster(testMonster);
        room.setMyExit(true);

        room.setMyEntrance(true);

        assertTrue("Should be entrance", room.isMyEntrance());
        assertFalse("Exit should be cleared", room.isMyExit());
        assertFalse("Healing potion should be cleared", room.hasHealingPotion());
        assertFalse("Vision potion should be cleared", room.hasVisionPotion());
        assertFalse("Pit should be cleared", room.hasPit());
        assertNull("Pillar should be cleared", room.getMyPillar());
        assertNull("Monster should be cleared", room.getMyMonster());
    }

    @Test
    public void setExitClearsRoomAndPreventsEntrance() {
        // Note: There's a bug in setMyExit - it always sets myExit = true regardless of parameter
        room.setMyHealingPotion(true);
        room.setMyEntrance(true);

        room.setMyExit(true);

        assertTrue("Should be exit", room.isMyExit());
        assertFalse("Entrance should be cleared", room.isMyEntrance());
        assertFalse("Healing potion should be cleared", room.hasHealingPotion());
    }

    // ========== PILLAR TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void setPillarWithNullThrowsException() {
        room.setMyPillar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPillarWithEmptyStringThrowsException() {
        room.setMyPillar("");
    }

    @Test
    public void setValidPillar() {
        room.setMyPillar("A");
        assertEquals("Pillar should be set to A", "A", room.getMyPillar());

        room = new Room(); // Create fresh room
        room.setMyPillar("E");
        assertEquals("Pillar should be set to E", "E", room.getMyPillar());

        room = new Room();
        room.setMyPillar("I");
        assertEquals("Pillar should be set to I", "I", room.getMyPillar());

        room = new Room();
        room.setMyPillar("P");
        assertEquals("Pillar should be set to P", "P", room.getMyPillar());
    }

    // ========== MONSTER TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void setMonsterWithNullThrowsException() {
        room.setMyMonster(null);
    }

    @Test
    public void setValidMonster() {
        room.setMyMonster(testMonster);
        assertSame("Monster should be set", testMonster, room.getMyMonster());
    }

    // ========== DOOR SETTER TESTS ==========
    @Test
    public void doorSetters() {
        room.setMyNorthDoor(true);
        room.setMySouthDoor(true);
        room.setMyEastDoor(true);
        room.setMyWestDoor(true);

        assertTrue("North door should be set", room.hasNorthDoor());
        assertTrue("South door should be set", room.hasSouthDoor());
        assertTrue("East door should be set", room.hasEastDoor());
        assertTrue("West door should be set", room.hasWestDoor());

        // Test setting back to false
        room.setMyNorthDoor(false);
        assertFalse("North door should be cleared", room.hasNorthDoor());
    }

    // ========== ITEM SETTER TESTS ==========
    @Test
    public void itemSetters() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        room.setMyPit(true);

        assertTrue("Healing potion should be set", room.hasHealingPotion());
        assertTrue("Vision potion should be set", room.hasVisionPotion());
        assertTrue("Pit should be set", room.hasPit());

        // Test setting back to false
        room.setMyHealingPotion(false);
        assertFalse("Healing potion should be cleared", room.hasHealingPotion());
    }

    // ========== MULTIPLE ITEMS TESTS ==========
    @Test
    public void hasMultipleItemsWithOneItem() {
        room.setMyHealingPotion(true);
        assertFalse("One item should not count as multiple", room.hasMultipleItems());
    }

    @Test
    public void hasMultipleItemsWithTwoItems() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        assertTrue("Two items should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void hasMultipleItemsWithThreeItems() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        room.setMyPit(true);
        assertTrue("Three items should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void hasMultipleItemsWithPillarAndMonster() {
        room.setMyPillar("E");
        room.setMyMonster(testMonster);
        assertTrue("Pillar and monster should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void hasMultipleItemsWithPillarOnly() {
        room.setMyPillar("A");
        assertFalse("Pillar alone should not count as multiple", room.hasMultipleItems());
    }

    @Test
    public void hasMultipleItemsWithMonsterOnly() {
        room.setMyMonster(testMonster);
        assertFalse("Monster alone should not count as multiple", room.hasMultipleItems());
    }

    // ========== REMOVAL TESTS ==========
    @Test
    public void removeHealingPotion() {
        room.setMyHealingPotion(true);
        assertTrue("Healing potion should be present before removal", room.hasHealingPotion());

        room.removeHealingPotion();
        assertFalse("Healing potion should be removed", room.hasHealingPotion());
    }

    @Test
    public void removeVisionPotion() {
        room.setMyVisionPotion(true);
        assertTrue("Vision potion should be present before removal", room.hasVisionPotion());

        room.removeVisionPotion();
        assertFalse("Vision potion should be removed", room.hasVisionPotion());
    }

    @Test
    public void removeMonster() {
        room.setMyMonster(testMonster);
        assertNotNull("Monster should be present before removal", room.getMyMonster());

        room.removeMonster();
        assertNull("Monster should be removed", room.getMyMonster());
    }

    // ========== GETTER TESTS ==========
    @Test
    public void gettersReturnCorrectValues() {
        room.setMyNorthDoor(true);
        room.setMyEntrance(true);

        assertTrue("hasNorthDoor should return true", room.hasNorthDoor());
        assertTrue("isMyEntrance should return true", room.isMyEntrance());
    }

    // ========== SYMBOL TESTS ==========
    // IMPORTANT: Each test must use a fresh room to avoid multiple items

    @Test
    public void getRoomSymbolForEntrance() {
        Room testRoom = new Room();
        testRoom.setMyEntrance(true);
        String roomString = testRoom.toString();
        // The center character (position 4 in a 3x3 grid with newlines) should be 'i'
        // String format: row0:3chars+\n, row1:3chars+\n, row2:3chars+\n
        // Center is at index 5 (0-indexed: row1[1])
        assertEquals("Symbol should be 'i' for entrance", 'i', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForExit() {
        Room testRoom = new Room();
        testRoom.setMyExit(true);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'O' for exit", 'O', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForPit() {
        Room testRoom = new Room();
        testRoom.setMyPit(true);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'X' for pit", 'X', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForHealingPotion() {
        Room testRoom = new Room();
        testRoom.setMyHealingPotion(true);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'H' for healing potion", 'H', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForVisionPotion() {
        Room testRoom = new Room();
        testRoom.setMyVisionPotion(true);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'V' for vision potion", 'V', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForMultipleItems() {
        Room testRoom = new Room();
        testRoom.setMyHealingPotion(true);
        testRoom.setMyVisionPotion(true);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'M' for multiple items", 'M', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForPillar() {
        Room testRoom = new Room();
        testRoom.setMyPillar("A");
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'A' for pillar A", 'A', roomString.charAt(5));

        testRoom = new Room();
        testRoom.setMyPillar("E");
        roomString = testRoom.toString();
        assertEquals("Symbol should be 'E' for pillar E", 'E', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForMonster() {
        Room testRoom = new Room();
        testRoom.setMyMonster(testMonster);
        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'T' for monster", 'T', roomString.charAt(5));
    }

    @Test
    public void getRoomSymbolForEmptyRoom() {
        Room testRoom = new Room();
        String roomString = testRoom.toString();
        assertEquals("Symbol should be space for empty room", ' ', roomString.charAt(5));
    }

    // ========== PRIORITY TESTS ==========
    @Test
    public void pitTakesPriorityOverOtherItems() {
        Room testRoom = new Room();
        testRoom.setMyPit(true);
        testRoom.setMyHealingPotion(true); // Should be ignored if pit exists
        testRoom.setMyVisionPotion(true);  // Should be ignored if pit exists

        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'X' for pit even with other items", 'X', roomString.charAt(5));
    }

    @Test
    public void entranceAndExitHavePriority() {
        Room testRoom = new Room();
        testRoom.setMyEntrance(true);
        testRoom.setMyHealingPotion(true); // Should be ignored if entrance
        testRoom.setMyVisionPotion(true);  // Should be ignored if entrance

        String roomString = testRoom.toString();
        assertEquals("Symbol should be 'i' for entrance even with other items", 'i', roomString.charAt(5));

        testRoom = new Room();
        testRoom.setMyExit(true);
        testRoom.setMyHealingPotion(true); // Should be ignored if exit

        roomString = testRoom.toString();
        assertEquals("Symbol should be 'O' for exit even with other items", 'O', roomString.charAt(5));
    }

    // ========== GENERATE CONTENTS TESTS ==========
    @Test
    public void generateContentsDoesNothingForEntrance() {
        room.setMyEntrance(true);
        room.generateContents();
        assertFalse("Entrance should have no pit", room.hasPit());
        assertFalse("Entrance should have no healing potion", room.hasHealingPotion());
        assertFalse("Entrance should have no vision potion", room.hasVisionPotion());
    }

    @Test
    public void generateContentsDoesNothingForExit() {
        room.setMyExit(true);
        room.generateContents();
        assertFalse("Exit should have no pit", room.hasPit());
        assertFalse("Exit should have no healing potion", room.hasHealingPotion());
        assertFalse("Exit should have no vision potion", room.hasVisionPotion());
    }

    @Test
    public void clearRoomMethod() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        room.setMyPit(true);
        room.setMyPillar("P");
        room.setMyMonster(testMonster);

        room.clearRoom();

        assertFalse("Healing potion should be cleared", room.hasHealingPotion());
        assertFalse("Vision potion should be cleared", room.hasVisionPotion());
        assertFalse("Pit should be cleared", room.hasPit());
        assertNull("Pillar should be cleared", room.getMyPillar());
        assertNull("Monster should be cleared", room.getMyMonster());
    }

    // ========== BUG IDENTIFICATION TESTS ==========
    @Test
    public void setExitBug() {
        // This test documents the bug in setMyExit
        // The method always sets myExit = true regardless of the parameter
        room.setMyExit(false);
        // According to the bug, this should still set myExit to true
        assertTrue("Bug: setMyExit(false) still sets exit to true", room.isMyExit());
    }
}