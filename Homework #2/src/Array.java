/*
   Aidan Chandra
   Homework #2
   Aug 30, 2017
 */

import java.util.Iterator;

public class Array<T> implements Iterable<T>{
    // Constructors to create an empty Array and
    // to create an initialized Array (using an array of T).
    private T[] masterArray;
    private int size;
    private int storageSize;
    public Array() {
        masterArray = (T[])new Object[1]; //Create with size 1 
        size = 0;
        storageSize = 1;
    }

    public Array(T[] values) {
        masterArray = (T[])new Object[(int)Math.ceil(Math.log(values.length)/Math.log(2))]; //Make the first array a power of two
        for(int i = 0; i < values.length; i++)
            masterArray[i] = values[i];
        size = values.length;
        storageSize = masterArray.length;
    }
// The size of the array and storage allocated so far.
    /*
    Get length of array 
    */
    public int size() {
        return size;
    }
    /*
    Get the STORAGE size of the array
    */
    public int storage() {
        return storageSize;
    }
// Get/set individual elements of the array.
// Get should throw and exception (an IndexOutOfBoundsException)
// when the index is past the size of the array. Set should
// extend the array when the index is past the size of the array.

    /*
        Get a certian element within range
    */
    public T get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        return masterArray[index];
    }
    /*
    Set a certian element within range
    */
    public void set(int index, T value) {
      if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
      masterArray[index] = value;
    }
// Methods to append and prepend elements to the end/beginning of
// the array. Again, extend the array (by doubling its size) if
// necessary.
    
    /*
    Add a new element to the end
    */
    public void append(T value) {
        if (size + 1 >= storageSize){
            T[] tempArray = (T[])new Object[masterArray.length * 2]; //Expand the array by newSize = size * 2 to keep to powers of two
            for(int i = 0; i < masterArray.length; i++)
                tempArray[i] = masterArray[i];
            tempArray[size] = value;
            storageSize = tempArray.length;
            size += 1;
            masterArray = tempArray;
        }
        else{
            masterArray[size] = value;
            size += 1;
        }
        //storageSize += tempArray.leng
    }

    /*
    Add a new element to the beginning
    */
    public void prepend(T value) {
        if (size + 1 >= storageSize){
            T[] tempArray = (T[])new Object[masterArray.length * 2]; //Expand the array by newSize = size * 2 to keep to powers of two
            for(int i = storageSize - 1; i > 0; i --){
                tempArray[i] = masterArray[i - 1];
            }
            tempArray[0] = value;
            storageSize = tempArray.length;
            masterArray = tempArray;
        }
        else{
            for(int i = storageSize - 1; i > 0; i --){
                masterArray[i] = masterArray[i - 1];
            }
            masterArray[0] = value;
        }
        size += 1;
    }
// A method to check if a given element is in the array.

    /*
    If it exists return true
    */
    public boolean contains(T value) {
        for(int i = 0; i < size; i ++)
            if (masterArray[i].equals(value))
                    return true;
        return false;
    }
// Methods (forwardIterator and reverseIterator) which return
// Iterators which can be used to iterate over the elements of
// the array in either direction. Don’t forget, this class needs
// to implement Iterable.
    
    
    //Forward Iterator
    @Override
    public Iterator<T> iterator(){
        return new ArrayIterator();
    }
    //Reverse iterator
    public Iterator<T> reverseIterator(){
        return new ArrayReverseIterator();
    }
    
    //Iterator class
    private class ArrayIterator implements Iterator<T>{
        private int pos;
        
        public ArrayIterator(){
            this.pos = 0;
        }
        public boolean hasNext(){
            return (pos < Array.this.size);
        }
        public T next(){
            pos += 1;
            return Array.this.masterArray[pos-1];
        }
    }
    //Reverse iterator class
    private class ArrayReverseIterator implements Iterator<T>{
        private int pos;
        
        public ArrayReverseIterator(){
            this.pos = Array.this.size - 1;
        }
        public boolean hasNext(){
            return (pos >= 0);
        }
        public T next(){
            pos -= 1;
            return Array.this.masterArray[pos + 1];
        }
    }
    
    //ToString return just like a normal array :)
    @Override
    public String toString() {
        String returnable = "{";
        for(int i = 0; i < size - 1; i++){
            returnable += (masterArray[i].toString() + ", ");
        }
        return returnable += (masterArray[size - 1].toString() + "}");
    } 
// Comma separated list enclosed in { }
// like an array initializer.
}
