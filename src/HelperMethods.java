import java.util.*;

public class HelperMethods {
    public static int getNumber(Scanner s, String question, int min, int max) {
        while (true) {
            System.out.print(question + " (" + min + "-" + max + ")? ");


            if (!s.hasNextInt()) {
                System.out.println("You must enter an *integer* between " + min + " and " + max + ".");
                s.nextLine();
            } else {
                int num = s.nextInt();
                if (num < min || num > max) {
                    System.out.println("Your number needs to be between " + min + " and " + max + ".");
                } else {
                    s.nextLine();
                    return num;
                }
            }
        }
    }

    public static String turnIntoDisplay(String id) {
        StringBuilder sb = new StringBuilder();
        String[] arr = id.split("-");
        for (int i = 0; i < arr.length; i++) {
            sb.append(capitalizeFirstLetter(arr[i]));
            if (i != arr.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public static String capitalizeFirstLetter(String word) {
        String[] letters = word.split("");
        word = letters[0].toUpperCase();
        for (int i = 1; i < letters.length; i++) {
            word = word + letters[i];
        }
        return word;
    }
}
