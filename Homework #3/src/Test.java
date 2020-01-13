
import java.util.Iterator;
/*
   Aidan Chandra
   Homework #3
   Sep 8, 2017

    PLEASE IGNORE THIS CLASS. THIS IS ONLY INTENDED FOR ISOLATED TESTING.
 */


public class Test {
    public static void main(String[] args){
        Integer[] arr = {1};
        DoublyLinkedList<Integer> l = new DoublyLinkedList(arr);
        l.reverse();
        System.out.println(l);
        Iterator t = l.reverseIterator();
                while(t.hasNext()){
                    System.out.println(t.next());
                }
    }
}
