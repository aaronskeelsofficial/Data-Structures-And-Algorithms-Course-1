import arrays.*;
import linkedlists.*;
import projects.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    DoublyLinkedList.Test_Insert();
    System.out.println("\n-----------\n");
    DoublyLinkedList.Test_IsInList();
    System.out.println("\n-----------\n");
    DoublyLinkedList.Test_Delete();
    System.out.println("\n-----------\n");
    DoublyLinkedList.Test_DeleteEntireList();
  }
}