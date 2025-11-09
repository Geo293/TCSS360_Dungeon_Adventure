package model;
/**
 * This class has all the logic for the Gremlin class including
 * its special skill, stats and its name.
 *
 * @author carson Poirier
 * @version 11/8/25
 */
public class Gremlin extends Monster {
    public Gremlin(String theName, int theHitPoints, int theMinDamage, int theMaxDamage,
                   int theAttackSpeed, double theChanceToHit, double theChanceToHeal, int theMinHeal, int theMaxHeal ) {

        super(theName, theHitPoints, theMinDamage, theMaxDamage,theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
