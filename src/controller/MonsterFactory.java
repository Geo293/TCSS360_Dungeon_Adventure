package controller;

import model.Gremlin;
import model.Monster;
import model.Ogre;
import model.Skeleton;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class responsible for loading monster data from the SQLite database
 * and instantiating appropriate Monster subclass objects.
 * Follows the MVC pattern: this class acts as the controller for monster creation.
 * @author Carson Poirier
 * @version 11/8/25
 */
public class MonsterFactory {
    private static final String DB_URL = "jdbc:sqlite:resources/monsters.db";

    /**
     * Loads all monsters from the database and returns them as a list of Monster objects.
     *
     * @return List of Monster instances based on database rows.
     */
    public static List<Monster> loadMonsters() {
        List<Monster> monsters = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM monsters")) {

            while (rs.next()) {
                Monster monster = createMonsterFromRow(rs);
                if (monster != null) {
                    monsters.add(monster);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading monsters: " + e.getMessage());
        }

        return monsters;
    }

    /**
     * Returns a randomly selected Monster from the database.
     *
     * @return A single Monster instance, or null if none found.
     */
    public static Monster getRandomMonster() {
        List<Monster> monsters = loadMonsters();
        if (monsters.isEmpty()) return null;
        return monsters.get(new Random().nextInt(monsters.size()));
    }

    /**
     * Creates a Monster subclass instance from a database row.
     *
     * @param rs ResultSet positioned at a valid monster row.
     * @return Monster instance matching the name field.
     * @throws SQLException if column access fails.
     */
    private static Monster createMonsterFromRow(ResultSet rs) throws SQLException {
        String name = rs.getString("name").toLowerCase();
        int attackSpeed = rs.getInt("attack_speed");
        double chanceToHit = rs.getDouble("Crit_chance_1");
        int maxDamage = rs.getInt("Crit_damage_1");
        double chanceToHeal = rs.getDouble("Crit_chance_2");
        int maxHeal = rs.getInt("Crit_damage_2");

        // Assign default values based on monster type
        return switch (name) {
            case "ogre" -> new Ogre(name);       // uses hardcoded stats from Ogre class
            case "skeleton" -> new Skeleton(name);
            case "gremlin" -> new Gremlin(name);
            default -> new Monster(name, 100, 20, maxDamage, attackSpeed,
                    chanceToHit, chanceToHeal, 10, maxHeal) {};
        };
    }
}
