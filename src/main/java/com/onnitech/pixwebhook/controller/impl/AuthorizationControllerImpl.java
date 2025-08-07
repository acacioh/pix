package com.onnitech.pixwebhook.controller.impl;

import com.onnitech.pixwebhook.controller.AuthorizationController;
import com.onnitech.pixwebhook.service.CallbackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthorizationControllerImpl implements AuthorizationController {

    private final CallbackService callbackService;

    public AuthorizationControllerImpl(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @Override
    public ResponseEntity<String> pixApproval(Map<String, String> headers, HttpServletRequest request) {
        return callbackService.pixApproval(headers, request);
    }

}
