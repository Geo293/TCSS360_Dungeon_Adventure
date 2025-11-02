package model;

/**
 * This class creates a room design that will randomly place objects
 * throughout the room for the player to interact with.
 * @author Justin Yee
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Room {
    //Constants
    /**Spawn chance for the Healing Potion.*/
    private static final double HEALING_POTION_SPAWN_CHANCE = 0.15;
    /**Spawn chance for the Healing Potion.*/
    private static final double VISION_POTION_SPAWN_CHANCE = 0.10;
    /**Spawn chance for the Healing Potion.*/
    private static final double PIT_SPAWN_CHANCE = 0.10;
    //Constants for the toString
    private static final char WALL_CHARACTER = '*'; // default is '*' but '█' is easy to read
    private static final boolean SHOW_DOORS = true;


    // Instance Variables of the Room
    private boolean northDoor;
    private boolean southDoor;
    private boolean eastDoor;
    private boolean westDoor;
    private boolean healingPotion;
    private boolean visionPotion;
    private boolean pit;
    private boolean entrance;
    private boolean exit;
    private String pillar; // null if no pillar, or "A", "E", "I", "P"
    private Monster monster;

    /**
     * This is the constructor for the Room class.
     */
    public Room(){
        // Initializing the variables with default vals
        this.northDoor = false;
        this.southDoor = false;
        this.eastDoor = false;
        this.westDoor = false;
        this.healingPotion = false;
        this.visionPotion = false;
        this.pit = false;
        this.entrance = false;
        this.exit = false;
        this.pillar = null;
        this.monster = null;
    }

    /**
     * This method will generate what items the room has.
     */
    public void generateContents(){
        if (entrance || exit) {
            return;
        }
        //if there is a pit then there will be no other items
        if (Math.random() < PIT_SPAWN_CHANCE) {
            this.pit = true;
            return;
        }

        if (Math.random() < HEALING_POTION_SPAWN_CHANCE) {
            this.healingPotion = true;
        }

        if (Math.random() < VISION_POTION_SPAWN_CHANCE) {
            this.visionPotion = true;
        }
    }

    /**
     * This method is used to set the room to an entrance.
     *
     * @param isEntrance    if you want to set it as an entrance or not
     */
    public void setEntrance(boolean isEntrance){
        this.entrance = isEntrance;
        if (isEntrance) {
            clearRoom();
            this.exit = false;
        }
    }

    /**
     * This method is used to set the room to an exit.
     *
     * @param isExit    if you want to set it as an exit or not
     */
    public void setExit(boolean isExit){
        this.exit = true;
        if (isExit) {
            clearRoom();
            this.entrance = false;
        }
    }

    /**
     * Helper method to clear all contents of a room.
     */
    public void clearRoom(){
        healingPotion = false;
        visionPotion = false;
        pit = false;
        pillar = null;
        monster = null;
    }

    /**
     * Method to set the Pillar in the room.
     *
     * @param pillarType    what pillar you want to set
     */
    public void setPillar(String pillarType) {
        clearRoom();
        this.pillar = pillarType;
    }

    /**
     * Set what monster is in the Room
     *
     * @param monster   the monster you want in the room.
     */
    public void setMonster(Monster monster) {
        clearRoom();
        this.monster = monster;
    }

    /**
     * Let there be a door on the north side
     * @param hasNorthDoor      yes or no
     */
    public void setNorthDoor(boolean hasNorthDoor) {
        this.northDoor = hasNorthDoor;
    }

    /**
     * Let there be a door on the south side
     * @param hasSouthDoor      yes or no
     */
    public void setSouthDoor(boolean hasSouthDoor) {
        this.southDoor = hasSouthDoor;
    }

    /**
     * Let there be a door on the east side
     * @param hasEastDoor      yes or no
     */
    public void setEastDoor(boolean hasEastDoor) {
        this.eastDoor = hasEastDoor;
    }

    /**
     * Let there be a door on the west side
     * @param hasWestDoor      yes or no
     */
    public void setWestDoor(boolean hasWestDoor) {
        this.westDoor = hasWestDoor;
    }

    public boolean hasMultipleItems() {
        int count = 0;
        if (healingPotion) count++;
        if (visionPotion) count++;
        if (pit) count++;
        if (pillar != null) count++;
        if (monster != null) count++;
        return count > 1;
    }

    public boolean hasNorthDoor() {
        return northDoor;
    }

    public boolean hasEastDoor() {
        return eastDoor;
    }

    public boolean hasSouthDoor() {
        return southDoor;
    }

    public boolean hasWestDoor() {
        return westDoor;
    }

    public boolean hasPit() {
        return pit;
    }

    public boolean hasHealingPotion() {
        return healingPotion;
    }

    public boolean hasVisionPotion() {
        return visionPotion;
    }

    public boolean isEntrance() {
        return entrance;
    }

    public boolean isExit() {
        return exit;
    }

    public String getPillar() {
        return pillar;
    }

    public Monster getMonster() {
        return monster;
    }

    /**
     * the toString to display a room correctly
     *
     * @return a string of the room
     */
    @Override
    public String toString() {
        char[][] roomDisplay = new char[3][3];
        char nsDoor = SHOW_DOORS ? '-' : ' ';
        char ewDoor = SHOW_DOORS ? '|' : ' ';

        // Top row - north door or wall
        roomDisplay[0][0] = WALL_CHARACTER;
        roomDisplay[0][1] = northDoor ? nsDoor : WALL_CHARACTER;
        roomDisplay[0][2] = WALL_CHARACTER;

        // Middle row - west door, center content, east door
        roomDisplay[1][0] = westDoor ? ewDoor : WALL_CHARACTER;
        roomDisplay[1][1] = getRoomSymbol();
        roomDisplay[1][2] = eastDoor ? ewDoor : WALL_CHARACTER;

        // Bottom row - south door or wall
        roomDisplay[2][0] = WALL_CHARACTER;
        roomDisplay[2][1] = southDoor ? nsDoor : WALL_CHARACTER;
        roomDisplay[2][2] = WALL_CHARACTER;

        // Build string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(roomDisplay[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    // Helper method to determine what symbol to show in center
    private char getRoomSymbol() {
        if (entrance) return 'i';
        if (exit) return 'O';
        if (pit) return 'X'; // Pit takes priority - no other items with pit
        if (hasMultipleItems()) return 'M';
        if (healingPotion) return 'H';
        if (visionPotion) return 'V';
        if (pillar != null) return pillar.charAt(0); // A, E, I, or P
        if (monster != null) return 'M'; // or different symbol for monster
        return ' '; // empty room
    }
}