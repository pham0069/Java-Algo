package data_structure.stack;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * https://www.hackerrank.com/challenges/equal-stacks/problem
 *
 * You have three stacks of cylinders where each cylinder has the same diameter, but they may vary in height. You can
 * change the height of a stack by removing and discarding its topmost cylinder any number of times.
 *
 * Find the maximum possible height of the stacks such that all of the stacks are exactly the same height. This means
 * you must remove zero or more cylinders from the top of zero or more of the three stacks until they're all the same
 * height, then print the height. The removals must be performed in such a way as to maximize the height.
 *
 * Note: An empty stack is still a stack.
 */
public class EqualStacks {
    public static void main(String[] args) {
        getEqualStacks();
    }
    static void getEqualStacks() {
        Scanner sc = new Scanner(System.in);
        int n1 = sc.nextInt();
        int n2 = sc.nextInt();
        int n3 = sc.nextInt();
        int h1[] = new int[n1];
        int h2[] = new int[n2];
        int h3[] = new int[n2];
        IntStream.range(0, n1).forEach(i -> h1[i] = sc.nextInt());
        IntStream.range(0, n2).forEach(i -> h2[i] = sc.nextInt());
        IntStream.range(0, n3).forEach(i -> h3[i] = sc.nextInt());

        MyStack[] stacks = new MyStack[3];
        stacks[0] = new MyStack(1, h1);
        stacks[1] = new MyStack(2, h2);
        stacks[2] = new MyStack(3, h3);

        while (!MyStack.equal(stacks)){
            int minHeight = MyStack.minHeight(stacks);
            for (int i = 0; i < 3; i++)
                stacks[i].trimTo(minHeight);
        }
        System.out.println(stacks[0].currentHeight);
    }
}
class MyStack{
    int id;
    int[] height;
    int[] sumHeight;
    int currentIndex;
    int currentHeight;
    public MyStack(int id, int[] height){
        this.id = id;
        this.height = height;
        sumHeight = new int[height.length+1];
        accumulateHeightFromBottom();
    }
    public void accumulateHeightFromBottom(){
        sumHeight[height.length] = 0;
        for (int i = height.length-1; i>=0; i--){
            sumHeight[i] = sumHeight[i-1] + height[i];
        }

        currentIndex = 0;
        currentHeight = sumHeight[currentIndex];
    }
    //find the height <= given height
    public void trimTo(int h){
        if (currentHeight == h)
            return;
        if (sumHeight[height.length] > h){
            //cannot trim
            return;
        }

        int start = currentIndex;
        int end = height.length;
        while(start < end-1){
            int mid = (start+end)/2;
            if (sumHeight[mid] <= h)
                end = mid;
            else
                start = mid;
        }

        //sumHeight[end] is surely <=h
        //just in case sumHeight[start] == h
        //for example array 10 7 5 4 1, h = 10
        if (sumHeight[start] == h)
            currentIndex = start;
        else
            currentIndex = end;

        currentHeight = sumHeight[currentIndex];
    }
    public static boolean equal (MyStack[] stacks){
        if (stacks.length <= 1)
            return true;
        for (int i = 1; i < stacks.length; i++){
            if (stacks[i].currentHeight != stacks[0].currentHeight)
                return false;
        }
        return true;
    }
    public static int minHeight(MyStack[] stacks){
        if (stacks.length == 1)
            return stacks[0].currentHeight;
        int min = stacks[0].currentHeight;
        for (int i = 1; i < stacks.length; i++){
            min = Math.min(min, stacks[i].currentHeight);
        }
        return min;
    }
}
