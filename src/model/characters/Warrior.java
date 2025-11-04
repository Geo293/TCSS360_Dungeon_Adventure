package model.characters;

import Dungeon_Adventure.DungeonCharacter;

public class Warrior extends Hero {

    public Warrior(String name) {
        super(name, 125, 35, 60, 4, 0.8, 0.2);
    }

    @Override
    public void useSpecialSkill(DungeonCharacter opponent) {
        System.out.println(name + " attempts Crushing Blow!");
        if (rand.nextDouble() <= 0.4) {
            int damage = rand.nextInt(101) + 75; // 75–175
            opponent.subtractHitPoints(damage);
            System.out.println("Crushing Blow successful! " + opponent.getName() + " takes " + damage + " damage.");
        } else {
            System.out.println("Crushing Blow failed.");
        }
    }
}
