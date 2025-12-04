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
        // Create a concrete Monster instance using an anonymous class
        testMonster = new Monster("Test Monster", 100, 10, 20, 2, 0.7, 0.3, 5, 15) {
            // Anonymous subclass of Monster for testing
        };
    }

    // ========== CONSTRUCTOR TESTS ==========
    @Test
    public void testConstructorInitializesDefaultValues() {
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
    public void testSetEntranceClearsRoomAndPreventsExit() {
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
    public void testSetExitClearsRoomAndPreventsEntrance() {
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
    public void testSetPillarWithNullThrowsException() {
        room.setMyPillar(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPillarWithEmptyStringThrowsException() {
        room.setMyPillar("");
    }

    @Test
    public void testSetValidPillar() {
        room.setMyPillar("A");
        assertEquals("Pillar should be set to A", "A", room.getMyPillar());

        room.setMyPillar("E");
        assertEquals("Pillar should be set to E", "E", room.getMyPillar());

        room.setMyPillar("I");
        assertEquals("Pillar should be set to I", "I", room.getMyPillar());

        room.setMyPillar("P");
        assertEquals("Pillar should be set to P", "P", room.getMyPillar());
    }

    // ========== MONSTER TESTS ==========
    @Test(expected = IllegalArgumentException.class)
    public void testSetMonsterWithNullThrowsException() {
        room.setMyMonster(null);
    }

    @Test
    public void testSetValidMonster() {
        room.setMyMonster(testMonster);
        assertSame("Monster should be set", testMonster, room.getMyMonster());
    }

    // ========== DOOR SETTER TESTS ==========
    @Test
    public void testDoorSetters() {
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
    public void testItemSetters() {
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
    public void testHasMultipleItemsWithOneItem() {
        room.setMyHealingPotion(true);
        assertFalse("One item should not count as multiple", room.hasMultipleItems());
    }

    @Test
    public void testHasMultipleItemsWithTwoItems() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        assertTrue("Two items should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void testHasMultipleItemsWithThreeItems() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        room.setMyPit(true);
        assertTrue("Three items should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void testHasMultipleItemsWithPillarAndMonster() {
        room.setMyPillar("E");
        room.setMyMonster(testMonster);
        assertTrue("Pillar and monster should count as multiple", room.hasMultipleItems());
    }

    @Test
    public void testHasMultipleItemsWithPillarOnly() {
        room.setMyPillar("A");
        assertFalse("Pillar alone should not count as multiple", room.hasMultipleItems());
    }

    @Test
    public void testHasMultipleItemsWithMonsterOnly() {
        room.setMyMonster(testMonster);
        assertFalse("Monster alone should not count as multiple", room.hasMultipleItems());
    }

    // ========== REMOVAL TESTS ==========
    @Test
    public void testRemoveHealingPotion() {
        room.setMyHealingPotion(true);
        assertTrue("Healing potion should be present before removal", room.hasHealingPotion());

        room.removeHealingPotion();
        assertFalse("Healing potion should be removed", room.hasHealingPotion());
    }

    @Test
    public void testRemoveVisionPotion() {
        room.setMyVisionPotion(true);
        assertTrue("Vision potion should be present before removal", room.hasVisionPotion());

        room.removeVisionPotion();
        assertFalse("Vision potion should be removed", room.hasVisionPotion());
    }

    @Test
    public void testRemoveMonster() {
        room.setMyMonster(testMonster);
        assertNotNull("Monster should be present before removal", room.getMyMonster());

        room.removeMonster();
        assertNull("Monster should be removed", room.getMyMonster());
    }

    // ========== GETTER TESTS ==========
    @Test
    public void testGettersReturnCorrectValues() {
        room.setMyNorthDoor(true);
        room.setMyHealingPotion(true);
        room.setMyPillar("I");
        room.setMyEntrance(true);

        assertTrue("hasNorthDoor should return true", room.hasNorthDoor());
        assertTrue("hasHealingPotion should return true", room.hasHealingPotion());
        assertEquals("getMyPillar should return I", "I", room.getMyPillar());
        assertTrue("isMyEntrance should return true", room.isMyEntrance());
    }

    // ========== SYMBOL TESTS ==========
    @Test
    public void testGetRoomSymbolForEntrance() {
        room.setMyEntrance(true);
        String roomString = room.toString();
        // The center character (position 4 in a 3x3 grid with newlines) should be 'i'
        assertEquals("Symbol should be 'i' for entrance", 'i', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForExit() {
        room.setMyExit(true);
        String roomString = room.toString();
        assertEquals("Symbol should be 'O' for exit", 'O', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForPit() {
        room.setMyPit(true);
        String roomString = room.toString();
        assertEquals("Symbol should be 'X' for pit", 'X', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForHealingPotion() {
        room.setMyHealingPotion(true);
        String roomString = room.toString();
        assertEquals("Symbol should be 'H' for healing potion", 'H', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForVisionPotion() {
        room.setMyVisionPotion(true);
        String roomString = room.toString();
        assertEquals("Symbol should be 'V' for vision potion", 'V', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForMultipleItems() {
        room.setMyHealingPotion(true);
        room.setMyVisionPotion(true);
        String roomString = room.toString();
        assertEquals("Symbol should be 'M' for multiple items", 'M', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForPillar() {
        room.setMyPillar("A");
        String roomString = room.toString();
        assertEquals("Symbol should be 'A' for pillar A", 'A', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForMonster() {
        room.setMyMonster(testMonster);
        String roomString = room.toString();
        assertEquals("Symbol should be 'T' for monster", 'T', roomString.charAt(4));
    }

    @Test
    public void testGetRoomSymbolForEmptyRoom() {
        String roomString = room.toString();
        assertEquals("Symbol should be space for empty room", ' ', roomString.charAt(4));
    }

    // ========== GENERATE CONTENTS TESTS ==========
    @Test
    public void testGenerateContentsDoesNothingForEntrance() {
        room.setMyEntrance(true);
        room.generateContents();
        assertFalse("Entrance should have no pit", room.hasPit());
        assertFalse("Entrance should have no healing potion", room.hasHealingPotion());
        assertFalse("Entrance should have no vision potion", room.hasVisionPotion());
    }

    @Test
    public void testGenerateContentsDoesNothingForExit() {
        room.setMyExit(true);
        room.generateContents();
        assertFalse("Exit should have no pit", room.hasPit());
        assertFalse("Exit should have no healing potion", room.hasHealingPotion());
        assertFalse("Exit should have no vision potion", room.hasVisionPotion());
    }

    @Test
    public void testClearRoomMethod() {
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
    public void testSetExitBug() {
        // This test documents the bug in setMyExit
        // The method always sets myExit = true regardless of the parameter
        room.setMyExit(false);
        // According to the bug, this should still set myExit to true
        assertTrue("Bug: setMyExit(false) still sets exit to true", room.isMyExit());
    }
}