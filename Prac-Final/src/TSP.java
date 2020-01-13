
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
   Aidan Chandra
   Homework #26
   May 22, 2018
 */
public class TSP {

    private static SymbolGraph graph; //Graph
    private static int[][] dataTable;
    
    private static boolean debug = false;

    private static State[] northWest = new State[]{States.CA, States.NV, States.OR, States.WA, States.ID, States.UT, States.AZ, States.CO, States.WY, States.MT, States.ND, States.SD};
    private static State[] south = new State[]{States.NE, States.KS, States.OK, States.NM, States.TX, States.IA, States.MO, States.LA, States.AR, States.MS, States.AL, States.TN};
    private static State[] northEast = new State[]{States.MN, States.WI, States.IL, States.IN, States.OH, States.MI, States.KY, States.WV, States.VA, States.NC, States.SC, States.MD, States.DE, States.PA, States.NJ, States.NY, States.CT, States.RI, States.MA, States.NH, States.VT, States.NE, States.DC};
    
    private static boolean[] visited;
    
    private static int n = -1; //Size 
    private static int run = 0;
    
    private static class edge{
        public SymbolGraph.Vertex home;
        public SymbolGraph.Vertex outOne;
        public edge(SymbolGraph.Vertex home, SymbolGraph.Vertex end){
            this.home = home;
            this.outOne = end;
        }
        @Override
        public boolean equals(Object other){
            edge others = (edge)other;
            return (this.home.index() == others.home.index() && this.outOne.index() == others.outOne.index()) || (this.home.index() == others.outOne.index() && this.outOne.index() == others.home.index());
        }
        @Override
        public String toString(){
            return States.find(home.name()).code() + "," + States.find(outOne.name()).code();
        }
        public int weight(){
            return getDistance(home, outOne);
        }
    }
    
    public static void main(String[] args) {
        dataTable = new int[49][49];
        visited = new boolean[49];
        resetVisited();
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
        
        for(int i = 0; i < dataTable.length; i++){
            State a = States.find(graph.get(i).name());
            for(int j = 0; j < dataTable[i].length; j++){
                State b = States.find(graph.get(j).name());
                int dist = (int)States.find(a.name()).capital().distance(States.find(b.name()).capital());
                dataTable[i][j] = dist;
            }
        }
        
        //while (true) {
            long lStartTime = System.nanoTime();
            SymbolGraph.Vertex leftStart = graph.find(northWest[random(0,northWest.length)].name());
            visited[leftStart.index()] = true;
            SymbolGraph.Vertex mid = graph.find(south[random(0,south.length)].name());
            visited[mid.index()] = true;
            SymbolGraph.Vertex rightStart = graph.find(northEast[random(0,northEast.length)].name());
            visited[rightStart.index()] = true;
            ArrayList<edge> edgeList = new ArrayList();
            
            edgeList.add(new edge(leftStart,mid));
            edgeList.add(new edge(mid,rightStart));
            edgeList.add(new edge(rightStart,leftStart));
            printEdgeList(edgeList);
            
            while(edgeList.size() < 50){
                
                int rand = random(0,49);
                if(visited[rand])
                    continue;
                
                
                System.out.println("Run");
                SymbolGraph.Vertex toInsert = graph.get(rand);
                visited[toInsert.index()] = true;
                
                //Select a random unvisited from left
                //Select a random unvisited from middle
                //Select a random unvisited from right
                
                SymbolGraph.Vertex leftRand = graph.find(northWest[random(0,northWest.length)].name());
                while(!visited[leftRand.index()])
                    leftRand = graph.find(northWest[random(0,northWest.length)].name());
                SymbolGraph.Vertex leftCo = edgeList.get(findEInt(leftRand,edgeList)).outOne;
                
                
                
                SymbolGraph.Vertex midRand = graph.find(south[random(0,south.length)].name());
                while(!visited[midRand.index()])
                    midRand = graph.find(south[random(0,south.length)].name());
                SymbolGraph.Vertex midCo = edgeList.get(findEInt(midRand,edgeList)).outOne;
                
                
                SymbolGraph.Vertex rightRand = graph.find(northEast[random(0,northEast.length)].name());
                while(!visited[rightRand.index()])
                    rightRand = graph.find(northEast[random(0,northEast.length)].name());
                SymbolGraph.Vertex rightCo = edgeList.get(findEInt(rightRand,edgeList)).outOne;
                
                System.out.println(leftCo.name() + " " + rightCo.name() + " "+ midCo.name());
                
                //Test which one to insert it into
                
                int distLeft = getDistance(leftRand, leftCo);
                int distMid = getDistance(midRand, midCo);
                int distRight = getDistance(rightRand, rightCo);
                
                int newLeft = getDistance(leftRand, toInsert) + getDistance(toInsert, leftCo);
                int newMid = getDistance(midRand, toInsert) + getDistance(toInsert, midCo);
                int newRight = getDistance(rightRand, toInsert) + getDistance(toInsert, rightCo);
                
                int deltaLeft = distLeft - newLeft;
                int deltaMid = distMid - newMid;
                int deltaRight = distRight - newRight;
                
                int minOfAll = Math.min(deltaLeft, Math.min(deltaMid, deltaRight));
                
                if(minOfAll == deltaLeft){ //Inserting it between left and leftCo
                    edgeList.remove(findInt(new edge(leftRand, leftCo), edgeList)); //Remove that entire edge
                    edgeList.add(new edge(toInsert, leftRand));
                    edgeList.add(new edge(leftCo, toInsert));
                }
                if(minOfAll == deltaMid){ //Inserting it between right and rightCo
                    edgeList.remove(findInt(new edge(midRand, midCo), edgeList)); //Remove that entire edge
                    edgeList.add(new edge(toInsert, midRand));
                    edgeList.add(new edge(midCo, toInsert));
                }
                if(minOfAll == deltaRight){ //Inserting it between mid and right
                    edgeList.remove(findInt(new edge(rightRand, rightCo), edgeList)); //Remove that entire edge
                    edgeList.add(new edge(toInsert, rightRand));
                    edgeList.add(new edge(rightCo, toInsert));
                }
                
                
                visited[rand] = true;
            }
            
            
            
            long lEndTime = System.nanoTime();
            long output = lEndTime - lStartTime;
            
            //"Run " + run + "      " + output / 1000 + "uS" + "    " + 
            logToFile(getTour(edgeList));
            run++;
            resetVisited();
            
        //}
    }

