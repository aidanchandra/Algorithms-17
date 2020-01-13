/*
   Aidan Chandra
   Homework #12
   Nov 29, 2017
 */
public class Homework_12 {
    
    private static int[] numbers; //Array of numbers
    private static int n;   //Size
    
    private static MaxPriorityQueue<Integer> maxQueue;
    private static MinPriorityQueue<Integer> minQueue;
    
    public static void main(String[] args){
        numbers = Input.getInts(args);
        n = numbers.length;     
        maxQueue = new MaxPriorityQueue((n / 2) + 2); 
        minQueue = new MinPriorityQueue((n / 2) + 2 ); 
        
        
        for(int num : numbers)
            try{
                addNumber(Integer.valueOf(num)); //Try to add it
            }
            catch (NumberFormatException e){ //Return if not all strings are numbers
                System.out.println("Not all provided strings are numbers, exiting");
                return;
            }
        
        
        System.out.println(getMedian()); //print the median. This can be done at any point (on line)
    }
    
    public static void addNumber(int num){
        if(minQueue.size() == 0 || num > minQueue.min()){ //IF its the first thing or the number is bigger than the head of min
            minQueue.add(num); //Add it to min
        }
        else{
            maxQueue.add(num); //Otherwise add it to the maxqueue
        }
        balance(); //Make sure sizes are correct
    }
    private static void balance(){
        //Essentially just remove the head of the larger tree and add it to the smaller tree
        
        if(minQueue.size() > maxQueue.size() + 1){
            int num = minQueue.removeMin();
            maxQueue.add(num);
        }
        else if(maxQueue.size() > minQueue.size() + 1){
            int num = maxQueue.removeMax();
            minQueue.add(num);
        }
    }
    //REturn the head of the biggest array or the average of the two
    public static int getMedian(){
        if(minQueue.size() > maxQueue.size()) return minQueue.min();
        else if(minQueue.size() < maxQueue.size()) return maxQueue.max();
        return (minQueue.min() + maxQueue.max()) / 2;
    }
}
