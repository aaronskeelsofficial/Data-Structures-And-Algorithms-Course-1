package src.trees.binarytreelinkedlist;

/*------------------------------
* - Note from author:
*    Beginning to dabble in generics here.
* ------------------------------
*/

/** Represents a linked list binary tree node structure
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class BinaryNode<T> {
  public T value;
  public BinaryNode<T> left, right;
  public int depth;

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public BinaryNode(){
  }
  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be stored in current basic tree node
  */
  public BinaryNode(T value) { // ------------------------------------------------------------------------------ O(1)
    this(value, null, null, 0); // ---------------------------------------------------------------------- O(1)
  }
  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be stored in current basic tree node
  * @param left Leftward child node reference
  * @param right Rightward child node reference
  */
  public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right) { // ------------------------------------- O(1)
    this(value, left, right, 0);
  }
  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be stored in current basic tree node
  * @param left Leftward child node reference
  * @param right Rightward child node reference
  * @param depth Current depth
  */
  public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right, int depth) { // ------------------------------------- O(1)
    this.value = value;
    if (left != null) {
      this.left = left;
      left.increaseDepth();
    }
    if (right != null) {
      this.right = right;
      right.increaseDepth();
    }
    this.depth = depth;
  }

  /** General means to increase depth
  * @version 1.0.0
  * @since 1.0.0
  */
  public void increaseDepth() {
    increaseDepth(false);
  }
  /** General means to increase depth
  * @version 1.0.0
  * @since 1.0.0
  * @param ignoreChildren Whether or not to recursively increase depth down the hierarchical chain
  */
  public void increaseDepth(boolean ignoreChildren) {
    depth++;
    if (!ignoreChildren) {
      if (left != null)
        left.increaseDepth();
      if (right != null)
        right.increaseDepth();
    }
  }
}