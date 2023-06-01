package src.queues;
import src.linkedlists.SLLNode;

/** Represents an array following principles of stack implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class QueueSinglyLinkedList {
  SLLNode head, tail;
  int size; // I'm aware implementations exist without size variable relying on head == null, but I like it

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public QueueSinglyLinkedList() { // ------------------------------------------------------------------------------ O(1)
    head = null;
    tail = null;
    size = 0;
  }

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public QueueSinglyLinkedList(int value) { // --------------------------------------------------------------------- O(1)
    initializeLinkedList(value); // -------------------------------------------------------------------------------- O(1)
  }

  /** Deletes the topmost element of the stack
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to delete from empty QueueSinglyLinkedList!");
      return;
    }
    
    head = head.next; // Pray garbage collector clears old head
    size--;
  }

  /** Gets the oldest element of the queue, removing it from the queue
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int dequeue() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to dequeue from empty QueueSinglyLinkedList! Returning base value.");
      return Integer.MIN_VALUE;
    }
    
    int cache = head.value;
    head = head.next;
    size--;
    return cache;
  }

  /** Adds a new element to the top of the queue
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the top of the queue
  */
  public void enqueue(int value) { // --------------------------------------------------------------------------------- O(1)
    SLLNode node = new SLLNode(value, null);
    if (tail != null)
      tail.next = node;
    tail = node;
    if (head == null)
      head = node;
    size++;
  }

  /** Generic initialization method
  * @version 1.0.0
  * @since 1.0.0
  * @param nodeValue Value to set first node to
  */
  public void initializeLinkedList(int nodeValue) { // ------------------------------------------------------------- O(1)
    SLLNode node = new SLLNode(nodeValue, null);
    head = node;
    tail = node;
    size = 1;
  }

  /** Checks if queue is empty
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is empty
  */
  public boolean isEmpty() { // ------------------------------------------------------------------------------------ O(1)
    if (size == 0)
      return true;
    return false;
  }

  /** Gets the oldest element of the queue without removal
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int peek() { // ------------------------------------------------------------------------------------------- O(1)
    if (isEmpty())
      return Integer.MIN_VALUE;
    
    return head.value;
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
  public static void Test_Enqueue() {
    QueueSinglyLinkedList q = new QueueSinglyLinkedList();
    System.out.println(q.toString());
    q.enqueue(1);
    System.out.println(q.toString());
    q.enqueue(2);
    System.out.println(q.toString());
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    System.out.println(q.toString());
    q.enqueue(6);
    System.out.println(q.toString());
  }
  public static void Test_Delete() {
    QueueSinglyLinkedList q = new QueueSinglyLinkedList();
    System.out.println(q.toString());
    q.enqueue(1);
    System.out.println(q.toString());
    q.enqueue(2);
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
  }
  public static void Test_Peek() {
    QueueSinglyLinkedList q = new QueueSinglyLinkedList();
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    System.out.println(q.toString());
    System.out.println("Peeking: " + q.peek());
    System.out.println(q.toString());
  }
  public static void Test_Dequeue() {
    QueueSinglyLinkedList q = new QueueSinglyLinkedList();
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
  }
}