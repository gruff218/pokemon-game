import pokeapi.bittle.models.games.VersionGroup;
import pokeapi.bittle.models.pokemon.*;

import java.lang.reflect.Array;
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

    private String type;
    private String type2;
    private boolean isDualType;
    private int level;
    private int hp;
    private int dexNum;
    private Scanner s;
    private Move[] moves;
    private boolean notSwitched;
    // A list of base stat values for this Pokémon.
    private ArrayList<PokemonStat> stats;
    HashMap<Move, Integer> possibleMoves = new HashMap<>();

    // A list of details showing types this Pokémon has.
    private ArrayList<PokemonType> types;

    private static Random r = new Random();
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

    public Pokemon(String pokName, int level, Scanner s) {

        PPokemon temp = PPokemon.getByName(pokName);
        this.display = HelperMethods.capitalizeFirstLetter(pokName);
        this.id = pokName;
        this.bHpStat = temp.getStats().get(0).getBaseStat();
        this.bAtkStat = temp.getStats().get(1).getBaseStat();
        this.bDefStat = temp.getStats().get(2).getBaseStat();
        this.bSpAtkStat = temp.getStats().get(3).getBaseStat();
        this.bSpDefStat = temp.getStats().get(4).getBaseStat();
        this.bSpdStat = temp.getStats().get(5).getBaseStat();
        if (temp.getTypes().size() == 1) {
            type = temp.getTypes().get(0).getType().getName();
            this.isDualType = false;
        } else if(temp.getTypes().size() == 2) {
            type2 = temp.getTypes().get(1).getType().getName();
            this.isDualType = true;
        } else {
            System.out.println("What happened here... (Pokemon Constructor)");
        }
        this.s = s;
        this.level = level;
        this.dexNum = temp.getGameIndices().get(9).getGameIndex();
        this.notSwitched = true;

        for (int i = 0; i < temp.getMoves().size(); i++) {
            PokemonMove tempMove= temp.getMoves().get(i);
            ArrayList<PokemonMoveVersion> tempVersion= tempMove.getVersionGroupDetails();
            for (int j = 0; j < tempVersion.size(); j++) {
                if(tempVersion.get(j).getVersionGroup().getName().equals("firered-leafgreen") && tempVersion.get(j).getMoveLearnMethod().getName().equals("level-up")) {
                    possibleMoves.put(new Move(tempMove.getMove().getName()), tempVersion.get(j).getLevelLearnedAt());
                }
            }
        }
        ArrayList<Move> currentPossibleMoves = new ArrayList<>();
        for (Map.Entry<Move, Integer> entry : possibleMoves.entrySet()) {
            if (entry.getValue() <= this.level) {
                currentPossibleMoves.add(entry.getKey());
            }
        }
        for (int i = 0; i < 4; i++) {
            if (currentPossibleMoves.size() == 0) {
                moves[i] = new Move();
                continue;
            }
            int choice = r.nextInt(currentPossibleMoves.size());
            System.out.println(choice);
            moves[i] = currentPossibleMoves.get(choice);
            currentPossibleMoves.remove(choice);
        }



    }

    public void initVariables() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public int getbHpStat() {
        return bHpStat;
    }

    public void setbHpStat(int bHpStat) {
        this.bHpStat = bHpStat;
    }

    public int getbAtkStat() {
        return bAtkStat;
    }

    public void setbAtkStat(int bAtkStat) {
        this.bAtkStat = bAtkStat;
    }

    public int getbDefStat() {
        return bDefStat;
    }

    public void setbDefStat(int bDefStat) {
        this.bDefStat = bDefStat;
    }

    public int getbSpAtkStat() {
        return bSpAtkStat;
    }

    public void setbSpAtkStat(int bSpAtkStat) {
        this.bSpAtkStat = bSpAtkStat;
    }

    public int getbSpDefStat() {
        return bSpDefStat;
    }

    public void setbSpDefStat(int bSpDefStat) {
        this.bSpDefStat = bSpDefStat;
    }

    public int getbSpdStat() {
        return bSpdStat;
    }

    public void setbSpdStat(int bSpdStat) {
        this.bSpdStat = bSpdStat;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public boolean isDualType() {
        return isDualType;
    }

    public void setDualType(boolean dualType) {
        isDualType = dualType;
    }

    public int getDexNum() {
        return dexNum;
    }

    public void setDexNum(int dexNum) {
        this.dexNum = dexNum;
    }

    public Move[] getMoves() {
        return moves;
    }

    public void setMoves(Move[] moves) {
        this.moves = moves;
    }

    public boolean isNotSwitched() {
        return notSwitched;
    }

    public static Double[][] getMultipliers() {
        return multipliers;
    }

    public static void setMultipliers(Double[][] multipliers) {
        Pokemon.multipliers = multipliers;
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
