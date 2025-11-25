package view;


import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Dungeon;
import model.Hero;

/**
 * This class displays the pause menu and allows the user to save and quit the game
 * and then if they want they can return to the game they are currently on.
 */
public class PauseMenu extends Scene {
    /**
     * This is the controller that allows the user to preform game actions
     * and allows the user to switch between scenes.
     */
    private GameController myController;
    /**
     * This is the dungeon object and has all the main
     * logic for the dungeon as a whole.
     */
    private Dungeon myDungeon;
    /**
     * This is the player object and has all the logic for the heros.
     */
    private Hero myHero;
    /**
     * This is the button that when pressed allows the user
     * to save their game then go back to main menu.
     */
     private Button mySaveQuit;
    /**
     * This is the button that allows the user to return to the game
     * they are currently on.
     */
    private Button myContinue;

    /**
     * This is the method that sets up local variables and displays the buttons
     * on the screen so that they may preform actions.
     * @param theController the controller object used throughout the game
     * @param theDungeon the dungeon object used throughoyt the game
     * @param theHero the hero object used throughout the game
     */
    public PauseMenu(GameController theController, Dungeon theDungeon, Hero theHero) {
            super(new VBox());
            VBox root = (VBox) getRoot();
            root.setAlignment(Pos.CENTER);
            myController = theController;
            myDungeon = theDungeon;
            myHero = theHero;
            mySaveQuit = new Button("Save Quit");
            mySaveQuit.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
            myContinue = new Button("Continue");
            myContinue.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
            setUpKeyListners();
            Label title = new Label("Game Pause");
            title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
            root.getChildren().addAll(title,myContinue, mySaveQuit);
            root.setStyle("-fx-background-color: #F5DEB3");

    }

    /**
     * Sets actions when the buttons are pressed 
     */
    public void setUpKeyListners(){
        mySaveQuit.setOnAction(e -> myController.saveQuit(myHero, myDungeon));
        myContinue.setOnAction(e -> myController.backToDungeon(myHero));
    }
}
