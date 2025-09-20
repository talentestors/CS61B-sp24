import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.20
 **/
public class BSTMap<K, V> implements Map61B<K, V> {

	private class BSTNode {
		K key;
		V value;
		BSTNode left, right;

		BSTNode(K key, V value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
		}

	}

	private BSTNode root;
	private int size;

	public BSTMap() {
		this.root = null;
		this.size = 0;
	}

	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map already contains the specified key, replaces the key's mapping
	 * with the value specified.
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void put(K key, V value) {
		BSTNode node = new BSTNode(key, value);
		BSTNode current = root;
		Comparable<K> comparable = (Comparable<K>) key;
		while (current != null) {
			if (comparable.compareTo(current.key) < 0) {
				if (current.left == null) {
					current.left = node;
					++size;
					return;
				}
				current = current.left;
			} else if (comparable.compareTo(current.key) > 0) {
				if (current.right == null) {
					current.right = node;
					++size;
					return;
				}
				current = current.right;
			} else {
				current.value = value;
				return;
			}
		}
		root = node;
		++size;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key
	 */
	@Override
	public V get(K key) {
		BSTNode current = root;
		Comparable<K> comparable = (Comparable<K>) key;
		while (current != null) {
			if (comparable.compareTo(current.key) < 0) {
				current = current.left;
			} else if (comparable.compareTo(current.key) > 0) {
				current = current.right;
			} else {
				return current.value;
			}
		}
		return null;
	}

	/**
	 * Returns whether this map contains a mapping for the specified key.
	 *
	 * @param key
	 */
	@Override
	public boolean containsKey(K key) {
		BSTNode current = root;
		Comparable<K> comparable = (Comparable<K>) key;
		while (current != null) {
			if (comparable.compareTo(current.key) < 0) {
				current = current.left;
			} else if (comparable.compareTo(current.key) > 0) {
				current = current.right;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes every mapping from this map.
	 */
	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
	}

	/**
	 * Returns a Set view of the keys contained in this map. Not required for Lab 7.
	 * If you don't implement this, throw an UnsupportedOperationException.
	 */
	@Override
	public Set<K> keySet() {
		BSTNode current = root;
		if (current == null) {
			return Set.of();
		}
		Stack<BSTNode> stack = new Stack<>();
		stack.push(root);
		TreeSet<K> treeSet = new TreeSet<>();
		while (!stack.isEmpty()) {
			current = stack.pop();
			treeSet.add(current.key);
			if (current.right != null) {
				stack.push(current.right);
			}
			if (current.left != null) {
				stack.push(current.left);
			}
		}
		return treeSet;
	}

	/**
	 * Removes the mapping for the specified key from this map if present,
	 * or null if there is no such mapping.
	 * Not required for Lab 7. If you don't implement this, throw an
	 * UnsupportedOperationException.
	 *
	 * @param key
	 */
	@Override
	public V remove(K key) {
		BSTNode current = root;
		Comparable<K> comparable = (Comparable<K>) key;
		BSTNode parent = null;
		while (current != null) {
			if (comparable.compareTo(current.key) < 0) {
				parent = current;
				current = current.left;
			} else if (comparable.compareTo(current.key) > 0) {
				parent = current;
				current = current.right;
			} else {
				break;
			}
		}
		if (current == null) {
			return null;
		}
		V value = current.value;
		if (current.left == null && current.right == null) {
			if (parent == null) {
				root = null;
			} else if (parent.left == current) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		} else if (current.left == null) {
			if (parent == null) {
				root = current.right;
			} else if (parent.left == current) {
				parent.left = current.right;
			} else {
				parent.right = current.right;
			}
		} else if (current.right == null) {
			if (parent == null) {
				root = current.left;
			} else if (parent.left == current) {
				parent.left = current.left;
			} else {
				parent.right = current.left;
			}
		} else {
			BSTNode successorParent = current;
			BSTNode successor = current.right;
			while (successor.left != null) {
				successorParent = successor;
				successor = successor.left;
			}
			current.key = successor.key;
			current.value = successor.value;
			if (successorParent.left == successor) {
				successorParent.left = successor.right;
			} else {
				successorParent.right = successor.right;
			}
		}
		--size;
		return value;
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public Iterator<K> iterator() {
		return keySet().iterator();
	}
}
