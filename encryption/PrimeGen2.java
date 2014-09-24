package encryption;

public class PrimeGen2 {
	public static int primeGen(int nth) {
		long t1;
		long t2;
		
		t1 = System.currentTimeMillis();
		if (nth == 1) {
			return 2;
		}
		if (nth == 2) {
			return 3;
		}
		int prime = 5;
		for (int i = 3; i != nth; i++) {
			prime = next(prime);
		}
		t2 = System.currentTimeMillis();
		System.out.println(t2-t1);
		
		return prime;
		
	}
	public static int next(int prime) {
		prime += 2;
		for (int i = 2; i <= Math.sqrt(prime); i++) {
			if (prime % i == 0) {
				i = 1;
				prime += 2;
			} 
		}
		return prime;
	}
	public static void main(String args[]) {
		System.out.println(null == null);
	}
}
