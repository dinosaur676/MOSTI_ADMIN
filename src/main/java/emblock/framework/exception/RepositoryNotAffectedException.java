package emblock.framework.exception;

public class RepositoryNotAffectedException extends RuntimeException {
    static final long serialVersionUID = 6799L;

    public RepositoryNotAffectedException(String msg){
        super(msg);
    }

}