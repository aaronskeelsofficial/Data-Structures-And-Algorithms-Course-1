package src.recursion;

/*------------------------------
* - Note from author:
*    This file in particular is a throwaway to explore the underworkings of a lesson.
*    Ignore.
* ------------------------------
*/

public class Pow {
  private Pow() {
  }

  public static void Test() {
    System.out.println("pow(3,0) = " + pow(3,0));
    System.out.println("pow(3,1) = " + pow(3,1));
    System.out.println("pow(3,2) = " + pow(3,2));
  }

  private static int pow(int base, int exp) {
    //Error cases
    if (exp < 0)
      return -1;
    //Intentional end case
    if (exp == 0)
      return 1;

    //Recursion
    return base * pow(base, exp-1);
  }
}