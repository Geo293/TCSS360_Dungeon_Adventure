package view;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Hero;

/**
 * GUI component that displays the current items the hero has.
 * Reads directly from Hero's fields (potions and pillars).
 */
public class HeroItemsPane extends VBox {

    private final Hero hero;
    private final Text potionsText;
    private final Text pillarsText;

    public HeroItemsPane(Hero hero) {
        this.hero = hero;

        Label title = new Label("Your Items:");
        potionsText = new Text();
        pillarsText = new Text();

        this.getChildren().addAll(title, potionsText, pillarsText);

        refresh();
    }

    /**
     * Refreshes the display to match the hero's current items.
     */
    public void refresh() {
        potionsText.setText("Healing Potions: " + hero.getHealingPotions() +
                "\nVision Potions: " + hero.getVisionPotions());

        boolean[] pillars = hero.getPillarsFound();
        StringBuilder pillarStatus = new StringBuilder("Pillars Found:\n");
        String[] names = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};

        for (int i = 0; i < pillars.length; i++) {
            pillarStatus.append(names[i]).append(": ")
                    .append(pillars[i] ? "✓" : "✗").append("\n");
        }

        pillarsText.setText(pillarStatus.toString());
    }
}
