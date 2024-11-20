package User;// User.User.java

public class User {
    private static int id;
    private static String username;

    public User(int id, String username) {
        User.id = id;
        User.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
