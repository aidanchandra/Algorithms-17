/*\
    Aidan Chandra
    Homework 16
*/
import java.io.Console;
import java.math.BigInteger;

public class Homework_16<Key, Value> {

    private boolean quadratic = false;  // Use quadratic probing
    private boolean primeSize = false;  // Don't round capacity up to next prime
    private double maxLoad = 0.5;    // Load factor at which table is resized

    private boolean debug = false;
    private Value[] arr;
    private Key[] kArr;
    private int size = 0;
    private int capacity;
    private static double loadThreshold = 0.5;

    private int comparisons = 0;
    private int searches = 0;

    private int nextPrime(int n) {  // Returns next prime number following n
        return BigInteger.valueOf(n).nextProbablePrime().intValue();
    }

    public Homework_16(int capacity, boolean quadratic, boolean primeSize) {
        if (primeSize) {
            capacity = nextPrime(capacity);
        }
        this.quadratic = quadratic;
        this.primeSize = primeSize;
        arr = (Value[]) new Object[capacity];
        kArr = (Key[]) new Object[capacity];
        this.capacity = arr.length;
    }

    public Homework_16(int capacity) {
        this(capacity, false, false);
    }

    public Homework_16() {
        this(16, false, false); //Should be 16
    }

    public int capacity() {
        return this.capacity;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

 
    public int hash(Key key) {
        // Returns the index of the key in the table
        // (provided that there are no collisions).
        return (key.hashCode() & 0x7FFFFFFF) % this.capacity();
    }

    public boolean contains(Key key) {
        comparisons++; //We do at least one comparison
        int index = hash(key); //Find where to start
        searches++;
        while(kArr[index] != key){ //Starting at the start point, look for the right thing
            index++; //Go to the next slot
            //Account for overflow
            if(index == arr.length)
                index = 0;
            //If we went all the way around it isnt in the list
            if(index == hash(key))
                return false;
            comparisons++;
        }
        return true;
    }

    public Value find(Key key) {
        //Same as contains but returns the thing at that index
        comparisons++;
        int index = hash(key);
        searches++;
        while(kArr[index] != key){
            index++;
            if(index == arr.length)
                index = 0;
            if(index == hash(key))
                return null;
            comparisons++;
        }
        return arr[index];
    }

    public void add(Key key, Value value) {
        if (debug) {
            System.out.println("    Adding: " + key + "," + value);
        }
        // Adds a key/value pair to the map.
        // If key is in the map, it updates the value.
        // Resizes the map if the load factor exceeds 50%

        double load = this.loadFactor();
        if (debug) {
            System.out.println("    Load Factor: " + load);
        }
        if (load >= loadThreshold) {
            if (debug) {
                System.out.println("    Resize required");
            }
            Value[] newArr;
            Key[] newKArr;
            if (primeSize) {
                newArr = (Value[]) new Object[this.nextPrime(this.capacity() * 2)];
                newKArr = (Key[]) new Object[newArr.length];

            } else {
                newArr = (Value[]) new Object[this.capacity() * 2];
                newKArr = (Key[]) new Object[newArr.length];

            }

            //We now have a new array to rehash into
            if (this.quadratic) {
                Value[] tva = newArr;
                Key[] kva = newKArr;
                newArr = arr;
                newKArr = kArr;
                arr = tva;
                kArr = kva;
                for(int i = 0; i < newArr.length; i++){
                    add(newKArr[i], newArr[i]);
                }
                add(key, value);
            } 
            else {
                for (Key k : kArr) {
                    comparisons++;
                    if (k == null) {
                        continue;
                    }
                    capacity = arr.length;
                    int oldIndexOfValue = hash(k);
                    
                    comparisons++;
                    searches++;
                    while(kArr[oldIndexOfValue] != k){
                        oldIndexOfValue++;
                        comparisons++;
                        if (oldIndexOfValue == kArr.length) {
                            oldIndexOfValue = 0;
                        }
                        comparisons++;
                    }
                    
                    capacity = newArr.length;
                    int newIndexOfValue = hash(k);

                    comparisons++;
                    while (newArr[newIndexOfValue] != null) {
                        
                        newIndexOfValue++;
                        comparisons++;
                        if (newIndexOfValue == newArr.length) {
                            newIndexOfValue = 0;
                        }
                        comparisons++;
                    }
                    newArr[newIndexOfValue] = arr[oldIndexOfValue];
                    newKArr[newIndexOfValue] = kArr[oldIndexOfValue];
                }
            }
            //Now, we have REHASHED EVERYTHING
            capacity = newArr.length;
            arr = newArr;
            kArr = newKArr;
            this.add(key, value);
            //Now to actually add the new element

        } 
        
        
        
        else {
            if (this.quadratic) {
                int index = hash(key);
                comparisons++;
                if (kArr[index] != null) { //If the key slot is filled
                    int oldIndex = index;
                    int step = 1;
                    comparisons++;
                    comparisons++;
                    searches++;
                    while (kArr[index] != null && !kArr[index].equals(key)) {
                        index += step;
                        step *= 2;
                        if (index >= arr.length) {
                            int a = index / step;
                            index -= a * (step);
                        }
                        comparisons += 2;
                    }

                    comparisons += 2;
                    if (index == oldIndex) {
                        arr[index] = value;
                    } 
                    else {
                        arr[index] = value;
                        kArr[index] = key;
                    }
                } 
                else {
                    kArr[index] = key;
                    arr[index] = value;
                }
                
            } 
            else {
                int index = hash(key);
                comparisons++;
                if (kArr[index] != null) { //If the key slot is filled
                    int oldIndex = index;
                    comparisons++;
                    comparisons++;
                    searches++;
                    while (kArr[index] != null && !kArr[index].equals(key)) {
                        index++;
                        comparisons++;
                        if (index == arr.length) {
                            index = 0;
                        }
                        comparisons += 2;
                    }
                    comparisons++;
                    if (index == oldIndex) {
                        arr[index] = value;
                    } 
                    else {
                        arr[index] = value;
                        kArr[index] = key;
                    }
                } 
                else {
                    kArr[index] = key;
                    arr[index] = value;
                }
            }
            size++;
        }
    }

    public void remove(Key key) {
        if (this.quadratic) {
            System.out.println("Removal NOT implemented for quadratic probing");
            System.out.println("Did NOT delete");
        } else {
            int index = hash(key);

            comparisons++;
            searches++;
            while (!kArr[index].equals(key)) {
                index++;
                if (index == arr.length) {
                    index = 0;
                }
                comparisons++;
            }
            arr[index] = null;
            kArr[index] = null;
            //Now we have found and removed the correct thing
            index++;
            size--;
            if (index == arr.length) {
                index = 0;
            }

            int count = 0;
            comparisons++; 
            searches++;
            while (arr[index] != null) {
                count++;
                searches++;
                searches++;
                Value val = arr[index];
                Key k = kArr[index];
                arr[index] = null;
                kArr[index] = null;
                size--;
                add(k, val);
                index++;
                if (index == arr.length) {
                    index = 0;
                }
                if (count == arr.length) {
                    return; //Array now has NO null elements
                }
                if (arr[index] != null) {
                    searches++;
                    comparisons++;
                }
            }
            //We reshash the rest
        }
    }

    public void print() {
        for (int i = 0; i < capacity; i++) {
            System.out.print(i + ": ");
            if(kArr[i] == null)
                System.out.println("");
            else
                System.out.println(kArr[i] + " => " + arr[i]);
        }
    }

    public int numberClusters() {
        int count = 0;
        boolean hadNull = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                hadNull = true;
            } else {
                if (hadNull) {
                    count++;
                    hadNull = false;
                }
            }
        }
        if (arr[arr.length - 1] == null && arr[0] != null) {
            count++;
        }
        return count;

    }

    public int largestClusterSize() {
        int largest = 0;
        int index = 0;
        int i;
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                index = i + 1;
                if (index == arr.length) {
                    index = 0;
                }
                while (arr[index] != null) {
                    index++;
                    if (index == arr.length) {
                        index = 0;
                    }
                }
                index--;
                if (index == -1) {
                    index = 16;
                }
                if (index < 2 && i > 5) {
                    if (Math.abs(capacity() - i) > largest) {
                        largest = Math.abs(capacity() - i);
                    }
                } else if (Math.abs(i - index) > 0) {
                    if (Math.abs(index - i) > largest) {
                        largest = Math.abs(index - i);
                    }
                }
            }

        }
        if (arr[arr.length - 1] != null && arr[0] == null) {
            index++;
            //System.out.println("* Cluster of size " + Math.abs(i - index));
        }
        return largest;
    }
    

    public double averageClusterSize() {
        int[] sizes = new int[numberClusters()];
        if(sizes.length == 0)
            return 0;
        int sizesIndex = 0;
        int old = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                old = i;
                while (arr[i] != null) {
                    i++;
                    if(i == arr.length)
                        i = 0;
                }
                int size = i - old;
                sizes[sizesIndex++] = size;
            }
        }
        int sum = 0;
        for(int a : sizes)
            sum += a;
        
        return (double)sum/(double)sizes.length;
    }

    public double loadFactor() {
        return (double) ((double) this.size / (double) this.capacity());
    }

    public int searches() {
        // Return the number of searches (hash table lookups).
        // Includes searches for contains, find, add and remove.
        return searches;
    }

    public int compares() {
        return comparisons;
    }

    public double averageCompares() {
        // Average number of equality tests per search.
        int searches = this.searches();
        int compares = this.compares();
        if (searches > 0) {
            return (double) compares / (double) searches;
        } else {
            return 0.0;
        }
    }

    private static String getArgument(String line, int index) {
        String[] words = line.split("\\s");
        return words.length > index ? words[index] : "";
    }

    private static String getCommand(String line) {
        return getArgument(line, 0);
    }

    private void printStatistics() {
        System.out.println("Size = " + this.size());
        System.out.println("Capacity = " + this.capacity());
        System.out.println("Searches = " + this.searches());
        System.out.println("Compares = " + this.compares());
        System.out.print("Load factor = ");
        System.out.printf("%.2f", this.loadFactor());
        System.out.println("");
        System.out.println("Number clusters = " + this.numberClusters());
        System.out.print("Average cluster size = ");
        System.out.printf("%.2f", this.averageClusterSize());
        System.out.println("");
        System.out.println("Largest cluster size = " + this.largestClusterSize());
        System.out.print("Average compares per search = ");
        System.out.printf("%.2f", this.averageCompares());
        System.out.println("");
    }

    public static void main(String[] args) {
        Homework_16<String, String> map = null;
        Console console = System.console();
        boolean statistics = false;
        boolean quadratic = false;
        boolean prime = true;
        int capacity = 16;
        double load = 0.5;
        String option = "";

        if (console == null) {
            System.err.println("No console");
            return;
        }

        // Command line options:
        //
        //     -prime		  Round capacity up to the next prime number
        //	   -exact		  Don't round capacity up to the next prime
        //     -linear        Use linear probing
        //     -quadratic     Use quadric probing
        //     -capacity <n>  Initial capacity of the hash table
        //     -load <x>      Load factor at which table is resized
        //     -statistics    Print statistics when done
        //     key			  (key, "") is added to the map
        //
        for (String arg : args) {
            switch (arg) {
                case "-prime":
                    prime = true;
                    continue;

                case "-exact":
                    prime = false;
                    continue;

                case "-linear":
                    quadratic = false;
                    continue;

                case "-quadratic":
                    quadratic = true;
                    continue;

                case "-capacity":
                    option = arg;
                    continue;

                case "-load":
                    option = arg;
                    continue;

                case "-statistics":
                    statistics = true;
                    continue;

                default:
                    break;
            }

            switch (option) {
                case "-capacity":
                    try {
                        capacity = Integer.parseInt(arg);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid capacity: " + arg);
                        continue;
                    }
                    break;

                case "-load":
                    try {
                        loadThreshold = Double.parseDouble(arg);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid load factor: " + arg);
                        continue;
                    }
                    break;

                default:
                    if (map == null) {
                        map = new Homework_16<String, String>(capacity, quadratic, prime);
                        map.maxLoad = load;
                    }
                    map.add(arg, "");
                    break;
            }
            option = "";
        }

        // Allow the user to enter commands on standard input:
        //
        //   hash <key>        prints the hash index for a given key
        //   contains <key>    prints true if a key is in the map; false if not
        //   find <key>        prints the value associated with the key
        //   add <key> <value> adds an item to the tree
        //   remove <key>      removes an item from the tree (if present)
        //   clear             removes all items from the tree
        //   print             prints the contents of the hash table
        //   clusters		   prints the number of clusters in the map
        //   average		   prints the average cluster size
        //   largest           prints the largest cluster size
        //   load              prints the load factor
        //   linear            use linear probing**
        //   quadratic         use quadratic probing**
        //   prime			   round up capacity to a prime**
        //   exact			   use exact capacity (don't round up to a prime)**
        //   exit              quit the program
        //
        //   ** takes effect on the next clear command.
        if (map == null) {
            map = new Homework_16<String, String>(capacity, quadratic, prime);
            map.maxLoad = load;
        }

        String line = console.readLine("Command: ").trim();
        while (line != null) {
            String command = getCommand(line);

            switch (command) {
                case "hash":
                    System.out.println(map.hash(getArgument(line, 1)));
                    break;

                case "size":
                    System.out.println(map.size());
                    break;

                case "capacity":
                    System.out.println(map.capacity());
                    break;

                case "contains":
                    System.out.println(map.contains(getArgument(line, 1)));
                    break;

                case "find":
                    System.out.println(map.find(getArgument(line, 1)));
                    break;

                case "add":
                case "insert":
                    map.add(getArgument(line, 1), getArgument(line, 2));
                    break;

                case "delete":
                case "remove":
                    map.remove(getArgument(line, 1));
                    break;

                case "print":
                    map.print();
                    break;

                case "clear":
                    map = new Homework_16<>(capacity, quadratic, prime);
                    break;

                case "stats":
                case "statistics":
                    map.printStatistics();
                    break;

                case "linear":
                    quadratic = false;
                    break;

                case "quadratic":
                    quadratic = true;
                    break;

                case "prime":
                    prime = true;
                    break;

                case "exact":
                    prime = false;
                    break;

                case "end":
                case "exit":
                case "quit":
                    if (statistics) {
                        map.printStatistics();
                    }
                    return;

                default:
                    System.out.println("Invalid command: " + command);
                    break;
            }

            line = console.readLine("Command: ").trim();
        }
    }
}
