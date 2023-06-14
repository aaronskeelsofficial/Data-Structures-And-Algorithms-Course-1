package src;
import src.arrays.*;
import src.linkedlists.*;
import src.projects.*;
import src.queues.*;
import src.recursion.*;
import src.stacks.*;
import src.trees.binarytreelinkedlist.*;
import src.trees.binarytreearray.*;
import src.trees.binarysearchtreelinkedlist.*;
import src.trees.avltreelinkedlist.*;
import src.trees.binaryheaparray.*;
import src.trees.trielinkedlist.*;
import src.hashes.directchaining.*;
import src.hashes.openaddressing.*;
import src.sortingalgos.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    BubbleSort.Test_Sort();
    System.out.println("\n-----\n");
    BucketSort.Test_Sort();
    System.out.println("\n-----\n");
    InsertionSort.Test_Sort();
    System.out.println("\n-----\n");
    MergeSort.Test_Sort();
    System.out.println("\n-----\n");
    MergeSortIterative.Test_Sort();
    System.out.println("\n-----\n");
    QuickSort.Test_Sort();
    System.out.println("\n-----\n");
    SelectionSort.Test_Sort();
    System.out.println("\n-----\n");
    HeapSort.Test_Sort();
    System.out.println("\n-----\n");
  }
}