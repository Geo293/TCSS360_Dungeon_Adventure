package view;

import controller.CombatSystem;
import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Hero;
import model.Monster;

/**
 * JavaFX scene for handling turn-based combat between a Hero and a Monster.
 * Displays stats, logs actions, and provides buttons for attack and special skill.
 */
public class CombatWindow extends Scene {
    private final Hero hero;
    private final Monster monster;
    private final TextArea combatLog;
    private final Label heroStats;
    private final Label monsterStats;
    private final Button attackButton;
    private final Button specialButton;
    private GameController myController;

    /**
     * Constructs the CombatWindow scene.
     *
     * @param hero    The player's hero character.
     * @param monster The monster to battle.
     */
    public CombatWindow(Hero hero, Monster monster , GameController theController) {
        super(new VBox(), 600, 400);
        this.hero = hero;
        this.monster = monster;
        myController = theController;
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);

        heroStats = new Label();
        monsterStats = new Label();
        updateStats();

        combatLog = new TextArea();
        combatLog.setEditable(false);
        combatLog.setPrefHeight(200);
        combatLog.setWrapText(true);

        attackButton = new Button("Attack");
        specialButton = new Button("Use Special Skill");

        attackButton.setOnAction(e -> handleAttack());
        specialButton.setOnAction(e -> handleSpecial());

        HBox buttonBox = new HBox(10, attackButton, specialButton);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(heroStats, monsterStats, buttonBox, combatLog);
    }

    /**
     * Handles a regular attack turn.
     */
    private void handleAttack() {
        combatLog.appendText(hero.getMyName() + " attacks.\n");
        CombatSystem.battleRound(hero, monster, combatLog);
        updateStats();
        checkCombatEnd();
    }

    /**
     * Handles the hero's special skill usage.
     */
    private void handleSpecial() {
        combatLog.appendText(hero.getMyName() + " uses special skill.\n");
       //hero.useSpecialSkill(monster);
        updateStats();
        checkCombatEnd();
    }

    /**
     * Updates the displayed stats for both hero and monster.
     */
    private void updateStats() {
        heroStats.setText("Hero: " + hero.getMyName() + " | HP: " + hero.getMyHitPoints());
        monsterStats.setText("Monster: " + monster.getMyName() + " | HP: " + monster.getMyHitPoints());
    }

    /**
     * Checks if combat has ended and disables buttons if so.
     */
    private void checkCombatEnd() {
        if (!monster.isAlive()) {
            combatLog.appendText(monster.getMyName() + " has been defeated.\n");
            attackButton.setDisable(true);
            specialButton.setDisable(true);
            myController.backToDungeon();
        } else if (!hero.isAlive()) {
            combatLog.appendText(hero.getMyName() + " has fallen.\n");
            attackButton.setDisable(true);
            specialButton.setDisable(true);

        }
    }
}