    private static void logToFile(String message) {
        try (FileWriter fw = new FileWriter("log.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            
        }
    }
    private static int random(int min, int max){
        int toReturn = (int)(Math.random() * (max - min) + min);
        if(toReturn == max)
            return random(min,max);
        return toReturn;
    }
    private static int getDistance(SymbolGraph.Vertex one, SymbolGraph.Vertex two){
        return dataTable[one.index()][two.index()];
    }
    private static void resetVisited(){
        for(int i = 0; i < 49; i++)
            visited[i] = false;
    }
//    private static edge find(edge toFind, ArrayList<edge> arr){
//        for(edge e : arr){
//            if(e.equals(toFind))
//                return e;
//        }
//        return null;
//    }
    private static int findInt(edge toFind, ArrayList<edge> arr){
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).equals(toFind)){
                return i;
            }
        }
        return -1;
    }
    private static int findEInt(SymbolGraph.Vertex toFind, ArrayList<edge> arr){
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).home.index() == toFind.index()){// || arr.get(i).outOne.index() == toFind.index()){
                return i;
            }
        }
        return -1;
    }
    private static int findEIntExc(SymbolGraph.Vertex toFind, ArrayList<edge> arr, SymbolGraph.Vertex exclusive){
        for(int i = 0; i < arr.size(); i++){
            if((arr.get(i).home.index() == toFind.index() && arr.get(i).home.index() != exclusive.index()) || (arr.get(i).outOne.index() == toFind.index() && arr.get(i).outOne.index() != exclusive.index())){
                return i;
            }
        }
        return -1;
    }
    private static String getTour(ArrayList<edge> arr){
        String returnable = "";
        int weight = 0;
        for(edge e : arr){
            returnable += e.toString() + "|";
            weight += e.weight();
        }
        return weight + "    " + returnable + "       ";
    }
    private static void printEdgeList(ArrayList<edge> arr){
        for(edge a : arr)
            System.out.print(a + " ");
        System.out.println("");
    }
}
