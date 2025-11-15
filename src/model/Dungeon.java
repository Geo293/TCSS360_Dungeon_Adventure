package model;

import controller.MonsterFactory;
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
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;
    private static final String[] PILLAR_TYPES = {"A", "E", "I", "P"}; // Abstraction, Encapsulation, Inheritance, Polymorphism
    private static final double EXTRA_DOOR_CHANCE = 0.25; // 25% chance to add extra doors
    private static final double BASE_MONSTER_SPAWN_CHANCE = 0.25; // 25% base chance for monster
    private static final double PILLAR_MONSTER_SPAWN_CHANCE = 0.8; // 80% chance near pillars
    private static final double EXIT_MONSTER_SPAWN_CHANCE = 0.6; // 60% chance near exit

    // Instance variables

    private Room[][] myMaze;
    private int myWidth;
    private int myHeight;
    private int myEntranceX;
    private int myEntranceY;
    private int myExitX;
    private int myExitY;
    private int myHeroX;
    private int myHeroY;
    private Random myRandom;



    /**
     * default constructor for the Dungeon.
     */
    public Dungeon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for dungeon of custom dimensions.
     *
     * @param theWidth  the width of the dungeon
     * @param theHeight the height of the dungeon
     */
    public Dungeon(int theWidth, int theHeight) {
        myWidth = theWidth;
        myHeight = theHeight;
        myMaze = new Room[theWidth][theHeight];
        myRandom = new Random();

        for (int i = 0; i < theWidth; i++) {
            for (int j = 0; j < theHeight; j++) {
                myMaze[i][j] = new Room();
            }
        }

        generateMaze();
        addExtraDoors();
        generateRoomContents();
        placeExit();
        placeEntrance();
        placePillars();
        placeMonsters();
    }

    /**
     * We are using  the Depth-First-Search (DFS) algorithm to generate a maze for the Dungeon
     * Places entrance, exit, and pillars down as well.
     */
    private void generateMaze() {
        boolean[][] visited = new boolean[myWidth][myHeight];
        Stack<int[]> stack = new Stack<>();

        // Start DFS from top left cell
        stack.push(new int[]{0, 0});
        visited[0][0] = true;

        //starting the DFS
        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            // Get unvisited neighbors
            int[][] neighbors = getUnvisitedNeighbors(x, y, visited);

            if (neighbors.length > 0) {
                // Choose a random neighbor
                int[] neighbor = neighbors[myRandom.nextInt(neighbors.length)];
                int nx = neighbor[0];
                int ny = neighbor[1];

                // Remove wall between current and neighbor
                if (nx == x + 1) { // East
                    myMaze[x][y].setEastDoor(true);
                    myMaze[nx][ny].setWestDoor(true);
                } else if (nx == x - 1) { // West
                    myMaze[x][y].setWestDoor(true);
                    myMaze[nx][ny].setEastDoor(true);
                } else if (ny == y + 1) { // South
                    myMaze[x][y].setSouthDoor(true);
                    myMaze[nx][ny].setNorthDoor(true);
                } else if (ny == y - 1) { // North
                    myMaze[x][y].setNorthDoor(true);
                    myMaze[nx][ny].setSouthDoor(true);
                }

                visited[nx][ny] = true;
                stack.push(new int[]{nx, ny});
            } else {
                stack.pop();
            }

        }
    }

    private int[][] getUnvisitedNeighbors(int theX, int theY, boolean[][] theVisited) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // E, W, S, N
        int count = 0;

        // Count all valid neighbors
        for (int[] dir : directions) {
            int nx = theX + dir[0];
            int ny = theY + dir[1];
            if (nx >= 0 && nx < myWidth && ny >= 0 && ny < myHeight && !theVisited[nx][ny]) {
                count++;
            }
        }

        int[][] neighbors = new int[count][2];
        int index = 0;

        for (int[] dir : directions) {
            int nx = theX + dir[0];
            int ny = theY + dir[1];
            if (nx >= 0 && nx < myWidth && ny >= 0 && ny < myHeight && !theVisited[nx][ny]) {
                neighbors[index][0] = nx;
                neighbors[index][1] = ny;
                index++;
            }
        }

        return neighbors;
    }

    /**
     * Places the entrance at 0,0 and sets the hero x,y to 0,0 as well.
     */
    private void placeEntrance() {
        myEntranceX = 0;
        myEntranceY = 0;
        myMaze[myEntranceX][myEntranceY].setEntrance(true);
        myHeroX = myEntranceX;
        myHeroY = myEntranceY;
    }

    /**
     * Places the exit in the bottom left corner of the dungeon.
     */
    private void placeExit() {
        myExitX = myWidth - 1;
        myExitY = myHeight - 1;
        myMaze[myExitX][myExitY].setExit(true);
    }

    /**
     * Places all the pillars in random locations in the dungeon.
     */
    private void placePillars() {
        for (String pillar : PILLAR_TYPES) {
            int pillarX, pillarY;
            do {
                pillarX = myRandom.nextInt(myWidth);
                pillarY = myRandom.nextInt(myHeight);
            } while ((pillarX == myEntranceX && pillarY == myEntranceY) ||
                    (pillarX == myExitX && pillarY == myExitY) ||
                    myMaze[pillarX][pillarY].getPillar() != null);

            myMaze[pillarX][pillarY].setPillar(pillar);
        }
    }

    /**
     * Generates all the contents in the Rooms of the Dungeon.
     */
    private void generateRoomContents() {
        for (int x = 0; x < myWidth; x++) {
            for (int y = 0; y < myHeight; y++) {
                if (!myMaze[x][y].isEntrance() && !myMaze[x][y].isExit()) {
                    myMaze[x][y].generateContents();
                }
            }
        }
    }

    /**
     * adding extra doors so that the dungeon feels less like a maze.
     * make sure the no doors are on the edge of the dungeon (out of bounds).
     */
    private void addExtraDoors() {
        for (int x = 0; x < myWidth; x++) {
            for (int y = 0; y < myHeight; y++) {
                // skip if random chance doesn't hit
                if (myRandom.nextDouble() >= EXTRA_DOOR_CHANCE) {
                    continue;
                }

                // try to add extra doors in random directions
                int direction = myRandom.nextInt(4); // 0: North, 1: South, 2: East, 3: West

                switch (direction) {
                    case 0: // north
                        if (y > 0 && !myMaze[x][y].hasNorthDoor()) { // not on top border and no door exists
                            myMaze[x][y].setNorthDoor(true);
                            myMaze[x][y-1].setSouthDoor(true);
                        }
                        break;
                    case 1: // south
                        if (y < myHeight - 1 && !myMaze[x][y].hasSouthDoor()) { // not on bottom border and no door exists
                            myMaze[x][y].setSouthDoor(true);
                            myMaze[x][y+1].setNorthDoor(true);
                        }
                        break;
                    case 2: // east
                        if (x < myWidth - 1 && !myMaze[x][y].hasEastDoor()) { // not on right border and no door exists
                            myMaze[x][y].setEastDoor(true);
                            myMaze[x+1][y].setWestDoor(true);
                        }
                        break;
                    case 3: // west
                        if (x > 0 && !myMaze[x][y].hasWestDoor()) { // not on left border and no door exists
                            myMaze[x][y].setWestDoor(true);
                            myMaze[x-1][y].setEastDoor(true);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Moves the hero in the specified direction
     */
    public boolean moveHero(String theDirection) {
        int newX = myHeroX;
        int newY = myHeroY;

        switch (theDirection.toUpperCase()) {
            case "N":
                if (myMaze[myHeroX][myHeroY].hasNorthDoor()) newY--;
                break;
            case "S":
                if (myMaze[myHeroX][myHeroY].hasSouthDoor()) newY++;
                break;
            case "E":
                if (myMaze[myHeroX][myHeroY].hasEastDoor()) newX++;
                break;
            case "W":
                if (myMaze[myHeroX][myHeroY].hasWestDoor()) newX--;
                break;
            default:
                return false;
        }

        // Check if move is valid
        if (newX >= 0 && newX < myWidth && newY >= 0 && newY < myHeight &&
                !(newX == myHeroX && newY == myHeroY)) {
            myHeroX = newX;
            myHeroY = newY;
            return true;
        }
        return false;
    }

    /**
     * Gets the current room where the hero is located
     */
    public Room getCurrentRoom() {
        return myMaze[myHeroX][myHeroY];
    }

    //Other getters
    public int getMyWidth() {
        return myWidth;
    }

    public int getMyHeight() {
        return myHeight;
    }

    public int getMyHeroX() {
        return myHeroX;
    }

    public int getMyHeroY() {
        return myHeroY;
    }

    public int getMyEntranceX() {
        return myEntranceX;
    }

    public int getMyEntranceY() {
        return myEntranceY;
    }

    public int getMyExitX() {
        return myExitX;
    }

    public int getMyExitY() {
        return myExitY;
    }

    public String getVisableArea(int theCenterX, int theCenterY, int theRadius){
        StringBuilder sb = new StringBuilder();
        int minX = Math.max(0, theCenterX - theRadius);
        int maxX = Math.min(myWidth - 1, theCenterX + theRadius);
        int minY = Math.max(0, theCenterY - theRadius);
        int maxY = Math.min(myHeight - 1, theCenterY + theRadius);

        for (int y = minY; y <= maxY; y++) {
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            StringBuilder line3 = new StringBuilder();

            for (int x = minX; x <= maxX; x++) {
                String roomStr = myMaze[x][y].toString();
                String[] lines = roomStr.split("\n");

                if (x < maxX) {

                    line1.append(lines[0].substring(0, lines[0].length() - 1));
                    line2.append(lines[1].substring(0, lines[1].length() - 1));
                    line3.append(lines[2].substring(0, lines[2].length() - 1));
                } else {

                    line1.append(lines[0]);
                    line2.append(lines[1]);
                    line3.append(lines[2]);
                }
            }

            if (y == minY) {
                sb.append(line1).append("\n");
            }

            sb.append(line2).append("\n");
            sb.append(line3).append("\n");
        }
        return sb.toString();
    }

    /**
     * Places monsters throughout the dungeon with higher probability near pillars and exit.
     */
    private void placeMonsters() {
        //Pre-load all monsters once at the start
        MonsterFactory.loadMonsters();

        for (int x = 0; x < myWidth; x++) {
            for (int y = 0; y < myHeight; y++) {
                // Skip entrance room
                if (x == myEntranceX && y == myEntranceY) {
                    continue;
                }

                // Skip exit room (though you might want monsters near exit)
                if (x == myExitX && y == myExitY) {
                    continue;
                }

                double spawnChance = calculateMonsterSpawnChance(x, y);

                if (myRandom.nextDouble() < spawnChance) {
                    // Now getRandomMonster() uses the pre-loaded list
                    Monster monster = MonsterFactory.getRandomMonster();
                    if (monster != null) {
                        myMaze[x][y].setMonster(monster);
                    }
                }
            }
        }
    }

    /**
     * Calculates the probability of spawning a monster in a given room.
     * Higher chances near pillars and exit.
     *
     * @param theX x-coordinate of the room
     * @param theY y-coordinate of the room
     * @return probability between 0 and 1
     */
    private double calculateMonsterSpawnChance(int theX, int theY) {
        double chance = BASE_MONSTER_SPAWN_CHANCE;

        // Check if this room has a pillar
        if (myMaze[theX][theY].getPillar() != null) {
            chance = PILLAR_MONSTER_SPAWN_CHANCE;
        } else {
            // Increase chance based on proximity to pillars and exit
            for (int x = 0; x < myWidth; x++) {
                for (int y = 0; y < myHeight; y++) {
                    if (myMaze[x][y].getPillar() != null) {
                        double distance = Math.sqrt(Math.pow(x - theX, 2) + Math.pow(y - theY, 2));
                        double pillarBonus = 0.3 / (distance + 1); // Bonus decreases with distance
                        chance += pillarBonus;
                    }
                }
            }

            // Increase chance near exit
            double exitDistance = Math.sqrt(Math.pow(theX - myExitX, 2) + Math.pow(theY - myExitY, 2));
            double exitBonus = 0.2 / (exitDistance + 1);
            chance += exitBonus;
        }

        // Cap the probability at 90%
        return Math.min(chance, 0.9);
    }


    /**
     * Returns a string representation of the entire dungeon.
     *
     * @return string representation of the maze
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // go through each row of rooms (y-axis)
        for (int y = 0; y < myHeight; y++) {
            // three lines for each row of rooms
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            StringBuilder line3 = new StringBuilder();

            // go through each column in this row (x-axis)
            for (int x = 0; x < myWidth; x++) {
                // Get the room's string representation and split into lines
                String roomStr = myMaze[x][y].toString();
                String[] lines = roomStr.split("\n");

                // Append each line (removing the last character if not the last room)
                if (x < myWidth - 1) {
                    // Remove the rightmost character to merge with next room
                    line1.append(lines[0], 0, lines[0].length() - 1);
                    line2.append(lines[1], 0, lines[1].length() - 1);
                    line3.append(lines[2], 0, lines[2].length() - 1);
                } else {
                    // Last room in row, keep full string
                    line1.append(lines[0]);
                    line2.append(lines[1]);
                    line3.append(lines[2]);
                }
            }

            // Only append the first line if it's the first row
            if (y == 0) {
                sb.append(line1).append("\n");
            }

            // Always append middle line
            sb.append(line2).append("\n");

            // Always append bottom line
            sb.append(line3).append("\n");
        }

        return sb.toString();
    }
}
