package proj3;
/**
 * @author Prabhdeep Singh
 * @project 3
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

/**
 * A red black tree maintains balance through
 * usage of painting specific nodes a color, then
 * using color patterns to handle rotations. This
 * ensures a constant operation time, rather than
 * having the tree degrade to a list.
 *
 * @param <E>   The type of values this tree holds.
 */

public class RedBlackTree<E extends Comparable<? super E>> {

    private static final int BLACK = 1;
    private static final int RED = 0;

    private RedBlackNode nullNode = new RedBlackNode();
    private RedBlackNode root = new RedBlackNode();
    private RedBlackNode current;
    private RedBlackNode parent;
    private RedBlackNode gParent;
    private RedBlackNode g2Parent;


    /**
     * Constructs a new tree with the given
     * low value. The low value should be the
     * absolute minimal value the tree could
     * have.
     *
     * @param minElement    The lowest value.
     */

    public RedBlackTree(final E minElement) {
        root = new RedBlackNode(minElement);
        root.setLeft(nullNode);
        root.setRight(nullNode);
    }


    /**
     * Inserts a new item into the tree at
     * the correct index. With this index,
     * rotations can then occur to main the
     * essence of the RBTree.
     *
     * @param item  The item to add.
     */

    public void insert(final E item) {
        current = root;
        parent = root;
        gParent = root;
        nullNode.setValue(item);

        while (current.getValue() != null && current.getValue().compareTo(item) != 0) {
            g2Parent = gParent;
            gParent = parent;
            parent = current;
            current = item.compareTo(current.getValue()) < 0 ? current.getLeft() : current.getRight();

            if (current.getLeft() != null && current.getRight() != null) {
                if (current.getLeft().isRed() && current.getRight().isRed()) {
                    handleReorient(item);
                }
            }
        }

        if (current != nullNode) {
            return;
        }

        current = new RedBlackNode(item, nullNode, nullNode);

        if (item.compareTo(parent.getValue()) < 0) {
            parent.setLeft(current);
        } else {
            parent.setRight(current);
        }

        handleReorient(item);
    }

    /**
     * Reorients the tree so that the
     * pattern is maintained with the levels
     * in respect to the colors. This helps
     * maintain the tree's functionality.
     *
     * @param item  The item recently added.
     */

    private void handleReorient(final E item) {
        current.setColor(RED);
        current.getLeft().setColor(BLACK);
        current.getRight().setColor(BLACK);

        if (parent.isRed()) {
            gParent.setColor(RED);
            if ((item.compareTo(gParent.getValue()) < 0) != (item.compareTo(parent.getValue()) < 0)) {
                parent = rotate(item, gParent);
            }
            current = rotate(item, g2Parent);
            current.setColor(BLACK);
        }

        root.getRight().setColor(BLACK);
    }

    /**
     * Handles the rotate of the tree, to
     * maintain the balance and color-pattern.
     *
     * @param item      The item recently added.
     * @param parent    The parent of the item
     *                  to rotate upon.
     * @return          The new parent of the
     *                  added item.
     */

    private RedBlackNode rotate(final E item, final RedBlackNode parent) {
        if (item.compareTo(parent.getValue()) < 0) {
            RedBlackNode node;
            if (item.compareTo(parent.getLeft().getValue()) < 0) {
                node = rotateWithLeftChild(parent.getLeft());
            } else {
                node = rotateWithRightChild(parent.getLeft());
            }
            parent.setLeft(node);
            return node;
        } else {
            RedBlackNode node;
            if (item.compareTo(parent.getRight().getValue()) < 0) {
                node = rotateWithLeftChild(parent.getRight());
            } else {
                node = rotateWithRightChild(parent.getRight());
            }
            parent.setRight(node);
            return node;
        }
    }

    /**
     * Performs a left rotation upon a child.
     *
     * @param node1 The child to rotate.
     * @return      The new root node for this
     *              subtree that was rotated.
     */

    private RedBlackNode rotateWithLeftChild(final RedBlackNode node1) {
        RedBlackNode node2 = node1.getLeft();
        node1.setLeft(node2.getRight());
        node2.setRight(node1);
        return node2;
    }

    /**
     * Performs a right rotation upon a child.
     *
     * @param node1 The child to rotate.
     * @return      The new root node for this
     *              subtree that was rotated.
     */

    private RedBlackNode rotateWithRightChild(final RedBlackNode node1) {
        RedBlackNode node2 = node1.getRight();
        node1.setRight(node2.getLeft());
        node2.setLeft(node1);
        return node2;
    }

    /**
     * Checks to see if the tree is empty.
     *
     * @return  true if the tree
     *          contains no values.
     */

