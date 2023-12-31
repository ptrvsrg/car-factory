package ru.nsu.ccfit.petrov.task4.factory.product;

import java.util.UUID;

/**
 * The type {@code Product} is class that describes all objects that stored in some storages. Each product have unique
 * 128-bit ID.
 *
 * @author ptrvsrg
 */
public class Product {

    private final UUID id = UUID.randomUUID();

    /**
     * Returns a string representation of the object. In general, the {@code toString} method returns a string that
     * "textually represents" this object. The result should be a concise but informative representation that is easy
     * for a person to read. It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object} returns a string consisting of the name of the class of
     * which the object is an instance, the at-sign character `{@code @}', and the unsigned hexadecimal representation
     * of the hash code of the object. In other words, this method returns a string equal to the value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("%s <%s>", getClass().getSimpleName(), id);
    }
}
