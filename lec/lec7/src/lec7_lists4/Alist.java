package lec7_lists4;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.08
 **/
public class Alist<T> {

	private T[] items;
	private int size;

	private static int MIN_CAPACITY = 16;

	/**
	 * Creates an empty list.
	 */
	@SuppressWarnings("unchecked")
	public Alist() {
		this.items = (T[]) new Object[MIN_CAPACITY];
		this.size = 0;
	}

	/**
	 * Creates an empty list with the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public Alist(int size) {
		this.items = (T[]) new Object[size];
		this.size = 0;
		MIN_CAPACITY = nextCapacity(size - 1);
	}

	/**
	 * Inserts x at the front of the list.
	 */
	public void addLast(T x) {
		expansion();
		items[size++] = x;
	}

	/**
	 * Returns the last item in the list.
	 */
	public T getLast() {
		return items[size - 1];
	}

	/**
	 * Removes the last item in the list.
	 */
	public void removeLast() {
		size--;
		arrayMemoryGc();
	}

	/**
	 * Inserts x at the front of the list.
	 */
	public void addFirst(T x) {
		expansion();
		System.arraycopy(items, 0, items, 1, size);
		items[0] = x;
		size++;
	}

	/**
	 * Removes the first item in the list.
	 */
	public void removeFirst() {
		System.arraycopy(items, 1, items, 0, size - 1);
		size--;
	}

	/**
	 * Returns the first item in the list.
	 */
	public T getFirst() {
		return items[0];
	}

	/**
	 * Returns the i-th item in the list.
	 *
	 * @throws IndexOutOfBoundsException if 'i' is out of bounds.
	 */
	public T get(int i) {
		checkBounds(i);
		return items[i];
	}

	/**
	 * Replaces the i-th item in the list with x.
	 *
	 * @throws IndexOutOfBoundsException if 'i' is out of bounds.
	 */
	public void insert(int i, T x) {
		checkBounds(i);
		expansion();
		System.arraycopy(items, i, items, i + 1, size - i);
		items[i] = x;
		size++;
	}

	/**
	 * Removes the i-th item in the list.
	 *
	 * @throws IndexOutOfBoundsException if 'i' is out of bounds.
	 */
	public void remove(int i) {
		checkBounds(i);
		System.arraycopy(items, i + 1, items, i, size - i - 1);
		size--;
		arrayMemoryGc();
	}

	/**
	 * Returns the number of items in the list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if the list is empty, false otherwise.
	 */
	public void clear() {
		size = 0;
	}

	/**
	 * Resizes the underlying array to the specified capacity.
	 * If the specified capacity is less than the current size, the size is adjusted accordingly.
	 */
	@SuppressWarnings("unchecked")
	public void resize(int capacity) {
		capacity = Math.max(capacity, MIN_CAPACITY);
		T[] newItems = (T[]) new Object[nextCapacity(capacity - 1)];
		System.arraycopy(items, 0, newItems, 0, size);
		items = newItems;
		size = capacity;
	}

	@SuppressWarnings("unchecked")
	private void reserve() {
		int newCapacity = nextCapacity(capacity());
		T[] newItems = (T[]) new Object[newCapacity];
		System.arraycopy(items, 0, newItems, 0, size);
		items = newItems;
	}


	/**
	 * Reserves space for the specified number of items.
	 * If the specified capacity is less than or equal to the current capacity, no action is taken.
	 */
	@SuppressWarnings("unchecked")
	public void reserve(int capacity) {
		if (capacity > capacity()) {
			int newCapacity = nextCapacity(capacity());
			T[] newItems = (T[]) new Object[newCapacity];
			System.arraycopy(items, 0, newItems, 0, size);
			items = newItems;
		}
	}

	private void checkBounds(int i) {
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
		}
	}

	private int capacity() {
		return items.length;
	}

	private void expansion() {
		if (size == capacity()) {
			reserve();
		}
	}

	private void arrayMemoryGc() {
		if (size < capacity() / 4 && capacity() > (MIN_CAPACITY << 1)) {
			resize(capacity() / 4);
		}
	}

	private static int nextCapacity(long capacity) {
		capacity |= capacity >> 1;
		capacity |= capacity >> 2;
		capacity |= capacity >> 4;
		capacity |= capacity >> 8;
		capacity |= capacity >> 16;
		return (int) (capacity + 1);
	}

}
