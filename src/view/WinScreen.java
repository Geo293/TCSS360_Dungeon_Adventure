package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.awt.*;

/**
 * This cless displays a win screen after the user collects all the pillars
 * and brings it to the exit
 *
 * @author Geovani Vasquez
 * @version 11/22/2025
 */
public class WinScreen extends Scene {
    /**
     * This is the controller object used throughout the game.
     */
    private GameController myController;
    /**
     * This is the button that takes you to the main menu
     */
    private Button myMenuButton;
    /**
     * This is the button that closes the application
     */
    private Button myExitButton;

    /**
     * This is the constructor that sets the variables and
     * displays  the screen
     * @param theController the controller object
     */
    public WinScreen(GameController theController) {
        super(new VBox());
        if (theController == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        myController = theController;
        myMenuButton = new Button("Main Menu");
        myExitButton = new Button("Exit");
        Label title = new Label("You Win!!");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        VBox box = choices();
        root.setStyle("-fx-background-color: #F5DEB3");
        root.getChildren().addAll(title, box);


    }

    /**
     * This sets up the choices that are presented to the user
     * @return returns the vbox that has all the buttons.
     */
    private VBox choices() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        myExitButton.setOnAction(e -> myController.exitGame());
        myMenuButton.setOnAction(e -> myController.goBackToStart());
        box.getChildren().addAll(myMenuButton, myExitButton);
        return box;
    }
}
