package com.onnitech.pixwebhook.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Hidden
@RestController
@RequestMapping("callback")
public interface CallbackController {

    @PostMapping(path = "/claim", produces = "application/json")
    ResponseEntity<String> claim(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-payment", produces = "application/json")
    ResponseEntity<String> pixPayment(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-payment-inbound", produces = "application/json")
    ResponseEntity<String> pixPaymentInbound(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-reversal", produces = "application/json")
    ResponseEntity<String> pixReversal(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-reversal-inbound", produces = "application/json")
    ResponseEntity<String> pixReversalInbound(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-infraction", produces = "application/json")
    ResponseEntity<String> pixInfraction(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-med", produces = "application/json")
    ResponseEntity<String> pixMed(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/pix-rco", produces = "application/json")
    ResponseEntity<String> pixRco(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/anti-fraud", produces = "application/json")
    ResponseEntity<String> antiFraud(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/automatic-pix", produces = "application/json")
    ResponseEntity<String> automaticPix(@RequestHeader Map<String, String> headers, HttpServletRequest request);

    @PostMapping(path = "/automatic-pix/payment-instruction", produces = "application/json")
    ResponseEntity<String> automaticPixPaymentInstruction(@RequestHeader Map<String, String> headers, HttpServletRequest request);

}
