import java.util.Arrays;
import java.util.List;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.08
 **/
public class ArrayDeque61B<T> implements Deque61B<T> {

	private T[] items;
	private int size;
	private static int MIN_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public ArrayDeque61B() {
		this.items = (T[]) new Object[MIN_CAPACITY];
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	public ArrayDeque61B(int size) {
		this.items = (T[]) new Object[size];
		this.size = 0;
		MIN_CAPACITY = nextCapacity(size - 1);
	}

	@Override
	public void addLast(T x) {
		expansion();
		items[size++] = x;
	}

	@Override
	public List<T> toList() {
		return Arrays.asList(Arrays.copyOf(items, size));
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public T removeLast() {
		size--;
		arrayMemoryGc();
		return items[size];
	}

	@Override
	public void addFirst(T x) {
		expansion();
		System.arraycopy(items, 0, items, 1, size);
		items[0] = x;
		size++;
	}

	@Override
	public T removeFirst() {
		System.arraycopy(items, 1, items, 0, size - 1);
		size--;
		return items[size];
	}

	@Override
	public T get(int i) {
		checkBounds(i);
		return items[i];
	}

	@Override
	public T getRecursive(int index) {
		throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
	}

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

	private void checkBounds(int i) {
		if (i < 0 || i >= size && size != 0) {
			throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
		}
	}

}
