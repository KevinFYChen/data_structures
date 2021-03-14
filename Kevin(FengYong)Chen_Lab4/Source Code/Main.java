
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
 * This program takes takes integer data from an input txt file, sort it in
 * ascending order and print the sorted values on an output txt file along
 * with the execution time it took to sort the file. Both the input file and
 * output files are passed in to the main method as command line 
 * parameters.
 * 
 * There are a total of 6 sorting methods, the program will therefore print
 * the results of each sorting method onto a separate output txt file. Hence
 * for each input file that is passed in, the program will generate or
 * print onto 6 output files, one for each sorting method.
 * 
 * The sorting methods used in this program are as follows:
 * Heap Sort
 * Shell sort with sequence number 1 (Knuth increments)
 * Shell sort with sequence number 2 
 * Shell sort with sequence number 3
 * Shell sort with sequence number 4
 * Insertion sort
 * 
 * Sequence number 1, 2, 3 and 4 corresponds to the 1st, 2nd, 3rd and 4th 
 * sequence on the assignment, respectively. As we were asked to create our 
 * own sequence for the 4th sequence, the sequence I've created is shown below:
 * 
 * 1 2 4 8 16 32 64 128 256 512 1024 2048 4096 8192 16384 32768 
 * 
 * It follows the pattern of 2 to the power of i where i starts from 0. 
 * 
 * The program assumes that the input file follows the following format.
 * for a file with N data:
 * 1
 * 2
 * 3
 * 4
 * 5
 * .
 * .
 * .
 * N
 * 
 * There should be no empty lines in the data file. 
 * 
 * Although the format noted above is assumed, if there are more than 1 values
 * in each line like indicated below, the system will still process the file 
 * properly, as long as the values are separated by a space " ".
 * The following format also works
 * 1 2 3 4
 * 5 6 7 8
 * . . . N
 * 
 * 
 *
 *
 * @author kevinchen
 */
public class Main {

