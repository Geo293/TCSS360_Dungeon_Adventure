package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class StartScreen extends Scene{
    Button myButton;
    GameController myController;
    public StartScreen(GameController theController) {
        super(new StackPane(),300,250);
        myController = theController;
        myButton = new Button("Start the game");
        myButton.setOnAction(e -> myController.startCharacter());
        ((StackPane)this.getRoot()).getChildren().add(myButton);
    }
}
