package app;

import controller.GameMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.characters.Hero;
import model.characters.Warrior;
import model.dungeon.Dungeon;

public class GameMenuApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameMenuView.fxml"));
        Scene scene = new Scene(loader.load());

        GameMenuController controller = loader.getController();
        Dungeon dungeon = new Dungeon();
        Hero hero = new Warrior("Aragorn");
        controller.initializeGame(dungeon, hero);

        stage.setTitle("Dungeon Adventure");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
