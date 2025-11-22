package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.awt.*;

public class WinScreen extends Scene {
    private GameController myController;
    private Button myMenuButton;
    private Button myExitButton;

    public WinScreen(GameController theController) {
        super(new VBox());
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        myController = theController;
        myMenuButton = new Button("Main Menu");
        myExitButton = new Button("Exit");
        Label title = new Label("You Win!!");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        VBox box = choices();
        root.getChildren().addAll(title, box);


    }
    private VBox choices() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        myExitButton.setOnAction(e -> myController.exitGame());
        myMenuButton.setOnAction(e -> myController.goBackToStart());
        box.getChildren().addAll(myMenuButton, myExitButton);
        return box;
    }
}
