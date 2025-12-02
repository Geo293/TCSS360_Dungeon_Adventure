package tests;

import model.Dungeon;
import model.Room;

public class DungeonTest {
    public static void main(String[] args) {
        Room room = new Room();
        Dungeon dungeon = new Dungeon();

        System.out.println(room);
        room.setMyPillar("P");
        System.out.println(room);

        System.out.println(dungeon);
    }
}
