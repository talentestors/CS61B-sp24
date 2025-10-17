package main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.10.17
 **/
public class WordNet {
	private final Map<Integer, Set<String>> idToWords = new HashMap<>();
	private final Map<String, Set<Integer>> wordToIds = new HashMap<>();
	private final Graph g = new Graph();

	public WordNet(String synsetsFile, String hyponymsFile) {
		parseSynsets(synsetsFile);
		parseHyponyms(hyponymsFile);
	}

	private void parseSynsets(String synsetsFile) {
		In in = new In(synsetsFile);
		String line;
		while ((line = in.readLine()) != null) {
			String[] parts = line.split(",");
			int id = Integer.parseInt(parts[0]);
			String synset = parts[1];

			Set<String> set = new HashSet<>();
			for (String word : splitWords(synset)) {
				set.add(word);
				wordToIds.computeIfAbsent(word, k -> new HashSet<>()).add(id);
			}
			idToWords.put(id, set);
			g.addVertex(id);
		}
	}

	/**
	 * Splits a line of synset words into individual words, removing commas.
	 */
	private List<String> splitWords(String synset) {
		List<String> result = new ArrayList<>();
		StringBuilder current = new StringBuilder();

		for (int i = 0; i < synset.length(); i++) {
			char c = synset.charAt(i);

			if (c == ' ') {
				if (!current.isEmpty()) {
					result.add(cleanWord(current));
					current.setLength(0);
				}
			} else {
				current.append(c);
			}
		}
		// add last word
		if (!current.isEmpty()) {
			result.add(cleanWord(current));
		}

		return result;
	}

	/**
	 * Removes commas from a word (no regex or built-ins).
	 */
	private String cleanWord(CharSequence s) {
		StringBuilder cleaned = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ',') {
				cleaned.append(c);
			}
		}
		return cleaned.toString();
	}

	private void parseHyponyms(String path) {
		In in = new In(path);
		String line;
		while ((line = in.readLine()) != null) {
			if (line.isEmpty()) {
				continue;
			}
			String[] parts = line.split(",");
			if (parts.length < 2) {
				int v = Integer.parseInt(parts[0]);
				g.addVertex(v);
				continue;
			}
			int v = Integer.parseInt(parts[0]);
			for (int i = 1; i < parts.length; i++) {
				int w = Integer.parseInt(parts[i]);
				g.addEdge(v, w);
			}
		}
	}

	/**
	 * Returns the set of words contained in all synsets reachable from any of the given ids.
	 */
	private Set<String> wordsFromReachableIds(Set<Integer> startIds) {
		Set<Integer> reachable = g.reachableFrom(startIds);
		Set<String> out = new HashSet<>();
		for (int id : reachable) {
			Set<String> words = idToWords.get(id);
			if (words != null) {
				out.addAll(words);
			}
		}
		return out;
	}

	/**
	 * Returns the set of words contained in all synsets that can reach any of the given ids
	 * (i.e., ancestors including the starting synsets).
	 */
	private Set<String> wordsFromAncestorIds(Set<Integer> startIds) {
		Set<Integer> ancestors = g.reachableTo(startIds);
		Set<String> out = new HashSet<>();
		for (int id : ancestors) {
			Set<String> words = idToWords.get(id);
			if (words != null) {
				out.addAll(words);
			}
		}
		return out;
	}

	/**
	 * Hyponyms (including the word itself) for a single word.
	 */
	public Set<String> hyponyms(String word) {
		Set<Integer> ids = wordToIds.get(word);
		if (ids == null || ids.isEmpty()) return Collections.emptySet();
		return wordsFromReachableIds(ids);
	}

	/**
	 * Common hyponyms of all words in the list (intersection over words, not synset nodes).
	 */
	public Set<String> hyponyms(List<String> words) {
		if (words == null || words.isEmpty()) {
			return Collections.emptySet();
		}
		Iterator<String> it = words.iterator();
		Set<String> acc = new HashSet<>(hyponyms(it.next()));
		while (it.hasNext()) {
			Set<String> next = hyponyms(it.next());
			acc.retainAll(next);
			if (acc.isEmpty()) {
				break;
			}
		}
		return acc;
	}

	/**
	 * Ancestors (including the word itself) for a single word.
	 */
	public Set<String> ancestors(String word) {
		Set<Integer> ids = wordToIds.get(word);
		if (ids == null || ids.isEmpty()) return Collections.emptySet();
		return wordsFromAncestorIds(ids);
	}

	/**
	 * Common ancestors of all words in the list (intersection over words).
	 */
	public Set<String> ancestors(List<String> words) {
		if (words == null || words.isEmpty()) {
			return Collections.emptySet();
		}
		Iterator<String> it = words.iterator();
		Set<String> acc = new HashSet<>(ancestors(it.next()));
		while (it.hasNext()) {
			Set<String> next = ancestors(it.next());
			acc.retainAll(next);
			if (acc.isEmpty()) {
				break;
			}
		}
		return acc;
	}
}
