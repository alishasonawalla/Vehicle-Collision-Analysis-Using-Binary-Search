import java.util.ArrayList;

/**
 * This class stores information about each collision object.
 *
 * It provides a constructor that takes in an ArrayList and assigns each
 * datafield to its relevant values.
 *
 * The class implements the comparable interface.
 *
 * The class contains different accessing methods (getters) to get various data
 * fields, a compareTo, a toString and an equals method.
 *
 * @author Alisha Sonawalla
 * @version 12/11/2017
 */
public class Collision implements Comparable<Collision> {

	// Datafields to hold the values for each collision
	private String zip;
	private int personsInjured;
	private int pedestriansInjured;
	private int cyclistsInjured;
	private int motoristsInjured;
	private int personsKilled;
	private int pedestriansKilled;
	private int cyclistsKilled;
	private int motoristsKilled;
	private String key;
	private Date date;

	/**
	 * This constructor takes in an ArrayList and updates the relevant data
	 * fields. It validates all the information in the ArrayList before updating
	 * the data fields.
	 *
	 * @param entries an ArrayList with information of each collision record
	 * @throws NullPointerException,
	 *             IllegalArgumentException
	 */
	public Collision(ArrayList<String> entries) throws NullPointerException, IllegalArgumentException {
		// Validate that entries is not a null ArrayList
		if (entries == null)
			throw new IllegalArgumentException("ArrayList cannot be null");
		// Verify that the there are at least 24 entries.
		if (entries.size() < 24)
			throw new IllegalArgumentException("Invalid zip code");

		// Extract and validate the date
		if (entries.get(0) == null)
			throw new NullPointerException("The date field cannot be null");
		// Update the date
		date = new Date(entries.get(0));

		// Extract and validate Zip Code
		if (entries.get(3) != null && entries.get(3).length() == 5 && entries.get(3).matches(".*\\d+.*")) {
			zip = entries.get(3);
		} else
			throw new IllegalArgumentException("Invalid zip code");

		// Extract and validate key
		if (entries.get(23) != null && !entries.get(23).isEmpty()) {
			key = entries.get(23);
		} else
			throw new NullPointerException("Invalid Key");

		// Extract and validate persons/pedestrians/cyclists/motorists.
		if (entries.get(10) == null || Integer.valueOf(entries.get(10)) < 0)
			throw new IllegalArgumentException("Invalid Number of Persons Injured");

		personsInjured = Integer.valueOf(entries.get(10));

		if (entries.get(11) == null || Integer.valueOf(entries.get(11)) < 0)
			throw new IllegalArgumentException("Invalid Number of Persons Killed");

		personsKilled = Integer.valueOf(entries.get(11));

		if (entries.get(12) == null || Integer.valueOf(entries.get(12)) < 0)
			throw new IllegalArgumentException("Invalid Number of Pedestrians Injured");

		pedestriansInjured = Integer.valueOf(entries.get(12));

		if (entries.get(13) == null || Integer.valueOf(entries.get(13)) < 0)
			throw new IllegalArgumentException("Invalid Number of Pedestrians Killed");

		pedestriansKilled = Integer.valueOf(entries.get(13));

		if (entries.get(14) == null || Integer.valueOf(entries.get(14)) < 0)
			throw new IllegalArgumentException("Invalid Number of Cyclists Injured");

		cyclistsInjured = Integer.valueOf(entries.get(14));

		if (entries.get(15) == null || Integer.valueOf(entries.get(15)) < 0)
			throw new IllegalArgumentException("Invalid Number of Cyclists Killed");

		cyclistsKilled = Integer.valueOf(entries.get(15));

		if (entries.get(16) == null || Integer.valueOf(entries.get(16)) < 0)
			throw new IllegalArgumentException("Invalid Number of Mortorists Injured");

		motoristsInjured = Integer.valueOf(entries.get(16));

		if (entries.get(17) == null || Integer.valueOf(entries.get(17)) < 0)
			throw new IllegalArgumentException("Invalid Number of Mortorists Killed");

		motoristsKilled = Integer.valueOf(entries.get(17));
	}

