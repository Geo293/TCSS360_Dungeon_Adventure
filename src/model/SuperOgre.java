package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a Super Ogre monster.
 * A boss monster that appears in pillar rooms.
 * Stronger than a regular Ogre, with stats defined in the database.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class SuperOgre extends Monster implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a SuperOgre with the given stats.
     *
     * @param theName        the name of the super ogre
     * @param theHitPoints   the hit points of the super ogre
     * @param theMinDamage   the minimum damage the super ogre can do
     * @param theMaxDamage   the maximum damage the super ogre can do
     * @param theAttackSpeed the attack speed of the super ogre
     * @param theChanceToHit the chance the super ogre has to hit
     * @param theChanceToHeal the chance the super ogre has to heal
     * @param theMinHeal     the minimum health the super ogre heals
     * @param theMaxHeal     the maximum health the super ogre heals
     */
    public SuperOgre(final String theName, final int theHitPoints,
                     final int theMinDamage, final int theMaxDamage,
                     final int theAttackSpeed, final double theChanceToHit,
                     final double theChanceToHeal, final int theMinHeal,
                     final int theMaxHeal) {

        super(theName, theHitPoints, theMinDamage, theMaxDamage,
                theAttackSpeed, theChanceToHit,
                theChanceToHeal, theMinHeal, theMaxHeal);
    }
}
