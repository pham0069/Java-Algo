package design.design_pattern.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * In composite pattern, we compose objects into tree structures to represent whole-part hierarchies
 * Composite lets clients treat individual objects and composition objects uniformly
 *
 * Composite pattern has
 * 1. Component: interface for objects in composition to access and manage child components
 * 2. Leaf: defines behavior for primitive objects in the composition
 * 3. Composite: stores child components
 * 4. Client: manipulates the objects in the composition
 *
 * Adv
 * 1. Reduce complexity when handling composition of inhomogeneous objects
 * Should use when programmers find that they are using multiple objects in the same way, and often have nearly identical
 * code to handle each of them
 *
 *
 * Disadv
 * 1. Hard to restrict type of components of a composite
 * 2. Make the design overly general. Need to make run-time checks of the type of object
 *
 *
 * In this demo, we have
 * 1. AbstractFile interface: component
 * 2. File, Directory implementation: leaf
 * 3. Directory: act as composite
 * 4. main() method is client code
 * This pattern is suitable here since the file system has a whole-part hierarchical relationships.
 * Directory is a container, whose containee could be a file or a directory (another container).
 * AbstractFile is defined as a lowest common denominator interface to make containers and containees interchangable
 *
 *
 * Note that Composite and Decorator have similar structure diagrams but they have different purpose
 * 1. Decorator lets you add responsibility to objects without subclassing
 * 2. Composite focus is not embellishment but on representation
 *
 * Flyweight can be used to create leaf for Composite
 */
public class CompositeDemo {
    static HierarchyBuilder hierarchyBuilder = new HierarchyBuilder();
    static class HierarchyBuilder {
        StringBuilder spaceBuilder = new StringBuilder();
        void indent() {
            spaceBuilder.append("   ");
        }
        void outdent() {
            int len = spaceBuilder.length();
            spaceBuilder.delete(len-3, len);
        }
        String getSpace() {
            return spaceBuilder.toString();
        }
    }
    interface AbstractFile {
        void ls();
    }

    // File implements the "lowest common denominator"
    static class File implements AbstractFile {
        private String name;

        public File(String name) {
            this.name = name;
        }

        public void ls() {
            System.out.println(hierarchyBuilder.getSpace() + name);
        }
    }

    // Directory implements the "lowest common denominator"
    static class Directory implements AbstractFile {
        private String name;
        private List<AbstractFile> includedFiles = new ArrayList<>();

        public Directory(String name) {
            this.name = name;
        }

        public void add(AbstractFile obj) {
            includedFiles.add(obj);
        }

        public void ls() {
            System.out.println(hierarchyBuilder.getSpace() + name);
            hierarchyBuilder.indent();
            // Leverage the "lowest common denominator"
            for (AbstractFile includedFile : includedFiles) {
                includedFile.ls();
            }
            hierarchyBuilder.outdent();
        }
    }

    public static void main(String[] args) {
        Directory music = new Directory("MUSIC");
        Directory scorpions = new Directory("SCORPIONS");
        Directory dio = new Directory("DIO");
        File track1 = new File("Don't wary, be happy.mp3");
        File track2 = new File("track2.m3u");
        File track3 = new File("Wind of change.mp3");
        File track4 = new File("Big city night.mp3");
        File track5 = new File("Rainbow in the dark.mp3");
        music.add(track1);
        music.add(scorpions);
        music.add(track2);
        scorpions.add(track3);
        scorpions.add(track4);
        scorpions.add(dio);
        dio.add(track5);
        music.ls();
    }
}
