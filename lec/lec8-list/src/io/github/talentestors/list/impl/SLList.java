package io.github.talentestors.list.impl;

import io.github.talentestors.list.AbstractList;
import io.github.talentestors.list.List61B;

/**
 * @author talentestors
 */
public class SLList<T> extends AbstractList<T> implements List61B<T> {

	private static class IntNode<T> {
		public T item;
		public IntNode<T> next;

		public IntNode(T f, IntNode<T> r) {
			item = f;
			next = r;
		}
	}

	// The first item (if it exists) is at sentinel.next.
	private final IntNode<T> sentinel;

	/* Creates a new SLList with one item, x. */
	public SLList(T x) {
		this();
		sentinel.next = new IntNode<>(x, null);
		size = 1;
	}

	/* Creates an empty SLList. */
	public SLList() {
		sentinel = new IntNode<>(null, null);
		size = 0;
	}

	/* Adds item x to the front of the list. */
	@Override
	public void addFirst(T x) {
		sentinel.next = new IntNode<>(x, sentinel.next);
		size += 1;
	}

	@Override
	public void removeFirst() {
		if (size == 0) {
			return;
		}
		sentinel.next = sentinel.next.next;
		size -= 1;
	}

	/* Gets the first item in the list. */
	@Override
	public T getFirst() {
		return sentinel.next.item;
	}

	@Override
	public T get(int i) {
		checkBounds(i);
		IntNode<T> p = sentinel.next;
		while (i > 0) {
			p = p.next;
			i -= 1;
		}
		return p.item;
	}

	@Override
	public void insert(int i, T x) {
		checkBounds(i);
		size += 1;
		IntNode<T> p = sentinel;
		while (i > 0) {
			p = p.next;
			i -= 1;
		}
		p.next = new IntNode<>(x, p.next);
	}

	@Override
	public void remove(int i) {
		checkBounds(i);
		size -= 1;
		IntNode<T> p = sentinel;
		while (i > 0) {
			p = p.next;
			i -= 1;
		}
		p.next = p.next.next;
	}

	/* Add x to the end of the list. */
	@Override
	public void addLast(T x) {
		size += 1;
		IntNode<T> p = sentinel;
		// Scan p until it reaches the end of the list.
		while (p.next != null) {
			p = p.next;
		}
		p.next = new IntNode<>(x, null);
	}

	@Override
	public T getLast() {
		IntNode<T> p = sentinel.next;
		// Scan p until it reaches the end of the list.
		while (p.next != null) {
			p = p.next;
		}
		return p.item;
	}

	@Override
	public void removeLast() {
		if (size == 0) {
			return;
		}
		size -= 1;
		IntNode<T> p = sentinel;
		// Scan p until it reaches the end of the list.
		while (p.next.next != null) {
			p = p.next;
		}
		p.next = null;
	}

	@Override
	public void clear() {
		sentinel.next = null;
		size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		IntNode<T> p = sentinel.next;
		while (p != null) {
			sb.append(p.item).append(", ");
			p = p.next;
		}
		sb.delete(sb.length() - 2, sb.length()).append(']');
		return sb.toString();
	}
}
