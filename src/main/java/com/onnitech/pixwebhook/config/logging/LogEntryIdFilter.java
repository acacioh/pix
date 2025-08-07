package com.onnitech.pixwebhook.config.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.onnitech.pixwebhook.util.PixWebhookUtil.LOG_ENTRY_ID;
import static com.onnitech.pixwebhook.util.PixWebhookUtil.logEntryIdGenerator;

@Component
public class LogEntryIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        MDC.put(LOG_ENTRY_ID, logEntryIdGenerator());

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(LOG_ENTRY_ID);
        }
    }

}
