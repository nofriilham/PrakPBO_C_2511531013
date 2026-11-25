package shop.service;

import shop.model.Order;
import shop.util.Rupiah;

/** Mengirim notifikasi setelah pesanan dibayar (saat ini lewat SMS). */
public class Notifier {

    public void notifyPaid(Order order, long amount) {
        System.out.println("[SMS] " + order.getAddress().getRecipient() + ": pesanan "
                + order.getOrderId() + " sudah dibayar (" + Rupiah.format(amount) + ")");
    }
}
