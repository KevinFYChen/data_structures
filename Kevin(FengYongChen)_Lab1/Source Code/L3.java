/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *@Language 3 follows the format A^nB^2n for n>0.
 *
 * This module only contain one method: test
 *
 * @author kevinchen
 */
public class L3 {

    /**
     * This method first pushes As into stack 1 and Bs into stack 2. The program
     * then goes through a loop, popping stack 1 once and stack 2 twice at each
     * iteration. If there are twice as many Bs as As then eventually the 2
     * stacks should be emptied on the same iteration. If one stack is emptied
     * before the other, then the string does not belong to language 3.
     *
     * Various measures are put in place to ensure that: 
     * 1) A precedes B 
     * 2) repetitions (ex. ABBABB) will not pass the test 
     * 3) Strings with only As ("AAAA") or B's ("BBBB") will not pass the test
     * 4) Strings containing characters other than A and B will not pass the 
     * test
     *
     * @param s a String
     * @return boolean tells the user if the string belongs to language 3
     */
    public boolean test(String s) {
        //for n>0, the string should have a minimum of 3 characters
        if (s.length() < 3) {
            return false;
        }
        Stack stack1 = new Stack();
        Stack stack2 = new Stack();

        /* 
        push all characters in the string into a stack, returns false if 
        there are characters other than A and B
         */
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A' || s.charAt(i) == 'B') {
                stack1.push(s.charAt(i));
            } else {
                return false;
            }
        }

        /*
        If string is language 3, B should be on top in the stack, the program 
        keeps popping stack 1 when the top is B and pushes B into stack 2. 
        The program breaks if it detects A on top of stack
         */
        while (!stack1.isEmpty()) {
            if (stack1.peek() == 'B') {
                try {
                    char popped = stack1.pop();
                    stack2.push(popped);
                } catch (EmptyStackException e) {
                    System.out.println("L3: " + e.getMessage());
                }
            } else if (stack1.peek() == 'A') {
                break;
            }
        }
        /*
        This block compares the content in stack 1, which only contains A
        and stack 2, which only contains B. stack 1 is popped once and stack 2
        is popped twice at each iteration. If either stack is empty before the 
        other then the string does not belong to language 3.
        
        This block accounts for the following errors:
        1) A does not precede B (ex "BAA")
            If B precedes A then stack 2 would be empty. Since one stack is 
            empty and the other is not, the program will return false
        2) String only contains A's ("AAAA") or B's ("BBBB")
            If the string only contains A's or B's then either one of the two
            stacks would be empty by the time the program reaches the while
            loop below. The program would then return false
        3) String contains repetitions (ex. ABBABB
            take ABBABB for example, in the previous block the while loop breaks
            when it detects A on top of stack 1. So that means after the 
            previous while loop stack 1 will contain ABBA <- top and stack 2 
            will contain BB <- top. Hence, the 2 stacks will fail the tests 
            below since stack 2 is not twice as long as stack 1
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
                    stack1.pop();
                    stack2.pop();
                    if (!stack2.isEmpty()) {
                        stack2.pop();
                    } else {
                        return false;
                    }
                } catch (EmptyStackException e) {
                    System.out.println("L3: " + e.getMessage());
                }
            }
        }

    }
}
