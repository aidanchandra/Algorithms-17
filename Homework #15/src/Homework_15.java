/*
    Aidan Chandra
    Homework 15
    1/14/18
*/
import java.io.Console;

public class Homework_15<T extends Comparable<T>> {

    private boolean debug = true; //Flag to turn on debugging messages
    
    private static enum Balance {
        LEFT('/'), EVEN('-'), RIGHT('\\');

        private char c;

        private Balance(char c) {
            this.c = c;
        }

        public String toString() {
            return Character.toString(c);
        }
    }

    public class Node {

        private T data;
        private Node left;
        private Node right;
        private Balance balance;

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.balance = Balance.EVEN;
        }

        public Node(T data) {
            this(data, null, null);
        }

        public T value() {
            return this.data;
        }

        public Node left() {
            return this.left;
        }

        public Node right() {
            return this.right;
        }

        public void value(T data) {
            this.data = data;
        }

        public void left(Node child) {
            this.left = child;
        }

        public void right(Node child) {
            this.right = child;
        }

    }

    private Node root;

    public Homework_15() {
        this.root = null;
    }

    public Homework_15(T[] values) {
        this();
        build(values, 0, values.length - 1);
    }

    private static class BalanceAndHeight {

        public int height;
        public boolean balanced;

        public BalanceAndHeight(int height, boolean balanced) {
            this.height = height;
            this.balanced = balanced;
        }
    }

    private BalanceAndHeight isHeightBalanced(Node node) {
        if (node == null) {
            return new BalanceAndHeight(0, true);
        }
        BalanceAndHeight left = isHeightBalanced(node.left);
        BalanceAndHeight right = isHeightBalanced(node.right);

        int height = Math.max(left.height, right.height) + 1;
        boolean balanced = left.balanced & right.balanced & Math.abs(left.height - right.height) <= 1;
        return new BalanceAndHeight(height, balanced);
    }

    public boolean isHeightBalanced() {
        return isHeightBalanced(root).balanced;
    }

    private void build(T[] values, int lo, int hi) {
        if (lo <= hi) {
            int mid = (lo + hi) / 2;
            this.add(values[mid]);
            build(values, lo, mid - 1);
            build(values, mid + 1, hi);
        }
    }

    public int numberNodes() {
        return numberNodes(this.root);
    }

    private int numberNodes(Node node) {
        if (node == null) {
            return 0;
        } else {
            return numberNodes(node.left) + numberNodes(node.right) + 1;
        }
    }

    public int height() {
        return height(this.root);
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + Math.max(height(node.left), height(node.right));
        }
    }

    private int width(T item) {
        if (item != null) {
            return item.toString().length();
        } else {
            return 0;
        }
    }

    private int width(Node node) {
        if (node != null) {
            return 2 + width(node.left) + width(node.data) + width(node.right);
        } else {
            return 0;
        }
    }

    public boolean contains(T item) {
        Node rover = this.root;
        while (rover != null) {
            int compare = item.compareTo(rover.data);
            if (compare < 0) {
                rover = rover.left;
            } else if (compare > 0) {
                rover = rover.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public Node locate(Node node, T item) {
        Node rover = node;
        while (rover != null) {
            int compare = item.compareTo(rover.data);
            if (compare < 0) {
                rover = rover.left;
            } else if (compare > 0) {
                rover = rover.right;
            } else {
                return rover;
            }
        }
        return null;
    }

    public Node locate(T item) {
        return locate(this.root, item);
    }

    public T find(Node node, T item) {
        Node result = locate(node, item);
        return (result == null) ? null : result.data;
    }

    public T find(T item) {
        Node result = locate(item);
        return (result == null) ? null : result.data;
    }

    public Node min(Node node) {
        Node rover = node;
        Node parent = null;
        while (rover != null) {
            parent = rover;
            rover = rover.left;
        }
        return parent;
    }

    public T min() {
        Node result = min(this.root);
        return (result == null) ? null : result.data;
    }

    public Node max(Node node) {
        Node rover = node;
        Node parent = null;
        while (rover != null) {
            parent = rover;
            rover = rover.right;
        }
        return parent;
    }

    public T max() {
        Node result = max(this.root);
        return (result == null) ? null : result.data;
    }

    public T floor(T item) {
        Node rover = this.root;
        Node floor = null;
        while (rover != null) {
            int compare = item.compareTo(rover.data);
            if (compare < 0) {
                rover = rover.left;
            } else if (compare > 0) {
                floor = rover;
                rover = rover.right;
            } else {
                return rover.data;
            }
        }
        return (floor == null) ? null : floor.data;
    }

    public T ceiling(T item) {
        Node rover = this.root;
        Node ceiling = null;
        while (rover != null) {
            int compare = item.compareTo(rover.data);
            if (compare < 0) {
                ceiling = rover;
                rover = rover.left;
            } else if (compare > 0) {
                rover = rover.right;
            } else {
                return rover.data;
            }
        }
        return (ceiling == null) ? null : ceiling.data;
    }

    private int size(Node node) {
        if (node != null) {
            return size(node.left) + size(node.right) + 1;
        } else {
            return 0;
        }
    }

    public int rank(Node node, T item) {
        if (node == null) {
            return 0;
        }
        int compare = item.compareTo(node.data);
        if (compare < 0) {
            return rank(node.left, item);
        } else if (compare > 0) {
            return 1 + size(node.left) + rank(node.right, item);
        } else {
            return size(node.left);
        }
    }

    public int rank(T item) {
        return rank(this.root, item);
    }

    public T select(Node node, int rank) {
        if (node == null) {
            return null;
        }
        int mid = size(node.left);
        if (rank < mid) {
            return select(node.left, rank);
        } else if (rank > mid) {
            return select(node.right, rank - mid - 1);
        } else {
            return node.data;
        }
    }

    public T select(int rank) {
        return select(this.root, rank);
    }

    private class NodeAndTaller {

        public boolean taller;
        public Node node;

        public NodeAndTaller(Node node, boolean taller) {
            this.node = node;
            this.taller = taller;
        }
    }

    public NodeAndTaller add(Node node, T item) {
        boolean taller = false;
        if (node == null) {
            return new NodeAndTaller(new Node(item), true);
        } else {
            int compare = item.compareTo(node.data); //Compare to
            if (compare < 0) { //We want to go the left
                NodeAndTaller left = add(node.left, item); //Add it to the left
                node.left = left.node; //Set current node's left to the node we got back
                if (left.taller) { //If the subtree got bigger
                    switch (node.balance) {
                        case LEFT:
                            
                            //The following two blocks of code set hadRightChild
                            //and hadLeft child and saves their balances before we rotate
                            boolean hadRightChild = node.right != null;
                            Balance rightBal = null;
                            if(hadRightChild) rightBal = node.right.balance;
                                
                            boolean hadLeftChild = node.left != null;
                            Balance leftBal = null;
                            if(hadLeftChild) leftBal = node.left.balance;
                            
                            //If we need to do a DOUBLE rotation because we were left leaning
                            //and now our subree is right leaning
                            if(left.node.balance == Balance.RIGHT){
                                if(debug) System.out.println("Double rotation needed (l-r) around"+ node.left.value());
                                Node prevNode = node.left; //Save the node that we are about to rotate around first
                                node.left = leftRotate(node.left);
                                //If the children of the left child are balanced, we are balanced
                                if((prevNode.left != null && prevNode.left.balance == Balance.EVEN) && (prevNode.right != null && prevNode.left.balance == Balance.EVEN))
                                    prevNode.balance = Balance.EVEN;
                                //now set a temp node to oldNode before we do our final rotation
                                Node oldNode = node;
                                //node = leftRotate(node);
                                //If we had a left child and its balance was even
                                if(hadLeftChild){
                                    if(leftBal == Balance.EVEN)
                                        oldNode.balance = Balance.LEFT;
                                }
                                //If not it will be evenly balanced
                                else
                                    oldNode.balance = Balance.EVEN;
                                if((node.left.left != null && node.left.left.balance == Balance.EVEN) && (node.left.right != null && node.left.right.balance == Balance.EVEN))
                                    node.left.balance = Balance.EVEN;
                                
                                
                                if(debug)System.out.println("   Post left rotation");
                                if(debug) System.out.print("    ");
                                if(debug) print();
                                if(debug) System.out.println("  Rotating right around " + node.value());
                                
                                if(node.balance == Balance.RIGHT){
                                    node.balance = Balance.LEFT;
                                }
                                else if(node.balance == Balance.LEFT)
                                    node.balance = Balance.RIGHT;
                                
                                oldNode = node;
                                node = rightRotate(node);
                                
                                if(node.right != null && node.right.right == null && node.right.left == null)
                                    node.right.balance = Balance.EVEN;
                                if(node.left != null && node.left.right == null && node.left.left == null)
                                    node.left.balance = Balance.EVEN;  
                                if(node.balance == Balance.EVEN)
                                    node.balance = Balance.RIGHT;
                                else node.balance = Balance.EVEN;
                                System.out.println("node " + node.value());
                                if(node.left.balance == Balance.EVEN && node.right.balance == Balance.EVEN)
                                    node.balance = Balance.EVEN;
                            }
                            
                            else{
                                //If we just need a right rotation
                                if(debug) System.out.println("Right rotation needed around " + node.value());
                                //Save our old node
                                Node oldNode = node;
                                node = rightRotate(node);
                                //If oldNode's children are both even then we are even
                                if(hadLeftChild && hadRightChild && oldNode.left.balance == Balance.EVEN && oldNode.right.balance == Balance.EVEN)
                                    oldNode.balance = Balance.EVEN;
                                //If we had a right child and it is even
                                else if(hadRightChild){
                                    if(rightBal == Balance.EVEN)
                                        oldNode.balance = Balance.RIGHT;
                                }
                                else
                                    oldNode.balance = Balance.EVEN;
                                if(node.left.balance == Balance.EVEN && node.right.balance == Balance.EVEN)
                                    node.balance = Balance.EVEN;
                            }
                            break;
                            
                        case EVEN: //If we were even and our left subree got taller, we are now left leaning
                            node.balance = Balance.LEFT;
                            taller = true;
                            break;
                        case RIGHT: //If we were right leaning and our left subree got taller
                            node.balance = Balance.EVEN;
                            taller = false;
                            break;
                    }
                } else {
                    taller = false;
                }

                
                //The exact same except left and right are swapped
            } else if (compare > 0) {
                NodeAndTaller right = add(node.right, item);
                node.right = right.node;
                if (right.taller) {
                    switch (node.balance) {
                        case RIGHT:
                            boolean hadRightChild = node.right != null;
                            Balance rightBal = null;
                            if(hadRightChild) rightBal = node.right.balance;
                                
                            boolean hadLeftChild = node.left != null;
                            Balance leftBal = null;
                            if(hadLeftChild) leftBal = node.left.balance;

                            if(right.node.balance == Balance.LEFT){
                                if(debug) System.out.println("Double rotation needed (r-l) around " + node.right.value());
                                Node prevNode = node.right;
                                node.right = rightRotate(node.right);
                                if((prevNode.left != null && prevNode.left.balance == Balance.EVEN) && (prevNode.right != null && prevNode.left.balance == Balance.EVEN))
                                    prevNode.balance = Balance.EVEN;                              
                                
                                Node oldNode = node;

                                if(hadRightChild){
                                    if(rightBal == Balance.EVEN)
                                        oldNode.balance = Balance.RIGHT;
                                }
                                else
                                    oldNode.balance = Balance.EVEN;
                                if((node.left != null && node.left.balance == Balance.EVEN) && (node.right != null && node.right.balance == Balance.EVEN))
                                    node.balance = Balance.EVEN;
                                
                                if(debug)System.out.println("   Post right rotation");
                                if(debug) System.out.print("    ");
                                if(debug) print();
                                if(debug) System.out.println("  Rotating left around " + node.value());
                                
                                if(node.balance == Balance.RIGHT){
                                    node.balance = Balance.LEFT;
                                }
                                else if(node.balance == Balance.LEFT)
                                    node.balance = Balance.RIGHT;
                                
                                
                                oldNode = node;
                                node = leftRotate(node);
                                System.out.println("node " + node.value());
                                if(node.right != null && node.right.right == null && node.right.left == null)
                                    node.right.balance = Balance.EVEN;
                                if(node.left != null && node.left.right == null && node.left.left == null)
                                    node.left.balance = Balance.EVEN;        
                                
                                
                                if(node.balance == Balance.EVEN)
                                    node.balance = Balance.RIGHT;
                                else node.balance = Balance.EVEN;
                                        
                                if(node.left.balance == Balance.EVEN && node.right.balance == Balance.EVEN)
                                    node.balance = Balance.EVEN;
                            }

                            else{
                                if(debug) System.out.println("Left rotation needed aroud " + node.value());
                                Node oldNode = node;
                                node = leftRotate(node);
                                
                                if(hadLeftChild && hadRightChild && oldNode.left.balance == Balance.EVEN && oldNode.right.balance == Balance.EVEN)
                                    oldNode.balance = Balance.EVEN;
                                else if(hadLeftChild){
                                    if(leftBal == Balance.EVEN)
                                        oldNode.balance = Balance.LEFT;
                                    else
                                        oldNode.balance = Balance.EVEN;
                                }
                                else
                                    oldNode.balance = Balance.EVEN;
                                if(node.left.balance == Balance.EVEN && node.right.balance == Balance.EVEN)
                                    node.balance = Balance.EVEN;
                                
                                
                            }
                            break;
                        case EVEN:
                            node.balance = Balance.RIGHT;
                            taller = true;
                            break;
                        case LEFT:
                            node.balance = Balance.EVEN;
                            taller = false;
                            break;
                    }
                } else {
                    taller = false;
                }

            } else {
                node.data = item;
                taller = false;
            }
            return new NodeAndTaller(node, taller);
        }
    }

    public Node rightRotate(Node nodeIn) {
        Node tmp = nodeIn.left; //keep the left of the given node
        nodeIn.left = nodeIn.left.right; //Set the left of the given node to the right of the left of the given node
        tmp.right = nodeIn; //basically the main swap
        return tmp; //return our left node as the new one
    }

    //The same as rightRotate with left and right
    public Node leftRotate(Node nodeIn) {
        Node tmp = nodeIn.right;
        nodeIn.right = nodeIn.right.left;
        tmp.left = nodeIn;
        return tmp;
    }

    private Node recursiveAdd(Node node, T item) {
        if (node == null) {
            return new Node(item);
        } else {
            int compare = item.compareTo(node.data);
            if (compare < 0) {
                node.left = recursiveAdd(node.left, item);
            } else if (compare > 0) {
                node.right = recursiveAdd(node.right, item);
            } else {
                node.data = item;
            }
        }
        return node;
    }

    public void add(T item) {
        this.root = add(this.root, item).node;
    }

    public interface Action<T> {

        public void visit(T data, int depth);
    }

    public void preorder(Action<T> action) {
        preorder(root, action, 0);
    }

    public void preorder(Node node, Action<T> action, int depth) {
        if (node != null) {
            action.visit(node.data, depth);
            preorder(node.left, action, depth + 1);
            preorder(node.right, action, depth + 1);
        }
    }

    public void postorder(Action<T> action) {
        postorder(root, action, 0);
    }

    public void postorder(Node node, Action<T> action, int depth) {
        if (node != null) {
            postorder(node.left, action, depth + 1);
            postorder(node.right, action, depth + 1);
            action.visit(node.data, depth);
        }
    }

    public void inorder(Action<T> action) {
        inorder(root, action, 0);
    }

    public void inorder(Node node, Action<T> action, int depth) {
        if (node != null) {
            inorder(node.left, action, depth + 1);
            action.visit(node.data, depth);
            inorder(node.right, action, depth + 1);
        }
    }

    private String indent(int depth) {
        String result = "";
        while (depth-- > 0) {
            result += "  ";
        }
        return result;
    }

    public void print(Node node, int depth) {
        if (node != null) {
            print(node.left, depth + 1);
            System.out.println(indent(depth) + node.data + "(" + node.balance + ")");
            print(node.right, depth + 1);
        }
    }

    public void print() {
        print(this.root, 0);
    }

    private static String getArgument(String line, int index) {
        String[] words = line.split("\\s");
        return words.length > index ? words[index] : "";
    }

    private static String getCommand(String line) {
        return getArgument(line, 0);
    }

    public static void main(String[] args) {
        Console console = System.console();
        Homework_15<String> tree;

        if (console == null) {
            System.err.println("No console");
            return;
        }

        if (args.length > 0) {
            tree = new Homework_15<String>(args);
        } else {
            tree = new Homework_15<String>();
        }

        String line = console.readLine("Command: ").trim();
        while (line != null) {
            String command = getCommand(line);
            String arg = getArgument(line, 1);

            switch (command) {
                case "size":
                    System.out.println(tree.numberNodes());
                    break;

                case "height":
                    System.out.println(tree.height());
                    break;

                case "add":
                    tree.add(arg);
                    break;

                case "contains":
                    System.out.println(tree.contains(arg));
                    break;

                case "find":
                    System.out.println(tree.find(arg));
                    break;

                case "floor":
                    System.out.println(tree.floor(arg));
                    break;

                case "ceiling":
                    System.out.println(tree.ceiling(arg));
                    break;

                case "min":
                    System.out.println(tree.min());
                    break;

                case "max":
                    System.out.println(tree.max());
                    break;

                case "rank":
                    System.out.println(tree.rank(arg));
                    break;

                case "select":
                    try {
                        int rank = Integer.parseInt(arg);
                        System.out.println(tree.select(rank));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rank: " + arg);
                    }
                    break;

                case "clear":
                    tree = new Homework_15<String>();
                    break;

                case "tree":
                    Action<String> action = new Action<String>() {
                        @Override
                        public void visit(String data, int depth) {
                            while (depth-- > 0) {
                                System.out.print("  ");
                            }
                            System.out.println(data);
                        }
                    };
                    tree.inorder(action);
                    break;

                case "inorder":
                    tree.inorder((data, depth) -> System.out.println(data));
                    break;

                case "pre":
                case "preorder":
                    tree.preorder((data, depth) -> System.out.println(data));
                    break;

                case "post":
                case "postorder":
                    tree.postorder((data, depth) -> System.out.println(data));
                    break;

                case "print":
                    tree.print();
                    break;

                case "help":
                    System.out.println("size             Size of the tree");
                    System.out.println("height           Height of the tree");
                    System.out.println("min              Smallest item in the tree");
                    System.out.println("max              Largest item in the tree");
                    System.out.println("add <key>        Add an item");
                    System.out.println("contains <key>   Is an item in the tree");
                    System.out.println("find <key>       Finds an item");
                    System.out.println("floor <key>      Finds largest item <= key");
                    System.out.println("ceiling <key>    Finds smallest item >= key");
                    System.out.println("rank <key>       Finds the rank of the key");
                    System.out.println("select <k>       Finds the kth smallest item");
                    System.out.println("remove <key>     Remove an item");
                    System.out.println("delete <key>     Remove an item");
                    System.out.println("delmin           Removes the smallest item");
                    System.out.println("delmax           Removes the largest item");
                    System.out.println("clear            Deletes the entire tree");
                    System.out.println("print            Displays the tree");
                    System.out.println("exit             Exits the program");
                    break;
                case "exit":
                case "quit":
                    return;

                default:
                    System.out.println("Unknown command: " + command);
                    break;
            }
            line = console.readLine("Command: ").trim();
        }
    }
}
