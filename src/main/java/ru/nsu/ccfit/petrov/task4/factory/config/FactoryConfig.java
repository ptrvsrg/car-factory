package ru.nsu.ccfit.petrov.task4.factory.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FactoryConfig {

    private static final Properties factorySettings;
    public static final String FACTORY_PROPERTIES_FILE = "factory.properties";

    private FactoryConfig() {
        throw new IllegalStateException("Utility class");
    }

    static {
        try (InputStream configStream = FactoryConfig.class.getClassLoader()
                                                    .getResourceAsStream(FACTORY_PROPERTIES_FILE)) {
            if (configStream == null) {
                throw new NullPointerException("Resource is not found");
            }

            factorySettings = new Properties();
            factorySettings.load(configStream);
            log.info("Json file with factory settings is read");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getEngineStorageCapacity() {
        return Integer.parseInt(factorySettings.getProperty("Storage.Engine.Capacity"));
    }

    public static int getBodyStorageCapacity() {
        return Integer.parseInt(factorySettings.getProperty("Storage.Body.Capacity"));
    }

    public static int getAccessoryStorageCapacity() {
        return Integer.parseInt(factorySettings.getProperty("Storage.Accessory.Capacity"));
    }

    public static int getCarStorageCapacity() {
        return Integer.parseInt(factorySettings.getProperty("Storage.Car.Capacity"));
    }

    public static int getAccessorySupplierCount() {
        return Integer.parseInt(factorySettings.getProperty("Supplier.Accessory.Count"));
    }

    public static int getWorkerCount() {
        return Integer.parseInt(factorySettings.getProperty("Worker.Count"));
    }

    public static int getDealerCount() {
        return Integer.parseInt(factorySettings.getProperty("Dealer.Count"));
    }
}
