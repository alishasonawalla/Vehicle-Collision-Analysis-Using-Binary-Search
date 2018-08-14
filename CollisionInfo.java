import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class contains a main function for running the program. The program asks
 * the user for a zip code, start date and end date and validates the input. It
 * then outputs the relevant number of collisions, injuries and fatalities.
 *
 * The program also contains the code for splitting the csv file into rows.
 *
 * @author Alisha Sonawalla
 * @version 12/11/2017
 */

public class CollisionInfo {

	public static void main(String[] args) {

		// Verify that there are command line arguments
		if (args.length == 0) {
			System.err.println("Usage Error:the program expects file " + "name as an argument.");
			System.exit(0);
		}

		// File object for input file
		String path = args[0];
		File inputFileName = new File(path);

		// Ensure that file exists
		if (!inputFileName.exists()) {
			System.err.println("ERROR: the file " + path + " does not exist");
			System.exit(1);
		}

		// Create a scanner object to open the file
		Scanner collisionInput = null;

		// Try to open the file, if there are any errors then let the user know
		// that the file cannot be opened.
		try {
			collisionInput = new Scanner(inputFileName);

		} catch (FileNotFoundException e) {
			System.err.println("ERROR: the file" + path + "cannot be opened");
			System.exit(2);
		}

		// Create a CollisionsData object that will store collision records
		CollisionsData tree = new CollisionsData();

		// While the scanner reading the file has input to give, read the lines
		while (collisionInput.hasNextLine()) {
			String textLine = collisionInput.nextLine();
			// Send the lines read in by the scanner to the splitCSVLine method
			// This will return an ArrayList
			ArrayList<String> entries = splitCSVLine(textLine);
			try {
				Collision collionRecord = new Collision(entries);
				tree.add(collionRecord);
			} catch (IllegalArgumentException e) {
				continue;
			}
		}

		// Datafields that will hold input from the user
		// These will then be sent as input into the getReport function
		String zip;
		String dateBegin;
		String dateEnd;
		Date dateFormatBegin;
		Date dateFormatEnd;

		// Use scanner to get input from System.in
		Scanner userInput = new Scanner(System.in);
		// Ask the user to enter a zipcode
		System.out.println("Enter a zipcode or 'quit' to exit:\n");
		zip = userInput.nextLine();

		// While zip doesn't have quit continue asking for input
		while (!zip.equalsIgnoreCase("quit")) {
			// If the zipcode is valid ask the user for dates
			if ((zip.length() == 5) && (zip.matches(".*\\d+.*"))) {
				System.out.println("Enter a start date (MM/DD/YYYY):\n");
				dateBegin = userInput.nextLine();
				System.out.println("Enter an end date (MM/DD/YYYY):\n");
				dateEnd = userInput.nextLine();

				//Try to create the date objects to ensure they're valid inputs
				try {
					dateFormatBegin = new Date(dateBegin);
					dateFormatEnd = new Date(dateEnd);
					System.out.println(tree.getReport(zip, dateFormatBegin, dateFormatEnd) + "\n");
				}

				//If the inputs are invalid then let the user know
				catch (Exception e) {
					System.err.println("Invalid Date Format. Try Again.");
				}
			}

			//If the zip code is invalid let the user know
			else {

				System.err.println("Inavlid Zip Code");
			}

			//Ask the user for input again while he/she doesn't hit quit
			System.out.println("Enter a zipcode or 'quit' to exit:\n");
			zip = userInput.nextLine();
		}

		//Once the user hits quit, close the scanner object
		userInput.close();
	}

	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine	a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */
	static ArrayList<String> splitCSVLine(String textLine) {

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry = false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar == '\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				} else {
					insideQuotes = true;
					insideEntry = true;
				}
			} else if (Character.isWhitespace(nextChar)) {
				if (insideQuotes || insideEntry) {
					// add it to the current entry
					nextWord.append(nextChar);
				} else { // skip all spaces between entries
					continue;
				}
			} else if (nextChar == ',') {
				if (insideQuotes) { // comma inside an entry
					nextWord.append(nextChar);
				} else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			} else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
}
