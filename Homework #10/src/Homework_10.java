 /*
   Aidan Chandra
   Homework #10
   Nov 6, 2017
 */

/* Command Usage

"-pivot <pivottype>"    select from an available list of pivots (see line 63). Defaults to Mean
"-cutoff <cutoff size>" Select an integer to be the cutoff depth. Defaults to 0
"-mutefile"             Sets all statistics to not print out except for the stack depth

    See the spreadsheet that was shared with you for the data. This is our conclusion from the collected data.

    Conclusion: For all array sizes greater than 10^3 and less than 10^6, implementing a cutoff at M = 50 and then
    running insertion sort at the very end minimizes the stack depth the most effectively because minimizing the 
    number of calls to quicksort (substituted for insertion sort) minimizes the stack depth (obviously).
    
*/

public class Homework_10 {

    private static int arr[]; //Universal array
    private static int cutoff; //Cutoff variable 
    enum pivots {MEAN, MEDIAN, TOP, BOTTOM, RANDOM} //Enum for pivot type
    private static pivots pivotType = pivots.MEAN; //Pivot variable
    
    private static int stackDepth = 0; //Stackdepth variable
    private static int maxDepth = 0; //max stackdepth variable

    public static void main(String[] args) {
        
        boolean mute = false; //Variable that if turned on by arguments will mute all stats but the stackdepth for easy datalogging
        arr = StdIn.readAllInts(); //Take all ints through StdIn from StdOut from Knuth generator. This is seperate from arguments.
        
        
        for (int i = 0; i < args.length; i++) { //For each argument (ONLY COMMANDS)
            String arg = args[i]; //String arg 
            
            if (arg.contains("-")) { //It is a command
                if (arg.equals("-mutefile")) //If we want mute then mute it
                    mute = true;
                if (arg.equals("-cutoff")) { //If it is -cutoff take the next with incramenting
                    try{
                        cutoff = Integer.valueOf(args[++i]);
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("No argument following the cutoff command... exiting ");
                        return;
                    }
                    if(cutoff <= 0){
                        System.out.println("Cutoff must be greater than 0... exiting");
                        return;
                    }
                }
                //If it is a pivot command
                if (arg.equals("-pivot")) { //If it is -cutoff take the next with incramenting 
                    String pivot;
                    try{
                        pivot = args[++i];
                    }
                    catch (IndexOutOfBoundsException e){
                        System.out.println("No argument following the pivot command... exiting ");
                        return;
                    }
                    //If none of these it defaults to mean
                    switch(pivot){
                        case "mean":
                            pivotType = pivots.MEAN;
                            break;
                        case "median":
                            pivotType = pivots.MEDIAN;
                            break;
                        case "middle":
                            pivotType = pivots.MEDIAN;
                            break;
                        case "mid":
                            pivotType = pivots.MEDIAN;
                            break;
                        case "top":
                            pivotType = pivots.TOP;
                            break;
                        case "bot":
                            pivotType = pivots.BOTTOM;
                            break;
                        case "rand":
                            pivotType = pivots.RANDOM;
                            break;
                        case "random":
                            pivotType = pivots.RANDOM;
                            break;
                    }
                }
            } 
        }
        //Print out statistics with respect to the mute command
        if(!mute) System.out.println("    * Pivot of type " + pivotType + " N: " + arr.length + " Cutoff: " + cutoff);
        quickSort();
        if(!mute) System.out.println("    * Statistics: Stack Depth " + maxDepth);
        if(mute) System.out.println(stackDepth + "  ");
        
    }

    private static void quickSort() { //Helper method
        quickSort(0, (arr.length - 1));
        arr = insertionSort(arr);
    }
    
    
    private static void quickSort(int lo, int hi) {
        
        //Cutoff check. If so, return and leave it unsorted. Insertion sort at the absolute end
        if (hi - lo <= cutoff) {
            return;
        }
        
        stackDepth++; //Incrament stack depth
        maxDepth = Math.max(stackDepth, maxDepth); //See if we have gone deeper than previously thought
        

        int i = lo; //Lo variable
        int j = hi; //Hi variable
        int v = 0; //Pivot
        
        //Depending on what pivot we want, take the pivot
        switch(pivotType) {
            case TOP:
                v = arr[lo];
                break;
            case MEDIAN:
                v = arr[(lo + hi) / 2];
                break;
            case RANDOM:
                v = arr[(int)(Math.random() * arr.length)];
            case BOTTOM:
                v = arr[hi];
                break;
            case MEAN:
                v = getMean(lo, hi);
                break;
        }
        //Count closer to the pivot and swap all incorrectly placed elements while we have not overlapped counters
        do {
            while (arr[i] <  v) {
                i++;
            }
            while (arr[j] >  v) {
                j--;
            }
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        } while (i <= j);

        //Now quicksort each side
        quickSort(lo, j);
        quickSort(i, hi);
        
        stackDepth--; //Decrement stack depth
    }

    private static int getMean(int lo, int high){  //Method to get the mean element
        int smallArr[] = new int[]{lo, high, (lo + high)/2};
        return insertionSort(smallArr)[1];
    }
    
    private static int[] insertionSort(int[] array) { //Standard Insertion Sort 
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j;
            for (j = i - 1; j >= 0 && temp < array[j]; j--) {
                array[j + 1] = array[j];
            }
            array[j + 1] = temp;
        }
        return array;
    }

}
