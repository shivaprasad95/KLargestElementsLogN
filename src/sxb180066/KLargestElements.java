package sxb180066;

public class KLargestElements {
    int arr[] = {1,3,2,4,5,6,8,9,11,10};
    int q = 0;
    void select(int arr[], int k) {
        select(arr,0,arr.length,k);
    }

    int select(int[] arr, int p, int n, int k) {
        if(n<1000) { //TODO: change to some other threshold
            insertionSort(arr,p,p+n-1);
        } else {
            q = randomizedPartition (arr,p,p+n-1);
            int left = q-p;
            int right = n-left-1;
            if(right >= k) {
                return select(arr,q+1,right,k);
            } else if(right+1 == k) {
                return q;
            } else {
                return select(arr,p,left,k-right-1);
            }
        }
        return k;
    }

    public static int randomizedPartition(int[] array, int startIndex, int endIndex) {
        int randomEndIndex = randomNumberBetween(startIndex,endIndex);
        swap(array,endIndex,randomEndIndex);
        return partition(array,startIndex,endIndex);
    }
    public static int randomNumberBetween(int startNumber, int endNumber) {
        return (int)Math.floor(Math.random() * (endNumber - startNumber + 1) + startNumber);
    }
    public static int partition(int[] array,int startIndex,int endIndex) {
        int pivotValue = array[endIndex];
        int pivotIndex = startIndex;

        for (int j = startIndex; j < endIndex; j++) {
            if (array[j] <= pivotValue) {
                swap(array,pivotIndex,j);
                pivotIndex = pivotIndex + 1;
            }
        }
        swap(array,pivotIndex,endIndex);
        return pivotIndex;
    }

    public static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
    /**
     * Function to perform insertion sort
     * @param arr Array to be sorted
     * @param p left index
     * @param r right index
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
        for( int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    public static void main(String[] args) {
        int[] array = {13, 19, 9, 5, 12, 8, 7, 4, 21, 2, 100, -1};
        KLargestElements k = new KLargestElements();
        k.select(array,3);
        k.printArray(array);
        // return 2 4 5 6 7 8 9 11 12 13 19 21
    }

}
