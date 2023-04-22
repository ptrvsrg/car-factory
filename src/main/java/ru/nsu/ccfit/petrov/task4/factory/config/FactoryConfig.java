package ru.nsu.ccfit.petrov.task4.factory.config;

import java.io.InputStream;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

/**
 * The type {@code FactoryConfig} is a util class that opens a config factory file, parses it and returns parameters.
 *
 * @author ptrvsrg
 */
@Log4j2
public class FactoryConfig {

    private static final String FACTORY_PROPERTIES_FILE = "factory.properties";
    private static final Properties factorySettings;

    private FactoryConfig() {
        throw new IllegalStateException("Utility class");
    }

    static {
        log.info("Read configuration file with factory settings");
        try (InputStream configStream = FactoryConfig.class.getClassLoader()
                                                           .getResourceAsStream(FACTORY_PROPERTIES_FILE)) {
            if (configStream == null) {
                throw new NullPointerException("Resource is not found");
            }

            factorySettings = new Properties();
            factorySettings.load(configStream);
        } catch (Exception e) {
            log.catching(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    private static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            log.catching(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets engine storage capacity.
     *
     * @return the engine storage capacity
     */
    public static int getEngineStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Engine.Capacity"));
    }

    /**
     * Gets body storage capacity.
     *
     * @return the body storage capacity
     */
    public static int getBodyStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Body.Capacity"));
    }

    /**
     * Gets accessory storage capacity.
     *
     * @return the accessory storage capacity
     */
    public static int getAccessoryStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Accessory.Capacity"));
    }

    /**
     * Gets car storage capacity.
     *
     * @return the car storage capacity
     */
    public static int getCarStorageCapacity() {
        return parseInt(factorySettings.getProperty("Storage.Car.Capacity"));
    }

    /**
     * Gets accessory supplier count.
     *
     * @return the accessory supplier count
     */
    public static int getAccessorySupplierCount() {
        return parseInt(factorySettings.getProperty("Supplier.Accessory.Count"));
    }

    /**
     * Gets worker count.
     *
     * @return the worker count
     */
    public static int getWorkerCount() {
        return parseInt(factorySettings.getProperty("Worker.Count"));
    }

    /**
     * Gets dealer count.
     *
     * @return the dealer count
     */
    public static int getDealerCount() {
        return parseInt(factorySettings.getProperty("Dealer.Count"));
    }
}
