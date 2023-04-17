package ru.nsu.ccfit.petrov.task4.factory.config;

import java.io.InputStream;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FactoryConfig {

    private static final String FACTORY_PROPERTIES_FILE = "factory.properties";
    private static final Properties factorySettings;

    private FactoryConfig() {
        throw new IllegalStateException("Utility class");
    }

    static {
        log.info("Read configuration file with factory settings");
        try (InputStream configStream = FactoryConfig.class.getClassLoader().getResourceAsStream(
            FACTORY_PROPERTIES_FILE)) {
            if (configStream == null) {
                throw new NullPointerException("Resource is not found");
            }

            factorySettings = new Properties();
            factorySettings.load(configStream);
        } catch (Exception e) {
            log.fatal(e);
            throw new RuntimeException(e);
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            log.fatal(e);
            throw new RuntimeException(e);
        }
    }

    public static int getEngineStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Engine.Capacity"));
    }

    public static int getBodyStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Body.Capacity"));
    }

    public static int getAccessoryStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Accessory.Capacity"));
    }

    public static int getCarStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Car.Capacity"));
    }

    public static int getAccessorySupplierCount() {
        return parseInt(factorySettings.getProperty("Supplier.Accessory.Count"));
    }

    public static int getWorkerCount() {
        return parseInt(factorySettings.getProperty("Worker.Count"));
    }

    public static int getDealerCount() {
        return parseInt(factorySettings.getProperty("Dealer.Count"));
    }
}
