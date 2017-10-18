package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		if(element == null)
			throw new NullPointerException("Invalid input element!");
		
		LLNode<E> newNode = new LLNode<>(element);
		
		if(head == null) {
			head = newNode;
			tail = newNode;
		}
		// List already contains elements => go to last element and add
		else {
			LLNode<E> curr = head;
			
			while(curr.next != null)
				curr = curr.next;
			
			curr.next = newNode;
			newNode.prev = curr;
			tail = newNode;
		}
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();
		
		if(index == 0)
			return head.data;
		
		// Go to index
		LLNode<E> curr = head;
		while(index > 0) {
			curr = curr.next;
			index--;
		}
		
		return curr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		if(index < 0 || index > this.size)
			throw new NullPointerException("Invalid input element!");
		
		if(element == null)
			throw new NullPointerException("");
		
		LLNode<E> newNode = new LLNode<>(element);
		LLNode<E> curr = head;
		
		// Add at last
		if(index == this.size)
			this.add(element);
		
		// Add as first element
		if(index == 0) {
			head = newNode;
			newNode.next = curr;
			if(curr != null) {
				curr.prev = newNode;
			}
			else {
				tail = newNode;
			}
			size++;
			return;
		}
		
		while(index > 0) {
			curr = curr.next;
			index--;
		}
		curr.prev.next = newNode;
		newNode.next = curr;
		newNode.prev = curr.prev;
		curr.prev = newNode;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index)
	{
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();

		LLNode<E> curr = head;
		
		// curr is first element
		if(index == 0) {
			head = curr.next;
			if(tail == curr)
				tail = head;
		}
		else {
			while(index > 0) {
				curr = curr.next;
				index--;
			}
			// curr is the last element
			if(curr.next == null) {
				curr.prev.next = null;
				tail = curr.prev;
			}
			// curr is middle element
			else {
				curr.prev.next = curr.next;
				curr.next.prev = curr.prev;
			}
		}
		size--;
		return curr.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(element == null)
			throw new NullPointerException("Invalid input element!");
		
		if(index < 0 || index >= this.size)
			throw new IndexOutOfBoundsException();

		LLNode<E> pointer = head;
		
		while(index > 0) {
			pointer = pointer.next;
			index--;
		}
		pointer.data = element;
		return pointer.data;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
