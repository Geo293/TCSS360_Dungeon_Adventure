package model;

import javafx.scene.control.TextArea;

import java.util.Random;

/**
 * Controller class for managing combat logic between Hero and Monster.
 * Supports multi-attack rounds, blocking, healing, and logging.
 *
 * @author Carson Poirier
 * @version 11/18/25
 */
public class CombatSystem {

    /**
     * Executes a single round of combat: hero attacks, then monster retaliates.
     *
     * @param theHero      the hero character
     * @param theMonster   the monster character
     * @param theCombatLog the combat log to append messages
     */
    public static void battleRound(final Hero theHero,
                                   final Monster theMonster,
                                   final TextArea theCombatLog) {
        theHero.attack(theMonster);
        if (theMonster.isAlive()) {
            theCombatLog.appendText(theMonster.getMyName() + " attacks.\n");
            monsterAttack(theMonster, theHero, theCombatLog);
        }
    }

    /**
     * Handles monster attacking hero, including block chance and healing.
     *
     * @param theMonster   the attacking monster
     * @param theHero      the defending hero
     * @param theCombatLog the combat log to append messages
     */
    private static void monsterAttack(final Monster theMonster,
                                      final Hero theHero,
                                      final TextArea theCombatLog) {
        final int numAttacks = Math.max(1,
                theMonster.getMyAttackSpeed() / theHero.getMyAttackSpeed());
        Random theRandom = new Random();
        Double randomHero = theRandom.nextDouble();
        Double randomMonster= theRandom.nextDouble();


        for (int i = 0; i < numAttacks; i++) {
            randomHero = theRandom.nextDouble();
            randomMonster = theRandom.nextDouble();
             if (theHero.getMyChanceToBlock() < randomHero) {
                 theCombatLog.appendText(theHero.getMyName() + " blocked the attack.\n");
                 continue;
             }
             else if (theMonster.getChanceToHit() < randomMonster) {
                 final int damage = theMonster.getDamage();
                 theHero.subtractHitPoints(damage);
                 theCombatLog.appendText(theMonster.getMyName()
                         + " hits for " + damage + " damage.\n");
             } else {
                 theCombatLog.appendText(theMonster.getMyName() + " missed.\n");
             }

            theMonster.tryHeal();
        }
    }
}

