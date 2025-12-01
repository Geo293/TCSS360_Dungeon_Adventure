package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;



/**
 * This class sets up a death screen after the user dies in combat. This
 * screen will allow the user to start a new game after they die or quit
 * the game.
 * @author Geovani Vasquez
 * @version 11/22/2025
 */
public class DeathScreen extends Scene {
    /**
     * This is the controller that allows the system to switch between screens
     */
    private final GameController myController;
    /**
     * This is the button the allows the user to exit
     */
    private final Button myExit;
    /**
     * this is the button the sends the user back to the
     * character select screen
     */
    private final Button myBackToCharatcer;

    /**
     * This is teh constructor that sets up all the field variables
     * and then sets up the VBox to displays the buttons
     * @param theController this is the controller object that controls the scenes
     */
    public DeathScreen(GameController theController) {
        super(new VBox());
        if (theController == null) {
            throw new IllegalArgumentException("Controller cannot be null");
        }
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        myController = theController;
        myExit = new Button("Exit");
        myBackToCharatcer = new Button("Back to Charater");
        Label title = new Label("You Died");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        VBox box = choices();
        root.setStyle("-fx-background-color: #F5DEB3");
        root.getChildren().addAll(title, box);


    }

    /**
     * this displays the choices that the user is allowed to
     * preform such as starting a new game or leaving the game.
     * by preforming an action on the controller class
     * @return the vbox of all the buttons
     */
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
