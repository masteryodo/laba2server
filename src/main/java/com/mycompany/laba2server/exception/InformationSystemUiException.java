package com.mycompany.laba2server.exception;

public class InformationSystemUiException extends RuntimeException
{
    public InformationSystemUiException(String message)
    {
        super(message);
    }

    public InformationSystemUiException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
