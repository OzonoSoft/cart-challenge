package ar.com.ozonosoft.taller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class ResourceBadRequestException
 * 
 *  @author Gabriel Gonzalez
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceBadRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 514295914669244681L;
	
	private String errorCode;

	/**
	 * ResourceBadRequestException
	 * 
	 * @param message
	 */
	public ResourceBadRequestException(String message) {
		super(message);
	}
	
	/**
	 * ResourceBadRequestException
	 * 
	 * @param message
	 * @param errorCode
	 */
	public ResourceBadRequestException(String message, String errorCode) {
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
