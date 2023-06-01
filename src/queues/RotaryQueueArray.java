package src.queues;

/*------------------------------
* - Note from author:
*    I am aware this is not a true "typical" type of queue. I believe it is important to understand this
*    "type" of queue as the default implementation, because this implementation is what brings about the
*    O(n) issue which linear and cicular queues then BUILD OFF to fix. If you don't understand why this
*    "type" is problematic, then you won't truly grasp why the others behave the way they do.
*
*    In a "Rotary Queue", deletion yields a full rotation of all elements keeping the array "start" and
*    "end" indices static.
*
*    "Linear Queues" work around this by gimping functionality, only allowing a single pass through.
*
*    "Circular Queues" by shifting the indices around the data O(2), instead of data around indices O(n)
* ------------------------------
*/

/** Represents an array following principles of rotary queue implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class RotaryQueueArray {
  int[] arr;
  //int startIndex, endIndex; // Not necessarily incorporated in Rotary Queue, but used in others so present
  int size; // Not necessary but I like it

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public RotaryQueueArray() { // ---------------------------------------------------------------------------------- O(n)
    this(10); // -------------------------------------------------------------------------------------------------- O(n)
  }

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public RotaryQueueArray(int size) { // -------------------------------------------------------------------------- O(n)
    arr = new int[size];
    for (int i = 0;i < size;i++) // ------------------------------------------------------------------------------- O(n)
      arr[i] = Integer.MIN_VALUE;
    // startIndex = 0;
    // endIndex = 0;
    this.size = 0;
  }

  /** Deletes the oldest element of the queue
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() {
    if (isEmpty()) {
      System.out.println("Attempted to delete from an empty RotaryQueue!");
      return;
    }
    
    for (int i = 0;i < size;i++) {
      if (i == arr.length-1)
        arr[i] = Integer.MIN_VALUE;
      else
        arr[i] = arr[i+1];
    }
    size--;
  }

  /** Gets the oldest element of the queue, removing it from the queue
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns oldest element of the queue
  */
  public int dequeue() {
    if (isEmpty()) {
      System.out.println("Attempted to dequeue from an empty RotaryQueue!");
      return Integer.MIN_VALUE;
    }

    int cache = arr[0];
    for (int i = 0;i < size;i++) {
      if (i == arr.length-1)
        arr[i] = Integer.MIN_VALUE;
      else
        arr[i] = arr[i+1];
    }
    size--;
    return cache;
  }

  /** Adds a new element to the back of the queue
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the back of the queue
  */
  public void enqueue(int value) {
    if (isFull()) {
      System.out.println("Attempted to enqueue to a full RotaryQueue!");
      return;
    }
    
    arr[size] = value;
    size++;
  }

  /** Checks if queue is empty
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is empty
  */
  public boolean isEmpty() {
    if (size == 0)
      return true;
    return false;
  }

  /** Checks if queue is full
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if queue is full
  */
  public boolean isFull() {
    if (size == arr.length)
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
      System.out.println("Attempted to peek at an empty RotaryQueue!");
      return Integer.MIN_VALUE;
    }
    
    return arr[size-1];
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------------ O(n)
    String str = "Size: " + size + " :: ";
    for (int i = 0;i < arr.length;i++) { // ------------------------------------------------------------------------ O(n)
      if (arr[i] == Integer.MIN_VALUE)
        str += ("_ ");
      else
        str += (arr[i] + " ");
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
    RotaryQueueArray q = new RotaryQueueArray(5);
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
    RotaryQueueArray q = new RotaryQueueArray(5);
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
    RotaryQueueArray q = new RotaryQueueArray(5);
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
    RotaryQueueArray q = new RotaryQueueArray(5);
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