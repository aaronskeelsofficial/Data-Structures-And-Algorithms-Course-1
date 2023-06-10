package src.trees.avltreelinkedlist;
import java.util.ArrayDeque;

public class AVLLL_OldV1<T extends Comparable<T>> {
  public AVLNode<T> root;
  
  public AVLLL() { // ------------------------------------------------------------------------------ O(1)
    root = null;
  }
  public AVLLL(T value) { // ----------------------------------------------------------------------- O(1)
    AVLNode<T> node = new AVLNode<T>(value);
    root = node;
  }

  public AVLNode<T> insert(T value) {
    /* Note: In this method, we will first be identifying the methodology/case which is applicable
    *   and then passing off the workload to a specially curated function for that case.
    */
    if (root == null) {
      AVLNode<T> node = new AVLNode<>(value);
      root = node;
      return root;
    }

    AVLNode<T> curNode = root; //curNode will represent the node the value is being attached to. Included in rotation
    AVLNode<T> prevNode = null; //prevNode will represent the parent node to the node being attached to. Included in rotation
    AVLNode<T> prevprevNode = null; //prevprevNode will represent the parent node to the prevNode. NOT included in rotation, but ref is necessary
    Direction prevDir = null; //prevDir represents the direction from the parent node of curNode (prevNode) to curNode
    Direction prevprevDir = null; //prevprevDir represents the dir from the parent node of prevNode (prevprevNode) to prevNode
    boolean prevNodeHadBranch = true;
    while (curNode != null) {
      if (value.compareTo(curNode.value) <= 0 && curNode.left != null) { // If should place to left and another child present
        prevprevNode = prevNode;
        prevNode = curNode;
        curNode = curNode.left;
        prevprevDir = prevDir;
        prevDir = Direction.LEFT;
        prevNodeHadBranch = (prevNode.left != null && prevNode.right != null);
        continue;
      } else if (value.compareTo(curNode.value) <= 0 && curNode.left == null) { // If should place to left and not another child present
        if (prevNodeHadBranch) { // If no rotation is necessary because only one layer down
          return insert_NoRotation(curNode, value, Direction.LEFT);
        } else if (!prevNodeHadBranch && prevDir.equals(Direction.LEFT)) { // If rotation is necessary with case LL
          return insert_Rotation_LL(curNode, value, prevNode, prevprevNode, prevprevDir);
        } else if (!prevNodeHadBranch && prevDir.equals(Direction.RIGHT)) { // If rotation is necessary with case RL
          return insert_Rotation_RL(curNode, value, prevNode, prevprevNode, prevprevDir);
        }
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) { // If should place to right and another child present
        prevprevNode = prevNode;
        prevNode = curNode;
        curNode = curNode.right;
        prevprevDir = prevDir;
        prevDir = Direction.RIGHT;
        prevNodeHadBranch = (prevNode.left != null && prevNode.right != null);
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) { // If should place to right and not another child present
        if (prevNodeHadBranch) { // If no rotation is necessary because only one layer down
          return insert_NoRotation(curNode, value, Direction.RIGHT);
        } else if (!prevNodeHadBranch && prevDir.equals(Direction.LEFT)) { // If rotation is necessary with case LR
          return insert_Rotation_LR(curNode, value, prevNode, prevprevNode, prevprevDir);
        } else if (!prevNodeHadBranch && prevDir.equals(Direction.RIGHT)) { // If rotation is necessary with case RR
          return insert_Rotation_RR(curNode, value, prevNode, prevprevNode, prevprevDir);
        }
      }
    }
    return null;
  }
  public AVLNode<T> insert_NoRotation(AVLNode<T> curNode, T value, Direction curDir) {
    System.out.println("No Rotation");
    AVLNode<T> newNode = new AVLNode<>(value);
    if (curDir.equals(Direction.LEFT))
      curNode.left = newNode;
    else if(curDir.equals(Direction.RIGHT))
      curNode.right = newNode;
    //Handle Depths
    newNode.depth = curNode.depth+1;
    return newNode;
  }
  public AVLNode<T> insert_Rotation_LL(AVLNode<T> curNode, T value, AVLNode<T> prevNode, AVLNode<T> prevprevNode, Direction prevprevDir) {
    System.out.println("LL");
    //curNode takes prevNode spot relative to prevprevNode
    if (prevprevDir == null)
      root = curNode;
    else if (prevprevDir.equals(Direction.LEFT))
      prevprevNode.left = curNode;
    else if (prevprevDir.equals(Direction.RIGHT))
      prevprevNode.right = curNode;
    //Wipe curNode from left spot relative to prevNode's references
    prevNode.left = null;
    //prevNode goes to right of curNode relative to curNode
    curNode.right = prevNode;
    //newNode goes to left of curNode
    AVLNode<T> newNode = new AVLNode<>(value);
    curNode.left = newNode;
    //Handle Depths
    curNode.depth = (prevprevNode == null) ? 0 : prevprevNode.depth+1;
    newNode.depth = curNode.depth+1;
    prevNode.depth = curNode.depth+1;
    return newNode;
  }
  public AVLNode<T> insert_Rotation_RL(AVLNode<T> curNode, T value, AVLNode<T> prevNode, AVLNode<T> prevprevNode, Direction prevprevDir) {
    System.out.println("RL");
    //newNode takes prevNode spot relative to prevprevNode
    AVLNode<T> newNode = new AVLNode<>(value);
    if (prevprevDir == null)
      root = newNode;
    else if (prevprevDir.equals(Direction.LEFT))
      prevprevNode.left = newNode;
    else if (prevprevDir.equals(Direction.RIGHT))
      prevprevNode.right = newNode;
    //Wipe curNode from right spot relative to prevNode's references
    prevNode.right = null;
    //prevNode goes to left of newNode relative to curNode
    newNode.left = prevNode;
    //curNode goes to right of newNode
    newNode.right = curNode;
    //Handle Depths
    newNode.depth = (prevprevNode == null) ? 0 : prevprevNode.depth+1;
    curNode.depth = newNode.depth+1;
    prevNode.depth = newNode.depth+1;
    return newNode;
  }
  public AVLNode<T> insert_Rotation_LR(AVLNode<T> curNode, T value, AVLNode<T> prevNode, AVLNode<T> prevprevNode, Direction prevprevDir) {
    System.out.println("LR");
    //newNode takes prevNode spot relative to prevprevNode
    AVLNode<T> newNode = new AVLNode<>(value);
    if (prevprevDir == null)
      root = newNode;
    else if (prevprevDir.equals(Direction.LEFT))
      prevprevNode.left = newNode;
    else if (prevprevDir.equals(Direction.RIGHT))
      prevprevNode.right = newNode;
    //Wipe curNode from left spot relative to prevNode's references
    prevNode.left = null;
    //curNode goes to left of newNode relative to curNode
    newNode.left = curNode;
    //prevNode goes to right of newNode
    newNode.right = prevNode;
    //Handle Depths
    newNode.depth = (prevprevNode == null) ? 0 : prevprevNode.depth+1;
    curNode.depth = newNode.depth+1;
    prevNode.depth = newNode.depth+1;
    return newNode;
  }
  public AVLNode<T> insert_Rotation_RR(AVLNode<T> curNode, T value, AVLNode<T> prevNode, AVLNode<T> prevprevNode, Direction prevprevDir) {
    System.out.println("RR");
    //curNode takes prevNode spot relative to prevprevNode
    if (prevprevDir == null)
      root = curNode;
    else if (prevprevDir.equals(Direction.LEFT))
      prevprevNode.left = curNode;
    else if (prevprevDir.equals(Direction.RIGHT))
      prevprevNode.right = curNode;
    //Wipe curNode from right spot relative to prevNode's references
    prevNode.right = null;
    //prevNode goes to left of curNode relative to curNode
    curNode.left = prevNode;
    //newNode goes to right of curNode
    AVLNode<T> newNode = new AVLNode<>(value);
    curNode.right = newNode;
    //Handle Depths
    curNode.depth = (prevprevNode == null) ? 0 : prevprevNode.depth+1;
    newNode.depth = curNode.depth+1;
    prevNode.depth = curNode.depth+1;
    return newNode;
  }

  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void inorderTraversal() { // ------------------------------------------------------------------------ O(n)
    inorderTraversal(root);
  }
  /** Traverses using LCR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void inorderTraversal(AVLNode<T> node) { // --------------------------------------------------------- O(n)
    if (node == null)
      return;

    inorderTraversal(node.left); // -------------------------------------------------------------------------- O(n/2)
    System.out.print(node.value + " ");
    inorderTraversal(node.right); // ------------------------------------------------------------------------- O(n/2)
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void levelorderTraversal() { // --------------------------------------------------------------------- O(n)
    levelorderTraversal(root);
  }
  /** Traverses using level (queue) logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void levelorderTraversal(AVLNode<T> node) { // ------------------------------------------------------ O(n)
    //Finally iterative design. I hate recursion. It's dirty.
    AVLNode<T> curNode = node;
    ArrayDeque<AVLNode<T>> queue = new ArrayDeque<>();
    queue.add(curNode);
    while (!queue.isEmpty()) { // ---------------------------------------------------------------------------- O(n)
      curNode = queue.remove();
      System.out.print(curNode.value + " ");
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void postorderTraversal() { // ----------------------------------------------------------------------- O(n)
    postorderTraversal(root);
  }
  /** Traverses using LRC recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void postorderTraversal(AVLNode<T> node) { // -------------------------------------------------------- O(n)
    if (node == null)
      return;

    postorderTraversal(node.left); // ------------------------------------------------------------------------- O(n/2)
    postorderTraversal(node.right); // ------------------------------------------------------------------------ O(n/2)
    System.out.print(node.value + " ");
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  */
  public void preorderTraversal() { // ----------------------------------------------------------------------- O(n)
    preorderTraversal(root);
  }
  /** Traverses using CLR recursion logic
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root node for traversal initialization
  */
  public void preorderTraversal(AVLNode<T> node) { // -------------------------------------------------------- O(n)
    if (node == null)
      return;

    System.out.print(node.value + " ");
    preorderTraversal(node.left); // ------------------------------------------------------------------------- O(n/2)
    preorderTraversal(node.right); // ------------------------------------------------------------------------ O(n/2)
  }

