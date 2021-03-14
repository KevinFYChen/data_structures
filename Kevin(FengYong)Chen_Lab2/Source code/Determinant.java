/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This class calculates the determinant of a matrix using recursion and
 *minors.
 *
 *
 *
 * @author kevinchen
 */
public class Determinant {

   /**
    * This method takes in a n*n matrix and the row and column position of an 
    * element x that is within the matrix and it returns the minor of that 
    * element in the matrix.
    *
    * This method first creates a (n-1)*(n-1) matrix named temp
    *
    * The method then iterates over the original matrix, element by element
    * using a for loop nested inside another for loop. The outer loop iterates
    * over the rows of the original matrix. The inner loop iterates over the
    * columns of the original matrix.
    *
    * The nested loops goes through each element in the matrix and copies it 
    * to the temp matrix. If the outer or inner loop hits the row or column 
    * containing element x then the respective loop jumps to the next 
    * iteration.
    *
    * @param matrix original matrix that is passed in from the det method
    * @param n order of the original n*n matrix
    * @param i row number of element x
    * @param j column number of element x
    * @return minor of element x (a sub-matrix)
    */
   public static double[][] minor(double[][] matrix, int n, int i, int j) {
      double[][] temp = new double[n - 1][n - 1];
      int col = 0;
      int row = 0;

      // iterates over row of original matrix
      for (int x = 0; x < n; x++) {
         if (x == i) {
            continue;
         } else {
            //iterates over columns of original matrix
            for (int y = 0; y < n; y++) {
               if (y == j) {
                  continue;
               } else {
                  temp[row][col] = matrix[x][y];
                  col++;
               }
            }
            col = 0; // reset column number to 0 for the next row
            row++;
         }
      }
      return temp;
   }

   /**
    * This method calculates the determinant of a matrix. 
    * base case: 
    * when the order of a matrix is 1 (i.e. n=1).
    *
    * recursive case: the function always chooses the first row of the matrix 
    * and for each element in the first row, the function forms the product: 
    * product = Math.pow(-1, ((i + 1) + (j + 1))) * matrix[i][j]
    *           *det(minor(matrix, n, i, j), n - 1);
    * then all of the products are added together to get the determinant
    * of the matrix
    *
    * In each product calculation, a recursive call is made with the minor of
    * an element being the parameter that is passed in. Since a minor is one
    * order lower than the original matrix (i.e. n-1), the function will
    * continue to make recursive calls until the order of a matrix becomes one
    * which is the base case.
    *
    *
    * @param matrix represented by a 2D array
    * @param n order of the matrix
    * @return determinant of the matrix. It is a real number.
    */
   public static double det(double[][] matrix, int n) {
      double sum = 0;
      if (n == 1) {
         return matrix[0][0];
      } else {
         int i = 0;
         for (int j = 0; j < n; j++) {
            //since we are using a 2D array to represent a matrix, the
            //row and column index, represented by i and j respectively, 
            //starts from 0. So to convert them to matrix indices,
            //I added 1 to both i and j
            double product = Math.pow(-1, ((i + 1) + (j + 1))) * matrix[i][j]
                    * det(minor(matrix, n, i, j), n - 1);
            sum = sum + product;
         }

      }
      return sum; // returns the determinant
   }

}
