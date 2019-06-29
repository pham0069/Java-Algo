package design.design_pattern.creational;

import java.util.HashMap;
import java.util.Map;

/**
 * https://sourcemaking.com/design_patterns/prototype
 * https://www.geeksforgeeks.org/prototype-design-pattern/
 *
 * In prototype pattern, new instances are created by cloning, rather than created brand new from scratch, something that
 * may include costly operations.
 * The existing object acts as a prototype and contains the state of the object.
 * The newly copied object may change same properties only if required. This approach saves costly resources and time,
 * especially when the object creation is a heavy process.
 *
 * In prototype pattern, we have:
 * 1. Prototype: prototype of actual object
 * 2. Prototype registry: this is used as registry service to have all prototypes accessible
 * 3. Client: use registry to access prototype instances
 *
 * Adv
 * 1. Adding and removing products at run-time
 * 2. Specifying new objects by varying values
 * 3. Specifying new objects by varying structures
 * 4. Reduced subclassing for Creator class
 *
 * Disadv
 * 1. Overkill for projects using very few objects
 * 2. Hide concrete product classes from client
 * 3. Each prototype has to implement clone(), which could be difficult when internals include objects that dont support
 * copying or have circular references
 */
public class PrototypeDemo {
    static abstract class Color implements Cloneable {
        protected String colorName;

        Color(String colorName) {
            this.colorName = colorName;
        }
        void addColor() {
            System.out.println(colorName + " color added!!!");
        }

        public Object clone() {
            Object clone = null;
            try {
                clone = super.clone();
            }
            catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clone;
        }
    }

    static class RedColor extends Color{
        public RedColor() {
            super("Red");
        }
    }

    static class GreenColor extends Color {
        public GreenColor() {
            super("Green");
        }
    }

    static class YellowColor extends Color{
        public YellowColor() {
            super("Yellow");
        }
    }

    static class ColorStore {
        private final Map<String, Color> colorMap = new HashMap<>();

        ColorStore() {
            colorMap.put("Green", new GreenColor());
            colorMap.put("Red", new RedColor());
            colorMap.put("Yellow", new YellowColor());
        }

        public Color getColor(String colorName) {
            return (Color) colorMap.get(colorName).clone();
        }
    }

    public static void main (String[] args) {
        ColorStore colorStore = new ColorStore();
        colorStore.getColor("Green").addColor();
        colorStore.getColor("Red").addColor();
        colorStore.getColor("Yellow").addColor();
        colorStore.getColor("Red").addColor();
    }
}
