/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Feb 6, 2018
 */



public class Variable implements Operand{
    String symbol;
    public Variable(String symbol){ //The key where to look for on variable map
        Runner.variables.put(symbol, null);
        this.symbol = symbol;
    }
    public Variable(String symbol, int value){ //The key where to look for on variable map
        Runner.variables.put(symbol, value);
        this.symbol = symbol;
    }
    public int evaluate(){
        return Runner.variables.get(symbol);
    }
    public String format(){
        return this.symbol;
    }
    public void set(int newVal){
        Main.variables.put(symbol, newVal);
    }
    public int precedence(){
        return 0;
    }
}
