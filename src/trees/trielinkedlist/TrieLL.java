package src.trees.trielinkedlist;
import java.util.ArrayList;

public class TrieLL {
  private TrieNode root;

  public TrieLL() {
    root = new TrieNode();
  }

  public void delete(String s) {
    // Step 1. Step through nodes until end of string
    ArrayList<TrieNode> nodeTrail = new ArrayList<>();
    TrieNode curNode = root;
    nodeTrail.add(curNode); // -------------------------------------------------------------------------------------- ~O(1)
    int stringLength = s.length(); // Separated this from for loop for time complexity annotation -------------------- O(1)
    for (int i = 0;i < stringLength;i++) {
      char c = s.charAt(i); // --------------------------------------------------------------------------------------- O(1)
      if(curNode.children.containsKey(c)) { // ----------------------------------------------------------------------- O(1)
        curNode = curNode.children.get(c); // ----------------------------------------------------------------------- ~O(1)
        nodeTrail.add(curNode); // ---------------------------------------------------------------------------------- ~O(1)
        continue;
      } else {
        return;
      }
    }
    if (!curNode.endOfString)
      return;
    // Step 2. Make last node not endOfString
    curNode.endOfString = false;
    // Step 3. Reverse backwards through node chain deleting each node which doesn't have any other children until children are present
    int nodeTrailLength = nodeTrail.size(); // Separated this from for loop for time complexity annotation ----------- O(1)
    TrieNode parentNode = null;
    for (int i = nodeTrailLength-1;i >= 0;i--) {
      curNode = nodeTrail.get(i);
      parentNode = (i != 0) ? nodeTrail.get(i-1) : null; // ---------------------------------------------------------- O(1)
      if (parentNode == null) { // This means curNode is root
        return;
      }
      if (curNode.children.isEmpty() && !curNode.endOfString) {
        //If this node has no children, remove it from its parent's children references
        char charToRemoveFromChildren = s.charAt(i-1); // ------------------------------------------------------------ O(1)
        parentNode.children.remove(charToRemoveFromChildren); // Pray for garbage collector
        continue;
      } else
        return;
    }
  }

  public TrieNode insert(String s) {
    // Step 1. Loop through string, adding if necessary
    TrieNode curNode = root;
    int stringLength = s.length(); // Separated this from for loop for time complexity annotation -------------------- O(1)
    for (int i = 0;i < stringLength;i++) {
      char c = s.charAt(i); // --------------------------------------------------------------------------------------- O(1)
      if(curNode.children.containsKey(c)) { // ----------------------------------------------------------------------- O(1)
        curNode = curNode.children.get(c); // ----------------------------------------------------------------------- ~O(1)
        continue;
      } else {
        TrieNode newNode = new TrieNode();
        if (i == stringLength - 1)
          newNode.endOfString = true;
        curNode.children.put(c, newNode); // ------------------------------------------------------------------------ ~O(1)
        curNode = newNode;
        continue;
      }
    }
    return curNode;
  }

  public boolean searchExact(String s) {
    TrieNode curNode = root;
    int stringLength = s.length(); // Separated this from for loop for time complexity annotation -------------------- O(1)
    for (int i = 0;i < stringLength;i++) {
      char c = s.charAt(i); // --------------------------------------------------------------------------------------- O(1)
      if (curNode.children.containsKey(c) && i < stringLength-1) { // ------------------------------------------------ O(1)
        curNode = curNode.children.get(c); // ----------------------------------------------------------------------- ~O(1)
        continue;
      } else if (curNode.children.containsKey(c) && i == stringLength-1) { // ---------------------------------------- O(1)
        if (curNode.children.get(c).endOfString) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }
    return false;
  }

  public String toString() {
    if (root.children.isEmpty())
      return "_EMPTY_";
    return toString(root, 0);
  }
  public String toString(TrieNode node, int depth) {
    if (node == null)
      return "Null Node";
    if (node.children.isEmpty())
      return "_EMPTYCHILD_ " + depth;
    
    String str = "";
    for (Character c : node.children.keySet()) {
      str += "  ".repeat(depth) + c + (node.children.get(c).endOfString ? "." : "") + "\n";
      TrieNode childNode = node.children.get(c);
      str += (!childNode.children.isEmpty()) ? (toString(childNode, depth+1)) : ("");
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
    TrieLL t = new TrieLL();
    System.out.println(t);
    t.insert("air");
    System.out.println(t);
    t.insert("aim");
    System.out.println(t);
    t.insert("art");
    System.out.println(t);
    t.insert("arts");
    System.out.println(t);
    t.insert("arts");
    System.out.println(t);
    //Success
  }
  public static void Test_SearchExact() {
    TrieLL t = new TrieLL();
    System.out.println(t);
    t.insert("air");
    System.out.println(t);
    System.out.println("Search a: " + t.searchExact("a"));
    System.out.println("Search ai: " + t.searchExact("ai"));
    System.out.println("Search air: " + t.searchExact("air"));
    //Success
  }
  public static void Test_Delete() {
    //First test how it behaves with overlapping words
    TrieLL t = new TrieLL();
    System.out.println(t);
    t.insert("art");
    t.insert("arts");
    t.insert("air");
    System.out.println(t);
    t.delete("art");
    System.out.println(t);
    System.out.println("--");
    //Success
    //Now test it's ability to destroy chain if necessary
    TrieLL t2 = new TrieLL();
    t2.insert("arts");
    System.out.println(t2);
    t2.delete("arts");
    System.out.println(t2);
    System.out.println("--");
    //Success
    //Now test a super complicated one idk what else could break that's why we test
    TrieLL t3 = new TrieLL();
    t3.insert("art");
    t3.insert("arts");
    t3.insert("air");
    t3.insert("brt");
    System.out.println(t3);
    t3.delete("arts");
    System.out.println(t3);
    //Oop this actually broke my implementation. I told you testing is important. The problem is I only
    // currently check if the node has children for wipe conditions, ignoring if "endOfString" is present.
    //Okay it works now, but you wouldn't know it didn't work running this back. But it did break, but no longer >:)
    //Success
  }
  
}