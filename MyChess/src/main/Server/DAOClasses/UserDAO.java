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



//    public void createUser(User user) throws DataAccessException {
//        /**
//         * Checks to see if the username is already being used and checks to make sure there
//         * is a username and password before creating a new user and authToken
//         */
//        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()
//        || user.getPassword().isBlank() || user.getUsername().isBlank()){
//            throw new DataAccessException("Error: bad request");
//        }
//        if (users_map.containsKey(user.getUsername())){
//            throw new DataAccessException("Error: already taken");
//        }
//        users_map.put(user.getUsername(),user);
//        //throw new DataAccessException("Error: description");
//    }
public void createUser(User user) throws SQLException {

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
                throw new SQLException("Did not insert into database");
            }
        }
    }
    /**
     * Tries to find the desired user from the database by username
     */
    public User find(String username, String password) throws DataAccessException{
        if (users_map.containsKey(username)){
            if (!users_map.get(username).getPassword().equals(password)) {
                throw new DataAccessException("Error: unauthorized");
            }
            return users_map.get(username);
        }
        throw new DataAccessException("Error: unauthorized");
    }


    /**
     * Finds all users that have been created
     */
    public Integer findAll(){
        if (!users_map.keySet().isEmpty()){
            return users_map.keySet().size();
        }else {
            return 0;
        }
    }

    /**
     * Clears all users from the database
     */
    public void clearAll() {
        users_map.clear();
    }

    public void checkRegister(String username) throws DataAccessException{
        if (users_map.containsKey(username)){
            throw new DataAccessException("Error: already taken");
        }
    }
// TODO: do this first
    void configureDatabase() throws SQLException {
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

//    void makeSQLCalls() throws SQLException {
//        try (var conn = getConnection()) {
//            // Execute SQL statements on the connection here
//        }
//    }
}
