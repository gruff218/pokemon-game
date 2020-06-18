public class GreatBall extends CatchingBall {

    public GreatBall() {
        this.setBaseCatchRate(1.5);
        this.setDisplay("Great Ball");
        this.setId("great-ball");
    }
    public double getCatchMultiplier(Pokemon opponent) {
        return this.getBaseCatchRate();
    }
}
