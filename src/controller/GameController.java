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
    private final StartScreen myStartScreen;
    /**
     * this is the character select screen
     */
    private final CharacterSelected myCharacterSelected;
    /**
     * This is the stage object that is displaying the screen
     */
    private final Stage myStage;
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
     * Save the gameState
     */
    private GameGuide myGameGuide;
    /**
     * This saves the game to console
     */
    private SaveLoadManager mySaveLoadManager;

    /**
     *This is the constructor that assigns the stage to fileds
     * @param theStage the main screen
     */
    public GameController(Stage theStage) {
        if (theStage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
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
        if (theCharacterType == null || theCharacterType.trim().isEmpty() ||
                theCharacterName == null || theCharacterName.trim().isEmpty()) {
            throw new IllegalArgumentException("the character type and name cannot be null or empty");
        }
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
        myGameWindow = new DungeonWindow(this, myDungeon, myHero,myCharacterType);
        myStage.setScene(myGameWindow);

    }

    /**
     * This displays the combat screen and send the current hero and the
     * current monster being fought into the combat class
     * @param theHero this is the current hero
     * @param theMonster this is the current monster
     */
    public void startFight(Hero theHero, Monster theMonster) {
        if(theMonster == null || theHero == null) {
            throw new IllegalArgumentException("the monster and hero cannot be null");
        }
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
        if(theDungeon == null || theHero == null) {
            throw new IllegalArgumentException("the dungeon and hero cannot be null");
        }
        myPauseMenu = new PauseMenu(this, theDungeon, theHero);
        myStage.setScene(myPauseMenu);
    }

    /**
     * This saves the game and then brings the game back to the main menu
     * @param theHero the current hero
     * @param theDungeon the current dungeon
     */
    public void saveQuit(Hero theHero, Dungeon theDungeon) {
        if(theHero == null || theDungeon == null) {
            throw new IllegalArgumentException("the dungeon and hero cannot be null");
        }
        myGameState = new GameState(theHero, theDungeon, myCharacterType);
        mySaveLoadManager =  new SaveLoadManager();
        mySaveLoadManager.saveGame(myGameState);
        goBackToStart();
    }

    /**
     * This loads a game that has already been saved then lets the user pick off where
     * they left off
     */
    public void loadGame() {
        myGameState = mySaveLoadManager.loadGame();
        myGameWindow = new DungeonWindow(this,myGameState.getDungeon(), myGameState.getHero(), myGameState.getCharacterName());
        myStage.setScene(myGameWindow);
    }

    /**
     * 
     * @param theHero
     */
    public void backToDungeon(Hero theHero) {
        if(theHero == null) {
            throw new IllegalArgumentException("the hero cannot be null");
        }
        myHero = theHero;
        myGameWindow.refresh();
        myStage.setScene(myGameWindow);
    }

    /**
     * This allows the hero to preform actions very time they
     * enter a new room and then returns it to the main game.
     */
    public void processRoomEvents() {
            Room myCurrentRoom = myDungeon.getCurrentRoom();

            myHero.pickUpItem(myCurrentRoom);
            if (myCurrentRoom.hasPit()) {
                int damage = (int) (Math.random() * 20) + 1;
                myHero.takePitDamage(damage);
                myGameWindow.updateHeroStats();
            }
            if (myCurrentRoom.getMyMonster() != null) {
                startFight(myHero, myCurrentRoom.getMyMonster());
                myCurrentRoom.removeMonster();
            }
            if (myCurrentRoom.isMyExit()) {
                if (myHero.hasAllPillars()) {
                    gameWon();
                }
            }
            if(!myHero.isAlive()) {
                heroDied();
            }
        }

    /**
     * This exits and closes the program when the games ends.
     */
    public void exitGame(){
            Platform.exit();
        }

    /**
     * when the hero dies this switches to the death screen
     */
    public void heroDied(){
            myDeathScreen = new DeathScreen(this);
            myStage.setScene(myDeathScreen);
        }

    /**
     * When the game is won this method switches to the win screen
     */
    public void gameWon(){
            myWinScreen = new WinScreen(this);
            myStage.setScene(myWinScreen);
        }

    /**
     * This method switches to the inventory screen and sends in the
     * current hero and the current dungeon
     * @param theHero the current hero
     * @param theDungeon the current dungeon
     */
    public void invetoryScreen(Hero theHero, Dungeon theDungeon){
        if(theHero == null || theDungeon == null) {
            throw new IllegalArgumentException("the hero and hero cannot be null");
        }
            myHeroItemsPane = new HeroItemsPane(this,theHero, theDungeon);
            myStage.setScene(myHeroItemsPane);
        }

    /**
     * This sets up the mini map to expand on use of a vision potion
     * @param theDungeon the current dungeon
     */
    public void usesVPotion(Dungeon theDungeon  ){
        if(theDungeon == null) {
            throw new IllegalArgumentException("the dungeon cannot be null");
        }
            myDungeon = theDungeon;
            myGameWindow.updateDisplayRadius(1);
            myStage.setScene(myGameWindow);
        }

    /**
     * This method switches to the guide that tells the user what all
     * the symbols on the mini map mean
     * @param theHero the hero class just to it can easily switch to the dungeon window
     */
    public void gameGuide(Hero theHero){
        if(theHero == null) {
            throw new IllegalArgumentException("the hero cannot be null");
        }
            myGameGuide = new GameGuide(this, theHero);
            myStage.setScene(myGameGuide);
        }
}

