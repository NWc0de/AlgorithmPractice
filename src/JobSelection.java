/*
 * The file contains an algorithmic exploration of the job selection
 * problem: given an array of n "jobs" (integers representing wages)
 * which are available in a set of consecutive days relating to their
 * index in the array (eg. the job at index 1 happens day 1, etc.),
 * what is the maximum amount of revenue you can make if you can not
 * work on consecutive days.
 */


import java.util.LinkedList;
import java.util.List;

/**
 * A dynamic programming approach the job selection problem.
 */
public class JobSelection {

    public static void main(String[] args) {
        int[] test = new int[] {5,9,12,7,5,13,7,5,4,9,8,7,5,8,4,3,5,10,4,6,8,12,5,6,3,7,16,2,2,16};
        int[] res = maxJobRevenue(test);
        List<Integer> worked = recoverDaysWorked(res, test);
        System.out.println("Max revenue: " + res[test.length]);
        System.out.println("Sequence of days: " + worked.toString());
        int sum = 0;
        for (int x : worked) {
            sum += test[x];
        }
        System.out.println(sum);
    }


    /**
     * Computes the maximum amount of revenue from the provided list
     * of jobs.
     *
     * @param jobs an array representing job revenues
     * @return an array of integers where arr[i] is the max revenue for i days
     */
    private static int[] maxJobRevenue(int[] jobs) {
        int[] maxRevenues = new int[jobs.length + 1];
        maxRevenues[1] = jobs[0];
        for (int i = 2; i < jobs.length + 1; i++) {
            maxRevenues[i] = Math.max(maxRevenues[i - 1], maxRevenues[i - 2] + jobs[i - 1]);
        }
        return maxRevenues;
    }

    /**
     * Computes the indices of the days which were worked to produce the
     * maximum revenue at maxRevenue[maxRevenue.length - 1].
     *
     * @param maxRevenue an array of integers representing the max revenue
     *                   computed by {@code maxJobRevenue}.
     * @param jobs an array representing job revenues that were used to compute
     *             maxRevenue
     * @return a LinkedList of integers representing the indices of the days
     *         worked to achieve the max revenue for the last day
     */
    private static List<Integer> recoverDaysWorked(int[] maxRevenue, int[] jobs) {
        List<Integer> daysWorked = new LinkedList<>();
        for (int i = jobs.length - 1; i >= 1; i--) {
            if (maxRevenue[i + 1] == jobs[i] + maxRevenue[i - 1]) daysWorked.add(i--);
        }
        if (daysWorked.get(0) != 1) daysWorked.add(0); // account for case when i = 1 and it wasn't worked
        return daysWorked;
    }
}
