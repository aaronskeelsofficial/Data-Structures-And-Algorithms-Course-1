package src.trees.binarysearchtreelinkedlist;

public class BSTNode<T> {
  public int depth;
  public T value;
  public BSTNode<T> left, right;
  
  public BSTNode() {
    this(null, null, null, 0);
  }
  public BSTNode(T value) {
    this(value, null, null, 0);
  }
  public BSTNode(T value, BSTNode<T> left, BSTNode<T> right, int depth) {
    this.value = value;
    this.left = left;
    this.right = right;
    this.depth = depth;
  }

  public String toString() {
    return "  ".repeat(depth) + value + ":D" + depth
        + ":L" + ((left == null) ? "" : left.value) + " R" + ((right == null) ? "" : right.value);
  }
}