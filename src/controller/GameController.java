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
    /**
     * This is the stage object that is displaying the screen
     */
    private Stage myStage;
    /**
     * This is the gameplay window screen object
     */
    private DungeonWindow myGameWindow;
    /**
     * This is the combat window screen object
     */
    private CombatWindow myCombatWindow;
    /**
     * This is the pause menu object
     */
    private PauseMenu myPauseMenu;
    /**
     * This is the dungeon object
     */
    private Dungeon myDungeon;
    /**
     * This is the hero object
     */
    private Hero myHero;
    /**
     * This is the game state
     */
    private GameState myGameState;
    /**
     * this is the death screen object
     */
    private DeathScreen myDeathScreen;
    /**
     * this is the win screen object
     */
    private WinScreen myWinScreen;
    /**
     * This is t eh inventory object
     */
    private HeroItemsPane myHeroItemsPane;
    /**
     * This is the type of character the user chose
     */
    private String myCharacterType;

    /**
     *This is the constructor that assigns the stage to fileds
     * @param theStage the main screen
     */
    public GameController(Stage theStage) {
        myStage = theStage;
        myStartScreen = new StartScreen(this);
        myCharacterSelected = new CharacterSelected(this);

    }

    /**
     * This sets the screen to be the main menu
     */
    public void startApp() {
        myStage.setScene(myStartScreen);
        myStage.show();
    }

    /**
     * This displays the character select screen
     */
    public void startCharacter() {
        myStage.setScene(myCharacterSelected);
    }

    /**
     * This takes the user back to the start screen and
     * displays the main menu
     */
    public void goBackToStart() {
        myStage.setScene(myStartScreen);
        myStartScreen.show();
    }

    /**
     * This starts the game and send what character the user choices in the game
     * and starts a new game and makes a new dungeon
     * @param theCharacterType this is the string for the type of character the user choose
     * @param theCharacterName this is the name the user inputed
     */
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

    /**
     * This displays the combat screen and send the current hero and the
     * current monster being fought into the combat class
     * @param theHero this is the current hero
     * @param theMonster this is the current monster
     */
    public void startFight(Hero theHero, Monster theMonster) {
        myCombatWindow = new CombatWindow(theHero, theMonster, this,myCharacterType);
        myStage.setScene(myCombatWindow);
    }

    /**
     * This displays the pause menu and sends in the current info
     * just in case the user wants to save and quit
     * @param theDungeon this is the current dungeon
     * @param theHero this is the current hero
     */
    public void pauseMenu(Dungeon theDungeon, Hero theHero){
        myPauseMenu = new PauseMenu(this, theDungeon, theHero);
        myStage.setScene(myPauseMenu);
    }

    /**
     * This saves the game and then brings the game back to the main menu
     * @param theHero the current hero
     * @param theDungeon the current dungeon
     */
    public void saveQuit(Hero theHero, Dungeon theDungeon) {
        myGameState = new GameState(theHero, theDungeon);
        goBackToStart();
    }

    /**
     * This loads a game that has already been saved then lets the user pick off where
     * they left off
     */
    public void loadGame() {
        myGameWindow = new DungeonWindow(this,myGameState.getDungeon(), myGameState.getHero());
        myStage.setScene(myGameWindow);
    }

    /**
     * 
     * @param theHero
     */
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

