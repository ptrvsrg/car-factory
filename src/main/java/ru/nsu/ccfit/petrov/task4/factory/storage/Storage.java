package ru.nsu.ccfit.petrov.task4.factory.storage;

import java.util.ArrayDeque;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.petrov.task4.factory.product.Product;
import ru.nsu.ccfit.petrov.task4.observer.Observable;

@Log4j2
public class Storage<T extends Product>
    extends Observable {

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
                    log.warn(e);
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
                    log.warn(e);
                }
            }

            product = products.remove();

            products.notifyAll();

        }

        return product;
    }
}
