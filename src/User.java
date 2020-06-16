import java.util.Scanner;

public class User extends Trainer {
    public User(String name) {
        super();
        this.setName(name);
    }

    public User(Pokemon[] team, String name) {
        super(team, name);
    }

    public void move() {

    }


    public void getChoice(Pokemon currentPok, Pokemon opponent) {
        int choice = HelperMethods.getNumber("Would you like to:\n1) Attack\n2) Switch\n3) Bag\n4) Run\nEnter a number from:", 1, 4);
        if (choice == 1) {
            currentPok.attack(opponent);

        } else if (choice == 2) {
            this.switchPokemon();
        } else if (choice == 3) {

        } else if (choice == 4) {

        } else {
            System.out.println("Something has gone horribly horribly wrong... (getChoice method)");
        }
        if (opponent.getHp() > 0) {
            //System.out.println(opponent);
            opponent.attack(this.getCurrentPok());
        }
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
        if (opponent.getTrainer() == null) {
            System.out.println("A wild " + opponent.getDisplay() + " has appeared!");
            this.resetTempStats();
        } else {
            //System.out.println("Current Pok: " + this.getCurrentPok().getDisplay());
            System.out.println(opponent.getTrainer().getName() + " has sent out " + opponent.getDisplay() + "!");
        }
        System.out.println(this.getCurrentPok().getDisplay() + " is battling " + opponent.getDisplay() + "!");
        boolean getSwitched = true;
        while (getSwitched && opponent.getHp() > 0) {
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
            }
        }

    }

    @Override
    public void battleLost() {
        System.out.println("You have no remaining pokemon!");

    }
}
