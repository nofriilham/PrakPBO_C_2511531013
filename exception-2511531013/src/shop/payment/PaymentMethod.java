package shop.payment;

import shop.exception.PaymentFailedException;

/** Contract for every payment method. */
public interface PaymentMethod {
    String getName();

    /** Pays the amount; throws when the payment cannot be completed. */
    void pay(long amount) throws PaymentFailedException;
}