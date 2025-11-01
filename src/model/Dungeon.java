package model;

import java.util.Random;
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
        this.maze = new Room[height][width];
        this.random = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                maze[i][j] = new Room();
            }
        }

        generateMaze();
    }


    public void generateMaze(){

    }


}

