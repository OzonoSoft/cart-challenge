package ar.com.ozonosoft.taller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * class ResourceUnauthorizedException
 * 
 * @author Gabriel Gonzalez
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class ResourceUnauthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = -1687677368317173418L;
	
	private String errorCode;
	
	/**
	 * ResourceUnauthorizedException
	 * 
	 * @param message
	 */
	public ResourceUnauthorizedException(String message) {
		super(message);
	}
	
	/**
	 * ResourceUnauthorizedException
	 * 
	 * @param message
	 * @param errorCode
	 */
	public ResourceUnauthorizedException(String message, String errorCode) {
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
