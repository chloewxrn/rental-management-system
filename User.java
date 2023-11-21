//Chin Pei Wern
//user role in the system and get user data
public class User {
    protected String username;
    protected String password;
    protected String name;
    protected String ID;

    public User () {}
    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User (String ID, String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.ID = ID;
    }
}