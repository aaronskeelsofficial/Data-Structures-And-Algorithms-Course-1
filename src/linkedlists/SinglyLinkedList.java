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

/** Represents a singly linked list implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.3
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
  * @version 1.0.1
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public SinglyLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    initializeLinkedList(nodeValue);
  }

  /** Deletes an index from a singly linked list
  * @version 1.0.0
  * @since 1.0.2
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
    SLLNode nodeToLeft = null, nodeToDelete = head, nodeToRight = null;
    if (index == 0) { // Is head
      head = nodeToDelete.next; //Pray garbage collection cleans old head
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
    }
    size--;
  }

  /** Deletes entire singly linked list
  * @version 1.0.0
  * @since 1.0.3
  */
  public void deleteEntireList() { // --------------------------------------------------------------------- O(1)
    head = null;
    tail = null;
    size = 0;
  }
  
  /** Generic initialization method
  * @version 1.0.1
  * @since 1.0.1
  * @param nodeValue Value to set first node to
  */
  public void initializeLinkedList(int nodeValue) { // ---------------------------------------------------- O(1)
    SLLNode node = new SLLNode(nodeValue, null); // ------------------------------------------------------- O(1)
    head = node; // --------------------------------------------------------------------------------------- O(1)
    tail = node; // --------------------------------------------------------------------------------------- O(1)
    size = 1; // ------------------------------------------------------------------------------------------ O(1)
  }

  /** Insert a value into the linked list
  * @version 1.0.0
  * @since 1.0.1
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
    SLLNode node = new SLLNode(nodeValue, null);
    if (index == 0) { // Is head
      node.next = head;
      head = node;
    } else if (index == size) { // Is tail
      tail.next = node;
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

  /** Deduce if a value is present anywhere in the singly linked list
  * @version 1.0.0
  * @since 1.0.2
  * @param nodeValue Value to set the new node to
  * @return Returns a boolean representing if the value is found in the list
  */
  public boolean isInList(int nodeValue) { // ------------------------------------------------------------- O(n)
    //Edge case
    if (size == 0)
      return false;

    SLLNode curNode = head;
    while (curNode != null) { // -------------------------------------------------------------------------- O(n)
      if (curNode.value == nodeValue)
        return true;
      curNode = curNode.next;
    }
    return false;
  }
  
  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.1
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
    SinglyLinkedList sLL = new SinglyLinkedList(300);
    sLL.insert(1,1);
    sLL.insert(500,2);
    System.out.println(sLL.toString());
    sLL.insert(4,2);
    System.out.println(sLL.toString());
    sLL.insert(42,0);
    System.out.println(sLL.toString());
  }
  public static void Test_IsInList() {
    SinglyLinkedList sLL = new SinglyLinkedList(300);
    sLL.insert(1,1);
    sLL.insert(500,2);
    sLL.insert(4,2);
    System.out.println(sLL.isInList(42));
    sLL.insert(42,0);
    System.out.println(sLL.isInList(42));
  }
  public static void Test_Delete() {
    SinglyLinkedList sLL = new SinglyLinkedList(0);
    sLL.insert(1,1);
    sLL.insert(2,2);
    sLL.insert(3,3);
    sLL.insert(4,4);
    System.out.println(sLL.toString());
    sLL.delete(0);
    System.out.println(sLL.toString());
    sLL.delete(sLL.size-1);
    System.out.println(sLL.toString());
    sLL.delete(1);
    System.out.println(sLL.toString());
    sLL.delete(5);
    System.out.println(sLL.toString());
  }
  public static void Test_DeleteEntireList() {
    SinglyLinkedList sLL = new SinglyLinkedList(0);
    sLL.insert(1,1);
    sLL.insert(2,2);
    sLL.insert(3,3);
    sLL.insert(4,4);
    System.out.println(sLL.toString());
    sLL.deleteEntireList();
    System.out.println(sLL.toString());
  }
}