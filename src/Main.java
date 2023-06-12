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

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    LinearProbing.Test_Insert();
    System.out.println("\n---------\n");
    LinearProbing.Test_Delete();
    System.out.println("\n---------\n");
  }
}