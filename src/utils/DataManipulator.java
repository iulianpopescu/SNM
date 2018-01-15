package utils;

import model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by iulian_popescu
 * Use this class to alter the initial data by adding duplicates that may contain errors
 */
public class DataManipulator {
    private List<Person> recordsList = new ArrayList<>();

    public  DataManipulator(List<Person> records) {
        // The initial data doesn't have any duplicated records\
        records.forEach(person -> recordsList.add(new Person(person)));
    }

    public List<Person> manipulateData() {
        duplicateRecords();
        return recordsList;
    }

    /**
     * Add to the initial list of records a number between 30%-50% of new instances
     * and add some errors in different duplicates
     */
    private void duplicateRecords() {
        Random random = new Random();
        // Duplicate between 30% - 50% of the total number of records
        int numberOfRecordsDuplicated = (int) ((random.nextInt(21) + 30) / 100f * recordsList.size());

        for (int i = 0; i < numberOfRecordsDuplicated; i++) {
            int position = random.nextInt(recordsList.size());
            Person copy = new Person(recordsList.get(position));

            copy.setFirstName(alterString(copy.getFirstName()));
            copy.setLastName(alterString(copy.getLastName()));
            copy.setState(alterString(copy.getState()));
            copy.setStreet(alterString(copy.getStreet()));
            copy.setCity(alterString(copy.getCity()));
            recordsList.add(copy);
        }
    }

    /**
     * Alter a string by changing different values inside it
     * @param base the string to be altered
     * @return a string altered based on the given conditions
     */
    private String alterString(String base) {
        if (base.contains("pf")) {
            return base.replace("ph", "f");
        }
        if (base.contains("f")) {
            return base.replace("f", "ph");
        }
        if (base.contains("oo")) {
            return base.replace("oo", "u");
        }
        if (base.contains("u")) {
            return base.replace("u", "oo");
        }
        if (base.contains("ll")) {
            return base.replace("ll", "l");
        }
        if (base.contains("pp")) {
            return base.replace("pp", "p");
        }
        if (base.contains("ff")) {
            return base.replace("ff", "f");
        }
        if (base.contains("i")) {
            return base.replace("i", "j");
        }
        if (base.contains("c")) {
            return base.replace("c", "s");
        }
        if (base.contains("ss")) {
            return base.replace("ss", "s");
        }

        return base;
    }
}
