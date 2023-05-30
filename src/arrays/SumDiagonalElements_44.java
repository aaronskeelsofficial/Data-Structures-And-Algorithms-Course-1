package arrays;

/** Represents sum diagonal elements class which sums an array's diagonal elements
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class SumDiagonalElements_44 {
  int[][] array;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public SumDiagonalElements_44(int[][] array) {
    this.array = array;
  }

  /** Sums an array's diagonal elements
  * @version 1.0.0
  * @since 1.0.0
  * @return Sum of array's diagonal elements
  */
  public int Sum() { // ------------------------------------------------------------------------------ O(min(a,b))
    int sum = 0; // ---------------------------------------------------------------------------------- O(1)
    int maxRow = array.length; // -------------------------------------------------------------------- O(1)
    //Assumes all columns are same size
    int maxColumn = array[0].length; // -------------------------------------------------------------- O(1)
    for (int i = 0;i < Math.min(maxRow, maxColumn);i++) { // ----------------------------------------- O(min(a,b))
      sum += array[i][i]; // ------------------------------------------------------------------------- O(1)
    }
    return sum; // ----------------------------------------------------------------------------------- O(1)
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Sum() {
    int[][] array1 = {{1,2,3},{4,5,6},{7,8,9}};
    SumDiagonalElements_44 sde1 = new SumDiagonalElements_44(array1);
    System.out.println(sde1.Sum());
    int[][] array2 = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
    SumDiagonalElements_44 sde2 = new SumDiagonalElements_44(array2);
    System.out.println(sde2.Sum());
  }
}