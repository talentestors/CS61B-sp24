package lec2_intro2;

public class Dog {

	public int weightInPounds;
	public static String binomen = "Canis familiaris";

	public Dog(int weightInPounds) {
		this.weightInPounds = weightInPounds;
	}

	public void makeNoise() {
		if (weightInPounds < 10) {
			System.out.println("YipYipYip!");
		} else if (weightInPounds < 30) {
			System.out.println("Bark!");
		} else {
			System.out.println("Woof!");
		}
	}

	public static Dog maxDog(Dog d1, Dog d2) {
		if (d1.weightInPounds > d2.weightInPounds) {
			return d1;
		} else {
			return d2;
		}
	}

	public Dog maxDog(Dog d2) {
		if (this.weightInPounds > d2.weightInPounds) {
			return this;
		} else {
			return d2;
		}
	}

}
