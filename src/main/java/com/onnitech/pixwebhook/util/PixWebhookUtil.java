package com.onnitech.pixwebhook.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
public class PixWebhookUtil {

    public static final String LOG_ENTRY_ID = "logEntryId";

    private PixWebhookUtil() {
    }

    public static String logEntryIdGenerator() {
        return String.valueOf(new SecureRandom().nextInt(10000));
    }

    public static void errorLogger(String errorMessage) {
        log.error("{}", errorMessage);
    }

}
