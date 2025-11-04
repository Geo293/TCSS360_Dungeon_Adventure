package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.characters.Hero;
import model.characters.Monster;
import model.dungeon.Dungeon;

public class GameMenuController {
    private Dungeon dungeon;
    private Hero hero;

    public void initializeGame(Dungeon dungeon, Hero hero) {
        this.dungeon = dungeon;
        this.hero = hero;
    }

    @FXML private void moveNorth() { move("N"); }
    @FXML private void moveSouth() { move("S"); }
    @FXML private void moveEast()  { move("E"); }
    @FXML private void moveWest()  { move("W"); }

    private void move(String direction) {
        if (!dungeon.moveHero(hero, direction)) {
            showAlert("Can't move in that direction.");
        } else {
            checkRoom();
        }
    }

    @FXML private void useHealingPotion() {
        dungeon.useHealingPotion(hero);
        showAlert("Healing potion used.");
    }

    @FXML private void useVisionPotion() {
        dungeon.useVisionPotion(hero);
        showAlert("Vision potion used.");
    }

    @FXML private void useSpecialSkill() {
        Monster monster = dungeon.getMonsterAt(hero);
        if (monster != null && monster.isAlive()) {
            hero.useSpecialSkill(monster);
        } else {
            showAlert("No monster to use skill on.");
        }
    }

    @FXML private void viewStats() {
        showAlert(hero.toString());
    }

    @FXML private void revealDungeon() {
        dungeon.revealEntireDungeon();
        showAlert("Dungeon revealed.");
    }

    @FXML private void quitGame() {
        System.exit(0);
    }

    private void checkRoom() {
        Monster monster = dungeon.getMonsterAt(hero);
        if (monster != null && monster.isAlive()) {
            CombatSystem.battle(hero, monster);
        }
        if (!hero.isAlive()) {
            showAlert("You have died.");
            System.exit(0);
        } else if (dungeon.hasWon(hero)) {
            showAlert("You won!");
            dungeon.revealEntireDungeon();
            System.exit(0);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
