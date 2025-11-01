package model;

/**
 * This class has all the logic for the Ogre class including
 * its special skill, stats and its name.
 *
 * @author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class Ogre extends Monster {
    protected Ogre(String theName, int theHitPoints, int theMinDamage, int theMaxDamage, int theAttackSpeed, double theChance) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage, theAttackSpeed, theChance);
    }
}
