package design.design_pattern.structural;

import lombok.AllArgsConstructor;

/**
 * The problem is modelling an object with optional and additional feature
 * Specifically, we want to build a christmas tree, that could have 1 or more decorations such as bubble light, garland,
 * angel topper, tinsel...
 *
 * Approach 1:
 * Create a new class for each tree type with unique set of decorations.
 * There is exhaustive list of possible trees: PlainChristmasTree, ChristmasTreeWithBubbleLights, ChristmasTreeWithAngelTopper,
 * ChristmasTreeWithBubbleLightsAndAngelTopper,... It is impractical in real design
 *
 * Approach 2:
 * Create a single tree class ChristmasTreeImpl that has variables to indicate if it has a decoration or not:
 * hasBubbleLights, hasAngelTopper, ...
 *
 * This looks better but still inflexible. If there are more decoration types to add in future, we will need to add more
 * variables and also modifies decorate() method. This seriously violates Open-Closed principle
 *
 */
public class DecoratorProblem {
    interface ChristmasTree {
        String decorate();
    }

    // Approach 1
    static class PlainChristmasTree implements ChristmasTree {
        @Override
        public String decorate() {
            return "Christmas tree";
        }
    }

    static class ChristmasTreeWithBubbleLights extends PlainChristmasTree {
        @Override
        public String decorate() {
            return super.decorate() + " with Bubble Lights";
        }
    }

    static class ChristmasTreeWithBubbleLightsAndAngelTopper extends ChristmasTreeWithBubbleLights {
        @Override
        public String decorate() {
            return super.decorate() + " with Angel Topper";
        }
    }

    static class ChristmasTreeWithBubbleLightsAndAngelTopperAndGarland extends ChristmasTreeWithBubbleLightsAndAngelTopper {
        @Override
        public String decorate() {
            return super.decorate() + " with Garland";
        }
    }

    static class ChristmasTreeWithBubbleLightsAndAngelTopperAndGarlandAndTinsel extends ChristmasTreeWithBubbleLightsAndAngelTopperAndGarland {
        @Override
        public String decorate() {
            return super.decorate() + " with Tinsel";
        }
    }
    //==================================================================================================================
    // Approach 2
    @AllArgsConstructor
    static class ChristmasTreeImpl implements ChristmasTree {
        private boolean hasBubbleLights;
        private boolean hasAngelTopper;
        private boolean hasGarland;
        private boolean hasTinsel;
        @Override
        public String decorate() {
            StringBuilder builder = new StringBuilder();
            builder.append("Christmas tree");
            if (hasBubbleLights)
                builder.append(" with Bubble Lights");
            if (hasAngelTopper)
                builder.append(" with Angel Topper");
            if (hasGarland)
                builder.append(" with Garland");
            if (hasTinsel)
                builder.append(" with Tinsel");
            return builder.toString();
        }
    }


    public static void main(String[] args) {
        ChristmasTreeImpl tree = new ChristmasTreeImpl(true, false, false, true);
        ChristmasTreeWithBubbleLightsAndAngelTopperAndGarland tree2 = new ChristmasTreeWithBubbleLightsAndAngelTopperAndGarland();
        System.out.println(tree.decorate());
        System.out.println(tree2.decorate());
    }
}
