package arrays;

/** Represents a single dimensional array and all functional implementations
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.3
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
  * @version 1.0.2
  * @since 1.0.1
  * @return Value at specified index
  */
  public int get(int index) { // ------------------------------------------------------------------------- O(1)
    try { // --------------------------------------------------------------------------------------------- O(1)
      // System.out.println("Getting value at index: " + index + "."); // ----------------------------------- O(1)
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

  /** Traverses array value by value
  * @version 1.0.0
  * @since 1.0.2
  */
  public void traverseArray() { // ------------------------------------------------------------------------- O(n)
    for (int i = 0;i < arr.length;i++) { // ---------------------------------------------------------------- O(n)
      System.out.print(get(i) + " "); // ------------------------------------------------------------------- O(1)
    }
    System.out.println(""); // ----------------------------------------------------------------------------- O(1)
  }

  /** Conduct a linear search for a value within the array
  * @param valueToSearch The value to search for within the array
  * @return Whether or not value is found within the array
  * @version 1.0.1
  * @since 1.0.3
  */
  public boolean searchInArray_Linear(int valueToSearch) { // ---------------------------------------------- O(n)
    for (int i = 0;i < arr.length;i++) { // ---------------------------------------------------------------- O(n)
      if (get(i) == valueToSearch) { // -------------------------------------------------------------------- O(1)
        System.out.println(valueToSearch + " was found in array @ index " + i + "."); // ------------------- O(1)
        return true; // ------------------------------------------------------------------------------------ O(1)
      }
    }
    System.out.println(valueToSearch + " was not found in array."); // ------------------------------------- O(1)
    return false; // --------------------------------------------------------------------------------------- O(1)
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
  public static void Test_TraverseArray() {
    SingleDimensionArray sda = new SingleDimensionArray(10);
    sda.insert(0,0);
    sda.insert(1,10);
    sda.insert(2,20);
    sda.insert(3,30);
    sda.traverseArray();
  }
  public static void Test_SearchInArray_Linear() {
    SingleDimensionArray sda = new SingleDimensionArray(10);
    sda.insert(0,0);
    sda.insert(1,10);
    sda.insert(2,20);
    sda.insert(3,30);
    sda.searchInArray_Linear(20);
    sda.searchInArray_Linear(40);
  }
}