package src.trees.avltreelinkedlist;
import java.util.ArrayDeque;

/*------------------------------
* - Note from author:
*    This data type was HARD to work on. I spent ~12 hours and 3 iterations working solely on the insert method.
*    This has by far been the first real obstacle I've faced in terms of intuition not magically guiding me
*    for once and having to put some real effort into brainstorming, testing, realizing I had failed, and
*    then fundamentally iterating from the ground up again to try to fix the odd edge case I had missed. I
*    still haven't made the delete function yet, and I'm assuming that would be a lot simpler if I took
*    advantage of the "rotate left" and "rotate right" ideas taught alongside AVL trees. I figured it
*    would be more optimized to break LL, LR, RL, RR into their own single step operation rather than
*    stacking rotations one after the other. But my foresight tells me this decision and my avoidance of
*    recursion (recursion is gross) where I could manage is going to make this a difficult endeavor. That's
*    okay though I'm not afraid of dedicating myself and putting in effort. In some sense that's what makes
*    it fun >:) I WILL PROVE I DON'T NEED TO COPY CODE AND CAN WRITE IT FROM SCRATCH MYSELF AS PROVEN BY
*    NOBODY BEING CRAZY ENOUGH TO DO IT MY WAY AND EVERYONE ELSE STACKING ROTATIONS!
*
*  Day 2 Note:
*    I have implemented deletion. It required a bit of generalization to my existing code for insert, but
*    I got it baby. Let's GOOOO.
*
*  Additional Note:
*    With the class coded custom, straying from traditional means, I am analyzing the specific time complexity
*    of my implementation. It technically is O(n) for most operations due to the inclusion of a "depth" variable
*    being added in my node structure. If I didn't need to sweep the entire tree due to a possible root replacement
*    in insertion/deletion/rotation imbalances, my implementation would be O(logn). This depth variable isn't necessary for
*    the implementation in any way, and kind of exists for no reason (it felt right at first when making the node,
*    but now is clearly only fluff unoptimizing code). So I'm marking all my time complexities as O(logn) where
*    applicable, pretending the depth variable doesn't exist and the operations specific to updating it don't exist.
* ------------------------------
*/

public class AVLLL<T extends Comparable<T>> {
  public AVLNode<T> root;
  
  public AVLLL() { // ------------------------------------------------------------------------------ O(1)
    root = null;
  }
  public AVLLL(T value) { // ----------------------------------------------------------------------- O(1)
    AVLNode<T> node = new AVLNode<T>(value);
    root = node;
  }

