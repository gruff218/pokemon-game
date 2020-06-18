public class PokeBall extends CatchingBall{

    public PokeBall() {
        this.setBaseCatchRate(1);
        this.setDisplay("Poke Ball");
        this.setId("poke-ball");
    }
    public int getCatchMultiplier(Pokemon opponent) {
        return this.getBaseCatchRate();
    }
}
