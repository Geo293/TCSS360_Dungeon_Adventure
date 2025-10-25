package model;
/**
 * This class has all the logic for the Gremlin class including
 * its special skill, stats and its name.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Gremlin extends Monster{
    protected Gremlin(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
       super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}
