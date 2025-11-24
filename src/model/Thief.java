package model;
/**
 * This class has all the logic for the thief class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Thief extends Hero {
    /**
     * Is the base amount of hp of the Thief
     */
    private static final int BASE_HP = 75;
    /**
     * The maximum damage a Thief can do
     */
    private static final int MAX_DAMAGE = 40;
    /**
     * The minimum damage the Thief can do
     */
    private static final int MIN_DAMAGE = 20;
    /**
     * The Thief attack speed
     */
    private static final int ATTACK_SPEED = 6;
    /**
     * The chance to hit
     */
    private static final double CHANCE_TO_HIT = .8;
    /**
     * the chance to block
     */
    private static final double CHANCE_TO_BLOCK = .4;
    /**
     * chance to sneak
     */
    private static final double SNEAK_CHANCE = .4;
    /**
     * chance to get caught
     */
    private static final double CAUGHT_CHANCE = .2;

    /**
     * The constructor for all the fields and calls to super.
     * @param theName the character name
     */
    public Thief(String theName){
        super(theName, BASE_HP, MIN_DAMAGE, MAX_DAMAGE, ATTACK_SPEED, CHANCE_TO_HIT,CHANCE_TO_BLOCK);

    }

    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param theOpponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter theOpponent) {
        double roll = myRand.nextDouble();

        System.out.println(myName + " surprise attack");

        if (roll < CAUGHT_CHANCE) {
            System.out.println(myName + " was caught");

        } else if (roll < CAUGHT_CHANCE + SNEAK_CHANCE) {
            System.out.println(myName + " successfully surprise attacks");

            if (myRand.nextDouble() <= myChanceToHit) {
                int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
                theOpponent.subtractHitPoints(damage);
                System.out.println("First attack hits for " + damage + " damage");
            } else {
                System.out.println("First attack missed!");
            }

            if (theOpponent.isAlive() && myRand.nextDouble() <= myChanceToHit) {
                int damage = myRand.nextInt(myMaxDamage - myMinDamage + 1) + myMinDamage;
                theOpponent.subtractHitPoints(damage);
                System.out.println("Second attack hits for " + damage + " damage!");
            } else if (theOpponent.isAlive()) {
                System.out.println("Second attack missed");
            }
        } else {
            System.out.println(myName + " gets a normal attack.");
            attack(theOpponent);
        }
    }
}
