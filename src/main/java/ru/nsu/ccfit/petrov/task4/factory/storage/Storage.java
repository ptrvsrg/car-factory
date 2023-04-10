package ru.nsu.ccfit.petrov.task4.factory.storage;

import java.util.ArrayDeque;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.petrov.task4.factory.product.Product;

public class Storage<T extends Product> {

    private static final Logger logger = LogManager.getLogger(Storage.class);
    private final ArrayDeque<T> products;
    private final int capacity;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayDeque<>(capacity);
    }

    private boolean isFull() {
        synchronized (products) {
            return products.size() >= capacity;
        }
    }

    private boolean isEmpty() {
        synchronized (products) {
            return products.isEmpty();
        }
    }

    public void putProduct(T product) {
        synchronized (products) {
            while (isFull()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    logger.warn(e);
                }
            }

            products.add(product);

            products.notifyAll();
        }
    }

    public T takeProduct() {
        T product;

        synchronized (products) {
            while (isEmpty()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    logger.warn(e);
                }
            }

            product = products.remove();

            products.notifyAll();

        }

        return product;
    }

    public synchronized int getProductCount() {
        return products.size();
    }
}
