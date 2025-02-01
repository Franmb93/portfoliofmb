package com.franmunozbetanzos.portfolio.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.franmunozbetanzos.portfolio.constant.ApiConstants.TEST_ERROR_PATH;
import static com.franmunozbetanzos.portfolio.constant.ApiConstants.TEST_PATH;


@RestController
@RequestMapping(TEST_PATH)
@AllArgsConstructor
public class TestController {

    private final MessageSource messageSource;

    @GetMapping(TEST_ERROR_PATH)
    public void triggerException() {
        throw new IllegalArgumentException(messageSource.getMessage("test.exception", null, LocaleContextHolder.getLocale()));
    }
}
