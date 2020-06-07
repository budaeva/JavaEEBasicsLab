package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {
    private final static String SQL_INIT_FILE = "db_init.sql";
    private static Logger logger = LoggerFactory.getLogger(DbUtils.class);

    private static String DB_URL = "jdbc:postgresql://localhost:5432/javaeebasicsdb";
    private static String LOGIN = "postgres";
    private static String PASSWORD = "postgres";

    private static Connection connection;

    public static void connect() {
        logger.info("DatabaseManager: connect");
        try {
            Class.forName("org.postgresql.Driver");
            logger.info("DatabaseManager: Driver plugged in");
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
            logger.info("Database Manager: Connection established");
            connection.close();
            logger.info("Database Manager: Connection closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initDB() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(SQL_INIT_FILE).getFile());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String query = bufferedReader.readLine();
            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.closeOnCompletion();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
