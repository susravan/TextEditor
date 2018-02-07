/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 1000;

	Dictionary dict;

	public NearbyWords(Dictionary dict) {
		this.dict = dict;
	}

	/**
	 * Return the list of Strings that are one modification away from the input
	 * string.
	 * 
	 * @param s
	 *            The original String
	 * @param wordsOnly
	 *            controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly) {
		List<String> retList = new ArrayList<String>();
		insertions(s, retList, wordsOnly);
		substitution(s, retList, wordsOnly);
		deletions(s, retList, wordsOnly);
		return retList;
	}

	/**
	 * Add to the currentList Strings that are one character mutation away from the
	 * input string.
	 * 
	 * @param s
	 *            The original String
	 * @param currentList
	 *            is the list of words to append modified words
	 * @param wordsOnly
	 *            controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		char[] chrArr = s.toCharArray();
		for (int i = 0; i < chrArr.length; i++) {
			char temp = chrArr[i];
			for (char ch = 'a'; ch <= 'z'; ch++) {
				if (ch == temp)
					continue;

				chrArr[i] = ch;
				String newWord = chrArr.toString();
				if (!wordsOnly || (wordsOnly && dict.isWord(newWord))) {
					currentList.add(newWord);
				}
			}
			chrArr[i] = temp;
		}
		// System.out.println("List of substitutions:");
		// System.out.println(currentList);
	}

	/**
	 * Add to the currentList Strings that are one character insertion away from the
	 * input string.
	 * 
	 * @param s
	 *            The original String
	 * @param currentList
	 *            is the list of words to append modified words
	 * @param wordsOnly
	 *            controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i <= s.length(); i++) {
			for (char ch = 'a'; ch <= 'z'; ch++) {
				sb.setLength(0);
				sb.append(s.substring(0, i)).append(ch).append(s.substring(i));
				String newWord = sb.toString();
				if (!wordsOnly || (wordsOnly && dict.isWord(newWord)))
					currentList.add(newWord);
			}
		}
		// System.out.println("List of insertions:");
		// System.out.println(currentList);
	}

	/**
	 * Add to the currentList Strings that are one character deletion away from the
	 * input string.
	 * 
	 * @param s
	 *            The original String
	 * @param currentList
	 *            is the list of words to append modified words
	 * @param wordsOnly
	 *            controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly) {
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < s.length(); i++) {
			sb.setLength(0);
			sb.append(s.substring(0, i)).append(s.substring(i + 1));
			String newWord = sb.toString();
			if (!wordsOnly || (wordsOnly && dict.isWord(newWord)))
				currentList.add(newWord);
		}
		// System.out.println("List of deletions:");
		// System.out.println(currentList);
	}

	/**
	 * Add to the currentList Strings that are one character deletion away from the
	 * input string.
	 * 
	 * @param word
	 *            The misspelled word
	 * @param numSuggestions
	 *            is the maximum number of suggestions to return
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		Queue<String> queue = new LinkedList<String>();
		HashSet<String> visited = new HashSet<String>();
		List<String> suggestions = new LinkedList<String>();
		int numWordsSeen = 0;
		// insert first node
		queue.add(word);
		visited.add(word);

		int sugCount = 0;
		while (!queue.isEmpty() && sugCount < numSuggestions && numWordsSeen < THRESHOLD) {
			String currWord = queue.poll();
			// System.out.println("Getting mutations for " + currWord);
			List<String> currWordMutations = distanceOne(currWord, true);
			for (String mutation : currWordMutations) {
				if (visited.contains(mutation))
					continue;
				queue.offer(mutation);
				visited.add(mutation);
				suggestions.add(mutation);
				sugCount++;
			}
			// System.out.println("Current queue is:");
			// System.out.println(queue);
			// System.out.println("Current suggestions are");
			// System.out.println(suggestions);
		}
		return suggestions;
	}

	public static void main(String[] args) {
		String word = "i";
		// Pass NearbyWords any Dictionary implementation you prefer
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(d, "data/dict.txt");
		NearbyWords w = new NearbyWords(d);
		List<String> l = w.distanceOne(word, true);
		System.out.println("One away word Strings for \"" + word + "\" are:");
		System.out.println(l + "\n");

		word = "kangaro";
		List<String> suggest = w.suggestions(word, 10);
		System.out.println("Spelling Suggestions for \"" + word + "\" are:");
		System.out.println(suggest);
	}
}
