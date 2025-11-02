package model;

import java.util.Random;

/**
 * Abstract base class for all dungeon characters (Heroes and Monsters).
 * Encapsulates shared attributes/behaviors such as attacking, hit points, and  attack speed.
 */
public abstract class DungeonCharacter {
    protected String name;
    protected int hitPoints;
    protected int minDamage;
    protected int maxDamage;
    protected int attackSpeed;
    protected double chanceToHit;

    protected Random rand;

    /**
     * Protected constructor to be called by subclasses(Hero Monster,etc).
     */
    protected DungeonCharacter(String name, int hitPoints, int minDamage, int maxDamage,
                               int attackSpeed, double chanceToHit) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.attackSpeed = attackSpeed;
        this.chanceToHit = chanceToHit;
        this.rand = new Random();
    }

    // Getters and setters
    public String getName() { return name; }
    public int getHitPoints() { return hitPoints; }
    public int getAttackSpeed() { return attackSpeed; }
    public void setHitPoints(int hp) { this.hitPoints = Math.max(hp, 0); }
    public boolean isAlive() { return hitPoints > 0; }

    /**
     * Attack another DungeonCharacter(hero or Monster)
     * Number of attacks is determined by comparing attack speeds.
     */
    public void attack(DungeonCharacter opponent) {
        int numAttacks = Math.max(1, this.attackSpeed / opponent.attackSpeed);

        for (int i = 0; i < numAttacks; i++) {
            if (rand.nextDouble() <= chanceToHit) {
                int damage = rand.nextInt(maxDamage - minDamage + 1) + minDamage;
                opponent.subtractHitPoints(damage);
                System.out.println(name + " hits " + opponent.name + " for " + damage + " damage.");
            } else {
                System.out.println(name + " missed the attack on " + opponent.name + ".");
            }

            if (!opponent.isAlive()) {
                System.out.println(opponent.name + " has been defeated!");
                break;
            }
        }
    }

    /**
     * Subtract hit points from the character.
     */
    public void subtractHitPoints(int damage) {
        setHitPoints(hitPoints - damage);
    }

    /**
     * Display character stats.
     */
    public String toString() {
        return String.format("%s [HP: %d, Speed: %d, Damage: %d-%d, Hit Chance: %.2f]",
                name, hitPoints, attackSpeed, minDamage, maxDamage, chanceToHit);
    }
}
