package src;
import src.arrays.*;
import src.linkedlists.*;
import src.projects.*;
import src.stacks.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    StackSinglyLinkedList.Test_Push();
    System.out.println("\n --------------- \n");
    StackSinglyLinkedList.Test_Delete();
    System.out.println("\n --------------- \n");
    StackSinglyLinkedList.Test_Peek();
    System.out.println("\n --------------- \n");
    StackSinglyLinkedList.Test_Pop();
  }
}