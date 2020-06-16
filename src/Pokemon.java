import pokeapi.bittle.models.pokemon.*;

import java.util.*;

public class Pokemon extends GameComponent {

    private String display;
    private String id;

    private int hpStat;
    private int atkStat;
    private int defStat;
    private int spAtkStat;
    private int spDefStat;
    private int spdStat;

    private int atkStage;
    private int defStage;
    private int spAtkStage;
    private int spDefStage;
    private int spdStage;

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
    private Move[] moves;
    private boolean notSwitched;
    private Trainer trainer;
    // A list of base stat values for this Pokémon.
    private ArrayList<PokemonStat> stats;
    private HashMap<Move, Integer> possibleMoves = new HashMap<>();
    private int baseXp;
    private int xp;
    private int xpToNextLevel;
    private String ailment;
    private int movesUntilNoAilment;
    private double burn;

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

    public Pokemon(String pokName, int level) {

        this.constructPokemon(pokName, level);

        ArrayList<Move> currentPossibleMoves = new ArrayList<>();
        for (Map.Entry<Move, Integer> entry : possibleMoves.entrySet()) {
            if (entry.getValue() <= this.level) {
                currentPossibleMoves.add(entry.getKey());
            }
        }
        for (int i = 0; i < moves.length; i++) {
            if (currentPossibleMoves.size() == 0) {
                moves[i] = new Move();
            } else {
                int choice = r.nextInt(currentPossibleMoves.size());
                moves[i] = currentPossibleMoves.get(choice);
                currentPossibleMoves.remove(choice);
            }
        }

        this.initVariables(true);

    }

    public Pokemon(String pokName, int level, Move[] moves) {
        this(pokName, level);

        if (moves.length > 4) {
            System.out.println("A Pokemon was constructed with a moveset of more than 4 moves. This is obviously an issue.");
            System.exit(1);
        }

        for (int i = 0; i < 4; i++) {
            if (i >= moves.length) {
                this.moves[i] = new Move();
                continue;
            }
            if (moves[i] == null) {
                this.moves[i] = new Move();
                continue;
            }
            this.moves[i] = moves[i];
        }
    }

    public void constructPokemon(String pokName, int level) {
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
        } else if (temp.getTypes().size() == 2) {
            type2 = temp.getTypes().get(1).getType().getName();
            this.isDualType = true;
        } else {
            System.out.println("What happened here... (Pokemon Constructor)");
        }
        this.level = level;
        this.dexNum = temp.getGameIndices().get(9).getGameIndex();
        this.notSwitched = true;
        this.trainer = null;
        this.baseXp = temp.getBaseExperience();
        this.xp = 0;

