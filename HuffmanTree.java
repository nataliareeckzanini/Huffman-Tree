// Student: Natalia Reeck Zanini
// Course: CS 211
// Assignment #10
// June 10, 2022


// This is the HuffmanTree class for Assignment #10

// Import libraries
import java.util.*;
import java.io.*;

public class HuffmanTree {
   private HuffmanNode overallRoot;

   public HuffmanTree(Map<Character, Integer> counts) {
      PriorityQueue<HuffmanNode> curQueue = new PriorityQueue<HuffmanNode>();
      for (Character curChar : counts.keySet()) {
         HuffmanNode curNode = new HuffmanNode(counts.get(curChar), curChar, null, null);
         curQueue.add(curNode);
      }
      while(curQueue.size() > 1) {
         HuffmanNode node1 = curQueue.poll();
         HuffmanNode node2 = curQueue.poll();
         HuffmanNode newNode = new HuffmanNode(node1.frequency + node2.frequency, node1, node2);
         curQueue.add(newNode);
      }
      overallRoot = curQueue.poll();
   }
   
   public StringBuilder compress(InputStream inputFile) {
      Map<Character, String> charEncodings = new HashMap<Character, String>();
      mapEncodings(overallRoot, charEncodings, "");
      StringBuilder result = new StringBuilder();
      try{
         int curByte = inputFile.read();
         while(curByte != -1) {
            char curChar = (char) curByte;
            result.append(charEncodings.get(curChar));
            curByte = inputFile.read();
         }
         StringBuilder append = result.append(charEncodings.get(HuffmanNode.END_OF_FILE_CHAR));
         return result;
      }catch (IOException e) {
         System.out.println("Bad File");
         return null;
      }
   }
   
// Fill charEncodings with leaf nodes in the subtree starting at root:
   private void mapEncodings(HuffmanNode root, Map<Character, String> charEncodings, String pathString) {
      if(root.isLeaf()){
         charEncodings.put(root.character, pathString);
      }else{
         mapEncodings(root.left, charEncodings, pathString + "0");
         mapEncodings(root.right, charEncodings, pathString + "1");
      }
   }
   
   public StringBuilder decompress(StringBuilder inputString) {
      String inputS = inputString.toString();
      int[] i = new int[1];
      StringBuilder result = new StringBuilder();
      while(i[0] < inputS.length()) {
         char curChar = findCharacter(overallRoot, inputS, i); 
         if(curChar != HuffmanNode.END_OF_FILE_CHAR) {
            result.append(curChar);
         }
      }
      return result;
   }
   
// Returns the character associated with the bits in input starting at i:
   private char findCharacter(HuffmanNode root, String input, int[] i) {
      if(root.isLeaf()){
         return root.character;
      }
      i[0]++;
      if(input.charAt(i[0]-1) == '0') {
         return findCharacter(root.left, input, i);
      }else{
         return findCharacter(root.right, input, i);
      }
   }
   
   public String printSideways() {
      return printSideways(overallRoot, "").toString();
   }
   
   private StringBuilder printSideways(HuffmanNode root, String indent) {
      if(root == null) {
         return new StringBuilder();
      }else {
         StringBuilder result = printSideways(root.right, indent + "      ");
         result.append(indent + root.frequency + "=");
         if(root.isLeaf()){
            result.append("char(" + (int) root.character + ") \r\n");
         }else{
            result.append("count \r\n");
         }
         result.append("\n");
         result.append(printSideways(root.left, indent + "      "));
         return result;
      }
   
   }

}