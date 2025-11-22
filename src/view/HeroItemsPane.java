package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Hero;

/**
 * GUI component that displays the current items the hero has.
 * Reads directly from Hero's fields (potions and pillars).
 */
public class HeroItemsPane extends Scene {

    private final Hero hero;
    private final Text potionsText;
    private final Text pillarsText;
    private GameController myController;

    public HeroItemsPane(GameController theController, Hero hero) {
        super(new VBox());
        VBox root = (VBox) getRoot();
        this.hero = hero;
        myController = theController;
        Label title = new Label("Your Items:");
        potionsText = new Text();
        pillarsText = new Text();
        setUpKeyListeners();
        root.getChildren().addAll(title, potionsText, pillarsText);

        refresh();
    }

    /**
     * Refreshes the display to match the hero's current items.
     */
    public void refresh() {
        potionsText.setText("Healing Potions: " + hero.getMyHealingPotions() +
                "\nVision Potions: " + hero.getMyVisionPotions());

        boolean[] pillars = hero.getMyPillarsFound();
        StringBuilder pillarStatus = new StringBuilder("Pillars Found:\n");
        String[] names = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};

        for (int i = 0; i < pillars.length; i++) {
            pillarStatus.append(names[i]).append(": ")
                    .append(pillars[i] ? "✓" : "✗").append("\n");
        }

        pillarsText.setText(pillarStatus.toString());
    }
    public void setUpKeyListeners(){
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case M:
                    myController.backToDungeon(hero);
            }
        });
    }

}
