package src.graphs.array;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.StringBuilder;
// import src.queus.QueueSinglyLinkedList; //I can not use my old queue class because I made it before I learned generics 😢
import java.util.LinkedList;
import java.util.Stack;

/*------------------------------
* - Note from author:
*    I am aware my current implementation uses GraphNode.isVisited to PERMANENTLY mark a node as visited
*    and that makes the graph basically corrupt for any more than a singular traversal/sorting. I only
*    did this because it's how the course did it. I acknowledge it is BAD.
*
*    I am sure that this class is missing very basic functionality associated with such a type of object.
*    I only was following the functionality asked for by the course, and not adventuring further.
* ------------------------------
*/

public class GraphArr {
  ArrayList<GraphNode> nodeList;
  int[][] adjacencyMatrix;
  
  public GraphArr(ArrayList<GraphNode> nodeList) {
    this.nodeList = nodeList;
    adjacencyMatrix = new int[nodeList.size()][nodeList.size()];
  }

  public void addDirectedEdge(int fromI, int toI) {
    adjacencyMatrix[fromI][toI] = 1;
  }
  
  public void addUndirectedEdge(int fromI, int toI) {
    adjacencyMatrix[fromI][toI] = 1;
    adjacencyMatrix[toI][fromI] = 1;
  }

  public void BFSTraverse(GraphNode node) {
    LinkedList<GraphNode> q = new LinkedList<GraphNode>();
    q.add(node);
    node.isVisited = true;
    while (!q.isEmpty()) {
      GraphNode curNode = q.remove(0);
      // curNode.isVisited = true; //This was present in course but unnecessary
      System.out.print(curNode.name + " ");
      ArrayList<GraphNode> neighbors = getNeighbors(curNode);
      for (GraphNode neighbor : neighbors) {
        if (!neighbor.isVisited) {
          q.add(neighbor);
          neighbor.isVisited = true;
        }
      }
    }
  }

  public void DFSTraverse(GraphNode node) {
    Stack<GraphNode> s = new Stack<GraphNode>();
    s.push(node);
    node.isVisited = true;
    while (!s.isEmpty()) {
      GraphNode curNode = s.pop();
      // curNode.isVisited = true; //This was present in course but unnecessary
      System.out.print(curNode.name + " ");
      ArrayList<GraphNode> neighbors = getNeighbors(curNode);
      for (GraphNode neighbor : neighbors) {
        if (!neighbor.isVisited) {
          s.push(neighbor);
          neighbor.isVisited = true;
        }
      }
    }
  }

  private ArrayList<GraphNode> getNeighbors(GraphNode node) {
    ArrayList<GraphNode> neighbors = new ArrayList<GraphNode>();
    int nodeIndex = node.index;
    for (int i = 0;i < adjacencyMatrix[nodeIndex].length;i++) {
      if (adjacencyMatrix[nodeIndex][i] == 1) {
        neighbors.add(nodeList.get(i));
      }
    }
    return neighbors;
  }
  
  private ArrayList<GraphNode> getUnvisitedNeighbors(GraphNode node) {
    ArrayList<GraphNode> neighbors = new ArrayList<GraphNode>();
    int nodeIndex = node.index;
    for (int i = 0;i < adjacencyMatrix[nodeIndex].length;i++) {
      if (adjacencyMatrix[nodeIndex][i] == 1 && !nodeList.get(i).isVisited) {
        neighbors.add(nodeList.get(i));
      }
    }
    return neighbors;
  }

  public void SSSPP_PrintPath(GraphNode node) {
    // Recursion, ew
    if (node.parent != null)
      SSSPP_PrintPath(node.parent);
    System.out.print(node.name + " ");
  }

  public void SSSPP_BFS(GraphNode node) {
    LinkedList<GraphNode> q = new LinkedList<GraphNode>();
    q.add(node);
    node.isVisited = true;
    while (!q.isEmpty()) {
      GraphNode curNode = q.remove(0);
      System.out.print("Printing path from " + node.name + " -> " + curNode.name + ": ");
      SSSPP_PrintPath(curNode);
      System.out.println("");
      ArrayList<GraphNode> neighbors = getNeighbors(curNode);
      for (GraphNode neighbor : neighbors) {
        if (!neighbor.isVisited) {
          q.add(neighbor);
          neighbor.isVisited = true;
          neighbor.parent = curNode;
        }
      }
    }
  }

  public void TopologicalTraverse(GraphNode node) {
    /** Completely strayed from course content here to stick to pattern of BFS/DFS prior
    *    Custom coding this off the top of my head from scratch
    *
    *   Update: It has come to my attention that this traversal code I have concocted misses
    *    nodes in the tree. Below I'll show my test tree structure in ASCII and explain what
    *    is happening (assume left-to-right connections for diagonals).
    *   Example Tree: A -> C -> E -> H
                          /      \
                      B---        F -> G
                          \      /
                           D ----
    *   So the problem is if I start from A, I forward track DFS from A forwards down the tree
    *    and the sort works as expected, but node B and D (which are entirely disjointed from
    *    the A chain) are left out. I tried searching online and asking ChatGPT about this, but
    *    ultimately found no information. I get topological is meant to be a *sort* and not a
    *    *traversal* method so maybe that discrepency is the cause of confusion here. It's clear
    *    to me in a true topological sort of an ENTIRE graph, you'd just loop through all the
    *    nodes and restart your DFS from each indegree 0 vertex. With that understanding, I'm
    *    going to put my efforts to dig deeper into this issue to rest and just accept my
    *    "topological traverse" idea isn't a real thing and that's the cause of the discrepency
    *    and if I wanted to convert it to a topological sort, I could...
    *    *if I wanted to* (which I don't).
    */
    // Logic Step 1. Loop through root neighbors DFS
    Stack<GraphNode> DFSStack = new Stack<GraphNode>();
    Stack<GraphNode> sortedStack = new Stack<GraphNode>();
    DFSStack.push(node);
    node.isVisited = true;
    while (!DFSStack.isEmpty()) {
      // Logic Step 2. If child node has no neighbors, add it to sorted, if it does continue DFS
      GraphNode curNode = DFSStack.peek();
      ArrayList<GraphNode> neighbors = getUnvisitedNeighbors(curNode);
      if (neighbors.isEmpty()) {
        DFSStack.pop();
        // System.out.print(curNode.name + " ");
        sortedStack.push(curNode);
      } else {
        for (GraphNode neighbor : neighbors) {
          if (!neighbor.isVisited) {
            DFSStack.push(neighbor);
            neighbor.isVisited = true;
          }
        }
      }
    }
    GraphNode n;
    while (!sortedStack.isEmpty()) {
      n = sortedStack.pop();
      System.out.print(n.name + " ");
    }
  }

