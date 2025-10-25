package model;

import java.util.Random;

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
    protected int myMaxHealth;
    protected int myDamageMin;
    protected int myDamageMax;
    protected int myAttackSpeed;
    protected double myChance;

    protected DungeonCharacter(String theName, int theHitPoints, int theMinDamage,
                               int theMaxDamage, int theAttackSpeed, double theChance) {
        myName = theName;
        myHealth = theHitPoints;
        myMaxHealth = theHitPoints;
        myDamageMin = theMinDamage;
        myDamageMax = theMaxDamage;
        myAttackSpeed = theAttackSpeed;
        myChance = theChance;
    }
    public void attack(DungeonCharacter opponent) {
        Random rand = new Random();
        double roll = rand.nextDouble();
        int damageRoll = rand.nextInt(myDamageMax - myDamageMin) + myDamageMin +1;
        if(roll <= myChance){
            opponent.myHealth -= damageRoll;
        } else {
            System.out.print("faild");
        }
    }
    public String getName() {
        return myName;
    }
    public int getHealth() {
        return myHealth;
    }
    public int getMyAttackSpeed(){
        return myAttackSpeed;
    }
    public boolean isAlive(){
        return myHealth > 0;
    }
    public void setHealth(int theHeal) {
        if (myHealth < myMaxHealth) {
            myHealth = theHeal + myHealth;
            if (myHealth > myMaxHealth) {
                myHealth = myMaxHealth;
            }
        }
    }
}


