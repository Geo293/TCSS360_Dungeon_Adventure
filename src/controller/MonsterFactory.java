package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class responsible for loading monster data from the SQLite database
 * and instantiating appropriate Monster subclass objects.
 * Follows the MVC pattern: this class acts as the controller for monster creation.
 *
 * @author Carson Poirier
 * @version 11/29/25
 */
public class MonsterFactory {
    private static final String DB_URL = "jdbc:sqlite:monsters.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found!");
            e.printStackTrace();
        }
    }

    /**
     * Loads all regular (non-boss) monsters from the database.
     *
     * @return List of regular Monster instances.
     */
    public static List<Monster> loadRegularMonsters() {
        List<Monster> monsters = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM monsters")) {

            while (rs.next()) {
                String name = rs.getString("name");
                if (!name.equalsIgnoreCase("SuperOgre")) {
                    Monster monster = createMonsterFromRow(rs);
                    monsters.add(monster);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading regular monsters: " + e.getMessage());
        }

        return monsters;
    }

    /**
     * Loads all boss monsters from the database.
     *
     * @return List of boss Monster instances.
     */
    public static List<Monster> loadBossMonsters() {
        List<Monster> bosses = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM monsters")) {

            while (rs.next()) {
                String name = rs.getString("name");
                if (name.equalsIgnoreCase("SuperOgre")) {
                    Monster boss = createMonsterFromRow(rs);
                    bosses.add(boss);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading boss monsters: " + e.getMessage());
        }

        return bosses;
    }

    /**
     * Returns a randomly selected regular Monster.
     *
     * @return A single Monster instance, or null if none found.
     */
    public static Monster getRandomMonster() {
        List<Monster> monsters = loadRegularMonsters();
        if (monsters.isEmpty()) {
            return null;
        }
        return monsters.get(new Random().nextInt(monsters.size()));
    }

    /**
     * Returns the boss monster (currently only SuperOgre).
     *
     * @return a SuperOgre instance, or null if not found.
     */
    public static Monster getBossMonster() {
        List<Monster> bosses = loadBossMonsters();
        return bosses.isEmpty() ? null : bosses.get(0);
    }

    /**
     * Creates a Monster subclass instance from a database row.
     *
     * @param rs ResultSet positioned at a valid monster row.
     * @return Monster instance matching the name field.
     * @throws SQLException if column access fails.
     */
    private static Monster createMonsterFromRow(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int hitPoints = rs.getInt("hit_points");
        int attackSpeed = rs.getInt("attack_speed");
        double chanceToHit = rs.getDouble("chance_to_hit");
        int minDamage = rs.getInt("min_damage");
        int maxDamage = rs.getInt("max_damage");
        double chanceToHeal = rs.getDouble("chance_to_heal");
        int minHeal = rs.getInt("min_heal");
        int maxHeal = rs.getInt("max_heal");
        String nameLower = name.toLowerCase();

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



