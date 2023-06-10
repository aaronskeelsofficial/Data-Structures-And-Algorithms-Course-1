package src.trees.binarysearchtreelinkedlist;
import java.util.ArrayDeque;

/*------------------------------
* - Note from author:
*    Course instructor says to use recursion for methods, but I hate recursion, so I'm *not doing that* for a few.
*    Also he keeps programming a "height" variable into his node classes but not a "depth" variable, and
*    then he never does anything with his useless never-initialized height variable. Sooo he can't be trusted 🕵️‍♂️
*
*   Another important note:
*    The instructor has made such an absolutely crucial and fundamental Java mistake so bad that it's embarrassing
*    while working on this file. An explanation of the horrible error, what he attempted, and why he's so utterly
*    wrong can be found at the bottom of the file in the `Test_JavaSyntax()` method. When I saw what he was teaching
*    26k students I almost threw up. This isn't hyperbole or a joke, the instructor is straight up just teaching
*    Java factually incorrect in such an important way.
* ------------------------------
*/

public class BSTLL<T extends Comparable<T>> {
  BSTNode<T> root;
  
  public BSTLL() { // ------------------------------------------------------------------------------ O(1)
    root = null;
  }
  public BSTLL(T value) { // ----------------------------------------------------------------------- O(1)
    BSTNode<T> node = new BSTNode<T>(value);
    root = node;
  }

  /** Deletes the first occurrence (min depth) of a value from the BST using BST replacement logic to fill the missing node
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to delete
  */
  public void delete(T value) { // --------------------------------------------------------------------- O(n)
    if (root == null)
      return;
    
    // First we must find the node, if it exists
    BSTNode<T> foundNode = null;
    BSTNode<T> prevNode = null;
    BSTNode<T> curNode = root;
    while (curNode != null) { // ----------------------------------------------------------------------- O(n)
      if (value.compareTo(curNode.value) == 0) {
        foundNode = curNode;
        curNode = null;
        break;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left != null) {
        prevNode = curNode;
        curNode = curNode.left;
        continue;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left == null) {
        curNode = null;
        break;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) {
        prevNode = curNode;
        curNode = curNode.right;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) {
        curNode = null;
        break;
      }
    }
    if (foundNode == null)
      return;
    // We have found the node. Now we operate on it.
    //Case 1: Found node has 0 children
      if (foundNode.left == null && foundNode.right == null) {
        if (prevNode.left == foundNode)
          prevNode.left = null; // Pray for garbage collector 🙏
        else if (prevNode.right == foundNode)
          prevNode.right = null; // Pray for garbage collector 🙏
        return;
      }
    //Case 2: Found node has 1 child
      if ((foundNode.right == null && foundNode.left != null) &&
            (prevNode.left == foundNode)) { // Move found node left child to take spot as previous node's left child
        prevNode.left = foundNode.left; // Pray for garbage collector 🙏
        return;
      } else if ((foundNode.right == null && foundNode.left != null) &&
            (prevNode.right == foundNode)) { // Move found node left child to take spot as previous node's right child
        prevNode.right = foundNode.left; // Pray for garbage collector 🙏
        return;
      } else if ((foundNode.left == null && foundNode.right != null) &&
            (prevNode.left == foundNode)) { // Move found node right child to take spot as previous node's left child
        prevNode.left = foundNode.right; // Pray for garbage collector 🙏
        return;
      } else if ((foundNode.left == null && foundNode.right != null) &&
            (prevNode.right == foundNode)) { // Move found node right child to take spot as previous node's right child
        prevNode.right = foundNode.right; // Pray for garbage collector 🙏
        return;
      }
    //Case 3: Found node has 2 children
    if (foundNode.left != null && foundNode.right != null) {
      //Step 1: Find leftmost node to the found node's right branch
      BSTNode<T> prevLeftmostNode = null;
      BSTNode<T> leftmostNode = foundNode;
      while (leftmostNode != null) { // ----------------------------------------------------------------------- O(n)
        if (leftmostNode.left != null) {
          prevLeftmostNode = leftmostNode;
          leftmostNode = leftmostNode.left;
        } else
          break;
      }
      //Case 3.1: Leftmost node has 0 children (to the right)
      if (leftmostNode.right == null) {
        prevLeftmostNode.left = null; // Pray for garbage collector 🙏
        return;
      }
      //Case 3.2: Leftmost node has 1 child to the right (it can't have a child to the left because it's leftmost)
      if (leftmostNode.right != null) {
        prevLeftmostNode.left = leftmostNode.right; // Pray for garbage collector 🙏
        return;
      }
      //Set leftmostNode into foundNode spot
      if (prevNode.left == foundNode)
        prevNode.left = leftmostNode;
      else if (prevNode.right == foundNode)
        prevNode.right = leftmostNode;
      leftmostNode.left = foundNode.left;
      leftmostNode.right = foundNode.right;
    }
  }

  /** Deletes the entire binary search tree
  * @version 1.0.0
  * @since 1.0.0
  */
  public void deleteTree() { // ----------------------------------------------------------------------- O(1)
    if (root == null)
      return;
    root = null;
  }

