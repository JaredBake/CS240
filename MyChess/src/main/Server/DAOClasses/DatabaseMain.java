package Server.DAOClasses;

import java.sql.SQLException;

public class DatabaseMain {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            userDAO.configureDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            gameDAO.configureDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            authDAO.configureDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
