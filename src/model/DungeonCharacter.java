package model;

/**
 * this class the parent class that every monster and or character will
 * be based on which include how many hit points the npc will have ,
 * its name, attack speed, range, and attack behavior
 *@author Geovani Vasquez
 * @version Oct, 24 2025
 */
public class DungeonCharacter {
    protected String myName;
    protected int myHealth;
    protected int myDamageMin;
    protected int myDamageMax;
    protected int myAttackSpeed;
    protected double myChance;
    protected DungeonCharacter(String theName, int theHitPoints, int theMinDamage,
                               int theMaxDamage, int theAttackSpeed, double theChance) {
        myName = theName;
        myHealth = theHitPoints;
        myDamageMin = theMinDamage;
        myDamageMax = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChance = theChance;
    }
    public void attack(DungeonCharacter opponent) {

    }
}


