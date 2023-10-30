package Server.Model;

/**
 * keeps track of the accounts made
 */
public class User {
    /**
     * unique name for the user
     */
    String username;
    /**
     * password that matches with the username and email
     */
    String password;
    /**
     * unique email for the account
     */
    String email;

    String GetUserName(){
        return username;
    }
}
