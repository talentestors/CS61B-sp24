package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author talentestors
 */
public class HyponymsHandler extends NgordnetQueryHandler {

	private final Graph wordGraph;

	public HyponymsHandler(Graph wordGraph) {
		this.wordGraph = wordGraph;
	}

	@Override
	public String handle(NgordnetQuery q) {
		// Get the word from the query
		List<String> words = q.words();
		// Initialize the intersection with the hyponym of the first word
		Set<String> commonHyponyms = wordGraph.getHyponyms(words.getFirst());

		for (int i = 1; i < words.size(); i++) {
			Set<String> currentHyponyms = wordGraph.getHyponyms(words.get(i));
			if (currentHyponyms != null) {
				commonHyponyms.retainAll(currentHyponyms);
			} else {
				commonHyponyms.clear();
				break;
			}
		}

		// Sort the hyponyms alphabetically
		TreeSet<String> sortedHyponyms = new TreeSet<>(commonHyponyms);

		return sortedHyponyms.toString();
	}
}
