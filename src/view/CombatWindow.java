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
 * @author Carson Poirier
 * @author Geovnai Vasquez
 * @version 11/22/25
 */
public final class CombatWindow extends Scene {

    /** The hero participating in combat. */
    private final Hero myHero;

    /** The monster participating in combat. */
    private final Monster myMonster;

    /** Text area for logging combat actions. */
    private final TextArea myCombatLog;

    /** Label displaying hero stats. */
    private final Label myHeroStats;

    /** Label displaying monster stats. */
    private final Label myMonsterStats;

    /** Button for performing a regular attack. */
    private final Button myAttackButton;

    /** Button for performing a special skill. */
    private final Button mySpecialButton;

    /** Controller for managing game flow. */
    private final GameController myController;

    /** Image view for displaying the monster. */
    private final ImageView myMonsterImage;

    /** Image view for displaying the hero. */
    private final ImageView myHeroImage;

    /**
     * Constructs the CombatWindow scene.
     *
     * @param theHero          the player's hero character
     * @param theMonster       the monster to battle
     * @param theController    the game controller
     * @param theCharacterName the name of the hero character image
     */
    public CombatWindow(final Hero theHero, final Monster theMonster,
                        final GameController theController, final String theCharacterName) {
        super(new VBox(), 600, 400);
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
        myCombatLog.appendText(myHero.getMyName() + " attacks.\n");
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

    /**
     * Updates the displayed stats for both hero and monster.
     */
    private void updateStats() {
        myHeroStats.setText("Hero: " + myHero.getMyName() + " | HP: " + myHero.getMyHitPoints());
        myMonsterStats.setText("Monster: " + myMonster.getMyName() + " | HP: " + myMonster.getMyHitPoints());
    }

    /**
     * Checks if combat has ended and disables buttons if so.
     */
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
