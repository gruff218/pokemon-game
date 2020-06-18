public abstract class CatchingBall extends GameComponent{
    private int baseCatchRate;
    private String display;
    private String id;

    public abstract int getCatchMultiplier(Pokemon opponent);

    public int getBaseCatchRate() {
        return baseCatchRate;
    }

    public void setBaseCatchRate(int baseCatchRate) {
        this.baseCatchRate = baseCatchRate;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
