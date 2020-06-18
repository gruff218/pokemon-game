

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class GameWindow extends JFrame implements KeyListener, ActionListener {

    private String name;
    private User user;
    JTextArea displayArea;
    private static boolean lookingForKey;
    private static ArrayList<Character> keyRequests = new ArrayList<>();

    public GameWindow(String name, User user) {
        this.name = name;
        this.user = user;
    }

    /*private boolean expectingSecondCharacter = false;
    private enum Command{
        USE,
        DROP
    }
    private Command secondCharacterCommand;*/

    public static void main(String[] args) {
        Pokemon pok1 = new Pokemon("squirtle", 12);
        Pokemon pok2 = new Pokemon("charmander", 10);
        Pokemon pok3 = new Pokemon("pikachu", 100, new Move[]{new Move("thunder-wave"),
                new Move("will-o-wisp"),
                new Move("hypnosis"),
                new Move("powder-snow")});
        Pokemon pok4 = new Pokemon("ponyta", 10);
        //System.out.println(Arrays.toString(pok2.getMoves()));
        //System.out.println(Arrays.toString(pok1.getStats()));
        Pokemon[] userPokes = new Pokemon[6];
        Pokemon[] oppPokes = new Pokemon[6];
        oppPokes[0] = pok2;
        userPokes[0] = pok1;
        userPokes[1] = pok3;
        oppPokes[1] = pok4;

        User user = new User(userPokes, "Will");
        user.addBalls(new PokeBall(), 5);
        Trainer opponent = new Trainer(oppPokes, "Trainer Billy");

        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI(user);
                    }
                });
        user.battle(opponent);

    }

    private static void createAndShowGUI(User user) {
        GameWindow frame = new GameWindow("PokemonMap", user);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        frame.addComponentsToPane();


        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void addComponentsToPane() {
        displayArea = new JTextArea(20, 85);
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setMargin(new Insets(15, 15, 0, 15));
        Font font = new Font("monospaced", Font.PLAIN, 15);
        displayArea.setFont(font);
        displayArea.addKeyListener(this);
        getContentPane().add(displayArea, BorderLayout.CENTER);
        setResizable(false);
    }

    public void keyTyped(KeyEvent e) {
        int id = e.getID();
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();
            if (c == '1') {
                keyRequests.set(keyRequests.size() - 1, '1');
                System.out.println(keyRequests);
            } else if (c == '2') {
                keyRequests.set(keyRequests.size() - 1, '2');
            } else if (c == '3') {
                keyRequests.set(keyRequests.size() - 1, '3');
            } else if (c == '4') {
                keyRequests.set(keyRequests.size() - 1, '4');
            } else if (c == '5') {
                keyRequests.set(keyRequests.size() - 1, '5');
            } else if (c == '6') {
                keyRequests.set(keyRequests.size() - 1, '6');
            }

            if (c == 'L') {
                this.user.getCurrentPok().addXp(this.user.getCurrentPok().getXpToNextLevel());
            }
        }
    }

    /**
     * Handle the key pressed event from the text field.
     */
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Handle the key released event from the text field.
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Handle the button click.
     */
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public JTextArea getDisplayArea() {
        return displayArea;
    }

    public void setDisplayArea(JTextArea displayArea) {
        this.displayArea = displayArea;
    }

    public static boolean getLookingForKey() {
        return GameWindow.lookingForKey;
    }

    public static void setLookingForKey(boolean lookingForKey) {
        GameWindow.lookingForKey = lookingForKey;
    }

    public synchronized static ArrayList<Character> getKeyRequests() {
        return keyRequests;
    }

    public synchronized static void setKeyRequests(ArrayList<Character> keyRequests) {
        GameWindow.keyRequests = keyRequests;
    }
}
