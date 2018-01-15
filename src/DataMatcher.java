import lombok.Getter;
import lombok.Setter;
import model.Person;
import utils.LevenshteinDistanceHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by iulian_popescu
 * Use this class to detect duplicated records using The Sorted Neighbourhood algorithm
 */
public class DataMatcher {
    private static final int MAX_WINDOW_SIZE = 10;

    private List<Person> initialList;

    private List<Record> firstNameList = new ArrayList<>();
    private List<Record> lastNameList = new ArrayList<>();
    private List<Record> addressNameList = new ArrayList<>();

    @Getter
    private List<Integer> resultsFirstName = new ArrayList<>();

    @Getter
    private List<Integer> resultsLastName = new ArrayList<>();

    @Getter
    private List<Integer> addressResults = new ArrayList<>();

    public DataMatcher(List<Person> initial, List<Person> modified) {
        initialList = initial;

        modified.forEach(person -> {
            firstNameList.add(new Record(person.getFirstName(), person));
            lastNameList.add(new Record(person.getLastName(), person));

            addressNameList.add(new Record(
                            new Address(person.getStreet(),
                                    person.getStreetNumber(),
                                    person.getCity(),
                                    person.getState()),
                            person));
        });
    }

    public void matchDataUsingLastNameKey() {
        matchData(lastNameList, resultsLastName);
    }

    public void matchDataUsingFirstNameKey() {
        matchData(firstNameList, resultsFirstName);
    }

    public void matchDataUsingAddress() {
        matchData(addressNameList, addressResults);
    }


    /**
     * Search a given list and mark duplicates records accordingly
     * @param list list containing the records
     * @param results results containing the number of records matched for a given window size
     */
    private void matchData(List<Record> list, List<Integer> results) {
        //noinspection unchecked
        list.sort(Comparator.comparing(Record::getKey, Comparable::compareTo));

        for (int windowSize = 2; windowSize <= MAX_WINDOW_SIZE; windowSize++) {
            for (int i = 0; i <= list.size() - windowSize; i++) {
                for (int j = i+1; j < i + windowSize; j++) {
                    Record r1 = list.get(i);
                    Record r2 = list.get(j);
                    if (r1.match(r2)) {
                        r2.setFoundDuplicate(true);
                    }
                }
            }
            final int[] count = {0};
            list.forEach(record -> {
                if (record.foundDuplicate) {
                    count[0]++;
                    record.foundDuplicate = false;
                }

            });
            results.add(count[0]);
        }
    }

    /**
     * Placeholder class to use for Sorted Neighbourhood Method
     * Uses the key field for sorting and the value field represent the current entity
     */
    private static final class Record {
        @Getter @Setter
        Comparable key;

        @Getter @Setter
        Person value;

        @Getter @Setter
        boolean foundDuplicate = false;

        Record(Comparable key, Person person) {
            this.key = key;
            value = new Person(person);
        }

        boolean match(Record record) {
            return value.match(record.getValue());
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private static final class Address implements Comparable<Address> {
        private String street;
        private int streetNumber;
        private String city;
        private String state;

        Address(String street, int streetNumber, String city, String state) {
            this.state = state;
            this.street = street;
            this.streetNumber = streetNumber;
            this.city = city;
        }

        @Override
        public int compareTo(Address o) {
            int stateLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(state, o.state);
            if (stateLevDiff <= 2) {
                int cityLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(city, o.city);
                if (cityLevDiff <= 2) {
                    int streetLevDiff = LevenshteinDistanceHelper.computeLevenshteinDistance(street, o.street);
                    if (streetLevDiff <= 3 && streetNumber == o.streetNumber) {
                        return 0;
                    }
                    return street.compareTo(o.street);
                }
                return city.compareTo(o.city);
            }
            return state.compareTo(o.state);
        }
    }
}
