package model;

/**
 * Represents an Ogre monster.
 * Extends Monster and defines its stats and special skill behavior.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class Ogre extends Monster {

    /**
     * Constructs an Ogre with the given stats.
     *
     * @param theName        the name of the ogre
     * @param theHitPoints   the hit points of the ogre
     * @param theMinDamage   the minimum damage the ogre can do
     * @param theMaxDamage   the maximum damage the ogre can do
     * @param theAttackSpeed the attack speed of the ogre
     * @param theChanceToHit the chance the ogre has to hit
     * @param theChanceToHeal the chance the ogre has to heal
     * @param theMinHeal     the minimum health the ogre heals
     * @param theMaxHeal     the maximum health the ogre heals
     */
    public Ogre(final String theName, final int theHitPoints,
                final int theMinDamage, final int theMaxDamage,
                final int theAttackSpeed, final double theChanceToHit,
                final double theChanceToHeal, final int theMinHeal,
                final int theMaxHeal) {

        super(theName, theHitPoints, theMinDamage, theMaxDamage,
                theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
