package emblock.framework.exception;

public class EmptyListException extends RuntimeException {

    private static final long serialVersionUID = 6799L;

    public EmptyListException(String msg){
        super(msg);
    }
}