/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This module takes an array of integers and sort it in ascending order using
 * shell sort. The general concept of the algorithm came from chapter 6.6
 * of the zybook. But modifications were made to meet the requirements of this
 * assignment. 
 * 
 * This module contains 2 methods - insertion sort and sort
 * The insertion sort method is a modified version of the simple insertion
 * sort.
 * The sort method performs the actual shell sort. Shell sort breaks a main 
 * file down into a number of subfiles and then performs insertion sort within
 * each subfile. The number of subfiles is determined by the gap value which 
 * has to be given by the user. 
 * Shell sort is done through a number of passings. In each 
 * passing, the number of subfiles is less than the number of subfiles in the 
 * previous passing (i.e. the subfile becomes bigger in each subsequent 
 * passing) until eventually there is only 1 subfile (i.e. subfile equals the 
 * main file) and an insertion sort is performed within that 1 subfile. 
 * By doing so, shell sort creates an optimal environment for insertion sort 
 * to perform efficiently. 
 *
 * 
 * @author kevinchen
 */
public class ShellSort {

   /**
    * This method is a modified version of the simple insertion sort. Instead
    * of comparing the element that is just left of the inserted element, this
    * method compares the inserted element with the element that is one gap 
    * value before the inserted element. In other words, the method compares 
    * the inserted element with the element that is just left of it in its
    * subfile. If the compared element is greater than the inserted element, 
    * the method will swap the two. The method will continue to compare and
    * swap the inserted element with the element in front of it in the subfile
    * until the element in front of the inserted element is smaller than or 
    * equal to it. 
    * 
    * @param array array of integer, this is the main file. 
    * @param startIndex starting point of the subfile within the array
    * @param gap gap determines the distance between each element in the
    *             subfile. 
    */
   public static void Insertion(int[] array, int startIndex, int gap) {
      int i;
      int j;
      int N = array.length; 
      int temp;

      for (i = startIndex + gap; i < N; i += gap) {
         j = i;
         while (j - gap >= startIndex && array[j] < array[j - gap]) {
            temp = array[j];
            array[j] = array[j - gap];
            array[j - gap] = temp;
            j -= gap; // updating the location of the insert element
         }

      }

   }
   
   /**
    * This method performs the shell sort. It takes in an array of integers 
    * and an array of increments which contains the gap values that will be 
    * used by the method. The method determines the first value to be used
    * in the increments array by looking for the first value that is larger 
    * than the length of the array of integers (i.e. larger than the file size). 
    * After locating the first value that is larger than the file size, the
    * method moves back 2 increments and use that as the starting gap value 
    * for the first passing. After insertion is performed on all of the 
    * subfiles in the first passing, the method then chooses the increment
    * right in front of the previous increment and use that as the new gap 
    * value for the second passing. The method continues to do this until
    * it reaches index 0 of the increments array. 
    *
    * @param array array of integers which we want to sort
    * @param increments array of gap values which we use to sort the array of
    *                   integers. 
    */

   public static void sort(int[] array, int[] increments) {
      int N = array.length;
      int numIncrements = increments.length;
      int startPoint = -1;

      // the segment determines the starting gap value that we will use for 
      // the first passing of the shell sort
      for (int i = 0; i < numIncrements; i++) {
         if (increments[i] > N) {
            startPoint = i - 2;
            break;
         }
      }
      if (startPoint == -1) {
         System.out.println("There is no increment that is larger than file "
                 + "size.");
      }

      //this segment performs the actual shell sort. 
      for (int i = startPoint; i >= 0; i--) {
         int gapValue = increments[i];
         for (int j = 0; j < gapValue; j++) {
            Insertion(array, j, gapValue);
         }
      }
   }
}
