package lec2_intro2;

public class DogLauncher {

	public static void main(String[] args) {
		Dog yusuf = new Dog(100);
		yusuf.makeNoise();
		Dog chester = new Dog(17);
		chester.makeNoise();
		System.out.println();

		Dog larger = Dog.maxDog(yusuf, chester);
		larger.makeNoise();

		System.out.println(Dog.binomen);
	}

}
