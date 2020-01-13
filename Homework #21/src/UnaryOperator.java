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
public abstract class UnaryOperator extends Operator{
    
    protected Node operand;
    
    public UnaryOperator(Node operand){
        this.operand = operand;
    }
    
    @Override
    public int evaluate(){
        return evaluate(this.operand.evaluate());
    }
    
    @Override
    public String format(){
        return operator() + this.operand.format();
        //How to handle printing
        
    }
    
    public abstract int evaluate(int operand);
    public abstract String operator();
}
