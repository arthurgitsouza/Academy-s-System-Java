package com.wintech.portal.exception;

/**
 * Exceção customizada para quando um recurso não é encontrado no banco
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado(a) com ID: %d", resourceName, id));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s não encontrado(a) com %s: %s",
                resourceName, fieldName, fieldValue));
    }
}
