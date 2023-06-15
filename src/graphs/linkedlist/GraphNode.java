package src.graphs.linkedlist;
import java.util.ArrayList;

public class GraphNode {
  public String name;
  public int index;
  public ArrayList<GraphNode> neighbors;
  public boolean isVisited;
  public GraphNode parent;

  public GraphNode(String name, int index) {
    this.name = name;
    this.index = index;
    this.neighbors = new ArrayList<GraphNode>();
    this.isVisited = false;
    this.parent = null;
  }
  
}