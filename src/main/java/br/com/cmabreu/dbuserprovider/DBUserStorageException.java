package br.com.cmabreu.dbuserprovider;

public class DBUserStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DBUserStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBUserStorageException(Throwable cause) {
        super(cause);
    }

    public DBUserStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
