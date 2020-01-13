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
public abstract class Postfix{
    public class Incrament extends UnaryOperator{
        public Incrament(Node operand){
            super(operand);
        }
        @Override
        public int evaluate(int value){
            return value + 1;
                    
        }
        @Override
        public String operator() {
            return "--";
        }
        public int precedence(){
            return 0;
        }
          
    }
    
    public class Decrement extends UnaryOperator{
        public Decrement(Node operand){
            super(operand);
        }
        @Override
        public int evaluate(int value){
            return value - 1;
                    
        }
        @Override
        public String operator() {
            return "++";
        }
        public int precedence(){
            return 0;
        }
    }
}
