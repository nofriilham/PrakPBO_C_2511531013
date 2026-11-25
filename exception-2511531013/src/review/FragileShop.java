
package review;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import shop.exception.CatalogLoadException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.Inventory;

/** L5: Perbaikan penanganan exception pada FragileShop. */
public class FragileShop {

    /** Membayar pesanan dan meneruskan exception jika pembayaran gagal. */
    public static void payOrder(PaymentMethod payment, long amount)
            throws PaymentFailedException {
        payment.pay(amount);
    }

    /** Memeriksa apakah barang tersedia. */
    public static boolean isInStock(Inventory inventory, String sku) {
        try {
            inventory.reserve(sku, 1);
            inventory.release(sku, 1);
            return true;
        } catch (OutOfStockException e) {
            return false;
        }
    }

    /** Membaca baris pertama dari file katalog. */
    public static String readCatalogHeader(Path file) throws CatalogLoadException {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            return reader.readLine();
        } catch (IOException e) {
            throw new CatalogLoadException("Katalog gagal dibaca: " + file, e);
        }
    }

    /** Menambahkan barang ke keranjang. */
    public static String addToCart(Inventory inventory, String sku, String quantityText) {
        try {
            int quantity = Integer.parseInt(quantityText);
            inventory.reserve(sku, quantity);
            return "Ditambahkan " + quantity + " x " + sku;
        } catch (OutOfStockException | IllegalArgumentException e) {
            return "Gagal: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addStock("KOPI-001", 1);
        EWalletPayment wallet = new EWalletPayment("DompetKu", 50_000);

        try {
            payOrder(wallet, 85_000);
            System.out.println("1) Pembayaran berhasil.");
        } catch (PaymentFailedException e) {
            System.out.println("1) Pembayaran gagal: " + e.getMessage());
        }

        System.out.println("   Sisa saldo: Rp" + wallet.getBalance());

        System.out.println("2) Kopi tersedia? "
                + isInStock(inventory, "KOPI-001"));

        System.out.println("3) "
                + addToCart(inventory, "KOPI-001", "dua"));
        System.out.println("   "
                + addToCart(inventory, "KOPI-001", "5"));

        try {
            System.out.println("4) Header: "
                    + readCatalogHeader(Path.of("katalog-lama.csv")));
        } catch (CatalogLoadException e) {
            System.out.println("4) " + e.getMessage()
                    + " | penyebab: " + e.getCause());
        }
    }
}
