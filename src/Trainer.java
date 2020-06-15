import java.util.*;

public class Trainer {
    private Pokemon[] team;
    private Pokemon currentPok;
    private String name;
    private boolean switched;


    public Trainer() {
        this.team = new Pokemon[6];
        this.currentPok = null;
        this.name = "";
        this.switched = false;
    }

    public Trainer(Pokemon[] team, String name) {
        this.team = team;
        for (Pokemon pokemon : team) {
            if (pokemon == null) {
                continue;
            }
            pokemon.setTrainer(this);
        }
        this.currentPok = null;
        this.name = name;
        this.switched = false;
    }

    public boolean battle(Pokemon opponent, Scanner s) {
        if (opponent.getTrainer() == null) {
            System.out.println("A wild " + opponent.getDisplay() + " has appeared!");
            this.resetTempStats();
        } else {
            System.out.println(opponent.getTrainer().getName() + " has sent out " + opponent.getDisplay() + "!");
        }
        boolean cont = true;
        for (int i = 0; i < 6; i++) {
            if (team[i] != null) {
                this.currentPok = this.team[i];
                break;
            }
        }
        while(cont) {
            cont = currentPok.battle(this, opponent, s);
            if (opponent.getTrainer() != null && !switched) {
                cont = !cont;
                this.switched = !switched;
            }
        }
        if (opponent.getTrainer() == null) {
            System.out.println("Battle finished");
            return false;
        }

        return opponent.getTrainer().checkPokes();
    }

    public void battle(Trainer opponent, Scanner s) {
        System.out.println("You are battling " + opponent.getName() + "!");
        this.resetTempStats();
        opponent.resetTempStats();
        boolean cont = true;
        boolean currentPokNotChosen = true;
        boolean opponentCurrentPokNotChosen = true;
        for (int i = 0; i < 6; i++) {
            if (team[i] != null && currentPokNotChosen) {
                this.currentPok = this.team[i];
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
        while (cont) {
            cont = this.battle(opponent.getCurrentPok(), s);
            System.out.println(opponent.getCurrentPok());
        }

        System.out.println("Battle finished");
    }

    public boolean getChoice(Pokemon currentPok, Pokemon opponent, Scanner s) {
        int choice = HelperMethods.getNumber(s,"Would you like to:\n1) Attack\n2) Switch\n3) Bag\n4) Run\nEnter a number from:", 1, 4);
        if (choice == 1) {
            boolean temp = currentPok.attack(opponent, s);
            if (temp && opponent.getHp() > 0) {
                System.out.println(opponent);
                opponent.attack(this.currentPok, s);
            }
            return temp;
        } else if (choice == 2) {
            this.switchPokemon(s);
        } else if (choice == 3) {

        } else if (choice == 4) {

        } else {
            System.out.println("Something has gone horribly horribly wrong... (getChoice method)");
        }
        opponent.attack(this.currentPok, s);
        return true;
    }
    public boolean battleWon(Pokemon opponent, Scanner s) {
        System.out.println(opponent.getDisplay() + " has fainted!");
        if (opponent.getTrainer() != null) {
            return opponent.getTrainer().checkPokes(s);
        } else {
            return false;
        }
    }



    public void switchPokemon(Scanner s) {
        if (this instanceof User) {
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
                    System.out.println("You sent out " + this.currentPok.getDisplay() + "!");
                    this.switched = true;
                }
            }
        } else {
            for (int i = 0; i < this.team.length; i++) {
                if (this.team[i] != null) {
                    if (this.team[i].getHp() > 0) {
                        this.currentPok = this.team[i];
                        System.out.println(this.getName() + " sent out " + this.currentPok.getDisplay() + "!");
                        break;
                    }
                }
            }
        }
    }

    public boolean checkPokes(Scanner s) {
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
    public boolean checkPokes() {
        for (int i = 0; i < 6; i++) {
            if (this.team[i] != null) {
                if (this.team[i].getHp() > 0) {
                    return true;
                }
            }
        }
        return false;
    }
    public void resetTempStats() {
        for (Pokemon pokemon : team) {
            if (pokemon != null) {
                pokemon.resetTempStats();
            }
        }

    }

    public void battleLost() {
        if (this instanceof User) {
            System.out.println("You have no remaining pokemon!");
        } else {
            System.out.println(this.getName() + " has no remaining pokemon!");
        }
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
