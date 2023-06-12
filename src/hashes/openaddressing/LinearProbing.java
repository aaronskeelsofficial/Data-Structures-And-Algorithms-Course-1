package src.hashes.openaddressing;
import java.util.ArrayList;

public class LinearProbing {
  public String[] hashTable;
  public int usedCellNumber;
  
  public LinearProbing() {
    this(20);
  }
  public LinearProbing(int size) {
    hashTable = new String[size];
    usedCellNumber = 0;
  }

  public int ASCIIHashFunction(String s) {
    return ASCIIHashFunction(s, hashTable.length);
  }
  public int ASCIIHashFunction(String s, int assumedHashTableSize) {
    char c[] = s.toCharArray();
    int sum = 0;
    for(int i = 0;i < s.length();i++) {
      sum += c[i];
    }
    return sum % assumedHashTableSize;
  }

  public void delete(String s) {
    int hashIndex = ASCIIHashFunction(s);
    for (int i = hashIndex;i < hashIndex + hashTable.length;i++) {
      int tempIndex = i % hashTable.length;
      if (hashTable[tempIndex] != null && hashTable[tempIndex] == s) {
        hashTable[tempIndex] = null;
        return;
      }
    }
  }

  public double getLoadFactor() {
    double loadFactor = ((double) usedCellNumber) * 1.0d / ((double)hashTable.length);
    System.out.println("Load Factor Calculated: " + loadFactor);
    return loadFactor;
  }

  public void insert(String s) {
    double loadFactor = getLoadFactor();
    if (loadFactor >= 0.75) {
      System.out.println("Load factor exceeded. Rehashing...");
      rehash(s);
    } else {
      int hashIndex = ASCIIHashFunction(s);
      for (int i = hashIndex;hashIndex < hashIndex + hashTable.length;i++) {
        int tempIndex = i % hashTable.length;
        if (hashTable[tempIndex] == null) {
          hashTable[tempIndex] = s;
          System.out.println(s + " has been inserted at " + hashIndex + ":" + tempIndex);
          break;
        }
      }
    }
    usedCellNumber++;
  }

  public void rehash(String s) {
    ArrayList<String> data = new ArrayList<>();
    for (String str : hashTable) {
      if (str != null)
        data.add(str);
    }
    data.add(s);
    hashTable = new String[hashTable.length * 2];
    usedCellNumber = 0;
    for (String str : data)
      insert(str);
  }

  public int search(String s) {
    int hashIndex = ASCIIHashFunction(s);
    for (int i = hashIndex;i < hashIndex + hashTable.length;i++) {
      int tempIndex = i % hashTable.length;
      if (hashTable[tempIndex] != null && hashTable[tempIndex] == s) {
        return tempIndex;
      }
    }
    return -1;
  }

  public String toString() {
    String str = "";
    for (int i = 0;i < hashTable.length;i++) {
      if (hashTable[i] != null) {
        str += i + ": " + hashTable[i] + "\n";
      }
    }
    return (str != "") ? str : "_EMPTY_";
  }





  /* --------------------------------------------------
  *  - TEST METHODS
  *  - Purpose: All following methods were used to test functionality implementations.
  *     They will have no documentation. Probably ignore.
  *  --------------------------------------------------
  */
  public static void Test_Insert() {
    LinearProbing h = new LinearProbing(4);
    h.insert("111111");
    h.insert("222");
    h.insert("33");
    h.insert("6");
    System.out.println(h);
  }
  public static void Test_Delete() {
    LinearProbing h = new LinearProbing(4);
    h.insert("111111");
    h.insert("222");
    h.insert("33");
    h.insert("6");
    System.out.println(h);
    h.delete("6");
    System.out.println(h);
  }
  
}