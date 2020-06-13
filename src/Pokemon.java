import java.util.*;

public class Pokemon {

    private String display;
    private String id;

    private int hpStat;
    private int atkStat;
    private int defStat;
    private int spAtkStat;
    private int spDefStat;
    private int spdStat;

    private int bHpStat;
    private int bAtkStat;
    private int bDefStat;
    private int bSpAtkStat;
    private int bSpDefStat;
    private int bSpdStat;

    private Type type;
    private int level;
    private int hp;
    private int dexNum;
    private Scanner s;
    private Move[] moves;
    private boolean notSwitched;

    private static Double[][] multipliers = new Double[][] {
            new Double[] {1.0, 1.0, 1.0,	1.0,	1.0,	0.5,    1.0,	0.0,	0.5,    1.0,	1.0,	1.0,	1.0,	1.0,	1.0,	1.0,	1.0,	1.0},
            new Double[] {2.0, 1.0, 0.5,	0.5,	1.0,	2.0,	0.5,	0.0,    2.0,	1.0,	1.0,	1.0,	1.0,	0.5,	2.0,	1.0,	2.0,	0.5},
            new Double[] {1.0, 2.0, 1.0,	1.0,	1.0,	0.5,	2.0,	1.0,	0.5,	1.0,	1.0,	2.0,	0.5,	1.0,	1.0,	1.0,	1.0,	1.0},
            new Double[] {1.0, 1.0, 1.0,	0.5,	0.5,	0.5,	1.0,	0.5,	0.0,	1.0,	1.0,	2.0,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0},
            new Double[] {1.0, 1.0, 0.0,	2.0,	1.0,	2.0,	0.5,	1.0,	2.0,	2.0,	1.0,	0.5,	2.0,	1.0,	1.0,	1.0,	1.0,	1.0},
            new Double[] {1.0, 0.5, 2.0,	1.0,	0.5,	1.0,	2.0,	1.0,	0.5,	2.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	1.0},
            new Double[] {1.0, 0.5, 0.5,	0.5,	1.0,	1.0,	1.0,	0.5,	0.5,	0.5,	1.0,	2.0,	1.0,	2.0,	1.0,	1.0,	2.0,	0.5},
            new Double[] {0.0, 1.0, 1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	0.5,	1.0},
            new Double[] {1.0, 1.0, 1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	0.5,	0.5,	0.5,	1.0,	0.5,	1.0,	2.0,	1.0,	1.0,	2.0},
            new Double[] {1.0, 1.0, 1.0,	1.0,	1.0,	0.5,	2.0,	1.0,	2.0,	0.5,	0.5,	2.0,	1.0,	1.0,	2.0,	0.5,	1.0,	1.0},
            new Double[] {1.0, 1.0, 1.0,	1.0,	2.0,	2.0,	1.0,	1.0,	1.0,	2.0,	0.5,	0.5,	1.0,	1.0,	1.0,	0.5,	1.0,	1.0},
            new Double[] {1.0, 1.0, 0.5,	0.5,	2.0,	2.0,	0.5,	1.0,	0.5,	0.5,	2.0,	0.5,	1.0,	1.0,	1.0,	0.5,	1.0,	1.0},
            new Double[] {1.0, 1.0, 2.0,	1.0,	0.0,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	0.5,	0.5,	1.0,	1.0,	0.5,	1.0,	1.0},
            new Double[] {1.0, 2.0, 1.0,	2.0,	1.0,	1.0,	1.0,	1.0,	0.5,	1.0,	1.0,	1.0,	1.0,	0.5,	1.0,	1.0,	0.0,	1.0},
            new Double[] {1.0, 1.0, 2.0,	1.0,	2.0,	1.0,	1.0,	1.0,	0.5,	0.5,	0.5,	2.0,	1.0,	1.0,	0.5,	2.0,	1.0,	1.0},
            new Double[] {1.0, 1.0, 1.0,	1.0,	1.0,	1.0,	1.0,	1.0,	0.5,	1.0,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	0.0},
            new Double[] {1.0, 0.5, 1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	1.0,	1.0,	0.5,	0.5},
            new Double[] {1.0, 2.0, 1.0,	0.5,	1.0,	1.0,	1.0,	1.0,	0.5,	0.5,	1.0,	1.0,	1.0,	1.0,	1.0,	2.0,	2.0,	1.0},
    };


    enum Type {
        NORMAL,
        FIGHTING,
        FLYING,
        POISON,
        GROUND,
        ROCK,
        BUG,
        GHOST,
        STEEL,
        FIRE,
        WATER,
        GRASS,
        ELECTRIC,
        PSYCHIC,
        ICE,
        DRAGON,
        DARK,
        FAIRY
    }

    public Pokemon() {
        this.display = "";
        this.id = "";
        this.hpStat = 0;
        this.atkStat = 0;
        this.defStat = 0;
        this.spAtkStat = 0;
        this.spDefStat = 0;
        this.spdStat = 0;
    }

