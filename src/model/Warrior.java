package model;

/**
 * This class has all the logic for the warrior class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Nov 8, 2025
 */
public class Warrior extends Hero {
    /**
     * Is the base amount of hp of the warrior
     */
    private static final int BASE_HP = 125;
    /**
     * The minimum damage the warrior can do
     */
    private static final int MIN_DAMAGE = 35;
    /**
     * The maximum damage a warrior can do
     */
    private static final int MAX_DAMAGE = 60;
    /**
     * The warriors attack speed
     */
    private static final int ATTACK_SPEED = 4;
    /**
     * The chance to hit
     */
    private static final double CHANCE_TO_HIT = 0.8;
    /**
     * the chance to block
     */
    private static final double CHANCE_TO_BLOCK = 0.2;
    /**
     * minimum damage for crushing blow
     */
    private static final int CRUSH_MIN = 75;
    /**
     * maximum damage for crushing blow
     */
    private static final int CRUSH_MAX = 175;
    /**
     * the chance of crushing blow hitting
     */
    private static final double CRUSH_CHANCE = 0.4;
    /**
     * The constructor for all the fields and calls to super.
     * @param theName the character name
     */
    public Warrior(String theName) {
        super(theName, BASE_HP, MIN_DAMAGE, MAX_DAMAGE,
                ATTACK_SPEED, CHANCE_TO_HIT, CHANCE_TO_BLOCK);
    }

    /**
     * Crushing Blow: 75-175 damage, 40% chance to succeed
     *
     * @param theOpponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter theOpponent) {
        System.out.println(myName + " attempts a crushing blow");

        if (myRand.nextDouble() <= CRUSH_CHANCE) {
            int damage = myRand.nextInt(CRUSH_MAX - CRUSH_MIN + 1) + CRUSH_MIN;
            theOpponent.subtractHitPoints(damage);
            System.out.println(myName + " hits for " + damage + " damage");
        } else {
            System.out.println("Crushing blow failed");
        }
    }
}