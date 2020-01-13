
import java.util.ArrayList;

/*
   Aidan Chandra
   Homework #
   Aug 22, 2017
 */

public class Homework_1 {
    
    public static void main(String[] args){
        for(String num : args){ //For each argument
            IntegerProperties number; //Create a Integerproperties refrence
            try{
                number = new IntegerProperties(num); //try to create a new number (may throw exceptions)
                //If the number is AMICABLe or SOCIABLE then print out the sequence after it as per the sheet
                if(number.classify() == IntegerProperties.numType.AMICABLE || number.classify() == IntegerProperties.numType.SOCIABLE){
                    ArrayList l = number.aliquotSequence();
                    l.remove(l.size() - 1); //Not the last one though
                    System.out.println(number.toString() + ": " + number.classify() + l);
                }
                else //If not, print it out normally
                    System.out.println(number.toString() + ": " + number.classify());
            }
            //Below: Catch the exceptions and continue on with the other arguments :)
            catch (IntegerProperties.NegativeNumberException | IntegerProperties.StringException e){
                System.out.println(e.getMessage());
                continue;
            } 
        }
            
            
        }
        
    
    
}
