/*
   Aidan Chandra
   Homework #13
   Dec 4, 2017
 */
public class Homework_13 {
    public static void main(String[] args) throws Exception{
        //Exits the program if our input was too small to correctly proccess
        if(args.length < 4){
            System.out.println("Input array too small... exiting");
            return;
        }
        //Create a new array and fill it with all the elements except the two last ones
        //The last two ones are what we will find the least common ancestor of
        String[] arr = new String[args.length - 2];
        for(int i = 0; i < args.length - 2; i++)
            arr[i] = args[i];
        //Create a new tree with the new array
        BinaryTree<String> tree = new BinaryTree(arr);
        //Now, print out the least commoon ancestor of the two given names
        System.out.println(tree.leastCommonAncestor(args[args.length - 2], args[args.length-1]));
        
        System.out.println(tree.leafCount(tree.head));
        
    }
}
