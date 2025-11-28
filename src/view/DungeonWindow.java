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


public class DungeonWindow extends Scene {
    private static final Image DOOR = new Image("/images/Area/Door.png");
    private static final Image WALLW = new Image("/images/Area/WestW.png");
    private static final Image WALLN = new Image("/images/Area/NorthW.png");
    private final Label myGameAction = new Label( "    W/↑ North\n" +
            "A/← West  East →/D\n" +
            "    S/↓ South    Press G for Guide" );
    private Label myDungeonDisplay;
    private Hero myHero;
    private Dungeon myDungeon;
    private GameController myGameController;
    private final ImageView myPillarA;
    private final ImageView myPillarE;
    private final ImageView myPillarI;
    private final ImageView myPillarP;
    private final ImageView myWestW;
    private final ImageView myEastW;
    private final ImageView mySouthW;
    private final ImageView myNorthW;
    private final ImageView myDoorW;
    private final ImageView myDoorE;
    private final ImageView myDoorN;
    private final ImageView myDoorS;
    private final ImageView myFloor;
    private final ImageView myHeroImage;
    private final ImageView myChest;
    private Room myCurrentRoom;
    private final Label myHeroStats;



    public DungeonWindow(GameController theController, Dungeon theDungeon, Hero theHero, String theCharacterName) {
        super(new BorderPane());
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
        myFloor = new ImageView(new Image("/images/Area/NewFloor.png"));

        HBox bottomBox = bottomPlane(myDungeon, 0);
        HBox topBox = topPlane();
        BorderPane center = centerWindow();
        setUpKeyListeners();
        BorderPane root = (BorderPane)this.getRoot();

        root.setBottom(bottomBox);
        root.setTop(topBox);
        root.setCenter(center);
        root.setStyle("-fx-background-color: #F5DEB3");
        displayDoor();
        hide();
    }
    public HBox bottomPlane(Dungeon theDungeon, int theRad){
        myDungeonDisplay.setText(theDungeon.getVisableArea(theDungeon.getMyHeroX(),theDungeon.getMyHeroY(),theRad));
        HBox bottomPlane = new HBox(20);
        bottomPlane.setAlignment(javafx.geometry.Pos.CENTER);
        myDungeonDisplay.setStyle("-fx-font-size: 20px;");
        myGameAction.setStyle("-fx-font-size: 20px;");
        bottomPlane.getChildren().addAll(myDungeonDisplay , myGameAction);
        return bottomPlane;

    }
    public void updateDisplayRadius(int theRadius){
        myDungeonDisplay.setText(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),theRadius));
        int fontSize = theRadius > 0 ? 12 : 20;
        myDungeonDisplay.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-family: 'Courier New', monospace;");
    }
    public HBox topPlane(){
        HBox topPlane = new HBox();
        topPlane.setAlignment(javafx.geometry.Pos.CENTER);
        myHeroStats.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        topPlane.getChildren().addAll(myHeroStats,myPillarA, myPillarE, myPillarI, myPillarP);
        return topPlane;


    }
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
    public StackPane middle(){
        StackPane middle = new StackPane();
        myFloor.setPreserveRatio(false);
        myChest.setFitWidth(80);
        myChest.setFitHeight(120);
        myHeroImage.setFitWidth(80);
        myHeroImage.setFitHeight(120);
        myHeroImage.setTranslateX(-50);
        middle.getChildren().addAll(myFloor,myChest,myHeroImage);
        return middle;

    }
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

    public void handleMove(String theMove){
        if(myDungeon.moveHero(theMove)){
            myCurrentRoom = myDungeon.getCurrentRoom();
            updateDisplay();
            displayDoor();
            myGameController.processRoomEvents();
            checkForPillars();
        }
    }
    public void updateDisplay(){
        myDungeonDisplay.setText(myDungeon.getVisableArea(myDungeon.getMyHeroX(), myDungeon.getMyHeroY(), 0));
    }

    public void checkForPillars() {
        String pillar = myCurrentRoom.getPillar();
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
    public void refresh(){
        updateDisplay();
        updateHeroStats();
        displayDoor();
        checkForPillars();
    }
    public void updateHeroStats(){
        myHeroStats.setText(String.format("HP :%d",
                myHero.getMyHitPoints()));
    }
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

    public void displayDoor(){
        if(myCurrentRoom.hasWestDoor()){
            myDoorW.setVisible(true);
        } else{
            myDoorW.setVisible(false);
        }
        if(myCurrentRoom.hasEastDoor()){
            myDoorE.setVisible(true);
        } else {
            myDoorE.setVisible(false);
        }
        if(myCurrentRoom.hasNorthDoor()){
            myDoorN.setVisible(true);
        } else {
            myDoorN.setVisible(false);
        }
        if(myCurrentRoom.hasSouthDoor()){
            myDoorS.setVisible(true);
        } else {
            myDoorS.setVisible(false);
        }
        if(myCurrentRoom.hasVisionPotion() || myCurrentRoom.hasHealingPotion()
            || myCurrentRoom.hasMultipleItems()){
            myChest.setVisible(true);
        } else {
            myChest.setVisible(false);
        }
    }




}
