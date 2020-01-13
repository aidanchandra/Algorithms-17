
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/*
   Aidan Chandra
   Homework #
   Feb 6, 2018
 */

 /*
   Class Heirarcy:

    
                            Node
        Operand         Binary Operator            Unary Operator
    Constant   Var.   Assignment     Arithmetic   Postfix       Prefix
                                                            Identity     Negate
 */
public class Main {

    protected static Map<String, Integer> variables;

    public static void main(String[] args) {
        //Instantiate the hashmap used for variables

        variables = new HashMap();

        Prefix prefix = new Prefix();
        Arithmetic arithmetic = new Arithmetic();
        Assignment assignment = new Assignment();
        
        BinaryOperator x = assignment.new Equal(new Variable("X"), arithmetic.new Divide(new Constant(2), arithmetic.new Multiply(new Constant(4), new Constant(5))));
        System.out.println(x.format());
        System.out.println(x.evaluate());
    }
}
