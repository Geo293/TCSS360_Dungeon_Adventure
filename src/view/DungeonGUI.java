package view; // only include this if your file is inside a "view" package

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Displays the dungeon
 * @author Geovani Vasquez
 * @version Oct 24, 2025
 */
public class DungeonGUI extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dungeon_Adventure");
        button = new Button("Start the game");

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene openingWindow = new Scene(layout, 300, 250);
        primaryStage.setScene(openingWindow);
        primaryStage.show();
    }
}
