package net.ultrasist.api.model.exceptions;

public class ServiceException extends ControllerException {

	public ServiceException(String message) {
		super(new Exception(message));
	}
}
