package io.github.talentestors.list;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.09
 **/
public abstract class AbstractList<T> implements List61B<T> {

	protected int size;

	protected void checkBounds(int i) {
		if (i < 0 || i >= size && size != 0) {
			throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	abstract public void addLast(T x);

	@Override
	abstract public T getLast();

	@Override
	abstract public void removeLast();

	@Override
	abstract public void addFirst(T x);

	@Override
	abstract public void removeFirst();

	@Override
	abstract public T getFirst();

	@Override
	abstract public T get(int i);

	@Override
	abstract public void insert(int i, T x);

	@Override
	abstract public void remove(int i);

	@Override
	abstract public void clear();

}
