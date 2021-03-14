/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinchen
 */
/**
 * This program takes matrices from an input text file and prints out the
 * matrices onto an output text file along with the determinants of each matrix
 *
 * Both the location of input and output files are pass into the program
 * as command line parameters.
 *
 * This program is written on the assumption that the data in the input file
 * follows the following format:
 *
 * n
 * matrix
 *
 * with n being the order of an n*n matrix followed the actual matrix with no
 * empty line in between n and the matrix.
 *
 * This program was also written on the assumption that the values in a matrix
 * are separated from one another by one space (i.e. " ") as shown in the
 * sample inputs.
 *
 * As such, any format that does not follow the format indicated above will
 * be treated as an error. It should be noted here that if a line contains
 * multiple spaces between values (ex. "1 2        3"), then the program treat
 * it as an error and the program will be terminated.
 *
 * The order of the matrix is first detected and saved. A 2D array is then
 * initialized with n rows and n columns.
 *
 * This program then scans the input file line by line and converts each line
 * into a string. Each string is then split into an array of strings using a 
 * space " " as the delimiter for the split. Each element in the array is 
 * then added to its corresponding row and column number in the 2D array. 
 * 
 * After all of the values in a matrix are saved into a 2D array, it will  
 * be printed onto an output test file. The program will then call the det 
 * function in the determinant class to determine the determinant of this 2D 
 * array. The value of the determinant will also be printed onto the output 
 * file. 
 * 
 * various errors were also handled in the program, including:
 * - input file containing empty lines
 * - matrix is oversized or undersized (i.e. number of rows or columns does 
 * not match n)
 * - non-numeric data
 *
 *
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;

public class Main {

   public static void main(String[] args) {
      String inputFileName = args[0];
      String outputFileName = args[1];
      int n = 0;
      int nextN = 0;
      PrintWriter output = null;
      Scanner input = null;
      String Line = "";

      File inputFile = new File(inputFileName);

      try {
         output = new PrintWriter(outputFileName);
         input = new Scanner(inputFile);
         while (input.hasNextLine()) {

            // registering the value of n at the beginning
            if (nextN == 0) {
               String x = input.nextLine().trim();
               Line = x;
               nextN = Integer.parseInt(x);
               continue;
              // if value of n is already known then this branch constructs
              // a matrix using a 2D array
            } else {  
               n = nextN;
               output.println("n = " + n);
               double[][] matrix = new double[n][n];
               int i = 0;
               // this while loop is used to construct the matrix
               while (input.hasNextLine()) {
                  String s = input.nextLine().trim();
                  Line = s;
                  String[] sArray = s.split(" ");
                  //when the while loop hits the next n, it
                  // saves the value of the next n so that it could be used 
                  // to construct the next matrix. Various error handling
                  // are also written in this if statement
                  if (sArray.length == 1 && n != 1) {

                     //error handling, i represents the number of rows in the
                     // matrix, if it does not equal to n, then report error
                     if (i != n) { 
                        for (int x = 0; x < i; x++) {
                           for (int y = 0; y < matrix[x].length; y++) {
                              output.printf("%5.1f", matrix[x][y]);
                           }
                           output.println();
                        }
                        output.println("number of rows are less than n, "
                                + "n= " + n + ", program terminated");
                        output.close();
                        input.close();
                        return;
                     }
                     try {
                        // save the value of the next n
                        nextN = Integer.parseInt(s); 
                        // error handling, if n is not an integer
                     } catch (IllegalArgumentException e) {
                        for (int x = 0; x < i; x++) {
                           for (int y = 0; y < matrix[x].length; y++) {
                              output.printf("%5.1f", matrix[x][y]);
                           }
                           output.println();
                        }
                        output.println("error line: " + s);
                        output.println("the above data contains invalid values, "
                                + " please make sure there are no "
                                + "empty lines or non-integer values"
                                + ", program terminated");
                        output.close();
                        input.close();
                        return;

                     }
                     break; // the loop breaks when it hits the next n

                  }
                  //error handling, if the number of elements in a row does 
                  // not match n, then this if statement is executed
                  if (sArray.length != n) {

                     for (int x = 0; x < i; x++) {
                        for (int y = 0; y < matrix[x].length; y++) {
                           output.printf("%5.1f", matrix[x][y]);
                        }
                        output.println();
                     }
                     output.println("error line: " + s);
                     output.println("the number of columns indicated above "
                             + "does not match with order n= " + n
                             + ", program terminated");
                     output.close();
                     input.close();
                     return;

                  }
                  // constructs the matrix
                  for (int j = 0; j < sArray.length; j++) {
                     matrix[i][j] = Double.parseDouble(sArray[j]);

                  }

                  // if order is 1, then after constructing the single element
                  // matrix, the order number is reset to 0
                  if (n == 1) {
                     nextN = 0;

                     break;
                  }
                  
                  i++; 

               }
                  //error handling, if there is no more line left in the 
                  // input file but the number of rows in the matrix does not 
                  // match n then this statement will be executed.
               if (!input.hasNextLine() && i != n) {

                  for (int x = 0; x < i; x++) {
                     for (int y = 0; y < matrix[x].length; y++) {
                        output.printf("%5.1f", matrix[x][y]);
                     }
                     output.println();
                  }
                  output.println("number of rows are less than n, "
                          + "n= " + n + ", program terminated");
                  output.close();
                  input.close();
                  return;
               }

               output.println("matrix: ");
               
               // prints the matrix onto the output file
               for (int x = 0; x < matrix.length; x++) {

                  for (int y = 0; y < matrix[x].length; y++) {
                     output.printf("%5.1f", matrix[x][y]);

                  }
                  output.println();
               }
               //calculating the determinant
               double determinant = Determinant.det(matrix, n);
               output.println("The determinant of the above matrix is: "
                       + determinant);
               output.println();

            }

         }

      } catch (FileNotFoundException e) {
         output.println("The input or output file you have entered is not "
                 + "found");
      } catch (IllegalArgumentException e) {
         output.println("error line: " + Line);
         output.println("input data are not in the correct format, "
                 + "please make sure that there are no empty lines"
                 + " or non-integer values");
      } catch (ArrayIndexOutOfBoundsException e) {
         output.println("error line: " + Line);
         output.println("number of rows exceeds n, n= "
                 + n + ", program terminated");
      }

      output.close();
      input.close();

   }

}
