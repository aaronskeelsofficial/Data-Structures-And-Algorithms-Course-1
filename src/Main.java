import arrays.*;
import linkedlists.*;
import projects.*;
import stacks.*;

/** Main is used as a dynamic kickoff point of code. Referencing active code at each commit is pointless.
* @author Aaron Skeels
* @author aaronskeels.work/
*/
class Main {
  public static void main(String[] args) {
    StackArray.Test_Push();
    System.out.println("\n --------------- \n");
    StackArray.Test_Delete();
    System.out.println("\n --------------- \n");
    StackArray.Test_Peek();
    System.out.println("\n --------------- \n");
    StackArray.Test_Pop();
  }
}