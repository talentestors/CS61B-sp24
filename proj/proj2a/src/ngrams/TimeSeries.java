package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

	/**
	 * If it helps speed up your code, you can assume year arguments to your NGramMap
	 * are between 1400 and 2100. We've stored these values as the constants
	 * MIN_YEAR and MAX_YEAR here.
	 */
	public static final int MIN_YEAR = 1400;
	public static final int MAX_YEAR = 2100;

	/**
	 * Constructs a new empty TimeSeries.
	 */
	public TimeSeries() {
		super();
	}

	private static int validYear(int year) {
		if (year > MAX_YEAR) {
			return MAX_YEAR;
		}
		if (year < MIN_YEAR) {
			return MIN_YEAR;
		}
		return year;
	}

	/**
	 * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
	 * inclusive of both end points.
	 */
	public TimeSeries(TimeSeries ts, int startYear, int endYear) {
		super();
		startYear = validYear(startYear);
		endYear = validYear(endYear);
		this.putAll(ts.subMap(startYear, true, endYear, true));
	}

	/**
	 * Returns all years for this TimeSeries (in any order).
	 */
	public List<Integer> years() {
		return new ArrayList<>(this.keySet());
	}

	/**
	 * Returns all data for this TimeSeries (in any order).
	 * Must be in the same order as years().
	 */
	public List<Double> data() {
		List<Integer> years = this.years();
		List<Double> data = new ArrayList<>();
		for (int year : years) {
			data.add(this.get(year));
		}
		return data;
	}

	/**
	 * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
	 * each year, sum the data from this TimeSeries with the data from TS. Should return a
	 * new TimeSeries (does not modify this TimeSeries).
	 * <p>
	 * If both TimeSeries don't contain any years, return an empty TimeSeries.
	 * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
	 * should store the value from the TimeSeries that contains that year.
	 */
	public TimeSeries plus(TimeSeries ts) {
		TimeSeries result = new TimeSeries();
		for (int year : this.keySet()) {
			result.put(year, this.get(year));
		}
		for (int year : ts.keySet()) {
			result.put(year, result.getOrDefault(year, 0.0) + ts.get(year));
		}
		return result;
	}

	/**
	 * Returns the quotient of the value for each year this TimeSeries divided by the
	 * value for the same year in TS. Should return a new TimeSeries (does not modify this
	 * TimeSeries).
	 * <p>
	 * If TS is missing a year that exists in this TimeSeries, throw an
	 * IllegalArgumentException.
	 * If TS has a year that is not in this TimeSeries, ignore it.
	 */
	public TimeSeries dividedBy(TimeSeries ts) {
		TimeSeries result = new TimeSeries();
		for (int year : this.keySet()) {
			if (!ts.containsKey(year)) {
				throw new IllegalArgumentException("TS is missing a year that exists in this TimeSeries");
			}
			result.put(year, this.get(year) / ts.get(year));
		}
		return result;
	}

}
