
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
   Aidan Chandra
   Homework #
   Dec 1, 2017
 */
public class Test {
    public static void main(String[] args) throws IOException{
        ArrayList<Point> points = zoltanShip.findOccurances(ImageIO.read(new File("tst/ship2.jpg")),ImageIO.read(new File("tst/zoltanhead.jpg")));
        for(Point p : points)
            System.out.println(p);
        
    }
}
