package shop.service;

import java.util.ArrayList;
import java.util.List;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.model.Product;
import shop.payment.PaymentMethod;
import shop.payment.RefundablePayment;
import shop.shipping.Shippable;
import shop.shipping.ShippingService;

public class CheckoutService {
    private final Inventory inventory;

    public CheckoutService(Inventory inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory is required");
        }
        this.inventory = inventory;
    }

    public long subtotal(List<Product> items) {
        long total = 0;
        for (Product product : items) {
            total += product.getFinalPrice();
        }
        return total;
    }

    public int totalWeight(List<Product> items) {
        int grams = 0;
        for (Product product : items) {
            if (product instanceof Shippable shippable) { // a capability, not a class
                grams += shippable.getWeightGrams();
            }
        }
        return grams;
    }

    public long total(List<Product> items, ShippingService shipping) {
        return subtotal(items) + shipping.calculateCost(totalWeight(items));
    }

    /**
     * Reserves stock for every item, then pays.
     *
     * @return the amount that was paid
     * @throws IllegalStateException  if the cart is empty (caller's mistake)
     * @throws OutOfStockException    if an item is out of stock (stock is restored)
     * @throws PaymentFailedException if the payment fails (stock is restored)
     */
    public long checkout(List<Product> items, ShippingService shipping, PaymentMethod payment)
            throws OutOfStockException, PaymentFailedException {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cart must not be empty");
        }
        List<Product> reserved = new ArrayList<>();
        try {
            for (Product product : items) {
                inventory.reserve(product.getSku(), 1);
                reserved.add(product);
            }
            long total = total(items, shipping);
            payment.pay(total);
            return total;
        } catch (OutOfStockException | PaymentFailedException e) {
            for (Product product : reserved) {
                inventory.release(product.getSku(), 1); // undo what was reserved
            }
            throw e; // same exception: the caller decides what to tell the buyer
        }
    }

    public boolean refund(RefundablePayment payment, long amount) {
        return payment.refund(amount);
    }
}