package shop.app;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import shop.StudentInfo;
import shop.exception.CatalogLoadException;
import shop.exception.InsufficientBalanceException;
import shop.exception.OutOfStockException;
import shop.exception.PaymentFailedException;
import shop.io.CatalogReader;
import shop.model.Product;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.service.Inventory;
import shop.shipping.RegularShipping;
import shop.shipping.ShippingService;

public class ShopApp {

    public static void main(String[] args) {
        // L4: the catalog comes from a file; a different file can be given as an
        // argument
        Path catalogFile = Path.of(args.length > 0 ? args[0] : "katalog.csv");
        CatalogReader reader = new CatalogReader();
        List<Product> catalog;
        try {
            catalog = reader.load(catalogFile);
        } catch (CatalogLoadException e) {
            System.out.println("Toko tidak bisa dibuka: " + e.getMessage());
            System.out.println("Penyebab asli: " + e.getCause());
            return;
        }

        System.out.println("== Toko Online UMKM ==");
        System.out.println("(" + catalog.size() + " produk, "
                + reader.getSkippedCount() + " baris katalog dilewati)");

        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i).getInfo());
        }

        List<Product> cart = new ArrayList<>();

        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                System.out.print("Nomor produk (0 = selesai): ");

                int choice;

                // L1.c: Menangani input nomor produk yang bukan angka
                try {
                    choice = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException _) {
                    System.out.println("  Input harus berupa angka.");
                    continue;
                }

                if (choice == 0) {
                    break;
                }

                // L1.d: Memeriksa apakah nomor produk tersedia
                if (choice < 1 || choice > catalog.size()) {
                    System.out.println("  Nomor produk tidak ada.");
                    continue;
                }

                Product product = catalog.get(choice - 1);

                System.out.print("Jumlah: ");

                int quantity;

                // L1.d: Menangani jumlah yang bukan angka
                try {
                    quantity = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException _) {
                    System.out.println("  Input harus berupa angka.");
                    continue;
                }

                // L1.d: Memastikan jumlah lebih dari 0
                if (quantity <= 0) {
                    System.out.println("  Jumlah harus lebih dari 0.");
                    continue;
                }

                for (int i = 0; i < quantity; i++) {
                    cart.add(product);
                }

                System.out.println("  + " + quantity + " x " + product.getName());
            }
        }

        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong, tidak ada yang dibayar.");
            return;
        }

        Inventory inventory = new Inventory();

        for (Product product : catalog) {
            inventory.addStock(product.getSku(), StudentInfo.stockFor(product.getSku()));
        }

        CheckoutService checkout = new CheckoutService(inventory);
        ShippingService regular = new RegularShipping();

        EWalletPayment wallet = new EWalletPayment(
                "DompetKu",
                StudentInfo.walletBalance());

        System.out.println("\nKeranjang: " + cart.size()
                + " barang, total Reguler Rp"
                + checkout.total(cart, regular));

        System.out.println(buy(checkout, cart, regular, wallet));
        System.out.println("Sisa saldo: Rp" + wallet.getBalance());
    }

    /**
     * L3.e: runs the checkout and turns every outcome into one message for the
     * buyer.
     * The catch order goes from the most specific exception to the most general.
     */
    public static String buy(CheckoutService checkout, List<Product> cart,
            ShippingService shipping, PaymentMethod payment) {
        try {
            long paid = checkout.checkout(cart, shipping, payment);
            return "[OK] Pembayaran berhasil: Rp" + paid;
        } catch (InsufficientBalanceException e) {
            return "[SALDO] Saldo tidak cukup, kurang Rp" + e.getShortfall();
        } catch (PaymentFailedException e) {
            return "[BAYAR] " + e.getMessage();
        } catch (OutOfStockException e) {
            return "[STOK] Stok " + e.getSku() + " tidak cukup (diminta "
                    + e.getRequested() + ", tersedia " + e.getAvailable() + ")";
        }
    }
}