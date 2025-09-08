package lec7_lists4;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.08
 **/
public class Client {
	public static void main(String[] args) {
		int size = 16_0000;
		long startTime = System.nanoTime();
		Alist<Integer> list = new Alist<>(5);
		for (int i = 0; i < size; i++) {
			list.addLast(i);
			list.addFirst(i % Short.MAX_VALUE);
			if (i % 7 == 0) {
				list.removeLast();
			}
		}
		long endTime = System.nanoTime();
		System.out.println(Alist.class.getSimpleName() + " numbers of " + size + ", time taken: " + (double) (endTime - startTime) / 1000000 + " ms");
		System.out.println("Final size: " + list.size());
		System.out.println("Last item: " + list.getLast());
		System.out.println("First item: " + list.getFirst());
		System.out.println("5th item if exists: " + (list.size() >= 5 ? list.get(4) : "N/A"));
	}
}
