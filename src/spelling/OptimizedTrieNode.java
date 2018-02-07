package spelling;

import java.util.ArrayList;
import java.util.List;

public class OptimizedTrieNode {
	private OptimizedTrieNode[] next;
	private boolean isWord;
	
	public OptimizedTrieNode() {
		this.next = new OptimizedTrieNode[26];
		this.isWord = false;
	}
	
	public OptimizedTrieNode getChild(char ch) {
		if(ch == ' ')
			return null;
		return this.next[ch];
	}
	
	public boolean hasChild(char ch) {
		if(ch == ' ')
			return false;
		return this.next[ch] != null;
	}
	
	public OptimizedTrieNode insert(char ch) {
		if(ch == ' ')
			return null;
//		System.out.println("Current ch = " + ch);
		if(this.next[ch] == null) {
			this.next[ch] = new OptimizedTrieNode();
			return this.next[ch];
		}
		else
			return null;
	}
	
	public List<OptimizedTrieNode> getNextChars() {
		List<OptimizedTrieNode> res = new ArrayList<>();
		for(char ch='a'; ch <= 'z'; ch++) {
			if(this.next[ch] != null)
				res.add(this.next[ch]);
		}
		return res;
	}
	
	public void setIsWord() {
		this.isWord = true;
	}
	
	public boolean isWord() {
		return isWord;
	}
}
