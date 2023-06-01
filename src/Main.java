package src;
import src.arrays.*;
import src.errorexemplification.*;
import src.linkedlists.*;
import src.projects.*;
import src.queues.*;
import src.stacks.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    QueueSinglyLinkedList.Test_Enqueue();
    System.out.println("\n --------------- \n");
    QueueSinglyLinkedList.Test_Delete();
    System.out.println("\n --------------- \n");
    QueueSinglyLinkedList.Test_Peek();
    System.out.println("\n --------------- \n");
    QueueSinglyLinkedList.Test_Dequeue();
  }
}