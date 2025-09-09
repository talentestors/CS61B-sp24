package io.github.talentestors.list.impl;

import io.github.talentestors.list.AbstractList;
import io.github.talentestors.list.List61B;

import java.util.NoSuchElementException;

/**
 * @author talentestors
 */
public class DLList<T> extends AbstractList<T> implements List61B<T> {

	private static class Node<T> {
		T item;
		Node<T> next;
		Node<T> prev;

		Node(T f, Node<T> next, Node<T> prev) {
			item = f;
			this.next = next;
			this.prev = prev;
		}

		Node(T f) {
			this(f, null, null);
			this.next = this;
			this.prev = this;
		}
	}

	// The first item (if it exists) is at sentinel.next.
	private final Node<T> sentinel;

	/* Creates an empty SLList. */
	public DLList() {
		sentinel = new Node<>(null);
		this.size = 0;
	}

	/* Creates a new SLList with one item, x. */
	public DLList(T x) {
		this();
		sentinel.next = new Node<>(x, sentinel, sentinel);
		sentinel.prev = sentinel.next;
		this.size = 1;
	}

	/* Adds item x to the front of the list. */
	@Override
	public void addFirst(T x) {
		Node<T> temp = sentinel.next;
		sentinel.next = new Node<>(x, sentinel.next, sentinel);
		temp.prev = sentinel.next;
		this.size += 1;
	}

	/* Gets the first item in the list. */
	@Override
	public T getFirst() {
		return sentinel.next.item;
	}

	@Override
	public T get(int i) {
		checkBounds(i);
		Node<T> p = sentinel.next;
		for (int j = 0; j < i; j++) {
			p = p.next;
		}
		return p.item;
	}

	@Override
	public void insert(int i, T x) {
		checkBounds(i);
		if (i == this.size) {
			addLast(x);
			return;
		}
		Node<T> p = sentinel.next;
		for (int j = 0; j < i; j++) {
			p = p.next;
		}
		Node<T> newNode = new Node<>(x, p, p.prev);
		p.prev.next = newNode;
		p.prev = newNode;
		this.size += 1;
	}

	@Override
	public void remove(int i) {
		checkBounds(i);
		if (i == 0) {
			removeFirst();
			return;
		}
		if (i == this.size - 1) {
			removeLast();
			return;
		}
		Node<T> p = sentinel.next;
		for (int j = 0; j < i; j++) {
			p = p.next;
		}
		p.prev.next = p.next;
		p.next.prev = p.prev;
		p.prev = p.next = null;
		this.size -= 1;
	}

	@Override
	public T getLast() {
		return sentinel.prev.item;
	}

	/* Add x to the end of the list. */
	@Override
	public void addLast(T x) {
		this.size += 1;
		Node<T> p = sentinel.prev;
		p.next = new Node<>(x, sentinel, p);
		sentinel.prev = p.next;
	}

	@Override
	public void removeLast() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		Node<T> p = sentinel.prev;
		sentinel.prev = p.prev;
		p.prev.next = sentinel;
		p.prev = p.next = null;
		this.size -= 1;
	}

	@Override
	public void removeFirst() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		Node<T> p = sentinel.next;
		sentinel.next = p.next;
		p.next.prev = sentinel;
		p.prev = p.next = null;
		this.size -= 1;
	}

	@Override
	public void clear() {
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		this.size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node<T> p = sentinel.next;
		while (p != sentinel) {
			sb.append(p.item).append(", ");
			p = p.next;
		}
		sb.delete(sb.length() - 2, sb.length()).append(']');
		return sb.toString();
	}
}
