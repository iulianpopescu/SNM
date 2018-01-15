package model;


import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import utils.LevenshteinDistanceHelper;

/**
 * Created by iulian_popescu
 */
public class Person {

    @Getter @Setter
    @SerializedName("ssn")
    private String ssn;

    @Getter @Setter
    @SerializedName("first_name")
    private String firstName;

    @Getter @Setter
    @SerializedName("last_name")
    private String lastName;

    @Getter @Setter
    @SerializedName("street")
    private String street;

    @Getter @Setter
    @SerializedName("street_number")
    private int streetNumber;

    @Getter @Setter
    @SerializedName("city")
    private String city;

    @Getter @Setter
    @SerializedName("state")
    private String state;

    // Copy constructor
    public Person(Person person) {
        ssn = person.ssn;
        firstName = person.firstName;
        lastName = person.lastName;
        street = person.street;
        streetNumber = person.streetNumber;
        city = person.city;
        state = person.state;
    }

    /**
     * Check if the current instance and the given parameter are equal
     * @param person to check for equality
     * @return true if they are equal, false otherwise
     */
    public boolean match(Person person) {
        int firstNameDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(firstName, person.firstName);
        int lastNameDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(lastName, person.lastName);

        return firstNameDiff <= 3
                && lastNameDiff <= 3
                && matchAddress(person);
    }

    /**
     * Checks if the current instance and the given parameter have the same address
     * @param person to check the address
     * @return true if the addresses match, or false otherwise
     */
    private boolean matchAddress(Person person) {
        int stateLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(state, person.state);
        if (stateLevDiff <= 2) {
            int cityLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(city, person.city);
            if (cityLevDiff <= 2) {
                int streetLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(street, person.street);
                return streetLevDiff <= 3 && streetNumber == person.streetNumber;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return  ssn + ", " +
                firstName + " " +
                lastName + ", " +
                street + " " +
                streetNumber + ", " +
                city + ", " +
                state;
    }
}
