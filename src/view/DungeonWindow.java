package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import model.Dungeon;
import model.Hero;
import model.Room;
import util.CharacterImageLoader;

/**
 * This method displays the game and the dungeon and lets the user
 * interact with the game and allows them progress with the dungeon.
 *
 * @author Geovani Vasquez
 * @version 11/28/2025
 */
public class DungeonWindow extends Scene {
    /**
     * This is the door image
     */
    private static final Image DOOR = new Image("/images/Area/Door.png");
    /**
     * This is the west wall and east wall that is displayed on the map
     */
    private static final Image WALLW = new Image("/images/Area/WestW.png");
    /**
     * This is the north and south wall image that is being displayed on the map.
     */
    private static final Image WALLN = new Image("/images/Area/NorthW.png");
    /**
     * This is the instructions for how to play the game and what to press.
     */
    private final Label myGameAction = new Label("""
                W/↑ North
            A/← West  East →/D
                S/↓ South    Press G for Guide and press M for inventory""");
    /**
     * This is the mini map that is displayed on the bottom
     */
    private final Label myDungeonDisplay;
    /**
     * This is the current hero that the user made
     */
    private final Hero myHero;
    /**
     * This is the current dungeon the hero is going through
     */
    private final Dungeon myDungeon;
    /**
     * This is the game controller that switches the scenes
     */
    private final GameController myGameController;
    /**
     * This is the pillar image
     */
    private final ImageView myPillarA;
    /**
     * This is the pillar image
     */
    private final ImageView myPillarE;
    /**
     * This is the pillar image
     */
    private final ImageView myPillarI;
    /**
     * This is the pillar image
     */
    private final ImageView myPillarP;
    /**
     * This is the west wall
     */
    private final ImageView myWestW;
    /**
     * This is the east wall
     */
    private final ImageView myEastW;
    /**
     * This is the south wall
     */
    private final ImageView mySouthW;
    /**
     * This is the NorthW
     */
    private final ImageView myNorthW;
    /**
     * This is the west door
     */
    private final ImageView myDoorW;
    /**
     * This is the east door
     */
    private final ImageView myDoorE;
    /**
     * This is the north door
     */
    private final ImageView myDoorN;
    /**
     * This is the south door
     */
    private final ImageView myDoorS;
    /**
     * This is the floor of the dungeon
     */
    private final ImageView myFloor;
    /**
     * This is the hero being displayed on the screen
     */
    private final ImageView myHeroImage;
    /**
     * This is the chest that is displays when there are items
     */
    private final ImageView myChest;
    /**
     * this is the heros health
     */
    private final Label myHeroStats;
    /**
     * This is the pitfall the hero can fall into
     */
    private final ImageView myPitFall;
    /**
     * This is the current room
     */
    private Room myCurrentRoom;



