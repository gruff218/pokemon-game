import java.util.HashMap;
import java.util.Map;

public class User extends Trainer {

    private HashMap<PokeBall, Integer> pokeballs;
    private HashMap<Potion, Integer> potions;
    private boolean opponentCaught;

    public User(String name) {
        super();
        this.setName(name);
        this.pokeballs = new HashMap<>();
        this.potions = new HashMap<>();
    }

    public User(Pokemon[] team, String name) {
        super(team, name);
        this.pokeballs = new HashMap<>();
        this.potions = new HashMap<>();
    }

    public void move() {

    }


    public void getChoice(Pokemon opponent) {
        int choice = HelperMethods.getNumber("Would you like to:\n1) Attack\n2) Switch\n3) Bag\n4) Run\nEnter a number from:", 1, 4, this);
        if (choice == 1) {
            this.getCurrentPok().attack(opponent);

        } else if (choice == 2) {
            this.switchPokemon();
        } else if (choice == 3) {
            int bagChoice = HelperMethods.getNumber("Would you like to open your health restores, or pokeballs?\nEnter a number from:", 1, 2, this);
            if (bagChoice == 1) {
                int potionChoice = HelperMethods.getNumber("Which health restore would you like to use?\n" + this.getPotionDisplay() + "Enter a number from:", 1, this.potions.size(), this);
                int pokChoice = HelperMethods.getNumber("Which pokemon would you like to use it on?\n" + this.getTeamDisplay() + "Enter a number from:", 1, 6, this) - 1;
                int i = 0;
                for (Map.Entry<Potion, Integer> entry : this.potions.entrySet()) {
                    i++;
                    if (i == potionChoice) {
                        this.usePotion(pokChoice, entry.getKey());
                    }
                }
                this.getCurrentPok().doStatusEffects(opponent);

            } else {
                if (opponent.getTrainer() == null) {
                    int ballChoice = HelperMethods.getNumber("Which Poke Ball which you like to use?\n" + this.getBallDisplay() + "Enter a number from:", 1, this.pokeballs.size(), this);
                    int i = 0;
                    boolean caught = false;
                    for (Map.Entry<PokeBall, Integer> entry : this.pokeballs.entrySet()) {
                        i++;
                        if (i == ballChoice) {
                            caught = this.attemptCatch(opponent, entry.getKey());
                        }
                    }
                    if (!caught) {
                        this.getCurrentPok().doStatusEffects(opponent);
                    } else {
                        this.getCurrentPok().addXp(opponent.getBaseXp(), false);
                    }
                } else {
                    System.out.println("You can't catch another trainer's pokemon!");
                    this.getCurrentPok().doStatusEffects(opponent);
                }
            }
        } else if (choice == 4) {

        } else {
            System.out.println("Something has gone horribly horribly wrong... (getChoice method)");
        }
        if (opponent.getHp() > 0) {
            //System.out.println(opponent);
            opponent.attack(this.getCurrentPok());
        }
    }

    @Override
    public void resetTempStats() {
        for (Pokemon pokemon : this.getTeam()) {
            if (pokemon != null) {
                pokemon.resetTempStats();
            }
        }
        this.opponentCaught = false;
    }

    public void battle(Trainer opponent) {
        System.out.println("You are battling " + opponent.getName() + "!");
        this.resetTempStats();
        opponent.resetTempStats();
        boolean cont = true;
        boolean currentPokNotChosen = true;
        boolean opponentCurrentPokNotChosen = true;
        for (int i = 0; i < 6; i++) {
            if (this.getTeam()[i] != null && currentPokNotChosen) {
                this.setCurrentPok(this.getTeam()[i]);
                currentPokNotChosen = false;
            }
            if (opponent.getTeam()[i] != null && opponentCurrentPokNotChosen) {
                opponent.setCurrentPok(opponent.getTeam()[i]);
                opponentCurrentPokNotChosen = false;
            }
            if (!opponentCurrentPokNotChosen && !currentPokNotChosen) {
                break;
            }
        }
        while (this.checkPokeHP() && opponent.checkPokeHP()) {
            this.battle(opponent.getCurrentPok());
        }

        System.out.println("Battle finished");
    }

    public void battle(Pokemon opponent) {
        this.setSwitched(false);
        this.opponentCaught = false;
        if (opponent.getTrainer() == null) {
            System.out.println("A wild " + opponent.getDisplay() + " has appeared!");
            this.resetTempStats();
        } else {
            //System.out.println("Current Pok: " + this.getCurrentPok().getDisplay());
            System.out.println(opponent.getTrainer().getName() + " has sent out " + opponent.getDisplay() + "!");
            System.out.println("Current Pok: " + opponent.getTrainer().getCurrentPok().getDisplay());
        }
        System.out.println(this.getCurrentPok().getDisplay() + " is battling " + opponent.getDisplay() + "!");
        boolean getSwitched = true;
        while (getSwitched && opponent.getHp() > 0 && !opponentCaught) {
            this.getCurrentPok().battle(this, opponent);
            if (opponent.getTrainer() != null) {
                getSwitched = !opponent.getTrainer().getSwitched();
            }
        }
        this.setSwitched(false);
        if (opponent.getTrainer() == null) {
            System.out.println("Battle finished");
        } else {
            opponent.getTrainer().setSwitched(false);
        }
    }

