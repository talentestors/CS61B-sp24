package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author talentestors
 */
public class HyponymsHandler extends NgordnetQueryHandler {
	private final WordNet wn;
	private final NGramMap ngm;

	/**
	 * Preferred constructor: pass a prepared WordNet.
	 */
	public HyponymsHandler(WordNet wn) {
		this.wn = wn;
		this.ngm = null;
	}

	/**
	 * Compatibility constructor for AutograderBuddy.java: 4 files where only the last two are used here.
	 */
	public HyponymsHandler(String wordsFile, String totalCountsFile, String synsetsFile, String hyponymsFile) {
		this.wn = new WordNet(synsetsFile, hyponymsFile);
		this.ngm = new NGramMap(wordsFile, totalCountsFile);
	}

	/**
	 * Direct constructor for convenience in testing.
	 */
	public HyponymsHandler(String synsetsFile, String hyponymsFile) {
		this.wn = new WordNet(synsetsFile, hyponymsFile);
		this.ngm = null;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		Set<String> result;
		if (q.ngordnetQueryType() == NgordnetQueryType.ANCESTORS) {
			result = computeAncestors(words);
		} else {
			result = computeHyponyms(words);
		}

		int k = q.k();
		if (k != 0) {
			List<String> topK = topKByCounts(result, q.startYear(), q.endYear(), k);
			Collections.sort(topK);
			return formatAsList(topK);
		}

		List<String> sorted = new ArrayList<>(result);
		Collections.sort(sorted);
		return formatAsList(sorted);
	}

	private Set<String> computeHyponyms(List<String> words) {
		if (words == null || words.isEmpty()) {
			return Collections.emptySet();
		}
		if (words.size() == 1) {
			String w = words.getFirst();
			if (w == null) {
				return Collections.emptySet();
			}
			return wn.hyponyms(w);
		} else {
			return wn.hyponyms(words);
		}
	}

	private Set<String> computeAncestors(List<String> words) {
		if (words == null || words.isEmpty()) {
			return Collections.emptySet();
		}
		if (words.size() == 1) {
			String w = words.getFirst();
			if (w == null) {
				return Collections.emptySet();
			}
			return wn.ancestors(w);
		} else {
			return wn.ancestors(words);
		}
	}

	/**
	 * Returns at most k words with highest total counts in [startYear, endYear], breaking ties
	 * lexicographically to ensure deterministic selection. Includes words with total == 0.
	 * After selecting, the caller will alphabetize the results.
	 */
	private List<String> topKByCounts(Set<String> candidates, int startYear, int endYear, int k) {
		if (candidates == null || candidates.isEmpty() || k <= 0) {
			return new ArrayList<>();
		}
		List<WordCount> items = createWordCountList(candidates, startYear, endYear);
		sortWordCountsByFrequency(items);
		return extractTopKWords(items, k);
	}

	/**
	 * Creates a list of WordCount objects from candidate words with their frequency counts.
	 */
	private List<WordCount> createWordCountList(Set<String> candidates, int startYear, int endYear) {
		List<WordCount> items = new ArrayList<>();
		for (String w : candidates) {
			double total = sumCounts(w, startYear, endYear);

			if (total > 0) {
				items.add(new WordCount(w, total));
			}
		}
		return items;
	}

	/**
	 * Sorts WordCount objects by count descending, then by word ascending for tie-breaking.
	 */
	private void sortWordCountsByFrequency(List<WordCount> items) {
		items.sort(Comparator
			.comparingDouble(WordCount::count).reversed()
			.thenComparing(WordCount::word));
	}

	/**
	 * Extracts the top k words from a sorted list of WordCount objects.
	 */
	private List<String> extractTopKWords(List<WordCount> items, int k) {
		int limit = Math.min(k, items.size());
		List<String> out = new ArrayList<>(limit);
		for (int i = 0; i < limit; i++) {
			out.add(items.get(i).word());
		}
		return out;
	}

	private double sumCounts(String word, int startYear, int endYear) {
		if (ngm == null) {
			return 0.0;
		}
		TimeSeries ts = ngm.countHistory(word, startYear, endYear);
		double sum = 0.0;
		for (double v : ts.data()) {
			sum += v;
		}
		return sum;
	}

	public WordNet getWn() {
		return wn;
	}

	public NGramMap getNgm() {
		return ngm;
	}

	private String formatAsList(List<String> items) {
		return "[" + String.join(", ", items) + "]";
	}

	private record WordCount(String word, double count) {
	}
}
