package src.recursion;

/*------------------------------
* - Note from author:
*    This file in particular is a throwaway to explore the underworkings of a lesson.
*    Ignore.
* ------------------------------
*/

public class Fibonacci {
  private Fibonacci() { 
  }

  public static void Test_Fib() {
    System.out.println("fib(0) = " + fib(0));
    System.out.println("fib(1) = " + fib(1));
    System.out.println("fib(2) = " + fib(2));
    System.out.println("fib(3) = " + fib(3));
    System.out.println("fib(4) = " + fib(4));
    System.out.println("fib(5) = " + fib(5));
    System.out.println("fib(6) = " + fib(6));
  }

  private static int fib(int n) {
    if (n < 0)
      return -1;
    if (n == 0 || n == 1)
      return n;
    return fib(n-1) + fib(n-2);
  }
}