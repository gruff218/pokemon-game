public class FastBall extends CatchingBall {

    public FastBall() {
        this.setBaseCatchRate(1.0);
        this.setDisplay("Fast Ball");
        this.setId("fast-ball");
    }
    public double getCatchMultiplier(Pokemon opponent) {
        if (opponent.getbSpdStat() >= 100) {
            return 4.0;
        }
        return this.getBaseCatchRate();
    }
}
