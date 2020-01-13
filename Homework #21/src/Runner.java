
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #21
   Apr 25, 2018
 */

/*

                            Node
        Operand         Binary Operator            Unary Operator
    Constant   Var.   Assignment     Arithmetic   Postfix       Prefix
                                                            Identity     Negate

*/

public class Runner {
    static boolean manual = true;
    protected static Map<String, Integer> variables;
    TreeBuilder treeBuilder;
    public static void main(String[] args) throws TreeBuilder.parseException{
        TreeBuilder treeBuilder = new TreeBuilder();
        variables = new HashMap();
        
        if(manual){
            System.out.println(treeBuilder.evaluate("x=30").evaluate());
            System.out.println(treeBuilder.evaluate("y=4").evaluate());
            System.out.println("____________________");
            String eee = "3*--y";
            System.out.println(treeBuilder.evaluate(eee).evaluate());
            System.out.println(Runner.variables.get("y"));
        }
        
        else{
           
                for(int i = 0; i < args.length; i++){
                    String arg = args[i];
                    try{
                        System.out.println((i+1) + ": " + arg + " -> " + treeBuilder.evaluate(arg).evaluate());
                    }
                    catch(TreeBuilder.parseException e){
                        System.out.println((i+1) + ": " + arg + " -> Invalid expression");
                    }
                }
            
        }
    }
    
}
