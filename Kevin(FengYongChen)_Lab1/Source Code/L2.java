/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Language 2 is in the format A^nB^n for n>0. This means there is a number of
 * consecutive As followed by a number of consecutive Bs. The number of As
 * always equal the number of Bs.
 *
 * this module only contains one method: test
 *
 * @author kevinchen
 */
public class L2 {

    /**
     * this method uses stacks to determine if a string belongs to language 2
     * this method does not count the number of characters in the string. 
     * 
     * this method creates a stack containing all the As before the first B in 
     * the string. Then, starting from the first B, the stack pops every time 
     * there is a B. The stack should be emptied at the last B in the string. 
     * If the stack is empty before the last B or if it is not empty after the 
     * last B, then the string does not belong to language 2.
     *
     * Error handling measures are put in place for strings that contain 
     * characters other than A or B. Measures are also put in to handle 
     * repetition of As and Bs
     *
     * @param s string
     * @return boolean tells user if the string belongs to language 2
     */
    public boolean test(String s) {
        /*
          since n>0, 1 is the lowest value n can be and so the string must have
          at least 2 characters - one A and one B.
         */
        if (s.length() < 2) {
            return false;
        }

        Stack stack = new Stack();

        // push A into stack until it detects B
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'A') {
                stack.push(s.charAt(i));
            } else if (s.charAt(i) == 'B') {
                s = s.substring(i);
                break;
            } else {
                return false;
            }
        }
        /*
         starting from the first B in the string, the method pops stack every
         time the character is B. If stack is empty before the last B in the
         string, then there are more Bs than As in the string. If there is any
         character other than B in the string, the method returns false, this
         takes care of repetitions of As and Bs as well as if there are other
         characters in the string.
         */
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'B') {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    try {
                        stack.pop();
                    } catch (EmptyStackException e) {
                        System.out.println("L2: " + e.getMessage());
                    }
                }
            } else {
                return false;
            }
        }
        //if stack is not empty, then there were more As than Bs
        return (stack.isEmpty());
    }
}
