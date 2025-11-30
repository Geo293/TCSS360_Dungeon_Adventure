package controller;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory class responsible for loading monster data from the SQLite database
 * and instantiating appropriate Monster subclass objects.
 * Splits regular monsters and boss monsters into separate loaders so bosses
 * never appear in random encounters.
 *
 * @author Carson Poirier
 * @version 11/29/25
 */
public final class MonsterFactory {
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM monsters")) {

            while (rs.next()) {
                Monster m = createRegularMonsterFromRow(rs);
                if (m != null) {
                    monsters.add(m);
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
                Monster boss = createBossMonsterFromRow(rs);
                if (boss != null) {
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
     * Creates a regular Monster subclass instance from a database row.
     * Ignores boss monsters.
     *
     * @param rs ResultSet positioned at a valid monster row.
     * @return Monster instance, or null if row is a boss.
     * @throws SQLException if column access fails.
     */
    private static Monster createRegularMonsterFromRow(ResultSet rs) throws SQLException {
        String nameLower = rs.getString("name").toLowerCase();
        int hitPoints = rs.getInt("hit_points");
        int attackSpeed = rs.getInt("attack_speed");
        double chanceToHit = rs.getDouble("chance_to_hit");
        int minDamage = rs.getInt("min_damage");
        int maxDamage = rs.getInt("max_damage");
        double chanceToHeal = rs.getDouble("chance_to_heal");
        int minHeal = rs.getInt("min_heal");
        int maxHeal = rs.getInt("max_heal");

        return switch (nameLower) {
            case "ogre" -> new Ogre(rs.getString("name"), hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            case "skeleton" -> new Skeleton(rs.getString("name"), hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            case "gremlin" -> new Gremlin(rs.getString("name"), hitPoints, minDamage, maxDamage,
                    attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
            default -> null; // skip bosses here
        };
    }

    /**
     * Creates a boss Monster subclass instance from a database row.
     *
     * @param rs ResultSet positioned at a valid monster row.
     * @return Boss Monster instance, or null if not a boss.
     * @throws SQLException if column access fails.
     */
    private static Monster createBossMonsterFromRow(ResultSet rs) throws SQLException {
        String nameLower = rs.getString("name").toLowerCase();
        if (!nameLower.equals("superogre")) {
            return null;
        }

        int hitPoints = rs.getInt("hit_points");
        int attackSpeed = rs.getInt("attack_speed");
        double chanceToHit = rs.getDouble("chance_to_hit");
        int minDamage = rs.getInt("min_damage");
        int maxDamage = rs.getInt("max_damage");
        double chanceToHeal = rs.getDouble("chance_to_heal");
        int minHeal = rs.getInt("min_heal");
        int maxHeal = rs.getInt("max_heal");

        return new SuperOgre(rs.getString("name"), hitPoints, minDamage, maxDamage,
                attackSpeed, chanceToHit, chanceToHeal, minHeal, maxHeal);
    }
}



