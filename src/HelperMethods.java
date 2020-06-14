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

    public static double calcTypeMultiplier (String attackingType, Pokemon receiver) {
        double multiplier = multipliers[getTypeNum(attackingType)][getTypeNum(receiver.getType1())];
        if (receiver.isDualType()) {
            multiplier = multiplier * multipliers[getTypeNum(attackingType)][getTypeNum(receiver.getType2())];
        }
        return multiplier;
    }

    public static int getTypeNum(String type) {
        if (type.equals("normal")) {
            return 0;
        } else if (type.equals("fighting")) {
            return 1;
        } else if (type.equals("flying")) {
            return 2;
        } else if (type.equals("poison")) {
            return 3;
        } else if (type.equals("ground")) {
            return 4;
        } else if (type.equals("rock")) {
            return 5;
        } else if (type.equals("bug")) {
            return 6;
        } else if (type.equals("ghost")) {
            return 7;
        } else if (type.equals("steel")) {
            return 8;
        } else if (type.equals("fire")) {
            return 9;
        } else if (type.equals("water")) {
            return 10;
        } else if (type.equals("grass")) {
            return 11;
        } else if (type.equals("electric")) {
            return 12;
        } else if (type.equals("psychic")) {
            return 13;
        } else if (type.equals("ice")) {
            return 14;
        } else if (type.equals("dragon")) {
            return 15;
        } else if (type.equals("dark")) {
            return 16;
        } else if (type.equals("fairy")) {
            return 17;
        } else {
            System.out.println("Something went really wrong (getTypeNum)");
            System.exit(1);
        }
        return -1;
    }

}
