/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   May 30, 2018
 */
public class a {
    static String aa = "OR,WA|WA,NV|NV,ND|ND,MN|MN,KS|KS,TN|TN,AZ|AZ,UT|UT,ID|ID,CA|CA,LA|LA,NM|NM,RI|RI,WV|WV,CO|CO,TX|TX,MN|MN,MI|MI,OK|OK,MT|MT,VA|VA,SD|SD,IL|IL,WY|WY,FL|FL,AK|AK,NH|NH,CT|CT,WI|WI,VT|VT,MA|MA,NE|NE,NY|NY,IA|IA,GA|GA,MI|MI,AL|AL,NJ|NJ,ID|ID,DE|DE,MO|MO,MD|MD,PA|PA,NC|NC,OH|OH,SC|SC,KY|KY,DC|DC,OR|";
    public static void main(String[] args){
        SymbolGraph graph = new SymbolGraph(true);
        boolean debug = false;
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
        
        int count = 0;
        for(int i = 0; i < aa.length(); i++){
            if(!aa.substring(i, i+1).equals("|")){
                String edge = aa.substring(i, i+5);
                System.out.println("Edge: " + edge);
                State one = States.find(edge.substring(0,edge.indexOf(",")));
                State two = States.find(edge.substring(edge.indexOf(",") + 1,edge.length()));
                System.out.println("One: " + one.name());
                System.out.println("Two: " + two.name());
                count += (int)one.capital().distance(two.capital());
                i += 5;
            }
        }
        System.out.println(count);
    }
}
