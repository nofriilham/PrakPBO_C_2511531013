package shop.discount;

/** Diskon member PLATINUM: tetap 20%. */
public class PlatinumMemberDiscount implements DiscountStrategy {
    @Override
    public long discountFor(long subtotal) {
        return subtotal * 20 / 100;
    }
}
