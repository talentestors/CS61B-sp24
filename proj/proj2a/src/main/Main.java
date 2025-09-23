package main;

import browser.NgordnetServer;
import ngrams.NGramMap;
import org.slf4j.LoggerFactory;
import utils.Utils;

import static utils.Utils.TOTAL_COUNTS_FILE;

public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    /* Do not delete or modify the code above! */

    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        /* The following code might be useful to you.*/

        NGramMap ngm = null;
//		ngm = new NGramMap(SHORT_WORDS_FILE, TOTAL_COUNTS_FILE);
//        ngm = new NGramMap(Utils.Q_WORDS_FILE, TOTAL_COUNTS_FILE);
        ngm = new NGramMap(Utils.TOP_14337_WORDS_FILE, TOTAL_COUNTS_FILE);

        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));

        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet_2a.html");
    }
}
