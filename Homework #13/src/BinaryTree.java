/*
   Aidan Chandra
   Homework #13
   Dec 4, 2017
 */
public class BinaryTree<T> {
    public Node head; //Could also be called root
    
    private class Node{
        public T val;
        public Node left;
        public Node right;
        public Node parent;
        public Node(T value, Node leftIn, Node rightIn, Node parentIn){
            val = value;
            left = leftIn;
            right = rightIn;
            parent = parentIn;
        }
        /*
            Your usual toString method to let us print out the node we 
            get from the least common ancestor method
        */
        @Override
        public String toString(){
            return val.toString();
        }
    }
    /*
        Initializes the binary tree from a generic array
    */
    public BinaryTree(T[] arr) throws Exception{
        if(arr.length < 2)
            throw new Exception();
        this.head = build(null, arr, 0);
    }
    
    /*
        This method builds the binary tree from an array of type T recursively
    */
    public Node build(Node parentNode, T[] arr, int index){
        Node newNode = new Node(arr[index], null, null, parentNode); //Make a new node that will be a child of the provided parentNode
        
        if(arr[index].toString().equals("-")) //If the value we got was supposed to be null, make it null
            newNode.val = null;
        
        //If we have an existant left child, set the newNode's left child to that node
        if(((2 * index) + 1) < arr.length)
            newNode.left = build(newNode, arr, ((2 * index) + 1));
        
        //If we have an existant right child, set the newNode's right child to that node
        if(((2 * index) + 2) < arr.length)
            newNode.right = build(newNode, arr, ((2 * index) + 2));
        
        //Finally, return our fully created node
        return newNode;
    }
    
    /*
    
    */
    public Node leastCommonAncestor(T firstValue, T secondValue){
        if(secondValue.equals("-") || firstValue.equals("-")) //If we are asking for ancestors of null items, return null
            return null;
        
        //Find the nodes that match with our given values. We know there are no repeats. These compuations are not included in our cost model
        Node childOne = find(firstValue);
        Node childTwo = find(secondValue);
        
        //Temporary variables for the position of each child
        int childOnePosition = 0;
        int childTwoPosition = 0;
        
        //Now, their places in the tree
        Node childOneTemp = childOne;
        while(childOneTemp != null){
            childOnePosition++;
            childOneTemp = childOneTemp.parent;
        }
        
        Node childTwoTemp = childTwo;
        while(childTwoTemp != null){
            childTwoPosition++;
            childTwoTemp = childTwoTemp.parent;
        }

        //Now, we have the level of each of our two children in worst case 2 * height
        //The next step is to get them to the same level in order to check if their parents are equal at the same time
        
        if(childOnePosition > childTwoPosition){ //Child one is lower in the tree, go up ultil one is at the same level
            while(childTwoPosition != childOnePosition){
                childOne = childOne.parent;
                childOnePosition--;
            }
        }
        else if(childTwoPosition > childOnePosition){ //Child two is lower in the tree, go up untl two is at the same level
            while(childOnePosition != childTwoPosition){
                childTwo = childTwo.parent;
                childTwoPosition--;
            }
        }
        
        //Now, we have in total, at worst, gone through 3 * height
        
        //Finally, we go back up through the remaining height. This leaves us with at worst 4 * height
        while(childTwo != null && childOne != null){ //While our current children arent null
            if(childOne.val.equals(childTwo.val)) //If our two children are the same, return one of them
                return childOne;
            //If not, go one up for each child. If each child is a child of the head, child one and child two will become null and the loop will exit ...
            //... this is why we return the head after exiting because our two children will only be null if their least common ancestor
            childOne = childOne.parent;
            childTwo = childTwo.parent;
        }
        
        //We end up with O(height)
        return this.head;
    }
    
    
    /*
        The rest of this code was done in class :)
    */
    public Node find(T item){
        return find(item, this.head);
    }
    private Node find(T item, Node nextNode){
         if(nextNode == null)
            return null;
        else if(nextNode.val != null && nextNode.val.equals(item))
            return nextNode;
        else{
            Node result = find(item, nextNode.left); 
            if(result == null)
                result = find(item, nextNode.right);
            return result;
        }
    }
    
    
    public boolean contains(T item){
        return contains(item, this.head);
    }
    private boolean contains(T item, Node nextNode){
        if(nextNode == null)
            return false;
        else if(nextNode.val != null && nextNode.val.equals(item))
            return true;
        return contains(item, nextNode.left) || contains(item, nextNode.right);
    }
    
    public int leafCount(Node node){
        if(node.left == null && node.right == null)
            return 1;
        if(node == null)
            return 0;
        return leafCount(node.left) + leafCount(node.right); 
    }
}
