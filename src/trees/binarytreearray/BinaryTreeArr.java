package src.trees.binarytreearray;

public class BinaryTreeArr<T> {
  Object[] arr;
  int lastUsedIndex;

  /** Initializes an array based binary tree with a specified max depth
  * @version 1.0.0
  * @since 1.0.0
  * @param maxDepth Static maximum tree depth (0 depth implies 1 layer aka root node only)
  */
  public BinaryTreeArr(int maxDepth) {
    arr = new Object[(int) Math.pow(2, maxDepth+1)];
    lastUsedIndex = 0;
  }

  public int getLeftChildIndex(int parentIndex) {
    return parentIndex*2;
  }
  public int getRightChildIndex(int parentIndex) {
    return parentIndex*2 + 1;
  }
  public int getParentIndex(int childIndex) {
    return childIndex/2 + childIndex%2;
  }

  public boolean isFull() {
    if (lastUsedIndex == arr.length-1)
      return true;
    return false;
  }

  public String toString() {
    String str = "";
    for (int i = 1;i < arr.length;i++) { // ------------------------------------------------------------------------ O(n)
      if (arr[i] == null)
        str += ("_ ");
      else
        str += (arr[i] + " ");
    }
    return str;
  }

// ----- Delete, Insert, Traversal, and Search Methods --------------------------------------------------------------------------

  //Delete Methods
  /** Deletes entire linked list tree
  * @version 1.0.0
  * @since 1.0.0
  */
  public void deleteTree() {
    arr = new Object[arr.length];
    lastUsedIndex = 0;
  }
  /** Deletes first occurrence of value by conducting a level search for the target value, swapping the last element value to the target element value, and then removing the last element from the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be deleted
  */
  public void levelDelete(T value) {
    levelDelete(1, value);
  }
  /** Deletes first occurrence of value by conducting a level search for the target value, swapping the last element value to the target element value, and then removing the last element from the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for search initialization
  * @param value Value to be deleted
  */
  public void levelDelete(int nodeIndex, T value) {
    /* Note 1: This delete method functionality is kinda odd to me and throws heirarchy out the window
    *  Essentially the instructor said to copy deepest node's value to the to-be-deleted node's location
    *  and then delete the deeper node. I don't see what structural utility atm that servers but whatever.
    */

    /* Note 2: This delete method's functionality ASSUMES the tree upholds the "complete binary tree" structure.
    *  Nowhere is that said in the instructor's video, and his repeated actions of continuously making trees by
    *  hand willy nilly suggest anything but. I only figured this out with independent critical thought and
    *  deduction. -1 points for the course's reputation.
    */

    int foundIndex = levelSearch(value);
    if (foundIndex == -1)
      return;
    arr[foundIndex] = arr[lastUsedIndex];
    arr[lastUsedIndex] = null;
    lastUsedIndex--;
  }
  
  //Insert Methods
  /** Inserts a value into the furthest node of the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param value Target Value
  */
  public void insert(T value) { // ---------------------------------------------------------------------------- O(1)
    if (isFull()) {
      System.out.println("Attempting to add " + value + " to a full BinaryTreeArray!");
      return;
    }

    arr[lastUsedIndex+1] = value;
    lastUsedIndex++;
  }

  //Search Methods
  /** Attempts to retrieve the first occurrence of a node with target value using level search philosophy
  * @version 1.0.0
  * @since 1.0.0
  * @param value Target Value
  */
  public int levelSearch(T value) {
    return levelSearch(1, value);
  }
  /** Attempts to retrieve the first occurrence of a node with target value using level search philosophy
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root for search initialization
  * @param value Target Value
  */
  public int levelSearch (int nodeIndex, T value) {
    for (int i = nodeIndex;i <= lastUsedIndex;i++) { // ------------------------------------------------------- O(n)
      if (arr[i] == value)
        return i;
    }
    return -1;
  }

