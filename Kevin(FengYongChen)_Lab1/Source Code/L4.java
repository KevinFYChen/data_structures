/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * language 4 is in the format (A^nB^m)^p for n, m and p >0
 *
 * This module only contains one method: test
 *
 * @author kevinchen
 */
public class L4 {

   /**
    * This method tests whether a string is language 4.
    *
    * Since this language contains repetition, the first repetition of the
    * string containing A^nB^m is pushed into a stack1. The rest of the string
    * containing (A^nB^m)^p-1 is pushed into a stack 2. Then in a loop, the 2
    * stacks are compared. Both stacks are popped at each iteration, and what
    * is popped in stack 1 should be exactly the same as stack 2. If it is
    * not, then the string is not language 4. If stack 1 becomes empty, it is
    * refilled to have the exact same content as before and the loop continues
    * until stack 2 (containing (A^nB^m)^p-1) is empty. If both stacks are
    * emptied at the same time then the string belongs to string 4, if stack 2
    * is empty before stack 1 then the string is not language 4.
    *
    * Various measures are put in place to ensure that: 
    * 1) if string contain all A's (i.e. AAAA) or all B's (i.e. BBBB), then it 
    *    won't pass the test
    * 2) If B precedes A in the first repetition, then the string won't pass
    *    the test 
    * 3) If the string only has 1 repetition (i.e. p=1), then the string will 
    *    pass the test. 
    * 4) If the string contains any characters other than A or B, then it will 
    *    not pass the test
    *
    *
    *
    * @param s a string
    * @return boolean tells user whether a string is in language 4
    */
   public boolean test(String s) {
      // for m, n and p>0, the string should have a minimum of 2 characters
      if (s.length() < 2) {
         return false;
      }

      Stack stack1 = new Stack();
      Stack stack2 = new Stack();
      Stack stack3 = new Stack();

      /*
        this loop pushes A to stack 1 and breaks at first B in string
       */
      for (int i = 0; i < s.length(); i++) {
         /* 
            if the loop made it to the last character of the string without
            breaking and the last character is still A then the entire string 
            is made of A, so the system will return false
          */
         if (i == s.length() - 1 && s.charAt(i) == 'A') {
            return false;
         }

         if (s.charAt(i) == 'A') {
            stack1.push(s.charAt(i));
         } else if (s.charAt(i) == 'B') {
            //truncate string at the point where the loop breaks
            s = s.substring(i);
            break;
         } else {
            return false; //return false for any character other than A or B
         }
      }


      /*
      if stack1 is empty after the first loop, then that either means the 
      string contains no A or that B precedes A. Either way, the program will 
      return false
       */
      if (stack1.isEmpty()) {
         return false;
      }
      /*
      This loop pushes B in the string to stack 1 and breaks when it detects 
      A
       */
      for (int i = 0; i < s.length(); i++) {
         /*
            If the loop made it to the last character of the string without 
            hitting A, then the string only has one repetition (p=1), the 
            program should return true. The codes above have already ensured 
            that the contains at least 1 A at the beginning and at least 1 B 
            following the A(s). 
          */
         if (i == s.length() - 1 && s.charAt(i) == 'B') {
            return true;
         }
         if (s.charAt(i) == 'B') {
            stack1.push(s.charAt(i));
         } else if (s.charAt(i) == 'A') {
            //truncate string at the point where the loop breaks
            s = s.substring(i);
            break;
         } else {
            /*
            return false if the string contains any character other than 
            A or B
             */
            return false;
         }
      }
      //pushes the remaining characters in string to stack2
      for (int i = 0; i < s.length(); i++) {
         stack2.push(s.charAt(i));
      }


      /*
      this loop compares stack1, which has one repetition of A^nB^m in 
      reverse order and stack2 which has the rest of the characters from the 
      input string, in reverse order as well. The loop pops both stack1
      and stack2 once each iteration.
      
      5 possible scenarios could happen and they are all addressed in the loop
      1) both stacks are not empty and the top of both stacks are equal
      2) both stacks are not empty and the 2 stacks have different tops
      3) stack1 is empty and stack2 is not
      4) stack1 is not empty and stack2 is empty
      5) both stacks are emtpy
       */
      while (true) {
         if (!stack1.isEmpty() && !stack2.isEmpty()
                 && stack1.peek() == stack2.peek()) {
            try {
               char c = stack1.pop();
               /*
               characters popped by stack1 is catched by stack3 so that later
               when stack1 is empty, it can be refilled by popping stack3
               and pushing what is popped back into stack1
                */
               stack3.push(c);
               stack2.pop();
               continue;
            } catch (EmptyStackException e) {
               System.out.println("L4: " + e.getMessage());
            }

         } else if (!stack1.isEmpty() && !stack2.isEmpty() && stack1.peek()
                 != stack2.peek()) {
            return false;
         } else if (!stack1.isEmpty() && stack2.isEmpty()) {
            return false;
         } else if (stack1.isEmpty() && stack2.isEmpty()) {
            return true;
         }
         /*
         if stack 1 is empty before stack 2, refill it so that is has exactly 
         the same content as when it started at the beginning of the loop
          */
         if (stack1.isEmpty()) {
            while (!stack3.isEmpty()) {
               try {
                  //pop the characters in stack3 back into stack1
                  char c = stack3.pop();
                  stack1.push(c);
               } catch (EmptyStackException e) {
                  System.out.println("L4 last loop: " + e.getMessage());
               }
            }

         }

      }

   }
}
