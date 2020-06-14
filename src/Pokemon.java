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

    private String type1;
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
            type1 = temp.getTypes().get(0).getType().getName();
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
        this.moves = new Move[4];
        for (int i = 0; i < moves.length; i++) {
            if (currentPossibleMoves.size() == 0) {
                moves[i] = new Move();
            } else {
                int choice = r.nextInt(currentPossibleMoves.size());
                moves[i] = currentPossibleMoves.get(choice);
                currentPossibleMoves.remove(choice);
            }
        }

        this.initVariables();

    }

    public void initVariables() {

        this.atkStat = (int)Math.floor(Math.floor((2 * this.bAtkStat) * this.level / 100 + 5));
        this.defStat = (int)Math.floor(Math.floor((2 * this.bDefStat) * this.level / 100 + 5));
        this.spAtkStat = (int)Math.floor(Math.floor((2 * this.bSpAtkStat) * this.level / 100 + 5));
        this.spDefStat = (int)Math.floor(Math.floor((2 * this.bSpDefStat) * this.level / 100 + 5));
        this.spdStat = (int)Math.floor(Math.floor((2 * this.bSpdStat) * this.level / 100 + 5));
        this.hpStat = (int)Math.floor((2 * bHpStat) * this.level / 100 + this.level + 10);
        this.hp = this.hpStat;
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
            moves[choice - 1].setMove(moveName);
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

    public String getType1() {
        return type1;
    }

    public void setType1(String type) {
        this.type1 = type;
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

    public int calcDmg(Pokemon opponent) {
        return 5;
    }

    public void setNotSwitched(boolean notSwitched) {
        this.notSwitched = notSwitched;
    }

    public int[] getStats() {
        return new int[]{this.hp, this.atkStat, this.defStat, this.spAtkStat, this.spDefStat, this.spdStat};
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


    public int getDamage(Pokemon opponent, int moveUsed) {
        Move move = this.moves[moveUsed];
        if (move.getPower() == 0) {
            return 0;
        }
        int atk = 0;
        int def = 0;
        if (move.getDmgClass().equals("physical")) {
            atk = this.atkStat;
            def = opponent.getDefStat();
        } else if (move.getDmgClass().equals("special")) {
            atk = this.spAtkStat;
            def = opponent.getSpDefStat();
        }
        double modifier = ((double)(r.nextInt(16)+85))/100;
        double typeMultiplier = HelperMethods.calcTypeMultiplier(move.getType(), opponent);
        modifier = modifier * typeMultiplier;
        if (typeMultiplier > 1.999999 && typeMultiplier < 2.0000001) {
            System.out.println("It was super effective!");
        } else if (typeMultiplier < 0.5000001 && typeMultiplier > 0.4999999) {
            System.out.println("It was not very effective...");
        }
        int actingLevel = this.level;
        if (r.nextInt(256) < Math.floor(this.spdStat/2)) {
            actingLevel = actingLevel * 2;
        }
        if (move.getType().equals(this.type1) || move.getType().equals(this.type2)) {
            modifier = modifier * 1.5;
        }
        int damage = (int)Math.floor((((2*actingLevel/5 + 2) * move.getPower() * atk/def)/50 + 2) * modifier);

        return damage;
    }

    public boolean attack(Trainer user, Pokemon opponent, Scanner s) {
        int choice = HelperMethods.getNumber(s, "Moves:\n1) " + this.moves[0].getDisplay() + "   2) " + this.moves[1].getDisplay() +
                "\n3) " + this.moves[2].getDisplay() + "    4) " + this.moves[3].getDisplay() + "\nEnter a number from:", 1, 4);
        int damage = this.getDamage(opponent, choice - 1);
        System.out.println(choice + " " + damage);
        opponent.setHp(opponent.getHp() - damage);
        if (opponent.getHp() < 0) {
            opponent.setHp(0);
            return user.battleWon(opponent, this);
        }
        return true;
    }

    public void opponentAttack(Pokemon opponent) {

        int choice = r.nextInt(4);
        while (!moves[choice].isMove()) {
            choice = r.nextInt(4);
        }
        int damage = opponent.getDamage(this, choice - 1);
        System.out.println(opponent.getDisplay() +" has used " + opponent.getMoves()[choice].getDisplay() + "!");
        this.setHp(this.getHp() - damage);
        if (this.getHp() < 0) {
            this.setHp(0);

        }
    }



}
