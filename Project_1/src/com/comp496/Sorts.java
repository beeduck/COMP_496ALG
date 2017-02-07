package com.comp496;

/**
 * Created by Duck on 2/5/2017.
 */
public class Sorts
{
//
//    /*--------------Insertion Sort -----------------------*/
//    public static long insertionsort(int[] a)
//    {
//
//    }
//


    /*--------------------Merge Sort --------------------*/
    //merges sorted slices a[i.. j] and a[j + 1 …  k] for 0<=  i <=j < k < a.length

    public static long merge(int[] a, int i, int j, int k)
    {
        long comparisons = 0;

        // Create array of total size
        int tempSize = k-i+1;

        // Move on if a area is only 1 element
        if(tempSize < 2) {
            return comparisons;
        }

        int[] temp = new int[tempSize];
        int tempPos = 0;
        int leftPos = i;
        int rightPos = j+1;

        while(tempPos < k-i+1) {

            // If each array counter has not reached their ends
            if(leftPos <= j && rightPos <= k) {

                if(a[leftPos] <= a[rightPos]) {
                    temp[tempPos++] = a[leftPos++];
                } else {
                    temp[tempPos++] = a[rightPos++];
                }
                comparisons++;

            // If the left array is still not at its end
            } else if(leftPos <= j) {
                temp[tempPos++] = a[leftPos++];

            // If the right array is still not at its end
            } else if(rightPos <= k) {
                temp[tempPos++] = a[rightPos++];
            }

        }

        // Move temp array values to original array
        tempPos = 0;
        for(int swapPos = i; swapPos <= k; swapPos++) {
            a[swapPos] = temp[tempPos++];
        }

        return comparisons;
    }


    //sorts  a[ i .. k]  for 0<=i <= k < a.length
    private static long mergesort(int[] a, int i, int k)
    {
        // Mid point between k and i
        int mid = i + (k-i)/2;

        if(k-i >= 2) {
            mergesort(a, i, mid);
            mergesort(a, mid + 1, k);
        }

        return merge(a, i, mid, k);
    }


    //Sorts the array using mergesort
    public static  long mergesort(int[] a )
    {
        return mergesort(a, 0, a.length-1);
    }


    public static boolean isSorted(int[] a)
    {
        for(int i = 1; i < a.length; i++) {
            if(a[i] < a[i-1])
                return false;
        }

        return true;
    }

}
