package src.stacks;
import src.linkedlists.SLLNode;

/** Represents an array following principles of stack implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class StackSinglyLinkedList {
  SLLNode head;
  int size; // I'm aware implementations exist without size variable relying on head == null, but I like it

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public StackSinglyLinkedList() { // ------------------------------------------------------------------------------ O(1)
    head = null;
    size = 0;
  }

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public StackSinglyLinkedList(int value) { // --------------------------------------------------------------------- O(1)
    initializeLinkedList(value); // -------------------------------------------------------------------------------- O(1)
  }

  /** Deletes the topmost element of the stack
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to delete from empty StackSinglyLinkedList!");
      return;
    }
    
    head = head.next; // Pray garbage collector clears old head
    size--;
  }

  /** Generic initialization method
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public void initializeLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    SLLNode node = new SLLNode(nodeValue, null);
    head = node;
    size = 1;
  }

  /** Checks if stack is empty
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if stack is empty
  */
  public boolean isEmpty() { // ------------------------------------------------------------------------------------ O(1)
    if (size == 0)
      return true;
    return false;
  }

  /** Gets the topmost element of the stack without removal
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns topmost element of the stack
  */
  public int peek() { // ------------------------------------------------------------------------------------------- O(1)
    if (isEmpty())
      return Integer.MIN_VALUE;
    
    return head.value;
  }

  /** Gets the topmost element of the stack, removing it from the stack
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns topmost element of the stack
  */
  public int pop() { // -------------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to pop from empty StackSinglyLinkedList! Returning base value.");
      return Integer.MIN_VALUE;
    }
    
    int cache = head.value;
    head = head.next;
    size--;
    return cache;
  }

  /** Adds a new element to the top of the stack
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the top of the stack
  */
  public void push(int value) { // --------------------------------------------------------------------------------- O(1)
    SLLNode node = new SLLNode(value, head);
    head = node;
    size++;
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------------ O(n)
    if (isEmpty())
      return "Size: 0 :: _EMPTY_";
    
    String str = "Size: " + size + " :: ";
    SLLNode curNode = head;
    for (int i = 0;i < size;i++) { // ------------------------------------------------------------------------------ O(n)
      str += (curNode.value + " ");
      curNode = curNode.next;
    }
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Push() {
    StackSinglyLinkedList s = new StackSinglyLinkedList();
    System.out.println(s.toString());
    s.push(1);
    System.out.println(s.toString());
    s.push(2);
    System.out.println(s.toString());
    s.push(3);
    s.push(4);
    s.push(5);
    System.out.println(s.toString());
    s.push(6);
    System.out.println(s.toString());
  }
  public static void Test_Delete() {
    StackSinglyLinkedList s = new StackSinglyLinkedList();
    System.out.println(s.toString());
    s.push(1);
    System.out.println(s.toString());
    s.push(2);
    System.out.println(s.toString());
    s.delete();
    System.out.println(s.toString());
    s.delete();
    System.out.println(s.toString());
    s.delete();
    System.out.println(s.toString());
  }
  public static void Test_Peek() {
    StackSinglyLinkedList s = new StackSinglyLinkedList();
    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);
    System.out.println(s.toString());
    System.out.println("Peeking: " + s.peek());
    System.out.println(s.toString());
  }
  public static void Test_Pop() {
    StackSinglyLinkedList s = new StackSinglyLinkedList();
    s.push(1);
    s.push(2);
    s.push(3);
    s.push(4);
    s.push(5);
    System.out.println(s.toString());
    System.out.println("Popping: " + s.pop());
    System.out.println(s.toString());
    System.out.println("Popping: " + s.pop());
    System.out.println("Popping: " + s.pop());
    System.out.println("Popping: " + s.pop());
    System.out.println("Popping: " + s.pop());
    System.out.println(s.toString());
    System.out.println("Popping: " + s.pop());
    System.out.println(s.toString());
  }
}