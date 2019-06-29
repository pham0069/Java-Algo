package design.design_pattern.creational;

import lombok.Getter;

/**
 * Factory pattern:
 * Create object without exposing the creation logic to client
 * Client use the same common interface to create new type of object
 *
 * Specifically, VehicleFactory provides client with method to create Vehicle based on given vehicle type
 * The library is now responsible to decide which object type to create based on an input.
 * Client just needs to make call to library’s factory Create method and pass the type it wants without worrying about
 * the actual implementation of creation of objects.
 */
public class FactoryDemo {
    enum VehicleType {
        TWO_WHEELER, THREE_WHEELER, FOUR_WHEELER
    }

    interface Vehicle {
        void printVehicle();
    }
    static class TwoWheeler implements Vehicle {
        public void printVehicle()  {
            System.out.println("I am two wheeler");
        }
    };
    static class ThreeWheeler implements Vehicle {
        public void printVehicle()  {
            System.out.println ("I am three wheeler");
        }
    };
    static class FourWheeler implements Vehicle {
        public void printVehicle()  {
            System.out.println ("I am four wheeler");
        }
    };

    static class Client {
        @Getter
        private final Vehicle vehicle;
        public Client(VehicleType type)  {
            vehicle = VehicleFactory.generateVehicle(type);
        }
    }

    static class VehicleFactory {
        static Vehicle generateVehicle(VehicleType type) {
            switch(type) {
                case TWO_WHEELER:
                    return new TwoWheeler();
                case THREE_WHEELER:
                    return new ThreeWheeler();
                case FOUR_WHEELER:
                    return new FourWheeler();
                default:
                    return null;

            }
        }
    }

    // Driver program
    public static void main(String[] args) {
        Client client = new Client(VehicleType.TWO_WHEELER);
        Vehicle vehicle = client.getVehicle();
        if (vehicle != null)
            vehicle.printVehicle();
        else
            System.out.println("Unknown vehicle");
    }

}
