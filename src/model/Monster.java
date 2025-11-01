package model;

/**
 * This is the child class of DungeonCharacter but is also the parent class
 * of all the monsters this class will include an option to heal during or after
 * a battle and the odds of it happening
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Monster extends DungeonCharacter {
    protected Monster(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}
