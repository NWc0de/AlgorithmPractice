import java.util.Arrays;
import java.util.Random;

public class MedianOfTwoSortedLists {

    public static void main(String[] args) {
        boolean passed = true;
        Random gen = new Random();
        for (int i = 0; i < 100; i++) {
            int n = gen.nextInt(100000) + 1;
            int[] a = genSortedListOfSize(n), b = genSortedListOfSize(n);
            int[] m = mergeSortedArrays(a, b);
            int res = findMergedMedian(a, b);
            passed = passed && m[(n * 2) / 2] == res;
        }
        if (passed) System.out.println("All tested passed");
        else System.out.println("Test failed");
    }

    /**
     * Recursively computes the median of the array formed by
     * merging a and b. Assumes a and b are sorted in ascending
     * order and of the same size.
     *
     * @param a the first array to be combined
     * @param b the second array to be combined
     * @return the 'upper median' element at (floor(n / 2))
     *          of the combination of a and b
     */
    private static int findMergedMedian(int[] a, int[] b) {
        if (a.length <= 2 && b.length <= 2) {
            int[] merged = mergeSortedArrays(a, b);
            return merged[(a.length + b.length) / 2];
        }

        int am = a[a.length / 2];
        int bm = b[b.length / 2];

        if (am == bm) {
            return am;
        } else if (am < bm) {
            return findMergedMedian( // if a's length is even get one extra element so new arrays are of equal length
                    Arrays.copyOfRange(a, (a.length % 2 == 0 ? (a.length / 2) - 1 : a.length / 2), a.length),
                    Arrays.copyOfRange(b, 0, (b.length / 2) + 1));
        } else {
            return findMergedMedian(
                    Arrays.copyOfRange(a, 0, (a.length / 2) + 1),
                    Arrays.copyOfRange(b, (b.length % 2 == 0 ? (b.length / 2) - 1 : b.length / 2), b.length));
        }
    }

    /**
     * Combines two sorted arrays by merging their contents into
     * a unified sorted array. Assumes both arrays of are of the
     * same size and sorted in ascending order.
     * @param a the first array to merge
     * @param b the second array to merge
     * @return a new sorted array containing all elements of a and b
     */
    public static int[] mergeSortedArrays(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) c[k++] = a[i++];
            else c[k++] = b[j++];
        }

        while (i < a.length) c[k++] = a[i++];
        while (j < b.length) c[k++] = b[j++];

        return c;
    }

    public static int[] genSortedListOfSize(int n) {
        Random gen = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = gen.nextInt(100);
        }
        Arrays.sort(arr);
        return arr;
    }
}
