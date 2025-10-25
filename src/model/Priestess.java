package model;
/**
 * This class has all the logic for the priestess class including
 * its special skill and stats.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Priestess extends Hero {
    protected Priestess(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}
