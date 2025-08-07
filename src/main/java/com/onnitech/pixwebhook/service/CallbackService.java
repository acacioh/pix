package com.onnitech.pixwebhook.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CallbackService {

    ResponseEntity<String> claim(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixPayment(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixPaymentInbound(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixReversal(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixReversalInbound(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixApproval(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixInfraction(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixMed(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> pixRco(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> antiFraud(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> automaticPix(Map<String, String> headers, HttpServletRequest request);

    ResponseEntity<String> automaticPixPaymentInstruction(Map<String, String> headers, HttpServletRequest request);

}
