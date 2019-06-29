package design.design_pattern.behavioral;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Given an aggregator class (such as IntegerBox), we need to design a method to iterate over its content
 * In this implementation, iterating IntegerBox is made possible by accessing the box's internal data directly.(i.e. list
 * of integer)
 *
 * Concerns
 * 1. Exposure of internal data to outside world
 * 2. If there are some changes of internal data of IntegerBox (like using Set or Map instead), we need to change the
 * logic of iterateInbox
 */
public class IteratorProblem {
    static class IntegerBox {
        private final List<Integer> list = new ArrayList<>();

        public void add(int in) {
            list.add(in);
        }

        public List getData() {
            return list;
        }
    }

    public static void main(String[] args) {
        IntegerBox box = createIntBox();
        iterateIntBox(box);
    }

    private static void iterateIntBox(IntegerBox box) {
        for (Object anIntegerList : box.getData()) {
            System.out.print(anIntegerList + "  ");
        }
        System.out.println();
    }

    private static IntegerBox createIntBox() {
        IntegerBox box = new IntegerBox();
        IntStream.range(0, 9).boxed().forEach(i -> box.add(9 - i));
        return box;
    }
}
