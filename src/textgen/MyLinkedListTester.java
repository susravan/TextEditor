/**
* 
*/
package textgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	@Before
	public void setUp() throws Exception {
		shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		list1.add(-78);
	}

	/**
	 * Test if the get method is working correctly.
	 */
	@Test
	public void testGet() {
		// Test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		// Test out of bounds
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		// Test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		// Test short list, first contents
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		// Test longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
		}
	}

	/**
	 * Test removing an element from the list.
	 */
	@Test
	public void testRemove() {
		Integer a = list1.remove(0);
		assertEquals("Remove: check a is correct ", (Integer) 65, a);
		assertEquals("Remove: check size is correct ", 3, list1.size);

		Integer b = list1.remove(2);
		assertEquals("Remove: check b is correct", (Integer) (-78), b);
		assertEquals("Remove: check size is correct ", 2, list1.size);
	}

	/**
	 * Test adding an element into the end of the list, specifically public boolean
	 * add(E element)
	 */
	@Test
	public void testAddEnd() {
		// Test adding at index
		boolean state = shortList.add("C");
		assertEquals("AddEnd: check state after add", true, state);
		assertEquals("AddEnd: add at index", "C", shortList.get(2));
		assertEquals("AddEnd: check size after add", 3, shortList.size);

		state = emptyList.add(2);
		assertEquals("AddEnd: check state after add", true, state);
		assertEquals("AddEnd: add at index", (Integer) 2, emptyList.get(0));
		assertEquals("AddEnd: check size after add", 1, emptyList.size);

		try {
			shortList.add(null);
			fail("Check invalid element");
		} catch (Exception ex) {
		}
	}

	/** Test the size of the list */
	@Test
	public void testSize() {
		assertEquals("Size: check size", 0, emptyList.size);
		assertEquals("Size: check size", 4, list1.size);
	}

	/**
	 * Test adding an element into the list at a specified index, specifically
	 */
	@Test
	public void testAddAtIndex() {
		// Test adding at index
		shortList.add(0, "C");
		assertEquals("AddAtIndex: add at index", "C", shortList.get(0));
		assertEquals("AddEnd: check size after add", 3, shortList.size);

		shortList.add(1, "C");
		assertEquals("AddAtIndex: add at index", "C", shortList.get(1));

		// Test adding as last element
		shortList.add(4, "C");
		assertEquals("AddAtIndex: add at index", "C", shortList.get(4));

		try {
			shortList.add(1, null);
			fail("Invalid input element");
		} catch (Exception ex) {
		}
		
		// Test out of bounds
		try {
			shortList.add(-1, "C");
			fail("Check out of bounds");
		} catch (Exception ex) {
		}

		try {
			shortList.add(10, "C");
			fail("Check out of bounds");
		} catch (Exception ex) {
		}
	}

	/** Test setting an element in the list */
	@Test
	public void testSet() {
		// Test setting the value
		shortList.set(0, "C");
		assertEquals("Set: check set at index", "C", shortList.get(0));

		shortList.set(1, "D");
		assertEquals("Set: check set at index", "D", shortList.get(1));

		// Test out of bounds
		try {
			shortList.set(-1, "C");
			fail("Check out of bounds");
		} catch (Exception ex) {
		}

		try {
			shortList.set(10, "C");
			fail("Check out of bounds");
		} catch (Exception ex) {
		}

		try {
			shortList.set(1, null);
			fail("Invalid input element");
		} catch (Exception ex) {
		}
	}

}
