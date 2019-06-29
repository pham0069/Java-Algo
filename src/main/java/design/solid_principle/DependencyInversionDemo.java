package design.solid_principle;

/**
 * By declaring the StandardKeyboard and IntelMonitor with the new keyword, we've tightly coupled these 3 classes together.
 * This make our Windows98Computer hard to test, but we've also lost the ability to switch out our StandardKeyboard
 * class with a different one should the need arise. And we're stuck with our Monitor class, too.
 *
 * We can fix this by redesigning the class to have a constructor to pass in keyboard and monitor
 * This is easier for testing, reduce decoupling, machine does not need to know how to create keyboard and monitor
 * Also we use Keyboard and Monitor interface declaration for abstraction
 */
public class DependencyInversionDemo {
    interface Keyboard {}
    interface Monitor{}
    class StandardKeyboard {}
    class IntelMonitor {}
    class Windows98Machine {

        private final StandardKeyboard keyboard;
        private final IntelMonitor monitor;

        public Windows98Machine() {
            keyboard = new StandardKeyboard();
            monitor = new IntelMonitor();
        }
    }

    class Windows98MachineRedesign{
        private final Keyboard keyboard;
        private final Monitor monitor;

        public Windows98MachineRedesign(Keyboard keyboard, Monitor monitor) {
            this.keyboard = keyboard;
            this.monitor = monitor;
        }
    }
}
