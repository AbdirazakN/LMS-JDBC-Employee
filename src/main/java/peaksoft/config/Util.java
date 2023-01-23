package peaksoft.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection(){
        try {
            System.out.println("Successfully connected!");
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/SimpleDataBase",
                    "postgres",
                    "postgres00"
            );
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
