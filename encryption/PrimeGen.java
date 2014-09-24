package encryption;
import java.util.ArrayList;

public class PrimeGen {
	
	int[] pList;
	int pSize;
	long time;
	
	public PrimeGen(int nPrimes) {
		pSize = nPrimes;
		pList = new int[nPrimes-1];
		pList[0] = 2;
		long t1;
		long t2;
		t1 = System.currentTimeMillis();
		int pTrack = 2;
		for(int n = 3; pTrack != pSize; n += 2) {
			for(int k = 1; k != nPrimes-1; k++) {
				if (pList[k] == 0) {
					System.out.println(pTrack);
					pList[pTrack-1] = n;
					pTrack++;
					break;
				} 
				if (n % pList[k] == 0) {
					break;
				}
			}
		}
		t2 = System.currentTimeMillis();
		time =  t2-t1;
	}
	
	public void printPrimes() {
		for (int n = 0; n != pSize - 1; n++) {
			System.out.println(pList[n]);
		}
		System.out.println("Time:" + time);
	}
	public static void main(String args[]){
		System.out.println();
		PrimeGen pGen = new PrimeGen(100000);
//		System.out.println(pGen.pList[1]);
		pGen.printPrimes();
	}
}
