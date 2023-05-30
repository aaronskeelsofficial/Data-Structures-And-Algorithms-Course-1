package linkedlists;

/** Represents a singly linked list implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class SinglyLinkedList {
  public SLLNode head, tail;
  public int size;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public SinglyLinkedList() { // -------------------------------------------------------------------------- O(1)
    size = 0; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public SinglyLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    SLLNode node = new SLLNode(nodeValue, null); // ------------------------------------------------------- O(1)
    head = node; // --------------------------------------------------------------------------------------- O(1)
    tail = node; // --------------------------------------------------------------------------------------- O(1)
    size = 1; // ------------------------------------------------------------------------------------------ O(1)
  }
}