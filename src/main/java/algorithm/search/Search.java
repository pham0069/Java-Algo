package algorithm.search;

public class Search {
    public static void main(String[] args){
        int[] array = {1, 3, 5, 10, 15, 18, 20, 25, 27, 32};
        System.out.println(linearSearch(array, 15));
        System.out.println(binarySearch(array, 25));
        System.out.println(jumpSearch(array, 32));
        System.out.println(interpolationSearch(array, 18));
        System.out.println(exponentialSearch(array, 10));
    }

    public static int linearSearch(int[] array, int key) {
        for (int i = 0; i < array.length; i++){
            if (array[i] == key)
                return i;
        }
        return -1;
    }

    //array should be sorted
    public static int binarySearch(int[] array, int key) {
        int start = 0, end = array.length-1;
        int mid;
        while (start <= end){
            mid = (start+end)/2;
            if (array[mid] == key)
                return mid;
            else if (array[mid] < key)
                start = mid+1;
            else
                end = mid-1;
        }
        return -1;
    }

    //array should be sorted
    public static int jumpSearch(int[] array, int key) {
        int step = (int) Math.sqrt(array.length);
        return jumpSearch(array, key, step);
    }
    public static int jumpSearch(int[] array, int key, int step) {
        int i = 0;
        while (i < array.length){
            if (array[i] == key)
                return i;
            if (array[i] < key){
                i += step;
            } else {
                break;
            }
        }
        int j = i - step;
        int upperBound = Math.min(i, array.length);
        if (j < 0)
            return -1;
        for (; j < upperBound; j++) {
            if (array[j] == key)
                return j;
        }
        return -1;
    }

    //array should be sorted and uniformly distributed
    public static int interpolationSearch(int[] array, int key) {
        int lo = 0, hi = array.length-1;
        int pos;
        while (lo <= hi && key >= array[lo] && key <= array[hi]) {
            pos = lo + (key-array[lo])*(hi-lo)/(array[hi]-array[lo]);
            if (array[pos] == key)
                return pos;
            if (array[pos] < key)
                lo = pos+1;
            else
                hi = pos-1;
        }
        return -1;
    }

    public static int exponentialSearch(int[] array, int key) {
        int i = 0;
        while (i < array.length) {
            if (array[i] == key)
                return i;
            if (array[i] < key)
                i = 2*i+1;
            else
                break;
        }
        int start = i/2;
        if (start == 0)
            return -1;
        int end = Math.min(i, array.length)-1;
        int mid;
        while (start <= end){
            mid = (start + end)/2;
            if (array[mid] == key)
                return mid;
            if (array[mid] < key)
                start = mid+1;
            else
                end = mid-1;
        }
        return -1;
    }
}
