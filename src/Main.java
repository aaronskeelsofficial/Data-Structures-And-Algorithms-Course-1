package src;
import src.arrays.*;
import src.linkedlists.*;
import src.projects.*;
import src.queues.*;
import src.recursion.*;
import src.stacks.*;
import src.trees.binarytreelinkedlist.*;
import src.trees.binarytreearray.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    BinaryTreeArr.Test_Insert();
    System.out.println("\n----------\n");
    BinaryTreeArr.Test_Traversal();
    System.out.println("\n----------\n");
    BinaryTreeArr.Test_Search();
    System.out.println("\n----------\n");
    BinaryTreeArr.Test_Delete();
    System.out.println("\n----------\n");
  }
}