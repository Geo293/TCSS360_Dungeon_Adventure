package model.characters;

import Dungeon_Adventure.DungeonCharacter;

public abstract class Hero extends DungeonCharacter {
    private String name;
    private boolean isAlive = true;
    protected double chanceToBlock;

    public Hero(String name, int hitPoints, int minDamage, int maxDamage,
                int attackSpeed, double chanceToHit, double chanceToBlock) {
        super(name, hitPoints, minDamage, maxDamage, attackSpeed, chanceToHit);
        this.chanceToBlock = chanceToBlock;
    }

    public boolean blockAttack() {
        return rand.nextDouble() <= chanceToBlock;
    }

    public abstract void useSpecialSkill(DungeonCharacter opponent);
    public String getName() {

        return name;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
