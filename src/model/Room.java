package model;
import java.io.Serializable;
/**
 * This class creates a room design that will randomly place objects
 * throughout the room for the player to interact with.
 * @author Justin Yee
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Room implements Serializable {
    //Constants
    private static final long serialVersionUID = 1L;
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
    private boolean myNorthDoor;

    /**Is there a door to the South.*/
    private boolean mySouthDoor;

    /**Is there a door to the East.*/
    private boolean myEastDoor;

    /**Is there a door to the West.*/
    private boolean myWestDoor;

    /**Is there a healing potion in this room.*/
    private boolean myHealingPotion;

    /**Is there a vision potion in this room.*/
    private boolean myVisionPotion;

    /**Is there a pit in this room.*/
    private boolean myPit;

    /**Is this room an entrance.*/
    private boolean myEntrance;

    /**Is this room an exit.*/
    private boolean myExit;

    /**The pillars that the hero can collect.*/
    private String myPillar; // null if no pillar, or "A", "E", "I", "P"

    /**The monster in this room (if there is one).*/
    private Monster myMonster;

    /**
     * This is the constructor for the Room class.
     */
    public Room(){
        // Initializing the variables with default vals
        myNorthDoor = false;
        mySouthDoor = false;
        myEastDoor = false;
        myWestDoor = false;
        myHealingPotion = false;
        myVisionPotion = false;
        myPit = false;
        myEntrance = false;
        myExit = false;
        myPillar = null;
        myMonster = null;
    }

    /**
     * This method will generate what items the room has.
     */
    public void generateContents(){
        if (myEntrance || myExit) {
            return;
        }
        //if there is a pit then there will be no other items
        if (Math.random() < PIT_SPAWN_CHANCE) {
            myPit = true;
            return;
        }

        if (Math.random() < HEALING_POTION_SPAWN_CHANCE) {
            myHealingPotion = true;
        }

        if (Math.random() < VISION_POTION_SPAWN_CHANCE) {
            myVisionPotion = true;
        }
    }

    /**
     * This method is used to set the room to an entrance.
     *
     * @param theEntrance    if you want to set it as an entrance or not
     */
    public void setMyEntrance(boolean theEntrance){
        myEntrance = theEntrance;
        if (theEntrance) {
            clearRoom();
            myExit = false;
        }
    }

    /**
     * This method is used to set the room to an exit.
     *
     * @param TheExit    if you want to set it as an exit or not
     */
    public void setMyExit(boolean TheExit){
        myExit = true;
        if (TheExit) {
            clearRoom();
            myEntrance = false;
        }
    }

    /**
     * Helper method to clear all contents of a room.
     */
    public void clearRoom(){
        myHealingPotion = false;
        myVisionPotion = false;
        myPit = false;
        myPillar = null;
        myMonster = null;
    }

    /**
     * Method to set the Pillar in the room.
     *
     * @param pillarType    what pillar you want to set
     */
    public void setMyPillar(String pillarType) {
        if (pillarType == null || "".equals(pillarType)) {
            throw new IllegalArgumentException("pillarType cannot be null or empty");
        }
        myPillar = pillarType;
    }

    /**
     * Set what monster is in the Room
     *
     * @param theMonster   the monster you want in the room.
     */
    public void setMyMonster(Monster theMonster) {
        if (theMonster == null) {
            throw new IllegalArgumentException("monster cannot be null");
        }
        myMonster = theMonster;
    }

    /**
     * Let there be a door on the north side.
     * @param theNorthDoor      yes or no
     */
    public void setMyNorthDoor(boolean theNorthDoor) {
        myNorthDoor = theNorthDoor;
    }

    /**
     * Let there be a door on the south side.
     * @param theSouthDoor      yes or no
     */
    public void setMySouthDoor(boolean theSouthDoor) {
        mySouthDoor = theSouthDoor;
    }

    /**
     * Let there be a door on the east side.
     * @param theEastDoor      yes or no
     */
    public void setMyEastDoor(boolean theEastDoor) {
        myEastDoor = theEastDoor;
    }

    /**
     * Let there be a door on the west side.
     * @param theWestDoor      yes or no
     */
    public void setMyWestDoor(boolean theWestDoor) {
        myWestDoor = theWestDoor;
    }

    /**
     * add healing potion to room.
     * @param theHealing    yes or no
     */
    public void setMyHealingPotion(boolean theHealing) {
        myHealingPotion = theHealing;
    }

    /**
     * add vision potion to room.
     * @param theVision     yes or no
     */
    public void setMyVisionPotion(boolean theVision) {
        myVisionPotion = theVision;
    }

    /**
     * set pit for room.
     * @param thePit    yes or no
     */
    public void setMyPit (boolean thePit){
        myPit = thePit;
    }



    /**
     * Checks if there are multiple items in this room.
     * @return  true or false for if there are multiple items
     */
    public boolean hasMultipleItems() {
        int count = 0;
        if (myHealingPotion) count++;
        if (myVisionPotion) count++;
        if (myPit) count++;
        if (myPillar != null) count++;
        if (myMonster != null) count++;
        return count > 1;
    }

    /**
     * Does this room have a door to the North.
     * @return  true/false if there is a door
     */
    public boolean hasNorthDoor() {
        return myNorthDoor;
    }

    /**
     * Does this room have a door to the East.
     * @return  true/false if there is a door
     */
    public boolean hasEastDoor() {
        return myEastDoor;
    }

    /**
     * Does this room have a door to the South.
     * @return  true/false if there is a door
     */
    public boolean hasSouthDoor() {
        return mySouthDoor;
    }

    /**
     * Does this room have a door to the West.
     * @return  true/false if there is a door
     */
    public boolean hasWestDoor() {
        return myWestDoor;
    }

    /**
     * Does this room have a Pit.
     * @return  true/false
     */
    public boolean hasPit() {
        return myPit;
    }

    /**
     * Does this room have a Healing potion.
     * @return  true/false
     */
    public boolean hasHealingPotion() {
        return myHealingPotion;
    }

    /**
     * Does this room have a Vision Potion.
     * @return  true/false
     */
    public boolean hasVisionPotion() {
        return myVisionPotion;
    }

    /**
     * Is this room an entrance.
     * @return  true/false
     */
    public boolean isMyEntrance() {
        return myEntrance;
    }

    /**
     * Is this room an exit.
     * @return  true/false
     */
    public boolean isMyExit() {
        return myExit;
    }

    /**
     * What Pillar of OO is in this room of there is one.
     * @return  the Pillar or Null if there is none.
     */
    public String getMyPillar() {
        return myPillar;
    }

    /**
     * What Monster is in this room of there is one.
     * @return  the Pillar or Null if there is none.
     */
    public Monster getMyMonster() {
        return myMonster;
    }

    /**
     * Removes the healing potion from this room (after hero picks it up)
     */
    public void removeHealingPotion() {
        myHealingPotion = false;
    }

    /**
     * Removes the vision potion from this room (after hero picks it up)
     */
    public void removeVisionPotion() {
        myVisionPotion = false;
    }

    /**
     * Removes the monster from this room (after it's defeated)
     */
    public void removeMonster() {
        myMonster = null;
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
        roomDisplay[0][1] = myNorthDoor ? nsDoor : WALL_CHARACTER;
        roomDisplay[0][2] = WALL_CHARACTER;

        // Middle row - west door, center content, east door
        roomDisplay[1][0] = myWestDoor ? ewDoor : WALL_CHARACTER;
        roomDisplay[1][1] = getRoomSymbol();
        roomDisplay[1][2] = myEastDoor ? ewDoor : WALL_CHARACTER;

        // Bottom row - south door or wall
        roomDisplay[2][0] = WALL_CHARACTER;
        roomDisplay[2][1] = mySouthDoor ? nsDoor : WALL_CHARACTER;
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
        if (myEntrance) return 'i';
        if (myExit) return 'O';
        if (myPit) return 'X'; // Pit takes priority - no other items with pit
        if (hasMultipleItems()) return 'M';
        if (myHealingPotion) return 'H';
        if (myVisionPotion) return 'V';
        if (myPillar != null) return myPillar.charAt(0); // A, E, I, or P
        if (myMonster != null) return 'T'; // or different symbol for monster
        return ' '; // empty room
    }
}