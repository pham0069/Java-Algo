package design.design_pattern.creational;

import lombok.Getter;

/**
 * In this program, we have:
 * - 1 interface Vehicle
 * - several Vehicle implementations like TwoWheeler, FourWheeler
 * - 1 client code that holds a vehicle variable
 *
 * Problem in client code:
 * - directly call Vehicle constructor -> need to know how to create Vehicle based on type
 * - when there's some change in Vehicle library, e.g. a new vehicle (ThreeWheeler) is introduced,
 * client code needs to update and recompile
 *
 */
public class FactoryProblem {
    interface Vehicle {
        void printVehicle();
    }
    static class TwoWheeler implements Vehicle {
        public void printVehicle()  {
            System.out.println("I am two wheeler");
        }
    };
    static class FourWheeler implements Vehicle {
        public void printVehicle()  {
            System.out.println ("I am four wheeler");
        }
    };

    // Client (or user) class
    static class Client {
        @Getter private final Vehicle vehicle;
        public Client(int type)  {
            // Client explicitly creates classes according to type
            if (type == 1)
                vehicle = new TwoWheeler();
            else if (type == 2)
                vehicle = new FourWheeler();
            else
                vehicle = null;
        }
    }

    // Driver program
    public static void main(String[] args) {
        Client client = new Client(1);
        Vehicle vehicle = client.getVehicle();
        if (vehicle != null)
            vehicle.printVehicle();
        else
            System.out.println("Unknown vehicle");
    }
}
