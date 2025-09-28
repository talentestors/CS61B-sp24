package hashmap;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

	/**
	 * Protected helper class to store key/value pairs
	 * The protected qualifier allows subclass access
	 */
	protected class Node {
		K key;
		V value;

		Node(K k, V v) {
			key = k;
			value = v;
		}

		@Override
		public int hashCode() {
			return key.hashCode();
		}
	}

	/* Instance Variables */
	private Collection<Node>[] buckets;
	// You should probably define some more!
	protected final double loadFactor;
	protected int size;
	protected int capacity;

	protected static final int MIN_TREEIFY_CAPACITY = 64;
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final double DEFAULT_LOAD_FACTOR = 0.75;
	static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * Constructors
	 */
	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * MyHashMap constructor that creates a backing array of initialCapacity.
	 * The load factor (# items / # buckets) should always be <= loadFactor
	 *
	 * @param initialCapacity initial size of backing array
	 * @param loadFactor      maximum load factor
	 */
	public MyHashMap(int initialCapacity, double loadFactor) {
		if (initialCapacity <= 0 || initialCapacity > MAXIMUM_CAPACITY) {
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		}
		if (loadFactor <= 0 || Double.isNaN(loadFactor)) {
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
		}
		capacity = initialCapacity;
		//noinspection unchecked
		buckets = (Collection<Node>[]) new Collection[capacity];
		for (int i = 0; i < capacity; i++) {
			buckets[i] = createBucket();
		}
		this.loadFactor = loadFactor;
		size = 0;
	}

	protected class TreeBucket<T> extends AbstractCollection<T> {
		private final ArrayList<T> data;

		public TreeBucket() {
			this.data = new ArrayList<>();
		}

		@Override
		public Iterator<T> iterator() {
			return data.iterator();
		}

		@Override
		public int size() {
			return data.size();
		}

		private int compareKeys(K a, K b) {
			if (a.equals(b)) {
				return 0;
			}
			int h1 = a.hashCode();
			int h2 = b.hashCode();
			if (h1 != h2) {
				return Integer.compare(h1, h2);
			}
			return Integer.compare(System.identityHashCode(a), System.identityHashCode(b));
		}

		public T get(K key) {
			int lo = 0, hi = data.size() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) >>> 1;
				@SuppressWarnings("unchecked")
				K midKey = ((Node) data.get(mid)).key;
				if (midKey.equals(key)) {
					return data.get(mid);
				}
				int cmp = compareKeys(midKey, key);
				if (cmp < 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			return null;
		}

		@Override
		public boolean add(T t) {
			@SuppressWarnings("unchecked")
			K key = ((Node) t).key;
			int lo = 0, hi = data.size() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) >>> 1;
				@SuppressWarnings("unchecked")
				K midKey = ((Node) data.get(mid)).key;
				if (midKey.equals(key)) {
					data.set(mid, t);
					return false;
				}
				int cmp = compareKeys(midKey, key);
				if (cmp < 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			data.add(lo, t);
			return true;
		}

		@Override
		public boolean remove(Object o) {
			if (!(o instanceof MyHashMap.Node)) {
				return false;
			}
			@SuppressWarnings("unchecked")
			Node node = (Node) o;
			K key = node.key;
			int lo = 0, hi = data.size() - 1;
			while (lo <= hi) {
				int mid = (lo + hi) >>> 1;
				@SuppressWarnings("unchecked")
				K midKey = ((Node) data.get(mid)).key;
				if (midKey.equals(key)) {
					data.remove(mid);
					return true;
				}
				int cmp = compareKeys(midKey, key);
				if (cmp < 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			}
			return false;
		}
	}


	/**
	 * Returns a data structure to be a hash table bucket
	 * <p>
	 * The only requirements of a hash table bucket are that we can:
	 * 1. Insert items (`add` method)
	 * 2. Remove items (`remove` method)
	 * 3. Iterate through items (`iterator` method)
	 * Note that that this is referring to the hash table bucket itself,
	 * not the hash map itself.
	 * <p>
	 * Each of these methods is supported by java.util.Collection,
	 * Most data structures in Java inherit from Collection, so we
	 * can use almost any data structure as our buckets.
	 * <p>
	 * Override this method to use different data structures as
	 * the underlying bucket type
	 * <p>
	 * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
	 * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
	 */
	protected Collection<Node> createBucket() {
		if (MIN_TREEIFY_CAPACITY <= capacity) {
			return new TreeBucket<Node>();
		} else {
			return new ArrayList<Node>();
		}
	}

	private boolean isTreeified() {
		return capacity >= MIN_TREEIFY_CAPACITY;
	}

	private int getBucketIndex(K key) {
		return Math.floorMod(key.hashCode(), capacity);
	}

	@Override
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Key cannot be null");
		}
		int index = getBucketIndex(key);
		Collection<Node> bucket = buckets[index];
		for (Node node : bucket) {
			if (node.key.equals(key)) {
				node.value = value;
				return;
			}
		}
		bucket.add(new Node(key, value));
		size++;
		if ((double) size / capacity > loadFactor) {
			resize();
		}
	}

	private void resize() {
		int newCapacity = newCapacity();
		Collection<Node>[] oldBuckets = buckets;
		capacity = newCapacity;
		//noinspection unchecked
		Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[newCapacity];
		for (int i = 0; i < newCapacity; i++) {
			newBuckets[i] = createBucket();
		}
		for (Collection<Node> bucket : oldBuckets) {
			for (Node node : bucket) {
				int newIndex = Math.floorMod(node.key.hashCode(), capacity);
				newBuckets[newIndex].add(node);
			}
		}
		buckets = newBuckets;
	}

	private int newCapacity() {
		int tempCapacity = capacity;
		tempCapacity |= tempCapacity >>> 1;
		tempCapacity |= tempCapacity >>> 2;
		tempCapacity |= tempCapacity >>> 4;
		tempCapacity |= tempCapacity >>> 8;
		tempCapacity |= tempCapacity >>> 16;
		return tempCapacity >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : tempCapacity + 1;
	}

	private Node getNode(K key) {
		int index = getBucketIndex(key);
		Collection<Node> bucket = buckets[index];
		if (bucket instanceof TreeBucket) {
			return ((TreeBucket<Node>) bucket).get(key);
		} else {
			for (Node node : bucket) {
				if (node.key.equals(key)) {
					return node;
				}
			}
		}
		return null;
	}

	@Override
	public V get(K key) {
		Node node = getNode(key);
		if (node == null) {
			return null;
		}
		return node.value;
	}

	@Override
	public boolean containsKey(K key) {
		return getNode(key) != null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		//noinspection unchecked
		buckets = (Collection<Node>[]) new Collection[capacity];
		for (int i = 0; i < capacity; i++) {
			buckets[i] = createBucket();
		}
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		Set<K> set = new HashSet<>();
		for (int i = 0; i < capacity; i++) {
			for (Node node : buckets[i]) {
				set.add(node.key);
			}
		}
		return set;
	}

	@Override
	public V remove(K key) {
		int index = getBucketIndex(key);
		Collection<Node> bucket = buckets[index];
		if (bucket instanceof TreeBucket) {
			Node removed = ((TreeBucket<Node>) bucket).get(key);
			if (removed != null) {
				bucket.remove(removed);
				size--;
				return removed.value;
			}
			return null;
		}
		Iterator<Node> iterator = bucket.iterator();
		while (iterator.hasNext()) {
			Node node = iterator.next();
			if (node.key.equals(key)) {
				iterator.remove();
				size--;
				return node.value;
			}
		}
		return null;
	}

	@Override
	public Iterator<K> iterator() {
		return keySet().iterator();
	}
}
