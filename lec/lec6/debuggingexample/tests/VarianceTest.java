import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class VarianceTest {
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
			input[i - 1] = i ^ 3;
		}
		double output = Variance.variance(input);
		assertThat(output).isWithin(1e+10).of(8.060726760691366e+16);
	}
}
