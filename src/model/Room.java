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

    /**What character you want to represent the walls.*/
    private static final char WALL_CHARACTER = '█'; // default is '*' but '█' is easy to read

    /**If you want to have the doors ("-|") shown in the toString or not.*/
    private static final boolean SHOW_DOORS = true;


    /**Is there a door to the North.*/
    private boolean northDoor;

    /**Is there a door to the South.*/
    private boolean southDoor;

    /**Is there a door to the East.*/
    private boolean eastDoor;

    /**Is there a door to the West.*/
    private boolean westDoor;

    /**Is there a healing potion in this room.*/
    private boolean healingPotion;

    /**Is there a vision potion in this room.*/
    private boolean visionPotion;

    /**Is there a pit in this room.*/
    private boolean pit;

    /**Is this room an entrance.*/
    private boolean entrance;

    /**Is this room an exit.*/
    private boolean exit;

    /**The pillars that the hero can collect.*/
    private String pillar; // null if no pillar, or "A", "E", "I", "P"

    /**The monster in this room (if there is one).*/
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

    /**
     * Checks if there are multiple items in this room.
     * @return  true or false for if there are multiple items
     */
    public boolean hasMultipleItems() {
        int count = 0;
        if (healingPotion) count++;
        if (visionPotion) count++;
        if (pit) count++;
        if (pillar != null) count++;
        if (monster != null) count++;
        return count > 1;
    }

    /**
     * Does this room have a door to the North.
     * @return  true/false if there is a door
     */
    public boolean hasNorthDoor() {
        return northDoor;
    }

    /**
     * Does this room have a door to the East.
     * @return  true/false if there is a door
     */
    public boolean hasEastDoor() {
        return eastDoor;
    }

    /**
     * Does this room have a door to the South.
     * @return  true/false if there is a door
     */
    public boolean hasSouthDoor() {
        return southDoor;
    }

    /**
     * Does this room have a door to the West.
     * @return  true/false if there is a door
     */
    public boolean hasWestDoor() {
        return westDoor;
    }

    /**
     * Does this room have a Pit.
     * @return  true/false
     */
    public boolean hasPit() {
        return pit;
    }

    /**
     * Does this room have a Healing potion.
     * @return  true/false
     */
    public boolean hasHealingPotion() {
        return healingPotion;
    }

    /**
     * Does this room have a Vision Potion.
     * @return  true/false
     */
    public boolean hasVisionPotion() {
        return visionPotion;
    }

    /**
     * Is this room an entrance.
     * @return  true/false
     */
    public boolean isEntrance() {
        return entrance;
    }

    /**
     * Is this room an exit.
     * @return  true/false
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * What Pillar of OO is in this room of there is one.
     * @return  the Pillar or Null if there is none.
     */
    public String getPillar() {
        return pillar;
    }

    /**
     * What Monster is in this room of there is one.
     * @return  the Pillar or Null if there is none.
     */
    public Monster getMonster() {
        return monster;
    }

    /**
     * Removes the healing potion from this room (after hero picks it up)
     */
    public void removeHealingPotion() {
        this.healingPotion = false;
    }

    /**
     * Removes the vision potion from this room (after hero picks it up)
     */
    public void removeVisionPotion() {
        this.visionPotion = false;
    }

    /**
     * Removes the monster from this room (after it's defeated)
     */
    public void removeMonster() {
        this.monster = null;
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

    /**
     * A helper method to get the corresponding letter to what is in the room.
     * @return  A character depicting what is in the room.
     */
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