package ar.com.ozonosoft.taller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class ResourceForbiddenException
 * 
 * @author Gabriel Gonzalez
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 2847736406318614837L;
	
	private String errorCode;
	
	/**
	 * ResourceForbiddenException
	 * 
	 * @param message
	 */
	public ResourceForbiddenException(String message) {
		super(message);
	}
	
	/**
	 * ResourceForbiddenException
	 * 
	 * @param message
	 * @param errorCode
	 */
	public ResourceForbiddenException(String message, String errorCode) {
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
