package br.com.wmw.vendafacil_frontend.exception;

public class PersistenceException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PersistenceException(String message) {
		super(message);
	}
}
