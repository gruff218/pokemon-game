public enum PokeBall {

    public PokeBall() {
        this.setBaseCatchRate(1.0);
        this.setDisplay("Poke Ball");
        this.setId("poke-ball");
    }
    public double getCatchMultiplier(Pokemon opponent) {
        return this.getBaseCatchRate();
    }
}
