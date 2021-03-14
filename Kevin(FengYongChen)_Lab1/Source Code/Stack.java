/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This module implements the stack data structure using linked list.
 *
 * this data structure has 6 methods:
 * 1) isEmpty
 * 2) push
 * 3) pop
 * 4) peek
 * 5) print
 * 6) copy
 *
 * All of the methods except print are used in classifying at least one of the
 * six languages. Print was useful in debugging my code
 *
 * @author kevinchen
 */
public class Stack {

   Node top;

   public Stack() {
      top = null;
   }

   /**
    *
    * @return boolean true if stack is empty
    */
   public boolean isEmpty() {
      if (top == null) {
         return true;
      } else {
         return false;
      }
   }

   /**
    * takes an input character and add it to the top of the stack
    *
    * @param c a character c
    */
   public void push(char c) {
      Node temp = new Node();
      temp.data = c;
      temp.next = top;
      top = temp;
   }

   /**
    * removes a character from the top of the stack and returns that
    * character. throws EmptyStackException if stack is empty.
    *
    * @return a character c that was removed from the top of the stack
    * @throws EmptyStackException
    */
   public char pop() throws EmptyStackException {
      Node temp = top;
      char c;
      if (isEmpty()) {
         EmptyStackException empty = new EmptyStackException();
         throw empty;
      } else {
         c = temp.data;
         temp.data = ' ';
         top = top.next;
         temp.next = null;
      }
      return c;
   }

   /**
    * returns the character at the top of the stack without removing it
    *
    * @return a character c that is on top of the stack
    */
   public char peek() {
      if (!isEmpty()) {
         char c = top.data;
         return c;
      } else {
         return ' ';
      }
   }

   /**
    * prints all characters in a stack from top to bottom
    */
   public void print() {
      String s = "";
      Node temp = top;
      while (temp != null) {
         s = s + temp.data;
         temp = temp.next;
      }
      System.out.println(s);
   }

   /**
    * makes a deep copy of a stack
    * @return a deep copy of a stack
    */
   public Stack copy() {
      Stack newStack1 = new Stack();
      Stack newStack2 = new Stack();
      Node temp = top;
      char c;

      while (temp != null) {
         c = temp.data;
         newStack1.push(c);
         temp = temp.next;
      }
      while (!newStack1.isEmpty()) {
         try {
            c = newStack1.pop();
            newStack2.push(c);
         } catch (EmptyStackException e) {
            System.out.println("stack copy method: " + e.getMessage());
         }
      }
      return newStack2;

   }
}
