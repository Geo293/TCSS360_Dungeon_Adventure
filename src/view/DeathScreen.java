package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


import java.awt.*;

public class DeathScreen extends Scene {
    private GameController myController;
    private Button myExit;
    private Button myBackToCharatcer;
    public DeathScreen(GameController theController) {
        super(new VBox());
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        myController = theController;
        myExit = new Button("Exit");
        myBackToCharatcer = new Button("Back to Charater");
        Label title = new Label("You Died");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        VBox box = choices();
        root.getChildren().addAll(title, box);


    }

    public VBox choices() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        myBackToCharatcer.setOnAction(e -> {
            myController.startCharacter();
        });
        myExit.setOnAction(e ->{
                myController.exitGame();
        });
        box.getChildren().addAll(myBackToCharatcer,myExit);
        return box;
    }
}
