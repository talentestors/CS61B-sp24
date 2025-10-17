package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author talentestors
 * @version 1.0
 * @since 2025.09.23
 **/
public class HistoryHandler extends NgordnetQueryHandler {

	private final NGramMap ngramMap;

	public HistoryHandler(NGramMap ngramMap) {
		this.ngramMap = ngramMap;
	}

	@Override
	public String handle(NgordnetQuery q) {
		List<String> words = q.words();
		int startYear = q.startYear();
		int endYear = q.endYear();

		System.out.println("Got query that looks like:");
		System.out.println("Words: " + words);
		System.out.println("Start Year: " + startYear);
		System.out.println("End Year: " + endYear);

		ArrayList<TimeSeries> lts = new ArrayList<>();
		ArrayList<String> labels = new ArrayList<>();

		for (String word : words) {
			TimeSeries wordHistory = ngramMap.countHistory(word, startYear, endYear);
			if (wordHistory == null || wordHistory.isEmpty()) {
				continue;
			}
			labels.add(word);
			lts.add(wordHistory);
		}

		XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
		String encodedImage = Plotter.encodeChartAsString(chart);

		return encodedImage;
	}

}
