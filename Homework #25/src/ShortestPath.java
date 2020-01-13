
import java.util.ArrayList;
import java.util.PriorityQueue;
/*
   Aidan Chandra
   Homework #25
   May 12, 2018
 */
public class ShortestPath {

    
    //Custom class to store verticies and weights :)
    public static class vertexAndWeight implements Comparable<vertexAndWeight> {

        public SymbolGraph.Vertex v;
        private int weight;

        public vertexAndWeight(SymbolGraph.Vertex v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        //Compare to based off of weight
        public int compareTo(vertexAndWeight other) {
            return weight - other.weight;
        }
        public String toString(){
            return this.v.name() + " (" + String.valueOf(weight) + ")";
        }
        public int hashCode(){
            return this.v.index();
        }
        //EQUALS based off of states
        @Override
        public boolean equals(Object other){
            vertexAndWeight o = (vertexAndWeight)other;
            return this.v.name().equals(o.v.name());
        }
    }
    
    private static int[][] dataTable; //Matrix to store our data
    private static SymbolGraph graph; //Graph
    private static PriorityQueue<vertexAndWeight> pq; //Priority Queue

    private static boolean debug = false;
    
    private static int n = -1; //Size 

    public static void main(String[] args) {
        if(args.length <= 2){
            System.out.println("Not enough agruments supplied, exiting");
            return;
        }
        
        
        graph = new SymbolGraph(true);

        //Initialize the graph
        for (State s : States.states) {
            if (s.toString().equals("Hawaii") || s.toString().equals("Alaska")) {
                continue;
            }
            graph.addVertex(s.name());
            if (debug) {
                System.out.println("S: " + s.name());
            }
            for (State a : s.neighbors()) {
                if (debug) {
                    System.out.println("            A: " + a.toString());
                }
                graph.addEdge(s.toString(), a.name());
            }
        }

        n = graph.vertices();
        dataTable = new int[n][4]; //Create a datatable of states (C1), Distances (C2), Predecessor (C3), and Visited (C4)
        //Set all distances to infinity and their predeccessor to null
        for (int i = 0; i < n; i++) {
            dataTable[i][0] = i; //Its ID
            dataTable[i][1] = Integer.MAX_VALUE; //"Infinity"
            dataTable[i][2] = -1; //"null"
            dataTable[i][3] = 0; //"false"
        }
        pq = new PriorityQueue(n);
        
        
        //Add our starting vertex and set it as visited
        SymbolGraph.Vertex sourceVertex = graph.find(States.find(args[0]).name());
        setDistance(sourceVertex, 0);
        pq.add(new vertexAndWeight(sourceVertex, 0));
        setVisited(sourceVertex, true);

        
        
        //Keep popping stuff off of the priority queue
        while (!pq.isEmpty()) {
            SymbolGraph.Vertex v = pq.poll().v;
            addNeighbours(v);
            setVisited(v,true);
        }
        
        if(debug) printDataTable();
        
        //Now print out our distances and the path it took to get there
        for(int i = 1; i < args.length; i++){
            //Basically get our start state and out state we desire to get to
            State currentState = States.find(args[i]);
            System.out.print(args[0] + " to " + currentState.code() + " is " + getDistance(graph.find(currentState.name())) + " miles: ");
            ArrayList<String> buildArr = new ArrayList();
            SymbolGraph.Vertex v = graph.find(currentState.name());
            
            //Iterate backwards through predecessors, all the while adding to an arraylist
            while(!v.name().equals(States.find(args[0]).name())){
                buildArr.add(States.find(v.name()).code());
                buildArr.add(", ");
                v = getPredecessor(v);
            }
            buildArr.add(States.find(args[0]).code());
            //Print out or backwards array backwards
            for(int j = buildArr.size() - 1; j >= 0; j--){
                System.out.print(buildArr.get(j));
            }
            System.out.println("");
        }
        
    }

    private static void addNeighbours(SymbolGraph.Vertex e){
        //For all the neighbours of "Home" node
        for(SymbolGraph.Vertex neighbour : e.neighbors()){  
            //If it does not exist in the PQ add it to the PQ
            if(!pq.contains(new vertexAndWeight(neighbour, Integer.MAX_VALUE))){  
                //If we DONT want to ignore it
                if(! getVisited(neighbour)){
                    //Add it to the PQ with our best known distance :)
                    pq.add(new vertexAndWeight(neighbour, getDistance(e,neighbour)));
                }
            }
            //If we get to a vertex that is in the PQ and we find a better path to it through e, or we havent visited it yet
            if(getDistance(e) + getDistance(e,neighbour) < getDistance(neighbour) || getDistance(neighbour) == Integer.MAX_VALUE){
                //Change up the predecessor stuff and set the new distance :)
                setPredecessor(neighbour,e);
                setDistance(neighbour, getDistance(e) + getDistance(e,neighbour));
                
                //I learned just as much about Djikstra's as I did about manipulating default classes :)
                
                if(pq.contains(new vertexAndWeight(neighbour, Integer.MAX_VALUE)) && !getVisited(neighbour)){
                    pq.remove(new vertexAndWeight(neighbour, Integer.MAX_VALUE));
                    pq.add(new vertexAndWeight(neighbour, getDistance(neighbour)));
                }
            }
        }
    }

    private static int getDistance(SymbolGraph.Vertex first, SymbolGraph.Vertex second) {
        return (int)States.find(first.name()).capital().distance(States.find(second.name()).capital());
    }

    private static int getDistance(SymbolGraph.Vertex v) {
        return dataTable[v.index()][1];
    }

    private static void setDistance(SymbolGraph.Vertex v, int distance) {
        dataTable[v.index()][1] = distance;
    }

    private static void setPredecessor(SymbolGraph.Vertex v, SymbolGraph.Vertex predecessor) {
        dataTable[v.index()][2] = predecessor.index();
    }
    private static SymbolGraph.Vertex getPredecessor(SymbolGraph.Vertex v) {
        return graph.get(dataTable[v.index()][2]);
    }
    private static int getPredecessorID(SymbolGraph.Vertex v) {
        return dataTable[v.index()][2];
    }

    private static void setVisited(SymbolGraph.Vertex v, boolean visited) {
        if (visited) {
            dataTable[v.index()][3] = 1;
        } else {
            dataTable[v.index()][3] = 1;
        }
    }

    private static boolean getVisited(SymbolGraph.Vertex v) {
        return dataTable[v.index()][3] == 1;
    }

    private static void printDataTable() {
        System.out.println("State               Distance            Pred.       Visited");
        for (int i = 0; i < dataTable.length; i++) {

            for (int j = 0; j < dataTable[i].length; j++) {
                if (j == 0) {
                    System.out.print(graph.get(i).name() + "     ");
                }
                else if(j == 2 && dataTable[i][j] != -1)
                    System.out.print(graph.get(dataTable[i][j]).name() + "      ");
                else {
                    System.out.print(dataTable[i][j] + "     ");
                }
            }

            System.out.println("");
        }
    }

}
