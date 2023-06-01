package src.recursion;

/*------------------------------
* - Note from author:
*    This file in particular is a throwaway to explore the underworkings of a lesson.
*    Ignore.
* ------------------------------
*/

public class DecimalToBinary {
  private DecimalToBinary() {
  }

  public static void Test() {
    System.out.println("d2b(2) = " + d2b(2));
    System.out.println("d2b(6) = " + d2b(6));
    System.out.println("d2b(255) = " + d2b(255));
    System.out.println("d2b(1023) = " + d2b(1023));
    System.out.println("d2b(69420) = " + d2b(69420));
  }

  private static String d2b(int n) {
    //Error cases
    if (n < 0)
      return null;
    //Fix input cases
      // Can't think of any
    //Intentional end case
    if (n < 2)
      return n + "";

    //Recursion
    return d2b(n/2) + "" + (n%2);
  }
}