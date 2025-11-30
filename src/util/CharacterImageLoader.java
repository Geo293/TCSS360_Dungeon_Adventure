package util;

public class CharacterImageLoader {
    public static String getImageChar(String theImageName) {
        if (theImageName == null || theImageName.equals("")) {
            throw new IllegalArgumentException("Image name cannot be empty");
        }
        return switch (theImageName) {
            case "Warrior" -> "/images/characters/Warrior.png";
            case "Priestess" -> "/images/characters/Priestess.png";
            case "Thief" -> "/images/characters/Thief.png";
            default -> null;
        };
    }

    public static String getMonster(String theImageName) {
        if (theImageName == null || theImageName.equals("")) {
            throw new IllegalArgumentException("Image name cannot be empty");
        }
       return switch (theImageName) {
            case "Gremlin" -> "/images/monsters/Gremlin.png";
            case "Skeleton" -> "/images/monsters/Skeleton.png";
            case "Ogre" -> "/images/monsters/Ogre(1).png";
            case "SuperOgre" -> "/images/monsters/SuperOgre.png";
            default -> {
                System.err.println("WARNING: Unknown monster name: '" + theImageName + "'");
                yield null;
            }
        };
    }
}
