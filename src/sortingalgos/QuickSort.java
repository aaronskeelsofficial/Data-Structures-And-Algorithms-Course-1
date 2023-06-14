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

public class QuickSort {

  private static int partition(int[] arr, int start, int end) {
    int pivot = end;
    int i = start - 1;
    for (int j = start;j <= end;j++) {
      if (arr[j] <= arr[pivot]) {
        i++;
        int cache = arr[i];
        arr[i] = arr[j];
        arr[j] = cache;
      }
    }
    return i;
  }

  private static void quickSort(int[] arr, int start, int end) {
    if (start < end) {
      int pivot = partition(arr, start, end);
      quickSort(arr, start, pivot-1);
      quickSort(arr, pivot+1, end);
    }
  }
  
  public static void sort(int[] arr) { // -------------------------------------------------------------------- O(n^2)
    quickSort(arr, 0, arr.length-1);
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