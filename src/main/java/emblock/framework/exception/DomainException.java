package emblock.framework.exception;

public class DomainException extends RuntimeException {
    static final long serialVersionUID = 6799L;
    private String errorMessage;

    public DomainException() {
        super();
    }

    public DomainException(Throwable throwable, String errorMessage){
        super(errorMessage, throwable);
        this.errorMessage = errorMessage;
    }

    public DomainException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public DomainException(Throwable throwable){
        super(throwable);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
