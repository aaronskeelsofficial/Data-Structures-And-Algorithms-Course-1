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

public class BinarySearch {
  
  public static <T extends Comparable<T>> int search(ArrayList<T> arr, T value) { // --------------------------------- O(logn)
    // int MAX_RUNS = 500; // We had a couple infinite loops goin whoops 👀
    // Here I set begIndex to -1 and endIndex to size which might feel odd, but it's
    //  simply to offset the fact that I like changing the end indices to middleIndex
    //  rather than offsetting THAT by 1. We like to have fun here.
    int begIndex = -1, endIndex = arr.size();
    int middleIndex = 1;
    while (begIndex < arr.size()-1 && endIndex > 0/* && MAX_RUNS-- > 0*/) {
      middleIndex = (int) ((begIndex + endIndex) / 2.0f);
      if (arr.get(middleIndex).compareTo(value) == 0) {
        return middleIndex;
      } else if (arr.get(middleIndex).compareTo(value) < 0) {
        begIndex = middleIndex;
      } else {
        endIndex = middleIndex;
      }
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
    System.out.println(search(arr,0));
    System.out.println(search(arr,1));
    System.out.println(search(arr,2));
    System.out.println(search(arr,3));
    System.out.println(search(arr,4));
    System.out.println(search(arr,5));
    System.out.println(search(arr,6));
    System.out.println(search(arr,7));
    System.out.println(search(arr,8));
    ArrayList<Integer> bigboy = new ArrayList<>();
    for(int i = 0;i < 10000;i++)
      bigboy.add(i);
    System.out.println(search(bigboy,0));
    System.out.println(search(bigboy,9999));
    //Success
  }
  
}