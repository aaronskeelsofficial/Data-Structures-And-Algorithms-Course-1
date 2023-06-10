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

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    BinaryHeapArr.Test_InsertMin();
    System.out.println("\n---------\n");
    BinaryHeapArr.Test_ExtractMin();
    System.out.println("\n---------\n");
  }
}