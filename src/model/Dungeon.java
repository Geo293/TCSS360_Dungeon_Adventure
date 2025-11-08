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
    private static final int DEFAULT_WIDTH = 10;
    private static final int DEFAULT_HEIGHT = 10;
    private static final String[] PILLAR_TYPES = {"A", "E", "I", "P"}; // Abstraction, Encapsulation, Inheritance, Polymorphism
    private static final double EXTRA_DOOR_CHANCE = 0.25; // 25% chance to add extra doors

    // Instance variables
    private final Room[][] maze;
    private final int width;
    private final int height;
    private final Random random;
    private int entranceX;
    private int entranceY;
    private int exitX;
    private int exitY;
    private int heroX;
    private int heroY;

    /**
     * default constructor for the Dungeon.
     */
    public Dungeon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructor for dungeon of custom dimensions.
     *
     * @param width  the width of the dungeon
     * @param height the height of the dungeon
     */
    public Dungeon(int width, int height) {
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
        addExtraDoors();
        generateRoomContents();
        placeExit();
        placeEntrance();
        placePillars();
    }

    /**
     * We are using  the Depth-First-Search (DFS) algorithm to generate a maze for the Dungeon
     * Places entrance, exit, and pillars down as well.
     */
    private void generateMaze() {
        boolean[][] visited = new boolean[width][height];
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
                int[] neighbor = neighbors[random.nextInt(neighbors.length)];
                int nx = neighbor[0];
                int ny = neighbor[1];

                // Remove wall between current and neighbor
                if (nx == x + 1) { // East
                    maze[x][y].setEastDoor(true);
                    maze[nx][ny].setWestDoor(true);
                } else if (nx == x - 1) { // West
                    maze[x][y].setWestDoor(true);
                    maze[nx][ny].setEastDoor(true);
                } else if (ny == y + 1) { // South
                    maze[x][y].setSouthDoor(true);
                    maze[nx][ny].setNorthDoor(true);
                } else if (ny == y - 1) { // North
                    maze[x][y].setNorthDoor(true);
                    maze[nx][ny].setSouthDoor(true);
                }

                visited[nx][ny] = true;
                stack.push(new int[]{nx, ny});
            } else {
                stack.pop();
            }

        }
    }

    private int[][] getUnvisitedNeighbors(int x, int y, boolean[][] visited) {
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // E, W, S, N
        int count = 0;

        // Count all valid neighbors
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny]) {
                count++;
            }
        }

        int[][] neighbors = new int[count][2];
        int index = 0;

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visited[nx][ny]) {
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
        entranceX = 0;
        entranceY = 0;
        maze[entranceX][entranceY].setEntrance(true);
        heroX = entranceX;
        heroY = entranceY;
    }

    /**
     * Places the exit in the bottom left corner of the dungeon.
     */
    private void placeExit() {
        exitX = width - 1;
        exitY = height - 1;
        maze[exitX][exitY].setExit(true);
    }

    /**
     * Places all the pillars in random locations in the dungeon.
     */
    private void placePillars() {
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
    private void generateRoomContents() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!maze[x][y].isEntrance() && !maze[x][y].isExit()) {
                    maze[x][y].generateContents();
                }
            }
        }
    }

    /**
     * adding extra doors so that the dungeon feels less like a maze.
     * make sure the no doors are on the edge of the dungeon (out of bounds).
     */
    private void addExtraDoors() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // skip if random chance doesn't hit
                if (random.nextDouble() >= EXTRA_DOOR_CHANCE) {
                    continue;
                }

                // try to add extra doors in random directions
                int direction = random.nextInt(4); // 0: North, 1: South, 2: East, 3: West

                switch (direction) {
                    case 0: // north
                        if (y > 0 && !maze[x][y].hasNorthDoor()) { // not on top border and no door exists
                            maze[x][y].setNorthDoor(true);
                            maze[x][y-1].setSouthDoor(true);
                        }
                        break;
                    case 1: // south
                        if (y < height - 1 && !maze[x][y].hasSouthDoor()) { // not on bottom border and no door exists
                            maze[x][y].setSouthDoor(true);
                            maze[x][y+1].setNorthDoor(true);
                        }
                        break;
                    case 2: // east
                        if (x < width - 1 && !maze[x][y].hasEastDoor()) { // not on right border and no door exists
                            maze[x][y].setEastDoor(true);
                            maze[x+1][y].setWestDoor(true);
                        }
                        break;
                    case 3: // west
                        if (x > 0 && !maze[x][y].hasWestDoor()) { // not on left border and no door exists
                            maze[x][y].setWestDoor(true);
                            maze[x-1][y].setEastDoor(true);
                        }
                        break;
                }
            }
        }
    }

    /**
     * Moves the hero in the specified direction
     */
    public boolean moveHero(String direction) {
        int newX = heroX;
        int newY = heroY;

        switch (direction.toUpperCase()) {
            case "N":
                if (maze[heroX][heroY].hasNorthDoor()) newY--;
                break;
            case "S":
                if (maze[heroX][heroY].hasSouthDoor()) newY++;
                break;
            case "E":
                if (maze[heroX][heroY].hasEastDoor()) newX++;
                break;
            case "W":
                if (maze[heroX][heroY].hasWestDoor()) newX--;
                break;
            default:
                return false;
        }

        // Check if move is valid
        if (newX >= 0 && newX < width && newY >= 0 && newY < height &&
                !(newX == heroX && newY == heroY)) {
            heroX = newX;
            heroY = newY;
            return true;
        }
        return false;
    }

    /**
     * Gets the current room where the hero is located
     */
    public Room getCurrentRoom() {
        return maze[heroX][heroY];
    }

    //Other getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
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
        for (int y = 0; y < height; y++) {
            // three lines for each row of rooms
            StringBuilder line1 = new StringBuilder();
            StringBuilder line2 = new StringBuilder();
            StringBuilder line3 = new StringBuilder();

            // go through each column in this row (x-axis)
            for (int x = 0; x < width; x++) {
                // Get the room's string representation and split into lines
                String roomStr = maze[x][y].toString();
                String[] lines = roomStr.split("\n");

                // Append each line (removing the last character if not the last room)
                if (x < width - 1) {
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
