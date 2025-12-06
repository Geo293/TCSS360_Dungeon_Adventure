package tests;


import controller.MonsterFactory;
import model.*;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MonsterFactory using an in-memory SQLite database.
 * @author Carson Poirier
 * @version 12/5/25
 */
public class MonsterFactoryTest {

    private Connection conn;

    @BeforeEach
    void setUpDatabase() throws SQLException, ClassNotFoundException {
        // Ensure SQLite driver is available
        Class.forName("org.sqlite.JDBC");

        // In-memory SQLite DB
        conn = DriverManager.getConnection("jdbc:sqlite::memory:");

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE monsters (
                    name TEXT,
                    hit_points INTEGER,
                    attack_speed INTEGER,
                    chance_to_hit REAL,
                    min_damage INTEGER,
                    max_damage INTEGER,
                    chance_to_heal REAL,
                    min_heal INTEGER,
                    max_heal INTEGER
                )
            """);
            // Regular monsters
            stmt.executeUpdate("INSERT INTO monsters VALUES ('Ogre', 100, 2, 0.7, 10, 20, 0.2, 5, 10)");
            stmt.executeUpdate("INSERT INTO monsters VALUES ('Skeleton', 80, 3, 0.6, 5, 15, 0.1, 3, 6)");
            stmt.executeUpdate("INSERT INTO monsters VALUES ('Gremlin', 60, 4, 0.5, 8, 12, 0.3, 4, 8)");
            // Boss
            stmt.executeUpdate("INSERT INTO monsters VALUES ('SuperOgre', 200, 5, 0.8, 20, 30, 0.4, 10, 20)");
        }
    }

    @AfterEach
    void tearDownDatabase() throws SQLException {
        if (conn != null) conn.close();
    }

    @Test
    void createRegularMonstersFromRows_returnsExpectedSubclasses() throws Exception {
        List<Monster> monsters = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM monsters")) {
            while (rs.next()) {
                Monster m = invokeCreateRegularMonsterFromRow(rs);
                if (m != null) monsters.add(m);
            }
        }

        assertEquals(3, monsters.size(), "Should load 3 regular monsters");
        assertTrue(monsters.stream().anyMatch(m -> m instanceof Ogre));
        assertTrue(monsters.stream().anyMatch(m -> m instanceof Skeleton));
        assertTrue(monsters.stream().anyMatch(m -> m instanceof Gremlin));
    }

    @Test
    void createBossMonsterFromRow_returnsOnlySuperOgre() throws Exception {
        List<Monster> bosses = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM monsters")) {
            while (rs.next()) {
                Monster boss = invokeCreateBossMonsterFromRow(rs);
                if (boss != null) bosses.add(boss);
            }
        }

        assertEquals(1, bosses.size(), "Should load 1 boss monster");
        assertInstanceOf(SuperOgre.class, bosses.getFirst());
    }

    @Test
    void regularFactory_skipsSuperOgre() throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM monsters WHERE name = ?")) {
            ps.setString(1, "SuperOgre");
            try (ResultSet rs = ps.executeQuery()) {
                assertTrue(rs.next(), "Row should exist");
                Monster m = invokeCreateRegularMonsterFromRow(rs);
                assertNull(m, "SuperOgre should not be created as a regular monster");
            }
        }
    }

    // Reflection helpers to access private factory methods
    private Monster invokeCreateRegularMonsterFromRow(ResultSet rs) throws Exception {
        var method = MonsterFactory.class.getDeclaredMethod("createRegularMonsterFromRow", ResultSet.class);
        method.setAccessible(true);
        return (Monster) method.invoke(null, rs);
    }

    private Monster invokeCreateBossMonsterFromRow(ResultSet rs) throws Exception {
        var method = MonsterFactory.class.getDeclaredMethod("createBossMonsterFromRow", ResultSet.class);
        method.setAccessible(true);
        return (Monster) method.invoke(null, rs);
    }
}