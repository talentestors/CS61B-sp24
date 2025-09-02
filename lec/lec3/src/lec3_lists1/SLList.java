package lec3_lists1;

public class SLList {

	private static class IntNode {
		public int item;
		public IntNode next;

		public IntNode(int f, IntNode r) {
			item = f;
			next = r;
		}
	}

	// The first item (if it exists) is at sentinel.next.
	private final IntNode sentinel;
	private int size;

	/* Creates a new SLList with one item, x. */
	public SLList(int x) {
		this();
		sentinel.next = new IntNode(x, null);
		size = 1;
	}

	/* Creates an empty SLList. */
	public SLList() {
		sentinel = new IntNode(0, null);
		size = 0;
	}

	/* Adds item x to the front of the list. */
	public void addFirst(int x) {
		sentinel.next = new IntNode(x, sentinel.next);
		size += 1;
	}

	/* Gets the first item in the list. */
	public int getFirst() {
		return sentinel.next.item;
	}

	/* Add x to the end of the list. */
	public void addLast(int x) {
		size += 1;
		IntNode p = sentinel.next;
		// Scan p until it reaches the end of the list.
		while (p.next != null) {
			p = p.next;
		}
		p.next = new IntNode(x, null);
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
		SLList L = new SLList();
		L.addFirst(10);
		L.addFirst(15);
		L.addLast(20);
		System.out.println("L.size() = " + L.size());
		System.out.println("L.getFirst() = " + L.getFirst());
	}

}
