package shop.exception;

/** Katalog tidak bisa dimuat (misalnya file tidak ada atau tidak terbaca). */
public class CatalogLoadException extends ShopException {
    private static final long serialVersionUID = 1L;

    public CatalogLoadException(String message) {
        super(message);
    }

    public CatalogLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}