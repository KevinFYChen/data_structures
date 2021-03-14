/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This class calculates the determinant of a matrix using recursion and
 *minors.
 * 
 * @author kevinchen
 */
public class Determinant {

   /**
    * This method takes in a n*n matrix and the row and column position of 
    * an element x that is within the matrix and it returns the minor of that 
    * element in the matrix.
    *
    * This method first creates a (n-1)*(n-1) matrix named subMatrix
    *
    * The method then iterates over the original matrix, element by element
    * using a for loop nested inside another for loop. The outer loop iterates
    * over the rows of the original matrix. The inner loop iterates over the
    * columns of the original matrix.
    *
    * The nested loops goes through each element in the matrix and copies it 
    * to the subMatrix. If the outer or inner loop hits the row or column 
    * containing element x then the respective loop jumps to the next 
    * iteration.
    * 
    * @param matrix original matrix from which the sub matrix is created
    * @param i row number of element x
    * @param j column number of element x
    * @return minor of element x (a sub-matrix)
    */
   public static Matrix minor(Matrix matrix, int i, int j) {
      Matrix subMatrix = new Matrix(matrix.n - 1);
      int n = matrix.n;
      int row = 1;

      // iterates over row of original matrix
      for (int x = 1; x <= n; x++) {
         if (x == i) {
            continue;
         } else {
            subMatrix.newRow();
            //iterates over columns of original matrix
            for (int y = 1; y <= n; y++) {
               if (y == j) {
                  continue;
               } else {
                  Double item = matrix.GetValue(x, y);
                  subMatrix.Append(row, item);
               }

            }
            row++;
         }
      }
      return subMatrix;
   }

   /**
    * This method calculates the determinant of a matrix using recursion. 
    * base case: 
    * when the order of a matrix is 1 (i.e. n=1).
    *
    * recursive case: the function always chooses the first row of the matrix 
    * and for each element on the first row, the function forms the product: 
    * product = Math.pow(-1, (i + j)) * matrix[i][j]
    *           *det(minor(matrix, i, j));
    * then all of the products are added together to get the determinant
    * of the matrix
    *
    * In each product calculation, a recursive call is made with the minor of
    * an element being the parameter that is passed in. Since a minor is one
    * order lower than the original matrix (i.e. n-1), the function will
    * continue to make recursive calls until the order of a matrix becomes one
    * which is the base case.
    * @param matrix represented by a linked list structure
    * @return the determinant of the matrix
    */
   public static double det(Matrix matrix) {
      int n = matrix.n; //order of the matrix
      double sum = 0;

      if (n == 1) {
         return matrix.GetValue(1, 1); // base case
         
      } else {      // recursive case
         //the linked list representation of a matrix starts from 1 not 0
         int i = 1;  
         for (int j = 1; j <= n; j++) {
            double product = Math.pow(-1, (i + j)) * matrix.GetValue(i, j) 
                    * det(minor(matrix, i, j));
            sum = sum + product;
         }
      }
      return sum;
   }
}