  //Traversal Methods - These naming conventions are really stupid. I feel like LCR, LRC, CLR, and level are much more sensical.
  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void inorderTraversal() { // ---------------------------------------------------------------------- O(n)
    inorderTraversal(1);
  }
  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void inorderTraversal(int nodeIndex) { // ---------------------------------------------------------- O(n)
    if (nodeIndex > lastUsedIndex)
      return;

    inorderTraversal(getLeftChildIndex(nodeIndex)); // ------------------------------------------------------- O(n/2)
    Object value = arr[nodeIndex];
    System.out.print(((value == null) ? "_" : value) + " ");
    inorderTraversal(getRightChildIndex(nodeIndex)); // ----------------------------------------------------- O(n/2)
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void levelorderTraversal() { // -------------------------------------------------------------------- O(n)
    levelorderTraversal(1);
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void levelorderTraversal(int nodeIndex) { // ------------------------------------------------------- O(n)
    // Iterative over recursive all day let's GOOOOO
    for (int i = nodeIndex;i <= lastUsedIndex;i++) { // ------------------------------------------------------- O(n)
      Object value = arr[i];
      // System.out.println("value: " + value);
      System.out.print(((value == null) ? "_" : value) + " ");
    }
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void postorderTraversal() { // --------------------------------------------------------------------- O(n)
    postorderTraversal(1);
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void postorderTraversal(int nodeIndex) { // -------------------------------------------------------- O(n)
    if (nodeIndex > lastUsedIndex)
      return;

    postorderTraversal(getLeftChildIndex(nodeIndex)); // ---------------------------------------------------- O(n/2)
    postorderTraversal(getRightChildIndex(nodeIndex)); // --------------------------------------------------- O(n/2)
    Object value = arr[nodeIndex];
    System.out.print(((value == null) ? "_" : value) + " ");
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void preorderTraversal() { // ----------------------------------------------------------------------- O(n)
    preorderTraversal(1);
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void preorderTraversal(int nodeIndex) { // -------------------------------------------------------- O(n)
    if (nodeIndex > lastUsedIndex)
      return;

    Object value = arr[nodeIndex];
    System.out.print(((value == null) ? "_" : value) + " ");
    preorderTraversal(getLeftChildIndex(nodeIndex)); // ---------------------------------------------------- O(n/2)
    preorderTraversal(getRightChildIndex(nodeIndex)); // --------------------------------------------------- O(n/2)
  }




  
  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    BinaryTreeArr<Integer> t = new BinaryTreeArr(4);
    System.out.println(t);
    t.insert(1);
    t.insert(2);
    t.insert(3);
    t.insert(4);
    t.insert(5);
    t.insert(6);
    System.out.println(t);
  }
  public static void Test_Traversal() {
    BinaryTreeArr<Integer> t = new BinaryTreeArr(4);
    t.insert(1);
    t.insert(2);
    t.insert(3);
    t.insert(4);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    t.insert(8);
    t.insert(9);
    t.preorderTraversal();
    System.out.println("");
    t.inorderTraversal();
    System.out.println("");
    t.postorderTraversal();
    System.out.println("");
    t.levelorderTraversal();
    System.out.println("");
    //Successful output as expected (even though it looks random).
  }
  public static void Test_Search() {
    BinaryTreeArr<Integer> t = new BinaryTreeArr(4);
    t.insert(1);
    t.insert(2);
    t.insert(3);
    t.insert(4);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    t.insert(8);
    System.out.println("Contains 9: " + t.levelSearch(9));
    t.insert(9);
    System.out.println("Contains 9: " + t.levelSearch(9));
  }
  public static void Test_Delete() {
    BinaryTreeArr<Integer> t = new BinaryTreeArr(4);
    t.insert(1);
    t.insert(2);
    t.insert(3);
    t.insert(4);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    t.insert(8);
    t.insert(9);
    System.out.println(t);
    t.levelDelete(9);
    System.out.println(t);
  }
}