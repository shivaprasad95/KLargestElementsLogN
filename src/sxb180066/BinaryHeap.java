/*
* Implementation of priority queue using binary heap
* @author Bitla Shiva Prasad Reddy, sxb180066
* @author Rajitha Koppisetty, rxk164330
*/
package sxb180066;
import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int size; //maintains current size of the heap

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
        size=0;
    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
    }

    /**
     * Method to add element to the heap, throw exception if pq is full
     * @param x Generic parameter to be added
     * @return void
     */
    public void add(T x) {
        if(size >= pq.length) {
            throw new IndexOutOfBoundsException(); // throws the IndexOutOfBoundsException (could be anything)
        } else {
            pq[size] = x;
            percolateUp(size);
            size++;
        }
    }

    /**
     * Method to offer element to the heap, throw exception if pq is full
     * @param x Generic parameter to be added
     * @return void
     */
    public boolean offer(T x) { /* return false if pq is full */
        if(size >= pq.length) {
            return false;
        } else {
            add(x);
            return true;
        }
    }

    /**
     * Method to Remove first element of the heap, throw exception if pq is full
     * @return Generic value removed from heap
     */
    public T remove() { /* throw exception if pq is empty */
        if(size == 0) {
            throw new IndexOutOfBoundsException(); // throws the IndexOutOfBoundsException (could be anything)
        } else {
            T min = pq[0];
            pq[0] = pq[size - 1];
            size--;
            percolateDown(0);
            return min;
        }
    }

    /**
     * Method to Poll first element of the heap
     * @return Generic value removed from heap, NULL if pq is empty
     */
    public T poll() {
        if(size == 0) {
            return null;
        } else {
            T x = remove();
            return x;
        }
    }

    /**
     * Method to Display first element of the heap
     * @return Generic value which is first element of the heap, NULL if pq is empty
     */
    public T peek() { /* return null if pq is empty */
        if(size == 0) {
            return null;
        } else {
            return pq[0];
        }
    }

    /**
     * Method to percolateUp, used if pq[i] may violate heap order with parent
     * @return void
     */
    void percolateUp(int i) {
        T x = pq[i];

        while (comp.compare(pq[parent(i)],x) > 0 && i > 0) {
            pq[i] = pq[parent(i)];
            i = parent(i);
        }
        pq[i] = x;
    }

    /**
     * Method to percolateDown, used if pq[i] may violate heap order with children
     * @return void
     */
    void percolateDown(int i) {
        T x = pq[i];
        int c = leftChild(i);
        while(c <= size-1) {
            if(c + 1 < size && comp.compare(pq[c], pq[c + 1]) > 0) {
                c++;
            }
            if(comp.compare(x, pq[c]) <= 0) {
                break;
            }
            pq[i] = pq[c];
            i = c;
            c = leftChild(i);
        }
        pq[i] = x;
    }

    /**
     * Method to Assign x to pq[i].  Indexed heap will override this method
     * @return void
     */
    void move(int i, T x) {
        T prev = pq[i];
        pq[i] = x;
        if(comp.compare(x, prev) < 0) { // Perculate Up or Down after comparing new value with previous value
            percolateUp(i);
        } else {
            percolateDown(i);
        }
    }

    /**
     * @param i index of element
     * @return  parent of i
     */
    int parent(int i) {
        return (i-1)/2;
    }

    /**
     * @param i index of element
     * @return  leftChild of i
     */
    int leftChild(int i) {
        return 2*i + 1;
    }

    /**
     * Method to display the inorder traversal of the tree
     */
    public void heapInorder() {
        if(size == 0) {
            System.out.println("Heap is empty");
            return;
        }
        System.out.println("Inorder traversal of heap: ");
        for(int i = 0; i < size; i++) {
            System.out.print(pq[i] + " ");
        }
        System.out.println("");
    }

    //Driver Method
    public static void main(String args[]) {

        Integer[] arr = {1,2,3,4,5,10,11,9,7,6};
        BinaryHeap<Integer> pq = new BinaryHeap<Integer>(arr);
        pq.size = 4; //k;
        for(int i = 0;i<arr.length;i++) {
            if(pq.peek() == null) {
                pq.add(arr[i]);
            } else {
                if(pq.peek() < arr[i]) {
                    pq.poll();
                    pq.add(arr[i]);
                }
            }
        }
        pq.heapInorder();


    }

}