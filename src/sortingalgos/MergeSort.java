package src.sortingalgos;

/*------------------------------
* - Note from author:
*    Refer to note from author on BubbleSort.java on Java's ANNOYING generic array reluctance.
*
*    This will be a rudamentary implementation with no optimizations beyond bog standard
*
*    I have simply copied the code from the instructor for this class.
*
*    This implementation uses recursion which makes time complexity analysis disgusting. *ptuh*. Ew.
* ------------------------------
*/

public class MergeSort {

  private static void merge(int[] arr, int left, int middle, int right) {
    // Step 1. Initialize temporary arrays for operation
    int[] leftArr = new int[middle-left+2]; // Allegedly +2 solves out of index range. I haven't thought it through.
    int[] rightArr = new int[right-middle+1]; // Allegedly +1 solves out of index range. I haven't thought it through.
    // Step 2. Fill arrays
    for (int i = 0;i <= middle-left;i++) {
      leftArr[i] = arr[left+i];
    }
    for (int i = 0;i < right-middle;i++) {
      rightArr[i] = arr[middle+1+i];
    }
    // Step 3. Operate on arrays (Continuously pull whether left or right branch has min in left position)
    leftArr[middle-left+1] = Integer.MAX_VALUE;
    rightArr[right-middle] = Integer.MAX_VALUE;
    int i = 0, j = 0;
    for (int k = left;k <= right;k++) {
      if (leftArr[i] < rightArr[j]) {
        arr[k] = leftArr[i];
        i++;
      } else {
        arr[k] = rightArr[j];
        j++;
      }
    }
  }

  private static void split(int[] arr, int left, int right) {
    if (right > left) {
      int m = (left+right)/2;
      split(arr, left, m);
      split(arr, m+1, right);
      merge(arr, left, m, right);
    }
  }
  
  public static void sort(int[] arr) { // ------------------------------------------------------------------- O(nlogn)
    split(arr, 0, arr.length-1);
  }

  public static String toString(int[] arr) {
    String str = "";
    for (int i = 0;i < arr.length;i++) {
      str += arr[i] + " ";
    }
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Sort() {
    int[] arr = {1,5,2,4,3};
    System.out.println(toString(arr));
    sort(arr);
    System.out.println(toString(arr));
  }
  
}