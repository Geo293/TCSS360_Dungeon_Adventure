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

    public static String getMonster(String theImageName) {
       return switch (theImageName) {
            case "Gremlin" -> "/images/monsters/Gremlin.png";
            case "Skeleton" -> "/images/monsters/Skeleton.png";
            case "Ogre" -> "/images/monsters/Ogre(1).png";
            case "superogre" -> "/images/monsters/SuperOgre.png";
            default -> null;
        };
    }
}
