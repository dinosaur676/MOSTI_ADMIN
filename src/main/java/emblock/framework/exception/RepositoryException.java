package emblock.framework.exception;

public class RepositoryException extends RuntimeException {
    static final long serialVersionUID = 6799L;

    public RepositoryException(Throwable throwable, String msg){
        super(msg, throwable);
    }

    public RepositoryException(String msg){
        super(msg);
    }

    public RepositoryException(Throwable throwable){
        super(throwable);
    }

}
