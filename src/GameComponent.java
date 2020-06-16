public class GameComponent {

    private static Character currentKey = null;

    public GameComponent() {

    }

    public static synchronized Character getCurrentKey() {
        return currentKey;
    }

    public static synchronized void setCurrentKey(Character currentKey) {
        GameComponent.currentKey = currentKey;
    }
}