  /** Deletes the first occurrence (min depth) of a value from the BST using BST replacement logic to fill the missing node
  * @version 1.0.0
  * @since 1.0.0
  * @param value Value to delete
  */
  public void delete(T value) { // --------------------------------------------------------------------- O(logn)
    if (root == null)
      return;
    
    // First we must find the node, if it exists
    AVLNode<T> foundNode = null;
    AVLNode<T> prevNode = null;
    AVLNode<T> curNode = root;
    Object[] nodePath = new Object[root.height+1]; //+1 for 0 offset
    int lastIndexOfPathUsed = -1; //This variable represents the found node index of the nodePath array since it could be oversized
    Direction[] dirPath = new Direction[nodePath.length-1]; //Size is because direction represents transitions which has 1 less than elements
    int dirPathIndex = 0;
    while (curNode != null) { // ----------------------------------------------------------------------- O(logn)
      nodePath[curNode.depth] = curNode;
      if (value.compareTo(curNode.value) == 0) {
        foundNode = curNode;
        curNode = null;
        lastIndexOfPathUsed = foundNode.depth;
        break;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left != null) {
        prevNode = curNode;
        curNode = curNode.left;
        dirPath[dirPathIndex] = Direction.LEFT;
        dirPathIndex++;
        continue;
      } else if (value.compareTo(curNode.value) < 0 && curNode.left == null) {
        curNode = null;
        break;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right != null) {
        prevNode = curNode;
        curNode = curNode.right;
        dirPath[dirPathIndex] = Direction.RIGHT;
        dirPathIndex++;
        continue;
      } else if (value.compareTo(curNode.value) > 0 && curNode.right == null) {
        curNode = null;
        break;
      }
    }
    if (foundNode == null)
      return;
    // We have found the node. Now we operate on it.
    //Case 1: Found node has 0 children
      if (foundNode.left == null && foundNode.right == null) {
        if (prevNode.left == foundNode)
          prevNode.left = null; // Pray for garbage collector 🙏
        else if (prevNode.right == foundNode)
          prevNode.right = null; // Pray for garbage collector 🙏
        //Update array
        nodePath[lastIndexOfPathUsed] = null; //Set node in foundNode spot to null since removed from tree
        // return;
      }
    //Case 2: Found node has 1 child
      if ((foundNode.right == null && foundNode.left != null) &&
            (prevNode.left == foundNode)) { // Move found node left child to take spot as previous node's left child
        prevNode.left = foundNode.left; // Pray for garbage collector 🙏
        //Update array
        nodePath[lastIndexOfPathUsed] = foundNode.left; //Set node in foundNode spot to foundNode.left since took place in lineage
        // return;
      } else if ((foundNode.right == null && foundNode.left != null) &&
            (prevNode.right == foundNode)) { // Move found node left child to take spot as previous node's right child
        prevNode.right = foundNode.left; // Pray for garbage collector 🙏
        //Update array
        nodePath[lastIndexOfPathUsed] = foundNode.left; //Set node in foundNode spot to foundNode.left since took place in lineage
        // return;
      } else if ((foundNode.left == null && foundNode.right != null) &&
            (prevNode.left == foundNode)) { // Move found node right child to take spot as previous node's left child
        prevNode.left = foundNode.right; // Pray for garbage collector 🙏
        //Update array
        nodePath[lastIndexOfPathUsed] = foundNode.right; //Set node in foundNode spot to foundNode.right since took place in lineage
        // return;
      } else if ((foundNode.left == null && foundNode.right != null) &&
            (prevNode.right == foundNode)) { // Move found node right child to take spot as previous node's right child
        prevNode.right = foundNode.right; // Pray for garbage collector 🙏
        //Update array
        nodePath[lastIndexOfPathUsed] = foundNode.right; //Set node in foundNode spot to foundNode.right since took place in lineage
        // return;
      }
    //Case 3: Found node has 2 children
    if (foundNode.left != null && foundNode.right != null) {
      //Step 1: Find leftmost node to the found node's right branch
      AVLNode<T> prevLeftmostNode = null;
      AVLNode<T> leftmostNode = foundNode;
      while (leftmostNode != null) { // ----------------------------------------------------------------------- O(n)
        if (leftmostNode.left != null) {
          prevLeftmostNode = leftmostNode;
          leftmostNode = leftmostNode.left;
        } else
          break;
      }
      //Case 3.1: Leftmost node has 0 children (to the right)
      if (leftmostNode.right == null) {
        prevLeftmostNode.left = null; // Pray for garbage collector 🙏
        // return;
      }
      //Case 3.2: Leftmost node has 1 child to the right (it can't have a child to the left because it's leftmost)
      if (leftmostNode.right != null) {
        prevLeftmostNode.left = leftmostNode.right; // Pray for garbage collector 🙏
        // return;
      }
      //Set leftmostNode into foundNode spot
      if (prevNode.left == foundNode)
        prevNode.left = leftmostNode;
      else if (prevNode.right == foundNode)
        prevNode.right = leftmostNode;
      leftmostNode.left = foundNode.left;
      leftmostNode.right = foundNode.right;
      //Update array
      nodePath[lastIndexOfPathUsed] = leftmostNode; //Set node in foundNode spot to leftmostNode since took place in lineage
    }
    
    //Update heights through chain of nodes travelled bottom to top excluding foundNode bc 0
    for (int i = lastIndexOfPathUsed-1;i >= 0;i--) { // -------------------------------------------------------- O(n)
      if (nodePath[i] == null)
        continue;
      
      AVLNode<T> node = (AVLNode<T>) nodePath[i];
      updateHeight(node); // ----------------------------------------------------------------------------------- O(1)
    }
    //Update depths
    updateDepth(root, 0); // ----------------------------------------------------------------------------------- O(n)

    //Loop from bottom to top of chain, checking if rotation is needed at each step
    for (int i = lastIndexOfPathUsed;i >= 0;i--) {
      // System.out.println(i + ": " + nodePath[i]);
      checkForRotation(i, nodePath, dirPath);
    }
  }

