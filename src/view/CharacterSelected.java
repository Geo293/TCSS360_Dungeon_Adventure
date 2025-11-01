package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;

public class CharacterSelected extends Scene {
    private Button myBackButton;
    private TextField myCharacterName;
    private GameController myController;


    public CharacterSelected(GameController theController) {
        super(new VBox(),300,250);
        myController = theController;
        myCharacterName = new TextField();
        myBackButton = new Button("Back to main menu");
        myBackButton.setOnAction(e -> myController.goBackToStart());

        ((VBox)this.getRoot()).getChildren().addAll(myCharacterName, myBackButton);

    }

}

