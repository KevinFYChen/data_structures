/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Language 1 has an equal number of character 'A' and 'B' and contains no 
 * other characters. A and B can be in any order.
 *
 * An empty string is considered to be a valid language one as it has an equal
 * number of A and B (both are zero)
 *
 * This module contains only 1 method: test
 *
 * @author kevinchen
 */
public class L1 {

    /**
     * This method uses stacks to determine whether a string belongs to language
     * 1. This method does not involve any counting of the characters in the
     * string.
     *
     * @param s a string received from the input file
     * @return boolean tells the user whether the string parameter is language 
     * 1
     */
    public boolean test(String s) {
        Stack stackA = new Stack();
        Stack stackB = new Stack();
        /*
         go through the string and push A and B into separate stacks, return
         false if any other characters are found
         */
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                stackA.push(s.charAt(i));
            } else if (s.charAt(i) == 'B') {
                stackB.push(s.charAt(i));
            } else {
                return false;
            }
        }
        /*
         popping stack A and B at each iteration. if there is an equal 
         number of A and B in the string, then the 2 stacks should be emptied 
         at the same time. if one stack is empty before the other then there 
         isn't an equal number of A and B in the string
         */
        while (true) {
            if (stackA.isEmpty() && !stackB.isEmpty()) {
                return false;
            } else if (!stackA.isEmpty() && stackB.isEmpty()) {
                return false;
            } else if (stackA.isEmpty() && stackB.isEmpty()) {
                return true;
            } else {
                try {
                    stackA.pop();
                    stackB.pop();
                } catch (EmptyStackException e) {
                    System.out.println("L1: " + e.getMessage());
                }

            }

        }

    }
}
