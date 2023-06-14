package src.sortingalgos;
import java.util.Arrays;
import java.util.Random;

/*------------------------------
* - Note from author:
*    Refer to note from author on BubbleSort.java on Java's ANNOYING generic array reluctance.
*
*    The purpose of this class is to attempt to recode merge sort iteratively instead of recursively
*    so I can have confidence in my understanding of its time complexity. I am having a really hard
*    time accepting any explanation online of the recursive version.
* ------------------------------
*/

public class MergeSortIterative {
  private static boolean debug = false;

  private static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
  private static int[] buildArr(int size) {
    int[] arr = new int[size];
    for(int i = 1;i <= size;i++)
      arr[i-1] = i;
    return arr;
  }
  private static int[] buildRandArr(int size) {
    int[] arr = new int[size];
    for (int i = 0; i < arr.length; i++) {
       arr[i] = getRandomNumber(0,size); // storing random integers in an array
    }
    return arr;
  }
  
  private static void sortv1(int[] arr) {
    if (arr.length <= 1)
      return;
    
    // Logic 1. I know the smallest arrays will start with length 1 and multiply by 2 each iteration
    //  until they are half a positive n or half+1 a negative n.
    //  In short, we are looping through breaking the big boy array into size 1, 2, 4, 8, etc
    //  This loop will run ceil(logn) times aka time complexity multiplier * (logn + 1) worst case
    // This represents how many "layers of branches of recursion" would be present if handled recursively
    if (debug) System.out.println(arr.length + " :: ");
    int totalLayers = ((int) Math.ceil(Math.log(arr.length) / Math.log(2)));
    int subArrayLengthThisLayer = 1;
    int numberOfGroupComparisonsThisLayer = (int) Math.ceil( arr.length / ( subArrayLengthThisLayer*2f));
    for (int layer = 0;layer < totalLayers;layer++) {
      // Logic 2. It gets confusing to have nested upon nested for loops which all change size each of their
      //  runs. However, in short we can clump up all actions in a summarized version. For each "layer" of
      //  "recursion" (actually iteration in this implementation), all n elements with in the array will
      //  have the following done to them ONCE.
      //   1. Moved to temporary array
      //   2. Compared against one single other element before #3 (technically they can be compared more than once but doesn't matter)
      //   3. Moved back into original array (somewhere, possibly not original index)
      //  Due to having an outside loop of logn which (in summary) has an inner loop of 3n, the time complexity
      //  of the overall system becomes O(3n*logn) which simplifies to O(n*logn).
      if (debug) System.out.println("  " + subArrayLengthThisLayer + ":" + numberOfGroupComparisonsThisLayer);
      for (int groupComparison = 0;groupComparison < numberOfGroupComparisonsThisLayer;groupComparison++) {
        // i is relative to original array and represents combined index back into main array
        // iL is relative to temp array and represents left comparison array index
        // iR is relative to temp array and represents right comparison array index
        int i = groupComparison * (subArrayLengthThisLayer*2);
        int iL = 0;
        int iR = 0;
        // L, M, and R are temp values simply used in the creation of the temporary left and right arrays
        // R is also used in a loop below to dictate "end of group comparison index" relative to original array
        int L = i;
        int M = i+subArrayLengthThisLayer;
        M = (M > arr.length) ? arr.length : M;
        int R = i+subArrayLengthThisLayer*2;
        R = R > (arr.length) ? arr.length : R;
        int[] arrL = Arrays.copyOfRange(arr, L, M);
        int[] arrR = Arrays.copyOfRange(arr, M, R);
        if (debug) System.out.println("    Comparison " + groupComparison + ": " + Arrays.toString(arrL) + " : " + Arrays.toString(arrR));
        if (debug) System.out.print("      Result: ");
        // Now we build up the "group" comparisons in the main array sweeping left to right through the main array
        //  pulling the smaller number from the leftmost of either the left or right temp array
        for ( ;i < R;i++) {
          if (iR >= arrR.length || (iL < arrL.length && arrL[iL] <= arrR[iR])) {
            arr[i] = arrL[iL];
            iL++;
          } else if (iL >= arrL.length || (iR < arrR.length && arrR[iR] <= arrL[iL])) {
            arr[i] = arrR[iR];
            iR++;
          }
          if (debug) System.out.print(arr[i] + ",");
        }
        if (debug) System.out.println("");
      }
      
      subArrayLengthThisLayer *= 2;
      numberOfGroupComparisonsThisLayer = (int) Math.ceil((float) arr.length / ((float) subArrayLengthThisLayer*2f));
    }
    if (debug) System.out.println("Total Layers: " + totalLayers);
  }
  private static void sortv2(int[] arr) {
    if (arr.length <= 1)
      return;
    int totalLayers = ((int) Math.ceil(Math.log(arr.length) / Math.log(2)));
    int subArrayLengthThisLayer = 1;
    int numberOfGroupComparisonsThisLayer = (int) Math.ceil( arr.length / ( subArrayLengthThisLayer*2f));
    for (int layer = 0;layer < totalLayers;layer++) {
      for (int groupComparison = 0;groupComparison < numberOfGroupComparisonsThisLayer;groupComparison++) {
        int i = groupComparison * (subArrayLengthThisLayer*2);
        int iL = 0;
        int iR = 0;
        int L = i;
        int M = i+subArrayLengthThisLayer;
        M = (M > arr.length) ? arr.length : M;
        int R = i+subArrayLengthThisLayer*2;
        R = R > (arr.length) ? arr.length : R;
        int[] arrL = Arrays.copyOfRange(arr, L, M);
        int[] arrR = Arrays.copyOfRange(arr, M, R);
        for ( ;i < R;i++) {
          if (iR >= arrR.length || (iL < arrL.length && arrL[iL] <= arrR[iR])) {
            arr[i] = arrL[iL];
            iL++;
          } else if (iL >= arrL.length || (iR < arrR.length && arrR[iR] <= arrL[iL])) {
            arr[i] = arrR[iR];
            iR++;
          }
        }
      }
      
      subArrayLengthThisLayer *= 2;
      numberOfGroupComparisonsThisLayer = (int) Math.ceil( arr.length / ( subArrayLengthThisLayer*2f));
    }
  }
  private static void sortv3(int[] arr) {
    if (arr.length <= 1)
      return;

    int[] arrL = new int[arr.length];
    int[] arrR = new int[arr.length];
    int i, iL, iR;
    int L, M, R;
    int arrLLength, arrRLength;
    int totalLayers = ((int) Math.ceil(Math.log(arr.length) / Math.log(2)));
    int subArrayLengthThisLayer = 1;
    int numberOfGroupComparisonsThisLayer = (int) Math.ceil(arr.length / (subArrayLengthThisLayer*2f));
    for (int layer = 0;layer < totalLayers;layer++) {
      for (int groupComparison = 0;groupComparison < numberOfGroupComparisonsThisLayer;groupComparison++) {
        i = groupComparison * (subArrayLengthThisLayer*2);
        iL = 0;
        iR = 0;
        L = i;
        M = i+subArrayLengthThisLayer;
        M = (M > arr.length) ? arr.length : M;
        R = i+subArrayLengthThisLayer*2;
        R = R > (arr.length) ? arr.length : R;
        arrLLength = M-L;
        arrRLength = R-M;
        if (L >= arr.length) //If we have a number like 6 which will compare against null arrays this saves time
          continue;
        if (M >= arr.length) // If there is no right array to compare against, simply skip bc left already ordered
          continue;
        System.arraycopy(arr, L, arrL, 0, M-L);
        System.arraycopy(arr, M, arrR, 0, R-M);
        for ( ;i < R;i++) {
          if (iR >= arrRLength || (iL < arrLLength && arrL[iL] <= arrR[iR])) {
            arr[i] = arrL[iL];
            iL++;
          } else if (iL >= arrLLength || (iR < arrRLength && arrR[iR] <= arrL[iL])) {
            arr[i] = arrR[iR];
            iR++;
          }
        }
      }
      
      subArrayLengthThisLayer *= 2;
      numberOfGroupComparisonsThisLayer = (int) Math.ceil(arr.length / (subArrayLengthThisLayer*2f));
    }
  }
  public static void sort(int[] arr) { // ----------------------------------------------------------------- O(nlogn)
    // sortv2(arr);
    sortv3(arr);
  }

