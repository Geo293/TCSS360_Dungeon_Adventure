package controller;

import javafx.stage.Stage;
import model.*;
import view.CharacterSelected;
import view.DungeonWindow;
import view.StartScreen;

public class GameController {
    private StartScreen myStartScreen;
    private CharacterSelected myCharacterSelected;
    private Stage myStage;
    private DungeonWindow myGameWindow;
    private Dungeon myDungeon;
    private Hero myHero;

    public GameController(Stage theStage) {
        myStage = theStage;
        myStartScreen = new StartScreen(this);
        myCharacterSelected = new CharacterSelected(this);

    }
    public void startApp(){
     myStage.setScene(myStartScreen);
     myStage.show();
   }
    public void startCharacter(){
        myStage.setScene(myCharacterSelected);
    }

    public void goBackToStart() {
        myStage.setScene(myStartScreen);
    }
    public void startNewGame(String characterType, String characterName){
        myDungeon = new Dungeon();
        switch(characterType) {
            case "Warrior":
                //myHero = new Warrior(characterName);
                break;
            case "Priestess":
                //myHero = new Priestess(characterName);
                break;
            case "Thief":
                //myHero = new Thief(characterName);
                break;
        }
        myGameWindow = new DungeonWindow(this, myDungeon,myHero);
        myStage.setScene(myGameWindow);

    }
}
