/*
   Aidan Chandra
   Homework #2
   Aug 30, 2017
 */
import java.util.Iterator;


public class Tour {
// Constructors to create an empty Tour and
// to create an initialized Tour.
// Check that the tour is valid.
    private Array<City> arr;
    public Tour() {
        arr = new Array();
    }

    public Tour(City[] cities) {
        arr = new Array(cities);
    }

    public int storageSize() {
        return arr.storage();
    } // Number of cities on the tour
    public int size(){
        return arr.size();
    }
    public double length() {
        double returnable = 0;
        for(int i = 0; i < arr.size() - 1; i++){ //For each city get the length to the next
            returnable += arr.get(i).distance(arr.get(i + 1));
        }
        returnable += arr.get(0).distance(arr.get(arr.size() - 1)); //Get the first to the last bc the for loop didnt take of that
            
        return returnable; //Return the distance total
    } 
// Length (distance travelled) of the tour
// Methods to append/prepend cities to the end/beginning of a tour.
// Checks should be made that the tour remains valid.

    public void append(City city) {
        arr.append(city);
    }

    public void prepend(City city) {
        arr.prepend(city);
    }
// This class should implement Iterable and of course
// export and Iterator to traverse the cities in the tour.

    public Iterator iterator() {
        return arr.iterator();
    }
    public Iterator reverseIterator() {
        return arr.reverseIterator();
    }
// And finally  a method to display a tour:

    @Override
    public String toString() {
        return arr.toString();
    }
}
