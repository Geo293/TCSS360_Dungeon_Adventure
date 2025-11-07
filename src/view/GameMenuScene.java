package view;

import controller.GameMenuController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.characters.Hero;
import model.dungeon.Dungeon;

public class GameMenuScene extends Scene {
    private GameMenuController myController;

    public GameMenuScene(Dungeon theDungeon, Hero theHero) {
        super(new VBox());

        VBox root = (VBox) this.getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);


        myController = new GameMenuController();
        myController.initializeGame(theDungeon, theHero);

        Label title = new Label("Choose an action:");

        Button moveNorthBtn = new Button("Move North");
        moveNorthBtn.setOnAction(e -> myController.moveNorth());

        Button moveSouthBtn = new Button("Move South");
        moveSouthBtn.setOnAction(e -> myController.moveSouth());

        Button moveEastBtn = new Button("Move East");
        moveEastBtn.setOnAction(e -> myController.moveEast());

        Button moveWestBtn = new Button("Move West");
        moveWestBtn.setOnAction(e -> myController.moveWest());

        Button healingPotionBtn = new Button("Use Healing Potion");
        healingPotionBtn.setOnAction(e -> myController.useHealingPotion());

        Button visionPotionBtn = new Button("Use Vision Potion");
        visionPotionBtn.setOnAction(e -> myController.useVisionPotion());

        Button specialSkillBtn = new Button("Use Special Skill");
        specialSkillBtn.setOnAction(e -> myController.useSpecialSkill());

        Button viewStatsBtn = new Button("View Hero Stats");
        viewStatsBtn.setOnAction(e -> myController.viewStats());

        Button revealBtn = new Button("Reveal Dungeon (Debug)");
        revealBtn.setOnAction(e -> myController.revealDungeon());

        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(e -> myController.quitGame());

        // Add all buttons to the layout
        root.getChildren().addAll(
                title,
                moveNorthBtn,
                moveSouthBtn,
                moveEastBtn,
                moveWestBtn,
                healingPotionBtn,
                visionPotionBtn,
                specialSkillBtn,
                viewStatsBtn,
                revealBtn,
                quitBtn
        );
    }
}