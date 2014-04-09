package proj3;

import java.util.ArrayList;

public class Node implements Comparable <Object> {

	private String word;
	private int frequency;

	public Node(String word) {
		this.word = word;
		this.frequency = 1;
	}

	public Node(String word, int freq) {
		this.word = word;
		this.frequency = freq;
	}
	
	public String getWord() { return word; }
	public void setWord(String word) { this.word = word; }
	public int getFrequency() { return frequency; }
	public void setFrequency(int frequency) { this.frequency = frequency; }
	public void incrementFrequency() { this.frequency++; }
	
	@Override
	public String toString() { return "Node [word=" + word + ", frequency=" + frequency + "]"; }

	
	/** This function ONLY worries about matching the STRING portion of this node
	 * 
	 */
	public int compareTo(Object x) 
	{
		Node that = (Node) x;
		if(this.getFrequency() < that.getFrequency()) { return -1; }
		else if(this.getFrequency() > that.getFrequency()) { return 1; }
		else // then the frequency is the same, but hopefully not the word value
			{ return this.getWord().compareTo(that.getWord()); }
	}
	
}
