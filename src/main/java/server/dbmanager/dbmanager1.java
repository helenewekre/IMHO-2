package server.dbmanager;
import java.nio.file.Path;
import java.sql.*;

import com.mysql.cj.api.mysqla.result.Resultset;
import server.models.User;


public class dbmanager1 {
    // Creating the connection for the database
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "hello";
    private static Connection connection = null;

    public dbmanager1() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Method for closing the connection
    private static void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    //Method for authorizing the user. Prepared statement are used, and a user object is returned.
    public User authorizeUser(String username, String password) throws IllegalArgumentException {
        ResultSet resultSet = null;
        User user = null;

        try {

            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * from User where username = ? AND password = ?");

            authorizeUser.setString(1, username);
            authorizeUser.setString(2, password);

            resultSet = authorizeUser.executeQuery();
            System.out.println("RS:" + resultSet);

            while(resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getInt("type"));

            }

        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch(SQLException exception) {
                exception.printStackTrace();
                close();
            }
        }
        return user;
    }

    // Method for creating a user. Boolean returned, which decides if the user is created or not.
    public boolean createUser(User user) throws IllegalArgumentException {
        try {
            PreparedStatement createUser = connection.prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
            createUser.setString(1,user.getUsername());
            createUser.setString(2,user.getPassword());

            int rowsAffected = createUser.executeUpdate();
            if(rowsAffected == 1) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } return false;
    }
}