package src.searchingalgos;
import java.util.ArrayList;
import java.util.Arrays;

/*------------------------------
* - Note from author:
*    I use ArrayList rather than primitive actual arrays because generics doesn't
*    play well with them in Java because blablablareasonsblabla. I want generics.
*    They feel so essential and near and dear to my heart in these cases. I refuse
*    to allow Java to bully me out of using them.
* ------------------------------
*/

public class LinearSearch {
  
  public static <T extends Comparable<T>> int search(ArrayList<T> arr, T value) { // --------------------------------- O(n)
    for (int i = 0;i < arr.size();i++) {
      if (arr.get(i).equals(value))
        return i;
    }
    return -1;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Search() {
    ArrayList<Integer> arr = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7));
    System.out.println(search(arr,2));
    System.out.println(search(arr,7));
  }
  
}