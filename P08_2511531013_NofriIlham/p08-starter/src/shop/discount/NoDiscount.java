package shop.discount;

/** Tanpa diskon: selalu 0. */
public class NoDiscount implements DiscountStrategy {
    @Override
    public long discountFor(long subtotal) {
        return 0;
    }
}
