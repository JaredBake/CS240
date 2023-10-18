package chess;
// private variables for model
public class Request {// private variables for model

    // private variables for request
    private String login = "";// private variables for model
    private String user = "";// private variables for model
    private String register = "";// private variables for model
    private String join_game = "";// private variables for model
    // private variables for model

    boolean loginAccess(String login){// private variables for model
        Result r = new Result();// private variables for model
        this.login = login;// private variables for model
        return r.hasAccess(this.login);// private variables for model
    }
    boolean userAccess(String user) {// private variables for model
        Result r = new Result();// private variables for model
        this.user = user;// private variables for model
        return r.hasAccess(this.user);// private variables for model
    }
    boolean registerAccess(String register){// private variables for model
        Result r = new Result();// private variables for model
        this.register = register;// private variables for model
        return r.hasAccess(this.register);// private variables for model
    }
    boolean joinGameAccess(String register){// private variables for model
        Result r = new Result();// private variables for model
        this.register = register;// private variables for model
        return r.hasAccess(this.register);// private variables for model
    }
}
