package wf.utils.java.functions;

import java.util.Objects;

@FunctionalInterface
public interface QuaConsumer<T, U, D, N> {

    void accept(T t, U u, D d, N n);


    default QuaConsumer<T, U, D, N> andThen(QuaConsumer<? super T, ? super U, ? super D, ? super N> after) {
        Objects.requireNonNull(after);

        return (l, r, d, n) -> {
            accept(l, r, d, n);
            after.accept(l, r, d, n);
        };
    }
}