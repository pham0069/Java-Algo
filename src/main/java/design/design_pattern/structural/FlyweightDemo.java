package design.design_pattern.structural;

import java.util.HashMap;
import java.util.Map;

/**
 * Flyweight suggests removing non-sharable state from the class, and having the client supply it when methods are called
 * This places more responsibility on the client, but considerably fewer instances of the Flyweight instance created
 * Sharing of these instances is facilitated by a Factory class to maintain a cache of existing Flyweights (usually using
 * a map for maintenance)
 *
 * When designing a flyweight instance, note of 2 states
 * 1. Intrinsic state
 * state that can be shared among different objects as they are similar to each other
 * should be stored in the flyweight object itself
 * it's best practice to make these state final, i.e. immutable after created
 *
 * 2. Extrinsic state
 * state that can't be shared among objects
 * should be stored and maintained by client code, and passed to Flyweight object when its operations are invoked
 *
 * In our example, we take row as intrinsic state and col and extrinsic state, which user needs to pass in when calling
 * report() method.
 * In previous implementation, we created 600 Gazillion objects
 * In this implementation, we only create 6 Gazillion row objects, which are to be shared among all the cols
 *
 *
 */
public class FlyweightDemo {
    static class Gazillion {
        private final int row;

        Gazillion(int row) {
            this.row = row;
            System.out.println("Created: " + this.row);
        }

        void report(int col) {
            System.out.print(" " + row + col);
        }
    }

    static class GazillionRowFactory {
        private Map<Integer, Gazillion> pool;

        GazillionRowFactory() {
            pool = new HashMap<>();
        }

        Gazillion getFlyweight(int row) {
            if (!pool.containsKey(row)) {
                pool.put(row, new Gazillion(row));
            }
            return pool.get(row);
        }
    }


    public static void main(String[] args) {
        int rows = 6, cols = 100;
        GazillionRowFactory factory = new GazillionRowFactory();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                factory.getFlyweight(i).report(j);
            System.out.println();
        }
    }

}
