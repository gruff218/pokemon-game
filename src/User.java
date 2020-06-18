import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User extends Trainer {

    private HashMap<CatchingBall, Integer> pokeballs;
    private boolean opponentCaught;

    public User(String name) {
        super();
        this.setName(name);
        this.pokeballs = new HashMap<>();
    }

    public User(Pokemon[] team, String name) {
        super(team, name);
        this.pokeballs = new HashMap<>();
    }

    public void move() {

    }


    public void getChoice(Pokemon opponent) {
        int choice = HelperMethods.getNumber("Would you like to:\n1) Attack\n2) Switch\n3) Bag\n4) Run\nEnter a number from:", 1, 4);
        if (choice == 1) {
            this.getCurrentPok().attack(opponent);

        } else if (choice == 2) {
            this.switchPokemon();
        } else if (choice == 3) {
            if (opponent.getTrainer() == null) {
                int ballChoice = HelperMethods.getNumber("Which Poke Ball which you like to use?\n" + this.getBallDisplay() + "Enter a number from:", 1, this.pokeballs.size());
                int i = 0;
                boolean caught = false;
                for (Map.Entry<CatchingBall, Integer> entry : this.pokeballs.entrySet()) {
                    i++;
                    if (i == ballChoice) {
                        caught = this.attemptCatch(opponent, entry.getKey());
                    }
                }
                if (!caught) {
                    this.getCurrentPok().doStatusEffects(opponent);
                } else {
                    this.getCurrentPok().addXp(opponent.getBaseXp());
                }
            } else {
                System.out.println("You can't catch another trainer's pokemon!");
                this.getCurrentPok().doStatusEffects(opponent);
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
            int choice = HelperMethods.getNumber("Which pokemon would you like to switch to?\n" + this.getTeamDisplay() + "\n", 1, 6);
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

    @Override
    public void battleLost() {
        System.out.println("You have no remaining pokemon!");

    }

    public HashMap<CatchingBall, Integer> getPokeballs() {
        return pokeballs;
    }

    public void setPokeballs(HashMap<CatchingBall, Integer> pokeballs) {
        this.pokeballs = pokeballs;
    }

    public void addBall(CatchingBall ball) {
        this.addBalls(ball, 1);
    }

    public void addBalls(CatchingBall ball, int num) {
        if (this.pokeballs.containsKey(ball)) {
            this.pokeballs.replace(ball, this.pokeballs.get(ball) + num);
        } else {
            this.pokeballs.put(ball, num);
        }
    }

    public void removeBall(CatchingBall ball) {
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
        for (Map.Entry<CatchingBall, Integer> entry : this.pokeballs.entrySet()) {
            display += (i + ") " + entry.getKey().getDisplay() + ": " + entry.getValue() + "\n");
            i++;
        }
        return display;
    }

    public boolean attemptCatch(Pokemon opponent, CatchingBall ball) {
        double temp = (int)(3 * opponent.getHpStat() - 2 * opponent.getHp()) * opponent.getCatchRate() * ball.getCatchMultiplier(opponent);
        double statusRate = HelperMethods.ailmentToCatchRate(opponent.getAilment());
        int modifiedCatchRate = (int)(temp / (3 * opponent.getHpStat()) * statusRate);
        int shakeCount = 0;
        int b = 1048560/(int)Math.sqrt(Math.sqrt((double)16711680/modifiedCatchRate));
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

        if (shakeCount == 4) {
            System.out.println(opponent.getDisplay() + " was added to your team!");
            this.addPoke(opponent);
            this.opponentCaught = true;
            return true;
        }
        return false;
    }

}
