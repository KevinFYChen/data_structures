
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileNotFoundException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 ** This program takes matrices from an input text file and prints out the
 * matrices onto an output text file along with the determinants of each
 * matrix
 *
 * Both the location of input and output files are pass into the program as
 * command line parameters.
 *
 * This program is written based on the assumption that the data in the input 
 * file follows the following format:
 *
 * n
 * matrix
 *
 * with n being the order of an n*n matrix followed by the actual matrix with 
 * no empty lines in between n and the matrix. Any format that does not
 * follow this format will be treated as an error. 
 *
 * This program was also written based on the assumption that the values in a 
 * matrix are separated from one another by one space (i.e. " "). However, 
 * extra spaces between values will be ignored by the program. In other words, 
 * if there is a line that contains multiple spaces (i.e. "1 2      3"), the 
 * program will interpret it as (i.e. "1 2 3") and will not report an error.
 *
 * The order of the matrix is first detected and saved as an integer. 
 * A linked list structure is then initialized so that it can be used to store 
 * the values in a matrix. Details on the linked list structure will be 
 * explained in the introductory comment block of the matrix class.
 *
 * This program then scans the input file line by line and converts each line
 * into a string. Each string is then split into an array of strings using a
 * space " " as the delimiter for the split. Please note again that 
 * extra spaces will be ignored by the program. Each element in the array is 
 * then added to its corresponding row and column number in the linked list.
 *
 * After all of the values in a matrix are stored in a linked list, it will 
 * be printed onto an output text file. The program will then call the det
 * function in the determinant class to determine the determinant of this 
 * matrix. The value of the determinant will also be printed onto the output
 * file.
 * 
 * Besides from the main method, the main class also has a "isValidInput" 
 * method that tests whether all of the values on an input line are numeric.
 *
 * various errors were also handled in the program, including: 
 * - input file containing empty lines 
 * - matrix is oversized or undersized (i.e. number of rows or columns does 
 * not match n) 
 * - non-numeric data
 *
 * @author kevinchen
 */
public class Main {

   public static void main(String[] args) {
      String inputFileName = args[0];
      String outputFileName = args[1];
      File inputFile = new File(inputFileName);
      PrintWriter output = null;
      Scanner input = null;
      int nextN = -1;
      int n = 0;

      try {
         output = new PrintWriter(outputFileName);
         input = new Scanner(inputFile);
         while (input.hasNextLine()) {
            
            // registering the value of the first n at the beginning
            if (nextN == -1) {
               String s = input.nextLine().replaceAll("\\s+", " ").trim();
               if (isValidInput(s)) {
                  nextN = Integer.parseInt(s);
               } else {
                  output.println("error line: " + s);
                  output.println("invalid input, please make sure that there are "
                          + "no empty lines or non-numeric values. program "
                          + "terminated.");
                  input.close();
                  output.close();
                  return;
               }
               continue;
               
              // if value of n is already known then this branch constructs
              // a matrix using a linked list structure
            } else {
               int i = 0;
               n = nextN;
               output.println("n = " + n);
               Matrix matrix = new Matrix(n);
               
               // this while loop is used to construct the matrix
               while (input.hasNextLine()) {
                  i++;
                  // formatting the input line, getting rid of extra spaces
                  // between values and trim the space before and after the
                  // string
                  String s = input.nextLine().replaceAll("\\s+", " ").trim();
                  String[] sArray;
                  //isValidInput tests whether all of the values in the string 
                  //are numeric
                  if (isValidInput(s)) {
                     sArray = s.split(" ");
                  } else {
                     matrix.printMatrix(output);
                     output.println();
                     output.println("error line: " + s);
                     output.println("invalid input, please make sure that "
                             + "there are no empty lines or non-numeric values. "
                             + "program terminated.");
                     input.close();
                     output.close();
                     return;
                  }
                  // when the while loop hits the next n, it
                  // saves the value of the next n so that it could be used 
                  // to construct the next matrix. 
                  if (sArray.length == 1 && n != 1) {
                     
                     //error handling, i-1 represents the number of rows in 
                     //the current matrix, if it does not equal to n, then 
                     //report error
                     if (i - 1 != n) {
                        matrix.printMatrix(output);
                        output.println("the number of rows does not match with"
                                + " the order of the matrix n = " + n + ". "
                                + "program terminated.");
                        input.close();
                        output.close();
                        return;
                     }
                     nextN = Integer.parseInt(s);
                     break; // breaks out of the current while loop when the 
                            // next n is detected
                  }

                  //initiating a new row for the linked structure, this has to
                  //be done due to the way the linked structure was designed.
                  matrix.newRow();
                  
                  // constructing the matrix using a linked list
                  for (int j = 0; j < sArray.length; j++) {
                     matrix.Append(i, Double.parseDouble(sArray[j]));
                  }

                  //testing if the number of elements in the row matches
                  //with the order of the matrix, n. If not, report error
                  if (!matrix.isValid()) {
                     matrix.printMatrix(output);
                     output.println("The number of columns does not match with "
                             + "the order of the matrix n = " + n
                             + ". Program terminated.");
                     input.close();
                     output.close();
                     return;
                  }

                  // if order is 1, then after constructing the single element
                  // matrix, the order number is reset to -1
                  if (n == 1) {
                     nextN = -1;
                     break; //breaks out of the current while loop 
                  }
               }
               
               // error handling, if there are no more lines left in the 
               // input file but the number of rows in the matrix does not 
               // match n then this statement will be executed.
               if (!input.hasNextLine() && i != n &&n==nextN) {
                  matrix.printMatrix(output);
                  output.println("the number of rows does not match with"
                          + " the order of the matrix n = " + n + ". program"
                          + " terminated.");
                  input.close();
                  output.close();
                  return;

               }

               //prints the matrix onto the output text file
               output.println("matrix: ");
               matrix.printMatrix(output);
               //calculating the determinant of the matrix
               output.println("The determinant of the above matrix is "
                       + Determinant.det(matrix));
               output.println();

            }
         }

      } catch (FileNotFoundException e) {
         output.println("The input or output file you have entered is not "
                 + "found");
      } catch (IllegalArgumentException e) {
         output.println("Illegal input for the order of matrix, n.");
      }
      output.close();
      input.close();

   }

   /**
    * 
    * This method tests to see if all of the values in a string is 
    * numeric and it returns a boolean value. 
    * 
    * If the string only contains numeric values, then the method returns true. 
    * Else, the method will return false.
    * 
    * @param s input string that is passed in from the main method
    * @return boolean true if all values are numeric, false otherwise. 
    */
   public static boolean isValidInput(String s) {
      //split the input string into an array using " " as the delimiter
      String[] sArray = s.split(" ");
      if (sArray.length == 0) {
         return false;
      }
      for (int i = 0; i < sArray.length; i++) {
         try {
            Double.parseDouble(sArray[i]);
         } catch (IllegalArgumentException e) {
            return false;
         }

      }
      return true;
   }
}
