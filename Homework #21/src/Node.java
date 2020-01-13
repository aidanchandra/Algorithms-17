/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aidanchandra
 */
public interface Node {
    public int evaluate(); //Post order
    public String format(); //In order
    public int precedence();
}
