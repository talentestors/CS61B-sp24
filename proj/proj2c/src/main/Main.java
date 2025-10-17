package main;

import browser.NgordnetServer;
import org.slf4j.LoggerFactory;

public class Main {
	static {
		LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
	}

	public static void main(String[] args) {
		NgordnetServer hns = new NgordnetServer();

//        /* The following code might be useful to you.
		String wordFile = "./data/ngrams/top_14377_words.csv";
		String countFile = "./data/ngrams/total_counts.csv";
		String synSetFile = "./data/wordnet/synsets.txt";
		String hyponymFile = "./data/wordnet/hyponyms.txt";
//        */

		HyponymsHandler hym = (HyponymsHandler) AutograderBuddy.getHyponymsHandler(wordFile, countFile, synSetFile, hyponymFile);

		hns.startUp();
		hns.register("history", new HistoryHandler(hym.getNgm()));
		hns.register("historytext", new HistoryTextHandler(hym.getNgm()));
		hns.register("hyponyms", hym);

		System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
	}
}
