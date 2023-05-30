package arrays;
import java.util.Arrays;

/** Represents strip ends class which strips an array's first and last elements, returning the middle elements
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class StripEnds_43 {
  int[] array;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public StripEnds_43(int[] array) {
    this.array = array;
  }

  /** Strips an array's first and last elements, returning the middle elements
  * @version 1.0.0
  * @since 1.0.0
  * @return Middle elements of array, having stripped the first and last
  */
  public int[] Strip() { // ------------------------------------------------------------------------------ O(n)
    // If array has less than 2 elements, return an empty array
    if (array.length <= 2) { // -------------------------------------------------------------------------- O(1)
      return new int[0]; // ------------------------------------------------------------------------------ O(1)
    }

    //Strip array
    int[] strippedArray = new int[array.length - 2]; // -------------------------------------------------- O(1)
    for (int i = 0;i < strippedArray.length;i++) { // ---------------------------------------------------- O(n)
      strippedArray[i] = array[i+1]; // ------------------------------------------------------------------ O(1)
    }
    return strippedArray; // ----------------------------------------------------------------------------- O(1)
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Strip() {
    StripEnds_43 se1 = new StripEnds_43(new int[] {1,2,3,4,5});
    StripEnds_43 se2 = new StripEnds_43(new int[] {1});
    System.out.println(Arrays.toString(se1.Strip()));
    System.out.println(Arrays.toString(se2.Strip()));
  }
}