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
import src.searchingalgos.*;
import src.graphs.array.GraphArr;
import src.graphs.linkedlist.GraphLL;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    // GraphArr.Test_BFSTraverse();
    // System.out.println("\n-----\n");
    // GraphArr.Test_DFSTraverse();
    // System.out.println("\n-----\n");
    // GraphArr.Test_TopologicalTraverse();
    // System.out.println("\n-----\n");
    GraphArr.Test_SSSPPBFS();
    System.out.println("\n-----\n");
    
    // GraphLL.Test_BFSTraverse();
    // System.out.println("\n-----\n");
    // GraphLL.Test_DFSTraverse();
    // System.out.println("\n-----\n");
    // GraphLL.Test_TopologicalTraverse();
    // System.out.println("\n-----\n");
    GraphLL.Test_SSSPPBFS();
    System.out.println("\n-----\n");
  }
}