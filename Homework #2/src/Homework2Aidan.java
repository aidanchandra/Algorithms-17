/*
   Aidan Chandra
   Homework #2
   Aug 30, 2017
 */


import java.util.Iterator;
public class Homework2Aidan {
    public static void main(String[] args) {
        Tour t = new Tour(); //New tour
        for (String arg : args){
            System.out.println(arg);
            City c = Capitals.find(arg);
            if(c != null)
                t.append(c);
        }
        //Proof that my iterator is working
        Iterator i = t.iterator();
        while(i.hasNext())
            System.out.println(i.next());
        //Print out all of my data
        System.out.println("Tour Length: " + t.length());
        System.out.println("Size: " + t.size());
        System.out.println("Storage: " + t.storageSize());

    }
    
}
