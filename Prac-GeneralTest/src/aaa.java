   /*
 *To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Jan 1, 2018
 */
public class aaa {
    public static void main(String[] args){
        int[] arr = new int[]{39,45,67,33,98,76,90,34,21,56,32,38};
        System.out.println(getDigit(123,10,1));
         msdRadix(arr);
        for(int a : arr)
            System.out.println(a);
    }
    public static void lsdRadix(int[] arr){
        int max = numberDigits(arr[0], 10);
        for(int i = 0; i < arr.length; i++){
            if(numberDigits(arr[i], 10) > max)
                max = numberDigits(arr[i], 10);
        }
        for(int i = 0; i < max; i++){
            
            lsdradix(arr, i);
        }
    }
    public static void msdRadix(int[] arr){
        int max = numberDigits(arr[0], 10);
        for(int i = 0; i < arr.length; i++){
            if(numberDigits(arr[i], 10) > max)
                max = numberDigits(arr[i], 10);
        }
        msdradix(arr, max, 0, arr.length);
    }
    private static void lsdradix(int[] arr, int digit){
        int index = 0;
        for(int i = 0; i < 10; i++){
            for(int k = index; k < arr.length; k++){
                if(getDigit(arr[k], 10, digit) == i){
                    swap(arr, index++, k);
                }
            }
        }
    }
    private static void msdradix(int[] arr, int digit, int lo, int hi){
        if(digit < 0)
            return;
        int index = lo;
        int prevIndex = lo;
        for(int i = 0; i < 10; i++){
            boolean toRecurse = false;
            for(int k = index; k < hi; k++){
                if(getDigit(arr[k], 10, digit) == i){
                    swap(arr, index++, k);
                    toRecurse = true;
                }
            }
            if(toRecurse){
                msdradix(arr, digit - 1, prevIndex, index);
                prevIndex = index;
            }
        }
    }
    private static int numberDigits(int value, int radix) {
        int digits = 0;
        while (value > 0) {
            value /= radix;
            digits++;
        }
        return digits;
    }
    private static void swap(int[] G, int x, int y) {
        int tmp = G[x];
        G[x] = G[y];
        G[y] = tmp;
    }
    private static int getDigit(int value, int radix, int digit) {
        while (digit > 0) {
            value /= radix;
            digit--;
        }
        return value % radix;
    }
}
