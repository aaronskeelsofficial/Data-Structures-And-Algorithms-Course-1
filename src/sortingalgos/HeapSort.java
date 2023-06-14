package src.sortingalgos;
import src.trees.binaryheaparray.*;

/*------------------------------
* - Note from author:
*    Refer to note from author on BubbleSort.java on Java's ANNOYING generic array reluctance.
*
*    This will be a rudamentary implementation with no optimizations beyond bog standard
*
*    I have incorporated my from-scratch BinaryHeap code in this class (not following course code)
* ------------------------------
*/

public class HeapSort {
  
  public static void sort(int[] arr) { // -------------------------------------------------------------------- O(nlogn)
    int depth = (int) Math.ceil(Math.log(arr.length)/Math.log(2));
    BinaryHeapArr<Integer> bh = new BinaryHeapArr<>(depth, BinaryHeapType.MINIMUM);
    for (int i : arr) // ------------------------------------------------------------------------- O(n*logn) = O(nlogn)
      bh.insert(i); // --------------------------------------------------------------------------------------- O(logn)
    for (int i = 0;i < arr.length;i++) // -------------------------------------------------------- O(n*logn) = O(nlogn)
      arr[i] = bh.extract(); // ------------------------------------------------------------------------------ O(logn)
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