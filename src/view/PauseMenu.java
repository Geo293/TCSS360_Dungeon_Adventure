package view;


import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Dungeon;
import model.Hero;

public class PauseMenu extends Scene {
    private GameController myController;
    private Dungeon myDungeon;
    private Hero myHero;
     private Button mySaveQuit;
     private Button myContinue;

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
            mySaveQuit.setOnAction(e -> myController.saveQuit(myHero, myDungeon));
            myContinue.setOnAction(e -> myController.backToDungeon(myHero));
            Label title = new Label("Game Pause");
            title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
            root.getChildren().addAll(title,myContinue, mySaveQuit);
            root.setStyle("-fx-background-color: #F5DEB3");

    }
}
