

import java.util.*;

public class PokemonGameMain {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Pokemon pok1 = new Pokemon("squirtle", 5, s);
        Pokemon pok2 = new Pokemon("charmander", 5, s);
        System.out.println(pok1.getMoves().toString());
        Pokemon[] userPokes = new Pokemon[6];
        userPokes[0] = pok1;

        Trainer user = new Trainer(userPokes);
        user.battle(pok2, s);

    }



}
