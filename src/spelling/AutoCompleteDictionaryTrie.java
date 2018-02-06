package spelling;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {
	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
		this.size = 0;
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part 2),
	 * you should convert the string to all lower case before you insert it.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes into
	 * the trie, as described outlined in the videos for this week. It should
	 * appropriately use existing nodes in the trie, only creating new nodes when
	 * necessary. E.g. If the word "no" is already in the trie, then adding the word
	 * "now" would add only one additional node (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 *         in the dictionary.
	 */
	public boolean addWord(String word) {
		word = word.toLowerCase();
		TrieNode pointer = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			pointer.insert(ch);
			pointer = pointer.getChild(ch);
		}
		// If word already existing, return false
		if(pointer.isWord())
			return false;
		pointer.setEndsWord(true);
		this.size++;
		return true;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week.
	 */
	@Override
	public boolean isWord(String s) {
		s = s.toLowerCase();
		TrieNode pointer = root;
		char[] chrArr = s.toCharArray();
		for (int i = 0; i < chrArr.length; i++) {
			if(pointer.getChild(chrArr[i]) == null)
				return false;
			pointer = pointer.getChild(chrArr[i]);
		}
		return pointer.isWord();
	}

	/**
	 * Return a list, in order of increasing (non-decreasing) word length,
	 * containing the numCompletions shortest legal completions of the prefix
	 * string. All legal completions must be valid words in the dictionary. If the
	 * prefix itself is a valid word, it is included in the list of returned words.
	 * 
	 * The list of completions must contain all of the shortest completions, but
	 * when there are ties, it may break them in any order. For example, if there
	 * the prefix string is "ste" and only the words "step", "stem", "stew", "steer"
	 * and "steep" are in the dictionary, when the user asks for 4 completions, the
	 * list must include "step", "stem" and "stew", but may include either the word
	 * "steer" or "steep".
	 * 
	 * If this string prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix
	 *            The text to use at the word stem
	 * @param numCompletions
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to numCompletions best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the trie, return
		// an
		// empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem to the
		// back
		// of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions

		int predCount = 0;
		prefix = prefix.toLowerCase();
		List<String> predictions = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		TrieNode pointer = root;
		
		// Traversing through the prefix
		char[] chrArr = prefix.toCharArray();
		for (int i = 0; i < chrArr.length; i++) {
			char ch = chrArr[i];
			if (pointer.getChild(ch) != null) {
				sb.append(ch);
				pointer = pointer.getChild(ch);
			} else {
				return predictions; // Return empty list if prefix is not present
			}
		}
		
		Queue<TrieNode> possiblePred = new LinkedList<>();
		possiblePred.offer(pointer);
		
		while(predCount < numCompletions && !possiblePred.isEmpty()) {
			TrieNode curr = possiblePred.poll();
			if(curr.isWord()) {
				predictions.add(curr.getText());
				predCount++;
			}
			List<Character> validChars = new ArrayList<>(curr.getValidNextCharacters());
			for(int i=0; i < validChars.size() && predCount < numCompletions; i++) {
				char ch = validChars.get(i);
				possiblePred.offer(curr.getChild(ch));
				
//				if(possiblePred.peek().isWord()) {
//					System.out.println("Latest possiblePred = " + possiblePred.peek().getText());
//					predictions.add(possiblePred.peek().getText());
//					predCount++;
//				}
			}
		}
		System.out.println(predictions);
		return predictions;
	}
	
	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}
	
	public static void main(String args[]) {
		String dictFile = "data/words.small.txt"; 
		AutoCompleteDictionaryTrie emptyDict = new AutoCompleteDictionaryTrie();
		
		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("subsequent");
		
		AutoCompleteDictionaryTrie largeDict = new AutoCompleteDictionaryTrie();
		DictionaryLoader.loadDictionary(largeDict, dictFile);
		System.out.println(largeDict.isWord("no"));
		assertEquals("Testing isWord on small: hello", true, smallDict.isWord("hello"));
		assertEquals("Testing isWord on large: hello", true, largeDict.isWord("hello"));

		assertEquals("Testing isWord on small: hellow", false, smallDict.isWord("hellow"));
		assertEquals("Testing isWord on large: hellow", false, largeDict.isWord("hellow"));
		
		assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
		assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));
		assertEquals("Testing isWord on large: empty string", false, largeDict.isWord(""));
		
		assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
		assertEquals("Testing isWord on large: no", true, largeDict.isWord("no"));
		
		assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));
		assertEquals("Testing isWord on large: subsequent", true, largeDict.isWord("subsequent"));
		
//		System.out.println(at.isWord("add"));
//		at.addWord("add");
//		System.out.println(at.isWord(""));
//		at.addWord("ads");
//		at.addWord("bad");
//		at.addWord("bat");
//		at.addWord("sravan");
//		at.predictCompletions("s", 3);
	}
}