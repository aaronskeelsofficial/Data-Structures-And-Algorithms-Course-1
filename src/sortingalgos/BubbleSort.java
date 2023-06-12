package src.sortingalgos;

/*------------------------------
* - Note from author:
*    I wanted to incorporate generics into this, but ARRAYS CAN'T BE GENERIC. SO I CAN'T.
*    THIS PROBLEM HAS COME UP LIKE 4 TIMES NOW. WHY JAVA. WHY CAN'T ARRAYS BE GENERIC. I
*    looked it up online and their main rationale is for "backwards compatibility" *PRIOR
*    TO JAVA 7*. BRO WE ARE ON JAVA 20 RIGHT NOW. The market is predominantly Java 8. How
*    long must you stifle innovation to adhere to the cries of an overwhelming minority JAVA?
*    HOW LONG? You DROPPED LONG TERM SUPPORT FOR JAVA 8 MONTHS AGO YET STEP ON MY TOES OVER
*    JAVA 6?!?!
* ------------------------------
*/

public class BubbleSort {
  public static void sort(int[] arr) {
    int len = arr.length;
    for (int i = 0;i < len-1;i++) {
      for (int j = 0;j < n-1-i;j++) {
        if (arr[j] > arr[j+1]) {
          int cache = arr[j];
          arr[j] = arr[j+1];
          arr[j+1] = cache;
        }
      }
    }
  }

  public static String toString(int[] arr) {
    for (int i = 0;i < arr.length;i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println("");
  }
  
}