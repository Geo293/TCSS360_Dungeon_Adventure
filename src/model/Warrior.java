package model;

/**
 * This class has all the logic for the warrior class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Nov 8, 2025
 */
public class Warrior extends Hero {
    private static final int BASE_HP = 125;
    private static final int MIN_DAMAGE = 35;
    private static final int MAX_DAMAGE = 60;
    private static final int ATTACK_SPEED = 4;
    private static final double CHANCE_TO_HIT = 0.8;
    private static final double CHANCE_TO_BLOCK = 0.2;

    private static final int CRUSH_MIN = 75;
    private static final int CRUSH_MAX = 175;
    private static final double CRUSH_CHANCE = 0.4;

    public Warrior(String theName) {
        super(theName, BASE_HP, MIN_DAMAGE, MAX_DAMAGE,
                ATTACK_SPEED, CHANCE_TO_HIT, CHANCE_TO_BLOCK);
    }

    /**
     * Crushing Blow: 75-175 damage, 40% chance to succeed
     *
     * @param opponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter opponent) {
        System.out.println(myName + " attempts a crushing blow");

        if (myRand.nextDouble() <= CRUSH_CHANCE) {
            int damage = myRand.nextInt(CRUSH_MAX - CRUSH_MIN + 1) + CRUSH_MIN;
            opponent.subtractHitPoints(damage);
            System.out.println(myName + " hits for " + damage + " damage");
        } else {
            System.out.println("Crushing blow failed");
        }
    }
}