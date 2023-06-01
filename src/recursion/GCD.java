package src.recursion;

/*------------------------------
* - Note from author:
*    This file in particular is a throwaway to explore the underworkings of a lesson.
*    Ignore.
* ------------------------------
*/

public class GCD {
  private GCD() {
  }

  public static void Test() {
    System.out.println("gcd(2,4) = " + gcd(2,4));
    System.out.println("gcd(6,9) = " + gcd(6,9));
    System.out.println("gcd(5,7) = " + gcd(5,7));
  }

  private static int gcd(int n, int m) {
    //Error cases
      //Can't think of any
    //Fix input cases
    if (m > n)
      return gcd(m, n);
    //Intentional end case
    if (n == m) // In hindsight the next case covers this one too, but this is where my mind went when deriving Euclidean algorithm myself
      return n;
    if (m == 0)
      return n;

    //Recursion
    return gcd(m, n%m);
  }
}