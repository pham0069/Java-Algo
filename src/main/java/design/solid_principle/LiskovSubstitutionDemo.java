package design.solid_principle;

/**
 * In the implementation of ElectricCar, we threw exception in turnOnEngine() method.
 * By involving a car without an engine into the mix, we are inherently changing the behavior of our program.
 * This is a blatant violation of Liskov substitution and is a bit harder to fix than our previous 2 principles.
 *
 * A possible solution is reworking our model into interfaces that take into account the engine-less state of our Car.
 * E.g. Car {accelerate()} -> EngineCar (turnOnEngine()} -> MotorCar
 *        |-> ElectricCar
 */
public class LiskovSubstitutionDemo {
    interface Car {
        void turnOnEngine();
        void accelerate();
    }
    interface Engine {
        void on();
        void powerOn(int power);
    }

    class MotorCar implements Car {
        private Engine engine;

        //Constructors, getters + setters
        @Override
        public void turnOnEngine() {
            //turn on the engine!
            engine.on();
        }
        @Override
        public void accelerate() {
            //move forward!
            engine.powerOn(1000);
        }
    }

    class ElectricCar implements Car {
        public void turnOnEngine() {
            throw new AssertionError("I don't have an engine!");
        }

        public void accelerate() {
            //this acceleration is crazy!
        }
    }
}
