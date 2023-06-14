package src.sortingalgos;
import java.util.ArrayList;
import java.util.Collections;

/*------------------------------
* - Note from author:
*    Refer to note from author on BubbleSort.java on Java's ANNOYING generic array reluctance.
*
*    This will be a rudamentary implementation with no optimizations beyond bog standard
*
*    I have simply copied the code from the instructor for this class.
* ------------------------------
*/

public class BucketSort {

  // Note: Time complexity analysis (my coming to terms with why it's what it is) can be
  //  found within the "Questions/Ideas Building Off Material" section of the summary
  //  google doc here:
  //  https://docs.google.com/document/d/13lGgwgYIxSxmu5eq02kCt7QCmyDqdGi1f3RETBGU5Y8/edit?usp=sharing
  public static void sort(int[] arr) { // ------------------------------------------------------------------- O(depends on child sorting fn)
    // Step 1. Define bucket count (and implicitly suggest that many items per bucket)
    int numOfBuckets = (int) Math.ceil(Math.sqrt(arr.length));
    // Step 2. Find maximum value in array. This will be converted to % and used to identify bucket for each element.
    int maxValue = Integer.MIN_VALUE;
    for (int value : arr) {
      if (value > maxValue) {
        maxValue = value;
      }
    }
    // Step 3. Move values into associated buckets
    ArrayList<Integer>[] buckets = new ArrayList[numOfBuckets];
    for (int i = 0;i < buckets.length;i++)
      buckets[i] = new ArrayList<Integer>();
    for (int value : arr) {
      int bucketNumber = (int) Math.ceil(((float) value / (float) maxValue) * (float) numOfBuckets);
      // ArrayList insertion worst case takes O(n)
      buckets[bucketNumber-1].add(value);
    }
    // Step 4. Sort each individual bucket using another sorting algorithm
    // The class uses Collections.sort which internally uses an Adaptive Mergesort.
    //  The internet says this is blatantly inferior to Arrays.sort Dual-Pivot Quicksort
    //  for primitive data types (aka integers which we're working with). An optimization
    //  would be to either use actual arrays or *queue evil music* use reflections to access
    //  the internal array stored inside of ArrayList hahahahAHAHAHAHA WE HAVE FUN HERE
    for (ArrayList<Integer> bucket : buckets)
      Collections.sort(bucket);
    int index = 0;
    for (ArrayList<Integer> bucket : buckets) {
      for (int value : bucket) {
        arr[index] = value;
        index++;
      }
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