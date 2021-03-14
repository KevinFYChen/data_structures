/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This module implements a matrix using a linked list. The linked structure
 * is made with 2 kinds of nodes:
 * 1) multi-linked header nodes,
 * 2) singly linked value nodes
 *
 * The head nodes are used as indices for the rows of a matrix.
 * The value nodes contain actual values of a matrix. Each row is essentially
 * a singly linked list with a header node that marks the beginning of the
 * list and the header nodes are linked together.
 * This design was adopted for its simplicity. Details on the reasons
 * for this design will be discussed in the analysis section.
 * 
 * The following methods were created for the linked list structure. 
 * 
 * newRow
 * This method creates a new row by creating a new header node. 
 * When creating a matrix, a new header node needs to be created every time
 * a new row is created so that the singly linked nodes in that row could be 
 * attached to it.
 *
 * Append
 * Since this lab does not require the program to insert a new value into
 * the middle of a list, an insert method was not developed. Instead, an
 * append method was developed which adds new value to the end of a row.
 *
 * GetValue
 * In order to create minors of a matrix, the GetValue method was created so
 * that values in a matrix could be accessed and copied to a sub-matrix. 
 * 
 * printMatrix
 * The printMatrix method was created to simplify coding in the main method so
 * that I could re-use the code every time I need to print out the matrix
 * 
 * isValid
 * Since the number of elements in a row is not limited by a linked structure. 
 * The isValid method was created for error handling purposes, it was created
 * to check if the number of elements in a row matches with the order of the 
 * matrix, n. 
 * 
 * Replace
 * The Replace method replaces an old values in the matrix with a new value 
 * specified by the user. It was created for debugging purposes. I wanted to 
 * change the values in the matrix and test to see if the det method 
 * in the determinant class would calculate its determinant calculate. 
 * 
 * Pointer
 * The pointer method points to a location specified by the user. It was 
 * created so that the same codes could be re-used in the GetValue method and 
 * the Replace method. 
 * 
 * PrintHeader
 * The PrintHeader method prints the row number of each header node. It was 
 * created for debugging purposes. I wanted to make sure that the number of 
 * rows created was accurate.  
 * 
 * @author kevinchen
 */
import java.io.PrintWriter;

public class Matrix {
   int n; // order of the square matrix
   HeaderNode head;
   HeaderNode tail;

   // constructor, the order of the matrix is set when the matrix is 
   // initialized.
   public Matrix(int n) {
      this.n = n;
      head = null;
      tail = null;
   }

   /*
   This method creates a new row for the matrix by creating a new header node
   */
   public void newRow() {
      if (head == null && tail == null) {
         HeaderNode temp = new HeaderNode(1);
         head = temp;
         tail = temp;
      } else {
         int rowNumber = tail.row + 1;
         HeaderNode temp = new HeaderNode(rowNumber);
         tail.nextRow = temp;
         tail = temp;
      }
   }

/**
 * this pointer receives a pair of coordinates and returns a reference that 
 * points to that location within the matrix. It was created to simplify 
 * coding so that other methods that need to use the same code could reference 
 * this method and re-use this code
 * 
 * 
 * @param i row position in the matrix, starting from 1
 * @param j column position in the matrix, starting from 1
 * @return a Node reference that points to the node object at the specified
 *         location in the matrix. 
 */
   public Node Pointer(int i, int j) {
      HeaderNode row = head;
      Node column;

      //this for loop points to row, i. 
      for (int x = 1; x <= tail.row; x++) {
         if (row.row == i) {
            break;
         } else {
            row = row.nextRow;
         }
      }
      if (row == null) {
         System.out.println("in Pointer, i bigger than number of rows created");
         return null;
      }
      // column 1 is the first node in the row and it is attached to the 
      // header node
      column = row.firstValue;
      
      // this for loop points to the column position, j.
      for (int x = 1; x <= row.rowSize; x++) {
         if (x == j) {
            break;
         } else {
            column = column.next;
         }
      }
      if (column == null) {
         System.out.println("in Pointer, j bigger than number of columns created");
         return null;
      }

      // returns a reference that points to the node at position i, j in the 
      // matrix
      return column;
   }

