package view; // only include this if your file is inside a "view" package

import controller.GameController;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;



/**
 * Displays the dungeon
 * @author Geovani Vasquez
 * @version Oct 24, 2025
 */
public class DungeonGUI extends Application {
    private Stage myPrimaryStage;

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
        myPrimaryStage = thePrimaryStage;
        sizeRequirements();
        GameController myController = new GameController(thePrimaryStage);
        myController.startApp();

    }

    /**
     * This class sets the size of the screen so that the display fits different sizes screens
     * when loaded into different computers.
     */
    public void sizeRequirements(){
        Image icon = new Image(getClass().getResourceAsStream("/images/Icon/GameIcon.png"));
        myPrimaryStage.getIcons().add(icon);
        myPrimaryStage.setTitle("Dungeon_Adventure");
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = Math.min(1000, screenBounds.getWidth() * 0.8);
        double height = Math.min(1000, screenBounds.getHeight() * 0.8);
        myPrimaryStage.setWidth(width);
        myPrimaryStage.setHeight(height);
        myPrimaryStage.centerOnScreen();
        myPrimaryStage.setResizable(true);
        myPrimaryStage.setMinWidth(600);
        myPrimaryStage.setMinHeight(400);
    }

}
