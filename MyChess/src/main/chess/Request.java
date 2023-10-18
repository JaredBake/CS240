package chess;

public class Request {


    private String login = "";
    private String user = "";
    private String register = "";
    private String join_game = "";


    boolean loginAccess(String login){
        Result r = new Result();
        this.login = login;
        return r.hasAccess(this.login);
    }
    boolean userAccess(String user) {
        Result r = new Result();
        this.user = user;
        return r.hasAccess(this.user);
    }
    boolean registerAccess(String register){
        Result r = new Result();
        this.register = register;
        return r.hasAccess(this.register);
    }
    boolean joinGameAccess(String register){
        Result r = new Result();
        this.register = register;
        return r.hasAccess(this.register);
    }
}
