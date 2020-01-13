/*
   Aidan Chandra
   Homework #3
   Sep 8, 2017
 */

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {

    public static class ListIndexOutOfBoundsException extends IndexOutOfBoundsException {

        ListIndexOutOfBoundsException(int index) {
            super("Index Out Of Bounds Exception: " + index + " not in range");
        }
    }
    
    public static class ItemNotFoundException extends Exception{
        public ItemNotFoundException(String message){
            super(message);
        }
    }

    // Constructors:
    //    Empty list.
    //    Singleton list.
    //    Array of values.
    public static class Node<T> {
        public Node previous;
        public T item;
        public Node next;

        public Node(Node previous, T item, Node next) {
            this.previous = previous;
            this.item = item;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    
    private int size;

    public DoublyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public DoublyLinkedList(T value) {
        Node startingNode = new Node(null, value, null);
        head = startingNode;
        tail = startingNode;
        size = 1;
    }

    public DoublyLinkedList(T[] values) { 
        Node prevNode = new Node(null, values[0], null);
        this.head = prevNode;
        for(int i = 1; i < values.length - 1; i++){
            Node thisNode = new Node(prevNode, values[i], null);
            prevNode.next = thisNode;
            prevNode = thisNode;
        } 
        Node lastNode = new Node(prevNode, values[values.length - 1], null);
        prevNode.next = lastNode;
        this.tail = lastNode;
        
        size = values.length;
    }

    // Size        The number of elements in the list.
    // Contains    Determines if an item is in the list.
    public int size() {
        return size; //Rrturn size variable
    }

    public boolean contains(T item) {
        //Essentially iterates through every element in the lined list
        Node currentNode = this.head;
        while(currentNode != null){
            if(currentNode.item.equals(item)) //If we find what we want return true
                return true;
            currentNode = currentNode.next; //Incrament
        }
        return false; //If we went through and nothing matched, return false
    }

    // Get         Returns the value of the ith element of the list.
    // Set         Updates the value of the ith element of the list.
    public T get(int index) throws ListIndexOutOfBoundsException {
        int count = 0;
        Node currentNode = this.head;
        //Iterate through everything, looking for the right index
        while(currentNode != null){
            if(count == index){
                return (T)currentNode.item; //If we found it return it
            }
            count += 1; //Increament count
            currentNode = currentNode.next; //Incrament currentNode
        }
        throw new ListIndexOutOfBoundsException(index); //Throw exception if we never found it
    }

    public void set(int index, T value) throws ListIndexOutOfBoundsException {
        int count = 0;
        
        //Iterate through the entire linked list looking for the right index
        Node currentNode = this.head;
        while(currentNode != null){
            if(count == index){
                currentNode.item = value; //Set the value at that node
                size += 1;
                return;
            }
            count += 1;
            currentNode = currentNode.next; //Increament currentNode
        }
        throw new ListIndexOutOfBoundsException(index); //Throw exception if we never found it
    }

    // Append      Places a new element at the end of the list.
    // Prepend     Places a new element at the head of the list.
    // Remove      Removes ALL copies of a given element from the list.
    // Reverse     Reverses the order of the eleents in the list.
    //             (MUST be accomplished by changing the links)!
    public void append(T item) {
        if(size == 0 || this.head == null){ //If we have nothing in the list so far
            Node toAdd = new Node(this.tail, item, null); //Create a new node (this.tail will be null if there is nothing in the array)
            //Set tail and head to this
            this.head = toAdd; 
            this.tail = toAdd;
            size += 1; //Increament size
            //Return
            return;
        }
        //Else
        Node toAdd = new Node(this.tail, item, null); //Create a new node
        this.tail.next = toAdd; //Make tail reference toAadd as next
        this.tail = toAdd; //Make toAdd the tail
        size += 1; //Increament size
    }

    
    public void prepend(T item) {
        Node toInsert = new Node(null, item, this.head); //Create a new node, with the current head as its next
        this.head = toInsert; //Set head to this node, thus inserting this BEFORE the prevoius head
        size += 1; //Increament size
    }
    
    public void remove(T item){// throws ItemNotFoundException {
        //If the head is equal to the tail, ie there is only one node, just set head and tail equal to null, emptying the list
        if(head == tail){
           this.head = null;
           this.tail = null;
           size -= 1; //Dont forget to decrament size!
           return; //We can return now because we removced the first and only element in the node
       }
       
       if (head.item.equals(item)){ //If what we want to remoe exists in the head
                this.head = this.head.next; //Set the head to the next item
                if(this.head == null){ //If the next item is null
                    size -= 1; //decrament size
                    return; //return
                }
                this.head.previous = null; //Else, set the new head's previous to null
                size -= 1; //Decrament size
            }
        else if (tail.item.equals(item)){ //If the tail is what we are looking for
            this.tail = this.tail.previous; //Set the new tail to the previous of the old tail
            this.tail.next = null; //The next of the tail is null to cut off the old node
            size -= 1; //decrament size
        }
       
        //Now that we have reached here, go through the entire list and remove any instances of item
        Node currentNode = this.head;
        while(currentNode.next != null){
            if(currentNode.item.equals(item)){
                size -= 1;
                currentNode.previous.next = currentNode.next; //The actual removing
            }
            currentNode = currentNode.next;
        }
    }
    
    
    public void reverse() {
        if(size == 0) return; //Ensure that no null pointers happen because thre are no nodes
        
        Node temp = null; //Temporary node set to null
        Node current = head; //Current node as the first one
        
        while(current != null){
            //Essentially swaps the next and previous nodes of each node and then incraments based on the OLD next which is the NEW previous
            temp = current.previous; //Set the temp to the previous node of the current one (first time null)
            current.previous = current.next; //flip the current and previous
            current.next = temp; //Set the next to the temp previous
            current = current.previous;
            
        }
        
        //Essentially flips the head and tail
        Node tmpHead = this.head;
        head = temp.previous;
        tail = tmpHead;
    }

    
    // And the obvious iterators:
    public Iterator<T> iterator() {
        return new forwardIterator(); //return the forwardITerator
    }

    public Iterator<T> forwardIterator() {
        return new forwardIterator(); //just for the purpose of nomenclature being easy for the client to use, I provide a iterator that is explicitly forward


    }

    public Iterator<T> reverseIterator() {
        return new reverseIterator();

    }

    /**
     * Private forward iterator class
     */
    private class forwardIterator implements Iterator<T>{
        private Node currentNode; //current node 
        
        public forwardIterator(){
            currentNode = DoublyLinkedList.this.head; //set current node to the very first node
        }
        public boolean hasNext(){
            return currentNode != null; //return if we can go forward
        }
        public T next(){
            Node toReturn = currentNode; //temp node
            currentNode = currentNode.next; //incrament the node
            return (T)toReturn.item; //return
        }
    }
    
    /**
     * Private reverse iterator class
     */
    private class reverseIterator implements Iterator<T>{
        private Node currentNode; //current node
        
        public reverseIterator(){
            currentNode = DoublyLinkedList.this.tail; //set current node to the last node
        }
        public boolean hasNext(){
            if(currentNode == null) return false; //If we cannot move backwards, return false
            return true; 
        }
        public T next(){
            Node toReturn = currentNode; //Take current node temporarily 
            currentNode = currentNode.previous; //Incrament the node backwards
            return (T)toReturn.item; //Return tempNode item
        }
    }
   
    
    /*
    I commented this because it was only intended for debugging purposes 
    
    @Override
    public String toString(){
        if(size == 0)
            return "{}";
        String returnable = "{";
        for(int i = 0; i < size - 1; i++){
            returnable += (get(i) + ", ");
        }
        returnable += get(size - 1);
        returnable += "}";
        return returnable;
    }
    */
    
}
