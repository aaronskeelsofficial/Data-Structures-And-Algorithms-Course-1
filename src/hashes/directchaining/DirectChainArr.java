package src.hashes.directchaining;
import java.util.LinkedList;

/*------------------------------
* - Note from author:
*    I am following the general direction of the instructor by using an array of LinkedList<String> objects
*    as the hash values, however I am aware this is not the best because generics don't mix well with arrays.
*    I don't know all the details, but even a line like `hashTable = new LinkedList<>[size];` would break, as
*    an issue I've run into many times personally trying to implement generics myself for these projects,
*    due to type erasure post-compile. WHY. WHY DO YOU DO THIS JAVA? Long story short, with the slight
*    understanding I have literally as I type this (my knowledge is growing project by project of this
*    course), `LinkedList<String>[] hashTable;` should be `ArrayList<LinkedList<String>> hashTable;` as
*    ArrayLists are basically self-"growing" arrays that also support generics without crying and breaking
*    due to things that shouldn't break.
*
* - Note #2:
*    I get why we don't initialize LinkedLists for each value of the hash table immediately before we have
*    content for them under the lecturer's instruction, but I don't get why our deletion basically undoes
*    all the overhead of creating the LinkedList by setting the index to null if the list is empty after.
*    I feel like the code should be made more efficient by not simply checking if hashTable[hashIndex] == null
*    all the time and add backup optimization tolerances of also checking if hashTable[hashIndex].isEmpty()
*    and keeping the LinkedList in the hash table if it's already been created and the space allocated.
* ------------------------------
*/

public class DirectChainArr {
  public LinkedList<String>[] hashTable;
  public int MAXCHAINSIZE = 5;
  
  public DirectChainArr() {
    this(20);
  }
  public DirectChainArr(int size) {
    hashTable = new LinkedList[size];
  }

  public int ASCIIHashFunction(String s) {
    char c[] = s.toCharArray();
    int sum = 0;
    for(int i = 0;i < s.length();i++) {
      sum += c[i];
    }
    return sum % hashTable.length;
  }

  public void delete(String s) {
    int hashIndex = search(s);
    if (hashIndex != -1) {
      hashTable[hashIndex].remove(s);
      if (hashTable[hashIndex].isEmpty())
        hashTable[hashIndex] = null;
    }
  }

  public void insert(String s) {
    int hashIndex = ASCIIHashFunction(s);
    if (hashTable[hashIndex] == null) {
      hashTable[hashIndex] = new LinkedList<>();
      hashTable[hashIndex].add(s);
    } else {
      hashTable[hashIndex].add(s);
    }
  }

  public int search(String s) {
    int hashIndex = ASCIIHashFunction(s);
    if (hashTable[hashIndex] != null && hashTable[hashIndex].contains(s))
      return hashIndex;
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
    DirectChainArr h = new DirectChainArr(13);
    System.out.println(h);
    h.insert("test");
    h.insert("quick");
    h.insert("fox");
    System.out.println(h);
    //Success
  }
  public static void Test_Search() {
    DirectChainArr h = new DirectChainArr(13);
    h.insert("test");
    h.insert("quick");
    h.insert("fox");
    System.out.println(h.search("tes") + " : " + h.search("test") + " : " + h.search("fox"));
    //Success
  }
  public static void Test_Delete() {
    DirectChainArr h = new DirectChainArr(13);
    h.insert("test");
    h.insert("quick");
    h.insert("fox");
    System.out.println(h);
    h.delete("fox");
    System.out.println(h);
    h.delete("quick");
    System.out.println(h);
  }
  
}