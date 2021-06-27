package idv.angus.using_shardingsphere_proxy.exception;

public class DataAccessException extends Exception {
    public DataAccessException(Throwable e) {
        super(e);
    }

    public DataAccessException(String message) {
        super(message);
    }
}
