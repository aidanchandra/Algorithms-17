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
public class Arithmetic{
    public class Add extends BinaryOperator{
        public Add(Node firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            return firstValue.evaluate() + secondValue.evaluate();
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "+";
        } 
        public int precedence(){
            return 0;
        }
    }
    public class Subtract extends BinaryOperator{
        public Subtract (Node firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            return firstValue.evaluate() - secondValue.evaluate();
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "-";
        } 
        public int precedence(){
            return 0;
        }
    }
    public class Divide extends BinaryOperator{
        public Divide (Node firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            return firstValue.evaluate() / secondValue.evaluate();
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "/";
        } 
        public int precedence(){
            return 1;
        }
    }
    public class Multiply extends BinaryOperator{
        public Multiply (Node firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            return firstValue.evaluate() * secondValue.evaluate();
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "*";
        } 
        public int precedence(){
            return 1;
        }
    }
    public class Mod extends BinaryOperator{
        public Mod (Node firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            return firstValue.evaluate() % secondValue.evaluate();
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "%";
        } 
        public int precedence(){
            return 1;
        }
    }

}