  // This was my bare bones first attempt, but I see the course toString function is much nicer so I'm stealing it
  public String toString_Ghetto() {
    String str = "---\n";
    for(int i = 0;i < adjacencyMatrix.length;i++) {
      str += Arrays.toString(adjacencyMatrix[i]) + "\n";
    }
    str += "---\n";
    return str;
  }

  // Nicer looking toString stolen from class. Note: Only works if node "string" is *actually* a single character
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("   ");
    for (int i = 0; i < nodeList.size(); i++) {
      s.append(nodeList.get(i).name + " ");
    }
    s.append("\n");
    for (int i = 0; i < nodeList.size(); i++) {
      s.append(nodeList.get(i).name + ": ");
      for (int j : adjacencyMatrix[i]) {
        s.append((j) + " ");
      }
      s.append("\n");
    }
    return s.toString();
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_AddUndirectedEdge() {
    ArrayList<GraphNode> l = new ArrayList<>();
    l.add(new GraphNode("A", 0));
    l.add(new GraphNode("B", 1));
    l.add(new GraphNode("C", 2));
    l.add(new GraphNode("D", 3));
    l.add(new GraphNode("E", 4));
    GraphArr g = new GraphArr(l);
    g.addUndirectedEdge(0,1);
    g.addUndirectedEdge(0,2);
    g.addUndirectedEdge(2,4);
    System.out.println(g);
  }
  public static void Test_BFSTraverse() {
    ArrayList<GraphNode> l = new ArrayList<>();
    l.add(new GraphNode("A", 0));
    l.add(new GraphNode("B", 1));
    l.add(new GraphNode("C", 2));
    l.add(new GraphNode("D", 3));
    l.add(new GraphNode("E", 4));
    GraphArr g = new GraphArr(l);
    g.addUndirectedEdge(0,1);
    g.addUndirectedEdge(0,2);
    g.addUndirectedEdge(2,4);
    g.addUndirectedEdge(4,3);
    g.BFSTraverse(g.nodeList.get(0));
  }
  public static void Test_DFSTraverse() {
    ArrayList<GraphNode> l = new ArrayList<>();
    l.add(new GraphNode("A", 0));
    l.add(new GraphNode("B", 1));
    l.add(new GraphNode("C", 2));
    l.add(new GraphNode("D", 3));
    l.add(new GraphNode("E", 4));
    GraphArr g = new GraphArr(l);
    g.addUndirectedEdge(0,1);
    g.addUndirectedEdge(0,2);
    g.addUndirectedEdge(2,4);
    g.addUndirectedEdge(4,3);
    g.DFSTraverse(g.nodeList.get(0));
  }
  public static void Test_TopologicalTraverse() {
    ArrayList<GraphNode> l = new ArrayList<>();
    l.add(new GraphNode("A", 0));
    l.add(new GraphNode("B", 1));
    l.add(new GraphNode("C", 2));
    l.add(new GraphNode("D", 3));
    l.add(new GraphNode("E", 4));
    l.add(new GraphNode("F", 5));
    l.add(new GraphNode("G", 6));
    l.add(new GraphNode("H", 7));
    GraphArr g = new GraphArr(l);
    g.addDirectedEdge(0,2);
    g.addDirectedEdge(1,2);
    g.addDirectedEdge(1,3);
    g.addDirectedEdge(2,4);
    g.addDirectedEdge(3,5);
    g.addDirectedEdge(4,7);
    g.addDirectedEdge(4,5);
    g.addDirectedEdge(5,6);
    g.TopologicalTraverse(g.nodeList.get(0));
    //Success, kinda, comments in TopologicalTraverse Method
  }
  public static void Test_SSSPPBFS() {
    ArrayList<GraphNode> l = new ArrayList<>();
    l.add(new GraphNode("A", 0));
    l.add(new GraphNode("B", 1));
    l.add(new GraphNode("C", 2));
    l.add(new GraphNode("D", 3));
    l.add(new GraphNode("E", 4));
    l.add(new GraphNode("F", 5));
    l.add(new GraphNode("G", 6));
    l.add(new GraphNode("H", 7));
    GraphArr g = new GraphArr(l);
    g.addDirectedEdge(0,2);
    g.addDirectedEdge(1,2);
    g.addDirectedEdge(1,3);
    g.addDirectedEdge(2,4);
    g.addDirectedEdge(3,5);
    g.addDirectedEdge(4,7);
    g.addDirectedEdge(4,5);
    g.addDirectedEdge(5,6);
    g.SSSPP_BFS(g.nodeList.get(0));
    //success
  }
  
}