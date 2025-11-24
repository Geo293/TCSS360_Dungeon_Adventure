package controller;

import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import view.*;
//import view.DungeonWindow;


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
    private PauseMenu myPauseMenu;
    private Dungeon myDungeon;
    private Hero myHero;
    private GameState myGameState;
    private DeathScreen myDeathScreen;
    private WinScreen myWinScreen;
    private HeroItemsPane myHeroItemsPane;
    private String myCharacterType;

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
        myCharacterType = theCharacterName;
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
        myCombatWindow = new CombatWindow(theHero, theMonster, this,myCharacterType);
        myStage.setScene(myCombatWindow);
    }
    public void pauseMenu(Dungeon theDungeon, Hero theHero){
        myPauseMenu = new PauseMenu(this, theDungeon, theHero);
        myStage.setScene(myPauseMenu);
    }
    public void saveQuit(Hero theHero, Dungeon theDungeon) {
        myGameState = new GameState(theHero, theDungeon);
        goBackToStart();
    }

    public void loadGame() {
        myGameWindow = new DungeonWindow(this,myGameState.getDungeon(), myGameState.getHero());
        myStage.setScene(myGameWindow);
    }
    public void backToDungeon(Hero theHero) {
        myHero = theHero;
        myGameWindow.refresh();
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
            if (myCurrentRoom.isExit()) {
                if (myHero.hasAllPillars()) {
                    gameWon();
                }
            }
        }

        public void exitGame(){
            Platform.exit();
        }
        public void heroDied(){
            myDeathScreen = new DeathScreen(this);
            myStage.setScene(myDeathScreen);
        }
        public void gameWon(){
            myWinScreen = new WinScreen(this);
            myStage.setScene(myWinScreen);
        }
        public void invetoryScreen(Hero theHero, Dungeon theDungeon){
            myHeroItemsPane = new HeroItemsPane(this,theHero, theDungeon);
            myStage.setScene(myHeroItemsPane);
        }
        public void usesVPotion(Dungeon theDungeon  ){
            myDungeon = theDungeon;
            myGameWindow.updateDisplayRadius(1);
            myStage.setScene(myGameWindow);
        }
}

