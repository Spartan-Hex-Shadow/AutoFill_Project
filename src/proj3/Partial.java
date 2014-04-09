package proj3;

public class Partial implements Comparable <Object> {

	private String word;
	private MaxHeap<Node> matches = new MaxHeap<>( );

	public Partial(Node x) {
	
		if(x.getWord().length() > 1)
			this.word = x.getWord().substring(0, 2);
		else // single letter word
			this.word = x.getWord();
		
		insertNodeIntoHeap(x);
	}

	public String getWord() { return word; }
	public void setWord(String word) { this.word = word; }
	public void insertNodeIntoHeap(Node x) { matches.insert(x); }
	public void printImmediateOptions() 
	{ 
		System.out.println("*** Printing immediate matches for '" + this.getWord() + "'"); 
		
		if(this == null )
		{ System.out.println("There were no matches for '" + this.getWord() + "'"); }
		else if(matches.isEmpty())
		{ System.out.println("There were no matches for '" + this.getWord() + "'"); }
		else 
		{ matches.printImmediateOptions(); }
	}
		
	@Override
	public String toString() { return "Partial [word=" + word + "]" + matches; }

	@Override
	/** This function ONLY worries about matching the STRING portion of this Partial
	 * 
	 */
	public int compareTo(Object x) {
		
		Partial that = (Partial) x;
		return this.getWord().compareTo(that.getWord());
	}
	
}