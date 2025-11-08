package view; // only include this if your file is inside a "view" package

import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.awt.*;


/**
 * Displays the dungeon
 * @author Geovani Vasquez
 * @version Oct 24, 2025
 */
public class DungeonGUI extends Application {


    /**
     * This method launches the game
     * @param args is the argyment
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method creates the window and changes the app icon and sets the window to
     * be the starting window.
     * @param thePrimaryStage is the class arguments
     */
    @Override
    public void start(Stage thePrimaryStage) {
        Image icon = new Image(getClass().getResourceAsStream("/images/Icon/GameIcon.png"));
        thePrimaryStage.getIcons().add(icon);
        thePrimaryStage.setTitle("Dungeon_Adventure");
        thePrimaryStage.setWidth(400);
        thePrimaryStage.setHeight(300);
        GameController myController = new GameController(thePrimaryStage);
        myController.startApp();

    }

}
