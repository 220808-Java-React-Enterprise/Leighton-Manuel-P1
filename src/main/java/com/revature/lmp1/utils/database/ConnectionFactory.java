package com.revature.lmp1.utils.database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory connectionFactory;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private final Properties props = new Properties();

    private ConnectionFactory() {
        try {
            //It does not like the url!
            //props.load(new FileReader("src/main/resources/db.properties"));
            //It likes this url| Change url to absolute url right now, we have to figure out a better solution
            props.load(new FileReader("C:\\Users\\coola\\Documents\\GitHub\\Leighton-Manuel-P1\\src\\main\\resources\\db.properties"));
            System.out.println(props.getProperty("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) connectionFactory = new ConnectionFactory();
        return connectionFactory;
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
        if (conn == null) throw new RuntimeException("Could not establish connection with the database");
        return conn;
    }
}
