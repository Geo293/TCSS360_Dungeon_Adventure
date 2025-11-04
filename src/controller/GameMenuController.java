package controller;

import javafx.scene.control.Alert;
import model.Dungeon;
import model.DungeonCharacter;

public class GameMenuController {
    private Dungeon dungeon;
    private DungeonCharacter hero;

    public void initializeGame(Dungeon dungeon, DungeonCharacter hero) {
        this.dungeon = dungeon;
        this.hero = hero;
    }

    public void moveNorth() { move("N"); }
    public void moveSouth() { move("S"); }
    public void moveEast()  { move("E"); }
    public void moveWest()  { move("W"); }

    private void move(String direction) {
        if (!dungeon.moveHero(direction)) {
            showAlert("Can't move in that direction.");
        } else {
            checkRoom();
        }
    }

    public void useHealingPotion() {
        // TODO: Implement when Hero class is ready
        showAlert("Healing potion functionality coming soon.");
    }

    public void useVisionPotion() {
        // TODO: Implement when Hero class is ready
        showAlert("Vision potion functionality coming soon.");
    }

    public void useSpecialSkill() {
        // TODO: Implement when Hero and Monster classes are ready
        showAlert("Special skill functionality coming soon.");
    }

    public void viewStats() {
        showAlert(hero.toString());
    }

    public void revealDungeon() {
        showAlert(dungeon.toString());
    }

    public void quitGame() {
        System.exit(0);
    }

    private void checkRoom() {
        // TODO: Implement monster checking when Monster class is ready
        // For now, just check if hero is still alive
        if (!hero.isAlive()) {
            showAlert("You have died.");
            System.exit(0);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}