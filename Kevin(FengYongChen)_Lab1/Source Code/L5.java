/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *Language 5 follows the format (A^nB^3n)^p for n and p>0
 *compared to language 4, this this language adds more complexity in terms of
 *as the program now needs to first verify that each repetition follows the
 * format A^nB^3n. In contrast, language 4 doesn't care how many A's and B's
 * there are in each repetition, as long as all repetitions look the same.
 *
 * This module contains 2 methods: test and isValid
 *
 * test method tells the user whether a string belongs to language 5
 * isValid method tells the program whether a repetition follows the format
 * A^nB^3n
 *
 * @author kevinchen
 */
public class L5 {

   /**
    * This method tells the user whether a string belongs to language 5
    *
    * The approach to this problem is similar to the one used in the test
    * method of language 4. The first repetition of the string is pushed into
    * stack1. The program then makes a deep copy of stack1 and passes this
    * deep copy to the isValid method which verifies whether the first
    * repetition of the string follows the format A^nB^3n
    *
    * The isValid method returns a boolean value back to the calling
    * statement based on the format of the first repetition in the string. 
    * If the first repetition does not have the correct format, the test 
    * method will return false. Else, the method pushes the rest of the 
    * characters in the string to stack2 and goes in a loop to compare stack1 
    * and stack2.
    *
    * This method address the following errors: 
    * 1) if string contain all A's (i.e. AAAA) or all B's (i.e. BBBB), then it 
    *    won't pass the test 
    * 2) If B precedes A in the first repetition, then the string won't pass 
    *    the test
    * 3) If the string only has 1 repetition (i.e. p=1), depending on whether
    *    it follows the right format, it may or may not pass the test. 
    * 4) If the string contains any characters other than A or B, then it will 
    *    not pass the test
    *
    * @param s a string
    * @return boolean tells the user whether the string belongs to language 5
    */
   public boolean test(String s) {
      //since n, p>0, the string should at least have 4 characters
      if (s.length() < 4) {
         return false;
      }
      Stack stack1 = new Stack();
      Stack stack2 = new Stack();
      Stack stack3 = new Stack();
      Stack stackTest = new Stack();
      boolean single = false;
      boolean trueFalse = false;

      /*
      pushes A to stack1, breaks when the loop detects B, returns false for
      other characters
       */
      for (int i = 0; i < s.length(); i++) {
         /* 
         if the loop made it to the last character of the string without
         breaking and the last character is still an A then the entire string 
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
      if stack1 is empty after exiting the first loop, then that either means 
      the string contains no A or that B precedes A. Either way, the method 
      will return false
       */
      if (stack1.isEmpty()) {
         return false;
      }

      /*
      pushes B to stack1, breaks when the loop detects A, returns false for
      other characters
       */
      for (int i = 0; i < s.length(); i++) {
         /*
         If the loop made it to the last character of the string without 
         hitting A, then the string only has one repetition (p=1).The codes 
         above have already ensured that the contains at least 1 A at the 
         beginning and at least 1 B following the A(s). 
          */
         if (i == s.length() - 1 && s.charAt(i) == 'B') {
            single = true; //single means string only has 1 repetition
         }
         if (s.charAt(i) == 'B') {
            stack1.push(s.charAt(i));
         } else if (s.charAt(i) == 'A') {
            //truncate string at the point where the loop breaks
            s = s.substring(i);
            break;
         } else {
            return false;
         }
      }
      stackTest = stack1.copy(); // make a deep copy of stack1
      trueFalse = isValid(stackTest);

      //the method returns false if the first repetition is not A^nB^3n
      if (trueFalse == false) {
         return false;
      }
      //this handles the case of p=1 (one repetition)
      if (single) {
         return trueFalse;
      }

      //push the remaining characters of the string into stack2
      for (int i = 0; i < s.length(); i++) {
         stack2.push(s.charAt(i));
      }

      /*
      this loop compares stack1, which has one repetition of A^nB^3n in 
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
         if (!stack1.isEmpty() && !stack2.isEmpty() && stack1.peek()
                 == stack2.peek()) {
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
               System.out.println("L5: " + e.getMessage());
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
         if (stack1.isEmpty() && !stack2.isEmpty()) {
            while (!stack3.isEmpty()) {
               try {
                  //pop the characters in stack3 back into stack1
                  char c = stack3.pop();
                  stack1.push(c);
               } catch (EmptyStackException e) {
                  System.out.println("L5: " + e.getMessage());
               }

            }
         }
      }

   }

   /**
    * This method takes in a stack and determines if the original string
    * follows the format A^nB^3n. It takes into account the fact the order of
    * the characters are reversed (i.e. string that says "ABBB" is now BBBA in
    * a stack)
    *
    * The method assumes 
    * 1) the stack only contains A's and B's 
    * 2) there are at least 3 characters in the stack 
    * 3) A precedes B
    * It is fair to make these assumptions because the test method already
    * made sure that the above statements are true. 
    * 
    * The approach to this problem is very similar to the approach in the test
    * method of language 3.
    *
    * @param test1 a stack containing A's and B's
    * @return boolean, tells the caller if the format of a string is A^nB^3n
    */

   public boolean isValid(Stack test1) {
      Stack test2 = new Stack();

      //pop B's into test2 until A is detected
      while (!test1.isEmpty()) {
         if (test1.peek() == 'B') {
            try {
               char c = test1.pop();
               test2.push(c);
            } catch (EmptyStackException e) {
               System.out.println("L5: " + e.getMessage());
            }
         } else if (test1.peek() == 'A') {
            break;
         }
      }
      /*
        This block compares the content in test1, which only contains A
        and test2, which only contains B. test1 is popped once and test2
        is popped 3 times at each iteration. If either stack is empty before 
        the other then the string does not belong to language 5. The method 
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
         if (test1.isEmpty() && test2.isEmpty()) {
            return true;
         } else if (test1.isEmpty() && !test2.isEmpty()) {
            return false;
         } else if (!test1.isEmpty() && test2.isEmpty()) {
            return false;
         } else {
            // pops test1 once and test 2 3 times
            try {
               test1.pop();
               test2.pop();
               if (test2.isEmpty()) {
                  return false;
               } else {
                  test2.pop();
               }
               if (test2.isEmpty()) {
                  return false;
               } else {
                  test2.pop();
               }
            } catch (EmptyStackException e) {
               System.out.println("L5 test method: " + e.getMessage());
            }

         }
      }
   }

}
