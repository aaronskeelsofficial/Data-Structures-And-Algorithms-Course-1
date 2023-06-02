package src.trees.binarytreelinkedlist;
import java.util.ArrayDeque;

/*------------------------------
* - Note from author:
*    I am sure that this class is missing very basic functionality associated with such a type of object.
*    I only was following the functionality asked for by the course, and not adventuring further. I did
*    code every function myself using independently arrived at logic (confirmed at least as efficient if
*    not more efficient than course material explanation). I also ventured into implementing generics
*    where applicable because that seemed cool.
* ------------------------------
*/

/** Represents a linked list binary tree structure
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class BinaryTreeLL {
  public BinaryNode root;

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public BinaryTreeLL() { // --------------------------------------------------------------------------------- O(1)
    this.root = null;
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------ O(n)
    if (root == null)
      return "_EMPTYTREE_";
    
    String str = "";
    BinaryNode curNode = root;
    ArrayDeque<BinaryNode> queue = new ArrayDeque<>();
    if (curNode != null)
      queue.add(curNode);
    int emergencyLoopCounter = 0;
    final int LOOPCUTOFF = 200;
    while (!queue.isEmpty() && emergencyLoopCounter++ < LOOPCUTOFF) { // ------------------------------------- O(n)
      curNode = queue.remove();
      System.out.println("  ".repeat(curNode.depth) + curNode.value + ":D" + curNode.depth
        + ":L" + ((curNode.left == null) ? "" : curNode.left.value) + " R" + ((curNode.right == null) ? "" : curNode.right.value));
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
    return str;
  }

// ----- Delete, Insert, Traversal, and Search Methods --------------------------------------------------------------------------

  //Delete Methods
  /** Deletes entire linked list tree
  * @version 1.0.0
  * @since 1.0.0
  */
  public void deleteTree() { // ------------------------------------------------------------------------------ O(1)
    root = null; // Pray garbage collector catches this
  }
  /** Deletes first occurrence of value by conducting a level search for the target value, a level search for the "last" element in the tree, swapping the last element value to the target element value, and then removing the last element from the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be deleted
  */
  public <T> void levelDelete(T value) { // ------------------------------------------------------------------ O(n)
    levelDelete(root, value); // ----------------------------------------------------------------------------- O(n)
  }
  /** Deletes first occurrence of value by conducting a level search for the target value, a level search for the "last" element in the tree, swapping the last element value to the target element value, and then removing the last element from the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for search initialization
  * @param value Value to be deleted
  */
  public <T> void levelDelete(BinaryNode<T> node, T value) { // ---------------------------------------------- O(n)
    /* Note 1: This delete method functionality is kinda odd to me and throws heirarchy out the window
    *  Essentially the instructor said to copy deepest node's value to the to-be-deleted node's location
    *  and then delete the deeper node. I don't see what structural utility atm that servers but whatever.
    */

    /* Note 2: This delete method's functionality ASSUMES the tree upholds the "complete binary tree" structure.
    *  Nowhere is that said in the instructor's video, and his repeated actions of continuously making trees by
    *  hand willy nilly suggest anything but. I only figured this out with independent critical thought and
    *  deduction. -1 points for the course's reputation.
    */

    /* Note 3: This method is entirely coded by me because the instructor's implementation loops through all
    *  elements twice, whereas this only does a single passover and combines all steps into that single pass
    */
    
    //Root check
    if (node == null) {
      return;
    }
    //Normal handling
    BinaryNode<T> curNode = null, prevNode = null;
    ArrayDeque<BinaryNode<T>> queue = new ArrayDeque<>();
    queue.add(node);
    int emergencyLoopCounter = 0;
    final int LOOPCUTOFF = 200;
    BinaryNode<T> foundNode = null;
    while (!queue.isEmpty() && emergencyLoopCounter++ < LOOPCUTOFF) { // ------------------------------------- O(n)
      prevNode = curNode;
      curNode = queue.remove();
      if (curNode.value.equals(value))
        foundNode = curNode;
      
      // If we reach the end of the tree
      if (curNode.left == null) {
        if (foundNode != null) { // If we have reached the end and the target value was found, begin swap & delete
          foundNode.value = prevNode.right.value;
          prevNode.right = null;
        }
        return;
      }
      if (curNode.right == null) {
        if (foundNode != null) { // If we have reached the end and the target value was found, begin swap & delete
          foundNode.value = curNode.left.value;
          curNode.left = null;
        }
        return;
      }
      
      queue.add(curNode.left);
      queue.add(curNode.right);
    }
    return;
  }
  
  //Insert Methods
  /** Inserts value into tree using "complete binary tree" philosophy (top-down, left-to-right level methodology)
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be deleted
  */
  public <T> void levelInsert(T value) { // ------------------------------------------------------------------ O(n)
    levelInsert(root, value); // ----------------------------------------------------------------------------- O(n)
  }
  /** Inserts value into tree using "complete binary tree" philosophy (top-down, left-to-right level methodology)
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for search initialization
  * @param value Value to be deleted
  */
  public <T> void levelInsert(BinaryNode<T> node, T value) { // ---------------------------------------------- O(n)
    BinaryNode<T> newNode = new BinaryNode(value);
    //Root check
    if (node == null) {
      root = newNode;
      return;
    }
    //Normal handling
    BinaryNode<T> curNode = node;
    ArrayDeque<BinaryNode<T>> queue = new ArrayDeque<>();
    queue.add(curNode);
    int emergencyLoopCounter = 0;
    final int LOOPCUTOFF = 200;
    while (!queue.isEmpty() && emergencyLoopCounter++ < LOOPCUTOFF) { // ------------------------------------- O(n)
      curNode = queue.remove();
      if (curNode.left == null) {
        curNode.left = newNode;
        newNode.depth = curNode.depth+1;
        return;
      }
      if (curNode.right == null) {
        curNode.right = newNode;
        newNode.depth = curNode.depth+1;
        return;
      }
      queue.add(curNode.left);
      queue.add(curNode.right);
    }
    return;
  }
  
  //Traversal Methods - These naming conventions are really stupid. I feel like LCR, LRC, CLR, and level are much more sensical.
  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void inorderTraversal() { // ------------------------------------------------------------------------ O(n)
    inorderTraversal(root);
  }
  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void inorderTraversal(BinaryNode node) { // --------------------------------------------------------- O(n)
    if (node == null)
      return;

    inorderTraversal(node.left); // -------------------------------------------------------------------------- O(n/2)
    System.out.print(node.value + " ");
    inorderTraversal(node.right); // ------------------------------------------------------------------------- O(n/2)
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void levelorderTraversal() { // --------------------------------------------------------------------- O(n)
    levelorderTraversal(root);
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void levelorderTraversal(BinaryNode node) { // ------------------------------------------------------ O(n)
    //Finally iterative design. I hate recursion. It's dirty.
    BinaryNode curNode = node;
    ArrayDeque<BinaryNode> queue = new ArrayDeque<>();
    queue.add(curNode);
    while (!queue.isEmpty()) { // ---------------------------------------------------------------------------- O(n)
      curNode = queue.remove();
      System.out.print(curNode.value + " ");
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void postorderTraversal() { // ----------------------------------------------------------------------- O(n)
    postorderTraversal(root);
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void postorderTraversal(BinaryNode node) { // -------------------------------------------------------- O(n)
    if (node == null)
      return;

    postorderTraversal(node.left); // ------------------------------------------------------------------------- O(n/2)
    postorderTraversal(node.right); // ------------------------------------------------------------------------ O(n/2)
    System.out.print(node.value + " ");
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void preorderTraversal() { // ----------------------------------------------------------------------- O(n)
    preorderTraversal(root);
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void preorderTraversal(BinaryNode node) { // -------------------------------------------------------- O(n)
    if (node == null)
      return;

    System.out.print(node.value + " ");
    preorderTraversal(node.left); // ------------------------------------------------------------------------- O(n/2)
    preorderTraversal(node.right); // ------------------------------------------------------------------------ O(n/2)
  }

  //Search Methods
  /** Attempts to retrieve the first occurrence of a node with target value using level search philosophy
  * @version 1.0.0
  * @since 1.0.0
  * @param value Target Value
  */
  public BinaryNode levelSearch(Object value) { // ----------------------------------------------------------- O(n)
    return levelSearch(root, value);
  }
  /** Attempts to retrieve the first occurrence of a node with target value using level search philosophy
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root for search initialization
  * @param value Target Value
  */
  public <T> BinaryNode<T> levelSearch(BinaryNode<T> node, T value) { // ------------------------------------- O(n)
    //Root check
    if (node == null) {
      return null;
    }
    //Normal handling
    BinaryNode<T> curNode = node;
    ArrayDeque<BinaryNode<T>> queue = new ArrayDeque<>();
    queue.add(curNode);
    while (!queue.isEmpty()) { // ---------------------------------------------------------------------------- O(n)
      curNode = queue.remove();
      if (curNode.value.equals(value))
        return curNode;
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
    return null;
  }
  




  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Traversal() {
    BinaryTreeLL t = new BinaryTreeLL();
    BinaryNode n8 = new BinaryNode("N8");
    BinaryNode n9 = new BinaryNode("N9");
    BinaryNode n4 = new BinaryNode("N4", n8, n9);
    BinaryNode n5 = new BinaryNode("N5");
    BinaryNode n2 = new BinaryNode("N2", n4, n5);
    BinaryNode n6 = new BinaryNode("N6");
    BinaryNode n7 = new BinaryNode("N7");
    BinaryNode n3 = new BinaryNode("N3", n6, n7);
    BinaryNode n1 = new BinaryNode("N1", n2, n3);
    t.root = n1;
    t.preorderTraversal(t.root);
    System.out.println("");
    t.inorderTraversal(t.root);
    System.out.println("");
    t.postorderTraversal(t.root);
    System.out.println("");
    t.levelorderTraversal(t.root);
    System.out.println("");
    //Outputs looks random but are correct
  }
  public static void Test_Search() {
    BinaryTreeLL t = new BinaryTreeLL();
    BinaryNode<String> n8 = new BinaryNode("N8");
    BinaryNode<String> n9 = new BinaryNode("N9");
    BinaryNode<String> n4 = new BinaryNode("N4", n8, n9);
    BinaryNode<String> n5 = new BinaryNode("N5");
    BinaryNode<String> n2 = new BinaryNode("N2", n4, n5);
    BinaryNode<String> n6 = new BinaryNode("N6");
    BinaryNode<String> n7 = new BinaryNode("N7");
    BinaryNode<String> n3 = new BinaryNode("N3", n6, n7);
    BinaryNode<String> n1 = new BinaryNode("N1", n2, n3);
    t.root = n1;
    System.out.println((t.levelSearch("N9") == null) ? "N9 was not found" : "N9 was found");
    System.out.println((t.levelSearch("N10") == null) ? "N10 was not found" : "N10 was found");
  }
  public static void Test_Insert() {
    BinaryTreeLL t = new BinaryTreeLL();
    BinaryNode<Integer> n8 = new BinaryNode(8);
    BinaryNode<Integer> n9 = new BinaryNode(9);
    BinaryNode<Integer> n4 = new BinaryNode(4, n8, n9);
    BinaryNode<Integer> n5 = new BinaryNode(5);
    BinaryNode<Integer> n2 = new BinaryNode(2, n4, n5);
    BinaryNode<Integer> n6 = new BinaryNode(6);
    BinaryNode<Integer> n7 = new BinaryNode(7);
    BinaryNode<Integer> n3 = new BinaryNode(3, n6, n7);
    BinaryNode<Integer> n1 = new BinaryNode(1, n2, n3);
    t.root = n1;
    t.levelorderTraversal(t.root);
    System.out.println("");
    t.levelInsert(10);
    t.levelorderTraversal(t.root);
    System.out.println("");
  }
  public static void Test_ToString() {
    BinaryTreeLL t = new BinaryTreeLL();
    BinaryNode<Integer> n8 = new BinaryNode(8);
    BinaryNode<Integer> n9 = new BinaryNode(9);
    BinaryNode<Integer> n4 = new BinaryNode(4, n8, n9);
    BinaryNode<Integer> n5 = new BinaryNode(5);
    BinaryNode<Integer> n2 = new BinaryNode(2, n4, n5);
    BinaryNode<Integer> n6 = new BinaryNode(6);
    BinaryNode<Integer> n7 = new BinaryNode(7);
    BinaryNode<Integer> n3 = new BinaryNode(3, n6, n7);
    BinaryNode<Integer> n1 = new BinaryNode(1, n2, n3);
    t.root = n1;
    System.out.println(t);
    t.levelInsert(10);
    System.out.println(t);
    t.levelInsert(11);
    t.levelInsert(12);
    t.levelInsert(13);
    t.levelInsert(14);
    t.levelInsert(15);
    t.levelInsert(16);
    System.out.println(t);
    //Everything attaches where it should. Success.
  }
  public static void Test_Delete() {
    BinaryTreeLL t = new BinaryTreeLL();
    BinaryNode<Integer> n8 = new BinaryNode(8);
    BinaryNode<Integer> n9 = new BinaryNode(9);
    BinaryNode<Integer> n4 = new BinaryNode(4, n8, n9);
    BinaryNode<Integer> n5 = new BinaryNode(5);
    BinaryNode<Integer> n2 = new BinaryNode(2, n4, n5);
    BinaryNode<Integer> n6 = new BinaryNode(6);
    BinaryNode<Integer> n7 = new BinaryNode(7);
    BinaryNode<Integer> n3 = new BinaryNode(3, n6, n7);
    BinaryNode<Integer> n1 = new BinaryNode(1, n2, n3);
    t.root = n1;
    System.out.println(t);
    t.levelDelete(3);
    System.out.println(t);
    //Success. 9, the last element, was moved to 3's location, and 3 was deleted.
    t.deleteTree();
    System.out.println(t);
  }
}