  public void deleteTree() { // -------------------------------------------------------------------------------- O(1)
    root = null; // Pray for garbage collector 🙏
  }
  
  public AVLNode<T> insert(T value) { // ----------------------------------------------------------------------- O(logn)
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
    while (curNode != null) { // -------------------------------------------------------------------------------------- O(logn)
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
    for (int i = lastIndexOfPathUsed;i >= 0;i--) { // -------------------------------------------------------- O(logn)
      if (nodePath[i] == null)
        continue;
      
      AVLNode<T> node = (AVLNode<T>) nodePath[i];
      updateHeight(node); // --------------------------------------------------------------------------------- O(1)
    }

    //Step 3. Loop from bottom to top of chain, checking if rotation is needed at each step
    for (int i = lastIndexOfPathUsed-2;i >= 0;i--) { // ------------------------------------------------------ O(logn)
      checkForRotation(i, nodePath, dirPath); // ------------------------------------------------------------- O(1)
    }
    
    return newNode;
  }
  private void checkForRotation(int nodeIndex, Object[] nodePath, Direction[] dirPath) { // ----------------------------- O(1)
    AVLNode<T> node = (AVLNode<T>) nodePath[nodeIndex];
    if (node == null)
      return;
    //Step 1. Calculate balance
    int balance = getHeight(node.right) - getHeight(node.left); // ------------------------------------------------------ O(1)
    
    //Step 2. Handle Balance
    if (balance <= -2) {
      System.out.println("Balance is: " + balance);
      System.out.println("nodeIndex is: " + nodeIndex + " : " + node);
      for (int i = 0;i < (dirPath == null ? 0 : dirPath.length);i++) {
        System.out.println(i + ": " + (dirPath[i] != null ? dirPath[i].toString() : "NULL"));
      }
      //Find problematic grandchild
      int leftleftHeight = (node.left != null && node.left.left != null) ? node.left.left.height : -1;
      int leftrightHeight = (node.left != null && node.left.right != null) ? node.left.right.height : -1;
      if (leftleftHeight > leftrightHeight) {
        //Alter the tree lineage
        handleRotation_LL(nodeIndex, nodePath, dirPath); // -------------------------------------------------------------- O(1)
      } else if (leftrightHeight > leftleftHeight) {
        //Alter the tree lineage
        handleRotation_LR(nodeIndex, nodePath, dirPath); // -------------------------------------------------------------- O(1)
      }
    } else if (balance >= 2) {
      //Find problematic grandchild
      int rightleftHeight = (node.right != null && node.right.left != null) ? node.right.left.height : -1;
      int rightrightHeight = (node.right != null && node.right.right != null) ? node.right.right.height : -1;
      if (rightleftHeight > rightrightHeight) {
        //Alter the tree lineage
        handleRotation_RL(nodeIndex, nodePath, dirPath); // -------------------------------------------------------------- O(1)
        //Carry over alterations to nodePath and dirpath
        //TODO
      } else if (rightrightHeight > rightleftHeight) {
        //Alter the tree lineage
        handleRotation_RR(nodeIndex, nodePath, dirPath); // -------------------------------------------------------------- O(1)
      }
    }
  }
  private int getHeight(AVLNode<T> node) { // ---------------------------------------------------------------------------- O(1)
    if (node == null)
      return -1;
    return node.height;
  }
  private void handleRotation_LL(int nodeIndex, Object[] nodePath, Direction[] dirPath) { // ----------------------------- O(1)
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = (dirPath == null || nodeIndex-1 < 0) ? null : dirPath[nodeIndex-1];
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath == null ? null : dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) parentNode1.left; //This is the parent of the leaf
    Direction p0_c = dirPath == null ? null : dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) parentNode0.left; //This is the node added as a leaf
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
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree // ------------------ O(n) ignore
    else if (parentNode2 == null)
      updateDepth(parentNode0, 0); // Highest node runs recursively down tree // ---------------------------------- O(n) ignore
    updateHeight(newChildNode); updateHeight(parentNode1); //Layer 0 // ------------------------------------------- O(1)
    updateHeight(parentNode0); //Layer 1 // ----------------------------------------------------------------------- O(1)
    updateHeight(parentNode2); //Layer 2 // ----------------------------------------------------------------------- O(1)
    //Modify arrays
      //parentNode1 becomes parentNode0
    nodePath[nodeIndex-0] = parentNode0;
      //parentNode0 becomes newChildNode
    nodePath[nodeIndex+1] = newChildNode;
  }
  private void handleRotation_LR(int nodeIndex, Object[] nodePath, Direction[] dirPath) { // ----------------------------- O(1)
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = (dirPath == null || nodeIndex-1 < 0) ? null : dirPath[nodeIndex-1];
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath == null ? null : dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) parentNode1.left; //This is the parent of the leaf
    Direction p0_c = dirPath == null ? null : dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) parentNode0.right; //This is the node added as a leaf
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
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree // ---------------------- O(n) ignore
    else if (parentNode2 == null)
      updateDepth(newChildNode, 0); // Highest node runs recursively down tree // ------------------------------------- O(n) ignore
    updateHeight(parentNode0); updateHeight(parentNode1); //Layer 0 // ------------------------------------------------ O(1)
    updateHeight(newChildNode); //Layer 1 // -------------------------------------------------------------------------- O(1)
    updateHeight(parentNode2); //Layer 2 // --------------------------------------------------------------------------- O(1)
    //Modify arrays
      //parentNode1 becomes newChildNode
    nodePath[nodeIndex-0] = newChildNode;
  }
  private void handleRotation_RL(int nodeIndex, Object[] nodePath, Direction[] dirPath) { // ----------------------------- O(1)
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = (dirPath == null || nodeIndex-1 < 0) ? null : dirPath[nodeIndex-1];
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath == null ? null : dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) parentNode1.right; //This is the parent of the leaf
    Direction p0_c = dirPath == null ? null : dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) parentNode0.left; //This is the node added as a leaf
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
    parentNode1.right = newChildNode_left;
    //Handle Depths & Heights
    if (parentNode2 != null)
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree // ----------------------- O(n) ignore
    else if (parentNode2 == null)
      updateDepth(newChildNode, 0); // Highest node runs recursively down tree // -------------------------------------- O(n) ignore
    updateHeight(parentNode0); updateHeight(parentNode1); //Layer 0 // ------------------------------------------------- O(1)
    updateHeight(newChildNode); //Layer 1 // --------------------------------------------------------------------------- O(1)
    updateHeight(parentNode2); //Layer 2 // ---------------------------------------------------------------------------- O(1)
    //Modify arrays
      //parentNode1 becomes newChildNode
    nodePath[nodeIndex-0] = newChildNode;
  }
  private void handleRotation_RR(int nodeIndex, Object[] nodePath, Direction[] dirPath) { // ----------------------------- O(1)
    AVLNode<T> parentNode2 = (AVLNode<T>) (nodeIndex-1 >= 0 ? nodePath[nodeIndex-1] : null); //This is the parent of the parent of the parent of the leaf (or null if parentNode1 is root)
    Direction p2_p1 = (dirPath == null || nodeIndex-1 < 0) ? null : dirPath[nodeIndex-1];
    AVLNode<T> parentNode1 = (AVLNode<T>) nodePath[nodeIndex-0]; //This is the parent of the parent of the leaf
    Direction p1_p0 = dirPath == null ? null : dirPath[nodeIndex-0];
    AVLNode<T> parentNode0 = (AVLNode<T>) parentNode1.right; //This is the parent of the leaf
    Direction p0_c = dirPath == null ? null : dirPath[nodeIndex+1];
    AVLNode<T> newChildNode = (AVLNode<T>) parentNode0.right; //This is the node added as a leaf
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
      updateDepth(parentNode2, parentNode2.depth); // Highest node runs recursively down tree // ------------------------ O(n) ignore
    else if (parentNode2 == null)
      updateDepth(parentNode0, 0); // Highest node runs recursively down tree // ---------------------------------------- O(n) ignore
    updateHeight(newChildNode); updateHeight(parentNode1); //Layer 0 // ------------------------------------------------- O(1)
    updateHeight(parentNode0); //Layer 1 // ----------------------------------------------------------------------------- O(1)
    updateHeight(parentNode2); //Layer 2 // ----------------------------------------------------------------------------- O(1)
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
  public AVLNode<T> search(T value) { // ------------------------------------------------------------------- O(logn)
    if (root == null)
      return null;

    AVLNode<T> curNode = root;
    //Death to recursion! It is for apes!
    while (curNode != null) { // --------------------------------------------------------------------------- O(logn)
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

  private void updateHeight(AVLNode<T> node) { // --------------------------------------------------------------------- O(1)
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
  
  private void updateDepth(AVLNode<T> node, int specifiedNodeDepth) { // ---------------------------------------------- O(n) ignore
    /* Note: This method runs recursively unlike the updateHeight method due to the monodirectional reference structure
    */
    if (node == null)
      return;
    
    node.depth = specifiedNodeDepth;
    updateDepth(node.left, specifiedNodeDepth+1);
    updateDepth(node.right, specifiedNodeDepth+1);
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
    //Advanced method tests shifting further up tree than just @ newChild
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
  public static void Test_Insert_RLAdvanced() {
    //Advanced method tests shifting further up tree than just @ newChild
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(11, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(10, null, null, 1, 0);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(14, null, null, 1, 1);
    N_Root.right = N_R;
    AVLNode<Integer> N_RL = new AVLNode(13, null, null, 2, 0);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(16, null, null, 2, 0);
    N_R.right = N_RR;
    //Test behavior
    System.out.println(t);
    t.insert(12);
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
  public static void Test_Insert_LRAdvanced() {
    //Advanced method tests shifting further up tree than just @ newChild
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(11, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(8, null, null, 1, 1);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(12, null, null, 1, 0);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(6, null, null, 2, 0);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(9, null, null, 2, 0);
    N_L.right = N_LR;
    //Test behavior
    System.out.println(t);
    t.insert(10);
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
  public static void Test_Insert_RRAdvanced() {
    //Advanced method tests shifting further up tree than just @ newChild
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(7, null, null, 0, 2);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(5, null, null, 1, 0);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(9, null, null, 1, 0);
    N_Root.right = N_R;
    AVLNode<Integer> N_RL = new AVLNode(8, null, null, 2, 0);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(11, null, null, 2, 0);
    N_R.right = N_RR;
    //Test behavior
    System.out.println(t);
    t.insert(10);
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
  public static void Test_DeleteAdvanced() {
    //Preconfigure tree so adding will yield expected behavior
    AVLLL<Integer> t = new AVLLL<>();
    AVLNode<Integer> N_Root = new AVLNode(70, null, null, 0, 3);
    t.root = N_Root;
    AVLNode<Integer> N_L = new AVLNode(50, null, null, 1, 2);
    N_Root.left = N_L;
    AVLNode<Integer> N_R = new AVLNode(90, null, null, 1, 2);
    N_Root.right = N_R;
    AVLNode<Integer> N_LL = new AVLNode(30, null, null, 2, 1);
    N_L.left = N_LL;
    AVLNode<Integer> N_LR = new AVLNode(60, null, null, 2, 0);
    N_L.right = N_LR;
    AVLNode<Integer> N_RL = new AVLNode(80, null, null, 2, 1);
    N_R.left = N_RL;
    AVLNode<Integer> N_RR = new AVLNode(100, null, null, 2, 0);
    N_R.right = N_RR;
    AVLNode<Integer> N_LLL = new AVLNode(20, null, null, 3, 0);
    N_LL.left = N_LLL;
    AVLNode<Integer> N_LLR = new AVLNode(40, null, null, 3, 0);
    N_LL.right = N_LLR;
    AVLNode<Integer> N_RLR = new AVLNode(85, null, null, 3, 0);
    N_RL.right = N_RLR;
    //Test behavior
    //Easy delete first
    System.out.println(t);
    t.delete(40);
    System.out.println(t);
    //Success
    //--
    //LL delete
    t.delete(60);
    System.out.println(t);
    //Success
    //--
    //LR delete
    AVLLL<Integer> t2 = new AVLLL<>();
    AVLNode<Integer> N_Root2 = new AVLNode(70, null, null, 0, 3);
    t2.root = N_Root2;
    AVLNode<Integer> N_L2 = new AVLNode(50, null, null, 1, 2);
    N_Root2.left = N_L2;
    AVLNode<Integer> N_R2 = new AVLNode(90, null, null, 1, 2);
    N_Root2.right = N_R2;
    AVLNode<Integer> N_LL2 = new AVLNode(30, null, null, 2, 1);
    N_L2.left = N_LL2;
    AVLNode<Integer> N_LR2 = new AVLNode(60, null, null, 2, 0);
    N_L2.right = N_LR2;
    AVLNode<Integer> N_RL2 = new AVLNode(80, null, null, 2, 1);
    N_R2.left = N_RL2;
    AVLNode<Integer> N_RR2 = new AVLNode(100, null, null, 2, 0);
    N_R2.right = N_RR2;
    AVLNode<Integer> N_LLL2 = new AVLNode(20, null, null, 3, 0);
    N_LL2.left = N_LLL2;
    AVLNode<Integer> N_RLR2 = new AVLNode(85, null, null, 3, 0);
    N_RL2.right = N_RLR2;
    System.out.println(t2);
    t2.delete(100);
    System.out.println(t2);
    //Success
    //--
    //RR Delete + Duplicate value as node found in shift
    AVLLL<Integer> t3 = new AVLLL<>();
    AVLNode<Integer> N_Root3 = new AVLNode(70, null, null, 0, 3);
    t3.root = N_Root3;
    AVLNode<Integer> N_L3 = new AVLNode(50, null, null, 1, 2);
    N_Root3.left = N_L3;
    AVLNode<Integer> N_R3 = new AVLNode(90, null, null, 1, 2);
    N_Root3.right = N_R3;
    AVLNode<Integer> N_LL3 = new AVLNode(30, null, null, 2, 0);
    N_L3.left = N_LL3;
    AVLNode<Integer> N_LR3 = new AVLNode(60, null, null, 2, 1);
    N_L3.right = N_LR3;
    AVLNode<Integer> N_RL3 = new AVLNode(80, null, null, 2, 1);
    N_R3.left = N_RL3;
    AVLNode<Integer> N_RR3 = new AVLNode(100, null, null, 2, 0);
    N_R3.right = N_RR3;
    AVLNode<Integer> N_LRR3 = new AVLNode(70, null, null, 3, 0);
    N_LR3.right = N_LRR3;
    AVLNode<Integer> N_RLR3 = new AVLNode(85, null, null, 3, 0);
    N_RL3.right = N_RLR3;
    System.out.println(t3);
    t3.delete(30);
    System.out.println(t3);
    //Success
    //--
    //RL delete
    AVLLL<Integer> t4 = new AVLLL<>();
    AVLNode<Integer> N_Root4 = new AVLNode(70, null, null, 0, 3);
    t4.root = N_Root4;
    AVLNode<Integer> N_L4 = new AVLNode(50, null, null, 1, 2);
    N_Root4.left = N_L4;
    AVLNode<Integer> N_R4 = new AVLNode(90, null, null, 1, 2);
    N_Root4.right = N_R4;
    AVLNode<Integer> N_LL4 = new AVLNode(30, null, null, 2, 0);
    N_L4.left = N_LL4;
    AVLNode<Integer> N_LR4 = new AVLNode(60, null, null, 2, 1);
    N_L4.right = N_LR4;
    AVLNode<Integer> N_RL4 = new AVLNode(80, null, null, 2, 1);
    N_R4.left = N_RL4;
    AVLNode<Integer> N_RR4 = new AVLNode(100, null, null, 2, 0);
    N_R4.right = N_RR4;
    AVLNode<Integer> N_LRL4 = new AVLNode(55, null, null, 3, 0);
    N_LR4.left = N_LRL4;
    AVLNode<Integer> N_RLR4 = new AVLNode(85, null, null, 3, 0);
    N_RL4.right = N_RLR4;
    System.out.println(t4);
    t4.delete(30);
    System.out.println(t4);
    //Success
  }
}