  public static String getVersion(){
    // return "v2";
    return "v3";
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
    //Clean for fundamental debugging only
    // sort(buildArr(0));
    // sort(buildArr(1));
    // sort(buildArr(2));
    // sort(buildArr(3));
    // sort(buildArr(4));
    // sort(buildArr(5));
    // sort(buildArr(6));
    // sort(buildArr(7));
    // sort(buildArr(8));
    //----
    //Messy for actual testing debugging
    // sort(new int[] {});
    // sort(new int[] {1});
    // sort(new int[] {2,1});
    // sort(new int[] {2,3,1});
    // sort(new int[] {4,2,3,1});
    // sort(new int[] {5,2,4,3,1});
    // sort(new int[] {6,2,5,3,4,1});
    // sort(new int[] {7,2,6,3,5,4,1});
    // sort(new int[] {8,2,7,3,6,4,5,1});
    //----
    //Final Stage Testing
    int[] a8 = new int[] {8,2,7,3,6,4,5,1};
    sort(a8);
    System.out.println(Arrays.toString(a8));
    //Success
  }
  public static void Test_SortV3() {
    int[] a8 = new int[] {1,2,3,4,5,6,7};
    sortv3(a8);
    System.out.println(Arrays.toString(a8));
    int[] a20 = buildRandArr(20);
    sortv3(a20);
    System.out.println(Arrays.toString(a20));
  }
  
}