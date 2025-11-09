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
     * @param hero       The hero character.
     * @param monster    The monster character.
     * @param combatLog  TextArea to append combat messages.
     */
    public static void battleRound(Hero hero, Monster monster, TextArea combatLog) {
        hero.attack(monster);
        if (monster.isAlive()) {
            combatLog.appendText(monster.getName() + " attacks.\n");
            monsterAttack(monster, hero, combatLog);
        }
    }

    /**
     * Handles monster attacking hero, including block chance and healing.
     *
     * @param monster    The attacking monster.
     * @param hero       The defending hero.
     * @param combatLog  TextArea to append combat messages.
     */
    private static void monsterAttack(Monster monster, Hero hero, TextArea combatLog) {
        int numAttacks = Math.max(1, monster.getAttackSpeed() / hero.getAttackSpeed());

        for (int i = 0; i < numAttacks; i++) {
            if (hero.blockAttack()) {
                combatLog.appendText(hero.getName() + " blocked the attack.\n");
                continue;
            }

            if (monster.canHit()) {
                int damage = monster.calculateDamage();
                hero.subtractHitPoints(damage);
                combatLog.appendText(monster.getName() + " hits for " + damage + " damage.\n");
            } else {
                combatLog.appendText(monster.getName() + " missed.\n");
            }

            monster.tryHeal();
        }
    }
}
