package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * A boss monster that appears in pillar rooms.
 * Stronger than a regular Ogre, with stats defined in the database(TODO).
 * @author Carson Poirier
 * @version 11/15/25
 */
public class SuperOgre extends Monster implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public SuperOgre(String name, int hitPoints, int minDamage, int maxDamage,
                     int attackSpeed, double chanceToHit,
                     double chanceToHeal, int minHeal, int maxHeal) {
        super(name, hitPoints, minDamage, maxDamage,
                attackSpeed, chanceToHit,
                chanceToHeal, minHeal, maxHeal);
    }
}
