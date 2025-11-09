package view;

import controller.GameController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    public DungeonWindow(GameController theController, Dungeon theDungeon, Hero theHero) {
        super(new BorderPane());
        myDungeon = theDungeon;
        myHero = theHero;
        myGameController = theController;
        myDungeonDisplay =  new Label(myDungeon.getVisableArea(myDungeon.getMyHeroX(),myDungeon.getMyHeroY(),0));
        VBox bottomBox = bottomPlane();
        setUpKeyListeners();
        BorderPane root = (BorderPane)this.getRoot();
        root.setBottom(bottomBox);
    }
    public VBox bottomPlane(){
        VBox bottomPlane = new VBox();

        bottomPlane.getChildren().addAll(myDungeonDisplay, myGameAction);
        return bottomPlane;

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

    public void checkRooms(){
        Room currentRoom = myDungeon.getCurrentRoom();

        if (currentRoom.hasPit()){
            int damage = (int)(Math.random() * 20) + 1;
            myHero.subtractHitPoints(damage);
            // Maybe show a message?
        }
        if (currentRoom.hasVisionPotion()){
          //  myHero.addVisionPotion();
           // currentRoom.setVisionPotion(false);
        }

    }





}
