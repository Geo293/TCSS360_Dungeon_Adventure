package model;

/**
 * This class has all the logic for the warrior class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Warrior extends  Hero{
    protected Warrior(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}