    /**
     * This is the constructor that sets all the variables and then displays the
     * dungeon on the screen
     * @param theController this is the controller that switches the scenes
     * @param theDungeon this is the current dungeon being played
     * @param theHero this is the current hero that the user chose
     * @param theCharacterName this is the type of character that the user chose
     */
    public DungeonWindow(GameController theController, Dungeon theDungeon, Hero theHero, String theCharacterName) {
        super(new BorderPane());
        if(theController == null || theDungeon == null || theHero == null
                || theCharacterName == null || theCharacterName.trim().isEmpty()) {
            throw new IllegalArgumentException("the parameters cannot be null or empty");
        }
        myDungeon = theDungeon;
        myGameController = theController;
        myCurrentRoom = myDungeon.getCurrentRoom();
        myHero = theHero;
        myHeroImage = new ImageView(new Image(CharacterImageLoader.getImageChar(theCharacterName)));
        myDungeonDisplay =  new Label(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),0));
        myChest = new ImageView(new Image("/images/Items/chest.png"));
        myHeroStats = new Label();
        myPillarA = new ImageView();
        myPillarE = new ImageView();
        myPillarI = new ImageView();
        myPillarP = new ImageView();
        myWestW = new ImageView(WALLW);
        myEastW = new ImageView(WALLW);
        mySouthW = new ImageView(WALLN);
        myNorthW = new ImageView(WALLN);
        myDoorW = new ImageView(DOOR);
        myDoorE = new ImageView(DOOR);
        myDoorN = new ImageView(DOOR);
        myDoorS = new ImageView(DOOR);
        myFloor = new ImageView(new Image("/images/Area/FloorRight.png"));
        myPitFall = new ImageView(new Image("/images/Area/pitfall(1).png"));;

        HBox bottomBox = bottomPlane(myDungeon, 0);
        HBox topBox = topPlane();
        BorderPane center = centerWindow();
        setUpKeyListeners();
        BorderPane root = (BorderPane)this.getRoot();

        root.setBottom(bottomBox);
        root.setTop(topBox);
        root.setCenter(center);
        root.setStyle("-fx-background-color: #F5DEB3");
        displayRoomContents();
        hide();
    }

    /**
     * This displays the bottom of the screen which has the mini map and
     * the instructions on how to play
     *
     * @param theDungeon the current dungeon
     * @param theRad the radius of the mini map
     * @return returns a hdbox with all the contents of the bottom screen
     */
    public HBox bottomPlane(Dungeon theDungeon, int theRad){
        myDungeonDisplay.setText(theDungeon.getVisableArea(theDungeon.getMyHeroX(),theDungeon.getMyHeroY(),theRad));
        HBox bottomPlane = new HBox(20);
        bottomPlane.setAlignment(javafx.geometry.Pos.CENTER);
        myDungeonDisplay.setStyle("-fx-font-size: 20px;");
        myGameAction.setStyle("-fx-font-size: 20px;");
        bottomPlane.getChildren().addAll(myDungeonDisplay , myGameAction);
        return bottomPlane;

    }

    /**
     * This updates the size of the mini map so the user can see it on the screen
     * @param theRadius he radius of the minimap
     */
    public void updateDisplayRadius(int theRadius){
        myDungeonDisplay.setText(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),theRadius));
        int fontSize = theRadius > 0 ? 12 : 20;
        myDungeonDisplay.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-family: 'Courier New', monospace;");
    }

    /**
     * This is what is displayed at the top of the screen which includes the
     * pillars and the health of the hero
     * @return a HBox with all the contents of the top of  the screen
     */
    public HBox topPlane(){
        HBox topPlane = new HBox();
        topPlane.setAlignment(javafx.geometry.Pos.CENTER);
        myHeroStats.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        topPlane.getChildren().addAll(myHeroStats,myPillarA, myPillarE, myPillarI, myPillarP);
        return topPlane;


    }

    /**
     * This is the current room being displayed in the game
     * it contains the walls, doors, and the floor
     * @return a BorderPane that has all the contents of the dungeon
     */
    public BorderPane centerWindow(){
        StackPane westStack = west();
        StackPane eastStack = east();
        StackPane northStack = north();
        StackPane southStack = south();
        StackPane middleStack = middle();
        BorderPane centerWindow = new BorderPane();
        centerWindow.setLeft(westStack);
        centerWindow.setRight(eastStack);
        centerWindow.setTop(northStack);
        centerWindow.setBottom(southStack);

        centerWindow.setCenter(middleStack);

        return centerWindow;

    }

    /**
     * This displays the floor and the hero and if there are items then
     * a chest is displayed
     * @return returns the center of the dungeon as a StackPane
     */
    public StackPane middle(){
        StackPane middle = new StackPane();
        myFloor.setPreserveRatio(false);
        myFloor.fitWidthProperty().bind(middle.widthProperty());
        myFloor.fitHeightProperty().bind(middle.heightProperty());
        myPitFall.fitWidthProperty().bind(middle.widthProperty());
        myPitFall.fitHeightProperty().bind(middle.heightProperty());
        myChest.setFitWidth(80);
        myChest.setFitHeight(120);
        myHeroImage.setFitWidth(80);
        myHeroImage.setFitHeight(120);
        myHeroImage.setTranslateX(-50);
        middle.getChildren().addAll(myFloor,myChest,myHeroImage,myPitFall);
        return middle;

    }

    /**
     * This is the west wall and displays a wall and if there
     * is a door then displays an open door
     * @return the west part of the dungeon
     */
    public StackPane west(){
        StackPane westStack = new StackPane();
        myWestW.setPreserveRatio(false);
        myWestW.fitWidthProperty().bind(westStack.widthProperty());
        myWestW.fitHeightProperty().bind(westStack.heightProperty());
        myDoorW.setFitWidth(80);
        myDoorW.setFitHeight(120);
        westStack.getChildren().addAll(myWestW, myDoorW);
        return westStack;
    }

    /**
     * This is  the east  wall and displays a wall and if there
     * is a door then displays an open door
     * @return the east part of the dungeon
     */
    public StackPane east(){
        StackPane eastStack = new StackPane();
        myEastW.setPreserveRatio(false);
        myEastW.fitWidthProperty().bind(eastStack.widthProperty());
        myEastW.fitHeightProperty().bind(eastStack.heightProperty());
        myDoorE.setFitWidth(80);
        myDoorE.setFitHeight(120);
        eastStack.getChildren().addAll(myEastW, myDoorE);
        return eastStack;
    }

    /**
     * This is the north and displays a wall and if there
     * is a door then displays an open door
     * @return the north part of the dungeon
     */
   public StackPane north(){
        StackPane northStack = new StackPane();
        myNorthW.setPreserveRatio(false);
        myNorthW.fitWidthProperty().bind(northStack.widthProperty());
        myNorthW.fitHeightProperty().bind(northStack.heightProperty());
        myDoorN.setFitWidth(80);
        myDoorN.setFitHeight(120);
        northStack.getChildren().addAll(myNorthW, myDoorN);
        return northStack;
    }

    /**
     * This is the south and displays a wall and if there
     * is a door then displays an open door
     * @return the south part of the dungeon
     */
    public StackPane south(){
        StackPane southStack = new StackPane();
        mySouthW.setPreserveRatio(false);
        mySouthW.fitWidthProperty().bind(southStack.widthProperty());
        mySouthW.fitHeightProperty().bind(southStack.heightProperty());
        myDoorS.setFitWidth(80);
        myDoorS.setFitHeight(120);
        southStack.getChildren().addAll(mySouthW, myDoorS);
        return southStack;
    }

    /**
     * This method preforms actions when certain buttons are pressed
     * such as wasd which moves the character or g which opens the guide
     * and escape which opens the pause menu
     */
    public void setUpKeyListeners(){
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                case UP:
                    handleMove("N");
                    break;
                case S:
                case DOWN:
                    handleMove("S");
                    break;
                case D:
                case RIGHT:
                    handleMove("E");
                    break;
                case A:
                case LEFT:
                    handleMove("W");
                    break;
                case ESCAPE:
                    myGameController.pauseMenu(myDungeon,myHero);
                    break;
                case M:
                    myGameController. invetoryScreen( myHero, myDungeon);
                    break;
                case G:
                    myGameController.gameGuide(myHero);
            }
        });
    }

    /**
     * This handles what happens when a button is pressed and send it to the controller
     * to preform the action.
     * @param theMove is what direction the user is going
     */
    public void handleMove(String theMove){
        if(myDungeon.moveHero(theMove)){
            myCurrentRoom = myDungeon.getCurrentRoom();
            updateDisplay();
            displayRoomContents();
            myGameController.processRoomEvents();
            checkForPillars();
        }
    }

    /**
     * This updates the mini map
     */
    public void updateDisplay(){
        myDungeonDisplay.setText(myDungeon.getVisableArea(myDungeon.getMyHeroX(), myDungeon.getMyHeroY(), 0));
    }

    /**
     * This checks for the pillars that have been picked up then displays them on the
     * top of the screen for the user to keep track of
     */
    public void checkForPillars() {
        String pillar = myCurrentRoom.getMyPillar();
        if (pillar != null) {

            switch (pillar.toUpperCase()) {
                case "A":
                    myPillarA.setImage(new Image(getClass().getResourceAsStream("/images/pillars/Abstraction.png")));
                    myPillarA.setVisible(true);
                    break;
                case "E":
                    myPillarE.setImage(new Image(getClass().getResourceAsStream("/images/pillars/Encapsulation.png")));
                    myPillarE.setVisible(true);
                    break;
                case "I":
                    myPillarI.setImage(new Image(getClass().getResourceAsStream("/images/pillars/Inheritance.png")));
                    myPillarI.setVisible(true);
                    break;
                case "P":
                    myPillarP.setImage(new Image(getClass().getResourceAsStream("/images/pillars/Polymorphism.png")));
                    myPillarP.setVisible(true);
                    break;
            }

        }
    }

    /**
     * this refresh the screen whenever the user moves
     */
    public void refresh(){
        updateDisplay();
        updateHeroStats();
        displayRoomContents();
        checkForPillars();
    }

    /**
     * updates the heros health everytime the hero healths and or
     * takes damage
     */
    public void updateHeroStats(){
        myHeroStats.setText(String.format("HP :%d",
                myHero.getMyHitPoints()));
    }

    /**
     * This method hides all the pillars when the user doesn't have any pillars at the
     * start of the game
     */
    public void hide(){
        myPillarA.setVisible(false);
        myPillarA.setFitWidth(60);
        myPillarA.setFitHeight(60);
        myPillarE.setVisible(false);
        myPillarE.setFitWidth(60);
        myPillarE.setFitHeight(60);
        myPillarI.setVisible(false);
        myPillarI.setFitWidth(60);
        myPillarI.setFitHeight(60);
        myPillarP.setVisible(false);
        myPillarP.setFitWidth(60);
        myPillarP.setFitHeight(60);
    }

    /**
     * This displays the doors and chest if there are any in the room
     * and sets the pitfall there if there are any in the
     * current room
     */
    public void displayRoomContents(){
        myDoorW.setVisible(myCurrentRoom.hasWestDoor());
        myDoorE.setVisible(myCurrentRoom.hasEastDoor());
        myDoorN.setVisible(myCurrentRoom.hasNorthDoor());
        myDoorS.setVisible(myCurrentRoom.hasSouthDoor());
        myChest.setVisible(myCurrentRoom.hasVisionPotion() || myCurrentRoom.hasHealingPotion()
                || myCurrentRoom.hasMultipleItems());
        myPitFall.setVisible(myCurrentRoom.hasPit());
        myHeroImage.setVisible(!myCurrentRoom.hasPit());
    }





}
