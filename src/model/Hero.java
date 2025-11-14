package model;

import java.util.Random;

/**
 * This class will has all the basic logic that every playable character
 * will have including how many attacks they get, whether they block
 * an attack.
 *
 *@author Geovani Vasquez
 *@version Oct, 24 2025
 */
public abstract class Hero extends DungeonCharacter {
    private static final int TOTAL_PILLARS = 4;

    private int healingPotions;
    private int visionPotions;
    private final boolean[] pillarsFound;
    private final double chanceToBlock;
    private static final int MIN_HEAL_AMOUNT = 5;
    private static final int MAX_HEAL_AMOUNT = 15;

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
        this.healingPotions = 0;
        this.visionPotions = 0;
        this.pillarsFound = new boolean[TOTAL_PILLARS]; // A, E, I, P
        this.chanceToBlock = theBlockChance;
    }

    /**
     * Hero takes damage (chance to block).
     * @param damage how much damage we take.
     */
    @Override
    public void subtractHitPoints(int damage) {
        if (rand.nextDouble() < chanceToBlock) {
            System.out.println(name + " blocked the attack!");
        } else {
            super.subtractHitPoints(damage);
        }
    }

    /**
     * Use a healing potion and regen some health.
     */
    public void useHealingPotion() {
        if (healingPotions > 0) {
            int healAmount = rand.nextInt(MAX_HEAL_AMOUNT - MIN_HEAL_AMOUNT + 1) + MIN_HEAL_AMOUNT;
            setHitPoints(hitPoints + healAmount);
            healingPotions--;
            System.out.println(getName() + " used a healing potion and restored " + healAmount + " HP!");
        } else {
            System.out.println("No healing potions available!");
        }
    }

    /**
     * use a vision potion.
     * @param dungeon   the dungeon so we can see other rooms.
     */
    public void useVisionPotion(Dungeon dungeon) {
        if (visionPotions > 0) {
            visionPotions--;
            System.out.println(getName() + " used a vision potion!");
            //TODO: How the hell does a vision potion work
        } else {
            System.out.println("No vision potions available!");
        }
    }

    /**
     * Picks up an item from the current room
     * Handles healing potions, vision potions, and pillars
     *
     * @param room  the room to pick up items from
     */
    public void pickUpItem(Room room) {
        boolean pickedUpSomething = false;

        // Pick up healing potion
        if (room.hasHealingPotion()) {
            healingPotions++;
            room.removeHealingPotion();
            System.out.println(getName() + " picked up a healing potion!");
            pickedUpSomething = true;
        }

        // Pick up vision potion
        if (room.hasVisionPotion()) {
            visionPotions++;
            room.removeVisionPotion();
            System.out.println(getName() + " picked up a vision potion!");
            pickedUpSomething = true;
        }

        // Collect pillar
        if (room.getPillar() != null) {
            String pillar = room.getPillar();
            int index = getPillarIndex(pillar);
            if (index != -1 && !pillarsFound[index]) {
                pillarsFound[index] = true;
                System.out.println(getName() + " collected the Pillar of " + getPillarName(pillar) + "!");
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
     * @param pillar    the pillar letter
     * @return          the index, or -1 if invalid
     */
    private int getPillarIndex(String pillar) {
        return switch (pillar.toUpperCase()) {
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
     * @param pillar    the pillar letter
     * @return          the full name
     */
    private String getPillarName(String pillar) {
        return switch (pillar.toUpperCase()) {
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
        for (boolean found : pillarsFound) {
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param opponent  the opponent to use skill on
     */
    public abstract void specialSkill(DungeonCharacter opponent);

    public int getHealingPotions() {
        return healingPotions;
    }

    public int getVisionPotions() {
        return visionPotions;
    }

    public double getChanceToBlock() {
        return chanceToBlock;
    }

    public boolean[] getPillarsFound() {
        return pillarsFound;
    }
}



