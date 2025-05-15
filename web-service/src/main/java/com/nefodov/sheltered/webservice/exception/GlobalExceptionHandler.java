package com.nefodov.sheltered.webservice.exception;

import com.nefodov.sheltered.shared.exception.ShelteredException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShelteredException.class)
    public String handleShelteredException(ShelteredException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}