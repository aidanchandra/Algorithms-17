/*
   Aidan Chandra
   Homework #
   Aug 22, 2017
 */

import java.math.BigInteger;
import java.util.ArrayList;
        
public class IntegerProperties{
    //An exception to be thrown when the program is given a string containing not only numbers
    public static class StringException extends Exception{
        public StringException(String message){
            super(message);
        }
    }
    //An exception to be thrown when the program is given a negatvie number
    public static class NegativeNumberException extends Exception{
        public NegativeNumberException(String message){
            super(message);
        }
    }
    //An enum type of all the types of number possibilities 
    enum numType {
		PRIME,
		PERFECT,
		AMICABLE,
		SOCIABLE,
                ASOCIAL
	}
    
    private BigInteger masterNum; //private bigInteger masterNum 
    private ArrayList<IntegerProperties> masterList; //private aliquotlist
    
    /**
     * CONSTRUCTOR
     * @param num: The input string from the client
     * @throws IntegerProperties.NegativeNumberException if num represents a negative number
     * @throws IntegerProperties.StringException  if num is not only integers
     */
    public IntegerProperties(String num) throws NegativeNumberException, StringException{
        try{
            masterNum = new BigInteger(num);
        }
        catch(NumberFormatException e){
            throw new StringException(num + ": invalid number"); 
        }
        if(masterNum.compareTo(masterNum.ZERO) < 0){
            throw new NegativeNumberException(masterNum.toString() + ": is not a positive integer");
        }   
    }
    
    /**
     * Classify
     * @return enumType of the type of number that this object represents
     */
    public numType classify() throws NegativeNumberException, StringException {
        masterList = aliquotSequence(); //Create a masterList of the alSeq before trying to classify it to save time
        if (isPrime())
            return numType.PRIME;
        else if (isAmicable())
            return numType.AMICABLE;
        else if (isPerfect())
            return numType.PERFECT;
        else if (isSociable())
            return numType.SOCIABLE;
        else
            return numType.ASOCIAL;

    }
    /**
     * 
     * @return true if the masterNum is prime, as determined by if the first and second in the aliquot sum multiply to the number itself
     */
    private boolean isPrime(){
        return (masterList.get(0).masterNum.multiply(masterList.get(1).masterNum).equals(masterNum));
    }
    /**
     * 
     * @return true if the masterNum is perfect, meaning it has a size of two and both items are equal
     */
    private boolean isPerfect(){
        if(masterList.size() == 2)
            return masterList.get(0).equals(masterList.get(1));
        return false;
    }
    /**
     * 
     * @return true if the number is sociable, meaning the first item is equal to the last item, thus signifying a repeating period
     */
    private boolean isSociable(){
        return masterList.get(0).equals(masterList.get(masterList.size() - 1));
    }
    /**
     * 
     * @return true if the number is amicable, meaning it's sequence has a size of 3 and the first and last terms are equal, this signifying a period of two
     */
    private boolean isAmicable(){
        if(masterList.size() == 3){ 
            return masterList.get(0).equals(masterList.get(2));
        }
        return false;
    }
    
    /**
     * Calculates the aliquotSum of masterNum
     * @return the aliquotSum of masterNum
     */
    private BigInteger aliquotSum(){
        ArrayList<BigInteger> factors = new ArrayList(); //List of factors
        BigInteger factor = new BigInteger(masterNum.subtract(masterNum.ONE).toString()); //Start off with the next one down
        while (factor.compareTo(masterNum.ZERO) > 0){ //While our factor is greater than 0
            BigInteger factorResult = this.masterNum.mod(factor); //See if it is a clean factor
            if(factorResult.toString().equals("0")){ //if it is (mod == 0)
               factors.add(factor); //Add this factor
            }
            factor = factor.subtract(masterNum.ONE); //Decrement
        }
        BigInteger returnable = new BigInteger("0"); //New biginteger
        for(BigInteger b : factors) //For each BigInteger in the factor list, add it to returnable
            returnable = returnable.add(b);
        return returnable; //return returnable;
    }
    
    /**
     * 
     * @return
     * @throws IntegerProperties.NegativeNumberException
     * @throws IntegerProperties.StringException 
     */
    public ArrayList<IntegerProperties> aliquotSequence() throws NegativeNumberException, StringException{
        ArrayList<IntegerProperties> list = new ArrayList(); 
        IntegerProperties temp = new IntegerProperties(aliquotSum().toString()); //Current num
        list.add(this); //Add this object
        list.add(temp); //Add this object's alSum
        while (true){ //while true
            if(temp.aliquotSum().toString().equals("0")){ //if are at 0
                list.add(new IntegerProperties(temp.aliquotSum().toString())); //add it
                return list; //and return the whole list with 0 as the last one
            }
            if(temp.equals(list.get(0))){ //If we got the first one again, it is social
                return list; //Since we've got a number that is social, return now
            }
            if(list.size() > 3 && (list.get(0).equals(list.get(2)) && list.get(1).equals(list.get(3)))) //If it has a period of 2
                return list; //Return now cause that is classfiable as amicable
            
            //If none of the above are true, keep on adding and adding
            list.add(new IntegerProperties(temp.aliquotSum().toString()));
            temp = new IntegerProperties(temp.aliquotSum().toString());
        }
    }
    
    /**
     * Determines whether another IntegerProperties is equal to this one
     * @param other: other IntegerProperties to be compared to
     * @return true if their masterNums are equal
     */
    public boolean equals(IntegerProperties other){
        return this.masterNum.equals(other.masterNum);
        
    }
    /**
     * A simple toString method that returns the masterNum's string
     * @return masterNum string representation
     */
    @Override
    public String toString(){
        return masterNum.toString();
    }
    

    
   
}
