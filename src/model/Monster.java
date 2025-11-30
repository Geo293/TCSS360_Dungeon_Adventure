package model;

/**
 * Abstract base class for all monsters.
 * Extends DungeonCharacter and adds healing behavior during or after battle.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public abstract class Monster extends DungeonCharacter {

    /** Chance the monster has to heal. */
    protected final double myChanceToHeal;

    /** Minimum health the monster can heal. */
    protected final int myMinHeal;

    /** Maximum health the monster can heal. */
    protected final int myMaxHeal;

    /**
     * Constructs a Monster with values loaded from the database.
     *
     * @param theName        the name of the monster
     * @param theHitPoints   the hit points of the monster
     * @param theMinDamage   the minimum damage the monster can do
     * @param theMaxDamage   the maximum damage the monster can do
     * @param theAttackSpeed the attack speed of the monster
     * @param theChanceToHit the chance the monster has to hit
     * @param theChanceToHeal the chance the monster has to heal
     * @param theMinHeal     the minimum health the monster heals
     * @param theMaxHeal     the maximum health the monster heals
     */
    public Monster(final String theName, final int theHitPoints,
                   final int theMinDamage, final int theMaxDamage,
                   final int theAttackSpeed, final double theChanceToHit,
                   final double theChanceToHeal, final int theMinHeal,
                   final int theMaxHeal) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage,
                theAttackSpeed, theChanceToHit);
        myChanceToHeal = theChanceToHeal;
        myMinHeal = theMinHeal;
        myMaxHeal = theMaxHeal;
        myChanceToHit = theChanceToHit;
        myMaxDamage = theMaxDamage;
        myMinDamage = theMinDamage;
    }

    /**
     * Attempts to heal the monster if alive and chance succeeds.
     */
    public void tryHeal() {
        if (!isAlive()) {
            return;
        }
        if (myRand.nextDouble() <= myChanceToHeal) {
            final int healAmount = myRand.nextInt(myMaxHeal - myMinHeal + 1) + myMinHeal;
            myHitPoints += healAmount;
            System.out.println(myName + " heals for " + healAmount + " HP.");
        }
    }

    public double getChanceToHit() { return myChanceToHit; }

    public int getDamage(){
        int randomDanage = (int) (myMinDamage + (Math.random() * (myMaxDamage - myMinDamage)));
        return randomDanage;
    }
}
