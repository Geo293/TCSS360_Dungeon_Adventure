package model.characters;

import Dungeon_Adventure.DungeonCharacter;

public class Priestess extends Hero {

    public Priestess(String name) {
        super(name, 75, 25, 45, 5, 0.7, 0.3);
    }

    @Override
    public void useSpecialSkill(DungeonCharacter opponent) {
        int healAmount = rand.nextInt(21) + 30; // 30–50
        hitPoints += healAmount;
        System.out.println(name + " casts Heal and restores " + healAmount + " HP.");
    }
}
