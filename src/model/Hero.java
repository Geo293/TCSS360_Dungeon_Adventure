package model;

/**
 * This class will has all the basic logic that every playable character
 * will have including how many attacks they get, whether they block
 * an attack.
 *
 *@author Geovani Vasquez
 *@version Oct, 24 2025
 */
public class Hero extends DungeonCharacter {

    protected Hero(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}



