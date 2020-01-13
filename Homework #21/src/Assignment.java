/*
 * Notes:
        In the evaluate, I could have var.set return the value that we set to save time
 */

/*
   Aidan Chandra
   Homework #
   Feb 6, 2018
 */
public class Assignment{
    private boolean debug = false;
    
    public class Equal extends BinaryOperator{
        String varReference;
        public Equal (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            Runner.variables.put(firstOperand.symbol, secondOperand.evaluate());
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){ //FirstValue will NEED to be a variable according to our constructor    
            if(debug) System.out.println("          Assignment.Equal: Setting " + varReference + " to " + secondValue.evaluate());
            Runner.variables.put(varReference, secondValue.evaluate());
            return Runner.variables.get(varReference);
        }
        @Override
        public String operator() {
            return "=";
        } 
        public int precedence(){
            return 0;
        }
    }
    
    public class plusEqual extends BinaryOperator{
        private String varReference;
        
        public plusEqual (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            if(debug) System.out.println("          Assignment.plusEqual: Setting " + varReference + " to " + (firstValue.evaluate() + secondValue.evaluate()));
            Runner.variables.put(varReference, firstValue.evaluate() + secondValue.evaluate()); //firstValue OR var????
            return Runner.variables.get(varReference);
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "+=";
        } 
        public int precedence(){
            return 0;
        }
    }
    
    public class minusEqual extends BinaryOperator{
        String varReference;
        public minusEqual (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            if(debug) System.out.println("          Assignment.minusEqual: Setting " + varReference + " to " + (firstValue.evaluate() - secondValue.evaluate()));
            Runner.variables.put(varReference, firstValue.evaluate() - firstValue.evaluate());
            return Runner.variables.get(varReference);
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "-=";
        } 
        public int precedence(){
            return 0;
        }
    }
    
    public class timesEqual extends BinaryOperator{
        String varReference;
        public timesEqual (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            if(debug) System.out.println("          Assignment.timesEqual: Setting " + varReference + " to " + firstValue.evaluate() * secondValue.evaluate());
            Runner.variables.put(varReference, firstValue.evaluate() * secondValue.evaluate());
            return Runner.variables.get(varReference);
            //How to do the actual setting equal - I cant visualize it
                    
        }
        @Override
        public String operator() {
            return "*=";
        } 
        public int precedence(){
            return 0;
        }
    }
    
    public class divEqual extends BinaryOperator{
        String varReference;
        public divEqual (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            //System.out.println(secondValue.evaluate());
            if(debug) System.out.println("          Assignment.divEqual: Setting " + varReference + " to " + (firstValue.evaluate() / secondValue.evaluate()));
            Runner.variables.put(varReference, ((firstValue.evaluate() / secondValue.evaluate())));
            return Runner.variables.get(varReference);
        }
        @Override
        public String operator() {
            return "/=";
        } 
        public int precedence(){
            return 0;
        }
    }
    
    public class modEqual extends BinaryOperator{
        String varReference;
        public modEqual (Variable firstOperand, Node secondOperand){
            super(firstOperand, secondOperand);
            varReference = firstOperand.symbol;
        }
        
        @Override
        public int evaluate(Node firstValue, Node secondValue){
            if(debug) System.out.println("          Assignment.Mod: Setting " + varReference + " to " + firstValue.evaluate() % secondValue.evaluate());
            Runner.variables.put(varReference, firstValue.evaluate() % secondValue.evaluate());
            return Runner.variables.get(varReference);
        }
        @Override
        public String operator() {
            return "%=";
        } 
        public int precedence(){
            return 0;
        }
    }
}
