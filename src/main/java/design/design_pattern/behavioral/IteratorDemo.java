package design.design_pattern.behavioral;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * In iterator pattern:
 * 1. Take the responsibility of access and traversal out of the aggregate object
 * 2. Put it into Iterator object that defines a standard traversal protocol
 *
 * Checklist:
 * 1. Add createIterator() method to the collection class
 * 2. Design iterator class to encapsulate the traversal of the collection class
 * 3.
 *
 */
public class IteratorDemo {
    static class IntegerBox {
        private List<Integer> list = new ArrayList<>();

        public class Iterator {
            private IntegerBox box;
            private java.util.Iterator iterator;
            private int value;
            private boolean hasNext;

            public Iterator(IntegerBox integerBox) {
                box = integerBox;
                hasNext = true;
            }

            public void first() {
                iterator = box.list.iterator();
                next();
            }

            public void next() {
                try {
                    value = (Integer)iterator.next();
                } catch (NoSuchElementException ex) {
                    hasNext = false;
                }
            }

            public boolean isDone() {
                return !hasNext;
            }

            public int currentValue() {
                return value;
            }
        }

        public void add(int in) {
            list.add(in);
        }

        public Iterator getIterator() {
            return new Iterator(this);
        }
    }

    public static void main(String[] args) {
        IntegerBox box = createIntBox();

        iterateIntBoxOnce(box);
        System.out.println();
        iterateIntBoxTwice(box);
    }

    private static void iterateIntBoxOnce(IntegerBox box) {
        IntegerBox.Iterator firstItr = box.getIterator();
        for (firstItr.first(); !firstItr.isDone(); firstItr.next()) {
            System.out.print(firstItr.currentValue() + "  ");
        }
    }

    private static void iterateIntBoxTwice(IntegerBox box) {
        IntegerBox.Iterator firstItr = box.getIterator();
        IntegerBox.Iterator secondItr = box.getIterator();
        for (firstItr.first(), secondItr.first(); !firstItr.isDone(); firstItr.next(), secondItr.next()) {
            System.out.print(firstItr.currentValue() + " " + secondItr.currentValue() + "  ");
        }
    }

    private static IntegerBox createIntBox() {
        IntegerBox box = new IntegerBox();
        IntStream.range(0, 9).boxed().forEach(i -> box.add(9 - i));
        return box;
    }

    private static void test() {
        Table<Integer, Integer, Set<Integer>> table = HashBasedTable.create();
        register(table, 1, 2, 1);
        register(table, 1, 2, 2);
        register(table, 1, 2, 1);
        register(table, 1, 3, 8);
        System.out.println(table);
        unregister(table, 1, 2, 1);
        unregister(table, 1, 2, 2);
        System.out.println(table);
        System.out.println(table.containsRow(1));
        System.out.println(table.contains(1, 2));
        unregister(table, 1, 3, 8);
        unregister(table, 2, 5, 8);
        System.out.println(table);
        Map<Integer, Set<Integer>> row =  table.row(1);
        Set<Integer> e = row.get(2);
        System.out.println(e);
    }

    public static void register(Table<Integer, Integer, Set<Integer>> table, Integer group, Integer ccyPair, Integer listener) {
        Set<Integer> symbolListeners = table.get(group, ccyPair);
        if (symbolListeners == null) {
            symbolListeners = Sets.newHashSet();
            table.put(group, ccyPair, symbolListeners);
        }
        symbolListeners.add(listener);
    }

    public static void unregister(Table<Integer, Integer, Set<Integer>> table, Integer group, Integer ccyPair, Integer listener) {
        Optional.ofNullable(table.get(group, ccyPair))
                .ifPresent(listeners -> {
                    listeners.remove(listener);
                    if (listeners.isEmpty())
                        table.remove(group, ccyPair);
                });
    }
}
