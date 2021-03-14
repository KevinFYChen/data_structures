/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *language 6 follows the format (A^nB^2n)^3 for n>0. This language adds
 * even more complexity to coding compared to language 5 as the program not
 * only has to ensure that each repetition is in the format A^nB^2n but also
 * that the repetition has to occur exactly 3 times.
 * Also, A^nB^2n is used here as opposed to A^nB^3n in language 5.
 *
 * This module contains 2 methods: test and isValid
 *
 * the test method tests if the string belongs to language 6
 *
 * The is valid method tests if a string follows the format A^nB^2n
 *
 * @author kevinchen
 */
public class L6 {

   /**
    * This method takes a string and determines if the string belongs to
    * language 6
    *
    * The approach to this problem is similar to that of language 5. Except
    * additional codes need to be added to ensure that the repetition happens
    * exactly 3 times.
    *
    * All of the characters in the string are first pushed into stack1. If
    * characters other than A or B are found in the string, the the method
    * returns falls.
    *
    * The first repetition of the string is then pushed into stack2. The
    * program then makes a deep copy of stack2 and passes this deep copy to
    * the isValid method which verifies whether the first repetition of the
    * string follows the format A^nB^2n
    *
    * The isValid method returns a boolean value back to the calling statement
    * based on the format of the first repetition in the string. If the first
    * repetition does not have the correct format, the test method will return
    * false.
    *
    * However, if the first repetition is in the format A^nB^2n, then stack2
    * pushes all its content into a stack3 and 2 deep copies of stack3 are
    * then made. stack3 and its 2 deep copies are then pushed back into stack
    * 2. Now we know that A^nB^2n is repeated exactly 3 times in stack2 and
    * the order of the characters are in reversed order (i.e. (B^2nA^n)^3).
    *
    * The method then enters a while loop to compare stack 1 with stack 2. If
    * the string is in language 6, the contents of stack1 and stack2 should be
    * exactly the same.
    *
    *
    * @param s a String
    * @return boolean tells the user if a string belongs to language 6
    */
   public boolean test(String s) {
      // since n>0, the string should have a minimum of 9 characters
      if (s.length() < 9) {
         return false;
      }

      Stack stack1 = new Stack();
      Stack stack2 = new Stack();
      Stack stack3 = new Stack();
      Stack stack4;
      Stack stack5;
      Stack stackTest;
      boolean validOrNot;

      /*
        pushes the entire string into stack1, returns false if other 
        characters are found
       */
      for (int i = 0; i < s.length(); i++) {
         if (s.charAt(i) == 'A' || s.charAt(i) == 'B') {
            stack1.push(s.charAt(i));
         } else {
            return false;
         }
      }

      //pushes A to stack2, breaks when the loop detects B
      for (int i = 0; i < s.length(); i++) {
         // if entire string is made of A, return false
         if (i == s.length() - 1 && s.charAt(i) == 'A') {
            return false;
         }
         if (s.charAt(i) == 'A') {
            stack2.push(s.charAt(i));
         } else if (s.charAt(i) == 'B') {
            //truncate string at the point where the loop breaks
            s = s.substring(i); 
            break;
         }
      }
      /*
      if stack2 is empty after exiting the first loop, then that either means 
      the string contains no A or that B precedes A. Either way, the method 
      will return false
       */
      if (stack2.isEmpty()) {
         return false;
      }

      //pushes B to stack2, breaks when the loop detects A
      for (int i = 0; i < s.length(); i++) {
         /*
         if the loop finds no A, then that means there is only one repetition.
         Hence, the string does not belong to language 6
         */
         if (i == s.length() - 1 && s.charAt(i) == 'B') {
            return false;
         }
         if (s.charAt(i) == 'B') {
            stack2.push(s.charAt(i));
         } else if (s.charAt(i) == 'A') {
            // truncate the string at the point where the loop breaks
            s = s.substring(i); 
            break;
         }
      }
      stackTest = stack2.copy(); // make a deep copy of stack2
      validOrNot = isValid(stackTest);

      if (validOrNot == false) {
         // returns fals if first repetition of string is not A^nB^3n
         return false; 
        /*
         if the first repetition is A^nB^2n, then push all of stack2's content
         into stack3 and make 2 deep copies of stack3
         */ 
      } else {
         while (!stack2.isEmpty()) {
            try {
               char c = stack2.pop();
               stack3.push(c);
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         }
         stack4 = stack3.copy();
         stack5 = stack3.copy();
         while (!stack3.isEmpty()) {
            try {
               char c = stack3.pop();
               stack2.push(c);
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         }
         while (!stack4.isEmpty()) {
            try {
               char c = stack4.pop();
               stack2.push(c);
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         }
         //push stack3 and its 2 deep copies back into stack2
         while (!stack5.isEmpty()) {
            try {
               char c = stack5.pop();
               stack2.push(c);
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         }
      }
      
      /*
      this loop compares stack1, contains all the characters from the original
      string in reverse order and stack2 which has 3 repetition of A^nB^2n in 
      reverse order as well. The loop pops both stack1 and stack2 once at 
      each iteration.
      
      5 possible scenarios could happen and they are all addressed in the loop
      1) both stacks are not empty and the top of both stacks are equal
      2) both stacks are not empty and the 2 stacks have different tops
      3) stack1 is empty and stack2 is not
      4) stack1 is not empty and stack2 is empty
      5) both stacks are emtpy
      */

      while (true) {
         if (stack1.isEmpty() && stack2.isEmpty()) {
            return true;
         } else if (stack1.isEmpty() && !stack2.isEmpty()) {
            return false;
         } else if (!stack1.isEmpty() && stack2.isEmpty()) {
            return false;
         } else if (!stack1.isEmpty() && !stack2.isEmpty() && stack1.peek() 
                 == stack2.peek()) {
            try {
               stack1.pop();
               stack2.pop();
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         } else if (!stack1.isEmpty() && !stack2.isEmpty() && stack1.peek() 
                 != stack2.peek()) {
            return false;
         }
      }

   }

   /**
    * This method takes in a stack and determines if the original string
    * follows the format A^nB^2n. It takes into account the fact the order of
    * the characters are reversed (i.e. string that says "ABBB" is now BBBA in
    * a stack)
    *
    * The method assumes 
    * 1) the stack only contains A's and B's 
    * 2) there are at least 9 characters in the stack 
    * 3) A precedes B
    * It is fair to make these assumptions because the test method already
    * made sure that the above statements are true.
    * @param stack1 a stack
    * @return boolean tells the caller if the format of a string is
    * A^nB^2n
    */

   public boolean isValid(Stack stack1) {
      Stack stack2 = new Stack();

      // pops B until A is detected
      while (!stack1.isEmpty()) {
         if (stack1.peek() == 'B') {
            try {
               char popped = stack1.pop();
               stack2.push(popped);
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }
         } else if (stack1.peek() == 'A') {
            break;
         }
      }
        /*
        This block compares the content in stack1, which only contains A
        and stack2, which only contains B. stack1 is popped once and stack2
        is popped twice at each iteration. If either stack is empty before 
        the other then the string does not belong to language 6. The method 
        returns true only when both stacks are emptied at the same time. 
        
        This block handles the following errors:
        1) A does not precede B (ex "BAA")
            If B precedes A then stack 2 would be empty. Since one stack is 
            empty and the other is not, the program will return false
        2) String only contains A's ("AAAA") or B's ("BBBB")
            If the string only contains A's or B's then one of the two
            stacks would be empty by the time the program reaches the while
            loop below. The program would then return false
       */
      while (true) {
         if (stack1.isEmpty() && !stack2.isEmpty()) {
            return false;
         } else if (!stack1.isEmpty() && stack2.isEmpty()) {
            return false;
         } else if (stack1.isEmpty() && stack2.isEmpty()) {
            return true;
         } else {
            try {
               //pops stack1 once and stack2 twice
               stack1.pop();
               stack2.pop();
               if (!stack2.isEmpty()) {
                  stack2.pop();

               } else {
                  return false;
               }
            } catch (EmptyStackException e) {
               System.out.println("L6: " + e.getMessage());
            }

         }
      }

   }
}
