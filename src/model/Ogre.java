package model;

/**
 * This class has all the logic for the Ogre class including
 * its special skill, stats and its name.
 *
 * @author Carson Poirier
 * @version O11/8/25
 */
public class Ogre extends Monster {
    public Ogre(String theName, int theHitPoints, int theMinDamage, int theMaxDamage,
                int theAttackSpeed, double theChanceToHit, double theChanceToHeal, int theMinHeal, int theMaxHeal ) {
        super( theName, theHitPoints, theMinDamage, theMaxDamage,theAttackSpeed, theChanceToHit,
        theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
