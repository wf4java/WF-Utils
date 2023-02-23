package wf.utils.java.functions;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, D> {

    void accept(T t, U u, D d);


    default TriConsumer<T, U, D> andThen(TriConsumer<? super T, ? super U, ? super D> after) {
        Objects.requireNonNull(after);

        return (l, r, d) -> {
            accept(l, r, d);
            after.accept(l, r, d);
        };
    }
}
