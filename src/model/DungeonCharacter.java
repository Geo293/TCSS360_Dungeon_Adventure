package model;

import java.util.Random;

/**
 * Abstract base class for all dungeon characters (Heroes and Monsters).
 * Encapsulates shared attributes/behaviors such as attacking, hit points, and  attack speed.
 */
public abstract class DungeonCharacter {
    protected String myName;
    protected int myHitPoints;
    protected int myMinDamage;
    protected int myMaxDamage;
    protected int myAttackSpeed;
    protected double myChanceToHit;

    protected Random myRand;

    /**
     * Protected constructor to be called by subclasses(Hero Monster,etc).
     */
    protected DungeonCharacter(String theName, int theHitPoints, int theMinDamage, int theMaxDamage,
                               int theAttackSpeed, double theChanceToHit) {
        myName = theName;
        myHitPoints = theHitPoints;
        myMinDamage = theMinDamage;
        myMaxDamage = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChanceToHit = theChanceToHit;
        myRand = new Random();
    }

    // Getters and setters
    public String getMyName() { return myName; }
    public int getMyHitPoints() { return myHitPoints; }
    public int getMyAttackSpeed() { return myAttackSpeed; }
    public void setMyHitPoints(int hp) { this.myHitPoints = Math.max(hp, 0); }
    public boolean isAlive() { return myHitPoints > 0; }

    /**
     * Attack another DungeonCharacter(hero or Monster)
     * Number of attacks is determined by comparing attack speeds.
     */
    public void attack(DungeonCharacter theOpponent) {
        int numAttacks = Math.max(1, this.myAttackSpeed / theOpponent.myAttackSpeed);

        for (int i = 0; i < numAttacks; i++) {
            if (myRand.nextDouble() <= myChanceToHit) {
                int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
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
     * Subtract hit points from the character.
     */
    public void subtractHitPoints(int theDamage) {
        setMyHitPoints(myHitPoints - theDamage);
    }

    /**
     * Display character stats.
     */
    public String toString() {
        return String.format("%s [HP: %d, Speed: %d, Damage: %d-%d, Hit Chance: %.2f]",
                myName, myHitPoints, myAttackSpeed, myMinDamage, myMaxDamage, myChanceToHit);
    }
}
