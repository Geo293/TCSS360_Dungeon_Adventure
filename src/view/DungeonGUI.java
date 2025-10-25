package view; // only include this if your file is inside a "view" package

import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Observer;

/**
 * Displays the dungeon
 * @author Geovani Vasquez
 * @version Oct 24, 2025
 */
public class DungeonGUI extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dungeon_Adventure");

        GameController myController = new GameController(primaryStage);
        myController.startApp();

    }

}
