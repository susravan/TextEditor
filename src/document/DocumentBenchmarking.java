package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking {

	// Benchmarks the standard document and efficient document's implementations
	public static void main(String [] args) {
		int trials = 100;
	    String textfile = "data/warAndPeace.txt";
		int increment = 20000;	//Increment by these many characters each time
		int numSteps = 10;		// Number of steps/ iterations
		int start = 50000;
		
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment)
		{
			// numToCheck holds the number of characters that should be read from the 
			// file to create both a BasicDocument and an EfficientDocument
			String docContent = getStringFromFile(textfile, numToCheck);
			System.out.print(numToCheck + "\t");
			
			long startTime = System.nanoTime();
			for(int i=0; i < trials; i++) {
				BasicDocument bDoc = new BasicDocument(docContent);
				bDoc.getFleschScore();
			}
			long endTime = System.nanoTime();
			System.out.print(((double) (endTime - startTime)/1000000000) + "\t");
			
			startTime = System.nanoTime();
			for(int i=0; i < trials; i++) {
				EfficientDocument eDoc = new EfficientDocument(docContent);
				eDoc.getFleschScore();
			}
			endTime = System.nanoTime();
			System.out.println(((double) (endTime - startTime)/1000000000));
		}
	
	}
	
	/** Get a specified number of characters from a text file
	 * 
	 * @param filename The file to read from
	 * @param numChars The number of characters to read
	 * @return The text string from the file with the appropriate number of characters
	 */
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			// Get file input stream and input stream reader
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			
			// Till the file end or the count is reached
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char) val);
				count++;
			}

			// File ends first
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e)
		{
		  System.out.println(e);
		  System.exit(0);
		}
		
		return s.toString();
	}
	
}
