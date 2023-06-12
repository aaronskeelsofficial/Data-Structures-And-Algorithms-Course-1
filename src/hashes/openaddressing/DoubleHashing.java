package src.hashes.openaddressing;
import java.util.ArrayList;

public class DoubleHashing {
  public String[] hashTable;
  public int usedCellNumber;
  public int largestPrimeSmallerThanTableSize = 3; // This is necessary for the second hash function not to possibly endlessly loop the same indices. Updated on each table resize.
  
  public DoubleHashing() {
    this(20);
  }
  public DoubleHashing(int size) {
    hashTable = new String[size];
    largestPrimeSmallerThanTableSize = getPrime();
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
  public int HashFunction2(String s) {
    //Note to self: In future, probably remove this duplicate hashing code prior to modulo
    // and place it somewhere so that it's only calculated once her insert attempt.
    char c[] = s.toCharArray();
    int sum = 0;
    for(int i = 0;i < s.length();i++) {
      sum += c[i];
    }
    return largestPrimeSmallerThanTableSize - (sum % largestPrimeSmallerThanTableSize);
  }
  public int HashFinalization(int hash1, int hash2, int i) {
    return hash1 + i * hash2;
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

  //getPrime code from https://www.geeksforgeeks.org/java-program-to-implement-hash-tables-with-double-hashing/
  //Yes, for ONCE in this project I swooped code. SUE ME. I didn't want to mess this method up doing it off the dome.
  public int getPrime()
  {
    // Iterating using for loop in reverse order
    for (int i = hashTable.length - 1; i >= 1; i--) {
      // Initially assigning count to zero
      int cnt = 0;
      // Now, iterating from 2 upto the desired number
      // to be checked by dividing it with all no
      // in between [2 - no]
      for (int j = 2; j * j <= i; j++) {
        // If number is divisible
        // means not a prime number
        if (i % j == 0) {
          // So simply move to next number
          // to check for divisibility by
          // incrementing the count variable
          cnt++;
          ////////I, Aaron Skeels, have modified this stolen code the next lines of this for loop. Why don't they break?
          j = i;
        }
      }
      // By now number is not divisible
      // hence count holds 0 till last
      if (cnt == 0)
        // It means it is a prime number so
        // return the number as it is a prime number
        return i;
    }
    // Returning a prime number
    return 3;
  }

  public void insert(String s) {
    double loadFactor = getLoadFactor();
    if (loadFactor >= 0.75) {
      System.out.println("Load factor exceeded. Rehashing...");
      rehash(s);
    } else {
      int hashIndex = ASCIIHashFunction(s);
      int tempIndex = hashIndex;
      if (hashTable[tempIndex] == null) {
        hashTable[tempIndex] = s;
        System.out.println(s + " has been inserted at " + hashIndex + ":" + tempIndex);
        usedCellNumber++;
        return;
      }
      int MAX_RUNS = 500;
      int secondaryHashIndex, finalHashIndex;
      for (int i = 1;i < MAX_RUNS;i++) {
        secondaryHashIndex = HashFunction2(2);
        finalHashIndex = HashFinalization(hashIndex, secondaryHashIndex, i);
        if (hashTable[finalHashIndex] == null) {
          hashTable[finalHashIndex] = s;
          System.out.println(s + " has been inserted at " + hashIndex + ":" + finalHashIndex + ":" + i);
          usedCellNumber++;
          return;
        }
      }
    }
  }

  public void rehash(String s) {
    ArrayList<String> data = new ArrayList<>();
    for (String str : hashTable) {
      if (str != null)
        data.add(str);
    }
    data.add(s);
    hashTable = new String[hashTable.length * 2];
    largestPrimeSmallerThanTableSize = getPrime();
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
    DoubleHashing h = new DoubleHashing(4);
    h.insert("111111");
    h.insert("222");
    h.insert("33");
    h.insert("6");
    System.out.println(h);
  }
  public static void Test_Delete() {
    DoubleHashing h = new DoubleHashing(4);
    h.insert("111111");
    h.insert("222");
    h.insert("33");
    h.insert("6");
    System.out.println(h);
    h.delete("6");
    System.out.println(h);
  }
  
}