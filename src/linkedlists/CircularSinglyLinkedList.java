package linkedlists;

/** Represents a singly linked list implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class CircularSinglyLinkedList {
  public SLLNode head, tail;
  public int size;
  
  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public CircularSinglyLinkedList() { // ------------------------------------------------------------------ O(1)
    size = 0; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public CircularSinglyLinkedList(int nodeValue) { // ----------------------------------------------------- O(1)
    initializeLinkedList(nodeValue);
  }

  /** Deletes an index from a circular singly linked list
  * @version 1.0.0
  * @since 1.0.0
  * @param index Index to be deleted
  */
  public void delete(int index) { // ---------------------------------------------------------------------- O(n)
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
    SLLNode nodeToLeft = null, nodeToDelete = head, nodeToRight = null;
    if (index == 0) { // Is head
      head = nodeToDelete.next; //Pray garbage collection cleans old head
    } else if (index == size) { // Is tail
      for (int i = 0;i < index;i++) { // ------------------------------------------------------------------ O(n)
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
    }
    size--;
  }

  /** Deletes entire circular singly linked list
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
    SLLNode node = new SLLNode(nodeValue, null);
    node.next = node;
    head = node;
    tail = node;
    size = 1;
  }

  /** Insert a value into the circular linked list
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
    }
    
    //Insert node expecting already present nodes
    SLLNode node = new SLLNode(nodeValue, null);
    if (index == 0) { // Is head
      node.next = head;
      head = node;
      tail.next = node;
    } else if (index == size) { // Is tail
      tail.next = node;
      node.next = head;
      tail = node;
    } else { // Is in middle
      SLLNode nodeToLeft = null;
      SLLNode nodeAtIndex = head;
      for (int i = 0;i < index;i++) { // ----------------------------------------------------------------- O(index)
        nodeToLeft = nodeAtIndex;
        nodeAtIndex = nodeAtIndex.next;
      }
      nodeToLeft.next = node;
      node.next = nodeAtIndex;
    }
    size++;
  }

  /** Deduce if a value is present anywhere in the circular singly linked list
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set the new node to
  * @return Returns a boolean representing if the value is found in the list
  */
  public boolean isInList(int nodeValue) { // ------------------------------------------------------------- O(n)
    //Edge case
    if (size == 0)
      return false;

    SLLNode curNode = head;
    for (int i = 0;i < size;i++) { // -------------------------------------------------------------------------- O(n)
      if (curNode.value == nodeValue)
        return true;
      curNode = curNode.next;
    }
    return false;
  }
  
  /** Generic toString
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public String toString() { // -------------------------------------------------------------------------- O(n)
    String str = "Size: " + size + " :: ";
    SLLNode node = head;
    for (int i = 0;i < size;i++) { // -------------------------------------------------------------------- O(n)
      str += node.value + " ";
      node = node.next;
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
    CircularSinglyLinkedList cSLL = new CircularSinglyLinkedList(300);
    cSLL.insert(1,1);
    cSLL.insert(500,2);
    System.out.println(cSLL.toString());
    cSLL.insert(4,2);
    System.out.println(cSLL.toString());
    cSLL.insert(42,0);
    System.out.println(cSLL.toString());
  }
  public static void Test_IsInList() {
    CircularSinglyLinkedList cSLL = new CircularSinglyLinkedList(300);
    cSLL.insert(1,1);
    cSLL.insert(500,2);
    cSLL.insert(4,2);
    System.out.println(cSLL.isInList(42));
    cSLL.insert(42,0);
    System.out.println(cSLL.isInList(42));
  }
  public static void Test_Delete() {
    CircularSinglyLinkedList cSLL = new CircularSinglyLinkedList(0);
    cSLL.insert(1,1);
    cSLL.insert(2,2);
    cSLL.insert(3,3);
    cSLL.insert(4,4);
    System.out.println(cSLL.toString());
    cSLL.delete(0);
    System.out.println(cSLL.toString());
    cSLL.delete(cSLL.size-1);
    System.out.println(cSLL.toString());
    cSLL.delete(1);
    System.out.println(cSLL.toString());
    cSLL.delete(5);
    System.out.println(cSLL.toString());
  }
  public static void Test_DeleteEntireList() {
    CircularSinglyLinkedList cSLL = new CircularSinglyLinkedList(0);
    cSLL.insert(1,1);
    cSLL.insert(2,2);
    cSLL.insert(3,3);
    cSLL.insert(4,4);
    System.out.println(cSLL.toString());
    cSLL.deleteEntireList();
    System.out.println(cSLL.toString());
  }
}