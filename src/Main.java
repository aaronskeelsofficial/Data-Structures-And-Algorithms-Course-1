package src;
import src.arrays.*;
import src.linkedlists.*;
import src.projects.*;
import src.queues.*;
import src.recursion.*;
import src.stacks.*;
import src.trees.binarytreelinkedlist.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    BinaryTreeLL.Test_Traversal();
    System.out.println("\n----------\n");
    BinaryTreeLL.Test_Search();
    System.out.println("\n----------\n");
    BinaryTreeLL.Test_Insert();
    System.out.println("\n----------\n");
    BinaryTreeLL.Test_ToString();
    System.out.println("\n----------\n");
    BinaryTreeLL.Test_Delete();
    System.out.println("\n----------\n");
  }
}