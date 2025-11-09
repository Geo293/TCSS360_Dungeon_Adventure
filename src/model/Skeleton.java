package model;
/**
 * This class has all the logic for the Skeleton class including
 * its special skill, stats and its name.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public class Skeleton extends Monster {
    public Skeleton(String name) {
        super(name, 100, 30, 50, 3, 0.8, 0.3, 30, 50);
    }
}
