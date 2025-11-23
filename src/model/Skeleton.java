package model;

/**
 * Represents a Skeleton monster.
 * Extends Monster and defines its stats and special skill behavior.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class Skeleton extends Monster {

    /**
     * Constructs a Skeleton with the given stats.
     *
     * @param theName        the name of the skeleton
     * @param theHitPoints   the hit points of the skeleton
     * @param theMinDamage   the minimum damage the skeleton can do
     * @param theMaxDamage   the maximum damage the skeleton can do
     * @param theAttackSpeed the attack speed of the skeleton
     * @param theChanceToHit the chance the skeleton has to hit
     * @param theChanceToHeal the chance the skeleton has to heal
     * @param theMinHeal     the minimum health the skeleton heals
     * @param theMaxHeal     the maximum health the skeleton heals
     */
    public Skeleton(final String theName, final int theHitPoints,
                    final int theMinDamage, final int theMaxDamage,
                    final int theAttackSpeed, final double theChanceToHit,
                    final double theChanceToHeal, final int theMinHeal,
                    final int theMaxHeal) {

        super(theName, theHitPoints, theMinDamage, theMaxDamage,
                theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
