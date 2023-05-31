package src.linkedlists;

/*------------------------------
* - Note from author:
*    I am well aware that this class is missing very basic functionality associated with such a type of object.
*    Things that immediately come to mind are push, pop, get, rotate, set, etc.
*    I only was following the functionality asked for by the course, and not adventuring further.
*    I have confidence I could implement those feature easily, but this is so basic and I'm only 25% through the
*    course after ~20 hours input, so I have a LOT of other structures and algorithms to learn about.
* ------------------------------
*/

/** Represents a circular doubly linked list implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class CircularDoublyLinkedList {
  public DLLNode head, tail;
  public int size;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public CircularDoublyLinkedList() { // -------------------------------------------------------------------------- O(1)
    size = 0; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public CircularDoublyLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    initializeLinkedList(nodeValue);
  }

  /** Deletes an index from a circular doubly linked list
  * @version 1.0.0
  * @since 1.0.0
  * @param index Index to be deleted
  */
  public void delete(int index) { // ------------------------------------------------------------------------ O(n)
    //Error handling
    if (index > size) {
      System.out.println("Linked List index " + index + " is out of bounds!");
      return;
    }
    if (index < 0) {
      System.out.println("Linked List index " + index + " must be a positive number!");
      return;
    }

    //Delete node
    DLLNode nodeToLeft = null, nodeToDelete = head, nodeToRight = null;
    if (index == 0) { // Is head
      head = nodeToDelete.next; //Pray garbage collection cleans old head
      head.prev = tail;
      tail.next = head;
    } else if (index == size-1) { // Is tail
      for (int i = 0;i < index;i++) { // ------------------------------------------------------------------- O(n)
        nodeToLeft = nodeToDelete;
        nodeToDelete = nodeToDelete.next;
      }
      tail = nodeToLeft;
      nodeToLeft.next = head;
      head.prev = tail;
    } else { // Is in middle
      for (int i = 0;i < index;i++) { // ------------------------------------------------------------------- O(n)
        nodeToLeft = nodeToDelete;
        nodeToDelete = nodeToDelete.next;
      }
      nodeToRight = nodeToDelete.next;
      nodeToLeft.next = nodeToRight; //Pray garbage collection cleans deleted node
      nodeToRight.prev = nodeToLeft;
    }
    size--;
  }

  /** Deletes entire circular doubly linked list
  * @version 1.0.0
  * @since 1.0.0
  */
  public void deleteEntireList() { // --------------------------------------------------------------------- O(1)
    head = null;
    tail = null;
    size = 0;
  }
  
  /** Generic initialization method
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public void initializeLinkedList(int nodeValue) { // ---------------------------------------------------- O(1)
    DLLNode node = new DLLNode(nodeValue, null, null); // ------------------------------------------------- O(1)
    head = node; // --------------------------------------------------------------------------------------- O(1)
    head.prev = node;
    head.next = node;
    tail = node; // --------------------------------------------------------------------------------------- O(1)
    tail.prev = node;
    tail.next = node;
    size = 1; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Insert a value into the linked list
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set the new node to
  * @param index Index at which value should be inserted, shifting current and following elements at index to right.
  */
  public void insert(int nodeValue, int index) { // ------------------------------------------------------- O(index) or O(n)
    //Error handling
    if (index > size) {
      System.out.println("Linked List index " + index + " is out of bounds!");
      return;
    }
    if (index < 0) {
      System.out.println("Linked List index " + index + " must be a positive number!");
      return;
    }

    //Insert node as head if no head
    if (size == 0) {
      initializeLinkedList(nodeValue); // ---------------------------------------------------------------- O(1)
      return;
    }
    
    //Insert node expecting already present nodes
    DLLNode node = new DLLNode(nodeValue, null, null);
    if (index == 0) { // Is head
      node.prev = tail;
      node.next = head;
      head.prev = node;
      tail.next = node;
      head = node;
    } else if (index == size) { // Is tail
      head.prev = node;
      tail.next = node;
      node.prev = tail;
      node.next = head;
      tail = node;
    } else { // Is in middle
      DLLNode nodeToLeft = null;
      DLLNode nodeAtIndex = head;
      for (int i = 0;i < index;i++) { // ----------------------------------------------------------------- O(index)
        nodeToLeft = nodeAtIndex;
        nodeAtIndex = nodeAtIndex.next;
      }
      nodeToLeft.next = node;
      node.prev = nodeToLeft;
      node.next = nodeAtIndex;
      nodeAtIndex.prev = node;
    }
    size++;
  }

  /** Deduce if a value is present anywhere in the circular doubly linked list
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set the new node to
  * @return Returns a boolean representing if the value is found in the list
  */
  public boolean isInList(int nodeValue) { // ------------------------------------------------------------- O(n)
    //Edge case
    if (size == 0)
      return false;

    DLLNode curNode = head;
    for (int i = 0;i < size;i++) { // -------------------------------------------------------------------------- O(n)
      if (curNode.value == nodeValue)
        return true;
      curNode = curNode.next;
    }
    return false;
  }
  
  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // -------------------------------------------------------------------------- O(n)
    String str = "Size: " + size + " :: ";
    DLLNode node = head;
    for (int i = 0;i < size;i++) { // -------------------------------------------------------------------- O(n)
      str += node.value + " ";
      node = node.next;
    }
    return str;
  }

  /** Deep toString method for more in depth debugging
  * @version 1.0.0
  * @since 1.0.0
  */
  public String deepToString() { // ---------------------------------------------------------------------- O(n)
    String str = "Size: " + size + "\n";
    DLLNode node = head;
    for (int i = 0;i < size;i++) { // -------------------------------------------------------------------- O(n)
      String prev = (node.prev != null) ? node.prev.value + "" : "null";
      String next = (node.next != null) ? node.next.value + "" : "null";
      str += prev + " <- " + node.value + " -> " + next + "\n";
      node = node.next;
    }
    str += "---";
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    CircularDoublyLinkedList lL = new CircularDoublyLinkedList(300);
    System.out.println(lL.deepToString());
    lL.insert(4,1);
    System.out.println(lL.deepToString());
    lL.insert(42,0);
    System.out.println(lL.deepToString());
    lL.insert(6,1);
    System.out.println(lL.deepToString());
  }
  public static void Test_IsInList() {
    CircularDoublyLinkedList lL = new CircularDoublyLinkedList(300);
    lL.insert(1,1);
    lL.insert(500,2);
    lL.insert(4,2);
    System.out.println(lL.isInList(42));
    lL.insert(42,0);
    System.out.println(lL.isInList(42));
  }
  public static void Test_Delete() {
    CircularDoublyLinkedList lL = new CircularDoublyLinkedList(0);
    lL.insert(1,1);
    lL.insert(2,2);
    lL.insert(3,3);
    lL.insert(4,4);
    System.out.println(lL.deepToString());
    lL.delete(0);
    System.out.println(lL.deepToString());
    lL.delete(lL.size-1);
    System.out.println(lL.deepToString());
    lL.delete(1);
    System.out.println(lL.deepToString());
    lL.delete(5);
    System.out.println(lL.deepToString());
  }
  public static void Test_DeleteEntireList() {
    CircularDoublyLinkedList lL = new CircularDoublyLinkedList(0);
    lL.insert(1,1);
    lL.insert(2,2);
    lL.insert(3,3);
    lL.insert(4,4);
    System.out.println(lL.deepToString());
    lL.deleteEntireList();
    System.out.println(lL.deepToString());
  }
}