	/**
	 *This method returns the Zip Code of the Collision
	 * @return zip the zip code
	 */
	public String getZip() {
		return zip;
	}

	/**
	 *This method returns the date of the Collision
	 * @return date the date in the record
	 */
	public Date getDate() {
		return date;
	}

	/**
	 *This method returns the key of the Collision
	 * @return key the key of the collision object
	 */
	public String getKey() {
		return key;
	}

	/**
	 *This method returns the number of people injured
	 * @return personsInjured number of people injured
	 */
	public int getPersonsInjured() {
		return personsInjured;
	}

	/**
	 * This method returns the number of pedestrians injured
	 * @return pedestriansInjured number of pedestrians injured
	 */
	public int getPedestriansInjured() {
		return pedestriansInjured;
	}

	/**
	 * This method returns the number of cyclists injured
	 * @return cyclistsInjured number of cyclists injured
	 */
	public int getCyclistsInjured() {
		return cyclistsInjured;
	}

	/**
	 * This method returns the number of motorists injured
	 * @return motoristsInjured number of motorists injured
	 */
	public int getMotoristsInjured() {
		return motoristsInjured;
	}

	/**
	 * This method returns the number of persons killed
	 * @return personsKilled number of persons killed
	 */
	public int getPersonsKilled() {
		return personsKilled;
	}

	/**
	 * This method returns the number of pedestrians killed
	 * @return pedestriansKilled number of pedestrians killed
	 */
	public int getPedestriansKilled() {
		return pedestriansKilled;
	}

	/**
	 * This method returns the number of cyclists killed
	 * @return cyclistsKilled number of cyclists killed
	 */
	public int getCyclistsKilled() {
		return cyclistsKilled;
	}

	/**
	 * This method returns the number of motorists killed
	 * @return motoristsKilled number of motorists killed
	 */
	public int getMotoristsKilled() {
		return motoristsKilled;
	}


	/**
	 * This method returns the collision record in a String format
	 * @return String with all the record values
	 */
	@Override
	public String toString() {
		return String.valueOf(
				zip + " " + personsInjured + " " + pedestriansInjured + " " + cyclistsInjured
				+ " " + motoristsInjured + " " + personsKilled + " " + pedestriansKilled
				+" " + cyclistsKilled + " " + motoristsKilled);
	}

	/**
	 *This method overrides the compareTo method.
	 *It compares collision objects by zip code, then by date and then by key.
	 *
	 *@param other collision object
	 *
	 * @return 0 if equal, 1 if other is smaller, or -1 if other is greater
	 */
	@Override
	public int compareTo(Collision other) {
		//First compare zip codes
		return zip.compareTo(other.getZip()) < 0 ? -1
				: zip.compareTo(other.getZip()) > 0 ? 1
						//Then compare dates
						: date.compareTo(other.getDate()) == 0 ? date.compareTo(other.getDate())
								//Finally compare the keys
								: key.compareTo(other.getKey()) < 0 ? -1 : key.compareTo(other.getKey()) > 0 ? 1 : 0;
	}

	/**
	 *This method overrides the equals method.
	 *It checks if the object passed is an instance of Collision.
	 *If it is, then compares the zip, date and key.
	 *
	 *@param other collision object
	 *
	 * @return true/false based on comparison of values
	 */
	@Override
	public boolean equals(Object other) {
		//Check if the object is an instance of collision
		if (other instanceof Collision) {
			Collision collisionObj = (Collision) other;
			//First compare zip codes, then dates, then keys
			return zip.compareTo(collisionObj.getZip()) == 0 ? date.compareTo(collisionObj.getDate()) == 0
					? key.compareTo(collisionObj.getKey()) == 0 ? true : false : false : false;
		} else {
			return false;
		}

	}
}
