package io.github.talentestors.list;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.09
 **/
public interface List61B<Item> {
	/**
	 * Inserts x at the front of the list.
	 */
	void addLast(Item x);

	/**
	 * Returns the last item in the list.
	 */
	Item getLast();

	/**
	 * Removes the last item in the list.
	 */
	void removeLast();

	/**
	 * Inserts x at the front of the list.
	 */
	void addFirst(Item x);

	/**
	 * Removes the first item in the list.
	 */
	void removeFirst();

	/**
	 * Returns the first item in the list.
	 */
	Item getFirst();

	/**
	 * Returns the i-th item in the list.
	 */
	Item get(int i);

	/**
	 * Replaces the i-th item in the list with x.
	 */
	void insert(int i, Item x);

	/**
	 * Removes the i-th item in the list.
	 */
	void remove(int i);

	/**
	 * Returns the number of items in the list.
	 */
	int size();

	/**
	 * Returns true if the list is empty, false otherwise.
	 */
	void clear();

	/**
	 * Returns true if the list is empty, false otherwise.
	 */
	boolean isEmpty();

}
