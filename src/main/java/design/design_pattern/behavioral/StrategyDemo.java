package design.design_pattern.behavioral;

import lombok.Setter;

/**
 * Strategy pattern: (also known as the policy pattern) enables an algorithm’s behavior to be selected at runtime.
 * This pattern involves:
 * - Strategy interface: defines a family of algorithms
 * - Context class: not implement algorithm, but encapsulate an algorithm to delegate the execution.
 * Algorithm can be set at run time to any strategy implementation
 *
 * Advantages:
 *
 * - New behaviors can be introduced by writing new strategies implementation
 * - Change in strategies implementation does not affect Context.
 * - The application can switch strategies at run-time
 * - Strategy provides a neat way to change behavior, as alternative to using a “switch” statement or a series of “if-else” statements.
 *
 * Disadvantages:
 *
 * - The application must be aware of all the strategies to select the right one for the right situation.
 * - Context and the Strategy classes normally communicate through the interface specified by the abstract Strategy base
 * class. Strategy base class must expose interface for all the required behaviours, which some concrete Strategy
 * classes might not implement.
 * - In most cases, the application configures the Context with the required Strategy object. Therefore, the application
 * needs to create and maintain two objects in place of one.
 *
 */
public class StrategyDemo {
    @Setter
    static class Fighter {
        KickBehavior kickBehavior;
        JumpBehavior jumpBehavior;

        public Fighter(KickBehavior kickBehavior, JumpBehavior jumpBehavior) {
            this.jumpBehavior = jumpBehavior;
            this.kickBehavior = kickBehavior;
        }
        public void punch() {
            System.out.println("Default Punch");
        }
        public void kick() {
            // delegate to kick behavior
            kickBehavior.kick();
        }
        public void jump() {
            // delegate to jump behavior
            jumpBehavior.jump();
        }
        public void roll() {
            System.out.println("Default Roll");
        }
    }

    // Encapsulated kick behaviors
    interface KickBehavior {
        void kick();
    }
    static class LightningKick implements KickBehavior {
        public void kick() {
            System.out.println("Lightning Kick");
        }
    }
    static class TornadoKick implements KickBehavior {
        public void kick() {
            System.out.println("Tornado Kick");
        }
    }

    // Encapsulated jump behaviors
    interface JumpBehavior {
        void jump();
    }
    static class ShortJump implements JumpBehavior {
        public void jump() {
            System.out.println("Short Jump");
        }
    }
    static class LongJump implements JumpBehavior {
        public void jump() {
            System.out.println("Long Jump");
        }
    }

    public static void main(String args[]) {
        // let us make some behaviors first
        JumpBehavior shortJump = new ShortJump();
        JumpBehavior LongJump = new LongJump();
        KickBehavior tornadoKick = new TornadoKick();

        // Make a fighter with desired behaviors
        Fighter ken = new Fighter(tornadoKick,shortJump);
        // Test behaviors
        ken.punch();
        ken.kick();
        ken.jump();

        // Change behavior dynamically (algorithms are interchangeable)
        ken.setJumpBehavior(LongJump);
        ken.jump();
    }
}
