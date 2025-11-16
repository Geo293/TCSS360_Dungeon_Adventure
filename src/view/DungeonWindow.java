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



public class DungeonWindow extends Scene {
    private static final Image DOOR = new Image("/images/Area/Door.png");
    private static final Image WALLW = new Image("/images/Area/WestW.png");
    private static final Image WALLN = new Image("/images/Area/NorthW.png");
    private Label myGameAction = new Label( "    W/↑ North\n" +
            "A/← West  East →/D\n" +
            "    S/↓ South");
    private Label myDungeonDisplay;
    private Hero myHero;
    private Dungeon myDungeon;
    private GameController myGameController;
    private ImageView myPillarA;
    private ImageView myPillarE;
    private ImageView myPillarI;
    private ImageView myPillarP;
    private ImageView myWestW;
    private ImageView myEastW;
    private ImageView mySouthW;
    private ImageView myNorthW;
    private ImageView myDoorW;
    private ImageView myDoorE;
    private ImageView myDoorN;
    private ImageView myDoorS;
    private ImageView myFloor;
    private Room myCurrentRoom;


    public DungeonWindow(GameController theController, Dungeon theDungeon, Hero theHero) {
        super(new BorderPane());
        myDungeon = theDungeon;
        myGameController = theController;
        myCurrentRoom = myDungeon.getCurrentRoom();
        myHero = theHero;
        myDungeonDisplay =  new Label(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),0));
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

        HBox bottomBox = bottomPlane();
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
    public HBox bottomPlane(){
        HBox bottomPlane = new HBox();
        bottomPlane.setAlignment(javafx.geometry.Pos.CENTER);
        myDungeonDisplay.setStyle("-fx-font-size: 20px;");
        myGameAction.setStyle("-fx-font-size: 20px;");
        bottomPlane.getChildren().addAll(myDungeonDisplay, myGameAction);
        return bottomPlane;

    }
    public HBox topPlane(){
        HBox topPlane = new HBox();
        topPlane.setAlignment(javafx.geometry.Pos.CENTER);
        topPlane.getChildren().addAll(myPillarA, myPillarE, myPillarI, myPillarP);
        return topPlane;


    }
    public BorderPane centerWindow(){
        StackPane westStack = west();
        StackPane eastStack = east();
        StackPane northStack = north();
        StackPane southStack = south();
        BorderPane centerWindow = new BorderPane();
        centerWindow.setLeft(westStack);
        centerWindow.setRight(eastStack);
        centerWindow.setTop(northStack);
        centerWindow.setBottom(southStack);

        centerWindow.setCenter(myFloor);

        return centerWindow;

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
            }
        });
    }

    public void handleMove(String theMove){
        if(myDungeon.moveHero(theMove)){
            myCurrentRoom = myDungeon.getCurrentRoom();
            updateDisplay();
            myGameController.processRoomEvents();
            displayDoor();
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
    }




}
