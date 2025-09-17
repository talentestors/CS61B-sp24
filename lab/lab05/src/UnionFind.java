import java.util.Arrays;

public class UnionFind {

	private int[] parent;
	private int[] size;

	/* Creates a UnionFind data structure holding N items. Initially, all
	   items are in disjoint sets. */
	public UnionFind(int N) {
		parent = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
		}
		size = new int[N];
		Arrays.fill(size, 1);
	}

	/* Returns the size of the set V belongs to. */
	public int sizeOf(int v) {
		checkBounds(v);
		return size[find(v)];
	}

	/* Returns the parent of V. If V is the root of a tree, returns the
	   negative size of the tree for which V is the root. */
	public int parent(int v) {
		checkBounds(v);
		return parent[v];
	}

	private void checkBounds(int v) {
		if (v >= parent.length || v < 0) {
			throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
		}
	}

	/* Returns true if nodes/vertices V1 and V2 are connected. */
	public boolean connected(int v1, int v2) {
		return find(v2) == find(v1);
	}

	/* Returns the root of the set V belongs to. Path-compression is employed
	   allowing for fast search-time. If invalid items are passed into this
	   function, throw an IllegalArgumentException. */
	public int find(int v) {
		checkBounds(v);
		return findHelper(v);
	}

	private int findHelper(int v) {
		return v == parent[v] ? v : (parent[v] = findHelper(parent[v]));
	}

	/* Connects two items V1 and V2 together by connecting their respective
	   sets. V1 and V2 can be any element, and a union-by-size heuristic is
	   used. If the sizes of the sets are equal, tie break by connecting V1's
	   root to V2's root. Union-ing an item with itself or items that are
	   already connected should not change the structure. */
	public void union(int v1, int v2) {
		int root1 = find(v1);
		int root2 = find(v2);
		if (root1 == root2) {
			return;
		}
		if (size[root1] <= size[root2]) {
			parent[root1] = root2;
			size[root2] += size[root1];
		} else {
			parent[root2] = root1;
			size[root1] += size[root2];
		}
	}

}
