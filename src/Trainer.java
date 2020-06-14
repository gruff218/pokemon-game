import java.util.*;

public class Trainer {
    private Pokemon[] team;
    private Pokemon currentPok;
    private String name;


    public Trainer() {
        this.team = new Pokemon[6];
        this.currentPok = null;
        this.name = "";
    }

    public Trainer(Pokemon[] team, String name) {
        this.team = team;
        for (Pokemon pokemon : team) {
            if (pokemon == null) {
                continue;
            }
            pokemon.setTrainer(this);
        }
        currentPok = null;
        this.name = name;
    }

    public void battle(Pokemon opponent, Scanner s) {
        System.out.println("A wild " + opponent.getDisplay() + " has appeared!");
        boolean cont = true;
        for (int i = 0; i < 6; i++) {
            if (team[i] != null) {
                currentPok = team[i];
                break;
            }
        }
        while(cont) {
            cont = currentPok.battle(this, opponent, s);
        }
        System.out.println("Battle finished");
    }

    public boolean getChoice(Pokemon currentPok, Pokemon opponent, Scanner s) {
        int choice = HelperMethods.getNumber(s,"Would you like to:\n1) Attack\n2) Switch\n3) Bag\n4) Run\nEnter a number from:", 1, 4);
        if (choice == 1) {
            boolean temp = currentPok.attack(this, opponent, s);
            this.currentPok.opponentAttack(opponent);
            return temp;
        } else if (choice == 2) {
            this.switchPokemon(s);
        } else if (choice == 3) {

        } else if (choice == 4) {

        } else {
            System.out.println("Something has gone horribly horribly wrong... (getChoice method)");
        }
        //this.currentPok.opponentAttack(opponent);
        return true;
    }
    public boolean battleWon(Pokemon opponent, Pokemon currentPok) {
        System.out.println(opponent.getDisplay() + " has fainted!");
        return false;
    }



    public void switchPokemon(Scanner s) {
        boolean badPoke = true;
        while (badPoke) {
            int choice = HelperMethods.getNumber(s, "Which pokemon would you like to switch to?", 1, 6);
            if (this.team[choice - 1] == null) {
                System.out.println("You don't have a pokemon in that slot");
            } else if (this.team[choice - 1].getHp() <= 0) {
                System.out.println(this.team[choice - 1].getDisplay() + " is fainted!");
            } else {
                this.currentPok.setNotSwitched(false);
                this.currentPok = this.team[choice - 1];
                badPoke = false;
            }
        }
    }

    public boolean pokFainted(Scanner s) {
        for (int i = 0; i < 6; i++) {
            if (this.team[i] != null) {
                if (this.team[i].getHp() > 0) {
                    this.switchPokemon(s);
                    return true;
                }
            }
        }
        this.battleLost();
        return false;
    }
    public void resetTempStats() {
        for (Pokemon pokemon : team) {
            pokemon.resetTempStats();
        }
    }

    public void battleLost() {
        System.out.println("You have no remaining pokemon!");
    }

    public Pokemon[] getTeam() {
        return team;
    }

    public void setTeam(Pokemon[] team) {
        this.team = team;
    }

    public Pokemon getCurrentPok() {
        return currentPok;
    }

    public void setCurrentPok(Pokemon currentPok) {
        this.currentPok = currentPok;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
