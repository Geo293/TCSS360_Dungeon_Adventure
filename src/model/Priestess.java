package model;

import java.util.Random;

/**
 * This class has all the logic for the priestess class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */

public class Priestess extends Hero {
    /**
     * the minimum health
     */
    private static final int MIN_HP = 75;
    /**
     * the maximum health
     */
    private static final int MAX_HP = 100;
    /**
     * The maximum damage a priestess can do
     */
    private static final int MAX_DAMAGE = 45;
    /**
     * The minimum damage the priestess can do
     */
    private static final int MIN_DAMAGE = 25;
    /**
     * The priestess attack speed
     */
    private static final int ATTACK_SPEED = 5;
    /**
     * The chance to hit
     */
    private static final double CHANCE_TO_HIT = .7;
    /**
     * the chance to block
     */
    private static final double CHANCE_TO_BLOCK = .3;
    /**
     * the minimum amount the priestess can heal
     */
    private static final  int HEAL_MIN = 30;
    /**
     * the maximum amount the priestess can heal
     */
    private static final  int HEAL_MAX = 60;

    /**
     * The constructor for all the fields and calls to super.
     * @param theName the character name
     */
    public Priestess(String theName) {
        super(theName, new Random().nextInt(MAX_HP - MIN_HP + 1)
                + MIN_HP, MIN_DAMAGE, MAX_DAMAGE, ATTACK_SPEED, CHANCE_TO_HIT,CHANCE_TO_BLOCK);

    }
    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param theOpponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter theOpponent) {
            System.out.println(getMyName() + "casts heal");
        int heal = myRand.nextInt(HEAL_MAX-HEAL_MIN + 1) + HEAL_MIN;
        int currentHP = getMyHitPoints();
        int newHP = currentHP + heal;
        if(newHP > MAX_HP) {
            setMyHitPoints(MAX_HP);
            System.out.println(getMyName() + "heals " + (MAX_HP - HEAL_MIN));
        } else {
            setMyHitPoints(newHP);
            System.out.println(getMyName() + "heals " + heal);
        }

    }


}
