package emblock.framework.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 6799L;

    public NotFoundException(String msg){
        super(msg);
    }
}