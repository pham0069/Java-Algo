package design.design_pattern.creational;

import java.lang.reflect.Constructor;

/**
 * To overcome reflection, enums are used bcoz Java ensures internally that enum value is instantiated only once
 * Drawback is enum is not flexible, i.e. cannot be instantiated lazily
 *
 */
public class SingletonWithReflection {
    /*
    static class Singleton {
        public static Singleton instance = new Singleton();
        private Singleton() {}
    }
    */

    enum Singleton {
        instance;
    }

    public static void main(String[] args) {
        breakingSingletonWithReflection();
    }

    static void breakingSingletonWithReflection() {
        Singleton instance1 = Singleton.instance;
        Singleton instance2 = null;
        try {
            Constructor[] constructors = Singleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                // Below code will destroy the singleton pattern
                constructor.setAccessible(true);
                instance2 = (Singleton) constructor.newInstance();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("instance1.hashCode():- " + instance1.hashCode());
        System.out.println("instance2.hashCode():- " + instance2.hashCode());
    }
}
