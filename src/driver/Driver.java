package driver;

import proj3.*;

public class Driver {

	public static boolean debug = true;
	
	public static void printImmediateResults(Partial results)
	{
		if (results == null) { System.out.println("No Partials found with this text"); }
		else results.printImmediateOptions();
	}
	
	public static void main(String[] args) 
	{
		//HashedRBTs <Node> test1 = new HashedRBTs<Node>(52);
		HashedRBTs test1 = new HashedRBTs(52);
		
		test1.fileReader("input1.txt");
		
		// Notice that printHashCountResults() only prints THE ROOT of the BST and it's heap.
		// There can be many other nodes in the BST itself
		// The code below this does a more detailed job for a specific letter
		test1.printHashCountResults();

		// retrieve all A's (index 0)
		RedBlackTree <Partial> sample = test1.retreiveHashedRBTat(0);

		if(debug) { System.out.println("^^^ Printing A's Red-Black tree ROOT"); }
		sample.printRoot();
		if(debug) { System.out.println("&&& Printing A's Red-Black ENTIRE tree"); }
		sample.printTree();
		
		// Want to see what Immediate options are for "Ar"
		Partial results = (Partial) sample.retreiveIfItContains(new Partial(new Node("Ar", -1))); 
		// the frequency does not matter in the line (-1) above 
		printImmediateResults(results);

	}

}
