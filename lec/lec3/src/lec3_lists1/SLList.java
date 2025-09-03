package lec3_lists1;

public class SLList<T> {

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
	private int size;

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
	public void addFirst(T x) {
		sentinel.next = new IntNode<>(x, sentinel.next);
		size += 1;
	}

	/* Gets the first item in the list. */
	public T getFirst() {
		return sentinel.next.item;
	}

	/* Add x to the end of the list. */
	public void addLast(T x) {
		size += 1;
		IntNode<T> p = sentinel.next;
		// Scan p until it reaches the end of the list.
		while (p.next != null) {
			p = p.next;
		}
		p.next = new IntNode<>(x, null);
	}

	public int size() {
		return size;
	}

	/* Returns the size of the list using...recursion! */
//	private int size(IntNode p) {
//		if (p.next == null) {
//			return 1;
//		}
//		return 1 + size(p.next);
//	}

	public static void main(String[] args) {
		SLList<Integer> L = new SLList<>();
		L.addFirst(10);
		L.addFirst(15);
		L.addLast(20);
		System.out.println("L.size() = " + L.size());
		System.out.println("L.getFirst() = " + L.getFirst());

		SLList<String> S = new SLList<>();
		S.addFirst("hello");
		S.addLast("world");
		System.out.println("S.size() = " + S.size());
		System.out.println("S.getFirst() = " + S.getFirst());
	}

}
