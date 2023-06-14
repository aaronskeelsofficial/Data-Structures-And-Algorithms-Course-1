package src.sortingalgos;

/*------------------------------
* - Note from author:
*    Refer to note from author on BubbleSort.java on Java's ANNOYING generic array reluctance.
*
*    This will be a rudamentary implementation with no optimizations beyond bog standard
*
*    I have simply copied the code from the instructor for this class.
* ------------------------------
*/

public class InsertionSort {

  // (n-1) pairs of numbers that equal n means (n-1)/2 * n = n^2
  public static void sort(int[] arr) { // ------------------------------------------------------------------- O(n^2)
    // We could start i at 1 for optimizations but we're going for BOG STANDARD to make a POINT baby
    for (int i = 0;i < arr.length;i++) {
      int cache = arr[i], j = i;
      while (j > 0 && arr[j-1] > cache) {
        arr[j] = arr[j-1];
        j--;
      }
      arr[j] = cache;
    }
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