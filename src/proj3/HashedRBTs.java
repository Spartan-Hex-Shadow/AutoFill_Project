package proj3;
/**
 * @author Prabhdeep Singh
 * @project 3
 * @class CMSC 341 M-W 5:30-6:45pm
 * @section 3
 * @email CW28656@umbc.edu
 */

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores the needed data for constructing the
 * auto-completion trees. This will parse a
 * given text file to find word frequencies and
 * use this data to find the next-best match
 * when given a partial word.
 */

public class HashedRBTs {

    private static final String MATCH = "Node \\[word=([a-zA-Z]+), frequency=(\\d+)\\]";

    private ArrayList<RedBlackTree<Partial>> table;

    /**
     * Constructs the given tree structure, limiting the
     * number of available trees.
     *
     * @param size The amount of trees to preserve.
     */

    public HashedRBTs(final int size) {
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new RedBlackTree<>(new Partial(new Node("\u0000", 0))));
        }
    }

    /**
     * Parses the given text file to read word frequency
     * data. In this method, the trees will be populated.
     *
     * @param file The file to read the data from.
     *
     */

    public void fileReader(final String file) {
        final File input = new File(file);
        if (!input.exists() || !input.canRead()) {
            throw new IllegalArgumentException("Given file cannot be accessed.");
        }
        try (final Scanner in = new Scanner(new FileReader(input))) {
            while (in.hasNext()) {
                final String next = in.nextLine();

                if (!next.matches(MATCH)) {
                    continue;
                }
                final String[] tokens = next.replaceAll(MATCH, "$1 $2").split("\\s");
                final String word = tokens[0];
                final int frequency = Integer.parseInt(tokens[1]);

                final Node node = new Node(word, frequency);
                final RedBlackTree<Partial> tree = treeFor(node);
                final Partial find = new Partial(node);
                final Partial partial = tree.retreiveIfItContains(find);

                if (partial == null) {
                    tree.insert(find);
                } else {
                    partial.insertNodeIntoHeap(node);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the tree for the given node. This will
     * use the first character of the word to find
     * the tree. If the given tree exceeds the count
     * of recorded trees, null will be
     * returned.
     *
     * @param node  The node to match the tree for.
     * @return      The given tree, if found.
     */

    private RedBlackTree<Partial> treeFor(final Node node) {
        final char det = node.getWord().charAt(0);
        if (!Character.isAlphabetic(det)) {
            throw new IllegalArgumentException();
        }
        final int index = (Character.toUpperCase(det) - 'A') + (Character.isUpperCase(det) ? 0 : 26);
        return retreiveHashedRBTat(index);
    }

    /**
     * Returns the tree at the given index. If the
     * index exceeds the tree count, null
     * is returned.
     *
     * @param index The index of the tree to return.
     * @return      The given tree, if found.
     */

    public RedBlackTree<Partial> retreiveHashedRBTat(final int index) {
        if (index >= table.size()) {
            return null;
        }
        return table.get(index);
    }

    /**
     * Prints out the data for the trees. The data
     * will only contain the root entry, if there
     * is one. Otherwise, a message will be sent
     * to convey the tree is empty.
     */

    public void printHashCountResults() {
        for (final RedBlackTree<Partial> tree : table) {
            tree.printRoot();
        }
    }

}
