package model;

import java.util.Random;
import java.util.Stack;

/**
 * This class creates a randomly generated maze for the player to
 * travel through the maze will always have an  entrance, an exit, and
 * the four pillars of OO.
 *
 * @author Geovani Vasquez
 * @author Justin Yee
 * @version Oct, 24 2025
 */
public class Dungeon {
    // Constants
    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 15;
    private static final String[] PILLAR_TYPES = {"A", "E", "I", "P"}; // Abstraction, Encapsulation, Inheritance, Polymorphism

    // Instance variables
    private Room[][] maze;
    private int width;
    private int height;
    private int entranceX;
    private int entranceY;
    private int exitX;
    private int exitY;
    private int heroX;
    private int heroY;
    private Random random;

    /**
     * default constructor for the Dungeon.
     */
    public Dungeon () {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for dungeon of custom dimensions.
     *
     * @param width     the width of the dungeon
     * @param height    the height of the dungeon
     */
    public Dungeon (int width, int height){
        this.width = width;
        this.height = height;
        this.maze = new Room[width][height];
        this.random = new Random();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                maze[i][j] = new Room();
            }
        }

        generateMaze();
        placeExit();
        placeEntrance();
        placePillars();
    }

    /**
     * We are using  the Depth-First-Search (DFS) algorithm to generate a maze for the Dungeon
     * Places entrance, exit, and pillars down as well.
     */
    private void generateMaze(){

    }

    /**
     * Places the entrance at 0,0 and sets the hero x,y to 0,0 as well.
     */
    private void placeEntrance(){
        entranceX = 0;
        entranceY = 0;
        maze[entranceX][entranceY].setEntrance(true);
        heroX = entranceX;
        heroY = entranceY;
    }

    /**
     * Places the exit in the bottom left corner of the dungeon.
     */
    private void placeExit(){
        exitX = width - 1;
        exitY = height - 1;
        maze[exitX][exitY].setExit(true);
    }

    /**
     * Places all the pillars in random locations in the dungeon.
     */
    private void placePillars(){
        for (String pillar : PILLAR_TYPES) {
            int pillarX, pillarY;
            do {
                pillarX = random.nextInt(width);
                pillarY = random.nextInt(height);
            } while ((pillarX == entranceX && pillarY == entranceY) ||
                    (pillarX == exitX && pillarY == exitY) ||
                    maze[pillarX][pillarY].getPillar() != null);

            maze[pillarX][pillarY].setPillar(pillar);
        }
    }

    /**
     * Generates all the contents in the Rooms of the Dungeon.
     */
    private void generateRoomContents(){
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!maze[x][y].isEntrance() && !maze[x][y].isExit()) {
                    maze[x][y].generateContents();
                }
            }
        }
    }
}
