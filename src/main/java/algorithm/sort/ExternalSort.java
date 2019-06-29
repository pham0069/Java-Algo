package algorithm.sort;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/external-sorting/
 *
 * Divide the input into smaller chunks of fixed size that can be loaded into memory
 * So that we can sort each chunk and store the sorted result in a temporary output file
 * To merge the sorted inputs in these files, we use a min heap to store the first element of each file
 * Then extract the min value from this heap, write the value to output file
 * The next element to put into heap should be the next element from same file as the extracted min
 * Repeat this until all files are read
 */
public class ExternalSort {
    static Random random = new Random();
    static String INPUT_FILE = "files/algorithm/sort/externalSortInput.txt";
    static String PARTITION_PREFIX = "files/algorithm/sort/externalSortPartition";
    static String OUTPUT_FILE = "files/algorithm/sort/externalSortOutput.txt";
    static int NUM_PARTITIONS = 10;
    static int partitionSize = 1000;

    public static void main(String[] args) throws IOException {
        generateRandomInput();
        externalSort();
    }

    static void generateRandomInput() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(INPUT_FILE)))) {
            for (int i = 0; i < NUM_PARTITIONS; i++) {
                for (int j = 0; j < partitionSize; j++)
                    writer.write(Integer.toString(random.nextInt()) + " ");
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {}
    }

    /**
     * Sort data stored on disk
     */
    static void externalSort() throws IOException {
        int numPartitions = createInitialRuns();
        mergeFiles(numPartitions);
    }

    static String getSortedPartitionFileName(int index) {
        return PARTITION_PREFIX + index;
    }
    static class HeapNode implements Comparable<HeapNode> {
        @Getter int value;
        int partitionIndex;
        HeapNode(int value, int partitionIndex) {
            this.value = value;
            this.partitionIndex = partitionIndex;
        }
        @Override
        public int compareTo(HeapNode that) {
            if (that == null)
                return 1;
            if (this.value == that.value)
                return this.partitionIndex - that.partitionIndex;
            if (this.value < that.value)
                return -1;
            return 1;
        }
    }
    static void mergeFiles(int numPartitions) throws IOException {
        Scanner[] partitionReader = null;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(OUTPUT_FILE)))) {
            partitionReader = new Scanner[numPartitions];
            for (int i = 0; i < numPartitions; i++) {
                partitionReader[i] = new Scanner(new File(getSortedPartitionFileName(i)));
            }

            PriorityQueue<HeapNode> heap = new PriorityQueue<>();
            int i;
            for (i = 0; i < numPartitions; i++) {
                heap.add(new HeapNode(partitionReader[i].nextInt(), i));
            }
            int count = 0;

            while (count != numPartitions) {
                // Get the minimum element and store it in output file
                HeapNode min = heap.poll();
                writer.write(min.getValue() + " ");
                System.out.println(min.getValue());
                int partitionIndex = min.partitionIndex;
                //partitionIndex reaches EOF
                if (!partitionReader[partitionIndex].hasNext()) {
                    count++;
                } else {
                    heap.add(new HeapNode(partitionReader[partitionIndex].nextInt(), partitionIndex));
                }
            }
        } finally {
            if (partitionReader != null)
                for (int i = 0; i < numPartitions; i++)
                    partitionReader[i].close();
        }
    }

    /**
     * Do merge sort on each array
     */
    static void mergeSort(int[] array, int start, int end) {
        if (start < end) {
            // Same as (start+end)/2, but avoids overflow for large start and end
            int mid = start + (end - start) / 2;
            // Sort first and second halves
            mergeSort(array, start, mid);
            mergeSort(array, mid + 1, end);
            merge(array, start, mid, end);
        }
    }

    static void merge(int array[], int l, int m, int r) {
        int i, j, k;
        int n1 = m - l + 1;
        int n2 = r - m;
        int left[] = Arrays.copyOfRange(array, l, m+1);
        int right[] = Arrays.copyOfRange(array, m+1, r+1);

        /* Merge the temp arrays back into array[l..r]*/
        i = 0; // Initial index of left subarray
        j = 0; // Initial index of right subarray
        k = l; // Initial index of merged subarray
        while (i < n1 && j < n2) {
            if (left[i] <= right[j])
                array[k++] = left[i++];
            else
                array[k++] = right[j++];
        }

        /* Copy the remaining elements of left[], if there are any */
        while (i < n1)
            array[k++] = left[i++];

        /* Copy the remaining elements of right[], if there are any */
        while(j < n2)
            array[k++] = right[j++];
    }
    /**
     * Read input file, divide input elements into fixed-size chunks of integers,
     * do merge sort on each chunk and write each sorted output to a temporary output file
     * Number of output files should be equal to the number of chunk.
     * Note that the last chunk may have less number of elements than other chunks
     * Return number of partitions obtained from single input file
     */
    static int createInitialRuns() throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(INPUT_FILE)))) {
            int[] buffer = new int[partitionSize];
            int bufferIndex = 0, outputIndex = 0, arrayIndex = 0;
            String line = reader.readLine();

            while (line != null) {
                String[] array = line.trim().split("\\s+");
                arrayIndex = 0;
                while (arrayIndex < array.length && bufferIndex < partitionSize) {
                    buffer[bufferIndex] = Integer.parseInt(array[arrayIndex]);
                    bufferIndex++;
                    arrayIndex++;
                }
                line = reader.readLine();
                if (bufferIndex == partitionSize || line == null) {
                    mergeSort(buffer, 0, bufferIndex-1);

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                            new File(getSortedPartitionFileName(outputIndex))))) {
                        for (int i = 0; i < bufferIndex; i++)
                            writer.write(buffer[i] + " ");
                    }
                    bufferIndex = 0;
                    outputIndex ++;
                }
            }
            return outputIndex;
        }
    }
}
