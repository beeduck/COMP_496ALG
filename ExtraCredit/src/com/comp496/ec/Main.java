package com.comp496.ec;

public class Main {

    public static void main(String[] args) {

        int a[] = new int[] {1, 2, -5, 1};
        MaxArray maxArray = intersectArray(a, 0, a.length-1);
        return;

    }

    public static MaxArray maxSubArray(int a[]) {
        // Split array in half

        // Max array of left half

        // Max array of right half

        // Max array of intersection
        // Intersection starts with mid-1 + mid + mid+1
        return null;
    }

    private static MaxArray maxSubArray(int a[], int start, int end) {
        // If left array size > 1 or right array size > 1
        int leftEnd = end/2;
        int rightStart = leftEnd + 1;
        MaxArray leftMax, rightMax;
//        if(leftEnd - start > 1 || end - rightStart > 1) {
        if(end - start > 2) {
            leftMax = maxSubArray(a, start, leftEnd);
            rightMax = maxSubArray(a, rightStart, end);
        } else {



        }

        MaxArray intersectMax = intersectArray(a, start, end);

        return null;
    }

    private static MaxArray intersectArray(int a[], int start, int end) {
        int mid = end/2;


        int value = 0;
        MaxArray maxArray = new MaxArray();
        for(int i = start; i < end; i++) {
            value += a[i];
        }

        for(int i = mid; i > start; i--) {
            if(value - a[i] > value) {
                value += a[i];
                maxArray.start = i;
            } else {
                break;
            }
        }

        for(int i = mid; i < end; i++) {
            if(value + a[i] > value) {
                value += a[i];
                maxArray.end = i;
            } else {
                break;
            }
        }

        maxArray.value = value;

        return maxArray;
    }
}

class MaxArray {

    int start, end, value;

    public MaxArray() {
        this.start = this.end = this.value = 0;
    }

    public MaxArray(int start, int end, int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }
}
