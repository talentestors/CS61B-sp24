package lec3_lists1;

import java.util.NoSuchElementException;
import java.util.Random;

public class DLList<T> {

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
	private long size;

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
	public void addFirst(T x) {
		Node<T> temp = sentinel.next;
		sentinel.next = new Node<>(x, sentinel.next, sentinel);
		temp.prev = sentinel.next;
		this.size += 1;
	}

	/* Gets the first item in the list. */
	public T getFirst() {
		return sentinel.next.item;
	}

	public T getLast() {
		return sentinel.prev.item;
	}

	/* Add x to the end of the list. */
	public void addLast(T x) {
		this.size += 1;
		Node<T> p = sentinel.prev;
		p.next = new Node<>(x, sentinel, p);
		sentinel.prev = p.next;
	}

	public T removeLast() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		Node<T> p = sentinel.prev;
		sentinel.prev = p.prev;
		p.prev.next = sentinel;
		p.prev = p.next = null;
		this.size -= 1;
		return p.item;
	}

	public T removeFirst() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		Node<T> p = sentinel.next;
		sentinel.next = p.next;
		p.next.prev = sentinel;
		p.prev = p.next = null;
		this.size -= 1;
		return p.item;
	}

	public void clear() {
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		this.size = 0;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public long size() {
		return this.size;
	}

	public static void main(String[] args) {
		DLList<Integer> L = new DLList<>();
		L.addFirst(10);
		L.addFirst(15);
		L.addLast(20);
		System.out.println("L.removeLast() = " + L.removeLast());
		L.addFirst(1);
		System.out.println("L.removeFirst() = " + L.removeFirst());
		System.out.println("L.size() = " + L.size());
		L.addLast(20);
		System.out.println("L.getLast() = " + L.getLast());
		System.out.println("L.getFirst() = " + L.getFirst());
		System.out.println("L.size() = " + L.size());

		for (int i = 0; i < 1e6; i++) {
			Random random = new Random(System.currentTimeMillis());
			if (i % 2 == 0) {
				L.addLast(i);
			} else {
				L.addFirst(i * random.nextInt() % random.nextInt());
			}
			if (i % 7 == 0) {
				L.removeFirst();
			}
			if (random.nextInt() % 13 == 0) {
				L.removeLast();
			}
			if (i % 11 == 0) {
				L.removeLast();
			}
		}
		System.out.println(L.size());
	}

}
