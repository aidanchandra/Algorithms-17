/*
   Aidan Chandra
   Homework #23
   Apr 29, 2018
 */
public class IsTopologicalSort {
    private static boolean debug = false;
    
    public static void main(String[] args) throws Stack.OverflowException, Stack.UnderflowException, Stack.EmptyException{
        SymbolGraph graph = SymbolGraph.read(true);
        System.out.println(isValidPath(graph, args));
    }

    private static int findIndex(String name, String[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i].equals(name))
                return i;
        }
        return -1;
    }
    
    private static boolean isValidPath(SymbolGraph graph, String[] arr){
        if(debug) graph.print(graph);
        
        Boolean isGood[] = new Boolean[graph.vertices()];

        for(int i = 0; i < arr.length; i++){ //For all nodes
           SymbolGraph.Vertex currentNode = graph.find(arr[i]);
           //As long as a given node's neighbours follow after it it is good
           boolean comesAfter = true;
           for(SymbolGraph.Vertex v : currentNode.neighbors()){
               int index = findIndex(v.name(), arr);
               if(index < i){ //If a neighbour comes before the current node, current node is not good
                   if(debug) System.out.println(arr[i] + "(" + String.valueOf(i) + ") is not good because " + v.name() + " comes before ");
                   comesAfter = false;
                }
           }
           //Write to the array if it is good or not
           if(comesAfter)
               isGood[i] = true;
           else
               isGood[i] = false;
        }
        //Print out the boolean array and if any node wasnt satisfied return false
        boolean returnable = true;
        for(Boolean b : isGood){
            if(b && debug) System.out.print("T, ");
            else if(debug) System.out.print("F, ");
            else if(!b) returnable = false;
        }
        if(debug) System.out.println("");
        return returnable;
    }
}
