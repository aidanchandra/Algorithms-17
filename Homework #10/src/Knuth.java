/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Nov 6, 2017
 */
public class Knuth {
    public static void main(String[] args){
        
        int n = Integer.valueOf(args[0]); //Simply take the first argument
        int arr[] = new int[n];
        for(int i = 0; i < n; i++){ //Fill it with 1 to N
            arr[i] = i; 
        }
        for(int i = n-1; i >= 0; i--){ //Shuffle it using Knuth shuffle
            int j = (int)(Math.random() * i) + 1; //Math.random awfulness from the APCS days :)
            //Swap accordingly
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        for(int i : arr) //StdOut it
            StdOut.print(i + " ");
        
    }
}
