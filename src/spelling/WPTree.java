/**
 * 
 */
package spelling;

//import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * WPTree implements WordPath by dynamically creating a tree of words during a
 * Breadth First Search of Nearby words to create a path between two words.
 * 
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class WPTree implements WordPath {

	// this is the root node of the WPTree
	private WPTreeNode root;
	// used to search for nearby Words
	private NearbyWords nw;

	// This constructor is used by the Text Editor Application
	// You'll need to create your own NearbyWords object here.
	public WPTree() {
		this.root = null;
		Dictionary d = new DictionaryHashSet();
		DictionaryLoader.loadDictionary(d, "data/dict.txt");
		this.nw = new NearbyWords(d);
	}

	// This constructor will be used by the grader code
	public WPTree(NearbyWords nw) {
		this.root = null;
		this.nw = nw;
	}

	// see method description in WordPath interface
	public List<String> findPath(String word1, String word2) {
		// To avoid duplicates
		Set<String> visited = new HashSet<>();
		visited.add(word1);

		// If word1 is not present in the dictionary, it will be taken care by distanceOne method
		List<String> wordPath = new LinkedList<String>();
		WPTreeNode root = new WPTreeNode(word1, null);
		Queue<WPTreeNode> queue = new LinkedList<>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			WPTreeNode currNode = queue.poll();
			String currWord = currNode.getWord();
			List<String> currWordMutations = nw.distanceOne(currWord, true);

			for (String str : currWordMutations) {
				if (visited.contains(str))
					continue;
				if (str.equals(word2)) {
					wordPath.add(word2);
					wordPath.addAll(0,currNode.buildPathToRoot());
					return wordPath;
				}
				visited.add(str);
				WPTreeNode childNode = currNode.addChild(str);
				queue.offer(childNode);
			}
			System.out.println(printQueue(queue));
		}
		return wordPath;
	}

	// Method to print a list of WPTreeNodes (useful for debugging)
	private String printQueue(Queue<WPTreeNode> queue) {
		String ret = "[ ";

		for (WPTreeNode w : queue) {
			ret += w.getWord() + ", ";
		}
		ret += "]";
		return ret;
	}
}

/*
 * Tree Node in a WordPath Tree. This is a standard tree with each node having
 * any number of possible children. Each node should only contain a word in the
 * dictionary and the relationship between nodes is that a child is one
 * character mutation (deletion, insertion, or substitution) away from its
 * parent
 */
class WPTreeNode {

	private String word;
	private List<WPTreeNode> children;
	private WPTreeNode parent;

	/**
	 * Construct a node with the word w and the parent p (pass a null parent to
	 * construct the root)
	 * 
	 * @param w
	 *            The new node's word
	 * @param p
	 *            The new node's parent
	 */
	public WPTreeNode(String w, WPTreeNode p) {
		this.word = w;
		this.parent = p;
		this.children = new LinkedList<WPTreeNode>();
	}

	/**
	 * Add a child of a node containing the String s precondition: The word is not
	 * already a child of this node
	 * 
	 * @param s
	 *            The child node's word
	 * @return The new WPTreeNode
	 */
	public WPTreeNode addChild(String s) {
		WPTreeNode child = new WPTreeNode(s, this);
		this.children.add(child);
		return child;
	}

	/**
	 * Get the list of children of the calling object (pass a null parent to
	 * construct the root)
	 * 
	 * @return List of WPTreeNode children
	 */
	public List<WPTreeNode> getChildren() {
		return this.children;
	}

	/**
	 * Allows you to build a path from the root node to the calling object
	 * 
	 * @return The list of strings starting at the root and ending at the calling
	 *         object
	 */
	public List<String> buildPathToRoot() {
		WPTreeNode curr = this;
		List<String> path = new LinkedList<String>();
		while (curr != null) {
			path.add(0, curr.getWord());
			curr = curr.parent;
		}
		return path;
	}

	/**
	 * Get the word for the calling object
	 *
	 * @return Getter for calling object's word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * toString method
	 *
	 * @return The string representation of a WPTreeNode
	 */
	public String toString() {
		String ret = "Word: " + word + ", parent = ";
		if (this.parent == null) {
			ret += "null.\n";
		} else {
			ret += this.parent.getWord() + "\n";
		}
		ret += "[ ";
		for (WPTreeNode curr : children) {
			ret += curr.getWord() + ", ";
		}
		ret += (" ]\n");
		return ret;
	}
}
