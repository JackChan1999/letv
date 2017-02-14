package bolts;

public class Capture<T> {
    private T value;

    public Capture(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }
}
