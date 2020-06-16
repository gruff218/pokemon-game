import java.util.*;

public class Trainer extends GameComponent {
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
        if (team.length < 6) {
            this.team = new Pokemon[6];
            for (int i = 0; i < team.length; i++) {
                this.team[i] = team[i];
            }
        } else if (team.length > 6) {
            System.out.println("A Trainer was created with team of more than 6 (Don't worry we chopped off anything extra)");
            this.team = new Pokemon[6];
            for (int i = 0; i < 6; i++) {
                this.team[i] = team[i];
            }
        } else {
            this.team = team;
        }
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


    public void switchPokemon() {
        for (int i = 0; i < this.team.length; i++) {
            if (this.team[i] != null) {
                if (this.team[i].getHp() > 0) {
                    this.currentPok = this.team[i];
                    this.switched = true;
                    break;
                }
            }
        }
    }

    public boolean checkPokes() {
        for (int i = 0; i < 6; i++) {
            if (this.team[i] != null) {
                if (this.team[i].getHp() > 0) {
                    this.switchPokemon();
                    return true;
                }
            }
        }
        this.battleLost();
        return false;
    }

    public boolean checkPokeHP() {
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

    public void battleWon(Pokemon opponent) {
        System.out.println(opponent.getDisplay() + " has fainted!");
        this.getCurrentPok().addXp(opponent.getBaseXp());
        if (opponent.getTrainer() != null) {
            opponent.getTrainer().checkPokeHP();
        }
    }

    public void battleLost() {
        System.out.println(this.getName() + " has no remaining pokemon!");

    }

    public String getTeamDisplay() {
        String display = "";
        for (int i = 0; i < 6; i++) {
            display += "Slot " + (i + 1) + ": ";

            if (this.team[i] != null) {
                display += this.team[i].getDisplay() + " HP: " + this.team[i].getHp() + "/" + this.team[i].getHpStat();
            } else {
                display += "Empty";
            }

            if (i != 5) {
                display += "\n";
            }
        }
        return display;
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

    public boolean getSwitched() {
        return switched;
    }

    public void setSwitched(boolean switched) {
        this.switched = switched;
    }
}
