package Manager;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import IO.Input;
import IO.Log;

import java.awt.*;

public class Main {
    public static void main(String[] ignoredArgs) {

        JFrame frame = new JFrame("HotelSimulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane(Log.getTextPane());

        JButton button = new JButton("Enter");

        button.addActionListener(e -> Input.setInput(true));

        frame.getRootPane().setDefaultButton(button);
        button.requestFocus();

        frame.getContentPane().add(button, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(Input.getTextField(), BorderLayout.SOUTH);

        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        SimulationManager simulationManager = new SimulationManager();
//        simulationManager.simulate(31);
        simulationManager.mainMenu();

    }
}