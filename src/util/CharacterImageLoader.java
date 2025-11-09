package util;
import util.CharacterImageLoader;
import java.awt.*;

public class CharacterImageLoader {
    public static String getImage(String theImageName) {
        return switch (theImageName) {
            case "Warrior" -> "/images/characters/Warrior.png";
            case "Priestess" -> "/images/characters/Priestess.png";
            case "Thief" -> "/images/characters/Thief.png";
            default -> null;
        };
    }

}
