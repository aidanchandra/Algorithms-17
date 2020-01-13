/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aidanchandra
 */
public abstract class BinaryOperator extends Operator{
    
    private Boolean debug = false;
    protected Node firstOne;
    protected Node secondOne;
    
    public BinaryOperator(Node firstOne, Node secondOne){
        this.firstOne = firstOne;
        this.secondOne = secondOne;
    }
    
    @Override
    public int evaluate(){
        return evaluate(this.firstOne, this.secondOne);
    }
    
    @Override
    public String format(){
        if (debug)
            System.out.println("        BinaryOperator.format: firstOne.precedence " + firstOne.precedence() + "   secondOne.precedence " + secondOne.precedence() + "   this.precedence " + this.precedence());
        if (debug)
            System.out.println("        BinaryOperator.format: firstOne " + firstOne.format() + "   secondOne " + secondOne.format());
        if (debug)
            System.out.println("");
        
        
        if(secondOne.precedence() == 1 && firstOne.precedence() == 0 && this.precedence() > firstOne.precedence())
            return this.firstOne.format() + operator() + "(" + this.secondOne.format() + ")";
        if(firstOne.precedence() == 1 && secondOne.precedence() == 0 && this.precedence() > secondOne.precedence())
            return "(" + this.firstOne.format() + ")" + operator() + this.secondOne.format();
        if(firstOne.precedence() == 1 && secondOne.precedence() == 1)
            return "(" + this.firstOne.format() + ")" + operator() + "(" + this.secondOne.format() + ")";
        
        return this.firstOne.format() + operator() + this.secondOne.format();
        
    }
    
    public abstract int evaluate(Node firstOne, Node secondOne);
    public abstract String operator();
}
