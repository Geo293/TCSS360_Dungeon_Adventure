package controller;

import javafx.scene.control.TextArea;
import model.Hero;
import model.Monster;

/**
 * Controller class for managing combat logic between Hero and Monster.
 * Supports multi-attack rounds, blocking, healing, and logging.
 * @author Carson Poirier
 * @version 11/8/25
 */
public class CombatSystem {

    /**
     * Executes a single round of combat: hero attacks, then monster retaliates.
     *
     * @param theHero       The hero character.
     * @param theMonster    The monster character.
     * @param theCombatLog  TextArea to append combat messages.
     */
    public static void battleRound(Hero theHero, Monster theMonster, TextArea theCombatLog) {
        theHero.attack(theMonster);
        if (theMonster.isAlive()) {
            theCombatLog.appendText(theMonster.getMyName() + " attacks.\n");
            monsterAttack(theMonster, theHero, theCombatLog);
        }
    }

    /**
     * Handles monster attacking hero, including block chance and healing.
     *
     * @param theMonster    The attacking monster.
     * @param theHero       The defending hero.
     * @param theCombatLog  TextArea to append combat messages.
     */
    private static void monsterAttack(Monster theMonster, Hero theHero, TextArea theCombatLog) {
        int numAttacks = Math.max(1, theMonster.getMyAttackSpeed() / theHero.getMyAttackSpeed());

        for (int i = 0; i < numAttacks; i++) {
           // if (theHero.blockAttack()) {
             //   theCombatLog.appendText(theHero.getMyName() + " blocked the attack.\n");
               // continue;
           // }

          //  if (theMonster.canHit()) {
           //     int damage = theMonster.calculateDamage();
             //   theHero.subtractHitPoints(damage);
               // theCombatLog.appendText(theMonster.getMyName() + " hits for " + damage + " damage.\n");
           // } else {
             //   theCombatLog.appendText(theMonster.getMyName() + " missed.\n");
            //}

            theMonster.tryHeal();
        }
    }
}
