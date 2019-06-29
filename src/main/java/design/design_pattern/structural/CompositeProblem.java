package design.design_pattern.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * The problem is to design a file system that involves directories and files
 * Directory can contain directories and/or files. File should be leave node in system, i.e. cannot contain any other entities.
 * Design it so that we could print out the hierarchy of a given directory or file
 *
 * In the below implementation, File and Directory are implemented as 2 unrelated classes.
 * File is primitive object and Directory is composite object.
 * Given a child of directory, we need to explicitly check if the object is Directory or File object, cast them accordingly,
 * and call respective ls methods.
 * This is inconvenient because File and Directory needs to be treated differently, even they share a common behavior ls,
 * which is the single behavior interested here
 */
public class CompositeProblem {
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
    static class File {
        private String name;

        public File(String name) {
            this.name = name;
        }

        public void ls() {
            System.out.println(hierarchyBuilder.getSpace() + name);
        }
    }

    static class Directory {
        private String name;
        private List includedFiles = new ArrayList();

        public Directory(String name) {
            this.name = name;
        }

        public void add(Object obj) {
            includedFiles.add(obj);
        }

        public void ls() {
            System.out.println(hierarchyBuilder.getSpace() + name);
            hierarchyBuilder.indent();
            for (Object obj : includedFiles) {
                // Recover the type of this object
                String name = obj.getClass().getSimpleName();
                if (name.equals("Directory")) {
                    ((Directory)obj).ls();
                } else {
                    ((File)obj).ls();
                }
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
