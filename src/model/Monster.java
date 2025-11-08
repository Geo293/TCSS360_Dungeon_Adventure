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

    public Monster(String name, int hitPoints, int minDamage, int maxDamage,
                   int attackSpeed, double chanceToHit,
                   double chanceToHeal, int minHeal, int maxHeal) {
        super(name, hitPoints, minDamage, maxDamage, attackSpeed, chanceToHit);
        this.chanceToHeal = chanceToHeal;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
    }

    public void tryHeal() {
        if (!isAlive()) return;
        if (rand.nextDouble() <= chanceToHeal) {
            int healAmount = rand.nextInt(maxHeal - minHeal + 1) + minHeal;
            hitPoints += healAmount;
            System.out.println(name + " heals for " + healAmount + " HP.");
        }
    }
}