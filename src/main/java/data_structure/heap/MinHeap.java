package data_structure.heap;


public class MinHeap {
    int[] array;
    int capacity;
    int size;

    MinHeap(int[] array) {
        this.array = array;
        this.size = array.length;
        int i = (size -1)/2;
        while (i >= 0) {
            heapify(i);
            i--;
        }

    }
    public Integer extractMin() {
        if (size == 0)
            return null;
        int root = array[0];
        if (size > 1) {
            array[0] = array[size-1];
            heapify(0);
        }
        size --;
        return root;
    }

    private void heapify(int index) {
        int left = getLeft(index);
        int right = getRight(index);
        int minIndex = index;
        if (left < size && array[left] < array[index])
            minIndex = left;
        if (right < size && array[right] < array[minIndex])
            minIndex = right;
        if (minIndex != index) {
            swap(index, minIndex);
            heapify(minIndex);
        }

    }

    private void swap (int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private int parent(int i) {
        return (i-1)/2;
    }
    private int getLeft(int i) {
        return 2*i+1;
    }
    private int getRight(int i) {
        return 2*i+2;
    }


}
