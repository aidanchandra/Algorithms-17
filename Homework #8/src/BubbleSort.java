/*
   Aidan Chandra
   Homework #8
   Oct 23, 2017
 */

public class BubbleSort{
    static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {           
            for (int j = 1; j < (arr.length - i); j++) {  
                if (arr[j - 1] > arr[j]) {            
                    swap(j, arr);                  
                }
            }
            
        }
    }
    static void swap(int indexOne, int[] arr){
        int temp = arr[indexOne - 1];
        arr[indexOne - 1] = arr[indexOne];
        arr[indexOne] = temp;
    }

    public static void main(String[] args) {
        int arr[] = {6,5,4,3,2,1}; //Array to be sorted
        
        System.out.println("Bubble Sort Demonstration");
        bubbleSort(arr);//sorting array elements using bubble sort  

        System.out.println("After Bubble Sort");
        for(int a : arr)
            System.out.print(a + " ");
    }
}