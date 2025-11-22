package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Hero;
import model.Dungeon;

/**
 * GUI component that displays the current items the hero has
 * and allows the player to use potions.
 *
 * @author Carson Poirier
 * @version 11/22/25
 */
public class HeroItemsPane extends VBox {

    private final Hero myHero;
    private final Dungeon myDungeon;
    private final Text myPotionsText;
    private final Text myPillarsText;

    /**
     * Constructs a HeroItemsPane for the given hero and dungeon.
     *
     * @param theHero    the hero whose items are displayed
     * @param theDungeon the dungeon (needed for vision potion use)
     */
    public HeroItemsPane(final Hero theHero, final Dungeon theDungeon) {
        myHero = theHero;
        myDungeon = theDungeon;

        final Label title = new Label("Your Items:");
        myPotionsText = new Text();
        myPillarsText = new Text();

        final Button useHealingButton = new Button("Use Healing Potion");
        useHealingButton.setOnAction(theEvent -> {
            myHero.useHealingPotion();
            refresh();
        });

        final Button useVisionButton = new Button("Use Vision Potion");
        useVisionButton.setOnAction(theEvent -> {
            myHero.useVisionPotion(myDungeon);
            refresh();
        });

        this.getChildren().addAll(title, myPotionsText, myPillarsText,
                useHealingButton, useVisionButton);

        refresh();
    }

    /**
     * Refreshes the display to match the hero's current items.
     */
    public void refresh() {
        myPotionsText.setText("Healing Potions: " + myHero.getHealingPotions()
                + "\nVision Potions: " + myHero.getVisionPotions());

        final boolean[] pillars = myHero.getPillarsFound();
        final String[] names = {"Abstraction", "Encapsulation", "Inheritance", "Polymorphism"};
        final StringBuilder pillarStatus = new StringBuilder("Pillars Found:\n");

        for (int i = 0; i < pillars.length; i++) {
            pillarStatus.append(names[i]).append(": ")
                    .append(pillars[i] ? "✓" : "✗").append("\n");
        }

        myPillarsText.setText(pillarStatus.toString());
    }
}

