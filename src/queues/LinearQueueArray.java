package src.queues;

/*------------------------------
* - Note from author:
*    "Linear Queues" work around rotary queue O(n) deletion problem by gimping functionality, only allowing
*    a single pass through.
* ------------------------------
*/

/** Represents an array following principles of rotary queue implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class LinearQueueArray {
  int[] arr;
  int startIndex, endIndex;

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public LinearQueueArray() { // ---------------------------------------------------------------------------------- O(n)
    this(10); // -------------------------------------------------------------------------------------------------- O(n)
  }

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public LinearQueueArray(int size) { // -------------------------------------------------------------------------- O(n)
    arr = new int[size];
    for (int i = 0;i < size;i++) // ------------------------------------------------------------------------------- O(n)
      arr[i] = Integer.MIN_VALUE;
    startIndex = 0;
    endIndex = 0;
  }

  /** Deletes the oldest element of the queue
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() {
    if (isEmpty()) {
      System.out.println("Attempted to delete from an empty LinearQueue!");
      return;
    }
    
    arr[startIndex] = Integer.MIN_VALUE;
    startIndex++;
  }

  /** Gets the oldest element of the queue, removing it from the queue
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int dequeue() {
    if (isEmpty()) {
      System.out.println("Attempted to dequeue from an empty LinearQueue!");
      return Integer.MIN_VALUE;
    }

    int cache = arr[startIndex];
    arr[startIndex] = Integer.MIN_VALUE;
    startIndex++;
    return cache;
  }

  /** Adds a new element to the back of the queue
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the back of the queue
  */
  public void enqueue(int value) {
    if (isFull()) {
      System.out.println("Attempted to enqueue to a fully used LinearQueue!");
      return;
    }
    
    arr[endIndex] = value;
    endIndex++;
  }

  /** Checks if queue is empty
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is empty
  */
  public boolean isEmpty() {
    if (endIndex - startIndex < 1)
      return true;
    return false;
  }

  /** Checks if queue is full
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is full
  */
  public boolean isFull() {
    if (endIndex == arr.length)
      return true;
    return false;
  }

  /** Gets the oldest element of the queue without removal
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int peek() {
    if (isEmpty()) {
      System.out.println("Attempted to peek at an empty LinearQueue!");
      return Integer.MIN_VALUE;
    }
    
    return arr[startIndex];
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------------ O(n)
    String str = startIndex + ":" + endIndex + " :: ";
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
    LinearQueueArray q = new LinearQueueArray(5);
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
    LinearQueueArray q = new LinearQueueArray(5);
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
    LinearQueueArray q = new LinearQueueArray(5);
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
    LinearQueueArray q = new LinearQueueArray(5);
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
}