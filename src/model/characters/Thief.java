package model.characters;

import Dungeon_Adventure.DungeonCharacter;

public class Thief extends Hero {

    public Thief(String name) {
        super(name, 75, 20, 40, 6, 0.8, 0.4);
    }

    @Override
    public void useSpecialSkill(DungeonCharacter opponent) {
        double roll = rand.nextDouble();
        if (roll <= 0.4) {
            System.out.println(name + " performs a Surprise Attack!");
            attack(opponent);
            if (opponent.isAlive()) {
                System.out.println("Thief gets an extra attack!");
                attack(opponent);
            }
        } else if (roll <= 0.6) {
            System.out.println(name + " was caught! No attack this round.");
        } else {
            System.out.println(name + " performs a normal attack.");
            attack(opponent);
        }
    }
}
