package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * Abstract base class for all dungeon characters (Heroes and Monsters).
 * Encapsulates shared attributes and behaviors such as attacking, hit points, and attack speed.
 *
 * @author Carson Poirier
 * @version 11/1/25
 */
public abstract class DungeonCharacter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** The name of the character. */
    protected String myName;

    /** The hit points of the character. */
    protected int myHitPoints;

    /** The minimum damage the character can deal. */
    protected int myMinDamage;

    /** The maximum damage the character can deal. */
    protected int myMaxDamage;

    /** The attack speed of the character. */
    protected int myAttackSpeed;

    /** The chance the character has to hit. */
    protected double myChanceToHit;

    /** Random number generator for combat calculations. */
    protected final Random myRand;

    /**
     * Constructs a DungeonCharacter with the given stats.
     * Called by subclasses (Hero, Monster, etc.).
     *
     * @param theName        the name of the character
     * @param theHitPoints   the hit points of the character
     * @param theMinDamage   the minimum damage the character can deal
     * @param theMaxDamage   the maximum damage the character can deal
     * @param theAttackSpeed the attack speed of the character
     * @param theChanceToHit the chance the character has to hit
     */
    protected DungeonCharacter(final String theName, final int theHitPoints,
                               final int theMinDamage, final int theMaxDamage,
                               final int theAttackSpeed, final double theChanceToHit) {
        if (theName == null || theHitPoints < 0 || theMinDamage < 0
                || theMaxDamage < 0 || theAttackSpeed < 0 || theChanceToHit < 0) {
            throw new IllegalArgumentException("DungeonCharacter is invalid");
        }
        myName = theName;
        myHitPoints = theHitPoints;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChanceToHit = theChanceToHit;
        myRand = new Random();
    }

    /**
     * Returns the character's name.
     *
     * @return the name
     */
    public String getMyName() {
        return myName;
    }

    /**
     * Returns the character's hit points.
     *
     * @return the hit points
     */
    public int getMyHitPoints() {
        return myHitPoints;
    }

    /**
     * Returns the character's attack speed.
     *
     * @return the attack speed
     */
    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    /**
     * Sets the character's hit points, ensuring they do not drop below zero.
     *
     * @param theHitPoints the new hit points
     */
    public void setMyHitPoints(final int theHitPoints) {
        myHitPoints = Math.max(theHitPoints, 0);
    }

    /**
     * Checks if the character is alive.
     *
     * @return true if hit points are greater than zero, false otherwise
     */
    public boolean isAlive() {
        return myHitPoints > 0;
    }

    /**
     * Attacks another DungeonCharacter (Hero or Monster).
     * Number of attacks is determined by comparing attack speeds.
     *
     * @param theOpponent the opponent being attacked
     */
    public void attack(final DungeonCharacter theOpponent) {
        if (theOpponent == null) {
            throw new IllegalArgumentException("The opponent is null");
        }
        final int numAttacks = Math.max(1, myAttackSpeed / theOpponent.myAttackSpeed);

        for (int i = 0; i < numAttacks; i++) {
            if (myRand.nextDouble() <= myChanceToHit) {
                final int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
                theOpponent.subtractHitPoints(damage);
                System.out.println(myName + " hits " + theOpponent.myName + " for " + damage + " damage.");
            } else {
                System.out.println(myName + " missed the attack on " + theOpponent.myName + ".");
            }

            if (!theOpponent.isAlive()) {
                System.out.println(theOpponent.myName + " has been defeated!");
                break;
            }
        }
    }

    /**
     * Subtracts hit points from the character.
     *
     * @param theDamage the damage to subtract
     */
    public void subtractHitPoints(final int theDamage) {
        setMyHitPoints(myHitPoints - theDamage);
    }

    /**
     * Returns a string representation of the character's stats.
     *
     * @return formatted string of stats
     */
    @Override
    public String toString() {
        return String.format("%s [HP: %d, Speed: %d, Damage: %d-%d, Hit Chance: %.2f]",
                myName, myHitPoints, myAttackSpeed, myMinDamage, myMaxDamage, myChanceToHit);
    }
}
