package design.design_pattern.creational;

/**
 * AbstractFactory pattern work around a super-factory which creates other factories
 * Abstract factory pattern has:
 * 1. AbstractFactory interface: declared to create abstract product objects
 * 2. ConcreteFactory: implement AbstractFactory
 * 3. Product: concrete product that could be created from Factory
 *
 * Adv
 * 1. Isolation of concrete products and client code
 * 2. Easy exchange of product families: one specific factory targets at one product family only
 * Thus changing product family is equivalent to changing the specific factory
 * 3. Consistency among products: can enforce all products created are from same family
 * This is especially important if family products are designed to work together only
 *
 * Disadv
 * 1. Difficult to support new kind of products: AbstractFactory fixes the set of products that can be created
 * (e.g. CPU, MMU). Supporting new products requires extending AbstractFactory interface as well as all its subclasses
 */
public class AbstractFactoryDemo {
    static abstract class CPU {}
    static class EmberCPU extends CPU {}
    static class EnginolaCPU extends CPU {}

    static abstract class MMU {}
    static class EmberMMU extends MMU {}
    static class EnginolaMMU extends MMU {}

    // class EmberFactory
    static class EmberToolkit extends AbstractFactory {
        @Override
        public CPU createCPU() {
            return new EmberCPU();
        }

        @Override
        public MMU createMMU() {
            return new EmberMMU();
        }
    }

    // class EnginolaFactory
    static class EnginolaToolkit extends AbstractFactory {
        @Override
        public CPU createCPU() {
            return new EnginolaCPU();
        }

        @Override
        public MMU createMMU() {
            return new EnginolaMMU();
        }
    }

    enum Architecture {
        ENGINOLA, EMBER
    }

    static abstract class AbstractFactory {
        private static final EmberToolkit EMBER_TOOLKIT = new EmberToolkit();
        private static final EnginolaToolkit ENGINOLA_TOOLKIT = new EnginolaToolkit();

        // Returns a concrete factory object that is an instance of the
        // concrete factory class appropriate for the given architecture.
        static AbstractFactory getFactory(Architecture architecture) {
            AbstractFactory factory = null;
            switch (architecture) {
                case ENGINOLA:
                    factory = ENGINOLA_TOOLKIT;
                    break;
                case EMBER:
                    factory = EMBER_TOOLKIT;
                    break;
            }
            return factory;
        }

        public abstract CPU createCPU();

        public abstract MMU createMMU();
    }

    public static void main(String[] args) {
        AbstractFactory factory = AbstractFactory.getFactory(Architecture.EMBER);
        CPU cpu = factory.createCPU();
        System.out.println(cpu.getClass().getSimpleName());
    }
}
