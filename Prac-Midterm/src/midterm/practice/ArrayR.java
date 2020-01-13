/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package midterm.practice;
import java.lang.Exception;
/*
   Aidan Chandra
   Homework #
   Oct 9, 2017
 */
public class ArrayR<T> {
    T[] masterArr = (T[])new Object[2];
    int index = 0;
    public void append(T toAdd){
        if(index + 1 > masterArr.length){
            resize();
        }
        masterArr[index] = toAdd;
        index++;
    }
    //Prepend
    //Contains
    //Iterator
    //Size
    public T get(int index){
        if(index >= this.index)
            throw new IndexOutOfBoundsException();
        return masterArr[index];
    }
    private void resize(){
        int currentSize = masterArr.length;
        T[] newArr = (T[])new Object[(int)Math.pow(currentSize, 2)];
        for(int i = 0; i < currentSize; i++){
            newArr[i] = masterArr[i];
        }
        masterArr = newArr;
    }
}
