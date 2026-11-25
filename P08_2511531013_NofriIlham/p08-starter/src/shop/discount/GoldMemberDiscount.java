package shop.discount;

/** Diskon member GOLD: persen ditentukan lewat constructor. */
public class GoldMemberDiscount implements DiscountStrategy {
    private final int percent;

    public GoldMemberDiscount(int percent) {
        this.percent = percent;
    }

    @Override
    public long discountFor(long subtotal) {
        return subtotal * percent / 100;
    }
}
