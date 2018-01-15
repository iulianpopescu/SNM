import model.Person;
import utils.DataHandler;
import utils.DataManipulator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String filePath = "data/data.json";
        List<Person> list = DataHandler.readData(filePath);
        if (list == null) {
            System.out.println("File cannot be found or no data is present.");
            return;
        }

        System.out.println(list.size() + " entries were read from file");

        DataManipulator dataManipulator = new DataManipulator(list);
        List<Person> alteredList = dataManipulator.manipulateData();
        System.out.println(alteredList.size() - list.size() + " were added as duplicates for existing entries");
        System.out.println("Total number of entries: " + alteredList.size());
        printLine();

        DataMatcher dataMatcher = new DataMatcher(alteredList);
        dataMatcher.matchDataUsingFirstNameKey();
        printResults(1, "first name", dataMatcher.getResultsFirstName());

        dataMatcher.matchDataUsingLastNameKey();
        printResults(2, "last name", dataMatcher.getResultsLastName());

        dataMatcher.matchDataUsingAddress();
        printResults(3, "address", dataMatcher.getAddressResults());
    }

    private static void printResults(int caseNumber, String keyUsed, List<Integer> results) {
        System.out.println(String.format("Case #%d: use %s as key for sorting", caseNumber, keyUsed));
        System.out.println();
        for (int i = 0; i < results.size(); i++) {
            int value = results.get(i);
            System.out.println(String.format("%d duplicates found using a window of size %d", value, i+2));
        }
        printLine();
    }

    private static void printLine() {
        System.out.println("-----------------------------------------------------------");
        System.out.println();
    }
}
