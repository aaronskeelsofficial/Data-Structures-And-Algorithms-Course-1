package linkedlists;

/** Represents a doubly linked list implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class DoublyLinkedList {
  public DLLNode head, tail;
  public int size;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public DoublyLinkedList() { // -------------------------------------------------------------------------- O(1)
    size = 0; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public DoublyLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    initializeLinkedList(nodeValue);
  }

  /** Deletes an index from a doubly linked list
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
      head.prev = null;
    } else if (index == size-1) { // Is tail
      for (int i = 0;i < index;i++) { // ------------------------------------------------------------------- O(n)
        nodeToLeft = nodeToDelete;
        nodeToDelete = nodeToDelete.next;
      }
      tail = nodeToLeft;
      nodeToLeft.next = null;
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

  /** Deletes entire doubly linked list
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
    tail = node; // --------------------------------------------------------------------------------------- O(1)
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
      node.next = head;
      head.prev = node;
      head = node;
    } else if (index == size) { // Is tail
      tail.next = node;
      node.prev = tail;
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

  /** Deduce if a value is present anywhere in the doubly linked list
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
    while (curNode != null) { // -------------------------------------------------------------------------- O(n)
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
    DoublyLinkedList lL = new DoublyLinkedList(300);
    System.out.println(lL.deepToString());
    lL.insert(4,1);
    System.out.println(lL.deepToString());
    lL.insert(42,0);
    System.out.println(lL.deepToString());
    lL.insert(6,1);
    System.out.println(lL.deepToString());
  }
  public static void Test_IsInList() {
    DoublyLinkedList lL = new DoublyLinkedList(300);
    lL.insert(1,1);
    lL.insert(500,2);
    lL.insert(4,2);
    System.out.println(lL.isInList(42));
    lL.insert(42,0);
    System.out.println(lL.isInList(42));
  }
  public static void Test_Delete() {
    DoublyLinkedList lL = new DoublyLinkedList(0);
    lL.insert(1,1);
    lL.insert(2,2);
    lL.insert(3,3);
    lL.insert(4,4);
    System.out.println(lL.deepToString());
    lL.delete(0);
    lL.delete(lL.size-1);
    System.out.println(lL.deepToString());
    lL.delete(1);
    lL.delete(5);
    System.out.println(lL.deepToString());
  }
  public static void Test_DeleteEntireList() {
    DoublyLinkedList lL = new DoublyLinkedList(0);
    lL.insert(1,1);
    lL.insert(2,2);
    lL.insert(3,3);
    lL.insert(4,4);
    System.out.println(lL.deepToString());
    lL.deleteEntireList();
    System.out.println(lL.deepToString());
  }
}