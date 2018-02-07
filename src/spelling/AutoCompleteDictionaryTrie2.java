package spelling;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie2 implements Dictionary, AutoComplete {
	private OptimizedTrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie2() {
		root = new OptimizedTrieNode();
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
		OptimizedTrieNode pointer = root;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			pointer.insert(ch);
			pointer = pointer.getChild(ch);
		}
		if (pointer.isWord())
			return false;

		pointer.setIsWord();
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
		char[] chrArr = s.toCharArray();
		OptimizedTrieNode pointer = root;
		for (int i = 0; i < chrArr.length; i++) {
			if (pointer.hasChild(chrArr[i]))
				pointer = pointer.getChild(chrArr[i]);
			else
				return false;
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

		prefix = prefix.toLowerCase();
		List<String> predictions = new ArrayList<>();
		// Finding stem in the tree
		char[] chrArr = prefix.toCharArray();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < chrArr.length; i++) {
			char ch = chrArr[i];
			if (root.hasChild(ch)) {
				sb.append(ch);
				root = root.getChild(ch);
			} else {
				return predictions; // Return empty list if prefix is not present
			}
		}

		predictCompletionsHelper(root, predictions, sb, numCompletions);
		return predictions;
	}

	private void predictCompletionsHelper(OptimizedTrieNode node, List<String> predictions, StringBuilder sb,
			int numCompletions) {
		if (predictions.size() >= numCompletions)
			return;

		if (node.isWord())
			predictions.add(sb.toString());

		for (char ch = 'a'; ch <= 'z'; ch++) {
			if (node.getChild(ch) != null) {
				sb.append(ch);
				predictCompletionsHelper(node.getChild(ch), predictions, sb, numCompletions);
				sb.deleteCharAt(sb.length() - 1);
			}
		}
	}

	public static void main(String args[]) {
		long startTime = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			String dictFile = "data/words.small.txt";
			AutoCompleteDictionaryTrie2 emptyDict = new AutoCompleteDictionaryTrie2();

			AutoCompleteDictionaryTrie2 smallDict = new AutoCompleteDictionaryTrie2();
			smallDict.addWord("Hello");
			smallDict.addWord("HElLo");
			smallDict.addWord("help");
			smallDict.addWord("he");
			smallDict.addWord("hem");
			smallDict.addWord("hot");
			smallDict.addWord("hey");
			smallDict.addWord("a");
			smallDict.addWord("subsequent");

//			System.out.println(smallDict.isWord("hem"));
//			System.out.println(smallDict.predictCompletions("", 4));

			AutoCompleteDictionaryTrie2 largeDict = new AutoCompleteDictionaryTrie2();
			DictionaryLoader.loadDictionary(largeDict, dictFile);

			assertEquals("Testing isWord on small: hello", true, smallDict.isWord("hello"));
			assertEquals("Testing isWord on small: hellow", false, smallDict.isWord("hellow"));

			assertEquals("Testing isWord on empty: empty string", false, emptyDict.isWord(""));
			assertEquals("Testing isWord on small: empty string", false, smallDict.isWord(""));

			assertEquals("Testing isWord on small: no", false, smallDict.isWord("no"));
			assertEquals("Testing isWord on small: subsequent", true, smallDict.isWord("subsequent"));

		}
		long endTime = System.nanoTime();
		System.out.println("Time taken = " + (endTime - startTime) / 1000000 + " ms");
		// System.out.println(at.isWord("add"));
		// at.addWord("add");
		// System.out.println(at.isWord(""));
		// at.addWord("ads");
		// at.addWord("bad");
		// at.addWord("bat");
		// at.addWord("sravan");
		// at.predictCompletions("s", 3);
	}
}