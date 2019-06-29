package design.design_pattern.behavioral;

/**
 * In command pattern,
 * 1. A request is wrapped under an object as command and passed to invoker object
 * 2. Invoker object looks for appropriate object to handle this command
 * 3. Invoker passes this command to the corresponding object to execute the command
 * 4. Command class can hold an object, a method ot be applied to the object, and the args to be passed when method is applied.
 * Thus Command readily knows how to execute itself.
 *
 * Advantages
 * 1. Makes our code extensible as we can add new commands without changing existing code
 * 2. Reduce coupling the invoker and receiver of a command
 *
 * Disadvantages
 * Increase in the number of classes for each individual command
 *
 * In the solution, we create a Command interface with single method execute().
 * Remote controller acts as invoker object
 * Light, stereo,... act as receivers
 * Each command (not simply an enum) encapsulates a request by binding together a set of actions on a specific receiver
 *
 */
public class CommandDemo {
    // Light class and its corresponding command classes
    static class Light {
        public void on() {
            System.out.println("Light is on");
        }
        public void off() {
            System.out.println("Light is off");
        }
    }
    // Stereo and its command classes
    static class Stereo {
        public void on() {
            System.out.println("Stereo is on");
        }
        public void off() {
            System.out.println("Stereo is off");
        }
        public void setCD() {
            System.out.println("Stereo is set for CD input");
        }
        public void setDVD() {
            System.out.println("Stereo is set for DVD input");
        }
        public void setRadio() {
            System.out.println("Stereo is set for Radio");
        }
        public void setVolume(int volume) {
            System.out.println("Stereo volume set to " + volume);
        }
    }

    interface Command {
        void execute();
    }

    static class LightOnCommand implements Command {
        Light light;

        public LightOnCommand(Light light) {
            this.light = light;
        }
        public void execute() {
            light.on();
        }
    }

    static class LightOffCommand implements Command {
        Light light;
        public LightOffCommand(Light light) {
            this.light = light;
        }
        public void execute() {
            light.off();
        }
    }

    static class StereoOffCommand implements Command {
        Stereo stereo;
        public StereoOffCommand(Stereo stereo) {
            this.stereo = stereo;
        }
        public void execute() {
            stereo.off();
        }
    }
    static class StereoOnWithCDCommand implements Command {
        Stereo stereo;
        public StereoOnWithCDCommand(Stereo stereo) {
            this.stereo = stereo;
        }
        public void execute() {
            stereo.on();
            stereo.setCD();
            stereo.setVolume(11);
        }
    }

    static class SimpleRemoteControl {
        Command slot;  // only one button

        void setCommand(Command command) {
            slot = command;
        }

        void pressButton() {
            slot.execute();
        }
    }

    public static void main(String[] args) {
        SimpleRemoteControl control = new SimpleRemoteControl();
        Light light = new Light();
        Stereo stereo = new Stereo();

        control.setCommand(new LightOnCommand(light));
        control.pressButton();
        control.setCommand(new StereoOnWithCDCommand(stereo));
        control.pressButton();
        control.setCommand(new StereoOffCommand(stereo));
        control.pressButton();
    }

}
