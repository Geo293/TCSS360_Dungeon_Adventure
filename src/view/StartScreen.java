package view;

import controller.GameController;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * This class displays the start screen and lets the user start the game
 * @author Geovani Vasquez
 * @version Nov 07, 2025
 */
public class StartScreen extends Scene {
    /**
     * This field is the start button that allows the user
     * to go to the character select screen.
     */
    private final Button myNewGameButton;
    /**
     * This is an object of the gamecontroller class which
     * is what controls what classes are being presented at a time.
     */
    private final GameController myController;
    private final Button myLoadGame;
    private final Button myExitGame;
    private final Label myTitle;

    /**
     * This method is the controctor and sets the fields and the sets what
     * appears on the window such as the title and start button also sets
     * the color of the background.
     * @param theController this is the controller class the sets what windows are being presented.
     */
    public StartScreen(GameController theController) {
        super(new VBox());
        if (theController == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        VBox root = (VBox) getRoot();
        root.setAlignment(javafx.geometry.Pos.CENTER);
        myController = theController;
        myNewGameButton = new Button("Start New Game");
        myLoadGame = new Button("Load game");
        myExitGame = new Button("Exit");
        myTitle = new Label("Dungeon Adventure");
        setArt();
        setUpButtons();
        root.getChildren().addAll(myTitle, myNewGameButton, myLoadGame, myExitGame);
        root.setStyle("-fx-background-color: #F5DEB3");
        fadeIn();
    }

    /**
     * This method sets up how the buttons will look and how the title will look.
     */
    public void setArt(){
        myNewGameButton.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myNewGameButton.setOnAction(e -> fadeToCharacterSelect());
        myLoadGame.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myExitGame.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myTitle.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");

    }

    /**
     * This method sets up what the buttons will do when pressed.
     */
    public void setUpButtons(){
        myLoadGame.setOnAction(e-> myController.loadGame());
        myExitGame.setOnAction(e-> myController.exitGame());
    }

    /**
     * When called it will fade into the start screen.
     */
    public void show() {
        fadeIn();
    }

    /**
     * This sets all the setting for when the start fades in
     * like how long it fades in.
     */
    private void fadeIn() {
        VBox root = (VBox) getRoot();  // Changed to VBox
        root.setOpacity(1.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    /**
     * This ses up when the start screen fades out to a different screen
     * so when the user presses the start button it fades into the character selected
     * class.
     */
    private void fadeToCharacterSelect() {
        VBox root = (VBox) getRoot();  // Changed to VBox
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> myController.startCharacter());
        fadeOut.play();
    }

}
