package ru.nsu.ccfit.petrov.task4.factory.storage;

import java.util.ArrayDeque;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Product;
import ru.nsu.ccfit.petrov.task4.observer.Observable;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;

@Log4j2
public class Storage<T extends Product>
    extends Observable {

    private final ArrayDeque<T> products;
    private final int capacity;
    private int totalProductCount = 0;

    public Storage(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayDeque<>(capacity);
    }

    public int getCurrentProductCount() {
        synchronized (products) {
            return products.size();
        }
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
            // Wait for storage to be released
            while (isFull()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    log.warn(e);
                }
            }

            // Add product to storage
            products.add(product);
            totalProductCount++;

            // Allow others to add products to storage
            products.notifyAll();

            // Notify observers about storage state changing
            notifyObservers(
                new StorageMovingContext(capacity, getCurrentProductCount(), totalProductCount));
        }
    }

    public T takeProduct() {
        T product;

        synchronized (products) {
            // Wait for storage to fill up
            while (isEmpty()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    log.warn(e);
                }
            }

            // Take product from storage
            product = products.remove();

            // Allow others to take products from storage
            products.notifyAll();

            // Notify observers about storage state changing
            notifyObservers(
                new StorageMovingContext(capacity, getCurrentProductCount(), totalProductCount));
        }

        return product;
    }
}
