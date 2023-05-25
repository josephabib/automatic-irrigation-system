package com.banque.misr.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlotExceptionHandlerTest {

    @Test
    void testResourceNotFoundException() {
        PlotExceptionHandler plotExceptionHandler = new PlotExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("An error occurred");
        ResponseEntity<?> actualResourceNotFoundExceptionResult = plotExceptionHandler.resourceNotFoundException(ex,
                new ServletWebRequest(new MockHttpServletRequest()));
        assertEquals("The plot is not found", actualResourceNotFoundExceptionResult.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualResourceNotFoundExceptionResult.getStatusCode());
        assertTrue(actualResourceNotFoundExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandleConflict() {
        PlotExceptionHandler plotExceptionHandler = new PlotExceptionHandler();
        RuntimeException ex = new RuntimeException("error");
        ResponseEntity<Object> actualHandleConflictResult = plotExceptionHandler.handleConflict(ex,
                new ServletWebRequest(new MockHttpServletRequest()));
        assertEquals("Exception error occured", actualHandleConflictResult.getBody());
        assertEquals(HttpStatus.CONFLICT, actualHandleConflictResult.getStatusCode());
        assertTrue(actualHandleConflictResult.getHeaders().isEmpty());
    }

}

