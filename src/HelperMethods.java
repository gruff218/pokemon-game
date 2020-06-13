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
}
