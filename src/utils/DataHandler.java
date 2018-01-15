package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Person;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iulian_popescu
 * Use this class to parse instances of @class Person from a file
 */
public class DataHandler {

    private DataHandler() { }

    /**
     * Parses a file in format Json and returns a list of Person objects
     * @param filePath the path to the file
     * @return a list containing the parsed instances
     */
    public static List<Person> readData(String filePath) {
        Reader inputStreamReader = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            inputStreamReader = new InputStreamReader(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (inputStreamReader != null) {
            Gson gson = new GsonBuilder().create();
            Type listType = new TypeToken<ArrayList<Person>>() {}.getType();
            return gson.fromJson(inputStreamReader, listType);
        }
        return null;
    }
}