    @Override
    public void switchPokemon() {
        boolean badPoke = true;
        while (badPoke) {
            int choice = HelperMethods.getNumber("Which pokemon would you like to switch to?\n" + this.getTeamDisplay() + "\n", 1, 6, this);
            if (this.getTeam()[choice - 1] == null) {
                System.out.println("You don't have a pokemon in that slot");
            } else if (this.getTeam()[choice - 1].getHp() <= 0) {
                System.out.println(this.getTeam()[choice - 1].getDisplay() + " is fainted!");
            } else {
                this.getCurrentPok().setNotSwitched(false);
                this.setCurrentPok(this.getTeam()[choice - 1]);
                badPoke = false;
                System.out.println("You sent out " + this.getCurrentPok().getDisplay() + "!");
                this.setSwitched(true);
                this.getCurrentPok().resetTempStats();
            }
        }

    }

    public void usePotion(int pokChoice, Potion potion) {
        if (this.getTeam()[pokChoice] == null) {
            System.out.println("You don't have a pokemon in that slot");
            return;
        }
        System.out.println(this.getTeam()[pokChoice].getDisplay() + " has healed " + this.getTeam()[pokChoice].heal(HelperMethods.getPotionHealing(potion)) + " HP!");
        this.removePotion(potion);
    }


    @Override
    public void battleLost() {
        System.out.println("You have no remaining pokemon!");

    }

    public HashMap<PokeBall, Integer> getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(HashMap<PokeBall, Integer> pokeballs) {
        this.pokeballs = pokeballs;
    }

    public void addBall(PokeBall ball) {
        this.addBalls(ball, 1);
    }

    public void addBalls(PokeBall ball, int num) {
        if (this.pokeballs.containsKey(ball)) {
            this.pokeballs.replace(ball, this.pokeballs.get(ball) + num);
        } else {
            this.pokeballs.put(ball, num);
        }
    }

    public void removeBall(PokeBall ball) {
        if (this.pokeballs.containsKey(ball)) {
            if (this.pokeballs.get(ball) == 1) {
                this.pokeballs.remove(ball);
            } else {
                this.pokeballs.replace(ball, this.pokeballs.get(ball) - 1);
            }
        } else {
            System.out.println("Something went wrong... (User.removeBall()) a ball was removed that didn't exist");
        }
    }

    public String getBallDisplay() {
        String display = "";
        int i = 1;
        for (Map.Entry<PokeBall, Integer> entry : this.pokeballs.entrySet()) {
            display += (i + ") " + HelperMethods.getBallDisplay(entry.getKey()) + ": " + entry.getValue() + "\n");
            i++;
        }
        return display;
    }

    public boolean attemptCatch(Pokemon opponent, PokeBall ball) {
        double temp = (int) (3 * opponent.getHpStat() - 2 * opponent.getHp()) * opponent.getCatchRate() * HelperMethods.getBallCatchRate(ball, opponent);
        double statusRate = HelperMethods.ailmentToCatchRate(opponent.getAilment());
        int modifiedCatchRate = (int) (temp / (3 * opponent.getHpStat()) * statusRate);
        int shakeCount = 0;
        int b = 1048560 / (int) Math.sqrt(Math.sqrt((double) 16711680 / modifiedCatchRate));
        System.out.println("b: " + b);

        while (shakeCount != 4) {
            int randomNum = Pokemon.r.nextInt(65536);
            if (randomNum < b) {
                shakeCount++;
                System.out.println(HelperMethods.shakeNumToDisplay(opponent, shakeCount));
            } else {
                System.out.println("Oh no! " + opponent.getDisplay() + " broke free!");
                break;
            }
        }
        this.removeBall(ball);
        if (shakeCount == 4) {
            System.out.println(opponent.getDisplay() + " was added to your team!");
            this.addPoke(opponent);
            this.opponentCaught = true;
            return true;
        }
        return false;
    }

    public HashMap<Potion, Integer> getPotions() {
        return potions;
    }

    public void setPotions(HashMap<Potion, Integer> potions) {
        this.potions = potions;
    }

    public void addPotion(Potion potion) {
        this.addPotions(potion, 1);
    }

    public void addPotions(Potion potion, int num) {
        if (this.potions.containsKey(potion)) {
            this.potions.replace(potion, this.potions.get(potion) + num);
        } else {
            this.potions.put(potion, num);
        }
    }

    public void removePotion(Potion potion) {
        if (this.potions.containsKey(potion)) {
            if (this.potions.get(potion) == 1) {
                this.potions.remove(potion);
            } else {
                this.potions.replace(potion, this.potions.get(potion) - 1);
            }
        } else {
            System.out.println("Something went wrong... (User.removePotion()) a potion was removed that didn't exist");
        }
    }

    public String getPotionDisplay() {
        String display = "";
        int i = 1;
        for (Map.Entry<Potion, Integer> entry : this.potions.entrySet()) {
            display += (i + ") " + HelperMethods.getPotionDisplay(entry.getKey()) + ": " + entry.getValue() + "\n");
            i++;
        }
        return display;
    }
}
