package view;

import controller.CombatSystem;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Hero;
import model.Monster;

import java.awt.*;

import static javax.swing.SwingUtilities.getRoot;

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

    /**
     * Constructs the CombatWindow scene.
     *
     * @param hero    The player's hero character.
     * @param monster The monster to battle.
     */
    public CombatWindow(Hero hero, Monster monster) {
        super(new VBox(), 600, 400);
        this.hero = hero;
        this.monster = monster;

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
        combatLog.appendText(hero.getName() + " attacks.\n");
        CombatSystem.battleRound(hero, monster, combatLog);
        updateStats();
        checkCombatEnd();
    }

    /**
     * Handles the hero's special skill usage.
     */
    private void handleSpecial() {
        combatLog.appendText(hero.getName() + " uses special skill.\n");
        hero.useSpecialSkill(monster);
        updateStats();
        checkCombatEnd();
    }

    /**
     * Updates the displayed stats for both hero and monster.
     */
    private void updateStats() {
        heroStats.setText("Hero: " + hero.getName() + " | HP: " + hero.getHitPoints());
        monsterStats.setText("Monster: " + monster.getName() + " | HP: " + monster.getHitPoints());
    }

    /**
     * Checks if combat has ended and disables buttons if so.
     */
    private void checkCombatEnd() {
        if (!monster.isAlive()) {
            combatLog.appendText(monster.getName() + " has been defeated.\n");
            attackButton.setDisable(true);
            specialButton.setDisable(true);
        } else if (!hero.isAlive()) {
            combatLog.appendText(hero.getName() + " has fallen.\n");
            attackButton.setDisable(true);
            specialButton.setDisable(true);
        }
    }
}
