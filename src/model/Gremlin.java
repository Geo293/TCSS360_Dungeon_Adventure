package model;

/**
 * Represents a Gremlin monster.
 * Extends Monster and defines its stats and special skill behavior.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class Gremlin extends Monster {

    /**
     * Constructs a Gremlin with the given stats.
     *
     * @param theName        the name of the gremlin
     * @param theHitPoints   the hit points of the gremlin
     * @param theMinDamage   the minimum damage the gremlin can do
     * @param theMaxDamage   the maximum damage the gremlin can do
     * @param theAttackSpeed the attack speed of the gremlin
     * @param theChanceToHit the chance the gremlin has to hit
     * @param theChanceToHeal the chance the gremlin has to heal
     * @param theMinHeal     the minimum health the gremlin heals
     * @param theMaxHeal     the maximum health the gremlin heals
     */
    public Gremlin(final String theName, final int theHitPoints,
                   final int theMinDamage, final int theMaxDamage,
                   final int theAttackSpeed, final double theChanceToHit,
                   final double theChanceToHeal, final int theMinHeal,
                   final int theMaxHeal) {

        super(theName, theHitPoints, theMinDamage, theMaxDamage,
                theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
