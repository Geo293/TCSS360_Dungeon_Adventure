package util;

public class CharacterImageLoader {
    public static String getImageChar(String theImageName) {
        return switch (theImageName) {
            case "Warrior" -> "/images/characters/Warrior.png";
            case "Priestess" -> "/images/characters/Priestess.png";
            case "Thief" -> "/images/characters/Thief.png";
            default -> null;
        };
    }

}
