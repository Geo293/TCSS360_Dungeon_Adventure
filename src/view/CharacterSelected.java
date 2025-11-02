package view;
import util.CharacterImageLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;



/**
 * this class displays a character selection screen that will prompted
 * the user to choose what character they want and displays their stats,
 * and let the user name their character.
 *
 * @author Geovani Vasquez
 * @version Oct, 31 2025
 */
public class CharacterSelected extends Scene {
    private Button myBackButton;
    private TextField myCharacterName;
    private GameController myController;
    private Button myThief;
    private Button myPriestess;
    private Button myWarrior;
    private Label myStatsDisplay;
    private Button myConfirmChar;
    private ImageView myCharacterImage;

    /**
     * This is the main method that sets the instance fields
     * and set the design of the character select screen.
     * @param theController tells the window what screen to display.
     */
    public CharacterSelected(GameController theController) {
        super(new VBox(),300,250);
        myBackButton = new Button("Back to main menu");
        myStatsDisplay = new Label("Select a Character");
        myController = theController;
        myCharacterName = new TextField();
        myCharacterName.setVisible(false);
        myCharacterImage = new ImageView();
        myCharacterImage.setVisible(false);
        myCharacterImage.setFitHeight(100);
        myCharacterImage.setFitWidth(100);
        myCharacterImage.setPreserveRatio(true);


        VBox characters = createCharacterButtons();
        HBox characterWStats = showStats(characters);
        HBox bottomSec = bacKToMenu();
        ((VBox)this.getRoot()).getChildren().addAll(characterWStats, bottomSec);

    }

    /**
     * This displays the options for characters such as warrior priestess
     * and thief then prompts the user to confirm their option.
     * @return the window that contains the characters.
     */
    public VBox createCharacterButtons(){
        myWarrior = new Button("Warrior");
        myPriestess = new Button("Priestess");
        myThief = new Button("Thief");
        myWarrior.setOnAction(e -> {
            showStats("Warrior");
            myCharacterImage.setVisible(true);
            myConfirmChar.setVisible(true);
            myConfirmChar.requestFocus();

            });
        myPriestess.setOnAction(e -> {
            showStats("Priestess");
            myCharacterImage.setVisible(true);
            myConfirmChar.setVisible(true);
            myConfirmChar.requestFocus();
            });
        myThief.setOnAction(e -> {
            showStats("Thief");
            myCharacterImage.setVisible(true);
            myConfirmChar.setVisible(true);
            myConfirmChar.requestFocus();
        });
        VBox characterButtons = new VBox();
        characterButtons.getChildren().addAll(myWarrior, myPriestess, myThief);
        return characterButtons;

    }

    /**
     * This sets the options to confirm a character or return to the main
     * menu and when the character is selected you are then allowed to name it.
     *
     * @return the bottom window.
     */
    public HBox bacKToMenu(){
        HBox bac = new HBox();
        myConfirmChar = new Button("confirm");
        myConfirmChar.setVisible(false);
        myConfirmChar.setOnAction(e -> {
            myCharacterName.setVisible(true);
            myCharacterName.requestFocus();
        });
        myBackButton.setOnAction(e -> {
            myController.goBackToStart();
            myStatsDisplay.setText("Select a Character");
            myCharacterName.setVisible(false);
            myCharacterName.clear();
            myConfirmChar.setVisible(false);
            myCharacterImage.setVisible(false);
        });
        bac.getChildren().addAll(myBackButton, myConfirmChar, myCharacterName);
        return bac;
    }

    /**
     * This sets u[ the character buttons and their stats to be side by side
     * @param theCharButton the display for the character buttons
     * @return the character stat display
     */
    public HBox showStats(VBox theCharButton){
        HBox statsDisplay = new HBox();
        statsDisplay.getChildren().addAll(theCharButton, myStatsDisplay,myCharacterImage);
        return statsDisplay;


    }

    /**
     * This is the fromate for all the stats so when showstats is called
     * it displays the corresponding stat of the character being called.
     * @param theName The character being choosen
     */
    public void showStats(String theName) {
        String stats = "";
        String imagePerson = CharacterImageLoader.getImage(theName);
        if(imagePerson != null){
            Image image = new Image(getClass().getResourceAsStream(imagePerson));
            myCharacterImage.setImage(image);
        }

        switch(theName) {
            case "Warrior":
                stats = "Hit Points: 125\n" +
                        "Attack Speed: 4\n" +
                        "Chance to Hit: 80%\n" +
                        "Min Damage: 35\n" +
                        "Max Damage: 60\n" +
                        "Block Chance: 20%\n" +
                        "Special: Crushing Blow";
                break;
            case "Priestess":
                stats = "Hit Points: 75\n" +
                        "Attack Speed: 5\n" +
                        "Chance to Hit: 70%\n" +
                        "Min Damage: 25\n" +
                        "Max Damage: 45\n" +
                        "Block Chance: 30%\n" +
                        "Special: HEAL";
                break;
            case "Thief":
                stats = "Hit Points: 75\n" +
                        "Attack Speed: 6\n" +
                        "Chance to Hit: 80%\n" +
                        "Min Damage: 20\n" +
                        "Max Damage: 40\n" +
                        "Block Chance: 40%\n" +
                        "Special: surprise attack";
                break;
        }

        myStatsDisplay.setText(stats);

    }

}