  /** Searches for the first occurrence of a value (minimum depth) if it exists within the tree
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to search for
  * @return Returns a reference to the node with the target value
  */
  public AVLNode<T> search(T value) { // ------------------------------------------------------------------- O(n)
    if (root == null)
      return null;

    AVLNode<T> curNode = root;
    //Death to recursion! It is for apes!
    while (curNode != null) { // --------------------------------------------------------------------------- O(n)
      if (value.compareTo(curNode.value) == 0) {
        return curNode;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left != null) {
        curNode = curNode.left;
        continue;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left == null) {
        return null;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) {
        curNode = curNode.right;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) {
        return null;
      }
    }
    return null;
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  */
  public String toString() {
    if (root == null)
      return "";
    
    String str = "";
    AVLNode<T> curNode = root;
    ArrayDeque<AVLNode<T>> queue = new ArrayDeque<>();
    queue.add(curNode);
    int emergencyLoopCounter = 0;
    final int LOOPCUTOFF = 200;
    while (!queue.isEmpty() && emergencyLoopCounter++ < LOOPCUTOFF) {
      curNode = queue.remove();
      str += curNode.toString() + "\n";
      if (curNode.left != null)
        queue.add(curNode.left);
      if (curNode.right != null)
        queue.add(curNode.right);
    }
    return str;
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    AVLLL<Integer> t = new AVLLL<>();
    t.insert(10);
    System.out.println(t);
    t.insert(11);
    t.insert(9);
    System.out.println(t);
    t.insert(5);
    t.insert(6);
    t.insert(7);
    System.out.println(t);
  }
  public static void Test_Insert_LL() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(70, null, null, 0);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(50, null, null, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(90, null, null, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(30, null, null, 2);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(60, null, null, 2);
    N_L.right = N_LR;
    AVLNode<Integer> N_RL = new AVLNode(80, null, null, 2);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(100, null, null, 2);
    N_R.right = N_RR;
    AVLNode<Integer> N_LLL = new AVLNode(20, null, null, 3);
    N_LL.left = N_LLL;
    //Test behavior
    System.out.println(t);
    t.insert(10);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_RL() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(50, null, null, 0);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(40, null, null, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(60, null, null, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_RR = new AVLNode(70, null, null, 2);
    N_R.right = N_RR;
    //Test behavior
    System.out.println(t);
    t.insert(65);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_LR() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(70, null, null, 0);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(50, null, null, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(90, null, null, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(30, null, null, 2);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(60, null, null, 2);
    N_L.right = N_LR;
    AVLNode<Integer> N_RL = new AVLNode(80, null, null, 2);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(100, null, null, 2);
    N_R.right = N_RR;
    AVLNode<Integer> N_LLL = new AVLNode(20, null, null, 3);
    N_LL.left = N_LLL;
    //Test behavior
    System.out.println(t);
    t.insert(25);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_RR() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(50, null, null, 0);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(40, null, null, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(60, null, null, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_RR = new AVLNode(70, null, null, 2);
    N_R.right = N_RR;
    //Test behavior
    System.out.println(t);
    t.insert(75);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_Comprehensive() {
    AVLLL<Integer> t = new AVLLL<>();
    t.insert(30);
    System.out.println(t);
    t.insert(25);
    System.out.println(t);
    t.insert(35);
    System.out.println(t);
    t.insert(20);
    System.out.println(t);
    t.insert(15);
    System.out.println(t);
    t.insert(5);
    System.out.println(t);
    t.insert(10);
    System.out.println(t);
    t.insert(50);
    System.out.println(t);
    t.insert(60);
    System.out.println(t);
    t.insert(70);
    System.out.println(t);
    t.insert(65);
    System.out.println(t);
  }
}