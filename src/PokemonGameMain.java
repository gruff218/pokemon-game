

import java.util.*;

public class PokemonGameMain {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Pokemon pok1 = new Pokemon("squirtle", 10, s);
        Pokemon pok2 = new Pokemon("charmander", 10, s);
        Pokemon pok3 = new Pokemon("growlithe", 10, s);
        Pokemon pok4 = new Pokemon("ponyta", 10, s);
        //System.out.println(Arrays.toString(pok2.getMoves()));
        //System.out.println(Arrays.toString(pok1.getStats()));
        Pokemon[] userPokes = new Pokemon[6];
        Pokemon[] oppPokes = new Pokemon[6];
        oppPokes[0] = pok2;
        userPokes[0] = pok1;
        userPokes[1] = pok3;
        oppPokes[1] = pok4;

        User user = new User(userPokes, "Will");
        Trainer opponent = new Trainer(oppPokes, "Trainer Billy");
        user.battle(opponent, s);

    }




}
