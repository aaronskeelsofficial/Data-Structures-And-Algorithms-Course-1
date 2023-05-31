package stacks;

/** Represents an array following principles of stack implementation
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class StackArray {
  int[] arr;
  int size;

  /** Generic constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public StackArray() { // ----------------------------------------------------------------------------------------- O(n)
    this(10); // --------------------------------------------------------------------------------------------------- O(n)
  }

  /** Instantiating constructor
  * @version 1.0.0
  * @since 1.0.0
  */
  public StackArray(int size) { // --------------------------------------------------------------------------------- O(n)
    arr = new int[size];
    size = 0;
    for (int i = 0;i < arr.length;i++) { // ------------------------------------------------------------------------ O(n)
      arr[i] = Integer.MIN_VALUE;
    }
  }

  /** Deletes the topmost element of the stack
  * @version 1.0.0
  * @since 1.0.0
  */
  public void delete() { // ---------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to delete from empty StackArray!");
      return;
    }
    
    arr[size-1] = Integer.MIN_VALUE;
    size--;
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

  /** Checks if stack is full
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns if stack is full
  */
  public boolean isFull() { // ------------------------------------------------------------------------------------- O(1)
    if (size == arr.length)
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
    
    return arr[size-1];
  }

  /** Gets the topmost element of the stack, removing it from the stack
  * @version 1.0.0
  * @since 1.0.0
  * @return Returns topmost element of the stack
  */
  public int pop() { // -------------------------------------------------------------------------------------------- O(1)
    if (isEmpty()) {
      System.out.println("Attempted to pop from empty StackArray! Returning base value.");
      return Integer.MIN_VALUE;
    }
    
    int cache = arr[size-1];
    arr[size-1] = Integer.MIN_VALUE;
    size--;
    return cache;
  }

  /** Adds a new element to the top of the stack
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to be added to the top of the stack
  */
  public void push(int value) { // --------------------------------------------------------------------------------- O(1)
    if (isFull()) {
      System.out.println("Attempted to push " + value + " to full StackArray!");
      return;
    }

    arr[size] = value;
    size++;
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() { // ------------------------------------------------------------------------------------ O(n)
    String str = "";
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
  public static void Test_Push() {
    StackArray s = new StackArray(5);
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
    StackArray s = new StackArray(5);
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
    StackArray s = new StackArray(5);
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
    StackArray s = new StackArray(5);
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