package view;

import controller.GameController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;
import model.Hero;

/**
 * This method shows the user what everything on the mini map
 * means
 */
public class GameGuide extends Scene {
    /**
     * This is what will be displayed as the guide
     */
    private static final Label myGuide = new Label("""
            M = multiple items
             \
            X = Pit\s
            i = Entrance\s
             O = exit\s
            V = Vision Potion
            H = Healing Potion\s
             A, E, I, P = Pillars""");
    /**
     * This is the hero object
     */
    private final Hero myHero;
    /**
     * This is the game controller object
     */
    private final GameController myController;

    /**
     * This is the constructor that is used to display everything
     * into a vbox
     * @param theController the controller object received from the controller
     * @param theHero the hero object received from the controller
     */
    public GameGuide(GameController theController, Hero theHero) {
        super(new VBox());
        if (theHero == null || theController == null) {
            throw new IllegalArgumentException("theHero and theController are null!");
        }
        VBox root = (VBox) getRoot();
        root.setAlignment(Pos.CENTER);
        myController = theController;
        myHero = theHero;
        myGuide.setStyle("-fx-font-size: 20px;");
        setUpKeyListeners();
        root.setStyle("-fx-background-color: #F5DEB3");
        root.getChildren().add(myGuide);

    }

    /**
     * This method sets up key listeners to tell the controller
     * to close the guide.
     */
    public void setUpKeyListeners(){
        this.setOnKeyPressed(event -> {
           switch (event.getCode()) {
               case ESCAPE:
                   myController.backToDungeon(myHero);
                   break;
               case G:
                   myController.backToDungeon(myHero);
                   break;
           }
        });
    }
}
