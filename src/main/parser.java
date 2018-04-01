package main;

import javafx.stage.WindowEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class parser {


    public static void main(String[] args) throws IOException {
        /*System.out.print("Enter file name: ");
        Scanner scanner = new Scanner(System.in);
        String userFile = scanner.nextLine();

        File file = new File("C:\\Users\\Sean\\IdeaProjects\\Computer-Emulator\\src\\main\\", userFile);
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
        */
        JFrame myFrame = new JFrame("Pick File");
        myFrame.setSize(500, 500);
        myFrame.setLayout(new GridLayout(3, 1));
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        myFrame.setVisible(true);
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(myFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String temp = chooser.getSelectedFile().getName();
            System.out.print(temp);
        }
    }
}