    public Pokemon(Type type, int level, String display, String id, int dexNum, Scanner s) {
        this.type = type;
        this.level = level;
        this.display = display;
        this.id = id;
        this.dexNum = dexNum;
        this.s = s;
        this.notSwitched = true;
        this.moves = new Move[]{new Move(), new Move(), new Move(), new Move()};
        this.moves[0].setMove("tackle");
        System.out.println(this.moves[0]);
        this.bHpStat = 44;
        this.bAtkStat = 48;
        this.bDefStat = 65;
        this.bSpAtkStat = 50;
        this.bSpDefStat = 64;
        this.bSpdStat = 43;
        this.calcStats();
        this.hp = this.hpStat;
        System.out.println(hpStat + " " + atkStat + " " + defStat + " " + spAtkStat + " " + spDefStat + " " + spdStat);
    }

    public void calcStats() {
        this.atkStat = (int)Math.floor(Math.floor((2 * this.bAtkStat) * this.level / 100 + 5));
        this.defStat = (int)Math.floor(Math.floor((2 * this.bDefStat) * this.level / 100 + 5));
        this.spAtkStat = (int)Math.floor(Math.floor((2 * this.bSpAtkStat) * this.level / 100 + 5));
        this.spDefStat = (int)Math.floor(Math.floor((2 * this.bSpDefStat) * this.level / 100 + 5));
        this.spdStat = (int)Math.floor(Math.floor((2 * this.bSpdStat) * this.level / 100 + 5));
        this.hpStat = (int)Math.floor((2 * bHpStat) * this.level / 100 + this.level + 10);
    }

    public boolean learn (String moveName, Scanner s) {
        for (int i = 0; i < 4; i++) {
            if (!moves[i].isMove()) {
                System.out.println(this.display + " has learned " + moveName + "!");
                moves[i].setMove(moveName);
                return true;
            }
        }
        int choice = HelperMethods.getNumber(s, this.display + " already knows four moves. Which move would you like to replace? Enter 0 to stop learning " + moveName +
                ".\n1) " + moves[0].getDisplay() + "    2) " + moves[1].getDisplay() +
                "\n3) " + moves[2].getDisplay() + "    4) " + moves[3].getDisplay(), 0, 4);
        if (choice == 0) {
            System.out.println(this.display + " has stopped learning " + moveName + "!");
            return false;
        } else {
            System.out.println(this.display + " has forgotten " + moves[choice - 1].getDisplay() + " and learned " + moveName + "!");
            moves[choice - 1].setMove("water-gun");
            return true;
        }
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHpStat() {
        return hpStat;
    }

    public void setHpStat(int hpStat) {
        this.hpStat = hpStat;
    }

    public int getAtkStat() {
        return atkStat;
    }

    public void setAtkStat(int atkStat) {
        this.atkStat = atkStat;
    }

    public int getDefStat() {
        return defStat;
    }

    public void setDefStat(int defStat) {
        this.defStat = defStat;
    }

    public int getSpAtkStat() {
        return spAtkStat;
    }

    public void setSpAtkStat(int spAtkStat) {
        this.spAtkStat = spAtkStat;
    }

    public int getSpDefStat() {
        return spDefStat;
    }

    public void setSpDefStat(int spDefStat) {
        this.spDefStat = spDefStat;
    }

    public int getSpdStat() {
        return spdStat;
    }

    public void setSpdStat(int spdStat) {
        this.spdStat = spdStat;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int calcDmg(Pokemon opponent) {
        return 5;
    }

    public void setNotSwitched(boolean notSwitched) {
        this.notSwitched = notSwitched;
    }

    public boolean battle(Trainer user, Pokemon opponent, Scanner s) {
        boolean cont = true;
        System.out.println(this.getDisplay() + " is battling " + opponent.getDisplay() + "!");
        this.notSwitched = true;
        while(this.getHp() > 0 && opponent.getHp() > 0 && notSwitched) {
            System.out.println("Squirtle's HP: " + this.getHp() + "       " + opponent.getDisplay() + "'s HP: " + opponent.getHp());
            cont = user.getChoice(this, opponent, s);
        }
        if (this.getHp() == 0) {
            cont = user.pokFainted(s);
        }
        return cont;
    }


    public int getDamage(Pokemon opponent) {
        int damage = 5;
        if (false) {
            System.out.println("It was super effective!");
            damage = damage * 2;
        } else if (false) {
            System.out.println("It was not very effective...");
            damage = damage/2;
        }
        return damage;
    }

    public boolean attack(Trainer user, Pokemon opponent, Scanner s) {
        int choice = HelperMethods.getNumber(s, "Moves:\n1) Tackle   2) Tackle\n3) Tackle   4) Tackle\nEnter a number from:", 1, 4);
        int damage = 0;
        if (choice == 1) {
            System.out.println("You have used Tackle!");
            damage = this.getDamage(opponent);
        } else if (choice == 2) {

        } else if (choice == 3) {

        } else if (choice == 4) {

        } else {
            System.out.println("Something has gone horribly horribly wrong... (attack method)");
        }
        opponent.setHp(opponent.getHp() - damage);
        if (opponent.getHp() < 0) {
            opponent.setHp(0);
            return user.battleWon(opponent, this);
        }
        return true;
    }

    public void opponentAttack(Pokemon opponent) {

        Random r = new Random();
        int choice = r.nextInt();
        int damage = opponent.getDamage(this);
        System.out.println(opponent.getDisplay() +" has used Tackle!");
        this.setHp(this.getHp() - damage);
        if (this.getHp() < 0) {
            this.setHp(0);

        }
    }



}
