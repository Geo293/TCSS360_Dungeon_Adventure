package model;
/**
 * This class has all the logic for the thief class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Thief extends Hero {
    private static final int BASE_HP = 75;
    private static final int MAX_DAMAGE = 40;
    private static final int MIN_DAMAGE = 20;
    private static final int ATTACK_SPEED = 6;
    private static final double CHANCE_TO_HIT = .8;
    private static final double CHANCE_TO_BLOCK = .4;
    private static final double SNEAK_CHANCE = .4;
    private static final double CAUGHT_CHANCE = .2;


    public Thief(String theName){
        super(theName, BASE_HP, MIN_DAMAGE, MAX_DAMAGE, ATTACK_SPEED, CHANCE_TO_HIT,CHANCE_TO_BLOCK);

    }

    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param opponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter opponent) {
        double roll = myRand.nextDouble();

        System.out.println(myName + " surprise attack");

        if (roll < CAUGHT_CHANCE) {
            System.out.println(myName + " was caught");

        } else if (roll < CAUGHT_CHANCE + SNEAK_CHANCE) {
            System.out.println(myName + " successfully surprise attacks");

            if (myRand.nextDouble() <= myChanceToHit) {
                int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
                opponent.subtractHitPoints(damage);
                System.out.println("First attack hits for " + damage + " damage");
            } else {
                System.out.println("First attack missed!");
            }

            if (opponent.isAlive() && myRand.nextDouble() <= myChanceToHit) {
                int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
                opponent.subtractHitPoints(damage);
                System.out.println("Second attack hits for " + damage + " damage!");
            } else if (opponent.isAlive()) {
                System.out.println("Second attack missed");
            }
        } else {
            System.out.println(myName + " gets a normal attack.");
            attack(opponent);
        }
    }
}
