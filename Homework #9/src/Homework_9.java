/*
    Homework 9
    Aidan Chandra
    11/06/17
*/

import java.util.Comparator;
import java.util.Iterator;

public class Homework_9 {
        
	// Implement a generic sorting method that implements mergeSort on a singly linked list
	// of objects of type T.  Comparisons of objects of type T are performed using the
	// Comparator supplied as an argument to the invocation of your merge sort method. 

	// Rules of engagement:
	//
	//    * You must write an implementation of the top-down mergesort algorithm.
	//    * The running time of your implementation must be O(n lg n).
	//    * You must not convert the linked lists to an array; you must use lists directly.
	//    * You must access the elements of the linked list sequentially.
	//    * Do NOT write a method which returns the ith element of a list.
        //    * You must be able to use a comparator to control the sort order (forward or reverse).

	// Suggestions:
	//
	//    * Write a private generic method (parameterized with a type T) to merge two lists into a single list.
	//    * Write another private generic method to distribute items in a list evenly to two other lists.
        
    
	public static <T> SinglyLinkedList<T> mergeSort (SinglyLinkedList<T> list, Comparator<T> comparator) {
            
            if(list.size() <= 1){
                return list;
            }
            
            //Create two new lists to split :)
            SinglyLinkedList<T> topList = new SinglyLinkedList();
            SinglyLinkedList<T> botList = new SinglyLinkedList();
            
            //Splits array into two in n/2 time
            Iterator l = list.iterator();
            while(l.hasNext()){
                topList.append((T)l.next());
                if(l.hasNext())
                    botList.append((T)l.next());
            }
            
            //Call mergesort on the "top half" and "bottom half". I chose top and bottom because they are names I know and relate to regualar mergesort
            topList = mergeSort(topList, comparator);
            botList = mergeSort(botList, comparator);
            
            return merge(topList, botList, comparator); //Merge the two lists and return it
	}
        
        private static <T> SinglyLinkedList<T> merge (SinglyLinkedList<T> list1, SinglyLinkedList<T> list2, Comparator<T> comparator){
            SinglyLinkedList<T> returnable = new SinglyLinkedList<T>(); //Create a new list to be filled and sorted and returned

            Iterator i1 = list1.iterator(); //Iterator for the first list passed in
            Iterator i2 = list2.iterator(); //Iterator for the second list passed in
            //Take the beginning of both
            T l1 = (T)i1.next();
            T l2 = (T)i2.next();
            
            //While EITHER of them have values
            while(l1 != null && l2 != null){
                if(comparator.compare(l1, l2) > 0){ //If the one at pointer 1 is > or < than the one at pointer 2, add it to the returnable list and incrament
                    returnable.append(l1);
                    
                    if(i1.hasNext())
                        l1 = (T)i1.next();
                    else
                        l1 = null; //If there are no more elemnts left, set it to null to soon break out of the loop
                }
                
                //Same as above except with pointer 2 replacing pointer 1
                else if(comparator.compare(l1, l2) < 0){
                   
                    returnable.append(l2);
                    
                    if(i2.hasNext())
                        l2 = (T)i2.next();
                    else
                        l2 = null;
                }
                
                //If they are equal, add them both and incrament, allowing for null values to be caught by the while loop 
                else{
                    returnable.append(l1);
                    returnable.append(l2);
                    
                    if(i1.hasNext())
                        l1 = (T)i1.next();
                    else
                        l1 = null;
                    
                    if(i2.hasNext())
                        l2 = (T)i2.next();
                    else
                        l2 = null;
                    
                }
            }
            
            //After the end of the loop, simply go through each list and add the remains. This is OK because there will be some remaining... 
            //...and those will be the greatest or least in order. This essentially just cleans up.
            
            while(l1 != null){
                returnable.append(l1);
                if(i1.hasNext())
                    l1 = (T)i1.next();
                else
                    l1 = null;
            }
            while(l2 != null){
                returnable.append(l2);
                if(i2.hasNext())
                    l2 = (T)i2.next();
                else
                    l2 = null;
            }
            
            
            return returnable; //Return our newly sorted list that was sorted in N time :)
        }
        
        
	// Comparator objects of an anonymous class for sorting in forward and reverse order.
        //Self-explanatory
	public static Comparator<String> reverseOrder = new Comparator<String>() {
            @Override
            public int compare(String one, String two){
                return one.compareTo(two);
            }
            public boolean equals(String one, String two){
                return one.equals(two);
            }
		// Lookup the java Comparator interface to determine how to implement this anonymous class.
	};
        //Reversed of above
	public static Comparator<String> forwardOrder = new Comparator<String>() {
            @Override
            public int compare(String one, String two){
                int a = one.compareTo(two);
                if(a > 0) return -1;
                if(a < 0) return 1;
                return 0;
            }
            public boolean equals(String one, String two){
                return one.equals(two);
            }
		// Should be pretty easy if you figured out how to implement forwardOrder.
	};


	// Some kind sou; wrote a test program for you ... just figure out how to use it.

	public static void main(String[] args) {
		SinglyLinkedList<String> list = new SinglyLinkedList<>();
		Comparator<String> comparator = forwardOrder;

		for (String arg : args) {
			switch (arg) {
				case "-reverse":
					comparator = reverseOrder;
					break;

				case "-forward":
					comparator = forwardOrder;
					break;

				default:
					list.append(arg);
					break;
			}
		}
                System.out.println("Unsorted: " + list);
		list = mergeSort(list, comparator);
                System.out.println("Sorted: " + list);

	}
}