   /**
    * This method adds a new node to the end of a row. The row number needs to 
    * be specified by the user. 
    * 
    * @param i row number to which the method appends.
    * @param value the value of the new node that is added. 
    */
   public void Append(int i, double value) {
      Node temp = new Node();
      temp.value = value;
      HeaderNode row = head;
      Node column;

      // this for loop points to the row, i. 
      for (int x = 1; x <= tail.row; x++) {
         if (row.row == i) {
            break;
         } else {
            row = row.nextRow;
         }
      }
      if (row == null) {
         System.out.println("in Append, i bigger than number of rows created");
      }
      column = row.firstValue;

      // if the row is empty, then add the new node as the first node in the 
      // row
      if (row.rowSize == 0) {
         row.firstValue = temp;
      } else {
         //points to the current last node on the row
         for (int x = 1; x <= row.rowSize; x++) {
            if (x == row.rowSize) {
               break;
            } else {
               column = column.next;
            }
         }
         if (column == null) {
            System.out.println("in Append, j bigger than number of columns "
                    + "created");
         } else {
            //add the new node after the last node on the row
            column.next = temp;
         }
      }
      // update information in the header node, so that it contains the number
      // of elements that are currently in the row. 
      row.rowSize++;
   }

   /**
    * This method receives a pair of coordinates and returns the value of the 
    * specified node within the matrix. 
    * 
    * @param i row number of the node of interest
    * @param j column number of the node of interest
    * @return item the value of the node of interest
    */
   public double GetValue(int i, int j) {
      
      //using the pointer method to point to the node of interest
      Node temp = Pointer(i, j);
      //extract data from the node of interest
      double item = temp.value;

      return item;
   }

   /**
    * Prints the row number of each head node in chronological order from the
    * first row to the last. This method was created for debugging purposes. 
    */
   public void PrintHeaders() {
      HeaderNode temp = head;
      while (temp != null) {
         System.out.println(temp.row);
         temp = temp.nextRow;
      }
   }

   /**
    * This method prints the matrix onto an output text file
    * @param output text file the method prints the matrix on
    */
   public void printMatrix(PrintWriter output) {
      HeaderNode row = head;

      for (int i = 1; i <= tail.row; i++) {
         Node temp = row.firstValue;
         for (int j = 1; j <= row.rowSize; j++) {
            output.printf("%5.1f", temp.value);
            temp = temp.next;
         }
         output.println();
         row = row.nextRow;
      }
   }

   /*
   This method receives a pair of coordinates and replaces the node at that 
   location in the matrix with a new node 
   */
   public void Replace(int i, int j, double value) {
      HeaderNode row = head;
      Node temp = new Node();
      temp.value = value;
      /*
      takes care of the edge case where the node to be replaced is the 
      first node in the row. 
      */
      if (j == 1) {
         for (int x = 1; x <= tail.row; x++) {
            if (row.row == i) {
               break;
            } else {
               row = row.nextRow;
            }
         }
         if (row == null) {
            System.out.println("in Replace, i bigger than number of rows created");
         }
         Node current = row.firstValue;
         temp.next = current.next;
         row.firstValue = temp;
         current.next = null;
         //this branch takes care of cases where the node to be 
         //replaced is not the first node in the row. 
      } else {
         //using the pointer method to point to the node before the node of 
         //interest
         Node before = Pointer(i, j - 1);
         Node current = before.next; //reference points to the node of interest
         temp.next = current.next;
         before.next = temp;
         current.next = null;
      }
   }

   /*
   This method tests to see if the number of elements in the last row of the 
   matrix is equal to the order of the matrix, n. It only checks the last row
   because the main method calls this method every time a new row is added
   to the matrix. So essentially every row prior to the last row was 
   already checked.
   */
   public boolean isValid() {

      if (tail.rowSize != n) {
         return false;
      } else {
         return true;
      }
   }

}
