package main;

import edu.princeton.cs.algs4.In;

import java.util.Collections;
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

	private final Map<Integer, Set<Integer>> adjList;
	private final Map<String, Set<Integer>> wordToIds;
	private final Map<Integer, Set<String>> idToWords;

	/**
	 * Creates a new empty Graph.
	 */
	public Graph() {
		this.adjList = new HashMap<>();
		this.wordToIds = new HashMap<>();
		this.idToWords = new HashMap<>();
	}

	public void addEdge(int hypernymId, int hyponymId) {
		adjList.computeIfAbsent(hypernymId, k -> new HashSet<>()).add(hyponymId);
	}

	private void dfs(int wordId, Set<Integer> result) {
		if (!adjList.containsKey(wordId)) {
			return;
		}
		for (int neighborId : adjList.get(wordId)) {
			if (result.add(neighborId)) {
				dfs(neighborId, result);
			}
		}
	}

	public Set<String> getWordsById(int id) {
		return idToWords.getOrDefault(id, Collections.emptySet());
	}

	public Set<Integer> getIdsByWord(String word) {
		return wordToIds.getOrDefault(word, Collections.emptySet());
	}

	public Set<Integer> getHyponyms(int wordId) {
		Set<Integer> result = new HashSet<>();
		result.add(wordId);
		dfs(wordId, result);
		return result;
	}

	public Set<String> getHyponyms(String word) {
		Set<String> result = new HashSet<>();
		Set<Integer> ids = this.getIdsByWord(word);
		for (Integer id : ids) {
			Set<Integer> hyponymIds = this.getHyponyms(id);
			for (Integer hyponymId : hyponymIds) {
				result.addAll(this.getWordsById(hyponymId));
			}
		}
		return result;
	}

	public void loadFromFiles(String synsetsFile, String hyponymsFile) {
		In in = new In(synsetsFile);
		while (in.hasNextLine()) {
			String line = in.readLine();
			String[] parts = line.split(",");
			int id = Integer.parseInt(parts[0]);
			String[] words = parts[1].split(" ");
			for (String word : words) {
				wordToIds.computeIfAbsent(word, k -> new HashSet<>()).add(id);
				idToWords.computeIfAbsent(id, k -> new HashSet<>()).add(word);
			}
		}
		in.close();
		in = new In(hyponymsFile);
		while (in.hasNextLine()) {
			String line = in.readLine();
			String[] parts = line.split(",");
			int hypernymId = Integer.parseInt(parts[0]);
			for (int i = 1; i < parts.length; i++) {
				int hyponymId = Integer.parseInt(parts[i]);
				this.addEdge(hypernymId, hyponymId);
			}
		}
	}

}
