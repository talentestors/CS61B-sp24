package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

	private final Map<String, TimeSeries> wordData;
	private final TimeSeries totalCounts;

	/**
	 * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
	 */
	public NGramMap(String wordsFilename, String countsFilename) {
		wordData = new HashMap<>();
		totalCounts = new TimeSeries();

		// Read counts file first
		readCountsFile(countsFilename);

		// Read words file
		readWordsFile(wordsFilename);
	}

	/**
	 * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
	 * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
	 * words, changes made to the object returned by this function should not also affect the
	 * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
	 * returns an empty TimeSeries.
	 */
	public TimeSeries countHistory(String word, int startYear, int endYear) {
		if (!wordData.containsKey(word)) {
			return new TimeSeries();
		}
		return new TimeSeries(wordData.get(word), startYear, endYear);
	}

	/**
	 * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
	 * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
	 * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
	 * is not in the data files, returns an empty TimeSeries.
	 */
	public TimeSeries countHistory(String word) {
		if (!wordData.containsKey(word)) {
			return new TimeSeries();
		}
		return new TimeSeries(wordData.get(word), wordData.get(word).firstKey(), wordData.get(word).lastKey());
	}

	/**
	 * Returns a defensive copy of the total number of words recorded per year in all volumes.
	 */
	public TimeSeries totalCountHistory() {
		return new TimeSeries(totalCounts, totalCounts.firstKey(), totalCounts.lastKey());
	}

	/**
	 * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
	 * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
	 * TimeSeries.
	 */
	public TimeSeries weightHistory(String word, int startYear, int endYear) {
		TimeSeries wordHistory = countHistory(word, startYear, endYear);
		if (wordHistory.isEmpty()) {
			return new TimeSeries();
		}

		TimeSeries totalHistory = new TimeSeries(totalCounts, startYear, endYear);
		return wordHistory.dividedBy(totalHistory);
	}

	/**
	 * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
	 * words recorded in that year. If the word is not in the data files, returns an empty
	 * TimeSeries.
	 */
	public TimeSeries weightHistory(String word) {
		if (!wordData.containsKey(word)) {
			return new TimeSeries();
		}

		TimeSeries wordHistory = countHistory(word);
		return wordHistory.dividedBy(totalCounts);
	}

	/**
	 * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
	 * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
	 * rather than throwing an exception.
	 */
	public TimeSeries summedWeightHistory(Collection<String> words,
	                                      int startYear, int endYear) {
		TimeSeries result = new TimeSeries();

		for (String word : words) {
			TimeSeries wordWeight = weightHistory(word, startYear, endYear);
			result = result.plus(wordWeight);
		}

		return result;
	}

	/**
	 * Returns the summed relative frequency per year of all words in WORDS. If a word does not
	 * exist in this time frame, ignore it rather than throwing an exception.
	 */
	public TimeSeries summedWeightHistory(Collection<String> words) {
		TimeSeries result = new TimeSeries();

		for (String word : words) {
			TimeSeries wordWeight = weightHistory(word);
			result = result.plus(wordWeight);
		}

		return result;
	}

	private void readCountsFile(String countsFilename) {
		In in = new In(countsFilename);
		while (in.hasNextLine()) {
			String line = in.readLine();
			String[] parts = line.split(",");
			int year = Integer.parseInt(parts[0]);
			double totalCount = Double.parseDouble(parts[1]);
			totalCounts.put(year, totalCount);
		}
	}

	private void readWordsFile(String wordsFilename) {
		In in = new In(wordsFilename);
		while (in.hasNextLine()) {
			String line = in.readLine();
			String[] parts = line.split("\t");
			String word = parts[0];
			int year = Integer.parseInt(parts[1]);
			double count = Double.parseDouble(parts[2]);

			wordData.computeIfAbsent(word, k -> new TimeSeries()).put(year, count);
		}
	}
}
