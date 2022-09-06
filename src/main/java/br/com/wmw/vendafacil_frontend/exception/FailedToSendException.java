package br.com.wmw.vendafacil_frontend.exception;

public class FailedToSendException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FailedToSendException(String message) {
		super(message);
	}
	
}
