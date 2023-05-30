package arrays;

/** Represents a single dimensional array and all functional implementations
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.1
*/
public class SingleDimensionArray { // ------------------------------------------------------------------- O(n)
  int[] arr = null; // Declaration ----------------------------------------------------------------------- O(1)

  /** Creates a SingleDimensionArray with a static specified size
  * @param sizeOfArray Target size of array
  * @version 1.0.0
  * @since 1.0.0
  */
  public SingleDimensionArray(int sizeOfArray) {
    // Technically instantiation sets all values to 0 which I see as O(n) but it's allegedly O(1)
    arr = new int[sizeOfArray]; // Instantiation --------------------------------------------------------- O(1)
    for (int i = 0; i < sizeOfArray; i++) { // Initialization -------------------------------------------- O(n)
      arr[i] = Integer.MIN_VALUE; // --------------------------------------------------------------------- O(1)
    }
  }

  /** Gets value at a specified index
  * @param index Target index to get
  * @version 1.0.1
  * @since 1.0.1
  * @return Value at specified index
  */
  public int get(int index) { // ------------------------------------------------------------------------- O(1)
    try { // --------------------------------------------------------------------------------------------- O(1)
      System.out.println("Getting value at index: " + index + "."); // ----------------------------------- O(1)
      return arr[index]; // ------------------------------------------------------------------------------ O(1)
    } catch (ArrayIndexOutOfBoundsException e) { // ------------------------------------------------------ O(1)
      System.out.println("Invalid array index: " + index + "."); // -------------------------------------- O(1)
    }
    return 0; // ----------------------------------------------------------------------------------------- O(1)
  }
  
  /** Attempts to insert a value into a specified index
  * @param index Target index to attempt insertion at
  * @param valueToBeInserted Value to attempt to insert
  * @version 1.0.0
  * @since 1.0.0
  */
  public void insert(int index, int valueToBeInserted) {
    try {
      if (arr[index] == Integer.MIN_VALUE) { // ----------------------------------------------------------- O(1)
        arr[index] = valueToBeInserted; // ---------------------------------------------------------------- O(1)
        System.out.println("Successfully inserted."); // -------------------------------------------------- O(1)
      } else { // ----------------------------------------------------------------------------------------- O(1)
        System.out.println("Index " + index + " is already occupied!"); // -------------------------------- O(1)
      }
    } catch (ArrayIndexOutOfBoundsException e) { // ------------------------------------------------------- O(1)
      System.out.println("Invalid array index: " + index + "."); // --------------------------------------- O(1)
    }
  }





/* --------------------------------------------------
*  - TEST METHODS
*  - Purpose: All following methods were used to test functionality implementations.
*     They will have no documentation. Probably ignore.
*  --------------------------------------------------
*/
  public static void Test_Insert() {
    SingleDimensionArray sda = new SingleDimensionArray(10);
    sda.insert(0,0);
    sda.insert(1,10);
    sda.insert(2,20);
    sda.insert(3,30);
    sda.insert(1,40);
    sda.insert(12,120);
  }

  public static void Test_Get() {
    SingleDimensionArray sda = new SingleDimensionArray(10);
    sda.insert(0, 49);
    sda.get(0);
    sda.get(1);
    sda.get(12);
  }
}