package eu.tooizi.fooddeliverycostcalculator.DTOs.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a delivery cannot be made.
 */
public class UndeliverableException extends ResponseStatusException
{

    /**
     * Constructor.
     *
     * @param status HTTP status code to be responded with
     * @param reason the reason for the exception
     */
    public UndeliverableException(HttpStatusCode status, String reason)
    {
        super(status, reason);
    }

}
