package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Dungeon;
import model.Hero;

/**
 * JavaFX scene component that displays the current items the hero has.
 * Reads directly from Hero's fields (potions and pillars) and allows usage of potions.
 *
 * @author Carson Poirier
 * @author Geovani Vasquez
 * @version 11/22/25
 */
public final class HeroItemsPane extends Scene {

    /** The hero whose items are displayed. */
    private final Hero myHero;

    /** Text area showing potion counts. */
    private final Text myPotionsText;

    /** Reference to the game controller. */
    private final GameController myController;

    /** Reference to the dungeon (needed for vision potion). */
    private final Dungeon myDungeon;

    /** Button for using a vision potion. */
    private final Button myVisionPotionButton;

    /** Button for using a healing potion. */
    private final Button myHealingPotionButton;

    /**
     * Constructs the HeroItemsPane scene.
     *
     * @param theController the game controller
     * @param theHero       the hero whose items are displayed
     * @param theDungeon    the dungeon (needed for vision potion usage)
     */
    public HeroItemsPane(final GameController theController,
                         final Hero theHero,
                         final Dungeon theDungeon) {
        super(new VBox());
        final VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        myHero = theHero;
        myDungeon = theDungeon;
        myController = theController;

        final Label title = new Label("Your Items:");
        myPotionsText = new Text();

        myVisionPotionButton = new Button("Use Vision Potion");
        myHealingPotionButton = new Button("Use Healing Potion");

        setUpKeyListeners();
        setUpButtonActions();
        root.setStyle("-fx-background-color: #F5DEB3");
        root.getChildren().addAll(title, myPotionsText,
                myVisionPotionButton, myHealingPotionButton);

        refresh();
    }

    /**
     * Refreshes the display to match the hero's current items.
     */
    public void refresh() {
        myPotionsText.setText("Healing Potions: " + myHero.getMyHealingPotions()
                + "\nVision Potions: " + myHero.getMyVisionPotions());
    }

    /**
     * Sets up keyboard shortcuts for returning to the dungeon.
     */
    private void setUpKeyListeners() {
        this.setOnKeyPressed(theEvent -> {
            switch (theEvent.getCode()) {
                case M, ESCAPE -> myController.backToDungeon(myHero);
                default -> { }
            }
        });
    }

    /**
     * Sets up button actions for potion usage.
     */
    private void setUpButtonActions() {
        myVisionPotionButton.setOnAction(theEvent -> {
            if (myHero.getMyVisionPotions() > 0) {
                myHero.useVisionPotion(myDungeon);
                myController.usesVPotion(myDungeon);
                refresh();
            }
        });

        myHealingPotionButton.setOnAction(theEvent -> {
            if (myHero.getMyHealingPotions() > 0) {
                myHero.useHealingPotion();
                refresh();
            }
        });
    }
}

