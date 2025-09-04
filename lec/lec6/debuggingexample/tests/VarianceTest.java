import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class VarianceTest {

	@Test
	public void testVarianceLectureWhyAmIDoingThis() {
		double[] input = {10, 20, 30, 40};
		double expected = 125.0;
		double actual = Variance.variance(input);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testEmptyList() {
		double[] input = {};
		double output = Variance.variance(input);
		double expected = 0.0;
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testRepeatedNumber() {
		double[] input = {61, 61, 61, 61, 61};
		double output = Variance.variance(input);
		double expected = 0.0;
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testVarianceInputNonInteger() {
		double[] input = {1.5, 2.5, 3.5, 4.5};
		double expected = 1.25;
		double output = Variance.variance(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testNavigate() {
		double[] input = {-1, -2, -3, -8};
		double expected = 7.25;
		double output = Variance.variance(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverageZero() {
		double[] input = {0};
		double expected = 0.0;
		double output = Variance.average(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverageEmpty() {
		double[] input = {};
		// not sure about this yet
		double expected = 0.0;
		double output = Variance.average(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverageRepeated() {
		double[] input = {5, 5, 5, 5, 5, 5, 5, 5, 5, 20};
		double expected = 6.5;
		double output = Variance.average(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverageOutputNotIntegers() {
		double[] input = {5, 5, 5, 5, 5, 5, 5, 24};
		double expected = 7.375;
		double output = Variance.average(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverageInputNotIntegers() {
		double[] input = {5.5, 6.5};
		double expected = 6.0;
		double output = Variance.average(input);
		assertThat(output).isEqualTo(expected);
	}

	@Test
	public void testAverage() {
		double[] input = {0, 1, 2};
		double output = Variance.average(input);
		assertThat(output).isEqualTo(1);
	}

	@Test
	public void testVariance() {
		double[] input = {1, 2, 3, 4};
		double output = Variance.variance(input);
		assertThat(output).isEqualTo(1.25);
	}

	@Test
	public void testVarianceBigTest() {
		final int INPUTSIZE = 1000;
		double[] input = new double[INPUTSIZE];
		for (int i = 1; i <= INPUTSIZE; i++) {
			input[i - 1] = Math.pow(i, 3);
		}
		double output = Variance.variance(input);
		assertThat(output).isWithin(1e+10).of(8.060726760691366e+16);
	}
}
