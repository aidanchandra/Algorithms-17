/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Feb 13, 2018
 */
public class Test {
    public static void main(String[] args){
        String expression = "  2 * (x++ + 1) ";
        System.out.println("Expression: " + expression);
        String numbers = "1234567890";
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String operators = "+-/*%=";
         
            for(int i = 0; i < expression.length(); i++){
                String a = expression.substring(i, i+1);
                String finalString = "";
                if(numbers.contains(a)){ //we have a number
                    int start = i;
                    boolean hadUnderscore = false;
                    while((numbers.contains(a) || a.equals("_")) && i < expression.length()){
                        
                        //System.out.println("a: " + a);
                        
                        if(numbers.contains(a))
                            hadUnderscore = false;
                        
                        if(a.equals("_") && hadUnderscore)
                            System.out.println("Bad expression");
                        
                        if(a.equals("_") && !hadUnderscore)
                            hadUnderscore = true;
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);

                    }
                    finalString = expression.substring(start, i);
                    System.out.println(expression.substring(start, i));
                }
                else if(letters.contains(a.toLowerCase())){
                    int start = i;
                    boolean hadUnderscore = false;
                    while((letters.contains(a) || a.equals("_")) && i < expression.length()){
                        
                        
                        
                        if(letters.contains(a))
                            hadUnderscore = false;
                        
                        if(a.equals("_") && hadUnderscore)
                            System.out.println("Bad expression");
                        
                        if(a.equals("_") && !hadUnderscore)
                            hadUnderscore = true;
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);

                    }
                    finalString = expression.substring(start, i);
                    System.out.println(expression.substring(start, i));
                }
                else if(a.equals("_")){
                    System.out.println("Floating _");
                }
                if(finalString.endsWith("_"))
                    System.out.println("ENDED WITH UNDERSCORE");
                else if(operators.contains(a)){
                    int start = i;
                    int count = 0;
                    while(operators.contains(a) && i < expression.length()){
                        count++;
                        if(count == 3){
                            break;
                        }
                        
                        i++;
                        if(i + 1 > expression.length())
                            break;
                        a = expression.substring(i, i + 1);
                    }
                    finalString = expression.substring(start, i);
                    System.out.println(expression.substring(start, i));
                    if(count == 3){
                            i--;
                        }
                }
                else if(a.equals(")") ||  a.equals("(")){
                    if(i < expression.length())
                        finalString = expression.substring(i, i+1);
                    System.out.println(finalString);
                }
                
            }
    }
}
