package shop.exception;

/**
 * Pembayaran gagal (checked). Induk dari kegagalan bayar yang lebih spesifik.
 */
public class PaymentFailedException extends ShopException {
    private static final long serialVersionUID = 1L;

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}