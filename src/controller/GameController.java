package controller;

import javafx.stage.Stage;
import view.CharacterSelected;
import view.StartScreen;

public class  logsGameController {
    private StartScreen myStartScreen;
    private CharacterSelected myCharacterSelected;
    private Stage myStage;

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

}
