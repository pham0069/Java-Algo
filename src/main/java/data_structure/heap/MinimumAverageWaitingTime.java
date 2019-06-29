package data_structure.heap;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://www.hackerrank.com/challenges/minimum-average-waiting-time/problem
 *
 * Tieu owns a pizza restaurant and he manages it in his own way. While in a normal restaurant, a customer is served by
 * following the first-come, first-served rule, Tieu simply minimizes the average waiting time of his customers. So he
 * gets to decide who is served first, regardless of how sooner or later a person comes.
 *
 * Different kinds of pizzas take different amounts of time to cook. Also, once he starts cooking a pizza, he cannot
 * cook another pizza until the first pizza is completely cooked. Let's say we have three customers who come at time t=0,
 * t=1, & t=2 respectively, and the time needed to cook their pizzas is 3, 9, & 6 respectively. If Tieu applies first-come,
 * first-served rule, then the waiting time of three customers is 3, 11, & 16 respectively. The average waiting time in
 * this case is (3 + 11 + 16) / 3 = 10. This is not an optimized solution. After serving the first customer at time t=3,
 * Tieu can choose to serve the third customer. In that case, the waiting time will be 3, 7, & 17 respectively. Hence
 * the average waiting time is (3 + 7 + 17) / 3 = 9.
 *
 * Help Tieu achieve the minimum average waiting time. For the sake of simplicity, just find the integer part of the
 * minimum average waiting time.
 *
 * Input Format
 *
 * The first line contains an integer N, which is the number of customers.
 * In the next N lines, the ith line contains two space separated numbers Ti and Li. Ti is the time when ith customer
 * order a pizza, and Li is the time required to cook that pizza.
 *
 * The ith customer is not the customer arriving at the ith arrival time.
 *
 * Output Format
 *
 * Display the integer part of the minimum average waiting time.
 * Constraints
 *
 * 1 ≤ N ≤ 10^5
 * 0 ≤ Ti ≤ 10^9
 * 1 ≤ Li ≤ 10^9
 * Note
 *
 * The waiting time is calculated as the difference between the time a customer orders pizza (the time at which they
 * enter the shop) and the time she is served.
 *
 * Cook does not know about the future orders.
 *
 * Sample Input #00
 *
 * 3
 * 0 3
 * 1 9
 * 2 6
 * Sample Output #00
 *
 * 9
 * Sample Input #01
 *
 * 3
 * 0 3
 * 1 9
 * 2 5
 * Sample Output #01
 *
 * 8
 * Explanation #01
 *
 * Let's call the person ordering at time = 0 as A, time = 1 as B and time = 2 as C. By delivering pizza for A, C and B
 * we get the minimum average wait time to be (3 + 6 + 16)/3 = 25/3 = 8.33
 * The integer part is 8 and hence the answer.
 *
 * The average waiting time is minimized if Tieu picks the least-cooking time pizza to serve next after finishing 1 pizza
 */
public class MinimumAverageWaitingTime {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Task[] tasks = new Task[n];

        for (int i = 0; i < n; i++){
            tasks[i] = new Task(sc.nextInt(), sc.nextInt());
        }
        //sort the tasks in the order of increasing orderTime
        Arrays.sort(tasks, Comparator.comparingInt(Task::getOrderTime));

        long currentTime = tasks[0].orderTime;
        int index = 0;
        long totalWaitingTime = 0;
        PriorityQueue<Task> minCookHeap = new PriorityQueue<>(n, Comparator.comparingInt(Task::getCookTime));

        while (index < n){
            while (index < n && tasks[index].orderTime <= currentTime){
                minCookHeap.add(tasks[index]);
                index ++;
            }
            if (!minCookHeap.isEmpty()){
                //next task is the one with the lowest cooking time among the current task queue
                Task minCook = minCookHeap.poll();
                //update the currentTime to the time when this task is finished
                currentTime += minCook.cookTime;
                totalWaitingTime += (currentTime - minCook.orderTime);
            } else {
                //no order waiting
                if (index == n)
                    break;
                else
                    currentTime = tasks[index].orderTime; //just move to the next order's time
            }
        }

        while (!minCookHeap.isEmpty()){
            Task minCook = minCookHeap.poll();
            currentTime += minCook.cookTime;
            totalWaitingTime += (currentTime - minCook.orderTime);
        }

        System.out.println(totalWaitingTime/n);
    }
}
@Getter
class Task{
    int orderTime;
    int cookTime;
    Task (int order, int cook){
        orderTime = order;
        cookTime = cook;
    }

}
