

import java.util.*;

public class PokemonGameMain {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Pokemon pok1 = new Pokemon("squirtle", 10, s);
        Pokemon pok2 = new Pokemon("charmander", 10, s);
        System.out.println(Arrays.toString(pok2.getMoves()));
        //System.out.println(Arrays.toString(pok1.getStats()));
        Pokemon[] userPokes = new Pokemon[6];
        userPokes[0] = pok1;

        User user = new User(userPokes, "Will");
        user.battle(pok2, s);

    }




}