        for (int i = 0; i < temp.getMoves().size(); i++) {
            PokemonMove tempMove = temp.getMoves().get(i);
            ArrayList<PokemonMoveVersion> tempVersion = tempMove.getVersionGroupDetails();
            for (int j = 0; j < tempVersion.size(); j++) {
                if (tempVersion.get(j).getVersionGroup().getName().equals("firered-leafgreen") && tempVersion.get(j).getMoveLearnMethod().getName().equals("level-up")) {
                    possibleMoves.put(new Move(tempMove.getMove().getName()), tempVersion.get(j).getLevelLearnedAt());
                }
            }
        }
        this.moves = new Move[4];
        this.ailment = "none";
        this.burn = 1.0;
    }

    public void battle(User user, Pokemon opponent) {
        this.notSwitched = true;
        while (this.getHp() > 0 && opponent.getHp() > 0 && notSwitched) {
            System.out.println(this.getDisplay() + "'s HP: " + this.getHp() + "       " + opponent.getDisplay() + "'s HP: " + opponent.getHp());
            user.getChoice(this, opponent);
        }
        if (this.getHp() == 0) {
            user.checkPokes();
        }
    }


    public int getDamage(Pokemon opponent, int moveUsed) {
        Move move = this.moves[moveUsed];
        if (move.getPower() == 0) {
            return 0;
        }
        int atk = 0;
        int def = 0;
        if (move.getDmgClass().equals("physical")) {
            atk = (int) (Math.floor(this.atkStat * HelperMethods.stageToMultiplier(this.atkStage)) * this.burn);
            def = (int) Math.floor(opponent.getDefStat() * HelperMethods.stageToMultiplier(opponent.getDefStage()));
        } else if (move.getDmgClass().equals("special")) {
            atk = (int) Math.floor(this.spAtkStat * HelperMethods.stageToMultiplier(this.spAtkStage));
            def = (int) Math.floor(opponent.getSpDefStat() * HelperMethods.stageToMultiplier(opponent.getSpDefStage()));
        }
        double modifier = ((double) (r.nextInt(16) + 85)) / 100;
        double typeMultiplier = HelperMethods.calcTypeMultiplier(move.getType(), opponent);
        modifier = modifier * typeMultiplier;
        if (typeMultiplier > 1.999999 && typeMultiplier < 2.0000001) {
            System.out.println("It was super effective!");
        } else if (typeMultiplier < 0.5000001 && typeMultiplier > 0.4999999) {
            System.out.println("It was not very effective...");
        }
        int actingLevel = this.level;
        if (r.nextInt(256) < Math.floor(this.spdStat) * HelperMethods.stageToMultiplier(this.spdStage) / 2) {
            actingLevel = actingLevel * 2;
        }
        if (move.getType().equals(this.type1) || move.getType().equals(this.type2)) {
            modifier = modifier * 1.5;
        }
        int damage = (int) Math.floor((((2 * actingLevel / 5 + 2) * move.getPower() * atk / def) / 50 + 2) * modifier);

        return damage;
    }

    public void attack(Pokemon opponent) {
        int choice = -1;
        if (this.trainer instanceof User) {
            choice = HelperMethods.getNumber("Moves:\n1) " + this.moves[0].getDisplay() + "   2) " + this.moves[1].getDisplay() +
                    "\n3) " + this.moves[2].getDisplay() + "    4) " + this.moves[3].getDisplay() + "\nEnter a number from:", 1, 4) - 1;
            this.attackChosen(opponent, choice);
        } else {
            choice = r.nextInt(4);
            while (true) {
                if (this.getMoves()[choice].getDisplay().equals("Empty")) {
                    choice = r.nextInt(4);
                } else {
                    break;
                }
            }
            this.attackChosen(opponent, choice);
        }
    }

    public void attackChosen(Pokemon opponent, int choice) {
        if (this.ailment.equals("sleep")) {
            this.movesUntilNoAilment--;
            if (this.movesUntilNoAilment == 0) {
                System.out.println(this.display + " has woken up!");
                this.doAttack(opponent, choice);
            } else {
                System.out.println(this.display + " is fast asleep...");
            }
        } else if (this.ailment.equals("burn")) {
            this.doAttack(opponent, choice);
            System.out.println(this.display + " has taken damage from it's burn!");
            opponent.dealDamage((int)(this.hpStat/16), this);
        }

    }

    public void doAttack(Pokemon opponent, int choice) {
        System.out.println(this.display + " has used " + this.moves[choice].getDisplay() + "!");
        if (this.getMoves()[choice].getDmgClass().equals("status")) {
            this.affectStats(opponent, choice);
        } else {
            this.dealDamage(opponent, choice);
        }
    }

    public boolean affectStats(Pokemon opponent, int moveUsed) {
        Move move = this.moves[moveUsed];
        String changedStat = "";
        int change = 0;
        for (Map.Entry<String, Integer> entry : move.getStatChanges().entrySet()) {
            String addition = this.changeStat(entry.getKey(), entry.getValue(), opponent);
            System.out.print(opponent.getDisplay() + "'s " + HelperMethods.turnIntoDisplay(entry.getKey()) + " stat was ");
            if (entry.getValue() < 0) {
                System.out.print("lowered");
            } else {
                System.out.print("raised");
            }
            System.out.println(addition + "!");
        }
        return true;
    }

    public String changeStat(String stat, int change, Pokemon target) {
        String addition = "";
        if (Math.abs(change) == 2) {
            addition = " significantly";
        }
        if (stat.equals("attack")) {
            target.atkStage += change;
        } else if (stat.equals("defense")) {
            target.defStage += change;
        } else if (stat.equals("special-attack")) {
            target.spAtkStage += change;
        } else if (stat.equals("special-defense")) {
            target.spDefStage += change;
        } else if (stat.equals("speed")) {
            target.spdStage += change;
        }
        //target.printStages();
        return addition;
    }

    public void dealDamage(Pokemon opponent, int choice) {
        int damage = this.getDamage(opponent, choice);
        System.out.println("Damage dealt: " + damage);
        opponent.setHp(opponent.getHp() - damage);
        if (opponent.getHp() < 0) {
            opponent.setHp(0);
            this.trainer.battleWon(opponent);
        }
    }

    public void dealDamage(int damage, Pokemon opponent) {
        System.out.println("Damage dealt: " + damage);
        opponent.setHp(opponent.getHp() - damage);
        if (opponent.getHp() < 0) {
            opponent.setHp(0);
            this.trainer.battleWon(opponent);
        }
    }


    public void initVariables(boolean firstCall) {
        int atk = (int) Math.floor(Math.floor((2 * this.bAtkStat) * this.level / 100 + 5));
        int def = (int) Math.floor(Math.floor((2 * this.bDefStat) * this.level / 100 + 5));
        int spAtk = (int) Math.floor(Math.floor((2 * this.bSpAtkStat) * this.level / 100 + 5));
        int spDef = (int) Math.floor(Math.floor((2 * this.bSpDefStat) * this.level / 100 + 5));
        int spd = (int) Math.floor(Math.floor((2 * this.bSpdStat) * this.level / 100 + 5));
        int hp = (int) Math.floor((2 * bHpStat) * this.level / 100 + this.level + 10);

        if (!firstCall) {
            this.printStatChanges(atk, def, spAtk, spDef, spd, hp);
        }
        this.atkStat = atk;
        this.defStat = def;
        this.spAtkStat = spAtk;
        this.spDefStat = spDef;
        this.spdStat = spd;
        this.hpStat = hp;
        this.hp = this.hpStat;
        this.xpToNextLevel = (this.level + 1) * (this.level + 1) * (this.level + 1) - (this.level) * (this.level) * (this.level);
        this.movesUntilNoAilment = 0;
    }

    public void printStatChanges(int atk, int def, int spAtk, int spDef, int spd, int hp) {
        System.out.println("HP: " + this.hpStat + " --> " + hp);
        System.out.println("Attack: " + this.atkStat + " --> " + atk);
        System.out.println("Defense: " + this.defStat + " --> " + def);
        System.out.println("Special Attack: " + this.spAtkStat + " --> " + spAtk);
        System.out.println("Special Defense: " + this.spDefStat + " --> " + spDef);
        System.out.println("Speed: " + this.spdStat + " --> " + spd);
    }

    public boolean checkIfLeveledUp() {
        if (this.xp >= this.xpToNextLevel) {
            this.xp -= this.xpToNextLevel;
            this.level++;
            this.initVariables(false);
            this.checkIfLearnedMove();
            return true;
        } else {
            return false;
        }
    }

    public void checkIfLearnedMove() {
        for (Map.Entry<Move, Integer> entry : this.possibleMoves.entrySet()) {
            if (this.level == entry.getValue()) {
                this.learn(entry.getKey());
            }
        }
    }

    public boolean learn(String moveName) {
        Move move = new Move(moveName);
        return this.learn(move);
    }

    public boolean learn(Move move) {
        for (int i = 0; i < 4; i++) {
            if (!moves[i].isMove()) {
                moves[i] = move;
                return true;
            }
        }
        int choice = HelperMethods.getNumber(this.display + " already knows four moves. Which move would you like to replace? Enter 0 to stop learning " + move.getDisplay() +
                ".\n1) " + moves[0].getDisplay() + "    2) " + moves[1].getDisplay() +
                "\n3) " + moves[2].getDisplay() + "    4) " + moves[3].getDisplay(), 0, 4);
        if (choice == 0) {
            System.out.println(this.display + " has stopped learning " + move.getDisplay() + "!");
            return false;
        } else {
            System.out.println(this.display + " has forgotten " + moves[choice - 1].getDisplay() + " and learned " + move.getDisplay() + "!");
            moves[choice - 1] = move;
            return true;
        }
    }

    public void resetTempStats() {
        this.atkStage = 0;
        this.defStage = 0;
        this.spAtkStage = 0;
        this.spDefStage = 0;
        this.spdStage = 0;
    }

    public void printStats() {
        System.out.println(this.display + "'s Current Stats:");
        System.out.println("HP: " + this.hpStat + "\nAttack: " + this.atkStat + "\nDefense: " + this.defStat +
                "\nSpecial Attack: " + this.spAtkStat + "\nSpecial Defense: " + this.spDefStat + "\nSpeed: " + this.spdStat);
    }

    public void printBaseStats() {
        System.out.println(this.display + "'s Base Stats:");
        System.out.println("Base HP: " + this.bHpStat + "\nBase Attack: " + this.bAtkStat + "\nBase Defense: " + this.bDefStat +
                "\nBase Special Attack: " + this.bSpAtkStat + "\nBase Special Defense: " + this.bSpDefStat + "\nBase Speed: " + this.bSpdStat);
    }

    public void printStages() {
        System.out.println(this.display + "'s Stages:");
        System.out.println("Attack Stage: " + this.atkStage + "\nDefense Stage: " + this.defStage +
                "\nSpecial Attack Stage: " + this.spAtkStage + "\nSpecial Defense Stage: " + this.spDefStage + "\nSpeed Stage: " + this.spdStage);
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


    public int getAtkStage() {
        return atkStage;
    }

    public void setAtkStage(int atkStage) {
        this.atkStage = atkStage;
    }

    public int getDefStage() {
        return defStage;
    }

    public void setDefStage(int defStage) {
        this.defStage = defStage;
    }

    public int getSpAtkStage() {
        return spAtkStage;
    }

    public void setSpAtkStage(int spAtkStage) {
        this.spAtkStage = spAtkStage;
    }

    public int getSpDefStage() {
        return spDefStage;
    }

    public void setSpDefStage(int spDefStage) {
        this.spDefStage = spDefStage;
    }

    public int getSpdStage() {
        return spdStage;
    }

    public void setSpdStage(int spdStage) {
        this.spdStage = spdStage;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public HashMap<Move, Integer> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(HashMap<Move, Integer> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id='" + id + '\'' +
                ", hpStat=" + hpStat +
                ", atkStat=" + atkStat +
                ", defStat=" + defStat +
                ", spAtkStat=" + spAtkStat +
                ", spDefStat=" + spDefStat +
                ", spdStat=" + spdStat +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", isDualType=" + isDualType +
                ", level=" + level +
                ", hp=" + hp +
                ", moves=" + Arrays.toString(moves) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return hpStat == pokemon.hpStat &&
                atkStat == pokemon.atkStat &&
                defStat == pokemon.defStat &&
                spAtkStat == pokemon.spAtkStat &&
                spDefStat == pokemon.spDefStat &&
                spdStat == pokemon.spdStat &&
                bHpStat == pokemon.bHpStat &&
                bAtkStat == pokemon.bAtkStat &&
                bDefStat == pokemon.bDefStat &&
                bSpAtkStat == pokemon.bSpAtkStat &&
                bSpDefStat == pokemon.bSpDefStat &&
                bSpdStat == pokemon.bSpdStat &&
                isDualType == pokemon.isDualType &&
                level == pokemon.level &&
                hp == pokemon.hp &&
                dexNum == pokemon.dexNum &&
                notSwitched == pokemon.notSwitched &&
                Objects.equals(display, pokemon.display) &&
                Objects.equals(id, pokemon.id) &&
                Objects.equals(type1, pokemon.type1) &&
                Objects.equals(type2, pokemon.type2) &&
                Arrays.equals(moves, pokemon.moves) &&
                Objects.equals(possibleMoves, pokemon.possibleMoves);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(display, id, hpStat, atkStat, defStat, spAtkStat, spDefStat, spdStat, bHpStat, bAtkStat, bDefStat, bSpAtkStat, bSpDefStat, bSpdStat, type1, type2, isDualType, level, hp, dexNum, notSwitched, possibleMoves);
        result = 31 * result + Arrays.hashCode(moves);
        return result;
    }

    public int getBaseXp() {
        return baseXp;
    }

    public void setBaseXp(int baseXp) {
        this.baseXp = baseXp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int xp) {
        this.xp += xp;
        System.out.println(this.display + " has gained " + xp + "XP! (" + this.xp + "/" + this.xpToNextLevel + ")");
        this.checkIfLeveledUp();
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }
}
