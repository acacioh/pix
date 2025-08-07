package com.onnitech.pixwebhook.service.impl;

import com.onnitech.pixwebhook.dto.CallbackDto;
import com.onnitech.pixwebhook.enums.BtgApprovalEntityEnum;
import com.onnitech.pixwebhook.service.CallbackService;
import com.onnitech.pixwebhook.util.JsonUtil;
import com.onnitech.pixwebhook.util.PixWebhookUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static com.onnitech.pixwebhook.util.PixWebhookUtil.LOG_ENTRY_ID;

@Slf4j
@Service
public class CallbackServiceImpl implements CallbackService {

    private Integer versionAndGitHashLogCountdown = 100;

    @Value("${spring.application.version}")
    private String version;

    @Value("${spring.application.git-hash}")
    private String gitHash;

    @Value("${onnitech.url.base}")
    private String baseUrl;

    @Value("${onnitech.url.claim}")
    private String claim;

    @Value("${onnitech.url.payment}")
    private String payment;

    @Value("${onnitech.url.payment-inbound}")
    private String paymentInbound;

    @Value("${onnitech.url.reversal}")
    private String reversal;

    @Value("${onnitech.url.reversal-inbound}")
    private String reversalInbound;

    @Value("${onnitech.url.infraction}")
    private String infraction;

    @Value("${onnitech.url.med}")
    private String med;

    @Value("${onnitech.url.rco}")
    private String rco;

    @Value("${onnitech.url.anti-fraud}")
    private String antiFraud;

    @Value("${onnitech.url.approval.payment-inbound}")
    private String paymentInboundApproval;

    @Value("${onnitech.url.approval.reversal-inbound}")
    private String reversalInboundApproval;

    @Value("${onnitech.url.automatic-pix}")
    private String automaticPix;

    @Value("${onnitech.url.automatic-pix-payment-instruction}")
    private String automaticPixPaymentInstruction;

    private CallbackServiceImpl() {
    }

    @Override
    public ResponseEntity<String> claim(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, claim);
    }

    @Override
    public ResponseEntity<String> pixPayment(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, payment);
    }

    @Override
    public ResponseEntity<String> pixPaymentInbound(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, paymentInbound);
    }

    @Override
    public ResponseEntity<String> pixReversal(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, reversal);
    }

    @Override
    public ResponseEntity<String> pixReversalInbound(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, reversalInbound);
    }

    @Override
    public ResponseEntity<String> pixApproval(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, paymentInboundApproval);
    }

    @Override
    public ResponseEntity<String> pixInfraction(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, infraction);
    }

    @Override
    public ResponseEntity<String> pixMed(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, med);
    }

    @Override
    public ResponseEntity<String> pixRco(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, rco);
    }

    @Override
    public ResponseEntity<String> antiFraud(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, antiFraud);
    }

    @Override
    public ResponseEntity<String> automaticPix(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, automaticPix);
    }

    @Override
    public ResponseEntity<String> automaticPixPaymentInstruction(Map<String, String> headers, HttpServletRequest request) {
        return forwardToIndirect(headers, request, automaticPixPaymentInstruction);
    }

    private ResponseEntity<String> forwardToIndirect(
            Map<String, String> headers, HttpServletRequest httpRequest, String url) {
        if (versionAndGitHashLogCountdown > 0) {
            versionAndGitHashLogCountdown = versionAndGitHashLogCountdown - 1;
        } else {
            log.info("Version: {} - Git Hash: {}", version, gitHash);
            versionAndGitHashLogCountdown = 100;
        }

        try {
            final String body = new String(httpRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (url.equals(paymentInboundApproval) && BtgApprovalEntityEnum.PIX_REVERSAL.containsAtRequest(body)) {
                url = reversalInboundApproval;
            }
            var header = httpHeadersPrepare(headers);

            var callbackObject = JsonUtil.readValue(body, CallbackDto.class);

            log.info("Callback received: {}", JsonUtil.writeValueAsString(callbackObject));

            var request = new HttpEntity<>(body, header);
            log.debug("Request: {}", request);

            if (body.contains("status\": \"PING") || body.contains("status\":\"PING")) {
                log.info("Ping request");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if (Objects.nonNull(url)) {
                var response = getResponse(url, request);
                log.info("Response: {}", response.getBody());
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            } else {
                PixWebhookUtil.errorLogger("Can't get url to call");
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        } catch (Exception e) {
            PixWebhookUtil.errorLogger(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private ResponseEntity<String> getResponse(String url, HttpEntity<String> request) {
        try {
            log.info("Request Url: {}", url);
            return new RestTemplate().exchange(baseUrl + url, HttpMethod.POST, request, String.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        }
    }

    private static HttpHeaders httpHeadersPrepare(Map<String, String> headers) {
        var header = new HttpHeaders();
        headerSetter(headers, header, "x-tracekey");
        headerSetter(headers, header, "accept");
        headerSetter(headers, header, "content-type");
        headerSetter(headers, header, "authorization");
        headerSetter(headers, header, "x-webhook-baas-entity");
        headerSetter(headers, header, "x-webhook-baas-signature");
        headerSetter(headers, header, "x-webhook-baas-notifyid");
        header.set("unique-trx-id", MDC.get(LOG_ENTRY_ID));
        return header;
    }

    private static void headerSetter(Map<String, String> headers, HttpHeaders header, String field) {
        if (Objects.nonNull(headers.get(field))) {
            header.set(field, headers.get(field));
        }
    }

}
