
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
   Aidan Chandra
   Homework #
   May 28, 2018
 */
public class minFinder {
    static int min = Integer.MAX_VALUE;
    static String tour = "";
    static int count = 0;
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("log.txt"))) {
            for (String line; (line = br.readLine()) != null;) {
                count++;
                int[] states = new int[49];
                int weight = Integer.valueOf(line.substring(0,line.indexOf(" ")));
                if(weight < min){
                    min = weight;
                    tour = line.substring(line.indexOf(" "), line.length());
                    System.out.println("Found min of " + min + " " + tour);
                }
            }
        }
        System.out.println("Total number of tours examined: " + count);
    }
}