    public boolean isEmpty() {
        return root.getRight().getValue() == null;
    }


    /**
     * Checks to see if the given item is
     * found with the tree.
     *
     * @param item  The item to look for.
     * @return      true if the
     *              item was found in the
     *              tree.
     */

    public boolean contains(final E item) {
        return getElement(item) != null;
    }

    /**
     * Retrieves the element that corresponds
     * to the given item. If this item is not
     * found, null is returned.
     *
     * @param item  The item to match.
     * @return      The corresponding item
     *              found in the tree.
     */

    public E retreiveIfItContains(final E item) {
        return getElement(item);
    }


    /**
     * Retrieves the element that matches
     * the given item inside the tree.
     *
     * @param item  The item to look for.
     * @return      The matching item.
     */

    private E getElement(final E item) {
        nullNode.setValue(item);
        current = root.getRight();

        while (current.getValue() != null) {
            if (item.compareTo(current.getValue()) < 0)
                current = current.getLeft();
            else if (item.compareTo(current.getValue()) > 0)
                current = current.getRight();
            else if (current != nullNode)
                return current.getValue();
            else
                return null;
        }
        return null;
    }

    /**
     * Returns the root of the tree, as
     * long as the tree is not empty. If the
     * tree is empty, null is
     * returned.
     *
     * @return  The root element.
     */

    public E getRoot() {
        if (isEmpty()) {
            return null;
        }
        return root.getRight().getValue();
    }

    /**
     * Prints out the root, if it exists.
     * Otherwise, a message is conveyed
     * showing that the tree is empty.
     *
     */

    public void printRoot() {
        if (!isEmpty()) {
            System.out.print("This tree starts with ");
            printElement(getRoot());
        } else {
            System.out.println("This tree has no nodes");
        }
    }

    /**
     * Prints out the tree in a in-order
     * traversal.
     */

    public void printTree() {
        printTree(root.getRight());
    }

    /**
     * Prints out the tree in a in-order
     * traversal using a recursive sequence.
     *
     * @param node  The node to print.
     */

    private void printTree(final RedBlackNode node) {
        if (node == nullNode) {
            return;
        }
        printTree(node.getLeft());
        printElement(node.getValue());
        System.out.println();
        printTree(node.getRight());
    }

    /**
     * Prints out an element by correctly
     * parsing out the element's string
     * interpretation to display all
     * relevant information of the value.
     *
     * @param e The element to print out.
     */

    private void printElement(final E e){
        if(e == null){
            return;
        }
        final String str = String.valueOf(e);
        final int index = str.indexOf("]") + 1;
        final String nodePart = str.substring(0, index);
        final String matchPart = str.substring(index);
        System.out.printf("%s --> The heap contains: %s\n", nodePart, matchPart);
    }

    /**
     * Contains basic information regarding
     * each individual node. These hold all
     * data that makes this tree a proper tree.
     */

    private class RedBlackNode {

        private E value;
        private RedBlackNode left;
        private RedBlackNode right;
        private int color;

        /**
         * Constructs a new node with no stored data
         * value and no children with a preset color
         * of black.
         */

        public RedBlackNode() {
            this(null, null, null);
        }

        /**
         * Constructs a new node with a given data
         * value and no children with a preset color
         * of black.
         *
         * @param value The value for this node to hold.
         */

        public RedBlackNode(final E value) {
            this(value, null, null);
        }

        /**
         * Constructs a new node with the given data
         * value and given children. This will also
         * use the default, black, color.
         *
         * @param value The value for this node to hold.
         * @param left  The left child of the node.
         * @param right The right child of the node.
         */

        public RedBlackNode(final E value, final RedBlackNode left, final RedBlackNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = BLACK;
        }

        public E getValue() {
            return value;
        }

        public RedBlackNode getLeft() {
            return left;
        }

        public RedBlackNode getRight() {
            return right;
        }

        public int getColor() {
            return color;
        }

        public void setValue(final E value) {
            this.value = value;
        }

        public void setLeft(final RedBlackNode left) {
            this.left = left;
        }

        public void setRight(final RedBlackNode right) {
            this.right = right;
        }

        public void setColor(final int color) {
            this.color = color;
        }

        /**
         * Checks to see if the given node is
         * painted red.
         *
         * @return  true if this is a
         *          red node.
         */

        public boolean isRed() {
            return getColor() == RED;
        }

        /**
         * Checks to see if the given node is
         * painted black.
         *
         * @return  true if this is a
         *          black node.
         */

        public boolean isBlack() {
            return getColor() == BLACK;
        }
    }
}
