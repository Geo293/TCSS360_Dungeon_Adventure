package view;

import controller.CombatSystem;
import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Hero;
import model.Monster;
import util.CharacterImageLoader;

/**
 * JavaFX scene for handling turn-based combat between a Hero and a Monster.
 * Displays stats, logs actions, and provides buttons for attack and special skill.
 *
 * @author Carson
 * @version 11/30/25
 */
public final class CombatWindow extends Scene {

    private final Hero myHero;
    private final Monster myMonster;
    private final TextArea myCombatLog;
    private final Label myHeroStats;
    private final Label myMonsterStats;
    private final Button myAttackButton;
    private final Button mySpecialButton;
    private final GameController myController;
    private final ImageView myMonsterImage;
    private final ImageView myHeroImage;

    public CombatWindow(final Hero theHero, final Monster theMonster,
                        final GameController theController, final String theCharacterName) {
        super(new VBox(), 600, 400);
        if (theHero == null || theMonster == null || theController == null
                || theCharacterName == null || theCharacterName.trim().isEmpty()) {
            throw new IllegalArgumentException("The parameters cannot be empty");
        }
        myHero = theHero;
        myMonster = theMonster;
        myController = theController;

        final VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(15);

        myHeroStats = new Label();
        myMonsterStats = new Label();
        updateStats();

        myCombatLog = new TextArea();
        myCombatLog.setEditable(false);
        myCombatLog.setPrefHeight(200);
        myCombatLog.setWrapText(true);

        myAttackButton = new Button("Attack");
        mySpecialButton = new Button("Use Special Skill");

        myAttackButton.setOnAction(theEvent -> handleAttack());
        mySpecialButton.setOnAction(theEvent -> handleSpecial());

        final String monsterName = myMonster.getMyName();
        final HBox buttonBox = new HBox(10, myAttackButton, mySpecialButton);
        buttonBox.setAlignment(Pos.CENTER);

        myMonsterImage = new ImageView(new Image(CharacterImageLoader.getMonster(monsterName)));
        myMonsterImage.setFitWidth(200);
        myMonsterImage.setFitHeight(200);

        myHeroImage = new ImageView(new Image(CharacterImageLoader.getImageChar(theCharacterName)));
        myHeroImage.setFitWidth(200);
        myHeroImage.setFitHeight(200);

        root.setStyle("-fx-background-color: #F5DEB3");
        root.getChildren().addAll(myHeroImage, myHeroStats, myMonsterStats,
                myMonsterImage, buttonBox, myCombatLog);
    }

    /**
     * Handles a regular attack turn.
     */
    private void handleAttack() {
        // Calculate hero damage and log it
        if (myHero.canHit()) {
            int damage = myHero.calculateDamage();
            myMonster.subtractHitPoints(damage);
            myCombatLog.appendText(myHero.getMyName() + " hits "
                    + myMonster.getMyName() + " for " + damage + " damage.\n");
        } else {
            myCombatLog.appendText(myHero.getMyName() + " missed.\n");
        }

        // Monster retaliates via CombatSystem
        CombatSystem.battleRound(myHero, myMonster, myCombatLog);

        updateStats();
        checkCombatEnd();
    }

    /**
     * Handles the hero's special skill usage.
     */
    private void handleSpecial() {
        myCombatLog.appendText(myHero.getMyName() + " uses special skill.\n");
        myHero.specialSkill(myMonster);
        updateStats();
        checkCombatEnd();
    }

    private void updateStats() {
        myHeroStats.setText("Hero: " + myHero.getMyName() + " | HP: " + myHero.getMyHitPoints());
        myMonsterStats.setText("Monster: " + myMonster.getMyName() + " | HP: " + myMonster.getMyHitPoints());
    }

    private void checkCombatEnd() {
        if (!myMonster.isAlive()) {
            myCombatLog.appendText(myMonster.getMyName() + " has been defeated.\n");
            myAttackButton.setDisable(true);
            mySpecialButton.setDisable(true);
            myController.backToDungeon(myHero);
        } else if (!myHero.isAlive()) {
            myCombatLog.appendText(myHero.getMyName() + " has fallen.\n");
            myAttackButton.setDisable(true);
            mySpecialButton.setDisable(true);
            myController.heroDied();
        }
    }
}
