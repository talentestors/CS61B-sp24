package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.23
 **/
public class HistoryTextHandler extends NgordnetQueryHandler {

	private final NGramMap ngramMap;

	public HistoryTextHandler(NGramMap ngramMap) {
		this.ngramMap = ngramMap;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();

		StringBuilder responseBuilder = new StringBuilder();
		for (String word : words) {
			responseBuilder.append(word).append(": ");
			TimeSeries timeSeries = ngramMap.countHistory(word, startYear, endYear);
			responseBuilder.append(timeSeries.toString());
			responseBuilder.append("\n");
		}
		return responseBuilder.toString();
	}

}
