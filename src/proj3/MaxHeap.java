package proj3;
/**
 * @author Prabhdeep Singh
 * @project 3
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */


/**
 * The max heap data structure
 *
 * @param <E>   The type of elements in the tree.
 */

public class MaxHeap<E extends Comparable<? super E>> {

    private Object[] data;
    private int maxSize;
    private int size;

    /**
     * Constructs a default MaxHeap with an initial
     * size of 10 and a max size of 10.
     */

    public MaxHeap() {
        this.data = new Object[10];
        this.size = 0;
        this.maxSize = 10;
        buildHeap();
    }

    /**
     * Checks to see if the given index is a leaf.
     * This is most easily done by checking to see
     * if the index is in the upper-half of the
     * array, whilst still being within the bounds.
     *
     * @param pos   The position of the element.
     * @return      true if the index is
     *              a candidate to be a leaf.
     */

    private boolean isLeaf(int pos) {
        return (pos >= size / 2) && (pos < size);
    }

    /**
     * Returns the left child of the element at
     * the given index.
     *
     * @param pos   The position of the element.
     * @return      The position of the child.
     *              If this index is a leaf,
     *              -1 is returned.
     */

    private int leftChild(int pos) {
        if (pos >= size / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }

    /**
     * Returns the right child of the element at
     * the given index. Note that for indexing
     * issues, it may be best to check if the
     * given index is not a leaf before this.
     *
     * @param pos   The position of the element.
     * @return      The position of the child.
     *              If this index is a leaf,
     *              -1 is returned.
     */

    private int rightChild(int pos) {
        if (pos >= (size - 1) / 2) {
            return -1;
        }
        return 2 * pos + 2;
    }


    /**
     * Returns the parent of the element at
     * the given index. If the given element
     * is at the top of the tree, itself will
     * always be returned.
     *
     * @param pos   The position of the element.
     * @return      The position of the parent.
     *              If this index is the parent,
     *              0 is returned.
     */

    private int parent(int pos) {
        if (pos <= 0) {
            return 0;
        }
        return (pos - 1) / 2;
    }

    /**
     * Inserts a new value into the tree,
     * ensuring that the order stays consistent.
     *
     * @param val   The element to insert into
     *              the tree.
     */

    public void insert(E val) {
        if (size >= maxSize) {
            resize();
        }
        int curr = size++;
        data[curr] = val;
        while ((curr != 0) && (compare(data[curr], data[parent(curr)]) > 0)) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
    }

    /**
     * Resizes the heap to accommodate for a new
     * element. This will always double the
     * initial max size.
     */

    private void resize() {
        final Object[] newArray = new Object[maxSize * 2];
        System.arraycopy(data, 0, newArray, 0, size);
        this.maxSize *= 2;
        this.data = newArray;
    }

    /**
     * Constructs the initial heap, by shifting
     * elements into their proper location based
     * off the comparative value between the
     * other elements already inside the list.
     *
     */

    public void buildHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            shiftDown(i);
        }
    }

    /**
     * Shifts the element down from the position
     * to an index where it will fit in properly.
     *
     * @param pos   The position of the element
     *              to move.
     */

    private void shiftDown(int pos) {
        if (pos < 0 || pos >= size) {
            return;
        }
        while (!isLeaf(pos)) {
            int j = leftChild(pos);
            if ((j < (size - 1)) && (compare(data[j], data[j + 1]) < 0)) {
                j++;
            }
            if (compare(data[pos], data[j]) >= 0) {
                return;
            }
            swap(pos, j);
            pos = j;
        }
    }

    /**
     * Removes and returns the element at the
     * given index.
     *
     * @param pos   The position of the element
     *              to remove.
     * @return      The element that was removed.
     *
     */

    @SuppressWarnings("unchecked")
    public E remove(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (pos == (size - 1)) {
            size--;
        } else {
            swap(pos, --size);
            while (pos > 0 && compare(data[pos], data[parent(pos)]) > 0) {
                swap(pos, parent(pos));
                pos = parent(pos);
            }
            if (size != 0) shiftDown(pos);
        }
        return (E) data[size];
    }

    /**
     * Swaps elements in the heap.
     *
     * @param i First element.
     * @param j Second element.
     */

    private void swap(int i, int j) {
        final Object temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Compares to data values and returns the
     * result of this check.
     *
     * @param a First data element.
     * @param b Second data element.
     * @return  The comparison value when first
     *          element is compared to the second.
     */

    @SuppressWarnings("unchecked")
    private int compare(final Object a, final Object b) {
        return ((Comparable) a).compareTo(b);
    }

    /**
     * Checks to see if the heap is empty.
     *
     * @return  true if the heap
     *          contains no elements.
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the heap.
     *
     * @return  The size of the heap.
     */

    public int size(){
        return size;
    }

    /**
     * Prints the first three elements inside
     * the heap. If the size of the heap's content
     * is less than 3, only those elements existing
     * will be printed. This will print out values
     * that are null.
     */

    public void printImmediateOptions() {
        final int count = Math.min(maxSize, 3);
        for (int i = 0; i < count; i++) {
            System.out.println(data[i]);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(String.format("\n[%d] %s", i + 1, data[i]));
        }
        return builder.toString();
    }

    /**
     * Prints out the first element in the heap,
     * if it exists. If it does not, a warning
     * will be printed out explaining the heap
     * is empty.
     */

    public void printHeapRoot() {
        if (!isEmpty()) {
            System.out.println(data[0]);
        } else {
            System.out.print("Empty heap.");
        }
    }

    /**
     * Prints out the result.
     */

    public void printHeap() {
        System.out.println(toString());
    }

}