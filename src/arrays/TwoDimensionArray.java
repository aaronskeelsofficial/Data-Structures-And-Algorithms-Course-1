package arrays;

/** Represents a two dimensional array and all functional implementations
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.3
*/
public class TwoDimensionArray {
  int[][] arr = null; // Declaration --------------------------------------------------------------------- O(1)

  /** Creates a TwoDimensionArray with a static specified size
  * @param numberOfRows Target rows found in array
  * @param numberOfColumns Target columns found in array
  * @version 1.0.0
  * @since 1.0.0
  */
  public TwoDimensionArray(int numberOfRows, int numberOfColumns) { // ---------------------------------- O(a*b)
    // Technically instantiation sets all values to 0 which I see as O(n) but it's allegedly O(1)
    arr = new int[numberOfRows][numberOfColumns]; // Instantiation -------------------------------------- O(1)
    for (int i = 0;i < numberOfRows;i++) { // Initialization -------------------------------------------- O(a*b)
      for (int j = 0;j < numberOfColumns;j++) { // ------------------------------------------------------ O(b)
        arr[i][j] = Integer.MIN_VALUE; // --------------------------------------------------------------- O(1)
      }
    }
  }

  /** Gets value at a specified row and column
  * @param row Target row
  * @param column Target column
  * @version 1.0.0
  * @since 1.0.1
  * @return Value at specified row and column
  */
  public int get(int row, int column) { // ---------------------------------------------------------------- O(1)
    try { // ---------------------------------------------------------------------------------------------- O(1)
      return arr[row][column]; // ------------------------------------------------------------------------- O(1)
    } catch (ArrayIndexOutOfBoundsException e) { // ------------------------------------------------------- O(1)
      System.out.println("Invalid array index: R" + row + "C" + column + "."); // ------------------------- O(1)
    }
    return 0; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Attempts to insert a value into a specified row and column
  * @param row Target row to attempt insertion at
  * @param column Target column to attempt insertion at
  * @param valueToBeInserted Value to attempt to insert
  * @version 1.0.0
  * @since 1.0.0
  */
  public void insert(int row, int column, int valueToBeInserted) { // ------------------------------------- O(1)
    try { // ---------------------------------------------------------------------------------------------- O(1)
      if (arr[row][column] == Integer.MIN_VALUE) { // ----------------------------------------------------- O(1)
        arr[row][column] = valueToBeInserted; // ---------------------------------------------------------- O(1)
        System.out.println("Successfully inserted " + valueToBeInserted + " @ R" + row + "C" + column + "."); // ---- O(1)
      } else { // ----------------------------------------------------------------------------------------- O(1)
        System.out.println("R " + row + "C" + column + " is already occupied!"); // ----------------------- O(1)
      }
    } catch (ArrayIndexOutOfBoundsException e) { // ------------------------------------------------------- O(1)
      System.out.println("Invalid array index: R" + row + "C" + column + "."); // ------------------------- O(1)
    }
  }

  /** Conduct a linear search for if a value is found within the array at all
  * @param valueToSearch The value to search for within the array
  * @return Whether or not value is found within the array
  * @version 1.0.0
  * @since 1.0.3
  */
  public boolean searchInArray_Linear(int valueToSearch) { // ---------------------------------------------- O(a*b)
    for (int row = 0;row < arr.length;row++) { // ---------------------------------------------------------- O(a*b)
      for (int column = 0;column < arr[0].length;column++) { // -------------------------------------------- O(b)
        if (get(row, column) == valueToSearch) { // -------------------------------------------------------- O(1)
          System.out.println(valueToSearch + " was found in array @ R" + row + "C" + column + "."); // ----- O(1)
          return true; // ---------------------------------------------------------------------------------- O(1)
        }
      }
    }
    System.out.println(valueToSearch + " was not found in array."); // ------------------------------------- O(1)
    return false; // --------------------------------------------------------------------------------------- O(1)
  }
  
  /** Traverses array value by value
  * @version 1.0.0
  * @since 1.0.2
  */
  public void traverseArray() { // ------------------------------------------------------------------------ O(a*b)
    for (int row = 0;row < arr.length;row++) { // --------------------------------------------------------- O(a*b)
      for (int column = 0;column < arr[0].length;column++) { // ------------------------------------------- O(b)
        System.out.print(get(row, column) + " "); // ------------------------------------------------------ O(1)
      }
    }
    System.out.println(""); // ---------------------------------------------------------------------------- O(1)
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    TwoDimensionArray tda = new TwoDimensionArray(3,3);
    tda.insert(0,0,0);
    tda.insert(1,0,10);
    tda.insert(0,1,1);
    tda.insert(1,2,23);
    tda.insert(2,1,32);
    tda.insert(3,0,0);
    tda.insert(0,3,0);
  }
  public static void Test_Get() {
    TwoDimensionArray tda = new TwoDimensionArray(3,3);
    tda.insert(0,0,0);
    tda.insert(1,0,10);
    tda.insert(0,1,1);
    tda.insert(1,2,23);
    System.out.println(tda.get(0,0));
    System.out.println(tda.get(1,0));
    System.out.println(tda.get(0,1));
    System.out.println(tda.get(1,2));
  }
  public static void Test_TraverseArray() {
    TwoDimensionArray tda = new TwoDimensionArray(3,3);
    tda.insert(0,0,0);
    tda.insert(1,0,10);
    tda.insert(0,1,1);
    tda.insert(1,2,23);
    tda.traverseArray();
  }
  public static void Test_SearchInArray_Linear() {
    TwoDimensionArray tda = new TwoDimensionArray(3,3);
    tda.insert(0,0,1);
    tda.insert(1,0,10);
    tda.insert(0,1,1);
    tda.insert(1,2,23);
    tda.searchInArray_Linear(10);
    tda.searchInArray_Linear(23);
    tda.searchInArray_Linear(1);
    tda.searchInArray_Linear(2);
  }
}