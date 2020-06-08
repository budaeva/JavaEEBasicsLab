package db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUtils {
    private static final int BUF_SIZE = 1024;
    private final static String SQL_INIT_FILE = "db_init.sql";
    private static Logger logger = LoggerFactory.getLogger(DbUtils.class);

    private static String DB_URL = "jdbc:postgresql://localhost:5432/javaeebasicsdb";
    private static String LOGIN = "postgres";
    private static String PASSWORD = "postgres";

    private static Connection connection;

    public static void connect() {
        logger.info("DbUtils: connect");
        try {
            Class.forName("org.postgresql.Driver");
            logger.info("DbUtils: Driver plugged in");
            connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD);
            logger.info("DbUtils: Connection established");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initDB() {
        logger.info("DbUtils: initDB");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(SQL_INIT_FILE).getFile());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            char[] query = new char[BUF_SIZE];
            int count = bufferedReader.read(query);
            Statement statement = connection.createStatement();
            logger.info("DbUtils: initDB execution of " + SQL_INIT_FILE);
            statement.execute(new String(query, 0,  count));
            logger.info("DbUtils: initDB executed: " + SQL_INIT_FILE);
            statement.closeOnCompletion();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void closeConnection() {
        logger.info("DbUtils: closeConnection");
        if (connection != null) {
            try {
                connection.close();
                logger.info("DbUtils: Connection closed");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
