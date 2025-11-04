package controller;

import model.characters.DungeonCharacter;
import model.characters.Hero;
import model.characters.Monster;

public class CombatSystem {

    /**
     * Executes a full round of combat between a hero and a monster.
     */
    public static void battle(Hero hero, Monster monster) {
        System.out.println("\n Combat Begins: " + hero.getName() + " vs " + monster.getName());

        while (hero.isAlive() && monster.isAlive()) {
            System.out.println("\n" + hero.getName() + "'s Turn:");
            hero.attack(monster);

            if (monster.isAlive()) {
                System.out.println("\n" + monster.getName() + "'s Turn:");
                monsterAttack(monster, hero);
            }

            System.out.println("\n Status:");
            System.out.println(hero);
            System.out.println(monster);
        }

        System.out.println("\n Combat Over!");
        if (hero.isAlive()) {
            System.out.println(hero.getName() + " is victorious!");
        } else {
            System.out.println(monster.getName() + " has defeated " + hero.getName() + "!");
        }
    }

    /**
     * Handles monster attacking hero, with hero's chance to block.
     */
    private static void monsterAttack(Monster monster, Hero hero) {
        int numAttacks = Math.max(1, monster.getAttackSpeed() / hero.getAttackSpeed());

        for (int i = 0; i < numAttacks; i++) {
            if (hero.blockAttack()) {
                System.out.println(hero.getName() + " blocked the attack!");
                continue;
            }

            if (monster.canHit()) {
                int damage = monster.calculateDamage();
                hero.subtractHitPoints(damage);
                System.out.println(monster.getName() + " hits " + hero.getName() + " for " + damage + " damage.");
            } else {
                System.out.println(monster.getName() + " missed the attack.");
            }

            monster.tryHeal();
            if (!hero.isAlive()) break;
        }
    }
}
