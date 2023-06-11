package src.trees.trielinkedlist;
import java.util.HashMap;

public class TrieNode {
  public HashMap<Character, TrieNode> children;
  public boolean endOfString;

  public TrieNode() {
    children = new HashMap<Character, TrieNode>();
    endOfString = false;
  }
}