package main;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.10.09
 **/
public class Graph {
	private final Map<Integer, Set<Integer>> adj = new HashMap<>();
	private final Map<Integer, Set<Integer>> rev = new HashMap<>();

	public void addVertex(int v) {
		adj.computeIfAbsent(v, k -> new HashSet<>());
		rev.computeIfAbsent(v, k -> new HashSet<>());
	}

	public void addEdge(int v, int w) {
		addVertex(v);
		addVertex(w);
		adj.get(v).add(w);
		rev.get(w).add(v);
	}

	public Set<Integer> neighbors(int v) {
		Set<Integer> n = adj.get(v);
		if (n == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(n);
	}

	public Set<Integer> predecessors(int v) {
		Set<Integer> p = rev.get(v);
		if (p == null) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(p);
	}

	public Set<Integer> vertices() {
		return Collections.unmodifiableSet(adj.keySet());
	}

	public Set<Integer> reachableFrom(Set<Integer> starts) {
		Set<Integer> visited = new HashSet<>();
		Deque<Integer> stack = new ArrayDeque<>();
		for (int s : starts) {
			if (!adj.containsKey(s)) {
				addVertex(s);
			}
			if (visited.add(s)) {
				stack.push(s);
			}
		}
		while (!stack.isEmpty()) {
			int v = stack.pop();
			for (int w : neighbors(v)) {
				if (visited.add(w)) {
					stack.push(w);
				}
			}
		}
		return visited;
	}

	public Set<Integer> reachableTo(Set<Integer> starts) {
		Set<Integer> visited = new HashSet<>();
		Deque<Integer> stack = new ArrayDeque<>();
		for (int s : starts) {
			if (!rev.containsKey(s)) {
				addVertex(s);
			}
			if (visited.add(s)) {
				stack.push(s);
			}
		}
		while (!stack.isEmpty()) {
			int v = stack.pop();
			for (int u : predecessors(v)) {
				if (visited.add(u)) {
					stack.push(u);
				}
			}
		}
		return visited;
	}
}
