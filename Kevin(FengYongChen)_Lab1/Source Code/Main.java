/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This program takes in command line parameter of txt file containing
 * a list of strings and determines whether a string is in any of the 6
 * languages declared in this program.
 * The result output will be saved in a txt file. Name and location of the 
 * output txt file is specified by the user through the command line parameter.
 * Input and output files are specified in the following format through
 * command line:
 * java Main input output
 *
 * @author kevinchen
 */
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
   /**
    * This program is not case sensitive. So the string "aabbb" is the same as  
    * "AABBB" to the program
    * @param args passed in through command line
    */

    public static void main(String[] args) {

        String inputFileName = args[0];
        String outputFileName = args[1];
        String lanPassed = "";

        L1 Lan1 = new L1();
        L2 Lan2 = new L2();
        L3 Lan3 = new L3();
        L4 Lan4 = new L4();
        L5 Lan5 = new L5();
        L6 Lan6 = new L6();

        File inputFile = new File(inputFileName);

        try {
            PrintWriter output = new PrintWriter(outputFileName);
            Scanner input = new Scanner(inputFile);
            while (input.hasNext()) {
               /*
               trims each string so that there is no white space at the head 
               or tail of the string
               */
                String s = input.nextLine().trim();
                // turns all letters into uppercase
                s = s.toUpperCase();
                output.println("test case: " + s);
                if (Lan1.test(s)) {
                    lanPassed = lanPassed + "1" + ", ";
                }
                if (Lan2.test(s)) {
                    lanPassed = lanPassed + "2" + ", ";
                }
                if (Lan3.test(s)) {
                    lanPassed = lanPassed + "3" + ", ";
                }
                if (Lan4.test(s)) {
                    lanPassed = lanPassed + "4" + ", ";
                }
                if (Lan5.test(s)) {
                    lanPassed = lanPassed + "5" + ", ";
                }
                if (Lan6.test(s)) {
                    lanPassed = lanPassed + "6" + ", ";
                }
                if (lanPassed.equals("")) {
                    output.println("the above string is not compliant with any of the languages");
                    output.println();
                } else {
                    lanPassed = lanPassed.substring(0, lanPassed.length() - 2);
                    output.println("the above string is compliant with following language: " + lanPassed);
                    output.println();
                }
                lanPassed = "";

            }
            input.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found");
        }

    }

}
