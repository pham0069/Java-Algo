package design.solid_principle;

/**
 * Take a look at BearKeeper interface with 3 methods.
 * As avid zookeepers, we're more than happy to wash and feed our beloved bears.
 * However, we're all too aware of the dangers of petting them.
 * Unfortunately, our interface is rather large, and we have no choice than to implement the code to pet the bear.
 *
 * The solution is to segregate the interface to more specific interfaces
 * This allows implementing classes only need to be concerned about the methods that are of interest to them.
 */
public class InterfaceSegregationDemo {
    public interface BearKeeper {
        void washTheBear();
        void feedTheBear();
        void petTheBear();
    }
    public interface BearCleaner {
        void washTheBear();
    }

    public interface BearFeeder {
        void feedTheBear();
    }

    public interface BearPetter {
        void petTheBear();
    }

    public class BearCarer implements BearCleaner, BearFeeder {

        public void washTheBear() {
            //I think we missed a spot...
        }

        public void feedTheBear() {
            //Tuna Tuesdays...
        }
    }

    public class CrazyPerson implements BearPetter {

        public void petTheBear() {
            //Good luck with that!
        }
    }
}

