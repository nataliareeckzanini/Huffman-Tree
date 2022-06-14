// Student: Natalia Reeck Zanini
// Course: CS 211
// Assignment #10
// June 10, 2022


// This is the HuffmanNode class for Assignment #10

// Importing Libraries:
import java.io.*;
import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
	public static final char END_OF_FILE_CHAR = (char) 256;

	// Public fields
   public int frequency;
   public char character;
   public HuffmanNode left;
   public HuffmanNode right;
   
   // Constructs frequency of characters, and nodes that make up the HuffmanTree.
   public HuffmanNode(int frequency, char character, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.character = character;
      this.left = left;
      this.right = right;
   }
   
   public HuffmanNode(char character) {
      this(-1, character, null, null);
   }
   
   public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
      this(frequency, ' ', left, right);
   }
   
// Returns whether a node is a leaf node or not.
   public boolean isLeaf() {
      return left == null && right == null;
   }
   
   public int compareTo(HuffmanNode other) {
      return frequency - other.frequency;
   }

//Returns a map that counts the number of appearances for each character.
   public static Map<Character, Integer> getCounts(FileInputStream input) {
      Map<Character, Integer> result = new HashMap<Character, Integer> ();
      try{
         int curByte = input.read();
         while(curByte != -1) {
            char curChar = (char) curByte;
            if(result.containsKey(curChar)) {
               result.put(curChar, result.get(curChar) + 1);
            }else{
               result.put(curChar, 1);
            }
            curByte = input.read();
         }
         
         result.put((char) 256, 1);
         return result;
      }catch (IOException e) {
         System.out.println("Bad File");
         return null;
      }
   }
   
   public String toString() {
	   return frequency + ","  + character;
   }
}