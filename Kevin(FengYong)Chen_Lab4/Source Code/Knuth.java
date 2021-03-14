/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This module calculates the nth value of the Knuth's sequence using recursion
 *
 * @author kevinchen
 */
public class Knuth {

   /**
    * Takes in a parameter n and returns the nth value on the Knuth's sequence
    *
    * @param n an integer, representing the position in the Knuth's sequence
    * that the user wants to know.
    * @return nth value on the Knuth's sequence
    */
   public static int kValue(int n) {
      if (n == 1) // base case
      {
         return 1;
      } else // recursive call
      {
         return 3 * kValue(n - 1) + 1;
      }
   }
}
