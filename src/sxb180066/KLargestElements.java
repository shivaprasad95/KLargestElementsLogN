/*
 * Project: Find k largest elements in O(n) runtime.
 * @author Bitla Shiva Prasad Reddy, sxb180066
 * @author Rajitha Koppisetty, rxk164330
 */
package sxb180066;
import sxb180066.BinaryHeap;
import java.util.Arrays;
import java.util.Random;

public class KLargestElements {
    int arr[] = {1, 3, 2, 4, 5, 6, 8, 9, 11, 10};
    int q = 0;
    public static Random random = new Random();
    public static int numTrials = 1;
    int T = 17; //Threshold


    void select(int arr[], int k) {
        int a = select(arr, 0, arr.length, k);
        System.out.println("index: " + a);
    }

    int select(int[] arr, int p, int n, int k) {
        if (n < T) {
            insertionSort(arr, p, p + n - 1);
        } else {
            q = randomizedPartition(arr, p, p + n - 1);
            int left = q - p;
            int right = n - left - 1;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            } else if (right + 1 == k) {
                return q;
            } else {
                return select(arr, p, left, k - right - 1);
            }
        }
        return k;
    }

    public static int randomizedPartition(int[] array, int startIndex, int endIndex) {
        int randomEndIndex = randomNumberBetween(startIndex, endIndex);
        swap(array, endIndex, randomEndIndex);
        return partition(array, startIndex, endIndex);
    }

    public static int randomNumberBetween(int startNumber, int endNumber) {
        return (int) Math.floor(Math.random() * (endNumber - startNumber + 1) + startNumber);
    }

    public static int partition(int[] array, int startIndex, int endIndex) {
        int pivotValue = array[endIndex];
        int pivotIndex = startIndex;

        for (int j = startIndex; j < endIndex; j++) {
            if (array[j] <= pivotValue) {
                swap(array, pivotIndex, j);
                pivotIndex = pivotIndex + 1;
            }
        }
        swap(array, pivotIndex, endIndex);
        return pivotIndex;
    }

    public static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Function to perform insertion sort
     *
     * @param arr Array to be sorted
     * @param p   left index
     * @param r   right index
     */
    private static void insertionSort(int[] arr, int p, int r) {
        for (int i = p + 1; i <= r; i++) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= p && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void main(String[] args) {

        int n = 1 << 23;
        int choice = 2;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            choice = Integer.parseInt(args[1]);
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        int k = arr.length/2;
        Integer arr1[] = new Integer[k];


        Timer timer = new Timer();
        switch (choice) {
            case 1:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    KLargestElements l = new KLargestElements();
                    l.select(arr, k);
                }
                break;
            case 2:
                for (int i = 0; i < numTrials; i++) {
                    Shuffle.shuffle(arr);
                    BinaryHeap<Integer> minHeap = new BinaryHeap<Integer>(arr1);
                    for(int j : arr) {
                        if((minHeap.peek() == null) || (minHeap.size < k) || (minHeap.peek() < j && minHeap.poll() != null)) {
                            minHeap.add(j);
                        }
                    }
                }
                break;
        }
        timer.end();
        timer.scale(numTrials);
        System.out.println("Choice: " + choice + ", " + "Elements: " + n + "\n" + timer);
    }

    public static class Timer {

        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime - startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if (!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if (!ready) {
                end();
            }
            return memUsed;
        }

        public void scale(int num) {
            elapsedTime /= num;
        }

        @Override
        public String toString() {
            if (!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / " + (memAvailable / 1048576) + " MB.";
        }
    }

    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length - 1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from + 1;
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }

        public static <T> void shuffle(T[] arr, int from, int to) {
            int n = to - from + 1;
            Random random = new Random();
            for (int i = 1; i < n; i++) {
                int j = random.nextInt(i);
                swap(arr, i + from, j + from);
            }
        }
        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static <T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }


        public static <T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for (int i = from; i <= to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }

    }
}