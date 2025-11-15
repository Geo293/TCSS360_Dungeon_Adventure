package model;

/**
 * This is the child class of DungeonCharacter but is also the parent class
 * of all the monsters this class will include an option to heal during or after
 * a battle and the odds of it happening
 * @author Carson Poirier
 * @version 11/8/25
 */
public abstract class Monster extends DungeonCharacter {
    protected double chanceToHeal;
    protected int minHeal;
    protected int maxHeal;

    /**
     * This is the contractor that sets all the values based on the database values
     * @param name the name of the monster
     * @param hitPoints the amount of heal of the monster
     * @param minDamage the minimum damage a monster can do
     * @param maxDamage the maximum damage a monster can do
     * @param attackSpeed the attack speed of the monster
     * @param chanceToHit the chance the monster has to hit
     * @param chanceToHeal he chance the monster has to heal
     * @param minHeal the minimum health the monster heals
     * @param maxHeal the maximum health the monster heals
     */
    public Monster(String name, int hitPoints, int minDamage, int maxDamage,
                   int attackSpeed, double chanceToHit,
                   double chanceToHeal, int minHeal, int maxHeal) {
        super(name, hitPoints, minDamage, maxDamage, attackSpeed, chanceToHit);
        this.chanceToHeal = chanceToHeal;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
    }

    /**
     * this method checks if the monster is alive and then trys to heal.
     */
    public void tryHeal() {
        if (!isAlive()) return;
        if (myRand.nextDouble() <= chanceToHeal) {
            int healAmount = myRand.nextInt(maxHeal - minHeal + 1) + minHeal;
            myHitPoints += healAmount;
            System.out.println(myName + " heals for " + healAmount + " HP.");
        }
    }

}