   /**
    * The main method scans the input file line by line then concatenate each
    * line into one large string. Each line will be separated by a space " "
    * in the string. The string will then be split into an array using a space
    * " " as the delimiter. Each element in the array will then be parsed into
    * an integer and copied onto a new array of integers. 
    * This array of integers will act as the master copy. Separate copies  
    * of array will then be cloned using the master copy. A total of 6 copies 
    * will be made from the master copy, one for each sorting method. 
    * 
    * Each method will sort a clone of the master copy and print the sorted 
    * results onto an output file. The execution time of the sorting method
    * will also be measured. As sorting happens really quickly, each sorting 
    * method is executed multiple times through a loop and the average is 
    * taken from the total execution time of the method. This gives you a more 
    * accurate result. 
    * 
    * @param args 7 parameters passed in through command line. 1 for input
    *             and 6 parameters for output. 
    */
   public static void main(String[] args) {

      String inputFileName = args[0];
      String outputFileName1 = args[1];
      String outputFileName2 = args[2];
      String outputFileName3 = args[3];
      String outputFileName4 = args[4];
      String outputFileName5 = args[5];
      String outputFileName6 = args[6];

      File inputFile = new File(inputFileName);
      PrintWriter output1 = null;
      PrintWriter output2 = null;
      PrintWriter output3 = null;
      PrintWriter output4 = null;
      PrintWriter output5 = null;
      PrintWriter output6 = null;
      long totalTime = 0;
      double elapseTime = 0;
      double avgTime = 0;
      long startTime;
      long endTime;
      Scanner input = null;
      String file = "";
      int[] increments1 = new int[10]; // sequence 1
      for (int i = 1; i <= increments1.length; i++) {
         increments1[i - 1] = Knuth.kValue(i); // knuth sequence
      }
      //sequence 2
      int[] increments2 = {1, 5, 17, 53, 149, 373, 1123, 3371, 10111, 30341};
      // sequence 3
      int[] increments3 = {1, 10, 30, 60, 120, 360, 1080, 3240, 9720, 29160};
      int[] increments4 = new int[16];
      for (int i = 0; i < increments4.length; i++) {
         increments4[i] = (int) Math.pow(2, i); // sequence 4
      }

      try {
         input = new Scanner(inputFile);
         output1 = new PrintWriter(outputFileName1);
         output2 = new PrintWriter(outputFileName2);
         output3 = new PrintWriter(outputFileName3);
         output4 = new PrintWriter(outputFileName4);
         output5 = new PrintWriter(outputFileName5);
         output6 = new PrintWriter(outputFileName6);

         while (input.hasNextLine()) {
            //process scanned line before concatenation so that there are 
            //no extra space in the concatenated string. In the case where
            //there are more than 1 value on a line, the program will also
            // be able to process the input. 
            String s = input.nextLine().replaceAll("\\s+", " ").trim();
            file = file + " " + s; //concatenating scanned lines
         }
         file = file.trim(); //getting rid of the space at the beginning
         String[] fileArray = file.split(" ");
         int fileSize = fileArray.length;
         //creating an array of integers, this array will act as the master 
         //copy for all other copies of array. 
         int[] array = new int[fileSize]; 
         for (int i = 0; i < fileSize; i++) {
            //parsing elements in the string and copying them into the array
            //of integers
            array[i] = Integer.parseInt(fileArray[i]);
         }

         //cloning the master copy so that the new clone could be used for 
         //Heap sort
         int[] array1 = array.clone();  
         //this segment iterates the heap sort method 100 times and then 
         // calculates the average time. The clone is reset to its original 
         // order at the beginning of each iteration
         for (int i = 1; i <= 100; i++) {
            array1 = array.clone();
            startTime = System.nanoTime();
            HeapSort.sort(array1);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output1.println("Heap sorting file size: " + fileSize);
         output1.println("elapse time (in milliseconds): " + elapseTime);
         elapseTime = 0;
         output1.println("sorted values: ");
         for (int i = 0; i < fileSize; i++) {  
            output1.println(array1[i]);
         }

         // this segment makes a new clone of the master copy and then sorts
         // the new clone using shell sort and the first sequence of the 
         // assignment (i.e the knuth increments). 
         int[] array2 = array.clone();
         output2.println("Shell sorting with sequence # 1, file size: "
                 + fileSize);
         output2.println("sequence # 1: ");
         for (int i = 0; i < increments1.length; i++) {
            output2.print(increments1[i] + " ");
         }
         output2.println();
         // iterating the method 100 times, the clone is reset to its original
         // order at the beginning of each iteration.
         for (int i = 1; i <= 100; i++) { 
            array2 = array.clone();
            startTime = System.nanoTime();
            ShellSort.sort(array2, increments1);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output2.println("elapse time(in milliseconds): " + elapseTime);
         elapseTime = 0;
         output2.println("Sorted values: ");
         for (int i = 0; i < fileSize; i++) {
            output2.println(array2[i]);
         }

         // This segment clones the master copy and sorts the new clone using
         // shell sort and the second sequence on the assignment
         int[] array3 = array.clone();
         output3.println("Shell sorting with sequence # 2, file size: "
                 + fileSize);
         output3.println("sequence # 2: ");
         for (int i = 0; i < increments2.length; i++) {
            output3.print(increments2[i] + " ");
         }
         output3.println();
         // iterating the sort 100 times, the clone is reset to its original
         // order at the beginning of each iteration
         for (int i = 1; i <= 100; i++) {
            array3 = array.clone();
            startTime = System.nanoTime();
            ShellSort.sort(array3, increments2);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output3.println("elapse time(in milliseconds): " + elapseTime);
         elapseTime = 0;
         output3.println("Sorted values");
         for (int i = 0; i < fileSize; i++) {
            output3.println(array3[i]);
         }

         // this segment clones the master copy and then sorts the new clone
         // using shell sort and the third sequence on the assignment. 
         int[] array4 = array.clone();
         output4.println("Shell sorting for sequence # 3, file size: "
                 + fileSize);
         output4.println("sequence # 3: ");
         for (int i = 0; i < increments3.length; i++) {
            output4.print(increments3[i] + " ");
         }
         output4.println();
         // iterating the sort 100 times, the clone is reset to its original
         // order at the beginning of each iteration
         for (int i = 1; i <= 100; i++) {
            array4 = array.clone();
            startTime = System.nanoTime();
            ShellSort.sort(array4, increments3);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output4.println("elapse time(in milliseconds): " + elapseTime);
         elapseTime = 0;
         output4.println("Sorted values: ");
         for (int i = 0; i < fileSize; i++) {
            output4.println(array4[i]);
         }

         // this segment makes a new clone of the master copy and then sorts 
         // the new clone using shell sort and a sequence that I have created
         int[] array5 = array.clone();
         output5.println("Shell sorting with sequence # 4, file size: "
                 + fileSize);
         output5.println("sequence # 4: ");
         for (int i = 0; i < increments4.length; i++) {
            output5.print(increments4[i] + " ");
         }
         output5.println();
         // iterating the sort 100 times, the clone is reset to its original
         // order at the beginning of each iteration
         for (int i = 1; i <= 100; i++) {
            array5 = array.clone();
            startTime = System.nanoTime();
            ShellSort.sort(array5, increments4);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output5.println("elapse time(in milliseconds): " + elapseTime);
         elapseTime = 0;
         output5.println("Sorted values: ");
         for (int i = 0; i < fileSize; i++) {
            output5.println(array5[i]);
         }

         //this segment clones the master copy and then sorts the new clone
         //using a simple insertion sort
         int[] array6 = array.clone();
         // iterating the sort 100 times, the clone is reset to its original
         // order at the beginning of each iteration
         for (int i = 1; i <= 100; i++) {
            array6 = array.clone();
            startTime = System.nanoTime();
            InsertionSort.sort(array6);
            endTime = System.nanoTime();
            long Time = endTime - startTime;
            totalTime = totalTime + Time;
            startTime = 0;
            endTime = 0;
         }
         avgTime = totalTime / 100.0;
         totalTime = 0;
         elapseTime = avgTime / 1000000.0;
         avgTime = 0;
         output6.println("Insertion sort, file size: " + fileSize);
         output6.println("elapse time (in milliseconds): " + elapseTime);
         elapseTime = 0;
         output6.println("sorted values: ");
         for (int i = 0; i < fileSize; i++) {
            output6.println(array6[i]);
         }

      } catch (FileNotFoundException e) {
         output1.println("File not found");
         output2.println("File not found");
         output3.println("File not found");
         output4.println("File not found");
         output5.println("File not found");
         output6.println("File not found");

      }

      input.close();
      output1.close();
      output2.close();
      output3.close();
      output4.close();
      output5.close();
      output6.close();

   }
}
