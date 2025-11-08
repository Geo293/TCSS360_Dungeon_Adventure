package model;

/**
 * This class has all the logic for the Ogre class including
 * its special skill, stats and its name.
 *
 * @author Carson Poirier
 * @version O11/8/25
 */
public class Ogre extends Monster {
    public Ogre(String name) {
        super(name, 200, 30, 60, 2, 0.6, 0.1, 30, 60);
    }
}
