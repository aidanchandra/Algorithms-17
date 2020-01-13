 /*
   Aidan Chandra
   Homework #11
   Nov 13, 2017
 */



/*
    Stability Proof of In-Place Most Significant Digit Radix Sort
    
    The algorithm implmented below (MSD Radix Sort) is stable. Our sum variable ensures that 
    identical keys can only be swapped into adjacent indicies. We iterate through the array 
    in order, so when we encounter the first element that is to be swapped, that element will 
    also be swapped to the first position. As we continue iterating, any elements that are to
    be swapped are swapped to the index AFTER the first element that was originally swapped. 
    Thus, the original order of the elements is preserved, making this a stable sort. 


 */


public class Homework_11 {

    static String[] arr; //The only array that I used to help me make sure it is in-place
    private static int size; //Size of all the elements
    private static int cutoff = 5; //Size of the cutoff

    public static void main(String[] args) {

        arr = new String[args.length];

        //Returns if the array is empty
        if (args.length < 1) {
            System.out.println("No provided strings... exiting");
            return;
        }

        //A size variable 
        size = args[0].length();

        //Fills the "arr" variable and makes sure all the strings to sort are of uniform size
        for (int i = 0; i < args.length; i++) {
            arr[i] = args[i];
            if (arr[i].length() != size) {
                System.out.println("Provided strings are not all of uniform size... exiting");
                return;
            }
        }

        //Now sort it
        radixSort();
        
        //Print out the sorted array
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    /*
        Helper method that calls radixSortMSD with the actual correct numbers
     */
    public static void radixSort() {
        radixSortMSD(0, arr.length, 0);
        insertionSort(); //Insertionsort at the very end because of cutoff
    }

    private static void radixSortMSD(int lo, int hi, int digit) {
        if (digit > size - 1) { //Size
            return;
        }
        if (hi - lo < cutoff) //Cutoff to leave it unsorted for insertion sort at the end
        {
            return;
        }
        int sum = lo;
        int prevSum = lo;
        for (int i = 0; i < 26; i++) { //for each letter
            boolean toRecurse = false;
            for (int j = sum; j < hi; j++) { //For each inunsorted portion        
                char currentChar = arr[j].charAt(digit);
                if (getNumValue(currentChar) == i) { //If letter == currentchar
                    swap(sum++, j);
                    toRecurse = true;
                }
            }
            if (toRecurse) {
                radixSortMSD(prevSum, sum, digit + 1);
                prevSum = sum;
            }
        }

    }

    /*
        A method that swaps two elements in the arr array
     */
    private static void swap(int a, int b) {
        String temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
        Returns the integer value of a given character (0-26) regardless of case
     */
    private static int getNumValue(char a) {
        if (a < 91 && a > 64) //The character is an upper case
        {
            return a - 65; //Return the correct integer value given it is uppercase
        } else {
            return a - 97;  //Else, return the correct integer value for lowercase
        }
    }

    /*
        Standard Insertionsort
     */
    private static void insertionSort() {
        for (int i = 1; i < arr.length; i++) {
            String temp = arr[i];
            int j;
            for (j = i - 1; j >= 0 && temp.toLowerCase().compareTo(arr[j].toLowerCase()) < 0; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = temp;
        }
    }
}
