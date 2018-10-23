package rashjz.info.app.pow.exception;

public class ClientException extends RuntimeException{
    public ClientException(String s) {
        super(s);
    }

    public ClientException(Throwable throwable) {
        super(throwable);
    }
}
