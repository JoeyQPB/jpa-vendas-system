package exceptions;

public class RepositoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RepositoryException(String msg) {
		super(msg);
	}

}
