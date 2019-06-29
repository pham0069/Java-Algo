package design.design_pattern.behavioral;

/**
 * Given below problem:
 * You need to model a fighter that may have following moves: kick, punch, roll, jump
 * All fighters can kick and jump, but roll and punch are optional
 *
 * Approach 1:
 * Having single interface Ninja that includes all possible moves
 * For those fighters that cannot roll and jump, we can create a class that overrides to do nothing or throw exception
 * in roll() and jump(). This could cause unexpected behaviors in class, violating Liskov substitution principle
 *
 * Approach 2:
 * Segregate interface into 3: Fighter (kick, jump), Roll, Punch
 * This looks neater and describes the behavior better
 * However, the implementation of roll() and jump() could not be reused if they are same in different classes
 * For example, WormRoll and SteelPunch have implemented these behaviors
 * But they could not be reused in AdvancedFighter
 */
public class StrategyProblem {
    interface Ninja {
        void kick();
        void punch();
        void roll();
        void jump();
    }

    interface Fighter {
        void kick();
        void jump();
    }
    interface Roll {
        void roll();
    }
    interface Punch {
        void punch();
    }

    static class BeginnerFighter implements Fighter {
        public void kick() {
            System.out.println("Default Kick");
        }
        public void jump() {
            System.out.println("Default Jump");
        }
    }

    static class WormRoll implements Roll {
        public void roll() {
            System.out.println("Worm Roll");
        }
    }
    static class SteelPunch implements Punch {
        public void punch() {
            System.out.println("Steel Punch");
        }
    }

    static class AdvancedFighter implements Fighter, Roll, Punch {
        public void kick() {
            System.out.println("Default Kick");
        }
        public void jump() {
            System.out.println("Default Jump");
        }
        public void roll() {
            System.out.println("Worm Roll");
        }
        public void punch() {
            System.out.println("Steel Punch");
        }
    }
}
