package src.trees.avltreelinkedlist;

public class AVLNode<T> {
  public T value;
  public AVLNode<T> left, right;
  public int depth, height;
  
  public AVLNode() {
    this(null, null, null, 0, 0);
  }
  public AVLNode(T value) {
    this(value, null, null, 0, 0);
  }
  public AVLNode(T value, AVLNode<T> left, AVLNode<T> right, int depth, int height) {
    this.value = value;
    this.left = left;
    this.right = right;
    this.depth = depth;
    this.height = height;
  }

  public String toString() {
    return "  ".repeat(depth) + value + ":D" + depth + " H" + height
        + ":L" + ((left == null) ? "" : left.value) + " R" + ((right == null) ? "" : right.value);
  }
}