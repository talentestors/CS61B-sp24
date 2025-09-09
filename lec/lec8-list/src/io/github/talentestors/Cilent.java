package io.github.talentestors;

import io.github.talentestors.list.impl.AList;
import io.github.talentestors.list.impl.DLList;
import io.github.talentestors.list.List61B;
import io.github.talentestors.list.impl.SLList;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.09
 **/
public class Cilent {

	public static void testList(List61B<Integer> list) {
		for (int i = 0; i < 10; i++) {
			list.insert(0, i);
		}
		list.insert(5, 100);
		list.remove(0);
		list.addFirst(-1);
		list.removeLast();
		System.out.println(list);
	}

	public static void main(String[] args) {
		List61B<Integer> arrayList = new AList<>();
		testList(arrayList);
		List61B<Integer> linkedList = new SLList<>();
		testList(linkedList);
		List61B<Integer> arrayList2 = new DLList<>();
		testList(arrayList2);
	}

}
