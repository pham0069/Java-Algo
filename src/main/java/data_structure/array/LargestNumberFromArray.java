package data_structure.array;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * https://www.geeksforgeeks.org/given-an-array-of-numbers-arrange-the-numbers-to-form-the-biggest-number/
 * https://www.geeksforgeeks.org/arrange-given-numbers-form-biggest-number-set-2/
 * Given a list of non negative integers, arrange them in such a manner that they form the largest number possible.The
 * result is going to be very large, hence return the result in the form of a string.
 * For example, if the given numbers are {54, 546, 548, 60}, the arrangement 6054854654 gives the largest value. And if
 * the given numbers are {1, 34, 3, 98, 9, 76, 45, 4}, then the arrangement 998764543431 gives the largest value.
 */
public class LargestNumberFromArray {
    static void printLargest(List<String> arr){
        Collections.sort(arr, new Comparator<String>(){
            @Override
            public int compare(String X, String Y) {
                // first append Y at the end of X
                String XY=X + Y;

                // then append X at the end of Y
                String YX=Y + X;

                // Now see which of the two formed numbers
                // is greater
                return XY.compareTo(YX) > 0 ? -1:1;
            }
        });

        Iterator it = arr.iterator();
        while(it.hasNext())
            System.out.print(it.next());

    }
}
