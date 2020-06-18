import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class HelperMethods {
    public static int getNumber(String question, int min, int max) {
        /*while (true) {
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
        }*/
        GameWindow.setLookingForKey(true);
        System.out.println(question + " (" + min + "-" + max + ")? ");
        int number = Integer.MAX_VALUE;
        while (number < min || number > max) {
            number = Integer.MAX_VALUE;
            while (GameComponent.getCurrentKey() == null) {
                //System.out.println(GameComponent.getCurrentKey());
            }
            try {
                number = Character.getNumericValue(GameComponent.getCurrentKey());
            } catch (NullPointerException e) {

            }
            if (number < min || number > max) {
                System.out.println("Your number needs to be between " + min + " and " + max + ".");
                System.out.println(question + " (" + min + "-" + max + ")? ");
                GameComponent.setCurrentKey(null);
            }
        }
        GameComponent.setCurrentKey(null);
        GameWindow.setLookingForKey(false);
        return number;
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

    public static double stageToMultiplier(int stage) {
        if (stage == 0) {
            return 1.0;
        } else if (stage == 1) {
            return 1.33;
        } else if (stage == 2) {
            return 1.66;
        } else if (stage == 3) {
            return 2.0;
        } else if (stage == 4) {
            return 2.33;
        } else if (stage == 5) {
            return 2.66;
        } else if (stage == 6) {
            return 3.0;
        } else if (stage == -1) {
            return 0.75;
        } else if (stage == -2) {
            return 0.66;
        } else if (stage == -3) {
            return 0.5;
        } else if (stage == -4) {
            return 0.43;
        } else if (stage == -5) {
            return 0.36;
        } else if (stage == -6) {
            return 0.33;
        } else {
            System.out.println("Something went really wrong (stageToMultiplier)");
            return 0;
        }
    }

    public static String ailmentToDisplay(String ailment) {
        if (ailment.equals("none") || ailment.equals("unknown")) {
            return "";
        } else if (ailment.equals("sleep")) {
            return "(SLP)";
        } else if (ailment.equals("poison")) {
            return "(POIS)";
        } else if (ailment.equals("burn")) {
            return "(BRN)";
        } else if (ailment.equals("paralysis")) {
            return "(PAR)";
        } else if (ailment.equals("freeze")) {
            return "(FRZ)";
        } else {
            return "something went wrong with ailment id's";
        }
    }

    public static double round(double value, int places) {
        if (places < 0) System.out.println("You can't round something to negative places");;

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double ailmentToCatchRate(String ailment) {
        if (ailment.equals("sleep") || ailment.equals("freeze")) {
            return 2.0;
        } else if (ailment.equals("paralysis") || ailment.equals("poison") || ailment.equals("burn")) {
            return 1.5;
        } else {
            return 1.0;
        }
    }

    public static String shakeNumToDisplay(Pokemon opponent, int num) {
        if (num == 1) {
            return "It shook once!";
        } else if (num == 2) {
            return "It shook twice!";
        } else if (num == 3) {
            return "It shook three times!";
        } else if (num == 4) {
            return opponent.getDisplay() + " was caught!";
        } else {
            System.out.println("Something went wrong... (shakeNumToDisplay())");
            return "";
        }
    }

}
