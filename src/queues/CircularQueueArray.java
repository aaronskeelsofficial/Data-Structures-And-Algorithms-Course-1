package src.queues;

/*------------------------------
* - Note from author:
*    "Circular Queues" work around rotary queue O(n) deletion problem by rotating start/end indices around data O(2), rather
*    than all data around indices. I don't see any reason to use Linear Queues instead of this aside from hyper-niche reqs.
* ------------------------------
*/

/** Represents an array following principles of rotary queue implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class CircularQueueArray {
  int[] arr;
  int startIndex, endIndex, size;

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public CircularQueueArray() { // --------------------------------------------------------------------------------- O(n)
    this(10); // --------------------------------------------------------------------------------------------------- O(n)
  }

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public CircularQueueArray(int size) { // ------------------------------------------------------------------------- O(n)
    arr = new int[size];
    for (int i = 0;i < size;i++) // -------------------------------------------------------------------------------- O(n)
      arr[i] = Integer.MIN_VALUE;
    startIndex = 0;
    endIndex = 0;
    size = 0;
  }

  /** Deletes the oldest element of the queue
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to delete from an empty CircularQueue!");
      return;
    }
    
    arr[startIndex] = Integer.MIN_VALUE;
    startIndex = (startIndex+1) % arr.length;
    size--;
  }

  /** Gets the oldest element of the queue, removing it from the queue
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int dequeue() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to dequeue from an empty CircularQueue!");
      return Integer.MIN_VALUE;
    }

    int cache = arr[startIndex];
    arr[startIndex] = Integer.MIN_VALUE;
    startIndex = (startIndex+1) % arr.length;
    size--;
    return cache;
  }

  /** Adds a new element to the back of the queue
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the back of the queue
  */
  public void enqueue(int value) { // ------------------------------------------------------------------------------ O(1)
    if (isFull()) {
      System.out.println("Attempted to enqueue to a full CircularQueue!");
      return;
    }

    arr[endIndex] = value;
    endIndex = (endIndex+1) % arr.length;
    size++;
  }

  private int getPositiveIndiceDelta() { // ------------------------------------------------------------------------ O(1)
    return ((endIndex - startIndex) + arr.length) % arr.length;
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

  /** Checks if queue is full
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is full
  */
  public boolean isFull() { // ------------------------------------------------------------------------------------- O(1)
    if (size == arr.length)
      return true;
    return false;
  }

  /** Gets the oldest element of the queue without removal
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int peek() { // ------------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to peek at an empty CircularQueue!");
      return Integer.MIN_VALUE;
    }
    
    return arr[startIndex];
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------------ O(n)
    String str = "Size: " + size + " :: " + startIndex + ":" + endIndex + " :: ";
    for (int i = 0;i < arr.length;i++) { // ------------------------------------------------------------------------ O(n)
      //Debug start/end indices w/ parenthesis
      if (i == startIndex)
        str += "( ";
      if (i == endIndex)
        str += ") ";

      //Actually print values
      if (arr[i] == Integer.MIN_VALUE)
        str += ("_ ");
      else
        str += (arr[i] + " ");
    }
    //Debug start/end indices w/ parenthesis (if they exceed end of array)
    if (startIndex == arr.length)
      str += "( ";
    if (endIndex == arr.length)
      str += ") ";
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Enqueue() {
    CircularQueueArray q = new CircularQueueArray(5);
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
    CircularQueueArray q = new CircularQueueArray(5);
    System.out.println(q.toString());
    q.enqueue(1);
    System.out.println(q.toString());
    q.enqueue(2);
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
    q.enqueue(3);
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
    q.delete();
    System.out.println(q.toString());
  }
  public static void Test_Peek() {
    CircularQueueArray q = new CircularQueueArray(5);
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
    CircularQueueArray q = new CircularQueueArray(5);
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
    q.enqueue(6);
    System.out.println(q.toString());
  }
  public static void Test_Wrap() {
    CircularQueueArray q = new CircularQueueArray(5);
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(4);
    q.enqueue(5);
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    q.enqueue(6);
    System.out.println(q.toString());
    q.enqueue(7);
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    q.enqueue(8);
    System.out.println(q.toString());
    q.enqueue(9);
    System.out.println(q.toString());
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    q.enqueue(10);
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    q.enqueue(11);
    System.out.println("Dequeueing: " + q.dequeue());
    System.out.println(q.toString());
    q.enqueue(12);
    System.out.println(q.toString());
    System.out.println("Did it wrap correctly? Yes :)");
  }
}