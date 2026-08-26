package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

import model.*;
import structures.DynamicArray;

/**
 * JDBC persistence boundary for the Campus Service Hub.
 */
public final class SqliteRepository {

    private final String url;

    public SqliteRepository(String databaseFile) {
        url = "jdbc:sqlite:" + databaseFile;
    }

    public boolean initialiseAndSeed(
            String schema,
            DynamicArray<Location> locations,
            DynamicArray<Road> roads,
            DynamicArray<ServiceRequest> requests,
            DynamicArray<Resource> resources) {

        try (Connection c = DriverManager.getConnection(url)) {

            // Create the schema if the database has not been initialized yet.
            if (!tableExists(c, "locations")) {
                executeSchema(c, Files.readString(Path.of(schema)));
            }

            // Make sure the audit table exists even if the database
            // was created with an older version of the schema.
            ensureAuditTable(c);

            c.setAutoCommit(false);

            try (PreparedStatement p =
                         c.prepareStatement(
                                 "INSERT OR IGNORE INTO locations " +
                                 "VALUES (?,?,?,?,?,?)")) {

                for (int i = 0; i < locations.size(); i++) {
                    Location x = locations.get(i);

                    p.setString(1, x.getLocationId());
                    p.setString(2, x.getName());
                    p.setString(3, x.getArea());
                    p.setString(4, x.getType());
                    p.setDouble(5, x.getLatitude());
                    p.setDouble(6, x.getLongitude());

                    p.addBatch();
                }

                p.executeBatch();
            }

            try (PreparedStatement p =
                         c.prepareStatement(
                                 "INSERT OR IGNORE INTO roads " +
                                 "VALUES (?,?,?,?,?)")) {

                for (int i = 0; i < roads.size(); i++) {
                    Road x = roads.get(i);

                    p.setString(1, x.getFromLocationId());
                    p.setString(2, x.getToLocationId());
                    p.setDouble(3, x.getDistance());
                    p.setDouble(4, x.getTravelTime());
                    p.setDouble(5, x.getRoadConditionWeight());

                    p.addBatch();
                }

                p.executeBatch();
            }

            try (PreparedStatement p =
                         c.prepareStatement(
                                 "INSERT OR IGNORE INTO service_requests " +
                                 "VALUES (?,?,?,?,?,?,?,?,?)")) {

                for (int i = 0; i < requests.size(); i++) {
                    ServiceRequest x = requests.get(i);

                    p.setString(1, x.getRequestId());
                    p.setString(2, x.getSource());
                    p.setString(3, x.getDestination());
                    p.setString(4, x.getCategory());
                    p.setString(5, x.getDescription());
                    p.setInt(6, x.getUrgency());
                    p.setString(7, x.getTimeSubmitted());
                    p.setString(8, x.getDeadline());
                    p.setString(9, x.getStatus());

                    p.addBatch();
                }

                p.executeBatch();
            }

            try (PreparedStatement p =
                         c.prepareStatement(
                                 "INSERT OR IGNORE INTO resources " +
                                 "VALUES (?,?,?,?,?)")) {

                for (int i = 0; i < resources.size(); i++) {
                    Resource x = resources.get(i);

                    p.setString(1, x.getResourceId());
                    p.setString(2, x.getType());
                    p.setString(3, x.getHomeLocation());
                    p.setInt(4, x.getCapacity());
                    p.setString(5, x.getAvailabilityStatus());

                    p.addBatch();
                }

                p.executeBatch();
            }

            c.commit();

            return true;

        } catch (SQLException | IOException e) {
            return false;
        }
    }

    /**
     * Records an algorithm performance run.
     */
    public void recordRun(
            String id,
            String algorithm,
            int input,
            long nanos,
            double kb) throws SQLException {

        try (
                Connection c = DriverManager.getConnection(url);
                PreparedStatement p = c.prepareStatement(
                        "INSERT OR REPLACE INTO algorithm_runs " +
                        "(runId, algorithmName, inputSize, timeNs, memoryKb, dateRun) " +
                        "VALUES (?,?,?,?,?,date('now'))")
        ) {

            p.setString(1, id);
            p.setString(2, algorithm);
            p.setInt(3, input);
            p.setLong(4, nanos);
            p.setDouble(5, kb);

            p.executeUpdate();
        }
    }

    /**
     * Records an event in the audit log.
     */
    public void recordAuditEvent(
            String eventType,
            String description) throws SQLException {

        String sql =
                "INSERT INTO audit_events " +
                "(eventType, description, eventTime) " +
                "VALUES (?, ?, datetime('now','localtime'))";

        try (
                Connection c = DriverManager.getConnection(url);
                PreparedStatement p = c.prepareStatement(sql)
        ) {

            p.setString(1, eventType);
            p.setString(2, description);

            p.executeUpdate();
        }
    }

    /**
     * Returns the number of audit events currently stored.
     */
    public int getAuditEventCount() throws SQLException {

        try (
                Connection c = DriverManager.getConnection(url);
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery(
                        "SELECT COUNT(*) FROM audit_events")
        ) {

            return r.next() ? r.getInt(1) : 0;
        }
    }

    private void ensureAuditTable(Connection c) throws SQLException {

        try (Statement st = c.createStatement()) {

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS audit_events (" +
                    "eventId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "eventType TEXT NOT NULL," +
                    "description TEXT NOT NULL," +
                    "eventTime TEXT NOT NULL" +
                    ")"
            );
        }
    }

    private void executeSchema(Connection c, String sql)
            throws SQLException {

        for (String statement : sql.split(";")) {

            String s = statement.trim();

            if (!s.isEmpty()) {

                try (Statement st = c.createStatement()) {
                    st.execute(s);
                }
            }
        }
    }

    private boolean tableExists(Connection c, String table)
            throws SQLException {

        try (ResultSet r =
                     c.getMetaData().getTables(
                             null,
                             null,
                             table,
                             null)) {

            return r.next();
        }
    }
}