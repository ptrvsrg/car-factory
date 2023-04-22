package ru.nsu.ccfit.petrov.task4.factory.storage;

import java.util.ArrayDeque;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Product;
import ru.nsu.ccfit.petrov.task4.observer.Observable;
import ru.nsu.ccfit.petrov.task4.observer.context.StorageMovingContext;

/**
 * The type {@code Storage} is class that describes FIFO container that stores some products.
 *
 * @param <T> the type parameter
 * @author ptrvsrg
 */
@Log4j2
public class Storage<T extends Product>
    extends Observable {

    private final ArrayDeque<T> products;
    @Getter private final int capacity;
    private int totalProductCount = 0;

    /**
     * Constructs a Storage.
     *
     * @param capacity the capacity
     */
    public Storage(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayDeque<>(capacity);
    }

    /**
     * Gets current product count.
     *
     * @return the current product count
     */
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

    /**
     * Put product.
     *
     * @param product the product
     */
    public void putProduct(T product) {
        if (product == null) {
            return;
        }

        synchronized (products) {
            // Wait for storage to be released
            while (isFull() && Thread.currentThread().isAlive()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    return;
                }
            }

            // Add product to storage
            products.add(product);
            totalProductCount++;

            // Allow others to add products to storage
            products.notifyAll();

            // Notify observers about storage state changing
            notifyObservers(new StorageMovingContext(getCurrentProductCount(), totalProductCount));
        }
    }

    /**
     * Take product.
     *
     * @return the product
     */
    public T takeProduct() {
        T product = null;

        synchronized (products) {
            // Wait for storage to fill up
            while (isEmpty() && Thread.currentThread().isAlive()) {
                try {
                    products.wait();
                } catch (InterruptedException e) {
                    return product;
                }
            }

            // Take product from storage
            product = products.remove();

            // Allow others to take products from storage
            products.notifyAll();

            // Notify observers about storage state changing
            notifyObservers(new StorageMovingContext(getCurrentProductCount(), totalProductCount));
        }

        return product;
    }
}
