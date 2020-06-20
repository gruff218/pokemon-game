

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class CheatCodeWindow extends JFrame implements KeyListener, ActionListener {

    private String name;
    private User user;
    JTextArea displayArea;
    private static boolean lookingForCheatKey;


    public CheatCodeWindow(String name, User user) {
        this.name = name;
        this.user = user;
    }

    /*private boolean expectingSecondCharacter = false;
    private enum Command{
        USE,
        DROP
    }
    private Command secondCharacterCommand;*/

    public static void makeWindow(User user) {


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

    }

    private static void createAndShowGUI(User user) {
        CheatCodeWindow frame = new CheatCodeWindow("Cheat Code Window", user);
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
        displayArea.setText("Cheat Code Window");
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("Key Typed (Cheat Window)");
        int id = e.getID();
        if (id == KeyEvent.KEY_TYPED) {
            char c = e.getKeyChar();


            GameWindow.setCurrentCheatKey(c);

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


}
