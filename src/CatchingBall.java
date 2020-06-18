public abstract class CatchingBall {
    private double baseCatchRate;
    private String display;
    private String id;

    public abstract double getCatchMultiplier(Pokemon opponent);

    public double getBaseCatchRate() {
        return baseCatchRate;
    }

    public void setBaseCatchRate(double baseCatchRate) {
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
