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
 * GUI component that displays the current items the hero has.
 * Reads directly from Hero's fields (potions and pillars).
 */
public class HeroItemsPane extends Scene {

    private final Hero hero;
    private final Text potionsText;
    private GameController myController;
    private Dungeon myDungeon;
    private Button myVPotion;
    private Button myHPotion;

    public HeroItemsPane(GameController theController, Hero theHero, Dungeon theDungeon) {
        super(new VBox());
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        this.hero = theHero;
        myDungeon = theDungeon;
        myController = theController;
        Label title = new Label("Your Items:");
        potionsText = new Text();
        myVPotion = new Button("use vision potion");
        myHPotion = new Button("use health potion");
        setUpKeyListeners();
        root.getChildren().addAll(title, potionsText,myVPotion,myHPotion);

        refresh();
    }

    /**
     * Refreshes the display to match the hero's current items.
     */
    public void refresh() {
        potionsText.setText("Healing Potions: " + hero.getMyHealingPotions() +
                "\nVision Potions: " + hero.getMyVisionPotions());

    }
    public void setUpKeyListeners(){
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case M:
                    myController.backToDungeon(hero);
                    break;
                case ESCAPE:
                    myController.backToDungeon(hero);
                    break;
            }
        });
        if(hero.getMyHealingPotions() > 0) {
            myVPotion.setOnAction(event -> {
                hero.useVisionPotion(myDungeon);
                myController.usesVPotion(myDungeon);
                refresh();
            });
        if(hero.getMyHealingPotions() > 0) {
            myHPotion.setOnAction(event -> {hero.useHealingPotion();
            refresh();
            });
        }
        }
    }

}
