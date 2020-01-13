
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.Color;

/*
   Aidan Chandra
   Homework #
   Dec 1, 2017
 */
public class zoltanShip {

    static BufferedImage fire = getFire();

    public static BufferedImage getFire() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("ref/fire.jpg"));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUUND");
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public static BufferedImage getFireImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("shots/fireCurrentImage.jpg"));

        } catch (IOException e) {
            System.out.println("AAAAAHhhhh");
            return null;
        }
        return img;
    }

    public static void takeImage(String name) {
        try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "shots/" + name + "." + format;
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            ImageIO.write(screenFullImage, format, new File(fileName));
            System.out.println("A full screenshot saved!");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }

    public static ArrayList<Point> getFires() {
        takeImage("fireCurrentImage");
        BufferedImage currentImage = getFireImage();
        int notAlike = 0;
        ArrayList<Point> points = new ArrayList();

        for (int x = 0; x < 1280; x++) {
            for (int y = 310; y < 1060; y++) {
                //For every x/y pixel

                notAlike = 0;

                for (int x2 = x; (x2 - x < fire.getWidth()) && x2 < currentImage.getWidth(); x2++) {
                    for (int y2 = y; (y2 - y < fire.getHeight()) && y2 < currentImage.getHeight(); y2++) {
                        //For each 

                        int thisPixel = currentImage.getRGB(x2, y2);

                        int firePixel;
                        try {
                            firePixel = fire.getRGB(x2 - x, y2 - y);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                        Color currentColor = new Color(thisPixel);
                        Color fireColor = new Color(firePixel);
                        int difference = (Math.abs(currentColor.getRed() - fireColor.getRed())) + (Math.abs(currentColor.getBlue() - fireColor.getBlue())) + (Math.abs(currentColor.getGreen() - fireColor.getGreen()));
                        if (difference > 30) {
                            notAlike++;
                        }
                        if (notAlike >= ((fire.getWidth() * fire.getHeight()) - 10)) {
                            break;
                        }
                    }
                }
                if (notAlike < ((fire.getWidth() * fire.getHeight()) - 10)) {
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    public static ArrayList<Point> findOccurances(BufferedImage image, BufferedImage toFind) {
        ArrayList<Point> points = new ArrayList();

        for (int x = 0; x < image.getWidth() - toFind.getWidth(); x++) {
            for (int y = 0; y < image.getHeight() - toFind.getHeight(); y++) {
                
                BufferedImage subImage = image.getSubimage(x, y, toFind.getWidth(), toFind.getHeight());
                if(bufferedImagesEqual(subImage, toFind)){
                    points.add(new Point(x, y));
                }
            }
        }
        return points;
    }

    static boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        int bad = 0;
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    Color img1c = new Color(img1.getRGB(x, y));
                    Color img2c = new Color(img2.getRGB(x, y));
                    if ((Math.abs(img1c.getRed() - img2c.getRed())) + (Math.abs(img1c.getBlue() - img2c.getBlue())) + (Math.abs(img1c.getGreen() - img2c.getGreen())) > 90){
                        return false;
                    }
                    if(bad >= 10) return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
