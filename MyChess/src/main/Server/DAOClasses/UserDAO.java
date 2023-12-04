package Server.DAOClasses;

import Server.Model.Game;
import Server.Model.User;
import dataAccess.DataAccessException;
import dataAccess.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.sql.DriverManager.getConnection;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDAO {

    public UserDAO(){}
    private Map<String, User> users_map = new HashMap<>();
    private Database database = new Database();

public void createUser(User user) throws SQLException {
        // UPDATED
        Connection conn;
        try {
            conn = database.getConnection();
        } catch (DataAccessException exception) {
            throw new RuntimeException(exception);
        }
        if (user.getUsername().matches("[a-zA-Z]+")) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)", RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());

                preparedStatement.executeUpdate();
            }catch (SQLException exception){
                throw new SQLException("Error: already taken");
            }
        }
}

//    void deleteUser(Connection conn, String username) throws SQLException {
//    // TODO: UPDATE
//        try (var preparedStatement = conn.prepareStatement("DELETE FROM user WHERE username=?")) {
//            preparedStatement.setString(1, username);
//            preparedStatement.executeUpdate();
//        }
//    }
    /**
     * Tries to find the desired user from the database by username
     */
    public User find(String username, String password) throws DataAccessException{
        // UPDATED
        Connection conn;
        User user = null;
        try {
            conn = database.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var name = rs.getString("username");
                    var pass = rs.getString("password");
                    var email = rs.getString("email");

                    if (!pass.equals(password)){
                        throw new DataAccessException("Error: wrong password");
                    }

                    user = new User(pass, name, email);
                    System.out.printf("username: %s, password: %s, email: %s", name, pass, email);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
        return user;
    }


    /**
     * Finds all users that have been created
     */
    public Integer findAll(){
        // TODO: UPDATE LAST
        if (!users_map.keySet().isEmpty()){
            return users_map.keySet().size();
        }else {
            return 0;
        }
    }

    /**
     * Clears all users from the database
     */
    public void clearAll() throws SQLException{
        // UPDATED
        Connection conn = null;
        try {
            conn = database.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        try (var preparedStatement = conn.prepareStatement("TRUNCATE user")) {
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new SQLException("Error: unexpected error");
        }
    }

//    public void checkRegister(String username) throws DataAccessException{
//        if (users_map.containsKey(username)){
//            throw new DataAccessException("Error: already taken");
//        }
//    }

    public void configureDatabase() throws SQLException {
        // UPDATED
        try (Connection conn = database.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS mydatabase");
            createDbStatement.executeUpdate();

//            conn.setCatalog("mydatabase");

            var createUserTable = """
            CREATE TABLE  IF NOT EXISTS user (
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
            )""";


            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
