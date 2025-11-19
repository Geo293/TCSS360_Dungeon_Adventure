package view;
import javafx.geometry.Pos;
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
    /**
     * This is the back to menu button.
     */
    private final Button myBackButton;
    /**
     * This is the text box that the user inputs a name.
     */
    private final TextField myCharacterName;
    /**
     * This is the switches the windows in the game
     */
    private final GameController myController;
    /**
     * the button the sets the character as thief.
     */
    private Button myThief;
    /**
     * the button the sets the character as priestess.
     */
    private Button myPriestess;
    /**
     * the button the sets the character as warrior.
     */
    private Button myWarrior;
    /**
     * This displays all the character stats
     */
    private final Label myStatsDisplay;
    /**
     * This displays what the character looks like
     */
    private final ImageView myCharacterImage;
    /**
     * This is what character the user choose.
     */
    private String myChosenCharacter;
    /**
     * This is the button that starts the game.
     */
    private final Button myStartButton;

    /**
     * This is the main method that sets the instance fields
     * and set the design of the character select screen.
     * @param theController tells the window what screen to display.
     */
    public CharacterSelected(GameController theController) {
        super(new VBox());
        VBox root = (VBox)this.getRoot();
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setSpacing(20);
        root.setFillWidth(true);
        myBackButton = new Button("Back to main menu");
        myStatsDisplay = new Label("Select a Character");
        myController = theController;
        myCharacterName = new TextField();
        myCharacterImage = new ImageView();
        myCharacterImage.setFitHeight(100);
        myCharacterImage.setFitWidth(100);
        myCharacterImage.setPreserveRatio(true);
        myChosenCharacter = "";
        myStartButton = new Button("Start Game");
        myStartButton.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        reset();
        VBox characters = createCharacterButtons();
        HBox characterWStats = showStats(characters);
        HBox bottomSec = bacKToMenu();
        HBox gameStart = startTheGame();
        root.getChildren().addAll(characterWStats, bottomSec, gameStart);
        root.setStyle("-fx-background-color: #F5DEB3");
    }

    /**
     * This displays the options for characters such as warrior priestess
     * and thief then prompts the user to confirm their option.
     * @return the window that contains the characters.
     */
    public VBox createCharacterButtons(){
        myWarrior = new Button("Warrior");
        myWarrior.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myPriestess = new Button("Priestess");
        myPriestess.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myThief = new Button("Thief");
        myThief.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        myWarrior.setOnAction(e -> {
            showStringStats("Warrior");
            myCharacterImage.setVisible(true);
            myCharacterName.setVisible(true);
            myCharacterName.requestFocus();
            myStartButton.setVisible(true);
            myStartButton.requestFocus();
            myChosenCharacter = "Warrior";

            });
        myPriestess.setOnAction(e -> {
            showStringStats("Priestess");
            myCharacterImage.setVisible(true);
            myCharacterName.setVisible(true);
            myCharacterName.requestFocus();
            myStartButton.setVisible(true);
            myStartButton.requestFocus();
            myChosenCharacter = "Priestess";
            });
        myThief.setOnAction(e -> {
            showStringStats("Thief");
            myCharacterImage.setVisible(true);
            myCharacterName.setVisible(true);
            myCharacterName.requestFocus();
            myStartButton.setVisible(true);
            myStartButton.requestFocus();
            myChosenCharacter = "Thief";
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
        bac.setAlignment(javafx.geometry.Pos.CENTER);
        bac.setSpacing(20);
        myBackButton.setOnAction(e -> {
            reset();
        });
        myBackButton.setStyle("-fx-background-color: #80461B; -fx-text-fill: white;");
        bac.getChildren().addAll(myBackButton, myCharacterName);
        return bac;
    }

    /**
     * This method sets the bottom button to be a start game button
     * and then sends a message to the user to write a name if
     * they haven't already.
     * @return
     */
    public HBox startTheGame(){
        HBox gameStart = new HBox();
        gameStart.setAlignment(Pos.CENTER);
        gameStart.setSpacing(20);
        myStartButton.setOnAction(e -> {
            String heroName = myCharacterName.getText().trim();

            if (heroName.isEmpty()) {
                myStatsDisplay.setText("Please enter a name!");
                return;
            }

            myController.startNewGame(myChosenCharacter, heroName);
        });
        gameStart.getChildren().addAll(myStartButton);
        return gameStart;
    }

    /**
     * This sets u[ the character buttons and their stats to be side by side
     * @param theCharButton the display for the character buttons
     * @return the character stat display
     */
    public HBox showStats(VBox theCharButton){
        HBox statsDisplay = new HBox();
        statsDisplay.setAlignment(javafx.geometry.Pos.CENTER);
        statsDisplay.setSpacing(20);
        statsDisplay.getChildren().addAll(theCharButton, myStatsDisplay,myCharacterImage);
        return statsDisplay;

    }

    /**
     * This is the format for all the stats so when shows tats is called
     * it displays the corresponding stat of the character being called.
     * @param theName The character being chosen
     */
    public void showStringStats(String theName) {
        String stats = "";
        String imagePerson = CharacterImageLoader.getImageChar(theName);
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

    /**
     * This resets all the buttons and text that is not seen
     * until the user interacts with the screen.
     */
    public void reset(){
        myController.goBackToStart();
        myStatsDisplay.setText("Select a Character");
        myCharacterName.setVisible(false);
        myCharacterName.clear();
        myCharacterImage.setVisible(false);
        myChosenCharacter = "";
        myStartButton.setVisible(false);
    }

}

