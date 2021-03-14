/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class allows the stack to throw an exception to tell the user that
 * the stack is empty
 *
 * @author kevinchen
 */
public class EmptyStackException extends Exception {

    /**
     * This method overrides the original getMessage() method and returns a
     * string that tells the user the stack is empty
     *
     * @Override
     */
    public String getMessage() {
        return "Stack is empty";
    }
}
