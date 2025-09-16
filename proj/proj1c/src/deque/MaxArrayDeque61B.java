package deque;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.16
 **/
public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

	private Comparator<T> comparator;

	public MaxArrayDeque61B(Comparator<T> c) {
		super();
		this.comparator = c;
	}

	public MaxArrayDeque61B() {
		super();
	}

	public MaxArrayDeque61B(int size) {
		super(size);
	}

	public T max() {
		if (isEmpty()) {
			return null;
		}
		T maxItem = get(0);
		for (int i = 1; i < size(); i++) {
			T currentItem = get(i);
			if (comparator.compare(currentItem, maxItem) > 0) {
				maxItem = currentItem;
			}
		}
		return maxItem;
	}

	public T max(Comparator<T> c) {
		if (isEmpty()) {
			return null;
		}
		T maxItem = get(0);
		for (int i = 1; i < size(); i++) {
			T currentItem = get(i);
			if (c.compare(currentItem, maxItem) > 0) {
				maxItem = currentItem;
			}
		}
		return maxItem;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}
		MaxArrayDeque61B<?> that = (MaxArrayDeque61B<?>) o;
		return Objects.equals(comparator, that.comparator);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), comparator);
	}
}
