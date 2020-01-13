
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   May 5, 2018
 */
public class Main {

    private static class dataPoint implements Comparable{
        public String name = "";
        public int val = 0;
        public dataPoint(String name, int value){
            val = value;
            this.name = name.substring(0,2) + ", " + name.substring(2,4);
        }
        @Override
        public int compareTo(Object other){
            dataPoint otherDP = (dataPoint)other;
            return val - otherDP.val;
        }
    }
    public static void main(String[] args){
        ArrayList<dataPoint> list = new ArrayList();
        for(String a : args){
            list.add(new dataPoint(a.substring(0,a.indexOf(",")), Integer.valueOf(a.substring(a.indexOf(",") + 1, a.length()))));
        }
        list.sort(null);
        for(dataPoint a : list)
            System.out.println(a.name + ": " + a.val);
    }
}
