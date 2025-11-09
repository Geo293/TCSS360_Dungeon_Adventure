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
    private static final int MIN_HP = 75;
    private static final int MAX_HP = 100;
    private static final int MAX_DAMAGE = 45;
    private static final int MIN_DAMAGE = 25;
    private static final int ATTACK_SPEED = 5;
    private static final double CHANCE_TO_HIT = .7;
    private static final double CHANCE_TO_BLOCK = .3;
    private static final  int HEAL_MIN = 30;
    private static final  int HEAL_MAX = 60;


    public Priestess(String theName) {
        super(theName, new Random().nextInt(MAX_HP - MIN_HP + 1)
                + MIN_HP, MIN_DAMAGE, MAX_DAMAGE, ATTACK_SPEED, CHANCE_TO_HIT,CHANCE_TO_BLOCK);

    }
    /**
     * Abstract method for special skill - implemented by subclasses
     *
     * @param opponent the opponent to use skill on
     */
    @Override
    public void specialSkill(DungeonCharacter opponent) {
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
