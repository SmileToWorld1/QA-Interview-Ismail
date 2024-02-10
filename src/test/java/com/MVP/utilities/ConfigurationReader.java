package com.MVP.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This util class used for reading a data' from configuration.properties file
 */
public class ConfigurationReader {

    private static Properties properties = new Properties(); ;
    static {
        try {
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String keyword){
        return properties.getProperty(keyword);
    }
}
