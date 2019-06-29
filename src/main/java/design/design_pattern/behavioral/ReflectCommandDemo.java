package design.design_pattern.behavioral;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.IntStream;

/**
 * https://sourcemaking.com/design_patterns/command/java/2
 *
 * Sometimes we need to issue requests to objects without knowing anything about the operation being requested
 * In this case, wrap the command with object, method and arguments
 * Use reflection to invoke the method on given object and passed arguments
 */
public class ReflectCommandDemo {
    static class AddCommand {
        private int state;
        public AddCommand(int state) {
            this.state = state;
        }

        public void add(Integer value) {
            this.state += value;
        }

        public void add(Integer first, Integer second) {
            this.state += (first + second);
        }

        public int getState() {
            return state;
        }
    }

    interface Command {
        void execute();
    }

    static class ReflectCommand implements Command{
        private final Object receiver;
        private final Object[] args;
        private Method action;

        public ReflectCommand(Object receiver, String methodName, Object[] args) {
            this.receiver = receiver;
            this.args = args;
            this.action = parseMethod(receiver, methodName, args);
        }

        private Method parseMethod(Object receiver, String methodName, Object[] args) {
            Class receiverClass = receiver.getClass();
            Class[] argTypes = new Class[args.length];
            IntStream.range(0, args.length).boxed().forEach(i -> argTypes[i] = args[i].getClass());
            try {
                return receiverClass.getMethod(methodName, argTypes);
            } catch(NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void execute() {
            if (action == null) {
                System.out.println("Cannot do anything");
                return;
            }
            try {
                action.invoke(receiver, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Fail to execute");
            }
        }

    }

    public static void main(String[] args) {
        AddCommand c1 = new AddCommand(1);
        new ReflectCommand(c1, "add", new Integer[] {3}).execute();
        System.out.println(c1.state);
        new ReflectCommand(c1, "add", new Integer[] {2, 4}).execute();
        System.out.println(c1.state);
    }


}
