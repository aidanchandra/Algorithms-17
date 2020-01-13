/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
   Aidan Chandra
   Homework #
   May 12, 2018
 */
public class ShortestPath {

    private static class vertexAndWeight implements Comparable<vertexAndWeight> {

        public SymbolGraph.Vertex v;
        private int weight;

        public vertexAndWeight(SymbolGraph.Vertex v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(vertexAndWeight other) {
            return weight - other.weight;
        }
    }
    private static int[][] dataTable;
    private static SymbolGraph graph;
    private static MinPriorityQueue<vertexAndWeight> pq;

    private static boolean debug = false;
    private static String unit = "M";
    
    private static int n = -1;

    public static void main(String[] args) {
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
            dataTable[i][1] = -1; //"Infinity"
            dataTable[i][2] = -1; //"null"
            dataTable[i][3] = 0; //"false"
        }
        pq = new MinPriorityQueue(n);

        SymbolGraph.Vertex sourceVertex = graph.find("California");
        setDistance(sourceVertex, 0);
        pq.add(new vertexAndWeight(sourceVertex, 0));
        setVisited(sourceVertex, true);

        while (!pq.isEmpty()) {
            SymbolGraph.Vertex v = pq.removeMin().v;
            relax(v);
        }
        printDataTable();
        System.out.println("Distance from CA to NV " + getDistance(graph.find("California"), graph.find("Nevada")));
    }

    public static void relax(SymbolGraph.Vertex e) {
        for (SymbolGraph.Vertex v : e.neighbors()) {
            if (v != null) {
                relax(v, e);
            }
        }
    }

    public static void relax(SymbolGraph.Vertex one, SymbolGraph.Vertex two) {
        
        System.out.println("Relaxing " + one.name() + ", with distance of " + getDistance(one) + " and " + two.name() + ", with distance of " + getDistance(two));
        if (getDistance(one, two) < getDistance(one) || getDistance(one) == -1) {
            System.out.println("    -> minimizing with " + getDistance(one,two));
            setDistance(one, getDistance(one, two) + getDistance(one));
            setPredecessor(one, two);
            if(getVisited(one) == false)
                pq.add(new vertexAndWeight(one, getDistance(one) + getDistance(two)));
            setVisited(one, true);
        }
    }

    private static int getDistance(SymbolGraph.Vertex first, SymbolGraph.Vertex second) {
        double xsubone = States.find(first.name()).capital().longitude();
        double xsubtwo = States.find(second.name()).capital().longitude();
        double ysubone = States.find(first.name()).capital().latitude();
        double ysubtwo = States.find(second.name()).capital().latitude();
        return (int)distance(ysubone, xsubone, ysubtwo, xsubtwo, unit);
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

    //I just found this method online
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
