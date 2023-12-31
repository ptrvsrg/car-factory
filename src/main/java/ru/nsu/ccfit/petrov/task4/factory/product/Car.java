package ru.nsu.ccfit.petrov.task4.factory.product;

/**
 * The type {@code Car} is class that describes car and stores its details.
 *
 * @author ptrvsrg
 */
public class Car
    extends Product {

    private final Engine engine;
    private final Body body;
    private final SeatCover seatCover;

    /**
     * Constructs a Car.
     *
     * @param engine    the engine
     * @param body      the body
     * @param seatCover the seat cover
     */
    public Car(Engine engine, Body body, SeatCover seatCover) {
        if (engine == null || body == null || seatCover == null) {
            throw new IllegalArgumentException("Detail cannot be null");
        }

        this.engine = engine;
        this.body = body;
        this.seatCover = seatCover;
    }

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
        return String.format("%s (%s, %s, %s)", super.toString(), engine, body, seatCover);
    }
}
