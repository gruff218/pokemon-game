public class UltraBall extends CatchingBall {

    public UltraBall() {
        this.setBaseCatchRate(2.0);
        this.setDisplay("Ultra Ball");
        this.setId("ultra-ball");
    }
    public double getCatchMultiplier(Pokemon opponent) {
        return this.getBaseCatchRate();
    }
}
