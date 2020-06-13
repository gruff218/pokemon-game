

import java.util.*;

public class PokemonGameMain {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Pokemon pok1 = new Pokemon("water", 5, "Squirtle", "squirtle", 7, s);
        Pokemon pok2 = new Pokemon("fire", 5, "Charmander", "charmander", 4, s);
        Pokemon[] userPokes = new Pokemon[6];
        userPokes[0] = pok1;

        Trainer user = new Trainer(userPokes);
        user.battle(pok2, s);

    }



}
