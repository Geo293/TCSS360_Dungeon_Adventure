package controller;

import model.DungeonCharacter;

public class CombatSystem {

    /**
     * Executes a full round of combat between two dungeon characters.
     * For now, uses basic attack method from DungeonCharacter.
     */
    public static void battle(DungeonCharacter char1, DungeonCharacter char2) {
        System.out.println("\nCombat Begins: " + char1.getName() + " vs " + char2.getName());

        while (char1.isAlive() && char2.isAlive()) {
            System.out.println("\n" + char1.getName() + "'s Turn:");
            char1.attack(char2);

            if (char2.isAlive()) {
                System.out.println("\n" + char2.getName() + "'s Turn:");
                char2.attack(char1);
            }

            System.out.println("\nStatus:");
            System.out.println(char1);
            System.out.println(char2);
        }

        System.out.println("\nCombat Over!");
        if (char1.isAlive()) {
            System.out.println(char1.getName() + " is victorious!");
        } else {
            System.out.println(char2.getName() + " has defeated " + char1.getName() + "!");
        }
    }
}