package model;

import java.io.Serializable;
/**
 * This class will have all the basic logic that every playable character
 * will have including how many attacks they get, whether they block
 * an attack.
 *
 * @author Geovani Vasquez
 * @author Justin Yee
 * @version Oct, 24 2025
 */
public abstract class Hero extends DungeonCharacter implements Serializable{
    private static final int TOTAL_PILLARS = 4;
    private static final int MIN_HEAL_AMOUNT = 5;
    private static final int MAX_HEAL_AMOUNT = 15;

    private int myHealingPotions;
    private int myVisionPotions;
    private final boolean[] myPillarsFound;
    private final double myChanceToBlock;

    /**
     * The constructor for the Hero class.
     *
     * @param theName       name of the hero
     * @param theHitPoints  hp of the hero
     * @param theMinDamage  min damage the hero can do
     * @param theMaxDamage  max damage the hero can do
     * @param theAttackSpeed    speed of the hero
     * @param theChance         chance of crit for the hero
     */
    protected Hero(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance, double theBlockChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
        myHealingPotions = 0;
        myVisionPotions = 0;
        myPillarsFound = new boolean[TOTAL_PILLARS]; // A, E, I, P
        myChanceToBlock = theBlockChance;
    }

    /**
     * Hero takes damage (chance to block).
     * @param theDamage how much damage we take.
     */
    public void subtractHitPoints(int theDamage) {
        if (myRand.nextDouble() < myChanceToBlock) {
            System.out.println(myName + " blocked the attack!");
        } else {
            super.subtractHitPoints(theDamage);
        }
    }

    /**
     * Hero takes damage (cant block pit)
     * @param theDamage the amount of dmg we take
     */
    public void takePitDamage(int theDamage){
        super.subtractHitPoints(theDamage);
    }

    /**
     * Use a healing potion and regen some health.
     */
    public void useHealingPotion() {
        if (myHealingPotions > 0) {
            int healAmount = myRand.nextInt(MAX_HEAL_AMOUNT - MIN_HEAL_AMOUNT + 1) + MIN_HEAL_AMOUNT;
            super.setMyHitPoints(myHitPoints + healAmount);
            myHealingPotions--;
            System.out.println(myName + " used a healing potion and restored " + healAmount + " HP!");
        } else {
            System.out.println("No healing potions available!");
        }
    }

    /**
     * use a vision potion.
     */
    public void useVisionPotion() {
        if (myVisionPotions > 0) {
            myVisionPotions--;
            System.out.println(myName + " used a vision potion!");
        } else {
            System.out.println("No vision potions available!");
        }
    }

    /**
     * Picks up an item from the current room
     * Handles healing potions, vision potions, and pillars
     *
     * @param theRoom  the room to pick up items from
     */
    public void pickUpItem(Room theRoom) {
        boolean pickedUpSomething = false;

        // Pick up healing potion
        if (theRoom.hasHealingPotion()) {
            myHealingPotions++;
            theRoom.removeHealingPotion();
            System.out.println(myName + " picked up a healing potion!");
            pickedUpSomething = true;
        }

        // Pick up vision potion
        if (theRoom.hasVisionPotion()) {
            myVisionPotions++;
            theRoom.removeVisionPotion();
            System.out.println(myName + " picked up a vision potion!");
            pickedUpSomething = true;
        }

        // Collect pillar
        if (theRoom.getMyPillar() != null) {
            String pillar = theRoom.getMyPillar();
            int index = getPillarIndex(pillar);
            if (index != -1 && !myPillarsFound[index]) {
                myPillarsFound[index] = true;
                System.out.println(myName + " collected the Pillar of " + getPillarName(pillar) + "!");
                pickedUpSomething = true;
            }
        }

        if (!pickedUpSomething) {
            System.out.println("No items to pick up in this room.");
        }
    }

    /**
     * Helper method to get pillar index
     *
     * @param thePillar    the pillar letter
     * @return          the index, or -1 if invalid
     */
    private int getPillarIndex(String thePillar) {
        return switch (thePillar.toUpperCase()) {
            case "A" -> 0; // Abstraction
            case "E" -> 1; // Encapsulation
            case "I" -> 2; // Inheritance
            case "P" -> 3; // Polymorphism
            default -> -1;
        };
    }


    /**
     * Helper method to get full pillar name
     *
     * @param thePillar    the pillar letter
     * @return          the full name
     */
    private String getPillarName(String thePillar) {
        return switch (thePillar.toUpperCase()) {
            case "A" -> "Abstraction";
            case "E" -> "Encapsulation";
            case "I" -> "Inheritance";
            case "P" -> "Polymorphism";
            default -> "Unknown";
        };
    }

    /**
     * Checks if the hero has collected all four pillars
     *
     * @return  true if all pillars collected, false otherwise
     */
    public boolean hasAllPillars() {
        for (boolean found : myPillarsFound) {
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param theOpponent  the opponent to use skill on
     */
    public abstract void specialSkill(DungeonCharacter theOpponent);

    public int getMyHealingPotions() {
        return myHealingPotions;
    }

    public int getMyVisionPotions() {
        return myVisionPotions;
    }

    public double getMyChanceToBlock() {
        return myChanceToBlock;
    }

    public boolean[] getMyPillarsFound() {
        return myPillarsFound;
    }
}



