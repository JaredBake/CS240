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
    public User(String password, String username, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
