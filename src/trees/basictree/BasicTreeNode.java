package src.trees.basictree;
import java.util.ArrayList;

/*------------------------------
* - Note from author:
*    I am sure that this class is missing very basic functionality associated with such a type of object.
*    I only was following the functionality asked for by the course, and not adventuring further.
* ------------------------------
*/

/** Represents a basic tree node structure
* @author Aaron Skeels
* @author aaronskeels.work/
* @version 1.0.0
*/
public class BasicTreeNode {
  String data;
  ArrayList<BasicTreeNode> children;

  /** Initializing constructor
  * @version 1.0.0
  * @since 1.0.0
  * @param data Data to be stored in current basic tree node
  */
  public BasicTreeNode(String data) { // ---------------------------------------------------------------------- O(1)
    this.data = data;
    this.children = new ArrayList<BasicTreeNode>(); // -------------------------------------------------------- O(1)?
  }

  /** Adds a child node to parent node
  * @version 1.0.0
  * @since 1.0.0
  * @param node child node to be added
  */
  public void addChild(BasicTreeNode node) { // --------------------------------------------------------------- O(1)
    this.children.add(node); // ------------------------------------------------------------------------------- O(1)?
  }

  /** Turns object into viewable string
  * @version 1.0.0
  * @since 1.0.0
  * @param level Metaphorical level which represents spacing to begin with
  * @return Visual representation of parent and following children nodes
  */
  public String printFromLvl(int level) {
    String str;
    str = "  ".repeat(level) + data + "\n";
    for (BasicTreeNode node : this.children) {
      str += node.printFromLvl(level + 1);
    }
    return str;
  }

  /** Generic toString method
  * @version 1.0.0
  * @since 1.0.0
  * @return Visual representation of parent and following children nodes
  */
  public String toString() {
    return printFromLvl(0);
  }




  
  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_TestOne() {
    BasicTreeNode drinks = new BasicTreeNode("Drinks");
    BasicTreeNode hot = new BasicTreeNode("Hot");
    drinks.addChild(hot);
    BasicTreeNode tea = new BasicTreeNode("Tea");
    hot.addChild(tea);
    BasicTreeNode coffee = new BasicTreeNode("Coffee");
    hot.addChild(coffee);
    BasicTreeNode cold = new BasicTreeNode("Cold");
    drinks.addChild(cold);
    BasicTreeNode wine = new BasicTreeNode("Wine");
    cold.addChild(wine);
    BasicTreeNode beer = new BasicTreeNode("Beer");
    cold.addChild(beer);
    System.out.println(drinks);
  }
}