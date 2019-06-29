package design.design_pattern.creational;

/**
 * Given the problem that we need to create computer components given the component type and architecture type
 *
 * One approach is as below, where factory is created with a given architecture.
 * A disadvantage is that Factory needs to know all kinds of architecture and component types to handle the creation logic
 *
 */
public class AbstractFactoryProblem {
    static abstract class CPU {}
    static class EmberCPU extends CPU {}
    static class EnginolaCPU extends CPU {}

    static abstract class MMU {}
    static class EmberMMU extends MMU {}
    static class EnginolaMMU extends MMU {}

    enum Architecture {
        ENGINOLA, EMBER
    }

    static class Factory {
        private final Architecture architecture;
        Factory(Architecture architecture) {
            this.architecture = architecture;
        }
        public CPU createCPU() {
            switch (architecture) {
                case ENGINOLA:
                    return new EnginolaCPU();
                case EMBER:
                    return new EmberCPU();
            }
            return null;
        }

        public MMU createMMU() {
            switch (architecture) {
                case ENGINOLA:
                    return new EnginolaMMU();
                case EMBER:
                    return new EmberMMU();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Factory factory = new Factory(Architecture.EMBER);
        CPU cpu = factory.createCPU();
        System.out.println(cpu.getClass().getSimpleName());
    }
}
