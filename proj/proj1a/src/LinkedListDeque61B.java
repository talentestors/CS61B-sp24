import java.util.ArrayList;
import java.util.List;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.10
 **/
public class LinkedListDeque61B<T> implements Deque61B<T> {

	private class Node {
		T item;
		Node next;
		Node prev;

		Node(T f, Node next, Node prev) {
			item = f;
			this.next = next;
			this.prev = prev;
		}

	}

	private final Node sentinel;
	private int size;

	public LinkedListDeque61B() {
		sentinel = new Node(null, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	@Override
	public void addFirst(T x) {
		Node temp = sentinel.next;
		sentinel.next = new Node(x, sentinel.next, sentinel);
		temp.prev = sentinel.next;
		size++;
	}

	@Override
	public void addLast(T x) {
		Node temp = sentinel.prev;
		temp.next = new Node(x, sentinel, temp);
		sentinel.prev = temp.next;
		size++;
	}

	@Override
	public List<T> toList() {
		List<T> res = new ArrayList<>();
		Node curr = sentinel.next;
		while (curr != sentinel) {
			res.add(curr.item);
			curr = curr.next;
		}
		return res;
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
	public T removeFirst() {
		Node curr = sentinel.next;
		if (curr == sentinel) {
			return null;
		}
		sentinel.next = curr.next;
		curr.next.prev = sentinel;
		size--;
		return curr.item;
	}

	@Override
	public T removeLast() {
		Node curr = sentinel.prev;
		if (curr == sentinel) {
			return null;
		}
		sentinel.prev = curr.prev;
		curr.prev.next = sentinel;
		size--;
		return curr.item;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			return null;
		}
		Node curr = sentinel.next;
		while (index > 0) {
			curr = curr.next;
			index--;
		}
		return curr.item;
	}

	@Override
	public T getRecursive(int index) {
		if (index < 0 || index >= size) {
			return null;
		}
		return getRecursiveHelper(sentinel.next, index);
	}

	private T getRecursiveHelper(Node node, int index) {
		if (index == 0) {
			return node.item;
		}
		return getRecursiveHelper(node.next, index - 1);
	}

}
