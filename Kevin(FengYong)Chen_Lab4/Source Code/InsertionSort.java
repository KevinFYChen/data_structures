/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This method takes an array of integers and sort it in ascending order using 
 * a simple insertion sort. The codes in this module were copied from chapter
 * 6.3 of the zybook. 
 * 
 * Insertion sort is done by treating the array like it has 2 parts - an 
 * ordered part and an unordered part. At each insertion, the first element
 * in the unordered part is inserted into the last element of the ordered
 * part. Within the ordered part, the inserted element is compared with
 * the element in front of it. If the element in front of the inserted element
 * is bigger than it, then the 2 is swapped. This process continues until
 * the inserted element reaches a point where the element in front of it
 * is smaller than or equal to it
 * 
 * 
 * @author kevinchen
 */
public class InsertionSort {
      public static void sort(int [] numbers) {
      int i;
      int j;
      int temp; // Temporary variable for swap

      for (i = 1; i < numbers.length; ++i) {
         j = i;
         // Insert numbers[i] into sorted part 
         // stopping once numbers[i] in correct position
         while (j > 0 && numbers[j] < numbers[j - 1]) {

            // Swap numbers[j] and numbers[j - 1]
            temp = numbers[j];
            numbers[j] = numbers[j - 1];
            numbers[j - 1] = temp;
            --j;
         }
      } 
      }
}