  /** Inserts a value into the tree using binary search tree ("<=" means left, ">" means right) logic
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to insert as a new node into the tree
  * @return Returns a reference to the inserted node
  */
  public BSTNode<T> insert(T value) { // -------------------------------------------------------------- O(n)
    if (root == null) {
      BSTNode<T> node = new BSTNode<T>(value, null, null, 0);
      root = node;
      return root;
    }

    BSTNode<T> curNode = root;
    while(curNode != null) { // ----------------------------------------------------------------------- O(n)
      if (value.compareTo(curNode.value) <= 0 && curNode.left != null) { // If should place to left and another child present
        curNode = curNode.left;
        continue;
      } else if (value.compareTo(curNode.value) <= 0 && curNode.left == null) { // If should place to left and not another child present
        BSTNode<T> newNode = new BSTNode<T>(value, null, null, curNode.depth+1);
        curNode.left = newNode;
        return newNode;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) { // If should place to right and another child present
        curNode = curNode.right;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) { // If should place to right and not another child present
        BSTNode<T> newNode = new BSTNode<T>(value, null, null, curNode.depth+1);
        curNode.right = newNode;
        return newNode;
      }
    }
    return null;
  }

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
  public void inorderTraversal(BSTNode<T> node) { // --------------------------------------------------------- O(n)
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
  public void levelorderTraversal(BSTNode<T> node) { // ------------------------------------------------------ O(n)
    //Finally iterative design. I hate recursion. It's dirty.
    BSTNode<T> curNode = node;
    ArrayDeque<BSTNode<T>> queue = new ArrayDeque<>();
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
  public void postorderTraversal(BSTNode<T> node) { // -------------------------------------------------------- O(n)
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
  public void preorderTraversal(BSTNode<T> node) { // -------------------------------------------------------- O(n)
    if (node == null)
      return;

    System.out.print(node.value + " ");
    preorderTraversal(node.left); // ------------------------------------------------------------------------- O(n/2)
    preorderTraversal(node.right); // ------------------------------------------------------------------------ O(n/2)
  }

  /** Searches for the first occurrence of a value (minimum depth) if it exists within the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to search for
  * @return Returns a reference to the node with the target value
  */
  public BSTNode<T> search(T value) { // ------------------------------------------------------------------- O(n)
    if (root == null)
      return null;

    BSTNode<T> curNode = root;
    //Death to recursion! It is for apes!
    while (curNode != null) { // --------------------------------------------------------------------------- O(n)
      if (value.compareTo(curNode.value) == 0) {
        return curNode;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left != null) {
        curNode = curNode.left;
        continue;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left == null) {
        return null;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) {
        curNode = curNode.right;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) {
        return null;
      }
    }
    return null;
  }
  
  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() {
    if (root == null)
      return "";
    
    String str = "";
    BSTNode<T> curNode = root;
    ArrayDeque<BSTNode<T>> queue = new ArrayDeque<>();
    queue.add(curNode);
    int emergencyLoopCounter = 0;
    final int LOOPCUTOFF = 200;
    while (!queue.isEmpty() && emergencyLoopCounter++ < LOOPCUTOFF) {
      curNode = queue.remove();
      str += curNode.toString() + "\n";
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    BSTLL<Integer> t = new BSTLL<>();
    t.insert(10);
    System.out.println(t);
    t.insert(11);
    t.insert(9);
    System.out.println(t);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    System.out.println(t);
    //Integer works. I implemented generics but haven't tried them yet... let's?
    BSTLL<Double> t2 = new BSTLL<>();
    t2.insert(10.0);
    t2.insert(9.5);
    t2.insert(9.7);
    System.out.println(t2);
  }
  public static void Test_Search() {
    BSTLL<Integer> t = new BSTLL<>();
    System.out.println("Search returns: " + (t.search(10) == null ? "NOT_FOUND" : "FOUND"));
    System.out.println("Adding numbers to tree now...");
    t.insert(10);
    t.insert(11);
    t.insert(9);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    System.out.println("Search returns: " + (t.search(10) == null ? "NOT_FOUND" : "FOUND"));
  }
  public static void Test_JavaSyntax() {
    /* Note: In my course, the instructor handles deletion of the tree by doing things like
    *  `node = node.left` in an effort to do something akin to the following where
    *  "node" refers to the node in the 12's spot in the original tree:
    *
    *     10                           10
    *       \                            \
    *        \                            \
    *         12         ->                11
    *        /
    *      11
    *
    *  I've seen this instructor make other very blatant mistakes in his code about 5 times now
    *  and I'm under the impression this is not how Java actually works, so I'm testing it myself.
    */
    BSTLL<Integer> t = new BSTLL<>();
    BSTNode<Integer> nodeLoc1 = t.insert(10);
    BSTNode<Integer> nodeLoc2 = t.insert(12);
    BSTNode<Integer> nodeLoc3 = t.insert(11);
    System.out.println("Original Tree Lineage");
    System.out.println(t);
    System.out.println("Original Variable References");
    System.out.println(nodeLoc1);
    System.out.println(nodeLoc2);
    System.out.println(nodeLoc3);
    System.out.println("\n");
    // Now we test "nodeLoc2 = nodeLoc2.left" to see if that actually changes the *ROOT*'s reference to the second node position
    // because I don't believe it will. All it does is change what the variable nodeLoc2 points to, but it doesn't change the
    // actual tree lineage.
    nodeLoc2 = nodeLoc2.left;
    System.out.println("'Modified' Tree Lineage");
    System.out.println(t);
    System.out.println("'Modified' Variable References");
    System.out.println(nodeLoc1);
    System.out.println(nodeLoc2);
    System.out.println(nodeLoc3);
    // Update: I am right. This is straight up embarrassing that this is still being taught in the course to 26k students
  }
}