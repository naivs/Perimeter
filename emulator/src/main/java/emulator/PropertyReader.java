package emulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyReader {

    private static final String propertyFile = PropertyReader.class.getResource("/config.properties").getPath();

    public Map<String, String> readProperties(String deviceName) {
        Properties properties = new Properties();
        Map<String, String> propertyMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(propertyFile)) {
            properties.load(fis);

            for (Object key : properties.keySet()) {
                if (key.toString().contains(deviceName)) {
                    propertyMap.put(String.valueOf(key).split("\\.")[1], properties.getProperty(key.toString()));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println(String.format("Property file: %s is not found", propertyFile));
        } catch (IOException e) {
            System.err.println("IO error in: " + this.getClass().getName());
        }

        return propertyMap;
    }
}
