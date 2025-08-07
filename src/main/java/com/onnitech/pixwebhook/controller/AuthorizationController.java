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
@RequestMapping("authorization")
public interface AuthorizationController {

    @PostMapping(path = "/pix-approval", produces = "application/json")
    ResponseEntity<String> pixApproval(@RequestHeader Map<String, String> headers, HttpServletRequest request);

}
