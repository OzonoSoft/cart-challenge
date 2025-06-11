package ar.com.gabriel.cart.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * class ErrorDetails
 * 
 *  @author Gabriel Gonzalez
 */
public class ErrorDetails {
	
	private Date timestamp;
	private String message;
	private int status;
	private HttpStatus error;
	private String errorCode;
	private String path;
	private StackTraceElement[] errors;

	/**
	 * ErrorDetails
	 * 
	 * @param timestamp
	 * @param message
	 * @param status
	 * @param error
	 * @param errorCode
	 * @param path
	 * @param errors
	 */
	public ErrorDetails(Date timestamp, String message, int status, HttpStatus error, String errorCode,
			String path, StackTraceElement[] errors) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.status = status;
		this.error = error;
		this.errorCode = errorCode;
		this.path = path.replace("uri=", "");
		this.errors = errors;
	}

	public HttpStatus getError() {
		return error;
	}

	public void setError(HttpStatus error) {
		this.error = error;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public StackTraceElement[] getErrors() {
		return errors;
	}

	public void setErrors(StackTraceElement[] errors) {
		this.errors = errors;
	}
}