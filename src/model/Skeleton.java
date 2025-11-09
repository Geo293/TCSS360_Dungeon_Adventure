package model;
/**
 * This class has all the logic for the Skeleton class including
 * its special skill, stats and its name.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class Skeleton extends Monster {
    public Skeleton(String theName, int theHitPoints, int theMinDamage, int theMaxDamage,
                    int theAttackSpeed, double theChanceToHit, double theChanceToHeal, int theMinHeal, int theMaxHeal) {
        super(theName, theHitPoints, theMinDamage, theMaxDamage,theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
