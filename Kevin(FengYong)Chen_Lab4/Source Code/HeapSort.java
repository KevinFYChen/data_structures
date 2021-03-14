/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *This module takes an array of integers and sort it in ascending order using
 *a heap sort. The codes were mostly copied from:
 * https://algorithms.tutorialhorizon.com/heap-sort-java-implementation/
 * 
 * But I have converted the algorithm from recursion to iteration to meet 
 * the requirement of this assignment. 
 * 
 * This module contains 2 methods:
 * - Heapify
 * - Sort 
 * 
 * The heapify operation is used to convert an array into a heap. It does so by
 * ensuring that all nodes in the tree satisfy the property of a max heap.
 * Namely, the value of a node must not be smaller than any of its children. 
 * 
 * The sort method performs the actual heap sort. It does so by repeatedly 
 * removing the root of a max heap and build a sorted array in reverse order.
 * 
 * 
 * @author kevinchen
 */
public class HeapSort {

   /**
    * This method takes an array of integers and sort it in ascending order
    * using a heap sort. A heap sort contains 2 phases: 
    * 1) turning the array into a max heap.
    * 2) build a sorted array by repeated removing the root of the max heap, 
    *    thus building a sorted array in reverse order.
    * 
    * @param array array of integers that need to be sorted. 
    */
   
   public static void sort(int[] array) {
      int arraySize = array.length;
      // Build heap using the heapify operation. Starting from the largest
      // internal node of the tree at position (arraySize/2)-1
      for (int i = arraySize / 2 - 1; i >= 0; i--) {
         heapify(array, arraySize, i);
      }
      
      // One by one extract the root from heap and
      // swap it with the ith element in the array
      for (int i = arraySize - 1; i >= 0; i--) {
         //array[0] is the root of a max heap and it is the largest element
         //in a max heap
         int temp = array[0];
         array[0] = array[i];
         array[i] = temp;    
         
         // call the heapify method again to ensure that the new root 
         // satisfies the property of a max heap. 
         heapify(array, i, 0);
      }

   }

   /**
    * This method ensures that a subtree satisfies the property of a max heap.
    * Namely, a node must not be smaller than any of its children. It first 
    * start with the element at position i of the array. If the node is 
    * smaller than any of its children, then i is swapped with the largest of 
    * its 2 children. The process is then repeated on the child node that 
    * was swapped to make sure that it satisfies the max heap property. 
    * 
    * @param array an array of integers that need to be sorted
    * @param arraySize size of the array
    * @param i starting point in the array to perform the heapify operation.
    */
   public static void heapify(int[] array, int arraySize, int i) {

      while (true) {
         int largest = i; //initialize largest as root
         int leftIndex = 2 * i + 1; // left child
         int rightIndex = 2 * i + 2; // right child

         // if left child is larger than element on index i
         if (leftIndex < arraySize && array[leftIndex] > array[largest]) {
            largest = leftIndex;
         }
         
         // if right child is larger than largest so far
         if (rightIndex < arraySize && array[rightIndex] > array[largest]) {
            largest = rightIndex;
         }

         //if any of the children is larger than the parent, then swap 
         // the child with the parent
         if (largest != i) {
            int temp = array[i];
            array[i] = array[largest];
            array[largest] = temp;

            i = largest; // heapify the child node
         } else {
            break;
         }
      }
   }
}
