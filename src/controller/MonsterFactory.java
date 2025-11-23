package controller;

import model.Gremlin;
import model.Monster;
import model.Ogre;
import model.Skeleton;
import model.SuperOgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class responsible for loading monster data from the SQLite database
 * and instantiating appropriate Monster subclass objects.
 * Follows the MVC pattern: this class acts as the controller for monster creation.
 *
 * @author Carson Poirier
 * @version 11/8/25
 */
public final class MonsterFactory {

    /** Database URL for the monsters table. */
    private static final String DB_URL = "jdbc:sqlite:monsters.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (final ClassNotFoundException theException) {
            System.err.println("SQLite JDBC driver not found!");
            theException.printStackTrace();
        }
    }

    /**
     * Loads all monsters from the database and returns them as a list of Monster objects.
     *
     * @return list of Monster instances based on database rows
     */
    public static List<Monster> loadMonsters() {
        final List<Monster> monsters = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM monsters")) {

            while (resultSet.next()) {
                final Monster monster = createMonsterFromRow(resultSet);
                if (monster != null) {
                    monsters.add(monster);
                }
            }

        } catch (final SQLException theException) {
            System.err.println("Error loading monsters: " + theException.getMessage());
        }

        return monsters;
    }

    /**
     * Returns a randomly selected Monster from the database.
     *
     * @return a single Monster instance, or null if none found
     */
    public static Monster getRandomMonster() {
        final List<Monster> monsters = loadMonsters();
        if (monsters.isEmpty()) {
            return null;
        }
        return monsters.get(new Random().nextInt(monsters.size()));
    }

    /**
     * Creates a Monster subclass instance from a database row.
     *
     * @param theResultSet result set positioned at a valid monster row
     * @return Monster instance matching the name field
     * @throws SQLException if column access fails
     */
    private static Monster createMonsterFromRow(final ResultSet theResultSet) throws SQLException {
        final String name = theResultSet.getString("name");
        final int hitPoints = theResultSet.getInt("hit_points");
        final int attackSpeed = theResultSet.getInt("attack_speed");
        final double chanceToHit = theResultSet.getDouble("chance_to_hit");
        final int minDamage = theResultSet.getInt("min_damage");
        final int maxDamage = theResultSet.getInt("max_damage");
        final double chanceToHeal = theResultSet.getDouble("chance_to_heal");
        final int minHeal = theResultSet.getInt("min_heal");
        final int maxHeal = theResultSet.getInt("max_heal");
        final String nameLower = name.toLowerCase();

        return switch (nameLower) {
            case "ogre" -> new Ogre(name, hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            case "skeleton" -> new Skeleton(name, hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            case "gremlin" -> new Gremlin(name, hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            case "superogre" -> new SuperOgre(name, hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            default -> throw new IllegalArgumentException("Unknown monster type: " + name);
        };
    }
}

