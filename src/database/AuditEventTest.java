package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Verifies that scheduling audit events are actually persisted in SQLite.
 */
public class AuditEventTest {

    public static void main(String[] args) {

        try {

            SqliteRepository repository =
                    new SqliteRepository("campus_service.db");

            int before =
                    repository.getAuditEventCount();

            repository.recordAuditEvent(
                    "TEST",
                    "Audit persistence test"
            );

            int after =
                    repository.getAuditEventCount();

            if (after == before + 1) {

                System.out.println(
                        "Audit persistence test: PASS"
                );

            } else {

                System.out.println(
                        "Audit persistence test: FAIL"
                );
            }

            try (
                    Connection c =
                            DriverManager.getConnection(
                                    "jdbc:sqlite:campus_service.db");

                    Statement s = c.createStatement();

                    ResultSet r = s.executeQuery(
                            "SELECT eventType, description, eventTime " +
                            "FROM audit_events " +
                            "ORDER BY eventId DESC LIMIT 1")
            ) {

                if (r.next()) {

                    System.out.println(
                            "Latest event type: "
                            + r.getString("eventType")
                    );

                    System.out.println(
                            "Latest description: "
                            + r.getString("description")
                    );

                    System.out.println(
                            "Latest event time: "
                            + r.getString("eventTime")
                    );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Audit persistence test: FAIL"
            );

            e.printStackTrace();
        }
    }
}