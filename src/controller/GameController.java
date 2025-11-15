package controller;

import javafx.stage.Stage;
import model.*;
import view.CharacterSelected;
//import view.DungeonWindow;
import view.CombatWindow;
import view.DungeonWindow;
import view.StartScreen;

/**
 * This class changes all the windows
 *
 * @author Geovani Vasquez
 * @version Oct, 31 2025
 */
public class GameController {
    /**
     * This is the start screen
     */
    private StartScreen myStartScreen;
    /**
     * this is the character select screen
     */
    private CharacterSelected myCharacterSelected;
    private Stage myStage;
    private DungeonWindow myGameWindow;
    private CombatWindow myCombatWindow;
    private Dungeon myDungeon;
    private Hero myHero;

    public GameController(Stage theStage) {
        myStage = theStage;
        myStartScreen = new StartScreen(this);
        myCharacterSelected = new CharacterSelected(this);


    }

    public void startApp() {
        myStage.setScene(myStartScreen);
        myStage.show();
    }

    public void startCharacter() {
        myStage.setScene(myCharacterSelected);
    }

    public void goBackToStart() {
        myStage.setScene(myStartScreen);
        myStartScreen.show();
    }

    public void startNewGame(String theCharacterType, String theCharacterName) {
        myDungeon = new Dungeon();
        switch (theCharacterType) {
            case "Warrior":
                myHero = new Warrior(theCharacterName);
                break;
            case "Priestess":
                myHero = new Priestess(theCharacterName);
                break;
            case "Thief":
                myHero = new Thief(theCharacterName);
                break;
        }
        myGameWindow = new DungeonWindow(this, myDungeon, myHero);
        myStage.setScene(myGameWindow);

    }

    public void startFight(Hero theHero, Monster theMonster) {
        myCombatWindow = new CombatWindow(theHero, theMonster, this);
        myStage.setScene(myCombatWindow);
    }

    public void backToDungeon() {
        myStage.setScene(myGameWindow);
    }

    public void processRoomEvents() {
            Room myCurrentRoom = myDungeon.getCurrentRoom();

            myHero.pickUpItem(myCurrentRoom);
            if (myCurrentRoom.hasPit()) {
                int damage = (int) (Math.random() * 20) + 1;
                myHero.subtractHitPoints(damage);
            }
            if (myCurrentRoom.getMonster() != null) {
                startFight(myHero, myCurrentRoom.getMonster());
                myCurrentRoom.removeMonster();
            }
        }
}

