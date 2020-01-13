/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Oct 18, 2017
 */
public class ControlTest {
    public static void main(String[] args){
        int[] arr = new int[]{9,1,2,3,4,5,6,7,8};
        quickSort(0, arr.length - 1, arr);
    }
    
    public static void quickSort(int lo, int hi, int[] arr){
        int i = lo;
        int j = hi;
        
        if(lo == hi) return;
        
        int v = 1;
        //if(hi >= 2) v = lo + 1; //1 pivot
        
        do{
            while(arr[i] < arr[v])
                i++;
            while(arr[j] > arr[v])
                j--;
            
            
            if(i <= j){
                swap(i, j, arr);
                j--;
                i++;
            }
            
        }while(i <= j);
        
        
        for(int a : arr){
            if(a != 9) System.out.print(a + " ");
            else System.out.print(a + "! ");
        }
        System.out.println("");
        
        
        quickSort(lo, j, arr);
        quickSort(i, hi, arr);
    }
    public static void swap(int a, int b, int[] arr){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
