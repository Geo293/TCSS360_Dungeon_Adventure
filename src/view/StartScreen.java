package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class StartScreen extends Scene{
    Button myButton;
    GameController myController;
    public StartScreen(GameController theController) {
        super(new StackPane());
        StackPane root = (StackPane)getRoot();
        root.setAlignment(javafx.geometry.Pos.CENTER);
        myController = theController;
        myButton = new Button("Start the game");
        myButton.setOnAction(e -> myController.startCharacter());
        root.getChildren().add(myButton);
    }
}
