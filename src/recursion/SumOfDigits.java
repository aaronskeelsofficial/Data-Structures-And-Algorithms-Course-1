package src.recursion;

/*------------------------------
* - Note from author:
*    This file in particular is a throwaway to explore the underworkings of a lesson.
*    Ignore.
* ------------------------------
*/

public class SumOfDigits {
  private SumOfDigits() {
  }

  public static void Test() {
    System.out.println("sum(0) = " + sum(0));
    System.out.println("sum(1) = " + sum(1));
    System.out.println("sum(10) = " + sum(10));
    System.out.println("sum(13) = " + sum(13));
    System.out.println("sum(142) = " + sum(142));
    System.out.println("sum(-123) = " + sum(-123));
  }

  private static int sum(int n) {
    if (n < 0)
      return sum(-n);
    if (n < 10)
      return n;

    return n%10 + sum(n/10);
  }
}