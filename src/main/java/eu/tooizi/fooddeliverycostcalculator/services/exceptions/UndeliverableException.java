package eu.tooizi.fooddeliverycostcalculator.services.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class UndeliverableException extends ResponseStatusException
{
    public UndeliverableException(HttpStatusCode status)
    {
        super(status);
    }

    public UndeliverableException(HttpStatusCode status, String reason)
    {
        super(status, reason);
    }

    public UndeliverableException(HttpStatusCode status, String reason, Throwable cause)
    {
        super(status, reason, cause);
    }
}
