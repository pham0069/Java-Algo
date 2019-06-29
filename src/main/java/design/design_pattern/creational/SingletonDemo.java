package design.design_pattern.creational;

/**
 * The singleton pattern is a design pattern that restricts the instantiation of a class to one object.
 *
 * Sometimes we need to have only one instance of our class.
 * For example, a single DB connection shared by multiple objects as creating a separate DB connection for every object
 * may be costly. Similarly, there can be a single configuration manager or error manager in an application that handles
 * all problems instead of creating multiple managers.
 *
 *
 * Below is 3 implementations of singleton design patterns
 *
 * 1. Classic singleton
 * - use private constructor to force use of getInstance() to create Singleton object
 * - provide static getInstance() so that we can call it without instantiating the class.
 * - The first time getInstance() is called it creates a new singleton object and after that it just returns
 * the same object.
 * - Note that Singleton obj is not created until we need it and call getInstance() method. This is called lazy instantiation.
 *
 * Problem: not thread-safe. In multi-thread app, there's a chance more than 1 objects are created
 *
 * 2. Thread-safe singleton
 * - making getInstance() synchronized ensure singularity
 *
 * Problem: using synchronized every time while creating the singleton object is expensive and may decrease the
 * performance of your program.
 * Note that, if performance of getInstance() is not critical for your app, this approach provides a clean and simple
 * solution.
 *
 * 3. Eager instantiation:
 * - JVM executes static initializer when the class is loaded and hence this is guaranteed to be thread safe.
 * - Use this approach only when your singleton class is light and is used throughout the execution of your program.
 *
 * 4. Double checked locking
 * - It's observed that once an object is created, synchronization is no longer useful because now obj will not be null
 * and any sequence of operations will lead to consistent results.
 * So we will only acquire lock on the getInstance() once, when the obj is null. This way we only synchronize the first
 * way through, just what we want.
 * - We have declared the obj volatile which ensures that multiple threads offer the obj variable correctly when it is
 * being initialized to Singleton instance.
 * - This method drastically reduces the overhead of calling the synchronized method every time.
 *
 */
public class SingletonDemo {

    // classic
    static class Singleton {
        private static Singleton obj;
        private Singleton() {}
        public static Singleton getInstance() {
            if (obj==null)
                obj = new Singleton();
            return obj;
        }
    }

    // thread-safe
    static class One {
        private static Only obj;
        private One() {}
        public static synchronized Only getInstance() {
            if (obj==null)
                obj = new Only();
            return obj;
        }
    }

    // eager instantiation
    static class Unique {
        private static Unique obj = new Unique();
        private Unique() {}
        public static Unique getInstance() {
            return obj;
        }
    }

    // doubled-checked locking
    static class Only {
        private static volatile Only obj;
        private Only() {}
        public static Only getInstance() {
            if (obj==null) {
                synchronized (Only.class) {
                    if (obj==null)
                        obj = new Only();
                }
            }
            return obj;
        }
    }

    public static void main(String[] args) {
        Only a = Only.getInstance();
        Only b = Only.getInstance();
        System.out.println(a == b);
    }
}
