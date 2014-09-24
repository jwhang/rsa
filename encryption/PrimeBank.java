package encryption;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class PrimeBank {
	/**
	 * writing() creates a a .txt file at the specified location with a different prime number on every 
	 * different line. 
	 */
	public void writing() {
		try {
			File bank = new File("/Users/joelwhang/Documents/workspace/rsa/encryption/PrimeBank.txt");
			FileOutputStream is = new FileOutputStream(bank);
			OutputStreamWriter osw = new OutputStreamWriter(is);    
			Writer w = new BufferedWriter(osw);
			int i = 1000000; //number of primes desired
			int x = i;
			int n = 1000000001; // n has to be an odd number
			while(i > 0) {
				if(x != i) {
					x = i;
				}
				n = PrimeGen2.next(n);
				w.write(Integer.toString(n));
				((BufferedWriter) w).newLine();
				i--;
				
			}
			w.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file statsTest.txt");
		}
	}
	
	public static void main(String[]args) {
		PrimeBank write = new PrimeBank();
		write.writing();
	}
}

