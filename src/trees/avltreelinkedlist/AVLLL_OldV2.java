package src.trees.avltreelinkedlist;
import java.util.ArrayDeque;

public class AVLLL_OldV2<T extends Comparable<T>> {
  public AVLNode<T> root;
  
  public AVLLL() { // ------------------------------------------------------------------------------ O(1)
    root = null;
  }
  public AVLLL(T value) { // ----------------------------------------------------------------------- O(1)
    AVLNode<T> node = new AVLNode<T>(value);
    root = node;
  }

  public AVLNode<T> insert(T value) {
    /* Note: This method is typically handled via recursion. I have inspected the recursive technique, but just don't like
    *   recursion so I will be attempting to code my own completely custom iterative implementation kinda from scratch.
    */
    if (root == null) {
      AVLNode<T> node = new AVLNode<>(value);
      root = node;
      return root;
    }

    // Step 1. Add new node to tree
    AVLNode<T> newNode = null; //This will be returned at the end
    AVLNode<T> curNode = root;
    Object[] nodePath = new Object[root.height+2]; //+1 for 0 offset, +1 for new element being added
    int lastIndexOfPathUsed = -1; //This variable represents the newNode index of the nodePath array since it could be oversized
    Direction[] dirPath = new Direction[nodePath.length-1]; //Size is because direction represents transitions which has 1 less than elements
    int dirPathIndex = 0;
    System.out.println("Attempting to add: " + value);
    while (curNode != null) {
      nodePath[curNode.depth] = curNode;
      System.out.println("Hit node: " + curNode);
      if (value.compareTo(curNode.value) <= 0 && curNode.left != null) { // If should place to left and another child present
        curNode = curNode.left;
        dirPath[dirPathIndex] = Direction.LEFT;
        dirPathIndex++;
        continue;
      } else if (value.compareTo(curNode.value) <= 0 && curNode.left == null) { // If should place to left and not another child present
        newNode = new AVLNode<>(value, null, null, curNode.depth+1, 0);
        curNode.left = newNode;
        // updateHeight(curNode);
        nodePath[newNode.depth] = newNode;
        lastIndexOfPathUsed = newNode.depth;
        break;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) { // If should place to right and another child present
        curNode = curNode.right;
        dirPath[dirPathIndex] = Direction.RIGHT;
        dirPathIndex++;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) { // If should place to right and not another child present
        newNode = new AVLNode<>(value, null, null, curNode.depth+1, 0);
        curNode.right = newNode;
        // updateHeight(curNode);
        nodePath[newNode.depth] = newNode;
        lastIndexOfPathUsed = newNode.depth;
        break;
      }
    }

    //Step 2. Update heights through chain of nodes travelled bottom to top excluding newNode bc 0
    for (int i = lastIndexOfPathUsed;i >= 0;i--) {
      if (nodePath[i] == null)
        continue;
      
      AVLNode<T> node = (AVLNode<T>) nodePath[i];
      updateHeight(node);
    }

    //Step 3. Loop from bottom to top of chain, checking if rotation is needed at each step
    for (int i = lastIndexOfPathUsed-2;i >= 0;i--) {
      checkForRotation(i, nodePath, dirPath);
    }
    
    return newNode;
  }
  private void updateHeight(AVLNode<T> node) {
    /* Note: This method does NOT run recursively unlike the updateDepth method due to the monodirectional reference structure
    */
    if (node == null)
      return;
    if (node.left == null && node.right == null) {
      node.height = 0;
      return;
    }
    node.height = 1 + Math.max((node.left != null) ? node.left.height : 0, (node.right != null) ? node.right.height : 0);
  }
  private void updateDepth(AVLNode<T> node, int specifiedNodeDepth) {
    /* Note: This method runs recursively unlike the updateHeight method due to the monodirectional reference structure
    */
    if (node == null)
      return;
    
    node.depth = specifiedNodeDepth;
    updateDepth(node.left, specifiedNodeDepth+1);
    updateDepth(node.right, specifiedNodeDepth+1);
  }
  private void checkForRotation(int nodeIndex, Object[] nodePath, Direction[] dirPath) {
    AVLNode<T> node = (AVLNode<T>) nodePath[nodeIndex];
    //Step 1. Calculate balance
    int balance = getHeight(node.right) - getHeight(node.left);
    
    //Step 2. Handle Balance
    if (balance <= -2) {
      System.out.println("Balance is: " + balance);
      System.out.println("nodeIndex is: " + nodeIndex);
      for (int i = 0;i < dirPath.length;i++) {
        System.out.println(i + ": " + (dirPath[i] != null ? dirPath[i].toString() : "NULL"));
      }
      //Find problematic grandchild
      int leftleftHeight = (node.left != null && node.left.left != null) ? node.left.left.height : -1;
      int leftrightHeight = (node.left != null && node.left.right != null) ? node.left.right.height : -1;
      if (leftleftHeight > leftrightHeight) {
        //Alter the tree lineage
        handleRotation_LL(nodeIndex, nodePath, dirPath);
      } else if (leftrightHeight > leftleftHeight) {
        //Alter the tree lineage
        handleRotation_LR(nodeIndex, nodePath, dirPath);
      }
    } else if (balance >= 2) {
      //Find problematic grandchild
      int rightleftHeight = (node.right != null && node.right.left != null) ? node.right.left.height : -1;
      int rightrightHeight = (node.right != null && node.right.right != null) ? node.right.right.height : -1;
      if (rightleftHeight > rightrightHeight) {
        //Alter the tree lineage
        handleRotation_RL(nodeIndex, nodePath, dirPath);
        //Carry over alterations to nodePath and dirpath
        //TODO
      } else if (rightrightHeight > rightleftHeight) {
        //Alter the tree lineage
        handleRotation_RR(nodeIndex, nodePath, dirPath);
      }
    }
  }
  private int getHeight(AVLNode<T> node) {
    if (node == null)
      return -1;
    return node.height;
  }
  private void handleRotation_LL(int nodeIndex, Object[] nodePath, Direction[] dirPath) {
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = nodeIndex-1 >= 0 ? dirPath[nodeIndex-1] : null;
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) nodePath[nodeIndex+1]; //This is the parent of the leaf
    Direction p0_c = dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) nodePath[nodeIndex+2]; //This is the node added as a leaf
    //Wipe old layout
    parentNode1.left = null;
    AVLNode<T> parentNode0_right = parentNode0.right;
    parentNode0.right = null;
    //Rebuild
    if (parentNode2 == null)
      root = parentNode0;
    else if (p2_p1.equals(Direction.LEFT))
      parentNode2.left = parentNode0;
    else if (p2_p1.equals(Direction.RIGHT))
      parentNode2.right = parentNode0;
    parentNode0.right = parentNode1;
    parentNode1.left = parentNode0_right;
    //Handle Depths & Heights
    if (parentNode2 != null)
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree
    else if (parentNode2 == null)
      updateDepth(parentNode0, 0); // Highest node runs recursively down tree
    // parentNode0.depth = (parentNode2 == null) ? 0 : parentNode2.depth+1;
    // newChildNode.depth = parentNode0.depth+1;
    // parentNode1.depth = parentNode0.depth+1;
    updateHeight(newChildNode); updateHeight(parentNode1); //Layer 0
    updateHeight(parentNode0); //Layer 1
    updateHeight(parentNode2); //Layer 2
    //Modify arrays
      //parentNode1 becomes parentNode0
    nodePath[nodeIndex-0] = parentNode0;
      //parentNode0 becomes newChildNode
    nodePath[nodeIndex+1] = newChildNode;
  }
  private void handleRotation_LR(int nodeIndex, Object[] nodePath, Direction[] dirPath) {
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = nodeIndex-1 >= 0 ? dirPath[nodeIndex-1] : null;
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) nodePath[nodeIndex+1]; //This is the parent of the leaf
    Direction p0_c = dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) nodePath[nodeIndex+2]; //This is the node added as a leaf
    //Wipe old layout
    parentNode1.left = null;
    parentNode0.right = null;
    AVLNode<T> newChildNode_right = newChildNode.right;
    newChildNode.right = null;
    //Rebuild
    if (parentNode2 == null)
      root = newChildNode;
    else if (p2_p1.equals(Direction.LEFT))
      parentNode2.left = newChildNode;
    else if (p2_p1.equals(Direction.RIGHT))
      parentNode2.right = newChildNode;
    newChildNode.left = parentNode0;
    newChildNode.right = parentNode1;
    parentNode1.left = newChildNode_right;
    //Handle Depths & Heights
    if (parentNode2 != null)
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree
    else if (parentNode2 == null)
      updateDepth(newChildNode, 0); // Highest node runs recursively down tree
    // newChildNode.depth = (parentNode2 == null) ? 0 : parentNode2.depth+1;
    // parentNode0.depth = newChildNode.depth+1;
    // parentNode1.depth = newChildNode.depth+1;
    updateHeight(parentNode0); updateHeight(parentNode1); //Layer 0
    updateHeight(newChildNode); //Layer 1
    updateHeight(parentNode2); //Layer 2
    //Modify arrays
      //parentNode1 becomes newChildNode
    nodePath[nodeIndex-0] = newChildNode;
  }
  private void handleRotation_RL(int nodeIndex, Object[] nodePath, Direction[] dirPath) {
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = nodeIndex-1 >= 0 ? dirPath[nodeIndex-1] : null;
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) nodePath[nodeIndex+1]; //This is the parent of the leaf
    Direction p0_c = dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) nodePath[nodeIndex+2]; //This is the node added as a leaf
    //Wipe old layout
    parentNode1.right = null;
    parentNode0.left = null;
    AVLNode<T> newChildNode_left = newChildNode.left;
    newChildNode.left = null;
    //Rebuild
    if (parentNode2 == null)
      root = newChildNode;
    else if (p2_p1.equals(Direction.LEFT))
      parentNode2.left = newChildNode;
    else if (p2_p1.equals(Direction.RIGHT))
      parentNode2.right = newChildNode;
    newChildNode.right = parentNode0;
    newChildNode.left = parentNode1;
    parentNode0.right = newChildNode_left;
    //Handle Depths & Heights
    if (parentNode2 != null)
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree
    else if (parentNode2 == null)
      updateDepth(newChildNode, 0); // Highest node runs recursively down tree
    // newChildNode.depth = (parentNode2 == null) ? 0 : parentNode2.depth+1;
    // parentNode0.depth = newChildNode.depth+1;
    // parentNode1.depth = newChildNode.depth+1;
    updateHeight(parentNode0); updateHeight(parentNode1); //Layer 0
    updateHeight(newChildNode); //Layer 1
    updateHeight(parentNode2); //Layer 2
    //Modify arrays
      //parentNode1 becomes newChildNode
    nodePath[nodeIndex-0] = newChildNode;
  }
  private void handleRotation_RR(int nodeIndex, Object[] nodePath, Direction[] dirPath) {
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = nodeIndex-1 >= 0 ? dirPath[nodeIndex-1] : null;
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) nodePath[nodeIndex+1]; //This is the parent of the leaf
    Direction p0_c = dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) nodePath[nodeIndex+2]; //This is the node added as a leaf
    //Wipe old layout
    parentNode1.right = null;
    AVLNode<T> parentNode0_left = parentNode0.left;
    parentNode0.left = null;
    //Rebuild
    if (parentNode2 == null)
      root = parentNode0;
    else if (p2_p1.equals(Direction.LEFT))
      parentNode2.left = parentNode0;
    else if (p2_p1.equals(Direction.RIGHT))
      parentNode2.right = parentNode0;
    parentNode0.left = parentNode1;
    parentNode1.right = parentNode0_left;
    //Handle Depths & Heights
    if (parentNode2 != null)
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree
    else if (parentNode2 == null)
      updateDepth(parentNode0, 0); // Highest node runs recursively down tree
    // parentNode0.depth = (parentNode2 == null) ? 0 : parentNode2.depth+1;
    // newChildNode.depth = parentNode0.depth+1;
    // parentNode1.depth = parentNode0.depth+1;
    updateHeight(newChildNode); updateHeight(parentNode1); //Layer 0
    updateHeight(parentNode0); //Layer 1
    updateHeight(parentNode2); //Layer 2
    //Modify arrays
      //parentNode1 becomes parentNode0
    nodePath[nodeIndex-0] = parentNode0;
      //parentNode0 becomes newChildNode
    nodePath[nodeIndex+1] = newChildNode;
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

  /** Node based toString method
  * @version 1.0.0
  * @since 1.0.0
  * @param node Node to treat as root
  */
  public String toString(AVLNode<T> node) {
    if (node == null)
      return "";
    
    String str = "";
    AVLNode<T> curNode = node;
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
    t.insert(11);
    t.insert(9);
    t.insert(5);
    System.out.println(t);
    t.insert(6);
    System.out.println(t);
    t.insert(7);
    System.out.println(t);
  }
  public static void Test_Insert_LL() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(70, null, null, 0, 3);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(50, null, null, 1, 2);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(90, null, null, 1, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(30, null, null, 2, 1);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(60, null, null, 2, 0);
    N_L.right = N_LR;
    AVLNode<Integer> N_RL = new AVLNode(80, null, null, 2, 0);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(100, null, null, 2, 0);
    N_R.right = N_RR;
    AVLNode<Integer> N_LLL = new AVLNode(20, null, null, 3, 0);
    N_LL.left = N_LLL;
    //Test behavior
    System.out.println(t);
    t.insert(10);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_LLAdvanced() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(30, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(20, null, null, 1, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(35, null, null, 1, 0);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(15, null, null, 2, 0);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(25, null, null, 2, 0);
    N_L.right = N_LR;
    //Test behavior
    System.out.println(t);
    t.insert(5);
    System.out.println(t);
    //Looks random but once decyphered, this is correct behavior
  }
  public static void Test_Insert_RL() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(50, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(40, null, null, 0, 0);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(60, null, null, 0, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_RR = new AVLNode(70, null, null, 2, 0);
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
    AVLNode<Integer> N_Root = new AVLNode(70, null, null, 0, 3);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(50, null, null, 1, 2);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(90, null, null, 1, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(30, null, null, 2, 1);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(60, null, null, 2, 0);
    N_L.right = N_LR;
    AVLNode<Integer> N_RL = new AVLNode(80, null, null, 2, 0);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(100, null, null, 2, 0);
    N_R.right = N_RR;
    AVLNode<Integer> N_LLL = new AVLNode(20, null, null, 3, 0);
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
    AVLNode<Integer> N_Root = new AVLNode(50, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(40, null, null, 0, 0);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(60, null, null, 0, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_RR = new AVLNode(70, null, null, 2, 0);
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