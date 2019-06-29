package design.design_pattern.creational;

/**
 * To overcome this issue, 2 approaches:
 * 1. override clone() method and throw an exception from clone method that is
 * CloneNotSupportedException. Now whenever user will try to create clone of singleton object, it will throw exception
 * and hence our class remains singleton.
 *
 * 2. override clone() to return same instance
 */
public class SingletonWithCloning {
    static class SuperClass implements Cloneable {
        int i = 10;
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    /*
    static class Singleton extends SuperClass {
        public static Singleton instance = new Singleton();
        private Singleton() {}
    }
    */
    /*
    static class Singleton extends SuperClass {
        public static Singleton instance = new Singleton();
        private Singleton() {}
        @Override
        protected Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }
    */
    static class Singleton extends SuperClass {
        public static Singleton instance = new Singleton();
        private Singleton() {}
        @Override
        protected Object clone() throws CloneNotSupportedException {
            return instance;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Singleton instance1 = Singleton.instance;
        Singleton instance2 = (Singleton) instance1.clone();
        System.out.println("instance1 hashCode:- " + instance1.hashCode());
        System.out.println("instance2 hashCode:- " + instance2.hashCode());
    }
}
