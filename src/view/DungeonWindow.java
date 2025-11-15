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
    //private ImageView mySouthW;
    //private ImageView myNorthW;
    private ImageView myDoorW;
    private ImageView myDoorE;
    private ImageView myFloor;


    public DungeonWindow(GameController theController, Dungeon theDungeon, Hero theHero) {
        super(new BorderPane());
        myDungeon = theDungeon;
        myHero = theHero;
        myPillarA = new ImageView();
        myPillarE = new ImageView();
        myPillarI = new ImageView();
        myPillarP = new ImageView();
        myWestW = new ImageView(new Image("/images/Area/WestW.png"));
        myEastW = new ImageView(new Image("/images/Area/WestW.png"));
        myDoorW = new ImageView(new Image("/images/Area/Door.png"));
        myDoorE = new ImageView(new Image("/images/Area/Door.png"));
        myFloor = new ImageView(new Image("/images/Area/Floor.png"));
        hide();

        myGameController = theController;
        myDungeonDisplay =  new Label(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),0));
        HBox bottomBox = bottomPlane();
        HBox topBox = topPlane();
        BorderPane center = centerWindow();
        setUpKeyListeners();
        BorderPane root = (BorderPane)this.getRoot();

        root.setBottom(bottomBox);
        root.setTop(topBox);
        root.setCenter(center);
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
        BorderPane centerWindow = new BorderPane();
        centerWindow.setLeft(westStack);
        centerWindow.setRight(eastStack);
        myFloor.setFitWidth(1760);
        myFloor.setFitHeight(880);

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
            }
        });
    }

    public void handleMove(String theMove){
        if(myDungeon.moveHero(theMove)){
            updateDisplay();
            checkRooms();
        }
    }
    public void updateDisplay(){
        myDungeonDisplay.setText(myDungeon.getVisableArea(myDungeon.getMyHeroX(), myDungeon.getMyHeroY(), 0));
    }

    public void checkRooms() {
        Room currentRoom = myDungeon.getCurrentRoom();
        myHero.pickUpItem(currentRoom);
        if (currentRoom.hasPit()) {
            int damage = (int) (Math.random() * 20) + 1;
            myHero.subtractHitPoints(damage);
        }
        if (currentRoom.getMonster() != null) {
            myGameController.startFight(myHero, currentRoom.getMonster());
            currentRoom.removeMonster();
        }
        String pillar = currentRoom.getPillar();
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
            if(currentRoom.hasWestDoor()) {
                myDoorW.setVisible(true);
            }
            if(currentRoom.hasEastDoor()) {
                myDoorE.setVisible(true);
            }

        }
    }

    public void hide(){
        myPillarA.setVisible(false);
        myPillarP.setFitWidth(60);
        myPillarP.setFitHeight(60);
        myPillarE.setVisible(false);
        myPillarE.setFitWidth(60);
        myPillarE.setFitHeight(60);
        myPillarI.setVisible(false);
        myPillarI.setFitWidth(60);
        myPillarI.setFitHeight(60);
        myPillarP.setVisible(false);
        myPillarP.setFitWidth(60);
        myPillarP.setFitHeight(60);
        myDoorW.setVisible(false);
        myDoorE.setVisible(false);
    }





}
