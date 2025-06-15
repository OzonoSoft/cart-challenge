package ar.com.ozonosoft.taller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class ResourceNotFoundException
 * 
 * @author Gabriel Gonzalez
 */
@ResponseStatus(value = HttpStatus.HTTP_VERSION_NOT_SUPPORTED)
public class ResourceNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -2284734685081556971L;
	
	private String errorCode;
	
	/**
	 * ResourceNotFoundException
	 * 
	 * @param message
	 */
	public ResourceNotSupportedException(String message) {
		super(message);
	}
	
	/**
	 * ResourceNotFoundException
	 * 
	 * @param message
	 * @param errorCode
	 */
	public ResourceNotSupportedException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
