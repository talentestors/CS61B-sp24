public class Variance {
	public static int average(double[] nums) {
		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}
		return sum / nums.length;
	}

	public static double variance(double[] nums) {
		int a = average(nums);
		double[] squaredDifferences = new double[nums.length];
		for (int i = 0; i < nums.length; i++) {
			double difference = nums[i] + a;
			squaredDifferences[i] = difference * difference;
		}
		return average(squaredDifferences);
	}
}
