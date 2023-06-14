package src.trees.binaryheaparray;

public class BinaryHeapArr <T extends Comparable<T>> {
  public Object[] arr;
  private int lastUsedIndex;
  public BinaryHeapType type;

  public BinaryHeapArr(BinaryHeapType type) { // -------------------------------------------------------- O(1)
    this(4, type); // ------------------------------------------------------------------------------------------ O(1)
  }
  public BinaryHeapArr(int maxDepth, BinaryHeapType type) { // ------------------------------------------ O(1)
    arr = new Object[(int) Math.pow(2, maxDepth+1)];
    lastUsedIndex = 0;
    this.type = type;
  }

  public void deleteTree() {
    arr = new Object[arr.length];
  }

  public T extract() { // -------------------------------------------------------------------------------------- O(logn)
    // Step 1. Cache value to be returned
    T rootValue = (T) arr[1];
    // Step 2. Replace root with last value
    T lastValue = (T) arr[lastUsedIndex];
    arr[1] = lastValue;
    arr[lastUsedIndex] = null;
    lastUsedIndex--;
    // Step 3. Swap parent/child down through the chain to bottom leaf as much as necessary
    int parentIndex = 1;
    T parentValue = (T) arr[parentIndex];
    int lChildIndex = getLeftChildIndex(parentIndex); // ------------------------------------------------------- O(1)
    T lChildValue = (T) arr[lChildIndex];
    int rChildIndex = getRightChildIndex(parentIndex); // ------------------------------------------------------- O(1)
    T rChildValue = (T) arr[rChildIndex];
    int childIndex = 0;
    if (rChildValue == null ||
        (lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MINIMUM)) ||
        (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MAXIMUM)))
      childIndex = lChildIndex;
    else if (lChildValue == null ||
        (lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MAXIMUM)) ||
        (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MINIMUM)))
      childIndex = rChildIndex;
    T childValue = (T) arr[childIndex];
    int cacheIndex = 0;
    if (type.equals(BinaryHeapType.MINIMUM)) {
      // While not beyond bottom layer (can rotate) & should rotate, then do rotate
      while (childIndex < lastUsedIndex && childValue.compareTo(parentValue) < 0) { // ----------------------------- O(logn)
        arr[childIndex] = parentValue;
        arr[parentIndex] = childValue;
        lChildIndex = getLeftChildIndex(childIndex); // ------------------------------------------------------------ O(1)
        rChildIndex = getRightChildIndex(childIndex); // ----------------------------------------------------------- O(1)
        if ((lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MINIMUM)) ||
            (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MAXIMUM)))
          cacheIndex = lChildIndex;
        else if ((lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MAXIMUM)) ||
            (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MINIMUM)))
          cacheIndex = rChildIndex;
        parentIndex = childIndex;
        parentValue = (T) arr[parentIndex];
        childIndex = cacheIndex;
        childValue = (T) arr[childIndex];
      }
    } else if (type.equals(BinaryHeapType.MAXIMUM)) {
      // While not beyond bottom layer (can rotate) & should rotate, then do rotate
      while (childIndex < lastUsedIndex && childValue.compareTo(parentValue) > 0) { // ----------------------------- O(logn)
        arr[childIndex] = parentValue;
        arr[parentIndex] = childValue;
        lChildIndex = getLeftChildIndex(childIndex); // ------------------------------------------------------------ O(1)
        rChildIndex = getRightChildIndex(childIndex); // ----------------------------------------------------------- O(1)
        if ((lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MINIMUM)) ||
            (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MAXIMUM)))
          cacheIndex = lChildIndex;
        else if ((lChildValue.compareTo(rChildValue) < 0 && type.equals(BinaryHeapType.MAXIMUM)) ||
            (lChildValue.compareTo(rChildValue) > 0 && type.equals(BinaryHeapType.MINIMUM)))
          cacheIndex = rChildIndex;
        parentIndex = childIndex;
        parentValue = (T) arr[parentIndex];
        childIndex = cacheIndex;
        childValue = (T) arr[childIndex];
      }
    }
    return rootValue;
  }

  public int getLeftChildIndex(int parentIndex) { // ----------------------------------------------------------- O(1)
    return parentIndex*2;
  }
  public int getRightChildIndex(int parentIndex) { // ---------------------------------------------------------- O(1)
    return parentIndex*2 + 1;
  }
  public int getParentIndex(int childIndex) { // --------------------------------------------------------------- O(1)
    return childIndex/2 + childIndex%2;
  }
  
  public int getSize() { // ------------------------------------------------------------------------------------ O(1)
    return lastUsedIndex;
  }

  public void insert(T value) { // ----------------------------------------------------------------------------- O(logn)
    if (isFull()) { // ----------------------------------------------------------------------------------------- O(1)
      System.out.println("Attempted to add " + value + " to a full BinaryHeap!");
      return;
    }
    
    // Step 1. Place in first blank (lastUsedIndex+1)
    arr[lastUsedIndex+1] = value;
    lastUsedIndex++;
    // Step 2. Swap parent/child up through the chain to root as much as necessary
    int childIndex = lastUsedIndex;
    T childValue = value;
    int parentIndex = getParentIndex(childIndex); // ----------------------------------------------------------- O(1)
    T parentValue = (T) arr[parentIndex];
    int cacheIndex = 0;
    if (type.equals(BinaryHeapType.MINIMUM)) {
      // While not root (can rotate) & should rotate, then do rotate
      while (childIndex > 1 && childValue.compareTo(parentValue) < 0) { // ------------------------------------- O(logn)
        arr[childIndex] = parentValue;
        arr[parentIndex] = childValue;
        cacheIndex = getParentIndex(parentIndex); // ----------------------------------------------------------- O(1)
        childIndex = parentIndex;
        childValue = (T) arr[childIndex];
        parentIndex = cacheIndex;
        parentValue = (T) arr[parentIndex];
      }
    } else if (type.equals(BinaryHeapType.MAXIMUM)) {
      // While not root (can rotate) & should rotate, then do rotate
      while (childIndex > 1 && childValue.compareTo(parentValue) > 0) { // ------------------------------------- O(logn)
        arr[childIndex] = parentValue;
        arr[parentIndex] = childValue;
        cacheIndex = getParentIndex(parentIndex); // ----------------------------------------------------------- O(1)
        childIndex = parentIndex;
        childValue = (T) arr[childIndex];
        parentIndex = cacheIndex;
        parentValue = (T) arr[parentIndex];
      }
    }
  }

  public boolean isEmpty() { // -------------------------------------------------------------------------------- O(1)
    if (lastUsedIndex < 1)
      return true;
    return false;
  }
  public boolean isFull() { // --------------------------------------------------------------------------------- O(1)
    if (lastUsedIndex >= arr.length - 1)
      return true;
    return false;
  }

  public T peek() { // ----------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) // ------------------------------------------------------------------------------------------ O(1)
      return null;
    return (T) arr[1];
  }

  public String toString() {
    String str = "";
    for (int i = 1;i < arr.length;i++) {
      if (arr[i] == null)
        str += ("_ ");
      else
        str += (arr[i] + " ");
    }
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_InsertMin() {
    BinaryHeapArr<Integer> h = new BinaryHeapArr<>(4, BinaryHeapType.MINIMUM);
    Integer[] arr = {null, 5, 10, 20, 30, 40, 50, 60, null, null, null, null, null, null, null, null};
    h.arr = arr;
    h.lastUsedIndex = 7;
    System.out.println(h);
    h.insert(1);
    System.out.println(h);
    //Success
  }
  public static void Test_ExtractMin() {
    BinaryHeapArr<Integer> h = new BinaryHeapArr<>(4, BinaryHeapType.MINIMUM);
    Integer[] arr = {null, 5, 10, 20, 30, 40, 50, 60, 80, null, null, null, null, null, null, null};
    h.arr = arr;
    h.lastUsedIndex = 8;
    System.out.println(h);
    h.extract();
    System.out.println(h);
    //Success
  }
}