public class User extends Trainer{
    public User(String name) {
        super();
        this.setName(name);
    }

    public User(Pokemon[] team, String name) {
        super(team, name);
    }

    public void move() {

    }
}
