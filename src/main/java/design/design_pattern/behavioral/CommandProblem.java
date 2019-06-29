package design.design_pattern.behavioral;

/**
 * Suppose you are building a home automation system.
 * 1. There are some available devices such as light, stereo that have methods to function
 * 2. You need to design a programmable remote control, which given a command, can control these devices to turn on and off
 *
 * How can you design a class that can represent a command for the remote control to understand and take action accordingly?
 *
 * A simple approach is creating a command enum that enumerates all commands that can be executed
 * Depending on the type of command, remote control will know which device to execute and which method of the device to
 * execute.
 *
 * Disadvantages:
 * 1. Remote controller needs access to Light and Stereo instances.
 * It also needs good understanding of these devices' capabilities and call the correct methods.
 * This leads to tight coupling between remote controller and devices classes.
 * 2. Some commands are complicated with many steps (such as stereo on with CD).
 * This pushes a lot of responsibilities to remote controller.
 * 3. When we want to add more commands, we need to add more cases in remote controller to handle
 *
 */
public class CommandProblem {
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

    enum Command {
        LIGHT_ON,
        LIGHT_OFF,
        STEREO_OFF,
        STEREO_ON_WITH_CD;
    }

    static class SimpleRemoteControl {
        Light light;
        Stereo stereo;
        Command slot;


        SimpleRemoteControl(Light light, Stereo stereo) {
            this.light = light;
            this.stereo = stereo;
        }

        void setCommand(Command command) {
            slot = command;
        }

        void pressButton() {
            switch (slot) {
                case LIGHT_ON:
                    light.on();
                    break;
                case LIGHT_OFF:
                    light.off();
                    break;
                case STEREO_OFF:
                    stereo.off();
                    break;
                case STEREO_ON_WITH_CD:
                    stereo.on();
                    stereo.setCD();
                    stereo.setVolume(11);
            }
        }
    }

    public static void main(String[] args) {
        SimpleRemoteControl control = new SimpleRemoteControl(new Light(), new Stereo());
        control.setCommand(Command.LIGHT_OFF);
        control.pressButton();
        control.setCommand(Command.STEREO_ON_WITH_CD);
        control.pressButton();
    }
}
