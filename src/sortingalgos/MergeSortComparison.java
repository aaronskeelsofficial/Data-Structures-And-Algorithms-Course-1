package src.sortingalgos;
import java.util.Random;

/*------------------------------
* - Note from author:
*    This class's purpose was to test my custom coded from scratch (ground up from my noggin)
*    implementation of merge sort using iterative loops rather than recursion against the
*    recursion-centric code given in the course I'm taking. My motive in using iteration rather
*    than recursion is I despise recursion and don't yet have the knowledge of how to analyze
*    recursive time complexities. I'm not expecting my code to be all that great given the
*    instructor probably is just stealing code everyone passes around online with a near
*    perfect optimization, but I'm just curious yunno. As far as the validity of my timekeeping,
*    I'm sure there's a reason the way I'm doing it is trash, but I don't know any better atm 😢
*    I know for a fact SOMETHING is funky about it because when I test arrays of size 1 it takes
*    more than 0 ms, yet arrays of 10, 100, and sometimes 1000 take 0. So this is sus.
*
*    Update: I am swapping all System.nanoTime calls for System.nanoTime and reconducting
*    performance analysis. Results @
*    https://docs.google.com/spreadsheets/d/1w_E_6AZpfKGby5u997wG5ZrFtEM_fOyWvwjchLD_xxk/edit?usp=sharing
* ------------------------------
*/

public class MergeSortComparison {

  public static int[] buildArr(int size) {
    Random rd = new Random(); // creating Random object
    int[] arr = new int[size];
    for (int i = 0; i < arr.length; i++) {
       arr[i] = rd.nextInt(); // storing random integers in an array
    }
    return arr;
  }
  
  public static void Test_Compare() {
    int[] arr1a = buildArr(1), arr1b = arr1a.clone();
    int[] arr10a = buildArr(10), arr10b = arr10a.clone();
    int[] arr100a = buildArr(100), arr100b = arr100a.clone();
    int[] arr1000a = buildArr(1000), arr1000b = arr1000a.clone();
    int[] arr10000a = buildArr(10000), arr10000b = arr10000a.clone();
    int[] arr100000a = buildArr(100000), arr100000b = arr100000a.clone();
    int[] arr1000000a = buildArr(1000000), arr1000000b = arr1000000a.clone();
    long start1 = System.nanoTime();
    MergeSort.sort(arr1a);
    long end1a = System.nanoTime();
    MergeSortIterative.sort(arr1b);
    long end1b = System.nanoTime();
    long start10 = System.nanoTime();
    MergeSort.sort(arr10a);
    long end10a = System.nanoTime();
    MergeSortIterative.sort(arr10b);
    long end10b = System.nanoTime();
    long start100 = System.nanoTime();
    MergeSort.sort(arr100a);
    long end100a = System.nanoTime();
    MergeSortIterative.sort(arr100b);
    long end100b = System.nanoTime();
    long start1000 = System.nanoTime();
    MergeSort.sort(arr1000a);
    long end1000a = System.nanoTime();
    MergeSortIterative.sort(arr1000b);
    long end1000b = System.nanoTime();
    long start10000 = System.nanoTime();
    MergeSort.sort(arr10000a);
    long end10000a = System.nanoTime();
    MergeSortIterative.sort(arr10000b);
    long end10000b = System.nanoTime();
    long start100000 = System.nanoTime();
    MergeSort.sort(arr100000a);
    long end100000a = System.nanoTime();
    MergeSortIterative.sort(arr100000b);
    long end100000b = System.nanoTime();
    long start1000000 = System.nanoTime();
    MergeSort.sort(arr1000000a);
    long end1000000a = System.nanoTime();
    MergeSortIterative.sort(arr1000000b);
    long end1000000b = System.nanoTime();
    System.out.println((end1a-start1) + " : " + (end1b-end1a) + " : " + ((float) (end1b-end1a)/(end1a-start1)));
    System.out.println((end10a-start10) + " : " + (end10b-end10a) + " : " + ((float) (end10b-end10a)/(end10a-start10)));
    System.out.println((end100a-start100) + " : " + (end100b-end100a) + " : " + ((float) (end100b-end100a)/(end100a-start100)));
    System.out.println((end1000a-start1000) + " : " + (end1000b-end1000a) + " : " + ((float) (end1000b-end1000a)/(end1000a-start1000)));
    System.out.println((end10000a-start10000) + " : " + (end10000b-end10000a) + " : " + ((float) (end10000b-end10000a)/(end10000a-start10000)));
    System.out.println((end100000a-start100000) + " : " + (end100000b-end100000a) + " : " + ((float) (end100000b-end100000a)/(end100000a-start100000)));
    System.out.println((end1000000a-start1000000) + " : " + (end1000000b-end1000000a) + " : " + ((float) (end1000000b-end1000000a)/(end1000000a-start1000000)));
    System.out.println("Used Version: " + MergeSortIterative.getVersion());
    // ----- V1 Benchmark Results
    // I am losing quite considerably. My iterative implementation takes 2x -> 2.5x regardless of array size.
    // ----- V2 Benchmark Results
    // In this version I'm commented out my syso statements instead of hiding them behind an if (debug) condition.
    //  I also removed all (float) casts as the operations would naturally cast to float and MAYBE I'm double casting idk
    // This has shot me to actually taking a SINGIFICANT lead (their code is ~4x-5x) in tests up to 10k elements.
    // I am still ~2x for 100,000 and 1,000,000 though. I'm curious as to why. I wonder if it has to do with
    // continuous array instantiation each loop instead of reusing the same temporary arrays
    // ----- V3 Benchmark Results
    // In this version I have removed the Arrays.copyRangeOf in place of another method which allows me to not
    //  need to reinitialize the arrays each loop (there were MANY).
    /*
    * Sample size: 10 Tests
    * Array size 1: My code performs in 2.01x the speed of the original (mildly volatile)
    * Array size 10: My code performs in 37x the speed of the original (EXTREMELY slower. Overhead of avoiding recursion must be large here)
    * Array size 100: My code performs in .59x the speed of the original (Almost every run was sub .6x, but ONE run was 184x which I removed)
    * Array size 1000: My code performs in .504x the speed of the original (consistently ~.3x faster, ONE volatile 5x spike)
    * Array size 10000: My code performs in 2.27x the speed of the original (consistently slower)
    * Array size 100000: My code performs in 1.04x the speed of the original (slightly volatile performance above/below ~1x)
    * Array size 1000000: My code performs in .69x the speed of the original (consistently faster)
    * Array size misc: I attempted a few array sizes larger than 1,000,000 (limited by replit ram caps) and every time I seemed to hover around .6x. I feel as though at array sizes this large my algorithm consistently performs better than the recursive version.
    */
    // ----- V4 Idea
    // I'm not actually coding this up because I'm moving on, but if I had to guess I think the biggest limiter on my code is
    // `numberOfGroupComparisonsThisLayer = (int) Math.ceil(arr.length / (subArrayLengthThisLayer*2f));`
    // I think all these type castings and roundings really slows stuff down. I think the most optimal implementation would
    //  use some smart math trickery to update this value less primitively. I'm simply too lazy and have spent about 4 hours
    //  on this. I just wanted to know how close I could get with mild effort, but that tip toes into craziness when there
    //  is probably already a perfectly optimized version I can find in 2 seconds online.
  }
  
}