package shop.service;

import shop.discount.DiscountStrategy;
import shop.model.Order;
import shop.model.Shippable;

/** Menghitung rincian tagihan pesanan. */
public class OrderCalculator {
    private final long ratePerKg;

    public OrderCalculator(long ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    public Bill calculate(Order order, DiscountStrategy discountStrategy) {
        // [R1] Menghitung subtotal dan [R3] berat barang yang bisa dikirim
        long subtotal = 0;
        int grams = 0;
        for (Order.Line line : order.lines()) {
            subtotal += line.total();
            if (line.product() instanceof Shippable item) {
                grams += item.getWeightGram() * line.qty();
            }
        }
        // [R2] Menghitung diskon
        long discount = discountStrategy.discountFor(subtotal);
        // [R4] Menghitung shipping
        long shipping = grams == 0 ? 0 : ((grams + 999) / 1000) * ratePerKg;
        // [R5] Menghitung total
        long total = subtotal - discount + shipping;
        return new Bill(subtotal, discount, shipping, total);
    }
}
