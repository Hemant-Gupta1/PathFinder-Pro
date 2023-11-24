package TechSarathiInternship_MapNav;

import java.util.ArrayList;
import java.util.HashMap;

// Generic Heap class with elements of type T (T must be Comparable)
public class Heap<T extends Comparable<T>> {
    // ArrayList to store heap elements
    ArrayList<T> data = new ArrayList<>();
    // HashMap to store the index of each element in the heap
    HashMap<T, Integer> map = new HashMap<>();

	// Perform down-heapify to maintain the heap property
    private void heapify_downwards(int parentIndex) {

		int maxIndex = parentIndex;
        int rightChildIndex = 2 * parentIndex + 2;
		int leftChildIndex = 2 * parentIndex + 1;
        

        // Find the index of the maximum element among parent, left child, and right child
        if (leftChildIndex < this.data.size() && check_large(data.get(leftChildIndex), data.get(maxIndex)) > 0) {
            maxIndex = leftChildIndex;
        }

        if (rightChildIndex < this.data.size() && check_large(data.get(rightChildIndex), data.get(maxIndex)) > 0) {
            maxIndex = rightChildIndex;
        }

        // If the maximum element is not the parent, swap and continue down-heapify
        if (maxIndex != parentIndex) {
            swap(maxIndex, parentIndex);
            heapify_downwards(maxIndex);
        }
    }
    

    // Perform up-heapify to maintain the heap property
    private void heapify_upwards(int currentIndex) {

        int parentIndex = (currentIndex - 1) / 2;
        // If the current element is larger than its parent, swap and continue up-heapify
        if (check_large(data.get(currentIndex), data.get(parentIndex)) > 0) {

            swap(parentIndex, currentIndex);

            heapify_upwards(parentIndex);
        }
    }

    

	// Get the maximum element in the heap (root)
    public T get() {
        return this.data.get(0);
    }

    // show the elements in the heap
    public void show() {
        System.out.println(data);
    }

	// Swap elements at indices i and j
    private void swap(int i, int j) {

		T elementJ = data.get(j);
        T elementI = data.get(i);
        

        data.set(i, elementJ);
        data.set(j, elementI);

        // Update the map with the new indices after swapping
        map.put(elementI, j);
        map.put(elementJ, i);
    }
	

    // Get the size of the heap
    public int size() {
        return this.data.size();
    }

    // Check if the heap is empty
    public boolean empty_check() {
        return this.size() == 0;
    }

	

    // Remove and return the maximum element from the heap
    public T removal() {
        // Swap the root with the last element, remove the last element, and perform down-heapify
        swap(0, this.data.size() - 1);

        T removedValue = this.data.remove(this.data.size() - 1);

        heapify_downwards(0);

        // Remove the entry from the map

        map.remove(removedValue);
        return removedValue;
    }

	

    // Compare two elements and return the result
    public int check_large(T element1, T element2) {
        return element1.compareTo(element2);
    }




	// Add an item to the heap
    public void add(T item) {

        data.add(item);   
        // Update the map with the index of the newly added item
        map.put(item, this.data.size() - 1);
        // Perform up-heapify to maintain the heap property
        heapify_upwards(data.size() - 1);

    }

	    // Update the priority of an element in the heap
    public void comingness(T element) {
        // Get the index of the element in the heap and perform up-heapify
        int index = map.get(element);

        heapify_upwards(index);
    }


}
