/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This module implements the header node that is used in the linked list
 * structure "Matrix". A header node marks the beginning of a row. It 
 * also contains information on the row number and the number of elements in
 * that row.
 * 
 * This node is designed to be multi-linked so that it could be 
 * linked to other header nodes. Linking header nodes together allows the 
 * program to traverse through the header nodes and access different rows. 
 * @author kevinchen
 */
public class HeaderNode {

   int row; //row number
   int rowSize; // number of elements in the row
   HeaderNode nextRow; //points to the next row
   Node firstValue; // points to the first value in the row

   public HeaderNode(int row) {
      this.row = row;
      rowSize = 0;
      nextRow = null;
      firstValue = null;
   }
}
