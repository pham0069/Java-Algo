package design.design_pattern.structural;

/**
 * Decorator pattern attaches additional responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to subclassing for extending functionality.
 *
 * Characteristics
 * - decorator has same super type as the object they decorate
 * See TreeDecorator is a Christmas tree that wraps a given Christmas tree
 * - decorator has an instance variable that holds the reference to component it decorates(HAS-A relationship)
 * See TreeDecorator holds the reference to the wrapped Christmas tree
 * - decorator pattern is an alternative to subclassing. Subclassing adds behavior at compile time, and the change
 * affects all instances of the original class; decorating can provide new behavior at runtime for individual objects.
 *
 * Adv
 * - possible to extend (decorate) the functionality of a certain object at runtime.
 * - pay-as-you-go approach to adding responsibilities. Instead of trying to support all foreseeable features
 * in a complex, customizable class, you can define a simple class and add functionality incrementally with Decorator
 * objects.
 * - If we have additional decoration, we just need to create a new class extending TreeDecorator
 * Modifications to current ChristmasTreeImpl and existing types of decoration are not needed
 *
 * Disadv
 * - can complicate the process of instantiating the component because you not only have to instantiate the component,
 * but wrap it in a number of decorators.
 * - hard to keep track of other decorators, because to look back into multiple layers of the decorator chain starts
 * to push the decorator pattern beyond its true intent.
 */
public class DecoratorDemo {
    interface ChristmasTree {
        String decorate();
    }

    static class ChristmasTreeImpl implements ChristmasTree {
        @Override
        public String decorate() {
            return "Christmas tree";
        }
    }

    static abstract class TreeDecorator implements ChristmasTree {
        private ChristmasTree tree;
        public TreeDecorator(ChristmasTree tree) {
            this.tree = tree;
        }
        @Override
        public String decorate() {
            return tree.decorate() + additionalDecorate();
        }
        abstract String additionalDecorate();
    }

    static class BubbleLights extends TreeDecorator {
        public BubbleLights(ChristmasTree tree) {
            super(tree);
        }

        String additionalDecorate() {
            return " with Bubble Lights";
        }
    }

    static class AngelTopper extends TreeDecorator {
        public AngelTopper(ChristmasTree tree) {
            super(tree);
        }

        @Override
        String additionalDecorate() {
            return " with Angel Topper";
        }
    }

    static class Garland extends TreeDecorator {
        public Garland(ChristmasTree tree) {
            super(tree);
        }

        @Override
        String additionalDecorate() {
            return " with Garland";
        }
    }

    static class Tinsel extends TreeDecorator {
        public Tinsel(ChristmasTree tree) {
            super(tree);
        }

        @Override
        String additionalDecorate() {
            return " with Tinsel";
        }
    }
    public static void main(String[] args) {
        ChristmasTree tree = new Tinsel(new Garland(new AngelTopper(new ChristmasTreeImpl())));
        System.out.println(tree.decorate());
    }
}
