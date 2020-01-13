/*1
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Feb 6, 2018
 */

public class Constant implements Operand{
    private int val;
    private Node in;
    public Constant(int value){
        val = value;
    }
    public Constant(Node in){
        this.in = in;
    }
    public int evaluate(){
        if(in == null)
            return this.val;
        else
            return this.in.evaluate();
    }
    public String format(){
        if(in == null)
            return String.valueOf(this.val);
        else
            return String.valueOf(this.in.format());
    }
    public int precedence(){
        return 0;
    }
}

