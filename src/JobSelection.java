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
        System.out.println("Max revenue (no consecutive days): " + res[test.length]);
        System.out.println("Sequence of days: " + worked.toString());
        int sum = 0;
        for (int x : worked) {
            sum += test[x];
        }
        System.out.println("Sum of jobs[i] for each i in sequence of days: " + sum);
        System.out.println("Equals computed result: " + (sum == res[test.length]));

        res = maxJobRevenueTwo(test);
        worked = recoverDaysWorkedTwo(res, test);
        System.out.println("\nMax revenue (two consecutive days): " + res[test.length]);
        System.out.println("Sequence of days: " + worked);
        sum = 0;
        for (int x : worked) {
            sum += test[x];
        }
        System.out.println("\nSum of jobs[i] for each i in sequence of days: " + sum);
        System.out.println("Equals computed result: " + (sum == res[test.length]));
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
     *                   computed by {@code maxJobRevenue}
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

    /**
     * Computes the maximum amount of revenue from the provided list
     * of jobs assuming that two days can be worked consecutively (but
     * not three).
     *
     * @param jobs an array representing jbo revenues
     * @return an array of integers where arr[i] is the max revenue for i days
     */
    private static int[] maxJobRevenueTwo(int[] jobs) {
        int[] maxRevenues = new int[jobs.length + 1];
        maxRevenues[1] = jobs[0];
        maxRevenues[2] = jobs[0] + jobs[1];
        for (int i = 3; i < jobs.length + 1; i++) {
            maxRevenues[i] =
                    max(maxRevenues[i - 1],
                            maxRevenues[i - 2] + jobs[i - 1],
                            maxRevenues[i - 3] + jobs[i - 2] + jobs[i - 1]);
        }
        return maxRevenues;
    }

    /**
     * Computes the indices of the days which were worked to produce the
     * maximum revenue at maxRevenue[maxRevenue.length - 1], assuming
     * two days can be worked consecutively (but not three).
     *
     * @param maxRevenue an array of integers representing the max revenue
     *                   computed by {@code maxJobRevenueTwo}
     * @param jobs an array representing job revenues that were used to compute
     *             maxRevenue
     * @return a LinkedList of integers representing the indices of the days
     *         worked to achieve the max revenue for the last day
     */
    private static List<Integer> recoverDaysWorkedTwo(int[] maxRevenue, int[] jobs) {
        List<Integer> daysWorked = new LinkedList<>();
        for (int i = jobs.length - 1; i >= 2; i--) {
            if (maxRevenue[i + 1] == jobs[i] + maxRevenue[i - 1])  {
                daysWorked.add(i--);
            }
            else if (maxRevenue[i + 1] == jobs[i] + jobs[i - 1] + maxRevenue[i - 2]) {
                daysWorked.add(i);
                daysWorked.add(i - 1);
                i -= 2;
            }
        }
        return daysWorked;
    }

    /**
     * A helper method to compute the minimum integer from the
     * provided params.
     * @param ints an array or list of parameters to compute the max of
     * @return the maximum integer from the provided values
     */
    private static int max(Integer... ints) {
        int max = Integer.MIN_VALUE;
        for (int x : ints) {
            if (x > max) max = x;
        }
        return max;
    }
}
