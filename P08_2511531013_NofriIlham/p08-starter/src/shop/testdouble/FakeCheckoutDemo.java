package shop.testdouble;

import shop.discount.NoDiscount;
import shop.exception.PaymentFailedException;
import shop.model.Address;
import shop.model.Order;
import shop.model.PhysicalProduct;
import shop.service.CheckoutService;
import shop.service.InvoicePrinter;
import shop.service.Notifier;
import shop.service.OrderCalculator;

/**
 * Demo test double. CATATAN: ini versi buatan sendiri karena bahan/L3/FakeCheckoutDemo.java
 * dari dosen tidak ada di zip. Ganti dengan file asli bila sudah ada.
 */
public class FakeCheckoutDemo {
    public static void main(String[] args) throws PaymentFailedException {
        PhysicalProduct kopi = new PhysicalProduct("KOPI", "Kopi Gayo 250g", 85_000, 300);
        Order order = new Order("ORD-FAKE", new Address("Tester", "Padang"))
                .add(kopi, 1);

        FakePaymentMethod fake = new FakePaymentMethod();
        CheckoutService service = new CheckoutService(
                new OrderCalculator(10_000), new InvoicePrinter(), new Notifier());

        long total = service.checkout(order, new NoDiscount(), fake);

        System.out.println("Total dibayar : " + total);
        System.out.println("lastAmount    : " + fake.getLastAmount());
        System.out.println("callCount     : " + fake.getCallCount());
        boolean ok = fake.getLastAmount() == 95_000 && fake.getCallCount() == 1;
        System.out.println("Sesuai harapan (95000, 1 kali)? " + ok);
    }
}
