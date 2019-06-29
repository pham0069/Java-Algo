package data_structure.queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Given total possible page numbers that can be referred. We are also given cache (or memory) size, i.e, number of page
 * frames that cache can hold at a time. The LRU caching scheme is to remove the least recently used frame when the cache
 * is full and a new page is referenced which is not there in cache.
 *
 * We use two data structures to implement an LRU Cache.
 * 1. Queue: whose size is corresponding to cache size. The maximum size of the queue will be equal to the total number
 * of frames available (cache size).The most recently used pages will be near front end and least recently pages will be
 * near rear end.
 * 2. A Hash with page number as key and address of the corresponding queue node as value.
 * When a page is referenced, the required page may be in the memory. If it is in the memory, we need to detach the node
 * of the list and bring it to the front of the queue.
 * If the required page is not in the memory, we bring that in memory. In simple words, we add a new node to the front
 * of the queue and update the corresponding node address in the hash.
 * If the queue is full, i.e. all the frames are full, we remove a node from the rear of queue, and add the new node to
 * the front of queue.
 */
public class MyLRUCache {
    private static final Map<Integer, Integer> PAGE_MAP = new HashMap<>();
    static {
        IntStream.range(0, 10).forEach(i -> PAGE_MAP.put(i, 1000*i));
    }
    private final Map<Integer, Integer> cacheMap = new HashMap<>();
    private final Deque<Integer> deque = new ArrayDeque<>();
    private final int cacheSize;
    private int hit;
    private int miss;

    MyLRUCache(int cacheSize) {
        this.cacheSize = cacheSize;
        this.hit = 0;
        this.miss = 0;
    }

     int get(int page) {
        if (! cacheMap.containsKey(page)) {
            if (deque.size() == cacheSize) {
                // delete least recently used element at end of queue
                int lruPage = deque.removeLast();
                cacheMap.remove(lruPage);
            }
            cacheMap.put(page, PAGE_MAP.get(page));
            miss++;
        } else {
            deque.remove(page);
            hit++;
        }

        // update the most recently used to beginning of queue
        deque.offerFirst(page);
        return cacheMap.get(page);
    }

    public static void main(String[] args){
        MyLRUCache cache = new MyLRUCache(3);
        int[] pageReference = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        Arrays.stream(pageReference).forEach(i -> cache.get(i));
        System.out.println(cache.hit + " " + cache.miss);
    }
}
