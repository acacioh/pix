package com.onnitech.pixwebhook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class TranslatorUtil {

    private static ResourceBundleMessageSource messageSource;

    @Autowired
    TranslatorUtil(ResourceBundleMessageSource messageSource) {
        TranslatorUtil.messageSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        var locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }

}
