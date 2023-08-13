package ua.edu.sumdu.nefodov.sheltered.application.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerAdviser {

    private final Logger LOG = LogManager.getLogger(ControllerAdviser.class);

    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindException(BindException ex) {
        LOG.warn("BindException caught with message: " + ex.getMessage());
        ModelAndView mav = new ModelAndView();
        List<String> errorMsgs = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((errorMsg) -> errorMsgs.add(errorMsg.getDefaultMessage()));
        mav.setViewName("error");
        mav.addObject("errors", errorMsgs);
        return mav;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView handleIllegalStateException(IllegalStateException ex) {
        LOG.warn("IllegalStateException caught with message: " + ex.getMessage());
        ModelAndView mav = new ModelAndView();
        List<String> errorMsg = Collections.singletonList(ex.getMessage());
        mav.setViewName("error");
        mav.addObject("errors", errorMsg);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        LOG.warn("Exception caught with message: " + ex.getMessage());
        LOG.warn("Exception class: " + ex.getClass().getCanonicalName());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error");
        return mav;
    }
}
