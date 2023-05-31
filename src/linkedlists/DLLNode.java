package src.linkedlists;

/** Represents a node utilized by a doubly linked list
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class DLLNode {
  public int value;
  public DLLNode prev, next;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public DLLNode() { // ------------------------------------------------------------------------------ O(1)
  }

  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to put into this node
  * @param next Reference to next node. Null if tail.
  */
  public DLLNode(int value, DLLNode prev, DLLNode next) { // ------------------------------------------------------- O(1)
    this.value = value; // --------------------------------------------------------------------------- O(1)
    this.next = next; // ----------------------------------------------------------------------------- O(1)
  }
}