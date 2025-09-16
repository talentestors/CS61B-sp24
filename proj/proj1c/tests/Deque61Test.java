import deque.Deque61B;
import deque.LinkedListDeque61B;
import deque.MaxArrayDeque61B;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.16
 **/
public class Deque61Test {

	@Test
	public void testLinkedEquals() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
			lld2.addLast(i);
		}
		Assert.assertEquals(lld1, lld2);
		Assert.assertTrue(lld1.equals(lld2));
		Assert.assertTrue(lld2.equals(lld1));
	}

	@Test
	public void testLinkedHashCode() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
			lld2.addLast(i);
		}
		Assert.assertEquals(lld1.hashCode(), lld2.hashCode());
	}

	@Test
	public void testLinkedToString() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
		}
		Assert.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", lld1.toString());
	}

	@Test
	public void testArrayEquals() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
			lld2.addLast(i);
		}
		Assert.assertEquals(lld1, lld2);
		Assert.assertTrue(lld1.equals(lld2));
		Assert.assertTrue(lld2.equals(lld1));
	}

	@Test
	public void testArrayHashCode() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
			lld2.addLast(i);
		}
		Assert.assertEquals(lld1.hashCode(), lld2.hashCode());
	}

	@Test
	public void testArrayToString() {
		Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
		for (int i = 1; i <= 10; i++) {
			lld1.addLast(i);
		}
		Assert.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]", lld1.toString());
	}

	@Test
	public void testMaxArrayDeque61B() {
		MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
		for (int i = 1; i <= 10; i++) {
			m.addLast(i);
		}
		Assert.assertEquals(Integer.valueOf(10), m.max());
		Assert.assertEquals(Integer.valueOf(1), m.max(Comparator.reverseOrder()));
	}

}
