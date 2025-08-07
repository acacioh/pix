package com.onnitech.pixwebhook.controller.impl;

import com.onnitech.pixwebhook.controller.CallbackController;
import com.onnitech.pixwebhook.service.CallbackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CallbackControllerImpl implements CallbackController {

    private final CallbackService callbackService;

    public CallbackControllerImpl(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @Override
    public ResponseEntity<String> pixPayment(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixPayment(headers, request);
    }

    @Override
    public ResponseEntity<String> claim(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.claim(headers, request);
    }

    @Override
    public ResponseEntity<String> pixPaymentInbound(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixPaymentInbound(headers, request);
    }

    @Override
    public ResponseEntity<String> pixReversal(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixReversal(headers, request);
    }

    @Override
    public ResponseEntity<String> pixReversalInbound(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixReversalInbound(headers, request);
    }

    @Override
    public ResponseEntity<String> pixInfraction(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixInfraction(headers, request);
    }

    @Override
    public ResponseEntity<String> pixMed(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixMed(headers, request);
    }

    @Override
    public ResponseEntity<String> pixRco(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixRco(headers, request);
    }

    @Override
    public ResponseEntity<String> antiFraud(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.antiFraud(headers, request);
    }

    @Override
    public ResponseEntity<String> automaticPix(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.automaticPix(headers, request);
    }

    @Override
    public ResponseEntity<String> automaticPixPaymentInstruction(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.automaticPixPaymentInstruction(headers, request);
    }

}
