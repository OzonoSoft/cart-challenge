package ar.com.gabriel.cart.domain.spec;

/**
 * @author Gabriel Gonzalez
 */
@FunctionalInterface
public interface Specification<T> {
    boolean isSatisfiedBy(T target);

    default Specification<T> and(Specification<T> other) {
        return target -> this.isSatisfiedBy(target) && other.isSatisfiedBy(target);
    }

    default Specification<T> or(Specification<T> other) {
        return target -> this.isSatisfiedBy(target) || other.isSatisfiedBy(target);
    }

    default Specification<T> not() {
        return target -> !this.isSatisfiedBy(target);
    }

    default void check(T target, RuntimeException ex) {
        if (!isSatisfiedBy(target)) throw ex;
    }
}
